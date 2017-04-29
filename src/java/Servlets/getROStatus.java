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
@WebServlet(name = "getROStatus", urlPatterns = {"/getROStatus"})
public class getROStatus extends HttpServlet {

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
         String preparedSQL = "select * from RestockOrderStatus order by statusName asc";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<restockOrderStatusBean> restockOrderStatusRetrieved = new ArrayList<restockOrderStatusBean>();
         //retrieve the information.
            while(dbData.next()){
               restockOrderStatusBean roStatBean = new restockOrderStatusBean();
               roStatBean.setStatusID(dbData.getInt("statusID"));
               roStatBean.setStatusName(dbData.getString("statusName"));
               restockOrderStatusRetrieved.add(roStatBean);
            }
         request.setAttribute("roStatList", restockOrderStatusRetrieved);
         request.setAttribute("message",request.getAttribute("message"));
         
         context.log("Now in the getROStatusServlet!");
         String cartType = ""+session.getAttribute("cartType");
         
         if(session.getAttribute("ROcart")!=null && (""+session.getAttribute("cartType")).equals("restock")){
             request.setAttribute("ROquantity", request.getAttribute("ROquantity"));
             request.setAttribute("message",request.getAttribute("message"));
             request.getRequestDispatcher("createRestockOrder.jsp").forward(request,response);
             return;
         }
         
         
         
         else if(cartType.equals("restock")){
             
         }
         context.log("edit Restock is: "+request.getAttribute("editRestock"));
         if((""+request.getAttribute("editRestock")).equals("yes")){
             request.setAttribute("restock", request.getAttribute("restock"));
             request.setAttribute("ROItemsList", request.getAttribute("ROItemsList"));
             request.setAttribute("message",request.getAttribute("message"));
             context.log("now sending to editRestockOrder.jsp!");
             request.getRequestDispatcher("editRestockOrder.jsp").forward(request,response);
         }
         
         else{// if((""+request.getAttribute("whatFor")).equals("conditionsRestockOrder")){
             context.log("going to Search RO...");
             request.getRequestDispatcher("conditionsRestockOrder.jsp").forward(request,response);
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
