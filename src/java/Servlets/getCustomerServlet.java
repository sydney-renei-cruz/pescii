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
@WebServlet(name = "getCustomerServlet", urlPatterns = {"/getCustomerServlet"})
public class getCustomerServlet extends HttpServlet {

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
         String preparedSQL = "select Customer.customerID, Customer.PRCID, "
                 + "Customer.customerFirstName, Customer.customerLastName, "
                 + "Customer.customerMobileNumber, "
                 + "Customer.customerTelephoneNumber, SalesRep.salesRepFirstName, "
                 + "SalesRep.salesRepLastName, Customer.salesRepID, Customer.dateCreated, "
                 + "Customer.lastEdittedBy "
                 + "from Customer "
                 + "inner join SalesRep on SalesRep.salesRepID = Customer.salesRepID "
                 + "order by Customer.customerLastName asc";
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         context.log(preparedSQL);
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<customerBean> customersRetrieved = new ArrayList<customerBean>();
         //retrieve the information.
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
                data.setDateCreated(dbData.getTimestamp("dateCreated"));
                data.setLastEdittedBy(dbData.getString("lastEdittedBy"));
                customersRetrieved.add(data);
            }
            request.setAttribute("customersList", customersRetrieved);
            if(session.getAttribute("cart")!=null && (""+session.getAttribute("cartType")).equals("invoice")){
                request.setAttribute("addInvoice", "yes");
            }
            
            /*
            //this is for when the user will add a customer
                //its for filling in that dropdown list
            if((""+request.getParameter("forAdd")).equals("yes")){
                preparedSQL = "select * from SalesRep order by salesRepLastName asc";
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
                request.setAttribute("salesRepList", salesRepsRetrieved);
                
                
                preparedSQL = "select * from Province order by provinceName asc";
                ps = conn.prepareStatement(preparedSQL);

                dbData = ps.executeQuery();
                ArrayList<provinceBean> provsRetrieved = new ArrayList<provinceBean>();
                //retrieve the information.
                   while(dbData.next()){
                       provinceBean data = new provinceBean();
                       data.setProvinceID(dbData.getInt("provinceID"));
                       data.setProvinceName(dbData.getString("provinceName"));
                       data.setProvinceDivision(dbData.getString("provinceDivision"));
                       provsRetrieved.add(data);
                   }
                request.setAttribute("provList", provsRetrieved);
                
                
                request.getRequestDispatcher("addCustomer.jsp").forward(request,response);
            }*/
            //else{
                request.getRequestDispatcher("getCustomer.jsp").forward(request,response);
            //}
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
