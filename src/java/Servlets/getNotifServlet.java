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
@WebServlet(name = "getNotifServlet", urlPatterns = {"/getNotifServlet"})
public class getNotifServlet extends HttpServlet {

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
        
        context.log("the parameter forWhat is: "+request.getParameter("forWhat"));
        
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
         String preparedSQL;
         PreparedStatement ps;
         String forWhat = "" + request.getParameter("forWhat");
         ResultSet dbData;
         int accountType = 0;
         if(session.getAttribute("accountType") != null){
            accountType = Integer.parseInt(""+session.getAttribute("accountType"));
         }
         if(accountType==3){
            forWhat = "invoice";
         }
         else if(accountType==4 || accountType==5){
             forWhat = "restock";
         }
         else if(accountType==1){
             forWhat = "both";
         }
                 
         if(forWhat.equals("invoice") || forWhat.equals("both")){
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
                    + "where ((Invoice.paymentDueDate between now() and date_add(now(), interval 7 day) "
                           + "and Invoice.datePaid = null) "
                       + "or Invoice.deliveryDate between now() and date_add(now(), interval 7 day))"
                    + "and InvoiceStatus.statusName='In Progress' "
                    + "order by Invoice.paymentDueDate asc";


            ps = conn.prepareStatement(preparedSQL);
            context.log(preparedSQL);
            
            dbData = ps.executeQuery();
            ArrayList<invoiceBean> invoicesRetrieved = new ArrayList<invoiceBean>();
            //retrieve the information.
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
            request.setAttribute("invoiceList", invoicesRetrieved);
         }
         
         
         //get notifs for RestockOrders
         if(forWhat.equals("restock") || forWhat.equals("both")){/*
            preparedSQL = "select RestockOrder.restockOrderID, Product.productID, RestockOrder.productID, "
                    + "RestockOrder.ROName, RestockOrder.numberOfPiecesOrdered, Product.restockPrice, "
                    + "RestockOrder.numberOfPiecesReceived, Product.supplierID, RestockOrder.purpose, "
                    + "RestockOrder.RODateDue, RestockOrder.RODateDelivered, RestockOrder.amountPaid, "
                    + "RestockOrder.discount, RestockOrder.dateCreated, RestockOrder.lastEdittedBy, "
                    + "RestockOrder.datePaid, Product.productClassID, ProductClass.productClassID, "
                    + "ProductClass.productClassName, Supplier.supplierID, Supplier.supplierName, "
                    + "Product.productName "
                    + "from RestockOrder "
                    + "inner join Product on Product.productID = RestockOrder.productID "
                    + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                    + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                    + "where "
                       + "RestockOrder.RODateDue between now() and date_add(now(), interval 7 day) "
                       + "and RestockOrder.RODateDelivered = null "
                    + "order by RestockOrder.RODateDue asc";

            ps = conn.prepareStatement(preparedSQL);
            context.log(preparedSQL);

            dbData = ps.executeQuery();
            ArrayList<restockOrderBean> restocksRetrieved = new ArrayList<restockOrderBean>();
            //retrieve the information.
               while(dbData.next()){
                  restockOrderBean rbean = new restockOrderBean();
                   rbean.setRestockOrderID(dbData.getInt("restockOrderID"));
                   rbean.setRestockOrderName(dbData.getString("ROName"));
                   //rbean.setProductID(dbData.getInt("productID"));
                   rbean.setProductName(dbData.getString("productName"));
                   //rbean.setNumberOfPiecesOrdered(dbData.getInt("numberOfPiecesOrdered"));
                   //rbean.setNumberOfPiecesReceived(dbData.getInt("numberOfPiecesReceived"));
                   //rbean.setSupplierID(dbData.getInt("supplierID"));
                   //rbean.setSupplierName(dbData.getString("supplierName"));
                   rbean.setPurpose(dbData.getString("purpose"));
                   rbean.setRODateDue(dbData.getDate("RODateDue"));
                   rbean.setRODateDelivered(dbData.getDate("RODateDelivered"));
                   rbean.setRestockPrice(dbData.getFloat("restockPrice"));
                   rbean.setAmountPaid(dbData.getFloat("amountPaid"));
                   rbean.setDiscount(dbData.getFloat("discount"));
                   rbean.setDatePaid(dbData.getDate("datePaid"));
                   rbean.setDateCreated(dbData.getTimestamp("dateCreated"));
                   rbean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
                   restocksRetrieved.add(rbean);
               }
            request.setAttribute("restocksList", restocksRetrieved);
         */
         preparedSQL = "select RestockOrder.*, RestockOrderStatus.statusName from "
                 + "RestockOrder "
                 + "inner join RestockOrderStatus on RestockOrderStatus.statusID=RestockOrder.statusID "
                 + "where RestockOrderStatus.statusName='In Progress' and RestockOrder.RODateDue between now() and date_add(now(), interval 7 day) "
                 + "order by RestockOrder.RODateDue asc";
         
         
         ps = conn.prepareStatement(preparedSQL);
         context.log(preparedSQL);
         
         
         dbData = ps.executeQuery();
         ArrayList<restockOrderBean> restocksRetrieved = new ArrayList<restockOrderBean>();
         //retrieve the information.
            while(dbData.next()){
               restockOrderBean rbean = new restockOrderBean();
                rbean.setRestockOrderID(dbData.getInt("restockOrderID"));
                rbean.setRestockOrderName(dbData.getString("ROName"));
                rbean.setStatusID(dbData.getInt("statusID"));
                rbean.setStatusName(dbData.getString("statusName"));
                rbean.setPurpose(dbData.getString("purpose"));
                rbean.setRODateDue(dbData.getDate("RODateDue"));
                rbean.setRODateDelivered(dbData.getDate("RODateDelivered"));
                //rbean.setRestockPrice(dbData.getFloat("restockPrice"));
                rbean.setAmountPaid(dbData.getFloat("amountPaid"));
                rbean.setDiscount(dbData.getFloat("discount"));
                rbean.setDatePaid(dbData.getDate("datePaid"));
                rbean.setDateCreated(dbData.getTimestamp("dateCreated"));
                rbean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
                restocksRetrieved.add(rbean);
            }
         request.setAttribute("restocksList", restocksRetrieved);
         }
         
         request.getRequestDispatcher("homePage.jsp").forward(request,response);
            
         
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
