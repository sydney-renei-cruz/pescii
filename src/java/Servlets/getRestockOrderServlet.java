/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.restockOrderBean;
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
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class getRestockOrderServlet extends HttpServlet {

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
         //THIS IS WHERE YOU START CHANGING
         String preparedSQL = "Select RestockOrder.restockOrderID, Product.productID, Product.productName, Product.restockPrice, RestockOrder.numberOfPiecesOrdered, "
                    + "RestockOrder.numberOfPiecesReceived, RestockOrder.supplier, RestockOrder.purpose, RestockOrder.RODateDue, "
                    + "RestockOrder.RODateDelivered, RestockOrder.ROName "
                    + "from RestockOrder "
                    + "inner join Product on Product.productID = RestockOrder.productID "
                    + "order by RestockOrder.RODateDue desc";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<restockOrderBean> restocksRetrieved = new ArrayList<restockOrderBean>();
         //retrieve the information.
            while(dbData.next()){
               restockOrderBean rbean = new restockOrderBean();
                rbean.setRestockOrderID(dbData.getInt("restockOrderID"));
                rbean.setRestockOrderName(dbData.getString("ROName"));
                rbean.setProductID(dbData.getInt("productID"));
                rbean.setProductName(dbData.getString("productName"));
                rbean.setNumberOfPiecesOrdered(dbData.getInt("numberOfPiecesOrdered"));
                rbean.setNumberOfPiecesReceived(dbData.getInt("numberOfPiecesReceived"));
                rbean.setSupplier(dbData.getString("supplier"));
                rbean.setPurpose(dbData.getString("purpose"));
                rbean.setRODateDue(dbData.getDate("RODateDue"));
                rbean.setRODateDelivered(dbData.getDate("RODateDelivered"));
                rbean.setRestockPrice(dbData.getFloat("restockPrice"));
                restocksRetrieved.add(rbean);
            }
         request.setAttribute("restocksList", restocksRetrieved);
         
         request.getRequestDispatcher("getRestockOrder.jsp").forward(request,response);
            
         
        }
        catch(Exception ex){
            ex.printStackTrace();
            out.println("error: " + ex);
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
