/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.productBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
@WebServlet(name = "viewProductDetailsServlet", urlPatterns = {"/viewProductDetailsServlet"})
public class viewProductDetailsServlet extends HttpServlet {

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
         context.log("you're in the viewProductDetailsServlet!");
         //---------------
         //first get the customer details
         String preparedSQL = "select Product.productID, Product.productName, Product.productDescription, "
                 + "Product.productPrice, Product.restockPrice, Product.stocksRemaining, Product.lowStock, "
                 + "Product.brand, Product.productClassID, ProductClass.productClassname, Product.color, "
                 + "Product.supplierID, Supplier.supplierID, Supplier.supplierName, "
                 + "Product.dateCreated, Product.lastEdittedBy from Product "
                 + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                 + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                 + "where productID = ? order by productName asc";
         String inputProductID = "";
         //String forRestock = "";
         String forEdit = "";
         //String forInvoice = "";
         String cartSession="";
         String message = "";
         try{
             if(request.getParameter("prodID")!=null){
                inputProductID = request.getParameter("prodID");
             }
             else{
                inputProductID = ""+request.getAttribute("prodID");
             }
             /*
             if(request.getParameter("forInvoice")!=null){
                 forInvoice = request.getParameter("forInvoice");
             }
             else{
                 forInvoice = ""+request.getAttribute("forInvoice");
             }
             if(request.getParameter("forRestock")!=null){
                 forRestock = request.getParameter("forRestock");
             }
             else{
                 forRestock = ""+request.getAttribute("forRestock");
             }
             */
             cartSession = ""+session.getAttribute("cartType");
             
             if(request.getParameter("forEdit")!=null){
                 forEdit = request.getParameter("forEdit");
             }
             else{
                 forEdit = ""+request.getAttribute("forEdit");
             }
             if(request.getAttribute("message")!=null){
                 message = ""+request.getAttribute("message");
             }
             
         }
         catch(Exception e){
             request.getRequestDispatcher("errorPage.jsp").forward(request,response);
         }
         context.log("you're in the viewProductDetailsServlet! 2");
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1, inputProductID);
         
         productBean pbean = new productBean();
         ResultSet dbData = ps.executeQuery();
         while(dbData.next()){
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
            pbean.setDateCreated(dbData.getTimestamp("dateCreated"));
            pbean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
         }
         request.setAttribute("product", pbean);
         request.setAttribute("message",message);
         //if(forInvoice.equals("yes") || session.getAttribute("cart")!=null){
         if(cartSession.equals("invoice")){
            request.setAttribute("forInvoice", "yes");
         }
         //if(forRestock.equals("yes")){
         if(cartSession.equals("restock")){
             //request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);
             request.setAttribute("forRestock", "yes");
         }
         if(forEdit.equals("yes")){
             request.setAttribute("message",request.getAttribute("message"));
             request.setAttribute("forEdit", ""+request.getParameter("forEdit"));
             request.getRequestDispatcher("product.getProductClass").forward(request,response);
             return;
         }
         request.getRequestDispatcher("productDetails.jsp").forward(request,response);
         
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
