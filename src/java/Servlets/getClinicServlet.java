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

/**
 *
 * @author user
 */
@WebServlet(name = "getClinicServlet", urlPatterns = {"/getClinicServlet"})
public class getClinicServlet extends HttpServlet {

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
        
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        context.log("HEEEEEERE!!!!!");
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
         String inputClinID = request.getParameter("clinID");
         String preparedSQL = "select Clinic.clinicID, Customer.customerID, Customer.PRCID, Clinic.clinicAddress, Clinic.clinicPhoneNumber, Clinic.clinicName, "
                 + "Province.provinceName, Province.provinceDivision, Clinic.provinceID, Clinic.dateCreated, Clinic.lastEdittedBy, "
                 + "Clinic.customerID, Customer.customerFirstName, Customer.customerLastName "
                 + "from Clinic "
                 + "inner join Customer on Customer.customerID = Clinic.customerID "
                 + "inner join Province on Province.provinceID = Clinic.provinceID and clinicID=? "
                 + "order by Clinic.clinicName";
          PreparedStatement ps = conn.prepareStatement(preparedSQL);
          if(inputClinID!=null && !inputClinID.equals("")){
            context.log("there was a clinic ID!");
            preparedSQL = "select Clinic.clinicID, Customer.customerID, Customer.PRCID, Clinic.clinicAddress, Clinic.clinicPhoneNumber, Clinic.clinicName, "
                 + "Province.provinceName, Province.provinceDivision, Clinic.provinceID, Clinic.dateCreated, Clinic.lastEdittedBy, "
                 + "Clinic.customerID, Customer.customerFirstName, Customer.customerLastName "
                 + "from Clinic "
                 + "inner join Customer on Customer.customerID = Clinic.customerID "
                 + "inner join Province on Province.provinceID = Clinic.provinceID and clinicID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setString(1, inputClinID);
          }
         context.log(preparedSQL);

        ResultSet dbData = ps.executeQuery();
        ArrayList<clinicBean> clinicsRetrieved = new ArrayList<clinicBean>();
        while(dbData.next()){
            clinicBean clinbean = new clinicBean();
            clinbean.setClinicID(dbData.getString("clinicID"));
            clinbean.setPRCID(dbData.getString("PRCID"));
            clinbean.setCustomerID(dbData.getString("customerID"));
            clinbean.setCustomerFirstName(dbData.getString("customerFirstName"));
            clinbean.setCustomerLastName(dbData.getString("customerLastName"));
            clinbean.setClinicAddress(dbData.getString("clinicAddress"));
            clinbean.setClinicPhoneNumber(dbData.getString("clinicPhoneNumber"));
            clinbean.setClinicName(dbData.getString("clinicName"));
            clinbean.setProvinceID(dbData.getInt("provinceID"));
            clinbean.setProvinceName(dbData.getString("provinceName"));
            clinbean.setProvinceDivision(dbData.getString("provinceDivision"));
            clinbean.setDateCreated(dbData.getTimestamp("dateCreated"));
            clinbean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
            clinicsRetrieved.add(clinbean);
        }
            
         request.setAttribute("clinicsList", clinicsRetrieved);
         
         
        if(inputClinID!=null && !inputClinID.equals("")){
            clinicBean clinic = clinicsRetrieved.get(0);
            request.setAttribute("clinic",clinic);
            preparedSQL = "select * from Province order by provinceName asc";
            ps = conn.prepareStatement(preparedSQL);

            dbData = ps.executeQuery();
            ArrayList<provinceBean> provincesRetrieved = new ArrayList<provinceBean>();
            //retrieve the information.
            while(dbData.next()){
               provinceBean provBean = new provinceBean();
               provBean.setProvinceID(dbData.getInt("provinceID"));
               provBean.setProvinceName(dbData.getString("provinceName"));
               provBean.setProvinceDivision(dbData.getString("provinceDivision"));
               provincesRetrieved.add(provBean);
            }
            context.log("province list size is: "+provincesRetrieved.size());
            request.setAttribute("provList", provincesRetrieved);
            context.log("going to editClinic.jsp...");
            request.getRequestDispatcher("editClinic.jsp").forward(request,response);
         }
         else{
             context.log("going to getClinic.jsp...");
             request.getRequestDispatcher("getClinic.jsp").forward(request,response);
         }
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
