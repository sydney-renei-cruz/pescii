/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "editProductServlet_1", urlPatterns = {"/editProductServlet_1"})
public class editProductServlet extends HttpServlet {

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
         PrintWriter out = response.getWriter();
        
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
         
         String preparedSQL = "update Product set productName=?, productDescription=?, "
                 + "productPrice=?, restockPrice=?, lowStock=?, brand=?, productClassID=?, color=?, "
                 + "supplierID=? "
                 + "where productID=?";
         
         //int restockOrderID = Integer.parseInt(request.getParameter("restockOrderIDInput"));
         context.log(request.getParameter("productIDInput"));
         int productID = Integer.parseInt(request.getParameter("productIDInput"));
         String newProductName = request.getParameter("productNameInput");
         String newProductDescription = request.getParameter("productDescriptionInput");
         float newProductPrice = Float.parseFloat(request.getParameter("productPriceInput"));
         float newRestockPrice = Float.parseFloat(request.getParameter("restockPriceInput"));
         int newLowStock = Integer.parseInt(request.getParameter("lowStockInput"));
         String newBrand = request.getParameter("brandInput");
         int newProductClass = Integer.parseInt(request.getParameter("productClassInput"));
         String newColor = request.getParameter("colorInput");
         int newSupplier = Integer.parseInt(request.getParameter("supplierInput"));
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1,newProductName);
         ps.setString(2,newProductDescription);
         ps.setFloat(3,newProductPrice);
         ps.setFloat(4,newRestockPrice);
         ps.setInt(5,newLowStock);
         ps.setString(6,newBrand);
         ps.setInt(7,newProductClass);
         ps.setString(8,newColor);
         ps.setInt(9, newSupplier);
         ps.setInt(10,productID);
         
         context.log(preparedSQL);
         ps.executeUpdate();
         
         String message = "Product successfully editted!";
         request.setAttribute("message", message);
         request.getRequestDispatcher("homePage.jsp").forward(request,response);
         
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
