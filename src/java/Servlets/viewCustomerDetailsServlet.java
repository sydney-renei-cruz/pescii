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
         String preparedSQL = "select Customer.customerID, Customer.PRCID, "
                 + "Customer.customerFirstName, Customer.customerLastName, "
                 + "Customer.customerMobileNumber, "
                 + "Customer.customerTelephoneNumber, SalesRep.salesRepFirstName, "
                 + "SalesRep.salesRepLastName, Customer.salesRepID, Customer.dateCreated, "
                 + "Customer.lastEdittedBy "
                 + "from Customer "
                 + "inner join SalesRep on SalesRep.salesRepID = Customer.salesRepID "
                 + "where Customer.customerID=? "
                 + "order by Customer.customerLastName asc ";
         int inputCustomerID = 0;
         
         if(request.getParameter("custID") == null){
             if(request.getAttribute("custID") != null){
                inputCustomerID = 0 + Integer.parseInt(""+request.getAttribute("custID"));
             }
         }
         else{
            inputCustomerID = 0 + Integer.parseInt(request.getParameter("custID"));
         }
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setInt(1, inputCustomerID);
         
         customerBean cbean = new customerBean();
         ResultSet dbData = ps.executeQuery();
         while(dbData.next()){
         //customerBean cbean = new customerBean();
         cbean.setCustomerID(dbData.getInt("customerID"));
         cbean.setPRCID(dbData.getString("PRCID"));
         cbean.setCustomerFirstName(dbData.getString("customerFirstName"));
         cbean.setCustomerLastName(dbData.getString("customerLastName"));
         cbean.setCustomerMobileNumber(dbData.getString("customerMobileNumber"));
         cbean.setCustomerTelephoneNumber(dbData.getString("customerTelephoneNumber"));
         cbean.setSalesRep(dbData.getString("salesRepLastName")+", "+dbData.getString("salesRepFirstName"));
         cbean.setSalesRepID(dbData.getInt("salesRepID"));
         cbean.setDateCreated(dbData.getTimestamp("dateCreated"));
         cbean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
         }
         request.setAttribute("customer", cbean);
         
         //now get the clinic/s
         preparedSQL = "select Clinic.clinicID, Customer.PRCID, Clinic.clinicAddress, "
                 + "Clinic.clinicPhoneNumber, Clinic.clinicName, Clinic.provinceID, "
                 + "Province.provinceName, Province.provinceDivision, "
                 + "Clinic.dateCreated, Clinic.lastEdittedBy "
                 + "from Clinic "
                 + "inner join Customer on Customer.customerID = Clinic.customerID "
                 + "inner join Province on Province.provinceID = Clinic.provinceID "
                 + "where Clinic.customerID = ?";
         ps = conn.prepareStatement(preparedSQL);
         ps.setInt(1,inputCustomerID);
         
         dbData = ps.executeQuery();
         ArrayList<clinicBean> clinicsRetrieved = new ArrayList<clinicBean>();
            while(dbData.next()){
                clinicBean clinbean = new clinicBean();
                clinbean.setClinicID(dbData.getString("clinicID"));
                clinbean.setPRCID(dbData.getString("PRCID"));
                clinbean.setClinicAddress(dbData.getString("clinicAddress"));
                clinbean.setClinicPhoneNumber(dbData.getString("clinicPhoneNumber"));
                clinbean.setClinicName(dbData.getString("clinicName"));
                clinbean.setProvinceID(dbData.getInt("provinceID"));
                clinbean.setProvinceName(dbData.getString("provinceName"));
                clinbean.setProvinceDivision(dbData.getString("provinceDivision"));
                clinbean.setDateCreated(dbData.getTimestamp("dateCreated"));
                clinbean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
                clinicsRetrieved.add(clinbean);
            }
            
         request.setAttribute("clinicsList", clinicsRetrieved);
         context.log("size of clinicsRetrieved is " + clinicsRetrieved.size());
         
         //finally, get the invoices
         preparedSQL = "select Invoice.invoiceID, Invoice.invoiceName, Customer.PRCID, "
                 + "Customer.customerFirstName, Customer.customerLastName, "
                 + "Invoice.clinicID, Clinic.clinicName, Clinic.provinceID, "
                 + "Province.provinceID, Province.provinceName, Province.provinceDivision, "
                 + "Invoice.invoiceDate, Invoice.deliveryDate, "
                 + "Invoice.termsOfPayment, Invoice.paymentDueDate, Invoice.datePaid, "
                 + "Invoice.dateClosed, Invoice.statusID, InvoiceStatus.statusName, "
                 + "Invoice.overdueFee, Invoice.amountDue, Invoice.amountPaid, Invoice.discount, "
                 + "Invoice.dateDelivered, Invoice.dateCreated, Invoice.lastEdittedBy "
                 + "from Invoice "
                 + "inner join Customer on Customer.customerID = Invoice.customerID "
                 + "inner join Clinic on Clinic.clinicID = Invoice.clinicID "
                 + "inner join Province on Province.provinceID = Clinic.provinceID "
                 + "inner join InvoiceStatus on InvoiceStatus.statusID = Invoice.statusID "
                 + "where Invoice.customerID = ?";
         context.log(preparedSQL);
         ps = conn.prepareStatement(preparedSQL);
         ps.setInt(1,inputCustomerID);
         
         //later on, you'll just call the Servlets.viewInvoiceDetailsServlet to perform these
         dbData = ps.executeQuery();
         ArrayList<invoiceBean> invoicesRetrieved = new ArrayList<invoiceBean>();
         while(dbData.next()){
             invoiceBean invBean = new invoiceBean();
             invBean.setInvoiceID(dbData.getInt("invoiceID"));
             invBean.setInvoiceName(dbData.getString("invoiceName"));
             invBean.setPRCID(dbData.getString("PRCID"));
             invBean.setCustomerName(dbData.getString("customerLastName")+", "+dbData.getString("customerFirstName"));
             invBean.setClinicID(dbData.getInt("clinicID"));
             invBean.setClinicName(dbData.getString("clinicName"));
             invBean.setProvinceName(dbData.getString("provinceName"));
             invBean.setProvinceDivision(dbData.getString("provinceDivision"));
             invBean.setInvoiceDate(dbData.getDate("invoiceDate"));
             invBean.setDeliveryDate(dbData.getDate("deliveryDate"));
             invBean.setTermsOfPayment(dbData.getString("termsOfPayment"));
             invBean.setPaymentDueDate(dbData.getDate("paymentDueDate"));
             if(!(""+dbData.getDate("dateClosed")).equals("0000-00-00")){invBean.setDateClosed(dbData.getDate("dateClosed"));}
             if(!(""+dbData.getDate("datePaid")).equals("0000-00-00")){invBean.setDatePaid(dbData.getDate("datePaid"));}
             invBean.setStatusID(dbData.getInt("statusID"));
             invBean.setStatusName(dbData.getString("statusName"));
             invBean.setOverdueFee(dbData.getFloat("overdueFee"));
             invBean.setAmountDue(dbData.getFloat("amountDue"));
             invBean.setAmountPaid(dbData.getFloat("amountPaid"));
             invBean.setDiscount(dbData.getFloat("discount"));
             invBean.setDateDelivered(dbData.getDate("dateDelivered"));
             invBean.setDateCreated(dbData.getTimestamp("dateCreated"));
             invBean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
             invoicesRetrieved.add(invBean);
         }
         
         request.setAttribute("invoicesList", invoicesRetrieved);
         context.log("size of invoicesRetrieved is " + invoicesRetrieved.size());
         
         String forEdit = "";
         try{forEdit = request.getParameter("forEdit");
            if(forEdit==null){
                forEdit = "" + request.getAttribute("forEdit");
            }
         }
         catch(Exception e){forEdit = "" + request.getAttribute("forEdit");}
         context.log("for Edit is: "+forEdit);
         if(session.getAttribute("cart")!=null){
             request.setAttribute("message",request.getAttribute("message"));
             request.getRequestDispatcher("invoice.getStatus").forward(request, response);
         }
         else if(forEdit.equals("yes")){
             preparedSQL = "select * from SalesRep";
         
                ps = conn.prepareStatement(preparedSQL);

                dbData = ps.executeQuery();
                ArrayList<salesRepBean> salesRepsRetrieved = new ArrayList<salesRepBean>();
                //retrieve the information.
                   while(dbData.next()){
                       salesRepBean data = new salesRepBean();
                       data.setSalesRepFirstName(dbData.getString("salesRepFirstName"));
                       data.setSalesRepLastName(dbData.getString("salesRepLastName"));
                       data.setSalesRepID(dbData.getInt("salesRepID"));
                       salesRepsRetrieved.add(data);
                   }
             String message = "";
             try{message = request.getParameter("message");}
             catch(Exception e){
                 message = ""+request.getAttribute("message");
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
