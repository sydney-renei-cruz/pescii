/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.invoiceBean;
import Beans.invoiceItemBean;
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

/**
 *
 * @author user
 */
@WebServlet(name = "viewInvoiceDetailsServlet", urlPatterns = {"/viewInvoiceDetailsServlet"})
public class viewInvoiceDetailsServlet extends HttpServlet {

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
         String preparedSQL = "select Invoice.invoiceID, Invoice.invoiceName, Customer.PRCID, "
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
                 + "where Invoice.invoiceID=?";
         
         String inputInvID = request.getParameter("invID");
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1, inputInvID);
         
         ResultSet dbData = ps.executeQuery();
         dbData.next();
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
         
         request.setAttribute("invoice", invBean);
         
         //now get the InvoiceItem/s
         preparedSQL = "select InvoiceItem.invoiceItemID, InvoiceItem.invoiceID, Product.productID, "
                 + "Product.productName, InvoiceItem.quantityPurchased, Product.productPrice "
                 + "from InvoiceItem "
                 + "inner join Product on Product.productID = InvoiceItem.productID "
                 + "inner join Invoice on Invoice.invoiceID = InvoiceItem.invoiceID "
                 + "where Invoice.invoiceID = ?";
         ps = conn.prepareStatement(preparedSQL);
         ps.setString(1,inputInvID);
         
         dbData = ps.executeQuery();
         ArrayList<invoiceItemBean> invItemsRetrieved = new ArrayList<invoiceItemBean>();
         //retrieve the information.
            while(dbData.next()){
                invoiceItemBean invitembean = new invoiceItemBean();
                invitembean.setInvoiceItemID(dbData.getInt("invoiceItemID"));
                invitembean.setInvoiceID(dbData.getInt("invoiceID"));
                invitembean.setProductID(dbData.getInt("productID"));
                invitembean.setProductName(dbData.getString("productName"));
                invitembean.setQuantityPurchased(dbData.getInt("quantityPurchased"));
                invitembean.setTotalCost(dbData.getInt("quantityPurchased") * dbData.getFloat("productPrice"));
                invItemsRetrieved.add(invitembean);
            }
         request.setAttribute("invitemsList", invItemsRetrieved);
         String editInvoice = "" + request.getParameter("editInvoice");
         try{editInvoice = request.getParameter("editInvoice");}
         catch(Exception e){
             editInvoice = ""+request.getAttribute("editInvoice");
         }
         context.log("-->Editting Invoice?"+editInvoice);
         if(editInvoice.equals("yes")){
            String message = ""+request.getAttribute("message");
            request.setAttribute("editInvoice","yes");
            request.getRequestDispatcher("invoice.getStatus").forward(request, response);
         }
         else{
            request.getRequestDispatcher("invoiceDetails.jsp").forward(request,response);
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
