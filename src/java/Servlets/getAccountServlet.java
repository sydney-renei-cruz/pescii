/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.accountBean;
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
@WebServlet(name = "getAccountServlet", urlPatterns = {"/getAccountServlet"})
public class getAccountServlet extends HttpServlet {

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
         String accountType = ""+session.getAttribute("accountType");
         String preparedSQL = "select * from Account";
         if(accountType.equals("2") || accountType.equals("1")){
             preparedSQL = "select Account.accountID, Account.userName, Account.password, Account.dateCreated, "
                     + "AccountStatus.accountStatusName, AccountType.accountTypeName from Account "
                     + "inner join AccountStatus on AccountStatus.accountStatusID = Account.accountStatus "
                     + "inner join AccountType on AccountType.accountTypeID = Account.accountType";
         }
         else{
             String accountID = ""+session.getAttribute("accountID");
             //preparedSQL = "select * from Account where accountID="+accountID;
             preparedSQL = "select Account.accountID, Account.userName, Account.password, Account.dateCreated, "
                     + "AccountStatus.accountStatusName, AccountType.accountTypeName from Account "
                     + "inner join AccountStatus on AccountStatus.accountStatusID = Account.accountStatus "
                     + "inner join AccountType on AccountType.accountTypeID = Account.accountType "
                     + "where accountID="+accountID;
         }
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<accountBean> accountsRetrieved = new ArrayList<accountBean>();
         //retrieve the information.
            while(dbData.next()){
               accountBean abean = new accountBean();
                abean.setAccountID(dbData.getInt("accountID"));
                abean.setUserName(dbData.getString("userName"));
                abean.setPassword(dbData.getString("password"));
                abean.setAccountStatus(dbData.getString("accountStatusName"));
                abean.setAccountType(dbData.getString("accountTypeName"));
                //abean.setDateCreated(dbData.getTimestamp("dateCreated"));
                abean.setDateCreated(dbData.getTimestamp("dateCreated"));
                accountsRetrieved.add(abean);
            }
         request.setAttribute("accountsList", accountsRetrieved);
         
         request.getRequestDispatcher("getAccount.jsp").forward(request,response);
            
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
