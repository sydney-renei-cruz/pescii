/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pescii.controller.page.modal.json.page;

import Beans.invoiceItemBean;
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
public class getCustomerInvoiceItems extends HttpServlet {

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
            
            String invoiceID = request.getParameter("invoiceIDInput");
   
            inText = "select InvoiceItem.invoiceItemID, InvoiceItem.invoiceID, Product.productID, "
                 + "Product.productName, InvoiceItem.quantityPurchased, Product.productPrice "
                 + "from InvoiceItem "
                 + "inner join Product on Product.productID = InvoiceItem.productID "
                 + "inner join Invoice on Invoice.invoiceID = InvoiceItem.invoiceID "
                 + "where Invoice.invoiceID = ?";
            ps = conn.prepareStatement(inText);
            ps.setString(1, invoiceID);
            rs = ps.executeQuery();
            
            ArrayList<invoiceItemBean> al = new ArrayList<>();
            invoiceItemBean iib;
            while(rs.next()){
                iib = new invoiceItemBean();
                iib.setInvoiceItemID(rs.getInt("invoiceItemID"));
                iib.setInvoiceID(rs.getInt("invoiceID"));
                iib.setProductID(rs.getInt("productID"));
                iib.setProductName(rs.getString("productName"));
                iib.setQuantityPurchased(rs.getInt("quantityPurchased"));
                iib.setTotalCost(rs.getInt("quantityPurchased") * rs.getFloat("productPrice"));
                al.add(iib);
            }
            request.setAttribute("al", al);
            request.getRequestDispatcher("/WEB-INF/modal-content/invoice-items.jsp").forward(request, response);
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
