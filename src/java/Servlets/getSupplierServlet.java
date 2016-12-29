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

/**
 *
 * @author user
 */
@WebServlet(name = "getSupplierServlet", urlPatterns = {"/getSupplierServlet"})
public class getSupplierServlet extends HttpServlet {

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
         String preparedSQL = "select Supplier.supplierID, Supplier.supplierName, Supplier.supplierAddress, "
                 + "Supplier.supplierContactNumber, Supplier.ProductClassID, ProductClass.productClassID, "
                 + "ProductClass.productClassName, Supplier.dateCreated, Supplier.lastEdittedBy from Supplier "
                 + "inner join ProductClass on ProductClass.productClassID=Supplier.productClassID "
                 + "order by supplierName asc";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         ResultSet dbData = ps.executeQuery();
         ArrayList<supplierBean> suppliersRetrieved = new ArrayList<supplierBean>();
         //retrieve the information.
            while(dbData.next()){
               supplierBean suppbean = new supplierBean();
                suppbean.setSupplierID(dbData.getInt("supplierID"));
                suppbean.setSupplierName(dbData.getString("supplierName"));
                suppbean.setSupplierAddress(dbData.getString("supplierAddress"));
                suppbean.setSupplierContactNumber(dbData.getString("supplierContactNumber"));
                suppbean.setProductClassID(dbData.getInt("productClassID"));
                suppbean.setProductClassName(dbData.getString("productClassName"));
                suppbean.setDateCreated(dbData.getTimestamp("dateCreated"));
                suppbean.setLastEdittedBy(dbData.getString("lastEdittedBy"));
                suppliersRetrieved.add(suppbean);
            }
         request.setAttribute("suppliersList", suppliersRetrieved);
         
         request.setAttribute("prodClassList", request.getAttribute("prodClassList"));
         context.log("FOREDIT EQUAAAAALS: " + request.getAttribute("forEdit"));
         if((""+request.getAttribute("forEdit")).equals("yes")){
             //request.setAttribute("forEdit", "yes");
             request.setAttribute("product", request.getAttribute("product"));
             request.setAttribute("productClassList", request.getAttribute("productClassList"));
             request.getRequestDispatcher("editProduct.jsp").forward(request,response);
         }
         else if(!(""+request.getAttribute("searchWhat")).equals("")){
             context.log("getting suppliers for search Product...");
             String searchWhat = ""+request.getAttribute("searchWhat");
             request.setAttribute("prodClassList", request.getAttribute("prodClassList"));
             if(searchWhat.equalsIgnoreCase("prod")){request.getRequestDispatcher("conditionsProduct.jsp").forward(request,response);}
             else if(searchWhat.equalsIgnoreCase("ro")){request.getRequestDispatcher("conditionsRestockOrder.jsp").forward(request,response);}
         }
         else{
            request.getRequestDispatcher("addProduct.jsp").forward(request,response);
         }   
         
        }
        catch(Exception ex){
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
