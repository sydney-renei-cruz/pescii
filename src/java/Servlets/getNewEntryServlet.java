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
              forWhere = "where Invoice.dateCreated";
             //this makes the SQL statement for invoices near payment deadlines
                //that is, those with payment deadlines within 7 days ahead of the current day
             if(getWhat.equals("close")){
                 forWhere = "where Invoice.paymentDueDate";
                 interval = " between now() and date_add(now(), interval 7 day)";
                 status = " and InvoiceStatus.statusName='In Progress' and Invoice.datePaid = null";
                 orderBy = " order by Invoice.paymentDueDate asc";
             }
             //this sets up the SQL statement for invoices validated, regardless of date
                //remember: a validated invoice is that which is paid for but not delivered yet
             else if(getWhat.equals("validated")){
                 forWhere = "where Invoice.datePaid";
                 status = " and InvoiceStatus.statusName='In Progress'";
                 interval = "";
                 orderBy = " order by Invoice.deliveryDate asc";
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
                 //invoice status field
                 
                String[] inputStatus = request.getParameterValues("searchStatusInput");
                String invoiceStatuses="";
                if(inputStatus!=null){
                    if(!(condition.equals(""))){
                        compound=" and ";
                    }
                   invoiceStatuses = "(InvoiceStatus.statusName = ";
                   for(int i=0;i<inputStatus.length;i++){
                       if(i==0){invoiceStatuses=invoiceStatuses+"'"+inputStatus[i]+"'";}
                       else{invoiceStatuses=invoiceStatuses+" or InvoiceStatus.statusName = '"+inputStatus[i]+"'";}
                   }
                   condition = condition + compound + invoiceStatuses+")";
                   compound="";
                }
                //customer name field
                if(!request.getParameter("searchCustomerLastNameInput").equals("")){
                        if(!(condition.equals(""))){
                            compound=" and";
                        }
                        if(!(request.getParameter("searchCustomerLastNameInput").equals("all"))){
                           condition = condition + compound + " Customer.CustomerLastName like '%"+request.getParameter("searchCustomerLastNameInput")+"%'";
                        }
                        compound="";
                    }
                if(!request.getParameter("searchCustomerFirstNameInput").equals("")){
                        if(!(condition.equals(""))){
                            compound=" and";
                        }
                        if(!(request.getParameter("searchCustomerFirstNameInput").equals("all"))){
                           condition = condition + compound + " Customer.CustomerFirstName like '%"+request.getParameter("searchCustomerFirstNameInput")+"%'";
                        }
                        compound="";
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
                        interval = " between '" + searchDateFrom + " 00:00:00' and '" + searchDateTo + " 23:59:59'";
                        condition = condition + compound + request.getParameter("searchDateInput");
                        compound="";
                    }
                 }
                 forWhere="";
                 searchName="";
                 orderBy = " order by Invoice.invoiceName";
             }
             
             if(!condition.equals("")){condition = "where " + condition;}
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
                 + condition + forWhere + searchName + interval + status + orderBy;
             
             
             context.log(preparedSQL);
             ps = conn.prepareStatement(preparedSQL);
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
                 request.getRequestDispatcher("getInvoice.jsp").forward(request,response);
                 return;
         }
         
         //this part is for when a Customer is being searched. Currently not implemented
         else if(whatFor.equals("customer")){
             
             //this is for searching with compound conditions
             if(getWhat.equals("customSearch")){
                 
                 interval="";
                 forWhere="";
                 
                //customer name field
                if(!request.getParameter("searchCustomerLastNameInput").equals("")){
                        condition = condition + " Customer.CustomerLastName like '%"+request.getParameter("searchCustomerLastNameInput")+"%'";
                        compound="";
                    }
                if(!request.getParameter("searchCustomerFirstNameInput").equals("")){
                        if(!(condition.equals(""))){
                            compound=" and";
                        }
                        if(!(request.getParameter("searchCustomerFirstNameInput").equals("all"))){
                           condition = condition + compound + " Customer.CustomerFirstName like '%"+request.getParameter("searchCustomerFirstNameInput")+"%'";
                        }
                        compound="";
                    }
                //PRCID field
                if(!request.getParameter("searchPRCIDInput").equals("")){
                        if(!(condition.equals(""))){
                            compound=" and";
                        }
                        if(!(request.getParameter("searchPRCIDInput").equals("all"))){
                           condition = condition + compound + " Customer.PRCID like '%"+request.getParameter("searchPRCIDInput")+"%'";
                        }
                        compound="";
                    }
                
                 //SalesRep field
                 if(!request.getParameter("searchSalesRepInput").equals("")){
                     if(!(condition.equals(""))){
                         compound=" and";
                     }
                     if(!(request.getParameter("searchSalesRepInput").equalsIgnoreCase("all"))){
                        condition = condition + compound + " SalesRep.salesRepID = '"+request.getParameter("searchSalesRepInput")+"'";
                     }
                     compound="";
                 }
                 //province field
                 if(!request.getParameter("searchProvinceInput").equals("")){
                     if(!(condition.equals(""))){
                         compound=" and";
                     }
                     if(!(request.getParameter("searchProvinceInput").equalsIgnoreCase("all"))){
                        condition = condition + compound + " Province.provinceID = '"+request.getParameter("searchProvinceInput")+"'";
                     }
                     compound="";
                 }
                 
                 forWhere="";
                 searchName="";
                 orderBy = " group by Customer.PRCID";
             }
             
             if(!condition.equals("")){condition = "where " + condition;}
             preparedSQL = "select Customer.PRCID, Customer.customerFirstName, Customer.customerLastName, "
                     + "Customer.customerMobileNumber, Customer.customerTelephoneNumber, Customer.SalesRepID, "
                     + "SalesRep.salesRepID, SalesRep.salesRepFirstName, SalesRep.salesRepLastName, "
                     + "Customer.customerID, Clinic.customerID, Clinic.provinceID, "
                     + "Province.provinceID, Province.provinceName from Customer "
                     + "inner join SalesRep on SalesRep.salesRepID = Customer.salesRepID "
                     + "inner join Clinic on Clinic.customerID = Customer.customerID "
                     + "inner join Province on Province.provinceID = Clinic.provinceID "
                     + condition + forWhere + searchName + interval + orderBy;
             
             context.log(preparedSQL);
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
             //brand field
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
                productClasses = "(ProductClass.productClassName = ";
                for(int i=0;i<inputProductClass.length;i++){
                    if(i==0){productClasses=productClasses+"'"+inputProductClass[i]+"'";}
                    else{productClasses=productClasses+" or ProductClass.productClassName = '"+inputProductClass[i]+"'";}
                }
                condition = condition + compound + productClasses+")";
                compound="";
             }
             //lowStock field
             if(request.getParameter("lowStockInput")!=null){
                if(!(condition.equals(""))){
                    compound=" and";
                }
                String inputLowStock = "" + request.getParameter("lowStockInput");
                String inputStocks = "";
                if(inputLowStock.equals("yes")){inputStocks=" Product.stocksRemaining <= lowStock";}
                else if(inputLowStock.equals("no")){inputStocks=" Product.stocksRemaining >lowStock";}
                condition = condition + compound + inputStocks;
                compound="";
             }
             //supplier field
                 if(!(request.getParameter("searchSupplierInput").equals(""))){
                     if(!(condition.equals(""))){
                         compound=" and";
                     }
                     if(!request.getParameter("searchSupplierInput").equalsIgnoreCase("All")){
                        condition = condition + compound + " Supplier.supplierName like '%"+request.getParameter("searchSupplierInput")+"%'";
                         
                     }
                     compound="";
                 }
                 if(!condition.equals("")){condition = "where " + condition + "order by productName asc";}
                 else{condition = condition + " order by productName asc";}
             preparedSQL = "select Product.productID, Product.productName, Product.productDescription, "
                 + "Product.productPrice, Product.restockPrice, Product.stocksRemaining, Product.lowStock, "
                 + "Product.brand, Product.productClassID, ProductClass.productClassname, Product.color, "
                 + "Product.supplierID, Supplier.supplierID, Supplier.supplierName from Product "
                 + "inner join ProductClass on ProductClass.productClassID = Product.productClassID "
                 + "inner join Supplier on Supplier.supplierID = Product.supplierID "
                 + condition;
             
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
                pbean.setProductClassID(dbData.getInt("productClassID"));
                pbean.setProductClassName(dbData.getString("productClassName"));
                pbean.setColor(dbData.getString("color"));
                pbean.setSupplierID(dbData.getInt("supplierID"));
                pbean.setSupplierName(dbData.getString("supplierName"));
                productsRetrieved.add(pbean);
            }
            
            if((""+request.getParameter("forOther")).equals("invoice")){request.setAttribute("forInvoice", "yes");}
            request.setAttribute("productsList", productsRetrieved);
            request.getRequestDispatcher("getProduct.jsp").forward(request,response);
            return;
         }
         
          
         //this part is for when an RO is being searched
         else if(whatFor.equals("restockOrder")){
             forWhere = "where RestockOrder.dateCreated";
            //this is for getting ROs with near delivery dates
            if(getWhat.equals("close")){
                forWhere="where RestockOrder.RODateDue";
                interval=" between now() and date_add(now(), interval 7 day) and RestockOrder.RODateDelivered = null";
                orderBy = " order by RestockOrder.RODateDue asc";
            }
            //this is for getting completed ROs
            else if(getWhat.equals("completed")){
                forWhere="where RestockOrder.RODateDelivered";
                orderBy = " order by RestockOrder.RODateDelivered";
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
                     if(!request.getParameter("searchSupplierInput").equalsIgnoreCase("All")){
                        condition = condition + compound + " Supplier.supplierName like '%"+request.getParameter("searchSupplierInput")+"%'";
                         
                     }
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
                 //productClass field
                 String[] inputProductClass = request.getParameterValues("productClassInput");
                 String productClasses="";
                 if(inputProductClass!=null){
                    if(!(condition.equals(""))){
                        compound=" and ";
                    }
                   productClasses = "(ProductClass.productClassName = ";
                   for(int i=0;i<inputProductClass.length;i++){
                       if(i==0){productClasses=productClasses+"'"+inputProductClass[i]+"'";}
                       else{productClasses=productClasses+" or ProductClass.productClassName = '"+inputProductClass[i]+"'";}
                   }
                   condition = condition + compound + productClasses+")";
                   compound="";
                 }
                 //date fields (can be expected date delivered, date created, or date received
                 if(!(request.getParameter("searchDateInput").equals(""))){
                     if(!(searchDateFrom.equals(""))){
                        if(!(condition.equals(""))){
                            compound=" and ";
                        }
                        interval = " between '" + searchDateFrom + " 00:00:00' and '" + searchDateTo + " 23:59:59'";
                        condition = condition + compound + request.getParameter("searchDateInput");
                        compound="";
                    }
                 }
                 forWhere="";
                 searchName="";
                 orderBy = " order by RestockOrder.ROName";
             }
             if(!condition.equals("")){condition = "where " + condition;}
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
                 + condition + forWhere + searchName + interval + orderBy;
            
            
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
                rbean.setSupplierID(dbData.getInt("supplierID"));
                rbean.setSupplierName(dbData.getString("supplierName"));
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
