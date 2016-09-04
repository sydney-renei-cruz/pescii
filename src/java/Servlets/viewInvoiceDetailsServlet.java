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
         String preparedSQL = "select * from Invoice where invoiceID = ?";
         String inputInvID = request.getParameter("invID");
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1, inputInvID);
         
         ResultSet dbData = ps.executeQuery();
         dbData.next();
         invoiceBean ibean = new invoiceBean();
         ibean.setInvoiceID(dbData.getInt("invoiceID"));
         ibean.setPRCID(dbData.getString("PRCID"));
         ibean.setClinicID(dbData.getInt("clinicID"));
         ibean.setInvoiceDate(dbData.getString("invoiceDate"));
         ibean.setDeliveryDate(dbData.getString("deliveryDate"));
         ibean.setAdditionalAccessories(dbData.getString("additionalAccessories"));
         ibean.setTermsOfPayment(dbData.getString("termsOfPayment"));
         ibean.setPaymentDueDate(dbData.getString("paymentDueDate"));
         ibean.setDatePaid(dbData.getString("datePaid"));
         ibean.setDateClosed(dbData.getString("dateClosed"));
         ibean.setStatus(dbData.getString("status"));
         ibean.setOverdueFee(dbData.getFloat("overdueFee"));
         
         request.setAttribute("invoice", ibean);
         
         //now get the InvoiceItem/s
         String preparedSQL2 = "select * from InvoiceItem where invoiceID = ?";
         PreparedStatement ps2 = conn.prepareStatement(preparedSQL2);
         ps2.setString(1,inputInvID);
         
         ResultSet dbData2 = ps2.executeQuery();
         ArrayList<invoiceItemBean> invItemsRetrieved = new ArrayList<invoiceItemBean>();
         //retrieve the information.
            while(dbData2.next()){
                invoiceItemBean invitembean = new invoiceItemBean();
                invitembean.setInvoiceItemID(dbData2.getInt("invoiceItemID"));
                invitembean.setInvoiceID(dbData2.getInt("invoiceID"));
                invitembean.setProductID(dbData2.getInt("productID"));
                invitembean.setQuantityPurchased(dbData2.getInt("quantityPurchased"));
                invItemsRetrieved.add(invitembean);
            }
         request.setAttribute("invitemsList", invItemsRetrieved);
         String editInvoice = "" + request.getParameter("editInvoice");
         context.log("-->Editting Invoice?"+editInvoice);
         if(editInvoice.equals("yes")){
            request.getRequestDispatcher("editInvoice.jsp").forward(request, response);
         }
         else{
            request.getRequestDispatcher("invoiceDetails.jsp").forward(request,response);
         }   
         
        }
        catch(SQLException ex){
            ex.printStackTrace();
            out.println("SQL error: " + ex);
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
