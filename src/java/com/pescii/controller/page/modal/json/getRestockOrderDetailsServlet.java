/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pescii.controller.page.modal.json;

import Beans.invoiceBean;
import Beans.restockOrderBean;
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
public class getRestockOrderDetailsServlet extends HttpServlet {

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
            
            String roID = request.getParameter("restockIDInput");
   
            inText = "select RestockOrder.*, RestockOrderStatus.statusName, Supplier.supplierName from "
                 + "RestockOrder "
                 + "inner join RestockOrderStatus on RestockOrderStatus.statusID=RestockOrder.statusID "
                 + "inner join Supplier on Supplier.supplierID=RestockOrder.supplierID "
                 + "where restockOrderID=?";
            ps = conn.prepareStatement(inText);
            ps.setString(1, roID);
            rs = ps.executeQuery();
            
            ArrayList<restockOrderBean> al = new ArrayList<>();
            restockOrderBean rob;
            while(rs.next()){
                rob = new restockOrderBean();
                rob.setRestockOrderID(rs.getInt("restockOrderID"));
                rob.setRestockOrderName(rs.getString("ROName"));
                rob.setStatusID(rs.getInt("statusID"));
                rob.setStatusName(rs.getString("statusName"));
                rob.setSupplierID(rs.getInt("supplierID"));
                rob.setSupplierName(rs.getString("supplierName"));
                rob.setPurpose(rs.getString("purpose"));
                rob.setRODateDue(rs.getDate("RODateDue"));
                rob.setRODateDelivered(rs.getDate("RODateDelivered"));
                rob.setRestockPrice(rs.getFloat("restockPrice"));
                rob.setAmountPaid(rs.getFloat("amountPaid"));
                rob.setDiscount(rs.getFloat("discount"));
                rob.setDatePaid(rs.getDate("datePaid"));
                rob.setDateCreated(rs.getTimestamp("dateCreated"));
                rob.setLastEdittedBy(rs.getString("lastEdittedBy"));
                al.add(rob);
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
