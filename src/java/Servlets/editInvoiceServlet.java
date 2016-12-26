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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "editInvoiceServlet", urlPatterns = {"/editInvoiceServlet"})
public class editInvoiceServlet extends HttpServlet {

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
         //first get the invoice details
         String preparedSQL = "update Invoice set invoiceName=?, deliveryDate=?, termsOfPayment=?, "
                 + "paymentDueDate=?, datePaid=?, dateClosed=?, statusID=?, amountDue=?, "
                 + "discount=?, amountPaid=?, dateDelivered=?, lastEdittedBy=? "
                 + "where invoiceID=?";
         
         int invoiceID = Integer.parseInt(request.getParameter("invoiceIDInput"));
         String newInvoiceName = request.getParameter("newInvoiceNameInput");
         String newDeliveryDate = request.getParameter("deliveryDateInput");
         String newTop = request.getParameter("topInput");
         String newPaymentDueDate = request.getParameter("paymentDueDateInput");
         String newDatePaid = request.getParameter("datePaidInput");
         int newStatus = Integer.parseInt(request.getParameter("statusInput"));
         String newDateClosed = "";
         if(newStatus!=2){
            Date date = new Date();
            newDateClosed = new SimpleDateFormat("yyyy-MM-dd").format(date);
         }
         float newAmountDue = 0 + Float.parseFloat(request.getParameter("amountDueInput"));
         float newDiscount = 0 + Float.parseFloat(request.getParameter("discountInput"));
         float newAmountPaid = 0 + Float.parseFloat(request.getParameter("amountPaidInput"));
         String newDateDelivered = request.getParameter("dateDeliveredInput");
         String lastEdittedBy = ""+session.getAttribute("userName");
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1,newInvoiceName);
         ps.setString(2,newDeliveryDate);
         ps.setString(3,newTop);
         ps.setString(4,newPaymentDueDate);
         //ps.setString(4,newDatePaid);
         if(newDatePaid.equals("") || newStatus==3){ps.setString(5,null);}
         else{ps.setString(5,newDatePaid);}
         if(newDateClosed.equals("")){ps.setString(6,null);}
         else{ps.setString(6,newDateClosed);}
         ps.setFloat(7,newStatus);
         ps.setFloat(8,newAmountDue);
         ps.setFloat(9,newDiscount);
         ps.setFloat(10,newAmountPaid);
         ps.setString(11,newDateDelivered);
         ps.setString(12,lastEdittedBy);
         ps.setInt(13,invoiceID);
         
         
         ps.executeUpdate();
         context.log("--->Invoice successfully updated. InvoiceID is: "+invoiceID);
         
         
         if(newStatus!=2){
            //now update the product
            //first get the invoice items
            preparedSQL = "select * from InvoiceItem where invoiceID = ?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setInt(1,invoiceID);

            ResultSet dbData = ps.executeQuery();
            //you might wanna change this to an array one of these days
            ArrayList<invoiceItemBean> invItemsRetrieved = new ArrayList<invoiceItemBean>();
            //retrieve the information.
               while(dbData.next()){
                  invoiceItemBean invItemBean = new invoiceItemBean();
                  //invItemBean.setInvoiceID(dbData.getInt("invoiceID"));
                  invItemBean.setProductID(dbData.getInt("productID"));
                  //invItemBean.setQuantityPurchased(dbData.getInt("quantityPurchased"));
                  invItemsRetrieved.add(invItemBean);
               }

           //check if it was cancelled or completed
           String operator;
           if(newStatus==3){operator = "+";}
           else{operator = "-";}
               
            for(invoiceItemBean iibean : invItemsRetrieved){
                preparedSQL = "UPDATE Product JOIN InvoiceItem ON Product.productID=InvoiceItem.productID" +
   "               SET Product.stocksRemaining = Product.stocksRemaining "+operator+" InvoiceItem.quantityPurchased" +
   "               WHERE Product.productID=? and InvoiceItem.invoiceID=?;";
                ps = conn.prepareStatement(preparedSQL);
                ps.setInt(1,iibean.getProductID());
                ps.setInt(2,invoiceID);

                ps.executeUpdate();
            }
         }
         
         request.setAttribute("message", "Invoice successfully editted! Inventory updated.");
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
