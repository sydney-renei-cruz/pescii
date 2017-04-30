/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.salesRepBean;
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
@WebServlet(name = "getSalesRepServlet", urlPatterns = {"/getSalesRepServlet"})
public class getSalesRepServlet extends HttpServlet {

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
        String message="";
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
         String preparedSQL = "select * from SalesRep order by salesRepLastName asc";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         context.log(preparedSQL);
         ResultSet dbData = ps.executeQuery();
         ArrayList<salesRepBean> salesRepsRetrieved = new ArrayList<salesRepBean>();
         //retrieve the information.
            while(dbData.next()){
               salesRepBean srbean = new salesRepBean();
                srbean.setSalesRepID(dbData.getInt("salesRepID"));
                srbean.setSalesRepFirstName(dbData.getString("salesRepFirstName"));
                srbean.setSalesRepLastName(dbData.getString("salesRepLastName"));
                srbean.setSalesRepMobileNumber(dbData.getString("salesRepMobileNumber"));
                srbean.setSalesRepAddress(dbData.getString("salesRepAddress"));
                salesRepsRetrieved.add(srbean);
            }
         request.setAttribute("salesRepsList", salesRepsRetrieved);
         
         String whatFor=""+request.getParameter("whatFor");
         if((""+request.getParameter("whatFor")).equals("searchCustomer")){
             context.log("getting salesReps for searchCustomer...");
             request.setAttribute("whatFor","searchCustomer");
             request.getRequestDispatcher("province.get").forward(request,response);
         }
         else if(whatFor.equals("addCustomer")){
             context.log("getting salesReps for addCustomer...");
             request.setAttribute("whatFor","addCustomer");
             request.setAttribute("message",""+request.getAttribute("message"));
             request.getRequestDispatcher("province.get").forward(request,response);
         }
         else{
            request.getRequestDispatcher("getSalesRep.jsp").forward(request,response);
         }
        }
        catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            message = "Something went wrong. Please try again or contact the administrator.";
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
                message = "Something went wrong. Please try again or contact the administrator.";
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
