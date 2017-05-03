/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebServlet(name = "viewProductServlet", urlPatterns = {"/viewProductServlet"})
public class getProductServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        
        try {
         Class.forName(context.getInitParameter("jdbcDriver"));
      } catch(ClassNotFoundException ex) {
         ex.printStackTrace();
         out.println("jdbc error: " + ex);
      }
        
        Connection conn = null;
        Statement stmt = null;
        
        try{
        //Allocate a database Connection object
         //This uses the pageContext servlet.  Look at Web.xml for the params!
         //This means we don't need to recompile!
         
         conn = DriverManager.getConnection(context.getInitParameter("databaseUrl"), context.getInitParameter("databaseUser"), context.getInitParameter("databasePassword"));
        
         //Allocate a Statement object within the Connection
         stmt = conn.createStatement();
         
         //check what you're getting a product list for: either invoice or restock creation
         String forOther = ""+ request.getParameter("forOther");
         String cartType = "";
         context.log("session's cartType: "+session.getAttribute("cartType"));
         context.log("forOther in getProductServlet is: "+forOther);
         /*if((session.getAttribute("cartType")==null && forOther!=null) || 
                 ((!(forOther.equals(session.getAttribute("cartType")))) && session.getAttribute("cartType")!=null && forOther!=null)){
             cartType = forOther;
             session.setAttribute("cartType",forOther);
         }*/
         if(forOther!=null && !forOther.equals("") && !forOther.equals("null")){
             context.log("forOther was null. forOther = "+forOther);
             context.log("meanwhile, cartType is: "+session.getAttribute("cartType"));
             cartType = forOther;
             session.setAttribute("cartType",forOther);
         }
         else {
             cartType = ""+session.getAttribute("cartType");
         }
         
         context.log("cart type in getProductServlet is: "+session.getAttribute("cartType"));
         
         //---------------
         //THIS IS WHERE YOU START CHANGING
         String preparedSQL = "select Product.productID, Product.productName, Product.productDescription, "
                 + "Product.productPrice, Product.restockPrice, Product.stocksRemaining, Product.lowStock, "
                 + "Product.brand, Product.productClassID, ProductClass.productClassname, Product.color, "
                 + "Product.supplierID, Supplier.supplierID, Supplier.supplierName from Product "
                 + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                 + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                 + "order by Product.productName asc";
         context.log(preparedSQL);
         //PreparedStatement ps = conn.prepareStatement(preparedSQL);
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         //change the products retrieved depending on the cart.
            //this is for the invoice cart
         if(cartType.equals("invoice")){
            if(session.getAttribute("cart")!=null){
                context.log("something in the invoice cart!");
                LinkedList<productBean> cart = (LinkedList<productBean>)(session.getAttribute("cart"));
                String prodIDs = "";
                context.log("cart size is: "+cart.size());
                if(cart.size()>0){
                    prodIDs = " where productID!="+cart.get(0).getProductID();
                    if(cart.size()>1){
                       for(int i=1;i<cart.size();i++){
                           prodIDs = prodIDs + " and productID!=" + cart.get(i).getProductID();
                       }
                    }
                }
                preparedSQL = "select Product.productID, Product.productName, Product.productDescription, "
                    + "Product.productPrice, Product.restockPrice, Product.stocksRemaining, Product.lowStock, "
                    + "Product.brand, Product.productClassID, ProductClass.productClassname, Product.color, "
                    + "Product.supplierID, Supplier.supplierID, Supplier.supplierName from Product "
                    + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                    + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                    + prodIDs + " order by productName asc";
                context.log(preparedSQL);
                ps = conn.prepareStatement(preparedSQL);
            }
         }
         
            //this is for the RO cart
            context.log("cartType is: "+cartType);
            context.log("cartType == restock? "+cartType.equals("restock"));
         if(cartType.equals("restock")){
            String suppID = "";
            try{
            if(session.getAttribute("suppID")==null || (request.getParameter("suppID")!=null && !((""+session.getAttribute("suppID")).equals(request.getParameter("suppID"))))){
                if(request.getParameter("suppID")!=null && !((""+session.getAttribute("suppID")).equals(request.getParameter("suppID")))){
                    session.setAttribute("ROcart", null);
                    session.setAttribute("ROprodNames", null);
                    session.setAttribute("ROquantity", null);
                    session.setAttribute("ROtotalPrices", null);
                }
                suppID = request.getParameter("suppID");
                session.setAttribute("suppID",suppID);
            }
            else{
                suppID = ""+session.getAttribute("suppID");
            }

            }
            catch(Exception e){
                e.printStackTrace();
                //out.println("error: " + ex);
                String message = "Something went wrong: No Supplier Selected.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("errorPage.jsp").forward(request,response);
                return;
            }
            context.log("suppID in getProductServlet is: "+suppID);
            
            if(session.getAttribute("ROcart")!=null){
                context.log("getting products for RO!");
                LinkedList<String> ROcart = (LinkedList<String>)(session.getAttribute("ROcart"));
                
                String prodIDs = "";
                context.log("ROCart size is: "+ROcart.size());
                if(ROcart.size()>0){
                    prodIDs = " where productID!="+ROcart.get(0);
                    if(ROcart.size()>1){
                       for(int i=1;i<ROcart.size();i++){
                           prodIDs = prodIDs + " and productID!=" + ROcart.get(i);
                       }
                    }
                }
                preparedSQL = "select Product.productID, Product.productName, Product.productDescription, "
                    + "Product.productPrice, Product.restockPrice, Product.stocksRemaining, Product.lowStock, "
                    + "Product.brand, Product.productClassID, ProductClass.productClassname, Product.color, "
                    + "Product.supplierID, Supplier.supplierID, Supplier.supplierName from Product "
                    + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                    + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                    + prodIDs + " and Product.supplierID=? order by productName asc";
                context.log(preparedSQL);
            }
            
            else if(session.getAttribute("ROCart")==null){
            preparedSQL = "select Product.productID, Product.productName, Product.productDescription, "
                    + "Product.productPrice, Product.restockPrice, Product.stocksRemaining, Product.lowStock, "
                    + "Product.brand, Product.productClassID, ProductClass.productClassname, Product.color, "
                    + "Product.supplierID, Supplier.supplierID, Supplier.supplierName from Product "
                    + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                    + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                    + "where Product.supplierID=? order by productName asc";
                context.log(preparedSQL);
            }
                ps = conn.prepareStatement(preparedSQL);
                ps.setString(1,suppID);
         }
         
         //this is for the product low stock level cart
         if(cartType.equals("lowstockLevel")){
            if(session.getAttribute("prodCart")!=null){
                context.log("something in the product cart!");
                LinkedList<productBean> prodCart = (LinkedList<productBean>)(session.getAttribute("prodCart"));
                String prodIDs = "";
                context.log("prodCart size is: "+prodCart.size());
                if(prodCart.size()>0){
                    prodIDs = " where productID!="+prodCart.get(0).getProductID();
                    if(prodCart.size()>1){
                       for(int i=1;i<prodCart.size();i++){
                           prodIDs = prodIDs + " and productID!=" + prodCart.get(i).getProductID();
                       }
                    }
                }
                preparedSQL = "select Product.productID, Product.productName, Product.productDescription, "
                    + "Product.productPrice, Product.restockPrice, Product.stocksRemaining, Product.lowStock, "
                    + "Product.brand, Product.productClassID, ProductClass.productClassname, Product.color, "
                    + "Product.supplierID, Supplier.supplierID, Supplier.supplierName from Product "
                    + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                    + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                    + prodIDs + " order by productName asc";
                context.log(preparedSQL);
                ps = conn.prepareStatement(preparedSQL);
            }
         }
         
         
         
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<productBean> productsRetrieved = new ArrayList<productBean>();
         //retrieve the information.
            while(dbData.next()){
               productBean pbean = new productBean();
                pbean.setProductID(dbData.getString("productID"));
                pbean.setProductName(dbData.getString("productName"));
                pbean.setProductDescription(dbData.getString("productDescription"));
                pbean.setProductPrice(dbData.getFloat("productPrice"));
                pbean.setRestockPrice(dbData.getFloat("restockPrice"));
                pbean.setStocksRemaining(dbData.getInt("stocksRemaining"));
                pbean.setLowStock(dbData.getInt("lowStock"));
                pbean.setBrand(dbData.getString("brand"));
                pbean.setProductClassID(dbData.getInt("productClassID"));
                pbean.setProductClassName(dbData.getString("productClassName"));
                pbean.setColor(dbData.getString("color"));
                pbean.setSupplierID(dbData.getInt("supplierID"));
                pbean.setSupplierName(dbData.getString("supplierName"));
                productsRetrieved.add(pbean);
            }
         request.setAttribute("productsList", productsRetrieved);
         
         
         if((cartType.equals("invoice")/* && session.getAttribute("cart")!=null)*/)){
             request.setAttribute("forInvoice", "yes");
         }
         else if(forOther.equals("restock")){
             request.setAttribute("forRestock", "yes");
         }
         request.getRequestDispatcher("getProduct.jsp").forward(request,response);
            
         
        }
        catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            String message = "Something went wrong. Please try again or contact the administrator.";
            request.setAttribute("message", message);
            request.getRequestDispatcher("errorPage.jsp").forward(request,response);
        }
        finally {
            out.close();  // Close the output writer
            try {
              //Close the resources
              if (stmt != null) stmt.close();
              if (conn != null) conn.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                //out.println("Another SQL error: " + ex);
                String message = "Something went wrong. Please try again or contact the administrator.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("errorPage.jsp").forward(request,response);
            }
     }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
