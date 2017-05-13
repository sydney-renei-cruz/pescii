/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pescii.controller.page.modal.json;

import Beans.invoiceItemBean;
import Beans.restockOrderBean;
import Beans.restockOrderItemBean;
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
public class getRestockOrderItemsServlet extends HttpServlet {

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
            
            String restockID = request.getParameter("restockIDInput");
   
            inText = "select RestockOrderItem.ROIID, RestockOrderItem.RestockOrderID, Product.productID, "
                 + "Product.productName, RestockOrderItem.quantityPurchased, RestockOrderItem.quantityReceived, "
                 + "Product.restockPrice "
                 + "from RestockOrderItem "
                 + "inner join Product on Product.productID = RestockOrderItem.productID "
                 + "inner join RestockOrder on RestockOrder.RestockOrderID = RestockOrderItem.RestockOrderID "
                 + "where RestockOrder.restockOrderID = ?";
            ps = conn.prepareStatement(inText);
            ps.setString(1, restockID);
            rs = ps.executeQuery();
            
            ArrayList<restockOrderItemBean> al = new ArrayList<>();
            restockOrderItemBean rob;
            while(rs.next()){
                rob = new restockOrderItemBean();
                rob.setRestockOrderItemID(rs.getInt("ROIID"));
                rob.setRestockOrderID(rs.getInt("RestockOrderID"));
                rob.setProductID(rs.getInt("productID"));
                rob.setProductName(rs.getString("productName"));
                rob.setQuantityPurchased(rs.getInt("quantityPurchased"));
                rob.setQuantityReceived(rs.getInt("quantityReceived"));
                rob.setTotalCost(rs.getInt("quantityPurchased") * rs.getFloat("restockPrice"));
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
