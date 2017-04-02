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
         
         String message = "";
         
         //check the product ID
         int productID = Integer.parseInt(request.getParameter("productIDInput"));
         
         
         //check the product name
         String newProductName = "";
         try{
             newProductName = request.getParameter("productNameInput");
             if(newProductName.length()>255){
                message = "Product Name is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Product Name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
         
         
         String newProductDescription = request.getParameter("productDescriptionInput");
         
         
         //check the product price
         float newProductPrice = 0;
         try{
            newProductPrice = Float.parseFloat(request.getParameter("productPriceInput"));
         }
         catch(Exception e){
            message = "Product Price was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
            return;
         }
         
         //check restock price
         float newRestockPrice = 0;
         try{
                newRestockPrice = Float.parseFloat(request.getParameter("restockPriceInput"));
            }
            catch(Exception e){
                message = "Restock Price was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
         
         
         //check low stock
         int newLowStock = 0;
         try{
                newLowStock = Integer.parseInt(request.getParameter("lowStockInput"));
         }
         catch(Exception e){
                message = "Low Stock level was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
         
         
         //check brand
         String newBrand = "";
         try{
             newBrand = request.getParameter("brandInput");
             if(newBrand.length()>255){
                 message = "Brand name is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Brand name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
         
         //check product lass
         int newProductClass = 0;
         try{newProductClass = Integer.parseInt(request.getParameter("productClassInput"));}
         catch(Exception e){
                message = "Product Class input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
         }
         
         
         //check color
         String newColor = request.getParameter("colorInput");
         try{
             newColor = request.getParameter("colorInput");
             if(newColor.length()>20){
                 message = "Color name is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Color name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
         
         //check supplier
         int newSupplier = 0;
         try{newSupplier = Integer.parseInt(request.getParameter("supplierInput"));}
         catch(Exception e){
                message = "Supplier was input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
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
         
         message = "Product successfully editted!";
         request.setAttribute("message", message);
         request.getRequestDispatcher("homePage.jsp").forward(request,response);
         
        }
        catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            String message = "Something went wrong. Error: "+ex;
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
                String message = "Something went wrong. Error: "+ex;
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
