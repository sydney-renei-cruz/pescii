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
import java.text.Format;
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
@WebServlet(name = "createRestockOrderServlet", urlPatterns = {"/createRestockOrderServlet"})
public class createRestockOrderServlet extends HttpServlet {

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
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
         String toCancel = ""+request.getParameter("cancel");
         context.log("toCancel = "+toCancel);
         String message = "Restock Order successfully created!";
         if(toCancel.equals("yes")){    //this is for when the invoice is cancelled. It was put here to save space...I guess.
                message = "Restock Order creation cancelled.";
                request.setAttribute("message", message);
                session.setAttribute("ROcart", null);
                session.setAttribute("ROprodNames", null);
                session.setAttribute("ROquantity", null);
                session.setAttribute("ROtotalPrices", null);
                session.setAttribute("cartType", null);
                session.setAttribute("suppID", null);
                request.getRequestDispatcher("notif.get").forward(request,response);
                return;
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
        
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
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
            
         conn = DriverManager.getConnection(context.getInitParameter("databaseUrl"), context.getInitParameter("databaseUser"), context.getInitParameter("databasePassword"));
        
         //Allocate a Statement object within the Connection
         stmt = conn.createStatement();
         
         //---------------
         //THIS IS WHERE YOU START CHANGING
         
         String toCancel = ""+request.getParameter("cancel");
         context.log("toCancel = "+toCancel);
         String message = "Restock Order successfully created!";
         if(toCancel.equals("yes")){    //this is for when the invoice is cancelled. It was put here to save space...I guess.
                message = "Restock Order creation cancelled.";
                request.setAttribute("message", message);
                session.setAttribute("ROcart", null);
                session.setAttribute("ROprodNames", null);
                session.setAttribute("ROquantity", null);
                session.setAttribute("ROtotalPrices", null);
                session.setAttribute("cartType", null);
                session.setAttribute("suppID", null);
                request.getRequestDispatcher("notif.get").forward(request,response);
                return;
         }
         else{
         
         context.log("now in createRestockOrderServlet!");
         /*String preparedSQL = "insert into RestockOrder(productID, numberOfPiecesOrdered, supplier, purpose, RODateDue, ROName) values(?,?,?,?,?,?)";
         preparedSQL = "insert into RestockOrder(productID, ROName, numberOfPiecesOrdered, supplierID, "
                 + "purpose, RODateDue, discount, lastEdittedBy) "
                 + "values(?,?,?,?,?,?,?,?)";
             */
         String preparedSQL = "insert into RestockOrder(ROName, purpose, RODateDue, discount, lastEdittedBy, supplierID, restockPrice) "
                 + "values(?,?,?,?,?,?,?)";
         //you don't change this
         //PreparedStatement rs = conn.prepareStatement(preparedSQL);
         
          //this is put at the start because it needs to cancel immediately if there are no InvoiceItems
            LinkedList<String> cart;
            LinkedList<String> prodNames;
            LinkedList<Integer> quantity;
            LinkedList<Float> ROtotalPrices;
            if(session.getAttribute("ROcart")!=null && (""+session.getAttribute("cartType")).equals("restock")){
               context.log("so the ROcart isn't null");
               cart = (LinkedList<String>)(session.getAttribute("ROcart"));
               prodNames = (LinkedList<String>)(session.getAttribute("ROprodNames"));
               quantity = (LinkedList<Integer>)(session.getAttribute("ROquantity"));
               ROtotalPrices = (LinkedList<Float>)(session.getAttribute("ROtotalPrices"));
            }
            else{
                message = "You have no products selected. Invoice could not be created";
                request.setAttribute("message", message);
                return;
            }

            //you don't change this
            PreparedStatement ps = conn.prepareStatement(preparedSQL, Statement.RETURN_GENERATED_KEYS);
         
         //String message = "";
         
         //check RO name
         String inputROName;
         context.log("now in createRestockOrderServlet 2!");
         try{
             inputROName = request.getParameter("RONameInput");
             if(inputROName.length()>255){
                 message = "Restock Order Name is too long.";
                 request.setAttribute("message",message);
                 request.setAttribute("forRestock", "yes");
                request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Restock Order Name is too long.";
                request.setAttribute("message",message);
                request.setAttribute("forRestock", "yes");
                request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);
                return;
            }
         
         String inputPurpose = request.getParameter("purposeInput");
         context.log("now in createRestockOrderServlet 3!");
         //check date due
         String inputDateDue = "";
         Date date = new Date();
         try{
               inputDateDue = request.getParameter("dateDueInput");
                if(inputDateDue.length()>10){
                    message = "Due Date format is invalid.";
                    request.setAttribute("message",message);
                    request.setAttribute("forRestock", "yes");
                    request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);
                    return;
                 }

                else{
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //date = sdf.parse(inputDateDue);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    dateFormat.parse(inputDateDue.trim());
                }
             }
             catch(Exception e){
                message = "Due Date format is invalid.";
                request.setAttribute("message",message);
                request.setAttribute("forRestock", "yes");
                context.log(""+e);
                request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);
                return;
             }
         
         //check discount
         float inputDiscount = 0;
            try{
                inputDiscount = Float.parseFloat(request.getParameter("discountInput"));
                if(inputDiscount<0){
                    message = "Discount was input incorrectly. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.setAttribute("forRestock", "yes");
                    request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);
                    return;
                }
            }
            catch(Exception e){
                message = "Discount was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.setAttribute("forRestock", "yes");
                request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);
                return;
            }
            
            //check status ID
            int inputSupplierID = 0;
            try{inputSupplierID = Integer.parseInt(""+session.getAttribute("suppID"));}
            catch(Exception e){
                   message = "Restock Order Supplier is invalid.";
                   request.setAttribute("message",message);
                   request.setAttribute("forRestock", "yes");
                   request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);
                   return;
            }
            
         
         String lastEdittedBy = ""+session.getAttribute("userName");
         context.log("now in createRestockOrderServlet 4!");
         
          float total = 0;
            for(int i=0;i<cart.size();i++){
                total = total + ROtotalPrices.get(i);
                context.log("Total price is now: " + total);
            }
         
         
         ps.setString(1,inputROName);
         ps.setString(2,inputPurpose);
         ps.setString(3,inputDateDue);
         ps.setFloat(4,inputDiscount);
         ps.setString(5,lastEdittedBy);
         ps.setInt(6,inputSupplierID);
         ps.setFloat(7,total);
         
         ps.executeUpdate();                   //at this point, you have already inserted into the database
         
         //-----Now make the InvoiceItems
            int restockOrderIDInput;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
               if (generatedKeys.next()) {
                   restockOrderIDInput = generatedKeys.getInt(1);
                   context.log("The restockOrderID of the new RO is: " + restockOrderIDInput);

                   //the session attributes are:
                   //cart  -- these are the productIDs
                   //prodNames -- these are the productNames
                   //quantity  -- these are the quantities of each invoiceItem

                   //DO NOT SORT THEM EVER. They're already in order.

                   //This is where you start making the invoiceItems and add them to the database

                   String preparedSQL2;
                   PreparedStatement ps2;
                   Integer inputRestockOrderID = generatedKeys.getInt(1);
                   String inputProductID;
                   String inputQuantityPurchased;
                   for(int i=0;i<cart.size();i++){
                       //you gotta insert into the table for every product in the cart
                       preparedSQL2 = "insert into RestockOrderItem(RestockOrderID, productID, quantityPurchased) "
                                       + "values(?,?,?)";
                       ps2 = conn.prepareStatement(preparedSQL2);

                       inputProductID = cart.get(i);
                       inputQuantityPurchased = ""+quantity.get(i);

                       ps2.setInt(1, inputRestockOrderID);
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
            context.log("now in createRestockOrderServlet! 5");
               //it has just occured to us that the inventory should update regardless of completion
               preparedSQL = "select * from RestockOrderItem where RestockOrderID = ?";
               ps = conn.prepareStatement(preparedSQL);
               ps.setInt(1,restockOrderIDInput);

               ResultSet dbData = ps.executeQuery();
               //you might wanna change this to an array one of these days
               ArrayList<restockOrderItemBean> restockOrderItemsRetrieved = new ArrayList<restockOrderItemBean>();
               //retrieve the information.
                  while(dbData.next()){
                     restockOrderItemBean restockOrderItemBean = new restockOrderItemBean();
                     //invItemBean.setInvoiceID(dbData.getInt("invoiceID"));
                     restockOrderItemBean.setProductID(dbData.getInt("productID"));
                     //invItemBean.setQuantityPurchased(dbData.getInt("quantityPurchased"));
                     restockOrderItemsRetrieved.add(restockOrderItemBean);
                  }

              /* UPDATE Product JOIN InvoiceItem ON Product.productID=InvoiceItem.productID
               SET Product.stocksRemaining = Product.stocksRemaining-InvoiceItem.quantityPurchased
               WHERE Product.productID=1 and InvoiceItem.invoiceID=9;*/
               for(restockOrderItemBean roibean : restockOrderItemsRetrieved){
                   preparedSQL = "UPDATE Product JOIN RestockOrderItem ON Product.productID=RestockOrderItem.productID " +
                    "SET Product.stocksRemaining = Product.stocksRemaining+RestockOrderItem.quantityPurchased " +
                    "WHERE Product.productID=? and RestockOrderItem.RestockOrderID=?;";
                   ps = conn.prepareStatement(preparedSQL);
                   ps.setInt(1,roibean.getProductID());
                   ps.setInt(2,restockOrderIDInput);
                   
                   context.log(preparedSQL);
                   
                   ps.executeUpdate();

                   message = "Restock Order successfully created! Inventory Updated.";
               }
            //}

            context.log("now in createRestockOrderServlet 6!");
            request.setAttribute("message", message);
            session.setAttribute("ROcart", null);
            session.setAttribute("ROprodNames", null);
            session.setAttribute("ROsuppIDs", null);
            session.setAttribute("ROsuppNames", null);
            session.setAttribute("ROquantity", null);
            session.setAttribute("ROtotalPrices", null);
            session.setAttribute("cartType", null);
            session.setAttribute("supplier", null);
            request.getRequestDispatcher("notif.get").forward(request,response);

           }
         
         /*
         message = "Restock Order successfully created!";
         request.setAttribute("message", message);
         request.getRequestDispatcher("notif.get").forward(request,response);
         */
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
