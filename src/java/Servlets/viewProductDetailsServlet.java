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
         String inputProductID = request.getParameter("prodID");
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
         
         String forInvoice = "" + request.getParameter("forInvoice");
         if(forInvoice.equals("yes") || session.getAttribute("cart")!=null){
            request.setAttribute("forInvoice", "yes");
         }
         if((""+request.getParameter("forRestock")).equals("yes")){
             request.getRequestDispatcher("createRestockOrder.jsp").forward(request,response);
             return;
         }
         if((""+request.getParameter("forEdit")).equals("yes")){
             request.setAttribute("forEdit", ""+request.getParameter("forEdit"));
             request.getRequestDispatcher("product.getProductClass").forward(request,response);
             return;
         }
         request.getRequestDispatcher("productDetails.jsp").forward(request,response);
         
        }
        catch(Exception ex){
            ex.printStackTrace();
            out.println("error: " + ex);
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
                out.println("Another SQL error: " + ex);
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
