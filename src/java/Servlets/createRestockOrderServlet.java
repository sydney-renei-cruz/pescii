/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "createRestockOrderServlet", urlPatterns = {"/createRestockOrderServlet"})
public class createRestockOrderServlet extends HttpServlet {

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
         
         String preparedSQL = "insert into RestockOrder(productID, numberOfPiecesOrdered, supplier, purpose, RODateDue, ROName) values(?,?,?,?,?,?)";
         preparedSQL = "insert into RestockOrder(productID, ROName, numberOfPiecesOrdered, supplierID, "
                 + "purpose, RODateDue, discount, lastEdittedBy) "
                 + "values(?,?,?,?,?,?,?,?)";
             
         //you don't change this
         PreparedStatement rs = conn.prepareStatement(preparedSQL);
         context.log("The productID is ----> " + request.getParameter("pid"));
         
         String message = "";
         
         //check product ID
         int inputProductID = 0;
         try{inputProductID = Integer.parseInt(request.getParameter("pid"));}
         catch(Exception e){
                message = "Restock D is invalid.";
                request.setAttribute("message",message);
                request.setAttribute("forRestock", "yes");
                request.setAttribute("prodID",inputProductID);
                request.getRequestDispatcher("product.getDetails").forward(request,response);
                return;
         }
         
         //check RO name
         String inputROName;
         try{
             inputROName = request.getParameter("RONameInput");
             if(inputROName.length()>255){
                 message = "Restock Order Name is too long.";
                 request.setAttribute("message",message);
                 request.setAttribute("forRestock", "yes");
                request.setAttribute("prodID",inputProductID);
                request.getRequestDispatcher("product.getDetails").forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "Restock Order Name is too long.";
                request.setAttribute("message",message);
                request.setAttribute("prodID",inputProductID);
                request.setAttribute("forRestock", "yes");
                request.getRequestDispatcher("product.getDetails").forward(request,response);
                return;
            }
         
         
         //check the number of pieces ordered
         int inputPiecesOrdered = 0;
         try{
             inputPiecesOrdered = Integer.parseInt(request.getParameter("piecesOrderedInput"));
         }
         catch(Exception e){
                message = "Number of Pieces Ordered is wrong. It must be a whole number.";
                request.setAttribute("message",message);
                request.setAttribute("prodID",inputProductID);
                request.setAttribute("forRestock", "yes");
                request.getRequestDispatcher("product.getDetails").forward(request,response);
                return;
         }
         
         
         int inputSupplier = Integer.parseInt(request.getParameter("supplierIDInput"));
         String inputPurpose = request.getParameter("purposeInput");
         
         //check date due
         String inputDateDue = "";
         Date date = new Date();
         try{
               inputDateDue = request.getParameter("dateDueInput");
                if(inputDateDue.length()>10){
                    message = "Due Date format is invalid.";
                    request.setAttribute("message",message);
                    request.setAttribute("prodID",inputProductID);
                    request.setAttribute("forRestock", "yes");
                    request.getRequestDispatcher("product.getDetails").forward(request,response);
                    return;
                 }

                else{
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //date = sdf.parse(inputDateDue);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    dateFormat.parse(inputDateDue.trim());
                }
             }
             catch(Exception e){
                message = "Due Date format is invalid.";
                request.setAttribute("message",message);
                request.setAttribute("prodID",inputProductID);
                request.setAttribute("forRestock", "yes");
                context.log(""+e);
                request.getRequestDispatcher("product.getDetails").forward(request,response);
                return;
             }
         
         //check discount
         float inputDiscount = 0;
            try{
                inputDiscount = Float.parseFloat(request.getParameter("discountInput"));
            }
            catch(Exception e){
                message = "Discount was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.setAttribute("prodID",inputProductID);
                request.setAttribute("forRestock", "yes");
                request.getRequestDispatcher("product.getDetails").forward(request,response);
                return;
            }
         
         String lastEdittedBy = ""+session.getAttribute("userName");
         
         
         rs.setInt(1,inputProductID);
         rs.setString(2,inputROName);
         rs.setInt(3,inputPiecesOrdered);
         rs.setInt(4,inputSupplier);
         rs.setString(5,inputPurpose);
         rs.setString(6,inputDateDue);
         rs.setFloat(7,inputDiscount);
         rs.setString(8,lastEdittedBy);
         
         rs.executeUpdate();                   //at this point, you have already inserted into the database
         
         message = "Restock Order successfully created!";
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
