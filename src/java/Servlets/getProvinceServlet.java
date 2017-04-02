/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.provinceBean;
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
@WebServlet(name = "getProvinceServlet", urlPatterns = {"/getProvinceServlet"})
public class getProvinceServlet extends HttpServlet {

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
         String preparedSQL = "select * from Province order by provinceName asc";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<provinceBean> provincesRetrieved = new ArrayList<provinceBean>();
         //retrieve the information.
            while(dbData.next()){
               provinceBean provBean = new provinceBean();
               provBean.setProvinceID(dbData.getInt("provinceID"));
               provBean.setProvinceName(dbData.getString("provinceName"));
               provBean.setProvinceDivision(dbData.getString("provinceDivision"));
               provincesRetrieved.add(provBean);
            }
         request.setAttribute("provList", provincesRetrieved);
         
         
         String whatFor = "" + request.getParameter("whatFor");
         if(request.getParameter("whatFor")==null){
             context.log("so there was a misinput, huh?");
             whatFor = "" + request.getAttribute("whatFor");
         }
         if(whatFor.equals("addClinic")){
            String customerID = ""+request.getParameter("custID");
            if(request.getParameter("custID")==null){
                customerID = "" + request.getAttribute("custID");
                context.log("customer ID from getProv is "+customerID);
                request.setAttribute("message", request.getAttribute("message"));
            }
            request.setAttribute("custID", customerID);
            context.log("2nd to final custID is "+customerID);
            request.getRequestDispatcher("addClinic.jsp").forward(request,response);
         }
         else if(whatFor.equals("conditionsInvoice")){
             request.setAttribute("whatFor", "conditionsInvoice");
             request.getRequestDispatcher("invoice.getStatus").forward(request,response);
         }
         else if(whatFor.equals("searchCustomer")){
             context.log("sending provinces and salesReps to conditionsCustomer...");
             request.setAttribute("salesRepsList", request.getAttribute("salesRepsList"));
             request.getRequestDispatcher("conditionsCustomer.jsp").forward(request,response);
         }
         else if(whatFor.equals("addCustomer")){
             context.log("sending provinces and salesReps to conditionsCustomer...");
             request.setAttribute("salesRepsList", request.getAttribute("salesRepsList"));
             request.setAttribute("message",""+request.getAttribute("message"));
             request.getRequestDispatcher("addCustomer.jsp").forward(request,response);
         }
         
         
         
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
