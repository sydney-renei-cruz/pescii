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
@WebServlet(name = "editSalesRepServlet", urlPatterns = {"/editSalesRepServlet"})
public class editSalesRepServlet extends HttpServlet {

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
         
         String preparedSQL = "update SalesRep set salesRepFirstName=?, salesRepLastName=?, "
                 + "salesRepMobileNumber=?, salesRepAddress=?, lastEdittedBy=? "
                 + "where salesRepID=?";
         
         String message = "";
         
         //check salesrep ID
         int salesRepID = 0;
         try{salesRepID = Integer.parseInt(request.getParameter("srID"));}
         catch(Exception e){
                message = "Sales Representative ID is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
         }
         
         
         //check first name
         String newSalesRepFirstName = "";
         try{
             newSalesRepFirstName = request.getParameter("newSalesRepFirstNameInput");
             if(newSalesRepFirstName.length()>100){
                 message = "First name is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "First name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
            }
         
         
         //check last name
         String newSalesRepLastName = "";
         try{
             newSalesRepLastName = request.getParameter("newSalesRepLastNameInput");
             if(newSalesRepLastName.length()>100){
                message = "Last name is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Last name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
            }
         
         
         //check mobile number
         String newSalesRepMobileNumber = "";
         try{
             newSalesRepMobileNumber = request.getParameter("newSalesRepMNInput");
             if(newSalesRepMobileNumber.length()>12){
                 message = "Mobile Number is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
             }
             char c;
             for(int i=0;i<newSalesRepMobileNumber.length();i++){
                c = newSalesRepMobileNumber.charAt(i);
                if(!Character.isDigit(c)){
                    message = "Mobile Number was input incorrectly.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                    return;
                }
            }
         }
         catch(Exception e){
                message = "Mobile Number was input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
         }
         
         
         //check address
         String newSalesRepAddress = "";
         try{
             newSalesRepAddress = request.getParameter("newSalesRepAddressInput");
             if(newSalesRepAddress.length()>255){
                message = "Address is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Address was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
            }
         
         
         String lastEdittedBy = ""+session.getAttribute("userName");
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1,newSalesRepFirstName);
         ps.setString(2,newSalesRepLastName);
         ps.setString(3,newSalesRepMobileNumber);
         ps.setString(4,newSalesRepAddress);
         ps.setString(5,lastEdittedBy);
         ps.setInt(6,salesRepID);
         
         ps.executeUpdate();
         
         message = "Sales Rep successfully editted!";
         request.setAttribute("message", message);
         request.setAttribute("srID", salesRepID);
         request.getRequestDispatcher("anotherSalesRep.jsp").forward(request,response);
         
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
