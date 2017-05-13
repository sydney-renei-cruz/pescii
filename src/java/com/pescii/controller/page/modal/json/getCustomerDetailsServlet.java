/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pescii.controller.page.modal.json;

import Beans.customerBean;
import Beans.productBean;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sydney Cruz
 */
public class getCustomerDetailsServlet extends HttpServlet {

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
        Connection conn = null;
        PreparedStatement ps = null;
        ServletContext context = request.getSession().getServletContext();
        ResultSet rs = null;
        String inText = "";
        HttpSession session = request.getSession();
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(context.getInitParameter("databaseUrl"), context.getInitParameter("databaseUser"), context.getInitParameter("databasePassword"));
            
            String customerID = request.getParameter("customerIDInput");
   
            inText = "select Customer.customerID, Customer.PRCID, "
                 + "Customer.customerFirstName, Customer.customerLastName, "
                 + "Customer.customerMobileNumber, "
                 + "Customer.customerTelephoneNumber, SalesRep.salesRepFirstName, "
                 + "SalesRep.salesRepLastName, Customer.salesRepID, Customer.dateCreated, "
                 + "Customer.lastEdittedBy "
                 + "from Customer "
                 + "inner join SalesRep on SalesRep.salesRepID = Customer.salesRepID "
                 + "where Customer.customerID=? "
                 + "order by Customer.customerLastName asc ";
            ps = conn.prepareStatement(inText);
            ps.setString(1, customerID);
            rs = ps.executeQuery();
            
            ArrayList<customerBean> al = new ArrayList<>();
            customerBean cb;
            while(rs.next()){
                cb = new customerBean();
                cb.setCustomerID(rs.getInt("customerID"));
                cb.setPRCID(rs.getString("PRCID"));
                cb.setCustomerFirstName(rs.getString("customerFirstName"));
                cb.setCustomerLastName(rs.getString("customerLastName"));
                cb.setCustomerMobileNumber(rs.getString("customerMobileNumber"));
                cb.setCustomerTelephoneNumber(rs.getString("customerTelephoneNumber"));
                cb.setSalesRep(rs.getString("salesRepLastName")+", "+rs.getString("salesRepFirstName"));
                cb.setSalesRepID(rs.getInt("salesRepID"));
                cb.setDateCreated(rs.getTimestamp("dateCreated"));
                cb.setLastEdittedBy(rs.getString("lastEdittedBy"));
                al.add(cb);
            }
            String json = new Gson().toJson(al);
            response.setContentType("application/json");
            out.print(json);
        }catch(Exception e){
            e.printStackTrace();
            context.log("Exception: " + e);
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
