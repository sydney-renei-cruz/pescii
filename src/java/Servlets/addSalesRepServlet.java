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
@WebServlet(name = "addSalesRepServlet_1", urlPatterns = {"/addSalesRepServlet_1"})
public class addSalesRepServlet extends HttpServlet {

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
        //DO NOT CHANGE THESE
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
         Class.forName(context.getInitParameter("jdbcDriver"));
      } catch(ClassNotFoundException ex) {
         ex.printStackTrace();
         out.println("jdbc error: " + ex);
      }
        
        Connection conn = null;
        Statement stmt = null;
        
        try{
        
         conn = DriverManager.getConnection(context.getInitParameter("databaseUrl"), context.getInitParameter("databaseUser"), context.getInitParameter("databasePassword"));
        
         //Allocate a Statement object within the Connection
         stmt = conn.createStatement();
         
         //---------------
         //THIS IS WHERE YOU START CHANGING
         HttpSession session = request.getSession();
         String preparedSQL = "insert into SalesRep(salesRepFirstName, salesRepMobileNumber, salesRepAddress, salesRepLastName, lastEdittedBy) values (?,?,?,?,?)";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         String message;
         
         //check the salesrep first name
         String inputSalesRepFirstName = "";
         try{
             inputSalesRepFirstName = request.getParameter("salesrepFirstNameInput");
             if(inputSalesRepFirstName.length()>100){
                 message = "First name is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "First name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                return;
            }
         
         //check the salesrep last name
         String inputSalesRepLastName = "";
         try{
             inputSalesRepLastName = request.getParameter("salesrepLastNameInput");
             if(inputSalesRepFirstName.length()>100){
                 message = "Last name is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Last name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                return;
            }
         
         //check the salesrep mobile number
         String inputSalesRepMobileNumber = "";
         try{
             inputSalesRepMobileNumber = request.getParameter("salesrepMNInput");
             if(inputSalesRepMobileNumber.length()>12){
                 message = "Mobile Number is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                return;
             }
             char c;
             for(int i=0;i<inputSalesRepMobileNumber.length();i++){
                c = inputSalesRepMobileNumber.charAt(i);
                if(!Character.isDigit(c)){
                    message = "Mobile Number was input incorrectly.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                    return;
                }
            }
         }
         catch(Exception e){
                 message = "Mobile Number was input incorrectly.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                return;
         }
         
         
         String inputSalesRepAddress = "";
         try{
             inputSalesRepAddress = request.getParameter("salesrepAddressInput");
             if(inputSalesRepAddress.length()>255){
                 message = "Address is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Address was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("addSalesRep.jsp").forward(request,response);
                return;
            }
         
         String lastEdittedBy = ""+session.getAttribute("userName");
         
         ps.setString(1,inputSalesRepFirstName);
         ps.setString(2,inputSalesRepMobileNumber);
         ps.setString(3,inputSalesRepAddress);
         ps.setString(4,inputSalesRepLastName);
         ps.setString(5,lastEdittedBy);
         
         ps.executeUpdate();                   //at this point, you have already inserted into the database
         
         
         message = "Sales Rep successfully added!";
         request.setAttribute("message", message);
         request.getRequestDispatcher("notif.get").forward(request,response);
            
         
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
