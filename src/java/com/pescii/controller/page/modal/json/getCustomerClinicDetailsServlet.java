/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pescii.controller.page.modal.json;

import Beans.clinicBean;
import Beans.customerBean;
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
public class getCustomerClinicDetailsServlet extends HttpServlet {

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
   
            inText = "select Clinic.clinicID, Customer.PRCID, Clinic.clinicAddress, "
                 + "Clinic.clinicPhoneNumber, Clinic.clinicName, Clinic.provinceID, "
                 + "Province.provinceName, Province.provinceDivision, "
                 + "Clinic.dateCreated, Clinic.lastEdittedBy "
                 + "from Clinic "
                 + "inner join Customer on Customer.customerID = Clinic.customerID "
                 + "inner join Province on Province.provinceID = Clinic.provinceID "
                 + "where Clinic.customerID = ?";
            ps = conn.prepareStatement(inText);
            ps.setString(1, customerID);
            rs = ps.executeQuery();
            
            ArrayList<clinicBean> al = new ArrayList<>();
            clinicBean cb;
            while(rs.next()){
                cb = new clinicBean();
                cb.setClinicID(rs.getString("clinicID"));
                cb.setPRCID(rs.getString("PRCID"));
                cb.setClinicAddress(rs.getString("clinicAddress"));
                cb.setClinicPhoneNumber(rs.getString("clinicPhoneNumber"));
                cb.setClinicName(rs.getString("clinicName"));
                cb.setProvinceID(rs.getInt("provinceID"));
                cb.setProvinceName(rs.getString("provinceName"));
                cb.setProvinceDivision(rs.getString("provinceDivision"));
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
