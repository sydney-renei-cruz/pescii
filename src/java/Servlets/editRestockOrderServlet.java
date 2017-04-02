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
import javax.servlet.http.HttpSession;

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
        HttpSession session = request.getSession();
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
         String message = "Restock Order successfully editted!";
         String preparedSQL = "update RestockOrder set ROname=?,numberOfPiecesOrdered=?, "
                 + "numberOfPiecesReceived=?, amountPaid=?, purpose=?, RODateDelivered=?, "
                 + "lastEdittedBy=? "
                 + "where restockOrderID=?";
         
         int productID = Integer.parseInt(request.getParameter("productIDInput"));
         
         
         //check RO ID
         int inputRestockOrderID = 0;
         try{inputRestockOrderID = Integer.parseInt(request.getParameter("restockOrderIDInput"));}
         catch(Exception e){
                message = "Restock Oder ID is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("restockOrder.getDetails?editRestock=yes&restockID="+inputRestockOrderID).forward(request,response);
                return;
         }
         
         //check the RO name
         String newROName = request.getParameter("newRONameInput");
         try{
             newROName = request.getParameter("RONameInput");
             if(newROName.length()>255){
                message = "Restock Order Name is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("restockOrder.getDetails?editRestock=yes&restockID="+inputRestockOrderID).forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Restock Order Name is too long.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("restockOrder.getDetails?editRestock=yes&restockID="+inputRestockOrderID).forward(request,response);
                return;
            }
         
         
         //check number of pieces ordered
         int newNumberOfPiecesOrdered = 0;
         try{
             newNumberOfPiecesOrdered = Integer.parseInt(request.getParameter("numberOfPiecesOrderedInput"));
         }
         catch(Exception e){
                message = "Number of Pieces Ordered is wrong. It must be a whole number.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("restockOrder.getDetails?editRestock=yes&restockID="+inputRestockOrderID).forward(request,response);
                return;
         }
         
         
         //check number of pieces received
         int newNumberOfPiecesReceived = 0;
         try{
             newNumberOfPiecesReceived = Integer.parseInt(request.getParameter("numberOfPiecesReceivedInput"));
         }
         catch(Exception e){
                message = "Number of Pieces Received is wrong. It must be a whole number.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("restockOrder.getDetails?editRestock=yes&restockID="+inputRestockOrderID).forward(request,response);
                return;
         }
         
         
         //check amount paid
         float newAmountPaid = 0;
         try{
             newAmountPaid = Float.parseFloat(request.getParameter("amountPaidInput"));
         }
         catch(Exception e){
                message = "Amount Paid is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("restockOrder.getDetails?editRestock=yes&restockID="+inputRestockOrderID).forward(request,response);
                return;
         }
         
         
         //check purpose
         String newPurpose = request.getParameter("purposeInput");
         
         
         //check RO date delivered
         String newRODateDelivered = "";
         try{
                newRODateDelivered = request.getParameter("roDateDeliveredInput");
                if(newRODateDelivered.length()>10){
                    message = "Date Delivered format is invalid.";
                    request.setAttribute("message",message);
                request.getRequestDispatcher("restockOrder.getDetails?editRestock=yes&restockID="+inputRestockOrderID).forward(request,response);
                return;
                 }

                else if((!(newRODateDelivered.equals("")) || newRODateDelivered != null)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(newRODateDelivered);
                }
             }
             catch(Exception e){
                message = "Date Delivered format is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("restockOrder.getDetails?editRestock=yes&restockID="+inputRestockOrderID).forward(request,response);
                return;
             }
         
         String lastEdittedBy = ""+session.getAttribute("userName");
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1,newROName);
         ps.setInt(2,newNumberOfPiecesOrdered);
         ps.setInt(3,newNumberOfPiecesReceived);
         ps.setFloat(4,newAmountPaid);
         ps.setString(5,newPurpose);
         if(newRODateDelivered.equals("") || newRODateDelivered==null){ps.setString(6,null);}
         else{ps.setString(6,newRODateDelivered);}
         ps.setString(7,lastEdittedBy);
         ps.setInt(8,inputRestockOrderID);
         
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
