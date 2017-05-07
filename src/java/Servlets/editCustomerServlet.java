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
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "editCustomerServlet_1", urlPatterns = {"/editCustomerServlet_1"})
public class editCustomerServlet extends HttpServlet {

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
         //first get the invoice details
         String preparedSQL = "update Customer set customerLastName=?, customerFirstName=?, customerMobileNumber=?, customerTelephoneNumber=?, salesRepID=? where customerID=?";
         String message = "";
         boolean customerLastName=false;
         boolean customerFirstName=false;
         boolean customerMobileNumber;
         boolean customerTelephoneNumber=false;
         
         //check customer last name
         String newCustomerLastName = "";
         try{
             newCustomerLastName = request.getParameter("customerLastNameInput");
             if(newCustomerLastName.length()>100){
                 message = "Customer Last Name is too long.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                 return;
             }
             else if(!newCustomerLastName.equals("") && newCustomerLastName!=null){
                 customerLastName=true;
             }
             else{
                message = "Customer Last Name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                return;
             }
         }
         catch(Exception e){
                 message = "Customer Last Name was input incorrectly. It should also not be blank.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                 return;
         }
         
         //check first name
         String newCustomerFirstName = "";
         try{
             newCustomerFirstName = request.getParameter("customerFirstNameInput");
             if(newCustomerFirstName.length()>100){
                 message = "Customer First Name is too long.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                 return;
             }
             else if(!newCustomerFirstName.equals("") && newCustomerFirstName!=null){
                 customerFirstName=true;
             }
             else{
                message = "Customer First Name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                return;
             }
         }
         catch(Exception e){
                 message = "Customer First Name was input incorrectly. It should also not be blank.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                 return;
         }
         
         //check mobile number
         String newCustomerMobileNumber = "";
         try{
             newCustomerMobileNumber = request.getParameter("customerMobileNumberInput");
             char c;
             for(int i=0;i<newCustomerMobileNumber.length();i++){
                c = newCustomerMobileNumber.charAt(i);
                if(!Character.isDigit(c)){
                    message = "Customer Mobile Number should not include letters. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                    return;
                }
            }
             if(newCustomerMobileNumber.length()>20){
                 message = "Customer Mobile Number is too long.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                 return;
             }
             else{
                 customerMobileNumber=true;
             }
         }
         catch(Exception e){
                 message = "Customer Mobile Number was input incorrectly.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                 return;
         }
         
         //check tel number
         String newCustomerTelephoneNumber = "";
         try{
             newCustomerTelephoneNumber = request.getParameter("customerTelNumInput");
             char c;
             for(int i=0;i<newCustomerTelephoneNumber.length();i++){
                c = newCustomerTelephoneNumber.charAt(i);
                if(!Character.isDigit(c)){
                    message = "Customer Telephone Number was input incorrectly. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                    return;
                }
            }
            if(newCustomerTelephoneNumber.length()>15){
                message = "Customer Telephone Number is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                return;
            }
            else{
                customerTelephoneNumber=true;
            }
             
         }
         catch(Exception e){
                 message = "Customer Telephone Number was input incorrectly.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
                 return;
         }
         
         
         int inputCustomerID = Integer.parseInt(request.getParameter("customerIDInput"));
         
         
         //check salesrepID
         int newSalesRepID = 0;
         try{
             newSalesRepID = Integer.parseInt(request.getParameter("chosenSalesRep"));
         }
         catch(Exception e){
            message = "SalesRep was input incorrectly.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("Servlets.viewCustomerDetailsServlet?forEdit=yes&custID="+request.getParameter("customerIDInput")).forward(request,response);
            return;
         }
         preparedSQL = "update Customer set customerLastName=?, customerFirstName=?, customerMobileNumber=?, customerTelephoneNumber=?, salesRepID=? where customerID=?";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         if(customerLastName==true){
             preparedSQL = "update Customer set customerLastName=? where customerID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newCustomerLastName);
             ps.setInt(2,inputCustomerID);
             ps.executeUpdate();
             context.log("updated the customer last name!");
         }
         if(customerFirstName==true){
             preparedSQL = "update Customer set customerFirstName=? where customerID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newCustomerFirstName);
             ps.setInt(2,inputCustomerID);
             ps.executeUpdate();
             context.log("updated the customer first name!");
         }
         if(customerMobileNumber==true){
             preparedSQL = "update Customer set customerMobileNumber=? where customerID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newCustomerMobileNumber);
             ps.setInt(2,inputCustomerID);
             ps.executeUpdate();
             context.log("updated the customer customerMobileNumber!");
         }
         if(customerTelephoneNumber==true){
             preparedSQL = "update Customer set customerTelephoneNumber=? where customerID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newCustomerTelephoneNumber);
             ps.setInt(2,inputCustomerID);
             ps.executeUpdate();
             context.log("updated the customer customerTelephoneNumber!");
         }
        preparedSQL = "update Customer set salesRepID=? where customerID=?";
        ps = conn.prepareStatement(preparedSQL);
        ps.setInt(1,newSalesRepID);
        ps.setInt(2,inputCustomerID);
        ps.executeUpdate();
        context.log("updated the customer's sales rep!");
         
         context.log("--->Customer successfully updated. CustomerID is: "+inputCustomerID);
         
         request.setAttribute("custID",inputCustomerID);
         request.setAttribute("message", "Customer successfully editted!");
         request.getRequestDispatcher("anotherCustomerClinic.jsp").forward(request,response);
         
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
