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
import javax.servlet.http.HttpSession;

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
        HttpSession session = request.getSession();
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

             String message = "";
             //check the product ID
             int productID = Integer.parseInt(request.getParameter("productIDInput"));
    //         int productID = Integer.parseInt((String) session.getAttribute("productIDInput"));
             context.log("Product ID: " + productID);

             String newProductName = "";
             String newProductDescription = "";
             float newProductPrice = 0;
             float newRestockPrice = 0;
             int newLowStock = 0;
             String newBrand = "";
             int newProductClass = 0;
             String newColor = "";
             int newSupplier = 0;

             try{
                newProductName = newProductName = request.getParameter("productNameInput");
                newProductDescription = request.getParameter("productDescriptionInput");
                newProductPrice = Float.parseFloat(request.getParameter("productPriceInput"));
                newRestockPrice = Float.parseFloat(request.getParameter("restockPriceInput"));
                newLowStock = Integer.parseInt(request.getParameter("lowStockInput"));
                newBrand = request.getParameter("brandInput");
                newProductClass = Integer.parseInt(request.getParameter("productClassInput"));
                newColor = request.getParameter("colorInput");
                newSupplier = Integer.parseInt(request.getParameter("supplierInput"));
                 if(newProductName.length()>255){
                    message = "Product Name is too long.";
                    session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
                 }
                 if(newProductPrice < 0){
                    message = "Product Price was input incorrectly. It should also not be blank.";
                    session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
                }

                if(newRestockPrice < 0){
                    message = "Restock Price was input incorrectly. It should also not be blank.";
                    session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
                }

                if(newLowStock < 0){
                    message = "Low Stock level was input incorrectly. It should also not be blank.";
                    session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
                }
                 if(newBrand.length()>255){
                     message = "Brand name is too long.";
                     session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
                 }
                 if(newColor.length()>20){
                     message = "Color name is too long.";
                     session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
                 }
            }catch(Exception e){
                message = "All fields are required";
                session.setAttribute("error_msg", message);
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

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

             message = "Product successfully edited!";
             request.setAttribute("prodID", productID);
             session.setAttribute("success_msg", message);
            response.sendRedirect(request.getHeader("referer"));
        }catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            String message = "Something went wrong. Please try again or contact the administrator.";
            request.setAttribute("message", message);
            request.getRequestDispatcher("errorPage.jsp").forward(request,response);
            context.log("Exception: " + ex);
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
