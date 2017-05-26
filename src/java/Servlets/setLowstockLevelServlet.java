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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
@WebServlet(name = "setLowstockLevelServlet", urlPatterns = {"/setLowstockLevelServlet"})
public class setLowstockLevelServlet extends HttpServlet {

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
        String toCancel = ""+request.getParameter("cancel");
        context.log("toCancel = "+toCancel);
        String message = "Low stock levels have been set!";
        if(toCancel.equals("yes")){    //this is for when the invoice is cancelled. It was put here to save space...I guess.
            message = "Low stock level setting cancelled.";
            request.setAttribute("message", message);
            session.setAttribute("prodCart", null);
            session.setAttribute("cartType", null);
            request.getRequestDispatcher("notif.get").forward(request,response);
            return;
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
        //processRequest(request, response);
        
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        try {
         Class.forName(context.getInitParameter("jdbcDriver"));
        } catch(ClassNotFoundException ex) {
         ex.printStackTrace();
         out.println("jdbc error: " + ex);
      }
        
        Connection conn = null;
        Statement stmt = null;
        
        try{
            
         conn = DriverManager.getConnection(context.getInitParameter("databaseUrl"), context.getInitParameter("databaseUser"), context.getInitParameter("databasePassword"));
        
         //Allocate a Statement object within the Connection
         stmt = conn.createStatement();
         
         //---------------
         //THIS IS WHERE YOU START CHANGING
         
        String toCancel = ""+request.getParameter("cancel");
        context.log("toCancel = "+toCancel);
        String message = "Low stock levels have been set!";
        if(toCancel.equals("yes")){    //this is for when the invoice is cancelled. It was put here to save space...I guess.
            message = "Low stock level setting cancelled.";
            request.setAttribute("message", message);
            session.setAttribute("prodCart", null);
            session.setAttribute("cartType", null);
            request.getRequestDispatcher("notif.get").forward(request,response);
            return;
         }
         else{
         context.log("not cancelling lowstock level setting!");
         String preparedSQL = "update Product set lowStock=?, lastEdittedBy=? "
                 + "where productID=?";
         //you don't change this
         //PreparedStatement rs = conn.prepareStatement(preparedSQL);
         
          //this is put at the start because it needs to cancel immediately if there are no products selected
            LinkedList<productBean> cart;
            
            if(session.getAttribute("prodCart")!=null && (""+session.getAttribute("cartType")).equals("lowstockLevel")){
               context.log("so the prodCart isn't null");
               cart = (LinkedList<productBean>)(session.getAttribute("prodCart"));
            }
            else{
                message = "You have no products selected. Low stock level could not be set";
                request.setAttribute("message", message);
                return;
            }

            PreparedStatement ps;
         
        //check low stock
         int newLowStock = 0;
         try{
                newLowStock = Integer.parseInt(request.getParameter("newLowstockLevel"));
                if(newLowStock < 0){
                    message = "Low Stock level was input incorrectly. It should also not be blank or negative.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("viewProdCart.jsp").forward(request,response);
                    return;
                }
         }
         catch(Exception e){
                message = "Low Stock level was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("viewProdCart.jsp").forward(request,response);
                return;
        }
            
         
         String lastEdittedBy = ""+session.getAttribute("userName");
         context.log("now setting the lowstock level...");
         for(int i=0;i<cart.size();i++){
            ps = conn.prepareStatement(preparedSQL);
            ps.setInt(1, newLowStock);
            ps.setString(2,lastEdittedBy);
            ps.setInt(3,Integer.parseInt(cart.get(i).getProductID()));
            ps.executeUpdate();                   //at this point, you have already inserted into the database
            context.log(preparedSQL);
         }
         
            message = "Low stock successfully set!";
            request.setAttribute("message", message);
            session.setAttribute("prodCart", null);
            
            session.setAttribute("cartType", null);
            request.getRequestDispatcher("notif.get").forward(request,response);

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
