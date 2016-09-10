/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
@WebServlet(name = "viewCustomerDetailsServlet", urlPatterns = {"/viewCustomerDetailsServlet"})
public class viewCustomerDetailsServlet extends HttpServlet {

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
         //first get the customer details
         String preparedSQL = "select * from Customer where PRCID = ?";
         String inputPRCID = request.getParameter("custID");
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1, inputPRCID);
         
         customerBean cbean = new customerBean();
         ResultSet dbData = ps.executeQuery();
         while(dbData.next()){
         //customerBean cbean = new customerBean();
         cbean.setPRCID(dbData.getString("PRCID"));
         cbean.setCustomerName(dbData.getString("customerName"));
         cbean.setCustomerMobileNumber(dbData.getString("customerMobileNumber"));
         cbean.setCustomerTelephoneNumber(dbData.getString("customerTelephoneNumber"));
         }
         request.setAttribute("customer", cbean);
         
         //now get the clinic/s
         String preparedSQL2 = "select * from Clinic where PRCID = ?";
         PreparedStatement ps2 = conn.prepareStatement(preparedSQL2);
         ps2.setString(1,inputPRCID);
         
         ResultSet dbData2 = ps2.executeQuery();
         ArrayList<clinicBean> clinicsRetrieved = new ArrayList<clinicBean>();
            while(dbData2.next()){
                clinicBean clinbean = new clinicBean();
                clinbean.setClinicID(dbData2.getString("clinicID"));
                clinbean.setClinicAddress(dbData2.getString("clinicAddress"));
                clinbean.setClinicPhoneNumber(dbData2.getString("clinicPhoneNumber"));
                clinbean.setClinicName(dbData2.getString("clinicName"));
                clinicsRetrieved.add(clinbean);
            }
            
         request.setAttribute("clinicsList", clinicsRetrieved);
         context.log("size of clinicsRetrieved is " + clinicsRetrieved.size());
         
         //finally, get the invoices
         String preparedSQL3 = "select * from Invoice where PRCID = ?";
         PreparedStatement ps3 = conn.prepareStatement(preparedSQL3);
         ps3.setString(1,inputPRCID);
         
         //later on, you'll just call the Servlets.viewInvoiceDetailsServlet to perform these
         ResultSet dbData3 = ps3.executeQuery();
         ArrayList<invoiceBean> invoicesRetrieved = new ArrayList<invoiceBean>();
         while(dbData3.next()){
             invoiceBean invBean = new invoiceBean();
             invBean.setInvoiceID(dbData3.getInt("invoiceID"));
             invBean.setPRCID(dbData3.getString("PRCID"));
             invBean.setClinicID(dbData3.getInt("clinicID"));
             invBean.setInvoiceDate(dbData3.getString("invoiceDate"));
             invBean.setDeliveryDate(dbData3.getString("deliveryDate"));
             invBean.setAdditionalAccessories(dbData3.getString("additionalAccessories"));
             invBean.setTermsOfPayment(dbData3.getString("termsOfPayment"));
             invBean.setPaymentDueDate(dbData3.getString("paymentDueDate"));
             invBean.setDatePaid(dbData3.getString("datePaid"));
             invBean.setDateClosed(dbData3.getString("dateClosed"));
             invBean.setStatus(dbData3.getString("status"));
             invBean.setOverdueFee(dbData3.getFloat("overdueFee"));
             invoicesRetrieved.add(invBean);
         }
         
         request.setAttribute("invoicesList", invoicesRetrieved);
         context.log("size of invoicesRetrieved is " + invoicesRetrieved.size());
         
         String forEdit = ""+request.getParameter("forEdit");
         if(session.getAttribute("cart")!=null){
             request.getRequestDispatcher("addInvoice.jsp").forward(request, response);
         }
         else if(forEdit.equals("yes")){
             request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
         }
         else{
            request.getRequestDispatcher("customerDetails.jsp").forward(request,response);
         }
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
