/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pescii.controller.page.modal.json;

import Beans.clinicBean;
import Beans.invoiceBean;
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
public class getCustomerInvoiceDetailsServlet extends HttpServlet {

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
   
            inText = "select Invoice.invoiceID, Invoice.invoiceName, Customer.PRCID, "
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
                 + "where Invoice.customerID = ?";
            ps = conn.prepareStatement(inText);
            ps.setString(1, customerID);
            rs = ps.executeQuery();
            
            ArrayList<invoiceBean> al = new ArrayList<>();
            invoiceBean ib;
            while(rs.next()){
                ib = new invoiceBean();
                ib.setInvoiceID(rs.getInt("invoiceID"));
                ib.setInvoiceName(rs.getString("invoiceName"));
                ib.setPRCID(rs.getString("PRCID"));
                ib.setCustomerName(rs.getString("customerLastName")+", "+rs.getString("customerFirstName"));
                ib.setClinicID(rs.getInt("clinicID"));
                ib.setClinicName(rs.getString("clinicName"));
                ib.setProvinceName(rs.getString("provinceName"));
                ib.setProvinceDivision(rs.getString("provinceDivision"));
                ib.setInvoiceDate(rs.getDate("invoiceDate"));
                ib.setDeliveryDate(rs.getDate("deliveryDate"));
                ib.setTermsOfPayment(rs.getString("termsOfPayment"));
                ib.setPaymentDueDate(rs.getDate("paymentDueDate"));
                if(!(""+rs.getDate("dateClosed")).equals("0000-00-00")){ib.setDateClosed(rs.getDate("dateClosed"));}
                if(!(""+rs.getDate("datePaid")).equals("0000-00-00")){ib.setDatePaid(rs.getDate("datePaid"));}
                ib.setStatusID(rs.getInt("statusID"));
                ib.setStatusName(rs.getString("statusName"));
                ib.setOverdueFee(rs.getFloat("overdueFee"));
                ib.setAmountDue(rs.getFloat("amountDue"));
                ib.setAmountPaid(rs.getFloat("amountPaid"));
                ib.setDiscount(rs.getFloat("discount"));
                ib.setDateDelivered(rs.getDate("dateDelivered"));
                ib.setDateCreated(rs.getTimestamp("dateCreated"));
                ib.setLastEdittedBy(rs.getString("lastEdittedBy"));
                al.add(ib);
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
