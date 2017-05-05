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
                 + "supplierID=?, lastEdittedBy=? "
                 + "where productID=?";
         
         String message = "";
         
         //check the product ID
         int productID = Integer.parseInt(request.getParameter("productIDInput"));
         
         
         //check the product name
         String newProductName = "";
         boolean productName=false;
         try{
             if(request.getParameter("productNameInput")!=null && !request.getParameter("productNameInput").equals("")){
             newProductName = request.getParameter("productNameInput");
                if(newProductName.length()>255){
                   message = "Product Name is too long.";
                   request.setAttribute("message",message);
                   request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                   return;
                }
                else{productName=true;}
             }
            }
            catch(Exception e){
                message = "Product Name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
         
         String newProductDescription="";
         boolean productDescription=false;
         try{
            if(request.getParameter("productDescriptionInput")!=null && !request.getParameter("productDescriptionInput").equals("")){
                newProductDescription = request.getParameter("productDescriptionInput");
                productDescription=true;
            }
         }
         catch(Exception e){
         message = "Product Name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
         }
         
         //check the product price
         float newProductPrice = 0;
         boolean productPrice=false;
         try{
             
            newProductPrice = Float.parseFloat(request.getParameter("productPriceInput"));
            if(newProductPrice < 0){
                message = "Product Price was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
            else{productPrice=true;}
         }
         catch(Exception e){
            message = "Product Price was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
            return;
         }
         
         //check restock price
         float newRestockPrice = 0;
         boolean restockPrice=false;
         try{
                newRestockPrice = Float.parseFloat(request.getParameter("restockPriceInput"));
                if(newRestockPrice < 0){
                    message = "Restock Price was input incorrectly. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                    return;
                }
                else{restockPrice=true;}
            }
            catch(Exception e){
                message = "Restock Price was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
         
         
         //check low stock
         int newLowStock = 0;
         boolean lowStock=false;
         try{
                newLowStock = Integer.parseInt(request.getParameter("lowStockInput"));
                if(newLowStock < 0){
                    message = "Low Stock level was input incorrectly. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                    return;
                }
                else{lowStock=true;}
         }
         catch(Exception e){
                message = "Low Stock level was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
            }
         
         
         //check brand
         String newBrand = "";
         boolean brand=false;
         try{
             if(request.getParameter("brandInput")!=null && !request.getParameter("brandInput").equals("")){
                newBrand = request.getParameter("brandInput");
                if(newBrand.length()>255){
                    message = "Brand name is too long.";
                    request.setAttribute("message",message);
                   request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                   return;
                }
                else{brand=true;}
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
         boolean productClass=false;
         try{
             newProductClass = Integer.parseInt(request.getParameter("productClassInput"));
             productClass=true;
         }
         catch(Exception e){
                message = "Product Class input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
         }
         
         
         //check color
         String newColor = "";
         boolean color=false;
         try{
             if(request.getParameter("colorInput")!=null && !request.getParameter("colorInput").equals("")){
                newColor = request.getParameter("colorInput");
                if(newColor.length()>20){
                    message = "Color name is too long.";
                    request.setAttribute("message",message);
                   request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                   return;
                }
                else{color=true;}
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
         boolean supplier=false;
         try{
             newSupplier = Integer.parseInt(request.getParameter("supplierInput"));
             supplier=true;
         }
         catch(Exception e){
                message = "Supplier was input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("product.getDetails?forEdit=yes&prodID="+productID).forward(request,response);
                return;
         }
         String lastEdittedBy = ""+session.getAttribute("userName");
         preparedSQL = "update Product set productName=?, productDescription=?, "
                 + "productPrice=?, restockPrice=?, lowStock=?, brand=?, productClassID=?, color=?, "
                 + "supplierID=?, lastEdittedBy=? "
                 + "where productID=?";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         if(productName==true){
            preparedSQL = "update Product set productName=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setString(1,newProductName);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the productName!");
         }
         if(productDescription==true){
            preparedSQL = "update Product set productDescription=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setString(1,newProductDescription);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the productDescription!");
         }
         if(productPrice==true){
            preparedSQL = "update Product set productPrice=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setFloat(1,newProductPrice);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the productPrice!");
         }
         if(restockPrice==true){
            preparedSQL = "update Product set restockPrice=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setFloat(1,newRestockPrice);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the restockPrice!");
         }
         if(lowStock==true){
            preparedSQL = "update Product set lowStock=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setInt(1,newLowStock);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the lowStock!");
         }
         if(brand==true){
            preparedSQL = "update Product set brand=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setString(1,newBrand);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the brand!");
         }
         if(productClass==true){
            preparedSQL = "update Product set productClassID=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setInt(1,newProductClass);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the productClassID!");
         }
         if(color==true){
            preparedSQL = "update Product set color=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setString(1,newColor);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the product color!");
         }
         if(supplier==true){
            preparedSQL = "update Product set supplierID=? where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setInt(1,newSupplier);
            ps.setInt(2,productID);
            ps.executeUpdate();
            context.log("updated the supplierID!");
         }
         
        preparedSQL = "update Product set lastEdittedBy=? where productID=?";
        ps = conn.prepareStatement(preparedSQL);
        ps.setString(1,lastEdittedBy);
        ps.setInt(2,productID);
        ps.executeUpdate();
        context.log("updated the product lastEdittedBy!");
         
        /* 
         ps.setString(1,newProductName);
         ps.setString(2,newProductDescription);
         ps.setFloat(3,newProductPrice);
         ps.setFloat(4,newRestockPrice);
         ps.setInt(5,newLowStock);
         ps.setString(6,newBrand);
         ps.setInt(7,newProductClass);
         ps.setString(8,newColor);
         ps.setInt(9, newSupplier);
         ps.setString(10, lastEdittedBy);
         ps.setInt(11,productID);
         
         context.log(preparedSQL);
         ps.executeUpdate();
         */
         message = "Product successfully editted!";
         request.setAttribute("message", message);
         request.setAttribute("prodID", productID);
         request.getRequestDispatcher("anotherProduct.jsp").forward(request,response);
         
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
