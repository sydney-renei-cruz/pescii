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
         //String preparedSQL = "select * from Customer where customerID = ?";
         String preparedSQL = "select Customer.customerID, Customer.PRCID, Customer.customerName, Customer.customerMobileNumber, "
                 + "Customer.customerTelephoneNumber, SalesRep.salesRepName, Customer.salesRepID from Customer "
                 + "inner join SalesRep on SalesRep.salesRepID = Customer.salesRepID "
                 + "where Customer.customerID = ?";
         int inputCustomerID = Integer.parseInt(request.getParameter("custID"));
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setInt(1, inputCustomerID);
         
         customerBean cbean = new customerBean();
         ResultSet dbData = ps.executeQuery();
         while(dbData.next()){
         //customerBean cbean = new customerBean();
         cbean.setCustomerID(dbData.getInt("customerID"));
         cbean.setPRCID(dbData.getString("PRCID"));
         cbean.setCustomerName(dbData.getString("customerName"));
         cbean.setCustomerMobileNumber(dbData.getString("customerMobileNumber"));
         cbean.setCustomerTelephoneNumber(dbData.getString("customerTelephoneNumber"));
         cbean.setSalesRep(dbData.getString("salesRepName"));
         cbean.setSalesRepID(dbData.getInt("salesRepID"));;
         }
         request.setAttribute("customer", cbean);
         
         //now get the clinic/s
         String preparedSQL2 = "select Clinic.clinicID, Customer.PRCID, Clinic.clinicAddress, "
                 + "Clinic.clinicPhoneNumber, Clinic.clinicName from Clinic "
                 + "inner join Customer on Customer.customerID = Clinic.customerID "
                 + "where Clinic.customerID = ?";
         PreparedStatement ps2 = conn.prepareStatement(preparedSQL2);
         ps2.setInt(1,inputCustomerID);
         
         ResultSet dbData2 = ps2.executeQuery();
         ArrayList<clinicBean> clinicsRetrieved = new ArrayList<clinicBean>();
            while(dbData2.next()){
                clinicBean clinbean = new clinicBean();
                clinbean.setClinicID(dbData2.getString("clinicID"));
                clinbean.setPRCID(dbData2.getString("PRCID"));
                clinbean.setClinicAddress(dbData2.getString("clinicAddress"));
                clinbean.setClinicPhoneNumber(dbData2.getString("clinicPhoneNumber"));
                clinbean.setClinicName(dbData2.getString("clinicName"));
                clinicsRetrieved.add(clinbean);
            }
            
         request.setAttribute("clinicsList", clinicsRetrieved);
         context.log("size of clinicsRetrieved is " + clinicsRetrieved.size());
         
         //finally, get the invoices
         String preparedSQL3 = "select Invoice.invoiceID, Customer.PRCID, Invoice.clinicID, Invoice.invoiceDate, "
                 + "Invoice.deliveryDate, Invoice.additionalAccessories, Invoice.termsOfPayment, "
                 + "Invoice.paymentDueDate, Invoice.datePaid, Invoice.dateClosed, Invoice.status, "
                 + "Invoice.overdueFee from Invoice "
                 + "inner join Customer on Customer.customerID = Invoice.customerID "
                 + "where Invoice.customerID = ?";
         PreparedStatement ps3 = conn.prepareStatement(preparedSQL3);
         ps3.setInt(1,inputCustomerID);
         
         //later on, you'll just call the Servlets.viewInvoiceDetailsServlet to perform these
         ResultSet dbData3 = ps3.executeQuery();
         ArrayList<invoiceBean> invoicesRetrieved = new ArrayList<invoiceBean>();
         while(dbData3.next()){
             invoiceBean invBean = new invoiceBean();
             invBean.setInvoiceID(dbData3.getInt("invoiceID"));
             invBean.setPRCID(dbData3.getString("PRCID"));
             invBean.setClinicID(dbData3.getInt("clinicID"));
             invBean.setInvoiceDate(dbData3.getDate("invoiceDate"));
             invBean.setDeliveryDate(dbData3.getDate("deliveryDate"));
             invBean.setAdditionalAccessories(dbData3.getString("additionalAccessories"));
             invBean.setTermsOfPayment(dbData3.getString("termsOfPayment"));
             invBean.setPaymentDueDate(dbData3.getDate("paymentDueDate"));
             if(!(""+dbData3.getDate("dateClosed")).equals("0000-00-00")){invBean.setDateClosed(dbData3.getDate("dateClosed"));}
             if(!(""+dbData3.getDate("datePaid")).equals("0000-00-00")){invBean.setDatePaid(dbData3.getDate("datePaid"));}
                
             //invBean.setDatePaid(dbData3.getDate("datePaid"));
             //invBean.setDateClosed(dbData3.getDate("dateClosed"));
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
             preparedSQL = "select * from SalesRep";
         
                ps = conn.prepareStatement(preparedSQL);

                dbData = ps.executeQuery();
                ArrayList<salesRepBean> salesRepsRetrieved = new ArrayList<salesRepBean>();
                //retrieve the information.
                   while(dbData.next()){
                       salesRepBean data = new salesRepBean();
                       data.setSalesRepName(dbData.getString("salesRepName"));
                       data.setSalesRepID(dbData.getInt("salesRepID"));
                       salesRepsRetrieved.add(data);
                   }
             request.setAttribute("salesRepList", salesRepsRetrieved);
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
