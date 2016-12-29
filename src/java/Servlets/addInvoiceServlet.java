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
import java.util.LinkedList;
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
@WebServlet(name = "addInvoiceServlet_1", urlPatterns = {"/addInvoiceServlet_1"})
public class addInvoiceServlet extends HttpServlet {

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
         //THIS IS WHERE YOU START CHANGING
         String toCancel = ""+request.getParameter("cancel");
         context.log("toCancel = "+toCancel);
         String message = "";
         if(toCancel.equals("yes")){    //this is for when the invoice is cancelled. It was put here to save space...I guess.
                message = "Invoice cancelled.";
                request.setAttribute("message", message);
                session.setAttribute("cart", null);
                session.setAttribute("prodNames", null);
                session.setAttribute("quantity", null);
                request.getRequestDispatcher("homePage.jsp").forward(request,response);
                return;
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
        
        HttpSession session = request.getSession();
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
         String toCancel = ""+request.getParameter("cancel");
         context.log("toCancel = "+toCancel);
         String message = "Invoice successfully created!";
         if(toCancel.equals("yes")){    //this is for when the invoice is cancelled. It was put here to save space...I guess.
                message = "Invoice cancelled.";
                request.setAttribute("message", message);
                session.setAttribute("cart", null);
                session.setAttribute("prodNames", null);
                session.setAttribute("quantity", null);
                request.getRequestDispatcher("homePage.jsp").forward(request,response);
                return;
         }
         else{  //if not cancelled, then do what this servlet was made for.
            String preparedSQL = "insert into Invoice(customerID, clinicID, invoiceDate, "
                    + "deliveryDate, termsOfPayment, paymentDueDate, datePaid, dateClosed, "
                    + "statusID, overdueFee, invoiceName, amountDue, discount, amountPaid, lastEdittedBy) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            //this is put at the start because it needs to cancel immediately if there are no InvoiceItems
            LinkedList<String> cart;
            LinkedList<String> prodNames;
            LinkedList<Integer> quantity;
            if(session.getAttribute("cart")!=null){
               cart = (LinkedList<String>)(session.getAttribute("cart"));
               prodNames = (LinkedList<String>)(session.getAttribute("prodNames"));
               quantity = (LinkedList<Integer>)(session.getAttribute("quantity"));
            }
            else{
                message = "You have no products selected. Invoice could not be created";
                request.setAttribute("message", message);
                return;
            }


            //you don't change this
            PreparedStatement ps = conn.prepareStatement(preparedSQL, Statement.RETURN_GENERATED_KEYS);

            //-----First Make the Invoice entry
            int inputCustomerID = Integer.parseInt(request.getParameter("customerIDInput"));
            int inputClinicID = Integer.parseInt(request.getParameter("chosenClinic"));
            Date date = new Date();
            String inputInvoiceDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            String inputDeliveryDate = request.getParameter("deliveryDateInput");
            String inputAddAcc = request.getParameter("addAccInput");
            String inputTop = request.getParameter("topInput");
            String inputPaymentDueDate = request.getParameter("paymentDueDateInput");
            String inputDatePaid = request.getParameter("datePaidInput");
            int inputStatus = Integer.parseInt(request.getParameter("statusInput"));
            String inputInvoiceName = request.getParameter("invoiceNameInput");
            float inputAmountDue = 0 + Float.parseFloat(request.getParameter("amountDueInput"));
            float inputDiscount = 0 + Float.parseFloat(request.getParameter("discountInput"));
            float inputAmountPaid = 0 + Float.parseFloat(request.getParameter("amountPaidInput"));
            String lastEdittedBy = ""+session.getAttribute("userName");


            ps.setInt(1,inputCustomerID);
            ps.setInt(2, inputClinicID);
            ps.setString(3,inputInvoiceDate);
            ps.setString(4,inputDeliveryDate);
            ps.setString(5,inputTop);
            ps.setString(6,inputPaymentDueDate);
            if(inputDatePaid.equals("")){ps.setString(7,null);}
            else{ps.setString(7,inputDatePaid);}
            if(inputStatus==1){ps.setString(8,inputDatePaid);}
            else{ps.setString(8,null);}
            ps.setInt(9,inputStatus);
            ps.setFloat(10, 0);
            ps.setString(11,inputInvoiceName);
            ps.setFloat(12,inputAmountDue);
            ps.setFloat(13,inputDiscount);
            ps.setFloat(14, inputAmountPaid);
            ps.setString(15,lastEdittedBy);
            context.log(preparedSQL);
            ps.executeUpdate();                   //at this point, you have already inserted into the database


            //-----Now make the InvoiceItems
            int invoiceIDInput;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
               if (generatedKeys.next()) {
                   invoiceIDInput = generatedKeys.getInt(1);
                   context.log("The invoiceID of the new invoice is: " + invoiceIDInput);

                   //the session attributes are:
                   //cart  -- these are the productIDs
                   //prodNames -- these are the productNames
                   //quantity  -- these are the quantities of each invoiceItem

                   //DO NOT SORT THEM EVER. They're already in order.

                   //This is where you start making the invoiceItems and add them to the database

                   String preparedSQL2;
                   PreparedStatement ps2;
                   Integer inputInvoiceID = generatedKeys.getInt(1);
                   String inputProductID;
                   String inputQuantityPurchased;
                   for(int i=0;i<cart.size();i++){
                       //you gotta insert into the table for every product in the cart
                       preparedSQL2 = "insert into InvoiceItem(invoiceID, productID, quantityPurchased) "
                                       + "values(?,?,?)";
                       ps2 = conn.prepareStatement(preparedSQL2);

                       inputProductID = cart.get(i);
                       inputQuantityPurchased = ""+quantity.get(i);

                       ps2.setInt(1, inputInvoiceID);
                       ps2.setInt(2, Integer.parseInt(inputProductID));
                       ps2.setInt(3, Integer.parseInt(inputQuantityPurchased));
                       ps2.executeUpdate();
                       context.log("You've updated: " + inputProductID);
                   }

               }
               else {
                   throw new SQLException("--> no invoiceID retrieved");
               }
           }

            //if(inputStatus.equals("Completed")){
               //now update the product
               //first get the invoice items

               //it has just occured to us that the inventory should update regardless of completion
               preparedSQL = "select * from InvoiceItem where invoiceID = ?";
               ps = conn.prepareStatement(preparedSQL);
               ps.setInt(1,invoiceIDInput);

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

              /* UPDATE Product JOIN InvoiceItem ON Product.productID=InvoiceItem.productID
               SET Product.stocksRemaining = Product.stocksRemaining-InvoiceItem.quantityPurchased
               WHERE Product.productID=1 and InvoiceItem.invoiceID=9;*/
               for(invoiceItemBean iibean : invItemsRetrieved){
                   preparedSQL = "UPDATE Product JOIN InvoiceItem ON Product.productID=InvoiceItem.productID" +
      "               SET Product.stocksRemaining = Product.stocksRemaining-InvoiceItem.quantityPurchased" +
      "               WHERE Product.productID=? and InvoiceItem.invoiceID=?;";
                   ps = conn.prepareStatement(preparedSQL);
                   ps.setInt(1,iibean.getProductID());
                   ps.setInt(2,invoiceIDInput);

                   ps.executeUpdate();

                   message = "Invoice successfully created! Inventory Updated.";
               }
            //}


            request.setAttribute("message", message);
            session.setAttribute("cart", null);
            session.setAttribute("prodNames", null);
            session.setAttribute("quantity", null);
            request.getRequestDispatcher("homePage.jsp").forward(request,response);

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
