/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.productClassBean;
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
@WebServlet(name = "getProductClassServlet_1", urlPatterns = {"/getProductClassServlet_1"})
public class getProductClassServlet extends HttpServlet {

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
         String preparedSQL = "select * from ProductClass order by productClassName asc";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<productClassBean> productClassesRetrieved = new ArrayList<productClassBean>();
         //retrieve the information.
            while(dbData.next()){
               productClassBean prodClassBean = new productClassBean();
               prodClassBean.setProductClassID(dbData.getInt("productClassID"));
               prodClassBean.setProductClassName(dbData.getString("productClassName"));
               productClassesRetrieved.add(prodClassBean);
            }
         request.setAttribute("prodClassList", productClassesRetrieved);
         
         String searchWhat = ""+request.getParameter("searchWhat");
         String forOther = ""+request.getParameter("forOther");
         
         request.setAttribute("message", ""+request.getAttribute("message"));
         
         //this is for searching invoices
         if(request.getParameter("product")!=null){
             request.setAttribute("product", request.getAttribute("product"));
             request.getRequestDispatcher("conditionsInvoice.jsp").forward(request,response);
         }
         //this is for adding supplier
         else if((""+request.getParameter("addSupp")).equals("yes")){
             context.log("MADE IT TO SUPPPPP!!!");
             request.getRequestDispatcher("addSupplier.jsp").forward(request,response);
         }
         //this is for searching in general. Not sure if it's obsolete haha
         else if((""+request.getParameter("search")).equals("yes")){
             context.log("getting Product Classes for searching...");
             
             if(forOther.equals("invoice")){request.setAttribute("forOther", "invoice");}
             else if(forOther.equals("restock")){request.setAttribute("forOther", "restock");}
             
             if(searchWhat.equalsIgnoreCase("prod")){request.setAttribute("searchWhat","prod");}
             else if(searchWhat.equalsIgnoreCase("ro")){request.setAttribute("searchWhat", "ro");}
             else if(searchWhat.equalsIgnoreCase("supp")){
                request.getRequestDispatcher("conditionsSupplier.jsp").forward(request,response);
                return;
             }
             request.getRequestDispatcher("supplier.get").forward(request,response);
             return;
         }
         //this is for edit supplier
         else if((""+request.getAttribute("editSupplier")).equals("yes")){
             request.setAttribute("supplier", request.getAttribute("supplier"));
             request.getRequestDispatcher("editSupplier.jsp").forward(request,response);
         }
         //otherwise, this servlet is called for editing product(?)
         else{
             if((""+request.getAttribute("forEdit")).equals("yes")){
                 request.setAttribute("product", request.getAttribute("product"));
                 request.setAttribute("forEdit", "yes");
                 context.log("getProductClass for EDIT!!!!!");
             }
             context.log("getting Suppliers now...");
             request.setAttribute("message",request.getAttribute("message"));
            request.getRequestDispatcher("supplier.get").forward(request,response);
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
