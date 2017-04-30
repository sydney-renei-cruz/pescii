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
@WebServlet(name = "editSupplierServlet", urlPatterns = {"/editSupplierServlet"})
public class editSupplierServlet extends HttpServlet {

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
         
         String preparedSQL = "update Supplier set supplierName=?, supplierAddress=?, "
                 + "supplierContactNumber=?, productClassID=?, lastEdittedBy=?"
                 + " where supplierID=?";
         
         String message = "";
         
         //check the supplier ID
         int supplierID = 0;
         try{supplierID = Integer.parseInt(request.getParameter("supplierIDInput"));}
         catch(Exception e){
             message = "Supplier ID is invalid.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("homePage.jsp").forward(request,response);
            return;
        }
         
         //check supplier name
         String newSupplierName = "";
         try{
             newSupplierName = request.getParameter("supplierNameInput");
             if(newSupplierName.length()>100){
                message = "Supplier name is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("supplier.getDetails?forEdit=yes&suppID="+supplierID).forward(request,response);
                return;
             }
            }
        catch(Exception e){
            message = "Supplier name was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("supplier.getDetails?forEdit=yes&suppID="+supplierID).forward(request,response);
            return;
        }
         
         
         //check supplier address
         String newSupplierAddress = "";
         try{
             newSupplierAddress = request.getParameter("supplierAddressInput");
             if(newSupplierAddress.length()>255){
                message = "Supplier address is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("supplier.getDetails?forEdit=yes&suppID="+supplierID).forward(request,response);
                return;
             }
            }
        catch(Exception e){
            message = "Supplier address was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("supplier.getDetails?forEdit=yes&suppID="+supplierID).forward(request,response);
            return;
        }
         
         
         //check supplier contact number
         String newSupplierContactNumber = "";
         try{
             newSupplierContactNumber = request.getParameter("supplierContactNumberInput");
             if(newSupplierContactNumber.length()>12){
                message = "Contact Number is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("supplier.getDetails?forEdit=yes&suppID="+supplierID).forward(request,response);
                return;
             }
             char c;
             for(int i=0;i<newSupplierContactNumber.length();i++){
                c = newSupplierContactNumber.charAt(i);
                if(!Character.isDigit(c)){
                    message = "Contact Number was input incorrectly.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("supplier.getDetails?forEdit=yes&suppID="+supplierID).forward(request,response);
                    return;
                }
            }
         }
         catch(Exception e){
                message = "Contact Number was input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("supplier.getDetails?forEdit=yes&suppID="+supplierID).forward(request,response);
                return;
         }
         
         
         //check product class
         int newProductClass = 0;
         try{newProductClass = Integer.parseInt(request.getParameter("productClassInput"));}
         catch(Exception e){
            message = "Product Class was input incorrectly.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("supplier.getDetails?forEdit=yes&suppID="+supplierID).forward(request,response);
            return;
         }
         String lastEdittedBy = ""+session.getAttribute("userName");
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1,newSupplierName);
         ps.setString(2,newSupplierAddress);
         ps.setString(3,newSupplierContactNumber);
         ps.setInt(4,newProductClass);
         ps.setString(5,lastEdittedBy);
         ps.setInt(6,supplierID);
         
         ps.executeUpdate();
         
         message = "Supplier successfully editted!";
         request.setAttribute("message", message);
         request.setAttribute("suppID", supplierID);
         request.getRequestDispatcher("anotherSupplier.jsp").forward(request,response);
         
        }
        catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            String message = "Something went wrong. Please try again or contact the administrator.";
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
