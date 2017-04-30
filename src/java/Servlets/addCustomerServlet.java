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
         
         String preparedSQL = "insert into Customer(PRCID, customerFirstName, customerMobileNumber, "
                 + "customerTelephoneNumber, salesRepID, customerLastName, lastEdittedBy) "
                 + "values(?,?,?,?,?,?,?)";
         String preparedSQL2 = "insert into Clinic(customerID, clinicAddress, clinicPhoneNumber, "
                 + "clinicName, provinceID, lastEdittedBy) "
                 + "values(?,?,?,?,?,?)";
         
         //you don't change this
         PreparedStatement ps = conn.prepareStatement(preparedSQL, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement ps2 = conn.prepareStatement(preparedSQL2);
         
         String inputPRCID="";
         String inputCustomerFirstName="";
         String inputCustomerCelNum="";
         String inputTelNum="";
         int inputSalesRepID=0;
         String inputCustomerLastName="";
         String lastEdittedBy = ""+session.getAttribute("userName");
         
         
         //check the PRCID
         try{
             inputPRCID = request.getParameter("customerIDInput");
             if(inputPRCID.length()>50){
                 message = "PRCID is too long.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
             }
         }
         catch(Exception e){
            message = "PRCID was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
            return;
         }
         
         //check the CustomerFirstName
         try{
             inputCustomerFirstName = request.getParameter("customerFirstNameInput");
             if(inputCustomerFirstName.length()>100){
                 message = "Customer First Name is too long.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
             }
         }
         catch(Exception e){
                 message = "Customer First Name was input incorrectly. It should also not be blank.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
         }
         
         //check CustomerCelNum
         try{
             inputCustomerCelNum = request.getParameter("customerMobileNumberInput");
             if(inputCustomerCelNum.length()>20){
                 message = "Customer Mobile Number is too long.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
             }
             char c;
             for(int i=0;i<inputCustomerCelNum.length();i++){
                c = inputCustomerCelNum.charAt(i);
                if(!Character.isDigit(c)){
                    message = "Customer Mobile Number should not include letters. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                    return;
                }
            }
         }
         catch(Exception e){
                 message = "Customer Mobile Number was input incorrectly.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
         }
         
         //check telNum
         try{
             inputTelNum = request.getParameter("customerTelephoneNumberInput");
             if(inputTelNum.length()>15){
                 message = "Customer Telephone Number is too long.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
             }
             char c;
             for(int i=0;i<inputTelNum.length();i++){
                c = inputTelNum.charAt(i);
                if(!Character.isDigit(c)){
                    message = "Customer Telephone Number was input incorrectly. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                    return;
                }
            }
         }
         catch(Exception e){
                 message = "Customer Telephone Number was input incorrectly.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
         }
         
         //check SalesRepIDinputSalesRepID = Integer.parseInt(request.getParameter("chosenSalesRep"));
         try{
             inputSalesRepID = Integer.parseInt(request.getParameter("chosenSalesRep"));
         }
         catch(Exception e){
                 message = "SalesRep was input incorrectly.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
         }
         
         //check CustomerLastName
         try{
             inputCustomerLastName = request.getParameter("customerLastNameInput");
             if(inputCustomerLastName.length()>100){
                 message = "Customer Last Name is too long.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
             }
         }
         catch(Exception e){
                 message = "Customer Last Name was input incorrectly. It should also not be blank.";
                 request.setAttribute("message",message);
                 request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                 return;
         }
         
         ps.setString(1,inputPRCID);
         ps.setString(2,inputCustomerFirstName);
         ps.setString(3,inputCustomerCelNum);
         ps.setString(4,inputTelNum);
         ps.setInt(5,inputSalesRepID);
         ps.setString(6,inputCustomerLastName);
         ps.setString(7,lastEdittedBy);
         ps.executeUpdate();                   //at this point, you have already inserted into the database
         
         String inputClinicAddress="";
         String inputClinPhoneNum="";
         String inputClinicName="";
         String provinceID="";
         
         //check clinicAddress
         try{inputClinicAddress = request.getParameter("clinicAddressInput");}
         catch(Exception e){
             e.printStackTrace();
             message = "Clinic address was input incorrectly. It should also not be blank.";
             request.setAttribute("message",message);
             request.setAttribute("message",message);
             request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
             return;
         }
         
         //check clinicPhoneNum
         try{inputClinPhoneNum = request.getParameter("clinicPhoneNumInput");
            char c;
            for(int i=0;i<inputClinPhoneNum.length();i++){
                c = inputClinPhoneNum.charAt(i);
                if(Character.isLetter(c)){
                    message = "Clinic Phone Number was input incorrectly. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
                    return;
                }
            }
         }
         catch(Exception e){
             e.printStackTrace();
             message = "Clinic Phone Number was input incorrectly. It should also not be blank.";
             request.setAttribute("message",message);
             request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
             return;
         }
         
         //check clinicName
         try{inputClinicName = request.getParameter("clinicNameInput");}
         catch(Exception e){
             message = "Clinic Name was input incorrectly. It should also not be blank.";
             request.setAttribute("message",message);
             request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
             return;
         }
         
         //check provinceID
         try{provinceID = request.getParameter("chosenProvince");}
         catch(Exception e){
             e.printStackTrace();
             request.setAttribute("message",message);
             request.getRequestDispatcher("salesrep.get?whatFor=addCustomer").forward(request,response);
             return;
         }
         
         
         int customerID;
         try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                customerID = generatedKeys.getInt(1);
                request.setAttribute("custID",customerID);
                ps2.setInt(1, customerID);
                ps2.setString(2,inputClinicAddress);
                ps2.setString(3,inputClinPhoneNum);
                ps2.setString(4,inputClinicName);
                ps2.setString(5,provinceID);
                ps2.setString(6,lastEdittedBy);
                ps2.executeUpdate();
                
            }
            else {
                throw new SQLException("--> no customerID retrieved");
            }
        }
         
         message = "Customer successfully created!";
         request.setAttribute("message", message);
         //request.getRequestDispatcher("notif.get").forward(request,response);
         request.getRequestDispatcher("anotherCustomerClinic.jsp").forward(request,response);
            
         
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
