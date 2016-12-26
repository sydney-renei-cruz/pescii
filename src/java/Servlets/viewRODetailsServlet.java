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
@WebServlet(name = "viewRODetailsServlet", urlPatterns = {"/viewRODetailsServlet"})
public class viewRODetailsServlet extends HttpServlet {

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
         //first get the RestockOrder details
         String preparedSQL = "select RestockOrder.restockOrderID, Product.productID, RestockOrder.productID, "
                 + "RestockOrder.ROName, RestockOrder.numberOfPiecesOrdered, Product.restockPrice, "
                 + "RestockOrder.numberOfPiecesReceived, Product.supplierID, RestockOrder.purpose, "
                 + "RestockOrder.RODateDue, RestockOrder.RODateDelivered, RestockOrder.amountPaid, "
                 + "RestockOrder.discount, RestockOrder.dateCreated, RestockOrder.lastEdittedBy, "
                 + "RestockOrder.datePaid, Product.productClassID, ProductClass.productClassID, "
                 + "ProductClass.productClassName, Supplier.supplierID, Supplier.supplierName, "
                 + "Product.productName "
                 + "from RestockOrder "
                 + "inner join Product on Product.productID = RestockOrder.productID "
                 + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                 + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                 + "where RestockOrder.restockOrderID=? "
                 + "order by RestockOrder.datecreated desc";
         String inputRestockOrderID = request.getParameter("restockID");
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1, inputRestockOrderID);
         context.log(preparedSQL);
         
         restockOrderBean rbean = new restockOrderBean();
         ResultSet dbData = ps.executeQuery();
         while(dbData.next()){
            rbean.setRestockOrderID(dbData.getInt("restockOrderID"));
                rbean.setRestockOrderName(dbData.getString("ROName"));
                rbean.setProductID(dbData.getInt("productID"));
                rbean.setProductName(dbData.getString("productName"));
                rbean.setNumberOfPiecesOrdered(dbData.getInt("numberOfPiecesOrdered"));
                rbean.setNumberOfPiecesReceived(dbData.getInt("numberOfPiecesReceived"));
                rbean.setSupplierID(dbData.getInt("supplierID"));
                rbean.setSupplierName(dbData.getString("supplierName"));
                rbean.setPurpose(dbData.getString("purpose"));
                rbean.setRODateDue(dbData.getDate("RODateDue"));
                rbean.setRODateDelivered(dbData.getDate("RODateDelivered"));
                rbean.setRestockPrice(dbData.getFloat("restockPrice"));
                rbean.setAmountPaid(dbData.getFloat("amountPaid"));
                rbean.setDiscount(dbData.getFloat("discount"));
                rbean.setDatePaid(dbData.getDate("datePaid"));
                rbean.setDateCreated(dbData.getTimestamp("dateCreated"));
                rbean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
         }
         request.setAttribute("restockOrder", rbean);
         context.log("ID IIIIIISSS: " + rbean.getRestockOrderID());
         
         //now you get the Product's details
         /*
         preparedSQL = "select * from Product where productID = ?";
         int inputProductID = rbean.getProductID();
         ps = conn.prepareStatement(preparedSQL);
         ps.setInt(1, inputProductID);
         
         productBean pbean = new productBean();
         dbData = ps.executeQuery();
         while(dbData.next()){
            pbean.setProductID(dbData.getString("productID"));
            pbean.setProductName(dbData.getString("productName"));
            pbean.setProductDescription(dbData.getString("productDescription"));
            pbean.setProductPrice(dbData.getFloat("productPrice"));
            pbean.setRestockPrice(dbData.getFloat("restockPrice"));
            pbean.setStocksRemaining(dbData.getInt("stocksRemaining"));
            pbean.setLowStock(dbData.getInt("lowStock"));
            pbean.setBrand(dbData.getString("brand"));
            pbean.setProductClass(dbData.getString("productClass"));
            pbean.setColor(dbData.getString("color"));
         }
         request.setAttribute("product", pbean);
         */
         String editRestock = ""+request.getParameter("editRestock");
         if(editRestock.equals("yes")) {
             request.getRequestDispatcher("editRestockOrder.jsp").forward(request,response);
         }
         else{
             request.getRequestDispatcher("restockOrderDetails.jsp").forward(request,response);
         }
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
