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
@WebServlet(name = "getNewEntryServlet", urlPatterns = {"/getNewEntryServlet"})
public class getNewEntryServlet extends HttpServlet {

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
         String preparedSQL = "";
         PreparedStatement ps;
         String whatFor = ""+request.getParameter("whatFor");
         //String forClose = ""+request.getParameter("close");
         //String forValidated = ""+request.getParameter("validated");
         //String forCompleted = ""+request.getParameter("completed");
         String getWhat = ""+request.getParameter("getWhat");
         String forWhere = "dateCreated";   //by default, it'll look for new entries. This'll be changed later
         String interval = " between date_sub(now(),interval 1 week) and now()";
         String status = "";
         String orderBy = "";
         String condition = "";
         String compound = "";
         String province = "";
         
         //this is for the search by name
         String searchName = "";
         
         //this is for the search by date
         String searchDateFrom = ""+request.getParameter("fromDate");
         String searchDateTo = ""+request.getParameter("toDate");
         
         ResultSet dbData;
         
          if(whatFor.equals("invoice")){
             //this makes the SQL statement for invoices near payment deadlines
                //that is, those with payment deadlines within 7 days ahead of the current day
             if(getWhat.equals("close")){
                 forWhere = "paymentDueDate";
                 interval = " between now() and date_add(now(), interval 7 day)";
                 status = " and status='In Progress' and datePaid = null";
                 orderBy = " order by paymentDueDate asc";
             }
             //this sets up the SQL statement for invoices validated, regardless of date
                //remember: a validated invoice is that which is paid for but not delivered yet
             else if(getWhat.equals("validated")){
                 forWhere = "datePaid";
                 status = " and status='In Progress'";
                 interval = "";
                 orderBy = " order by deliveryDate asc";
             }
             
             //this is for searching with compound conditions
             else if(getWhat.equals("customSearch")){
                 
                 interval="";
                 forWhere="";
                 //name field
                 if(!(request.getParameter("searchNameInput").equals(""))){
                     condition = condition + "Invoice.invoiceName like '%"+request.getParameter("searchNameInput")+"%'";
                     compound = "";
                 }
                 //supplier field
                 if(!(request.getParameter("searchStatusInput").equals(""))){
                     if(!(condition.equals(""))){
                         compound=" and";
                         if(!(request.getParameter("searchStatusInput").equals("All"))){
                            condition = condition + compound + " Invoice.status = '"+request.getParameter("searchStatusInput")+"'";
                         }
                         compound="";
                     }
                 }
                 //province field
                 if(!request.getParameter("searchProvinceInput").equals("")){
                     if(!(condition.equals(""))){
                         compound=" and";
                     }
                     if(!(request.getParameter("searchProvinceInput").equals("all"))){
                        condition = condition + compound + " Province.provinceID = '"+request.getParameter("searchProvinceInput")+"'";
                     }
                     compound="";
                 }
                 //date fields
                 /*can be:
                    invoiceDate
                    deliveryDate
                    paymentDueDate
                    datePaid
                    dateClosed
                    dateCreated
                 */
                 if(!(request.getParameter("searchDateInput").equals(""))){
                     if(!(searchDateFrom.equals(""))){
                        if(!(condition.equals(""))){
                            compound=" and ";
                        }
                        interval = " between '" + searchDateFrom + "' and '" + searchDateTo + "'";
                        condition = condition + compound + request.getParameter("searchDateInput");
                        compound="";
                    }
                 }
                 forWhere="";
                 searchName="";
                 orderBy = " order by Invoice.invoiceName";
             }
             
             
             //preparedSQL = "select * from Invoice where " + forWhere + searchName + interval + status + orderBy;
             preparedSQL = "select Invoice.invoiceID, Invoice.invoiceName, Customer.PRCID, Customer.customerFirstName, "
                 + "Customer.customerLastName, Invoice.clinicID, Invoice.invoiceDate, Clinic.clinicName, "
                 + "Invoice.deliveryDate, Invoice.additionalAccessories, Invoice.termsOfPayment, "
                 + "Invoice.paymentDueDate, Invoice.datePaid, Invoice.dateClosed, Invoice.status, "
                 + "Invoice.overdueFee, Clinic.provinceID, Province.provinceName, Province.provinceDivision from Invoice "
                 + "inner join Customer on Customer.customerID = Invoice.customerID "
                 + "inner join Clinic on Clinic.clinicID = Invoice.clinicID "
                 + "inner join Province on Province.provinceID = Clinic.provinceID "
                 + "where " + condition + forWhere + searchName + interval + status + orderBy;
             
             context.log(preparedSQL);
             ps = conn.prepareStatement(preparedSQL);
             dbData = ps.executeQuery();
             ArrayList<invoiceBean> invoicesRetrieved = new ArrayList<invoiceBean>();
             //retrieve the information.
             while(dbData.next()){
                 invoiceBean ibean = new invoiceBean();
                 ibean.setInvoiceID(dbData.getInt("invoiceID"));
                 ibean.setInvoiceName(dbData.getString("invoiceName"));
                 ibean.setPRCID(dbData.getString("PRCID"));
                 ibean.setCustomerName(dbData.getString("customerLastName")+", "+dbData.getString("customerFirstName"));
                 ibean.setClinicID(dbData.getInt("clinicID"));
                 ibean.setClinicName(dbData.getString("clinicName"));
                 ibean.setInvoiceDate(dbData.getDate("invoiceDate"));
                 ibean.setDeliveryDate(dbData.getDate("deliveryDate"));
                 ibean.setAdditionalAccessories(dbData.getString("additionalAccessories"));
                 ibean.setTermsOfPayment(dbData.getString("termsOfPayment"));
                 ibean.setPaymentDueDate(dbData.getDate("paymentDueDate"));
                 if(!(""+dbData.getDate("dateClosed")).equals("0000-00-00")){ibean.setDateClosed(dbData.getDate("dateClosed"));}
                 if(!(""+dbData.getDate("datePaid")).equals("0000-00-00")){ibean.setDatePaid(dbData.getDate("datePaid"));}

                 //ibean.setDatePaid(dbData.getDate("datePaid"));
                 ibean.setStatus(dbData.getString("status"));
                 ibean.setOverdueFee(dbData.getFloat("overdueFee"));
                 invoicesRetrieved.add(ibean);
             }

                 request.setAttribute("invoiceList", invoicesRetrieved);
                 request.getRequestDispatcher("getInvoice.jsp").forward(request,response);
                 return;
         }
         
         //this part is for when a Customer is being searched. Currently not implemented
         else if(whatFor.equals("customer")){
             preparedSQL = "select Customer.PRCID, Customer.customerFirstName, Customer.customerLastName, Customer.customerMobileNumber, "
                     + "Customer.customerTelephoneNumber, SalesRep.salesRepFirstName, SalesRep.salesRepLastName, Customer.customerID, "
                     + "Invoice.invoiceID, Invoice.overdueFee from Customer "
                     + "inner join SalesRep on SalesRep.salesRepID = Customer.salesRepID "
                     + "inner join Invoice on Invoice.customerID = Customer.customerID "
                     + "where Invoice.status = 'In Progress' and Invoice.overdueFee != null "
                     + "order by Customer.customerLastName asc";
         
             ps = conn.prepareStatement(preparedSQL);
             dbData = ps.executeQuery();
             ArrayList<customerBean> customersRetrieved = new ArrayList<customerBean>();
             while(dbData.next()){
                customerBean data = new customerBean();
                data.setCustomerID(dbData.getInt("customerID"));
                data.setPRCID(dbData.getString("PRCID"));
                data.setCustomerFirstName(dbData.getString("customerFirstName"));
                data.setCustomerLastName(dbData.getString("customerLastName"));
                data.setCustomerMobileNumber(dbData.getString("customerMobileNumber"));
                data.setCustomerTelephoneNumber(dbData.getString("customerTelephoneNumber"));
                data.setSalesRep(dbData.getString("salesRepLastName")+", "+dbData.getString("salesRepFirstName"));
                data.setSalesRepID(dbData.getInt("salesRepID"));
                customersRetrieved.add(data);
            }
            
            request.setAttribute("customersList", customersRetrieved);
            request.getRequestDispatcher("getCustomer.jsp").forward(request,response);
            return;
         }
         
         //this part is for when a Product is being searched
         else if(whatFor.equals("product")){
             
             //name field
                 if(!(request.getParameter("searchNameInput").equals(""))){
                     condition = condition + "productName like '%"+request.getParameter("searchNameInput")+"%'";
                     compound = "";
                 }
             //supplier field
                 if(!(request.getParameter("searchBrandInput").equals(""))){
                     if(!(condition.equals(""))){
                         compound=" and";
                     }
                     condition = condition + compound + " brand like '%"+request.getParameter("searchBrandInput")+"%'";
                     compound="";
                 }
             
             //productClass field
             
             String[] inputProductClass = request.getParameterValues("productClassInput");
             String productClasses="";
             if(inputProductClass!=null){
                 if(!(condition.equals(""))){
                     compound=" and ";
                 }
                productClasses = "(productClass = ";
                for(int i=0;i<inputProductClass.length;i++){
                    if(i==0){productClasses=productClasses+"'"+inputProductClass[i]+"'";}
                    else{productClasses=productClasses+" or productClass = '"+inputProductClass[i]+"'";}
                }
                condition = condition + compound + productClasses+")";
                compound="";
             }
             //lowStock field
             //String lowStockInput = "" + request.getParameter("lowStockInput");
             //context.log(lowStockInput);
             //if(!(lowStockInput.equals("")) || lowStockInput!=null || !(lowStockInput.equals("null"))){
             if(request.getParameter("lowStockInput")!=null){
                if(!(condition.equals(""))){
                    compound=" and";
                }
                String inputLowStock = "" + request.getParameter("lowStockInput");
                String inputStocks = "";
                if(inputLowStock.equals("yes")){inputStocks=" stocksRemaining <= lowStock";}
                else if(inputLowStock.equals("no")){inputStocks=" stocksRemaining >lowStock";}
                condition = condition + compound + inputStocks;
                compound="";
             }
             preparedSQL = "select * from Product where " + condition + " order by productName asc";
             context.log(preparedSQL);
             ps = conn.prepareStatement(preparedSQL);
             dbData = ps.executeQuery();
             ArrayList<productBean> productsRetrieved = new ArrayList<productBean>();
             while(dbData.next()){
                productBean pbean = new productBean();
                pbean.setProductID(dbData.getString("productID"));
                pbean.setProductName(dbData.getString("productName"));
                pbean.setProductDescription(dbData.getString("productDescription"));
                pbean.setProductPrice(dbData.getFloat("productPrice"));
                pbean.setRestockPrice(dbData.getFloat("restockPrice"));
                pbean.setStocksRemaining(dbData.getInt("stocksRemaining"));
                pbean.setLowStock(dbData.getInt("lowStock"));
                pbean.setBrand(dbData.getString("brand"));
                pbean.setProductClass(dbData.getString("productClass"));
                pbean.setColor(dbData.getString("color"));
                productsRetrieved.add(pbean);
            }
            
            request.setAttribute("productsList", productsRetrieved);
            request.getRequestDispatcher("getProduct.jsp").forward(request,response);
            return;
         }
         
          
         //this part is for when an RO is being searched
         else if(whatFor.equals("restockOrder")){
            //this is for getting ROs with near delivery dates
            if(getWhat.equals("close")){
                forWhere="RODateDue";
                interval=" between now() and date_add(now(), interval 7 day) and RODateDelivered = null";
                orderBy = " order by RODateDue asc";
            }
            //this is for getting completed ROs
            else if(getWhat.equals("completed")){
                forWhere="RODateDelivered";
                orderBy = " order by RODateDelivered";
            }
            //this is for searching with compound conditions
             else if(getWhat.equals("customSearch")){
                 
                 interval="";
                 forWhere="";
                 //name field
                 if(!(request.getParameter("searchNameInput").equals(""))){
                     condition = condition + "RestockOrder.ROName like '%"+request.getParameter("searchNameInput")+"%'";
                     compound = "";
                 }
                 //supplier field
                 if(!(request.getParameter("searchSupplierInput").equals(""))){
                     if(!(condition.equals(""))){
                         compound=" and";
                     }
                     condition = condition + compound + " RestockOrder.supplier like '%"+request.getParameter("searchSupplierInput")+"%'";
                     compound="";
                 }
                 //productName field
                 if(!request.getParameter("searchProductNameInput").equals("")){
                     if(!(condition.equals(""))){
                         compound=" and";
                     }
                     condition = condition + compound + " Product.productName like '%"+request.getParameter("searchProductNameInput")+"%'";
                     compound="";
                 }
                 //date fields (can be expected date delivered, date created, or date received
                 if(!(request.getParameter("searchDateInput").equals(""))){
                     if(!(searchDateFrom.equals(""))){
                        if(!(condition.equals(""))){
                            compound=" and ";
                        }
                        interval = " between '" + searchDateFrom + "' and '" + searchDateTo + "'";
                        condition = condition + compound + request.getParameter("searchDateInput");
                        compound="";
                    }
                 }
                 forWhere="";
                 searchName="";
                 orderBy = " order by ROName";
             }
             /*
             //this is for searching by date
             else if(getWhat.equals("date")){
                 forWhere=""+request.getParameter("forWhere");
                 interval = " between '"+searchDateFrom+"' and '"+searchDateTo+"'"; 
             }
            */
            preparedSQL = "Select RestockOrder.restockOrderID, Product.productID, Product.productName, RestockOrder.numberOfPiecesOrdered, "
                    + "RestockOrder.numberOfPiecesReceived, RestockOrder.supplier, RestockOrder.purpose, RestockOrder.RODateDue, "
                    + "RestockOrder.RODateDelivered, RestockOrder.ROName "
                    + "from RestockOrder "
                    + "inner join Product on Product.productID = RestockOrder.productID "
                    + "where " + condition + forWhere + searchName + interval + orderBy;
            context.log(preparedSQL);
            ps = conn.prepareStatement(preparedSQL);
            dbData = ps.executeQuery();
            ArrayList<restockOrderBean> restocksRetrieved = new ArrayList<restockOrderBean>();
            //retrieve the information.
               while(dbData.next()){
                  restockOrderBean rbean = new restockOrderBean();
                   rbean.setRestockOrderID(dbData.getInt("restockOrderID"));
                   rbean.setRestockOrderName(dbData.getString("ROName"));
                   rbean.setProductID(dbData.getInt("productID"));
                   rbean.setProductName(dbData.getString("productName"));
                   rbean.setNumberOfPiecesOrdered(dbData.getInt("numberOfPiecesOrdered"));
                   rbean.setNumberOfPiecesReceived(dbData.getInt("numberOfPiecesReceived"));
                   rbean.setSupplier(dbData.getString("supplier"));
                   rbean.setPurpose(dbData.getString("purpose"));
                   rbean.setRODateDue(dbData.getDate("RODateDue"));
                   rbean.setRODateDelivered(dbData.getDate("RODateDelivered"));
                   restocksRetrieved.add(rbean);
               }
            request.setAttribute("restocksList", restocksRetrieved);
            context.log("THE SIZE OF LIST IS------"+restocksRetrieved.size());

            request.getRequestDispatcher("getRestockOrder.jsp").forward(request,response);

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
