/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.invoiceStatusBean;
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
@WebServlet(name = "getInvoiceStatusServlet", urlPatterns = {"/getInvoiceStatusServlet"})
public class getInvoiceStatusServlet extends HttpServlet {

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
         String preparedSQL = "select * from InvoiceStatus order by statusName asc";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         String message = "";
         ResultSet dbData = ps.executeQuery();
         ArrayList<invoiceStatusBean> invoiceStatusRetrieved = new ArrayList<invoiceStatusBean>();
         //retrieve the information.
            while(dbData.next()){
               invoiceStatusBean invStatBean = new invoiceStatusBean();
               invStatBean.setStatusID(dbData.getInt("statusID"));
               invStatBean.setStatusName(dbData.getString("statusName"));
               invoiceStatusRetrieved.add(invStatBean);
            }
         request.setAttribute("invStatList", invoiceStatusRetrieved);
         
         
         if(session.getAttribute("cart")!=null && (""+session.getAttribute("cartType")).equals("invoice")){
             
             request.setAttribute("customer", request.getAttribute("customer"));
             request.setAttribute("clinicsList", request.getAttribute("clinicsList"));
             request.setAttribute("message",request.getAttribute("message"));
             
           if((session.getAttribute("accountType")+"").equals("1") || (session.getAttribute("accountType")+"").equals("2")|| (session.getAttribute("accountType")+"").equals("3")|| (session.getAttribute("accountType")+"").equals("6")){
               request.getRequestDispatcher("addInvoice.jsp").forward(request,response);
               return;
           }
           else{
               message = "You do not have permission to perform that function.";
               request.setAttribute("message", message);
               request.getRequestDispatcher("notif.get").forward(request,response);
               return;
           }
             
         }
         context.log("editInvoice issss: "+request.getAttribute("editInvoice"));
         if((""+request.getAttribute("editInvoice")).equals("yes")){
             request.setAttribute("invoice", request.getAttribute("invoice"));
             request.setAttribute("invitemsList", request.getAttribute("invitemsList"));
             request.setAttribute("message",request.getAttribute("message"));
             context.log("now sending to editInvoice.jsp!");
             
            if((session.getAttribute("accountType")+"").equals("1") || (session.getAttribute("accountType")+"").equals("2")|| (session.getAttribute("accountType")+"").equals("3")|| (session.getAttribute("accountType")+"").equals("6")){
               request.getRequestDispatcher("editInvoice.jsp").forward(request,response);
            }
            else{
                message = "You do not have permission to perform that function.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("notif.get").forward(request,response);
                return;
            }
             
         }
         else if((""+request.getAttribute("whatFor")).equals("conditionsInvoice")){
             request.setAttribute("provList", request.getAttribute("provList"));
             
            if((session.getAttribute("accountType")+"").equals("1") || (session.getAttribute("accountType")+"").equals("2")|| (session.getAttribute("accountType")+"").equals("3")|| (session.getAttribute("accountType")+"").equals("6")){
               request.getRequestDispatcher("conditionsInvoice.jsp").forward(request,response);
            }
            else{
                message = "You do not have permission to perform that function.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("notif.get").forward(request,response);
                return;
            }
             
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
