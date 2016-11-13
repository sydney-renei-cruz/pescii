/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.invoiceItemBean;
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
@WebServlet(name = "editRestockOrderServlet", urlPatterns = {"/editRestockOrderServlet"})
public class editRestockOrderServlet extends HttpServlet {

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
         //first get the invoice details
         //String preparedSQL = "update RestockOrder set numberOfPiecesOrdered=?, numberOfPiecesReceived=?, supplier=?, purpose=?, RODateCreated=?, RODateDelivered=?"
         //                       + "where restockOrderID=?";
         String message = "Restock Order successfully editted!";
         String preparedSQL = "update RestockOrder set numberOfPiecesOrdered=?, numberOfPiecesReceived=?, supplier=?, purpose=?, RODateDelivered=?, ROName=? where restockOrderID=?";
         
         //int restockOrderID = Integer.parseInt(request.getParameter("restockOrderIDInput"));
         int productID = Integer.parseInt(request.getParameter("productIDInput"));
         int newNumberOfPiecesOrdered = Integer.parseInt(request.getParameter("numberOfPiecesOrderedInput"));
         int newNumberOfPiecesReceived = Integer.parseInt(request.getParameter("numberOfPiecesReceivedInput"));
         String newSupplier = request.getParameter("supplierInput");
         String newPurpose = request.getParameter("purposeInput");
         String newRODateDelivered = request.getParameter("roDateDeliveredInput");
         int inputRestockOrderID = Integer.parseInt(request.getParameter("restockOrderIDInput"));
         String newROName = request.getParameter("newRONameInput");
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setInt(1,newNumberOfPiecesOrdered);
         ps.setInt(2,newNumberOfPiecesReceived);
         ps.setString(3,newSupplier);
         ps.setString(4,newPurpose);
         ps.setString(5,newRODateDelivered);
         ps.setString(6,newROName);
         ps.setInt(7,inputRestockOrderID);
         
         ps.executeUpdate();
         
         context.log("--->Restock Order successfully updated. RestockID is: "+inputRestockOrderID);
         
         if(!newRODateDelivered.equals("")){
            //now update the product if an RODateDelivered was entered

           
            preparedSQL = "update Product set stocksRemaining = stocksRemaining + "+newNumberOfPiecesReceived+" where productID=?";
            ps = conn.prepareStatement(preparedSQL);
            ps.setInt(1,productID);

            ps.executeUpdate();
            message = "Restock Order successfully editted! Inventory updated.";
         }
         
         
         
         request.setAttribute("message", message);
         request.getRequestDispatcher("homePage.jsp").forward(request,response);
         
        }
        catch(SQLException ex){
            ex.printStackTrace();
            out.println("SQL error: " + ex);
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
                out.println("Another SQL error: " + ex);
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
