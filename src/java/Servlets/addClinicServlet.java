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
@WebServlet(name = "addClinicServlet", urlPatterns = {"/addClinicServlet"})
public class addClinicServlet extends HttpServlet {

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
        String message="";
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
         //THIS IS WHERE YOU START CHANGING
         
         String preparedSQL = "insert into Clinic(customerID, clinicAddress, clinicPhoneNumber, "
                 + "clinicName, provinceID, lastEdittedBy) "
                 + "values(?,?,?,?,?,?)";
         
         //you don't change this
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         int inputCustomerID=0;
         String inputClinicAddress="";
         String inputClinPhoneNum="";
         String inputClinicName="";
         String provinceID="";
         String lastEdittedBy=""+session.getAttribute("userName");
         int success = 0;
         try{
            context.log("CUSTOMER ID ISSSSSSS: "+request.getParameter("customerIDInput"));
            inputCustomerID = Integer.parseInt(request.getParameter("customerIDInput"));
         }
         catch(Exception e){
             message = "No customer selected. Please try again.";
             request.setAttribute("message",message);
             request.setAttribute("whatFor","addClinic");
             request.setAttribute("custID", request.getParameter("customerIDInput"));
             request.getRequestDispatcher("province.get").forward(request,response);
             return;
         }
         
         //check clinic address
         try{inputClinicAddress = request.getParameter("clinicAddressInput");}
         catch(Exception e){
             message = "Clinic address was input incorrectly. It should also not be blank.";
             request.setAttribute("message",message);
             request.setAttribute("whatFor","addClinic");
             request.setAttribute("custID", request.getParameter("customerIDInput"));
             request.getRequestDispatcher("province.get").forward(request,response);
             return;
         }
         
         //check clinic phone number
         try{inputClinPhoneNum = request.getParameter("clinicPhoneNumberInput");
            char c;
            for(int i=0;i<inputClinPhoneNum.length();i++){
                c = inputClinPhoneNum.charAt(i);
                if(!Character.isDigit(c)){
                    message = "Clinic Phone Number was input incorrectly. It should also not be blank.";
                    request.setAttribute("whatFor","addClinic");
                    request.setAttribute("custID", request.getParameter("customerIDInput"));
                    session.setAttribute("error_msg", message);
                    response.sendRedirect(request.getHeader("referer"));
                    return;
                }
            }
         }
         catch(Exception e){
             message = "Clinic Phone Number was input incorrectly. It should also not be blank.";
             request.setAttribute("whatFor","addClinic");
             request.setAttribute("custID", request.getParameter("customerIDInput"));
             session.setAttribute("error_msg", message);
            response.sendRedirect(request.getHeader("referer"));
            return;
         }
         
         //check clinic name
         try{inputClinicName = request.getParameter("clinicNameInput");}
         catch(Exception e){
             message = "Clinic Name was input incorrectly. It should also not be blank.";
             request.setAttribute("whatFor","addClinic");
             request.setAttribute("custID", request.getParameter("customerIDInput"));
             session.setAttribute("error_msg", message);
            response.sendRedirect(request.getHeader("referer"));
            return;
         }
         
         //check province
         try{provinceID = request.getParameter("chosenProvince");}
         catch(Exception e){
             message = "Province was input incorrectly. Please try again.";
             request.setAttribute("whatFor","addClinic");
             request.setAttribute("custID", request.getParameter("customerIDInput"));
             session.setAttribute("error_msg", message);
            response.sendRedirect(request.getHeader("referer"));
            return;
         }
         ps.setInt(1,inputCustomerID);
         ps.setString(2,inputClinicAddress);
         ps.setString(3,inputClinPhoneNum);
         ps.setString(4,inputClinicName);
         ps.setString(5,provinceID);
         ps.setString(6,lastEdittedBy);
         success = ps.executeUpdate();
         
         if(success > 0){
             message = "Clinic successfully added!";
            request.setAttribute("message", message);
            request.setAttribute("custID",inputCustomerID);
            session.setAttribute("success_msg", message);
            response.sendRedirect(request.getHeader("referer"));
         }else{
             message = "Something went wrong. Try again.";
             session.setAttribute("error_msg", message);
            response.sendRedirect(request.getHeader("referer"));
         }
         
            
         
        }
        catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            message = "Something went wrong. Please try again or contact the administrator.";
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
                message = "Something went wrong. Please try again or contact the administrator.";
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
