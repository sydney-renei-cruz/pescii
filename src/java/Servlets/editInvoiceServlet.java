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
         /*String preparedSQL = "update Invoice set invoiceName=?, deliveryDate=?, termsOfPayment=?, "
                 + "paymentDueDate=?, datePaid=?, dateClosed=?, statusID=?, amountDue=?, "
                 + "discount=?, amountPaid=?, dateDelivered=?, lastEdittedBy=?, overDueFee=? "
                 + "where invoiceID=?";
         */
         String message = "";
         
         int invoiceID = 0;
         try{invoiceID = Integer.parseInt(request.getParameter("invoiceIDInput"));}
         catch(Exception e){
                message = "Invoice ID format is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
         }
         
         
         
         //check the inoice name
         String newInvoiceName = "";
         boolean invoiceName=false;
         try{
            newInvoiceName = request.getParameter("newInvoiceNameInput");
            if(!newInvoiceName.equals("") && newInvoiceName!=null){
                if(newInvoiceName.length()>255){
                    message = "Invoice Name is too long.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                }
                else{invoiceName=true;}
            }
            
        }
        catch(Exception e){
            message = "Invoice Name was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
            return;
        }
         
         Date date = new Date();
         
         //check the delivery date
         String newDeliveryDate = "";
         boolean deliveryDate = false;
         try{
                if(request.getParameter("deliveryDateInput")!=null && !request.getParameter("deliveryDateInput").equals("")){
                    newDeliveryDate = request.getParameter("deliveryDateInput");
                    if(newDeliveryDate.length()>10){
                        message = "Delivery Date format is invalid.";
                        request.setAttribute("message",message);
                        request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                        return;
                     }

                    else{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        date = sdf.parse(newDeliveryDate);
                        deliveryDate=true;
                    }
                }
                /*else if(request.getParameter("deliveryDateInput")==null || request.getParameter("deliveryDateInput").equals("")){
                    newDeliveryDate=null;
                    deliveryDate=true;
                }*/
        }
        catch(Exception e){
           message = "Delivery Date format is invalid.";
           request.setAttribute("message",message);
           request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
           return;
        }
         
         //check terms of payment
         String newTop = "";
         boolean top=false;
         try{
             newTop = request.getParameter("topInput");
             if(!newTop.equals("") && newTop!=null){
                if(newTop.length()>20){
                    message = "Terms of Payment is too long.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                }
                else{top=true;}
             }
            }
            catch(Exception e){
                message = "Terms of Payment was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
            }
         
         //check payment due date
         String newPaymentDueDate = "";
         boolean paymentDueDate=false;
         try{
                if(request.getParameter("paymentDueDateInput")!=null && !request.getParameter("paymentDueDateInput").equals("")){
                    newPaymentDueDate = request.getParameter("paymentDueDateInput");
                    if(newPaymentDueDate.length()>10){
                        message = "Payment Due Date format is invalid.";
                        request.setAttribute("message",message);
                        request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                        return;
                    }
                    else{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        date = sdf.parse(newPaymentDueDate);
                        paymentDueDate=true;
                    }
                    char c;
                    for(int i=0;i<newPaymentDueDate.length();i++){
                        c=newPaymentDueDate.charAt(i);
                        if(Character.isLetter(c)){
                            paymentDueDate=false;
                            message = "Payment Due Date format is invalid.";
                            request.setAttribute("message",message);
                            request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                            return;
                        }
                    }

                    
                }
             }
             catch(Exception e){
                message = "Payment Due Date format is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
             }
         
         //check date paid
         String newDatePaid = "";
         boolean datePaid=false;
         try{
             if(request.getParameter("datePaidInput")!=null && !request.getParameter("datePaidInput").equals("")){
                newDatePaid = request.getParameter("datePaidInput");
                
                if(newDatePaid.length()>10){
                    message = "Date Paid format is too long.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                 }
                
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.parse(newDatePaid);
                    datePaid=true;
                }
                
                char c;
                    for(int i=0;i<newDatePaid.length();i++){
                        c=newDatePaid.charAt(i);
                        if(Character.isLetter(c)){
                            paymentDueDate=false;
                            message = "Date Paid format is invalid.";
                            request.setAttribute("message",message);
                            request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                            return;
                        }
                    }
                
             }
             }
             catch(Exception e){
                message = "Date Paid format is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
             }
         
         
         //check the amount due
         float newAmountDue = 0;
         boolean amountDue=false;
         try{
                newAmountDue = Float.parseFloat(request.getParameter("amountDueInput"));
                if(newAmountDue < 0){
                    message = "Amount Due was input incorrectly. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                }
                else{amountDue=true;}
            }
            catch(Exception e){
                message = "Amount Due was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
            }
         
         
         //check the new discount
         float newDiscount = 0;
         boolean discount=false;
         try{
                newDiscount = Float.parseFloat(request.getParameter("discountInput"));
                if(newDiscount < 0){
                    message = "Discount was input incorrectly.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                }
                else{discount=true;}
            }
            catch(Exception e){
                message = "Discount was input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
            }
         
         
         //check the amount paid
         float newAmountPaid = 0;
         boolean amountPaid=false;
         try{
                newAmountPaid = Float.parseFloat(request.getParameter("amountPaidInput"));
                if(newAmountPaid < 0){
                    message = "Amount Paid was input incorrectly.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                }
                else{amountPaid=true;}
            }
            catch(Exception e){
                message = "Amount Paid was input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
            }
         
         
         //check date delivered
         String newDateDelivered = "";//request.getParameter("dateDeliveredInput");
         boolean dateDelivered=false;
         try{
             
             if(request.getParameter("dateDeliveredInput")!=null && !request.getParameter("dateDeliveredInput").equals("")){
                newDateDelivered = request.getParameter("dateDeliveredInput");
                if(newDateDelivered.length()>10){
                    message = "Date Delivered format is too long.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                 }
                
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.parse(newDateDelivered);
                    dateDelivered=true;
                }
                
                char c;
                    for(int i=0;i<newDateDelivered.length();i++){
                        c=newDateDelivered.charAt(i);
                        if(Character.isLetter(c)){
                            paymentDueDate=false;
                            message = "Date Delivered format is invalid.";
                            request.setAttribute("message",message);
                            request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                            return;
                        }
                    }
            }
        }
         catch(Exception e){
            message = "Date Delivered format is invalid.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
            return;
         }
         
         //check invoice status
         int newStatus = 0;
         boolean status=false;
         try{
             newStatus = Integer.parseInt(request.getParameter("statusInput"));
             if(newStatus > 0){
                 status=true;
             }
             if(newStatus==1){
                 if((newDateDelivered!=null && !newDateDelivered.equals("")) && (newDatePaid!=null && !newDatePaid.equals(""))){
                     status=true;
                 }
                 else{
                    message = "Invoice cannot be 'Complete'. Date Delivered or DatePaid is missing. Confirm with the Secretary and Accountant.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                 }
             }
         }
         catch(Exception e){
                message = "Invoice Status is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
         }
         
         //no need to check, since it'll just set it to the current day
         String newDateClosed = "";
         boolean dateClosed=false;
         if(newStatus!=2){
            date = new Date();
            newDateClosed = new SimpleDateFormat("yyyy-MM-dd").format(date);
            dateClosed=true;
         }
         
         //check the overdue fee
         float newOverdueFee = 0;
         boolean overdueFee=false;
         try{
                newOverdueFee = Float.parseFloat(request.getParameter("overdueFeeInput"));
                if(newOverdueFee < 0){
                    message = "Overdue fee was input incorrectly. It should also not be blank.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                    return;
                }
                else{overdueFee=true;}
            }
            catch(Exception e){
                message = "Overdue fee was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID="+invoiceID).forward(request,response);
                return;
            }
         
         
         String lastEdittedBy = ""+session.getAttribute("userName");
         String preparedSQL = "update Invoice set invoiceName=?, deliveryDate=?, termsOfPayment=?, "
                 + "paymentDueDate=?, datePaid=?, dateClosed=?, statusID=?, amountDue=?, "
                 + "discount=?, amountPaid=?, dateDelivered=?, lastEdittedBy=?, overDueFee=? "
                 + "where invoiceID=?";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         if(invoiceName==true){
             preparedSQL = "update Invoice set invoiceName=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newInvoiceName);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the invoiceName!");
         }
         if(deliveryDate==true){
             preparedSQL = "update Invoice set deliveryDate=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newDeliveryDate);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the deliveryDate!");
         }
         if(top==true){
             preparedSQL = "update Invoice set termsOfPayment=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newTop);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the terms of payment!");
         }
         if(paymentDueDate==true){
             preparedSQL = "update Invoice set paymentDueDate=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newPaymentDueDate);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the paymentDueDate!");
         }
         if(datePaid==true){
             preparedSQL = "update Invoice set datePaid=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newDatePaid);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the invoiceName!");
         }
         if(dateClosed==true){
             preparedSQL = "update Invoice set dateClosed=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newDateClosed);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the invoice dateClosed!");
         }
         if(status==true){
             preparedSQL = "update Invoice set statusID=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setInt(1,newStatus);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the invoice status!");
         }
         if(amountDue==true){
             preparedSQL = "update Invoice set amountDue=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setFloat(1,newAmountDue);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the amountDue!");
         }
         if(discount==true){
             preparedSQL = "update Invoice set discount=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setFloat(1,newDiscount);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the invoice discount!");
         }
         if(amountPaid==true){
             preparedSQL = "update Invoice set amountPaid=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setFloat(1,newAmountPaid);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the invoice amountPaid!");
         }
         if(dateDelivered==true){
             preparedSQL = "update Invoice set dateDelivered=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newDateDelivered);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the invoice dateDelivered!");
         }
         if(overdueFee==true){
             preparedSQL = "update Invoice set overDueFee=? where invoiceID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setFloat(1,newOverdueFee);
             ps.setInt(2,invoiceID);
             ps.executeUpdate();
             context.log("updated the overdueFee!");
         }
         preparedSQL = "update Invoice set lastEdittedBy=? where invoiceID=?";
         ps = conn.prepareStatement(preparedSQL);
         ps.setString(1,lastEdittedBy);
         ps.setInt(2,invoiceID);
         ps.executeUpdate();
         context.log("updated the invoice lastEdittedBy!");
         /*
         ps.setString(1,newInvoiceName);
         ps.setString(2,newDeliveryDate);
         ps.setString(3,newTop);
         ps.setString(4,newPaymentDueDate);
         //ps.setString(4,newDatePaid);
         if(newDatePaid==null || newStatus==3){ps.setString(5,null);}
         else{ps.setString(5,newDatePaid);}
         if(newDateClosed==null){ps.setString(6,null);}
         else{ps.setString(6,newDateClosed);}
         ps.setInt(7,newStatus);
         ps.setFloat(8,newAmountDue);
         ps.setFloat(9,newDiscount);
         ps.setFloat(10,newAmountPaid);
         ps.setString(11,newDateDelivered);
         ps.setString(12,lastEdittedBy);
         ps.setFloat(13,newOverdueFee);
         ps.setInt(14,invoiceID);
         
         
         ps.executeUpdate();*/
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
         request.setAttribute("invID", invoiceID);
         request.setAttribute("message", "Invoice successfully editted! Inventory updated.");
         request.getRequestDispatcher("anotherInvoice.jsp").forward(request,response);
         
        }
        catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            String message = "Something went wrong. Please try again or contact the administrator.";
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
                String message = "Something went wrong. Please try again or contact the administrator.";
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
