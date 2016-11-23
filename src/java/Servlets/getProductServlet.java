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
@WebServlet(name = "viewProductServlet", urlPatterns = {"/viewProductServlet"})
public class getProductServlet extends HttpServlet {

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
         //THIS IS WHERE YOU START CHANGING
         String preparedSQL = "select * from Product order by productName asc";
         
         if(session.getAttribute("cart")!=null){
             context.log(preparedSQL);
             LinkedList<String> cart = (LinkedList<String>)(session.getAttribute("cart"));
             String prodIDs = "";
             context.log(""+cart.size());
             if(cart.size()>0){
                 prodIDs = " where productID!="+cart.get(0);
                 if(cart.size()>1){
                    for(int i=1;i<cart.size();i++){
                        prodIDs = prodIDs + " and productID!=" + cart.get(i);
                    }
                 }
             }
             preparedSQL = preparedSQL + prodIDs;
         };
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<productBean> productsRetrieved = new ArrayList<productBean>();
         //retrieve the information.
            while(dbData.next()){
               productBean pbean = new productBean();
                pbean.setProductID(dbData.getString("productID"));
                pbean.setProductName(dbData.getString("productName"));
                pbean.setProductDescription(dbData.getString("productDescription"));
                pbean.setProductPrice(dbData.getFloat("productPrice"));
                pbean.setRestockPrice(dbData.getFloat("restockPrice"));
                pbean.setStocksRemaining(dbData.getInt("stocksRemaining"));
                pbean.setLowStock(dbData.getInt("lowStock"));
                pbean.setBrand(dbData.getString("brand"));
                pbean.setProductClass(dbData.getString("productClass"));
                pbean.setColor(dbData.getString("color"));
                productsRetrieved.add(pbean);
            }
         request.setAttribute("productsList", productsRetrieved);
         
         String forOther = ""+ request.getParameter("forOther");
         if(forOther.equals("invoice") || session.getAttribute("cart")!=null){
             request.setAttribute("forInvoice", "yes");
         }
         else if(forOther.equals("restock")){
             request.setAttribute("forRestock", "yes");
         }
         request.getRequestDispatcher("getProduct.jsp").forward(request,response);
            
         
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
