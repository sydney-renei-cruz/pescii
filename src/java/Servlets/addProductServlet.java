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
@WebServlet(name = "addProductServlet", urlPatterns = {"/addProductServlet"})
public class addProductServlet extends HttpServlet {

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
        //DO NOT CHANGE THESE
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
         Class.forName(context.getInitParameter("jdbcDriver"));
      } catch(ClassNotFoundException ex) {
         ex.printStackTrace();
         out.println("jdbc error: " + ex);
      }
        
        Connection conn = null;
        Statement stmt = null;
        int success = 0;
        
        try{
            conn = DriverManager.getConnection(context.getInitParameter("databaseUrl"), context.getInitParameter("databaseUser"), context.getInitParameter("databasePassword"));

            //Allocate a Statement object within the Connection
            stmt = conn.createStatement();

            //---------------
            //THIS IS WHERE YOU START CHANGING

            String preparedSQL = "insert into Product(productName, productDescription, productPrice, "
                    + "restockPrice, stocksRemaining, lowStock, brand, productClassID, color, supplierID, "
                    + "lastEdittedBy) values(?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(preparedSQL, Statement.RETURN_GENERATED_KEYS);

            String message = "";

            //check the product name
            String inputProductName = "";
            String inputProductDesc = request.getParameter("productDescInput");
            float inputProductPrice = 0;
            float inputRestockPrice = 0;
            int inputLowStock = 0;
            String inputBrand = "";
            int inputProductClass = 0;
            String inputColor = "";
            int inputSupplier = 0;
            try{
               inputProductName = request.getParameter("productNameInput");
               inputRestockPrice = Float.parseFloat(request.getParameter("restockPriceInput"));
               inputProductPrice = Float.parseFloat(request.getParameter("productPriceInput"));
               inputLowStock = Integer.parseInt(request.getParameter("lowStockInput"));
               inputBrand = request.getParameter("brandInput");
               inputProductClass = Integer.parseInt(request.getParameter("productClassInput"));
               inputColor = request.getParameter("colorInput");
               inputSupplier = Integer.parseInt(request.getParameter("supplierInput"));

               if(inputProductName.length()>255){
                   message = "Product Name is too long";
                   session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
               }
               if(inputProductPrice<0){
                   message = "Product Price was input incorrectly. It should also not be blank.";
                   session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
               }
               if(inputRestockPrice<0){
                   message = "Restock Price was input incorrectly. It should also not be blank.";
                   session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
               }
               if(inputLowStock<0){
                   message = "Low Stock level was input incorrectly. It should also not be blank.";
                   session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
               }

                if(inputBrand.length()>255){
                    message = "Brand name is too long.";
                   session.setAttribute("error_msg", message);
                   response.sendRedirect(request.getHeader("referer"));
                   return;
                }

                if(inputColor.length()>20){
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


            String lastEdittedBy = ""+session.getAttribute("userName");

            ps.setString(1,inputProductName);
            ps.setString(2,inputProductDesc);
            ps.setFloat(3,inputProductPrice);
            ps.setFloat(4,inputRestockPrice);
            ps.setInt(5,0);
            ps.setInt(6,inputLowStock);
            ps.setString(7,inputBrand);
            ps.setInt(8,inputProductClass);
            ps.setString(9,inputColor);
            ps.setInt(10,inputSupplier);
            ps.setString(11,lastEdittedBy);
            success = ps.executeUpdate();                   //at this point, you have already inserted into the database

            //get the generated product ID
            int productIDInput;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                   productIDInput = generatedKeys.getInt(1);
                   context.log("The productID of the new product is: " + productIDInput);
                }
               else {
                   throw new SQLException("--> no productID retrieved");
               }
           }
            if(success > 0){
                message = "Product successfully created!";
                request.setAttribute("prodID", productIDInput);
                session.setAttribute("success_msg", message);
                response.sendRedirect(request.getHeader("referer"));
            }else{
                message = "Something went wrong, please try again.";
                session.setAttribute("error_msg", message);
                response.sendRedirect(request.getHeader("referer"));
            }
            

         
        }catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            String message = "Something went wrong. Please try again or contact the administrator.";
            request.setAttribute("message", message);
            request.getRequestDispatcher("errorPage.jsp").forward(request,response);
        }finally {
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
