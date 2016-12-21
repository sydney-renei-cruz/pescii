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

/**
 *
 * @author user
 */
@WebServlet(name = "addCustomerServlet", urlPatterns = {"/addCustomerServlet"})
public class addCustomerServlet extends HttpServlet {

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
         
         String preparedSQL = "insert into Customer(PRCID, customerFirstName, customerMobileNumber, customerTelephoneNumber, salesRepID, customerLastName) values(?,?,?,?,?,?)";
         String preparedSQL2 = "insert into Clinic(customerID, clinicAddress, clinicPhoneNumber, clinicName, provinceID) values(?,?,?,?,?)";
         
         //you don't change this
         PreparedStatement ps = conn.prepareStatement(preparedSQL, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement ps2 = conn.prepareStatement(preparedSQL2);
         
         String inputPRCID = request.getParameter("customerIDInput");
         String inputCustomerFirstName = request.getParameter("customerFirstNameInput");
         String inputCustomerCelNum = request.getParameter("customerMobileNumberInput");
         String inputTelNum = request.getParameter("customerTelephoneNumberInput");
         int inputSalesRepID = Integer.parseInt(request.getParameter("chosenSalesRep"));
         String inputCustomerLastName = request.getParameter("customerLastNameInput");
         
         ps.setString(1,inputPRCID);
         ps.setString(2,inputCustomerFirstName);
         ps.setString(3,inputCustomerCelNum);
         ps.setString(4,inputTelNum);
         ps.setInt(5,inputSalesRepID);
         ps.setString(6,inputCustomerLastName);
         ps.executeUpdate();                   //at this point, you have already inserted into the database
         
         String inputClinicAddress = request.getParameter("clinicAddressInput");
         String inputClinPhoneNum = request.getParameter("clinicPhoneNumInput");
         String inputClinicName = request.getParameter("clinicNameInput");
         String inputProvinceID = request.getParameter("chosenProvince");
         int customerID;
         try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                customerID = generatedKeys.getInt(1);

                ps2.setInt(1, customerID);
                ps2.setString(2, inputClinicAddress);
                ps2.setString(3, inputClinPhoneNum);
                ps2.setString(4, inputClinicName);
                ps.setString(5, inputProvinceID);
                ps2.executeUpdate();
                
            }
            else {
                throw new SQLException("--> no customerID retrieved");
            }
        }
         
         String message = "Customer successfully created!";
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
