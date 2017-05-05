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
@WebServlet(name = "editSalesRepServlet", urlPatterns = {"/editSalesRepServlet"})
public class editSalesRepServlet extends HttpServlet {

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
         
         String preparedSQL = "update SalesRep set salesRepFirstName=?, salesRepLastName=?, "
                 + "salesRepMobileNumber=?, salesRepAddress=?, lastEdittedBy=? "
                 + "where salesRepID=?";
         
         String message = "";
         
         //check salesrep ID
         int salesRepID = 0;
         try{salesRepID = Integer.parseInt(request.getParameter("srID"));}
         catch(Exception e){
                message = "Sales Representative ID is invalid.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
         }
         
         
         //check first name
         String newSalesRepFirstName = "";
         boolean salesRepFirstName=false;
         try{
             if(request.getParameter("newSalesRepFirstNameInput")!=null && !request.getParameter("newSalesRepFirstNameInput").equals("")){
                newSalesRepFirstName = request.getParameter("newSalesRepFirstNameInput");
                if(newSalesRepFirstName.length()>100){
                    message = "First name is too long.";
                    request.setAttribute("message",message);
                   request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                   return;
                }
                else{salesRepFirstName=true;}
             }
        }
        catch(Exception e){
            message = "First name was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
            return;
        }
         
         
         //check last name
         String newSalesRepLastName = "";
         boolean salesRepLastName=false;
         try{
             if(request.getParameter("newSalesRepLastNameInput")!=null && !request.getParameter("newSalesRepLastNameInput").equals("")){
                newSalesRepLastName = request.getParameter("newSalesRepLastNameInput");
                if(newSalesRepLastName.length()>100){
                    message = "Last name is too long.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                    return;
                }
                else{salesRepLastName=true;}
            }
        }
        catch(Exception e){
            message = "Last name was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
            return;
        }
         
         
         //check mobile number
         String newSalesRepMobileNumber = "";
         boolean salesRepMobileNumber=false;
         try{
             if(request.getParameter("newSalesRepMNInput")!=null && !request.getParameter("newSalesRepMNInput").equals("")){
                newSalesRepMobileNumber = request.getParameter("newSalesRepMNInput");
                char c;
                for(int i=0;i<newSalesRepMobileNumber.length();i++){
                   c = newSalesRepMobileNumber.charAt(i);
                   if(!Character.isDigit(c)){
                       message = "Mobile Number was input incorrectly.";
                       request.setAttribute("message",message);
                       request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                       return;
                   }
                }
                if(newSalesRepMobileNumber.length()>12){
                    message = "Mobile Number is too long.";
                    request.setAttribute("message",message);
                   request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                   return;
                }
                else{salesRepMobileNumber=true;}
                
            }
         }
         catch(Exception e){
                message = "Mobile Number was input incorrectly.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                return;
         }
         
         
         //check address
         String newSalesRepAddress = "";
         boolean salesRepAddress=false;
         try{
             if(request.getParameter("newSalesRepAddressInput")!=null && !request.getParameter("newSalesRepAddressInput").equals("")){
                newSalesRepAddress = request.getParameter("newSalesRepAddressInput");
                if(newSalesRepAddress.length()>255){
                   message = "Address is too long.";
                   request.setAttribute("message",message);
                   request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
                   return;
                }
                else{salesRepAddress=true;}
            }
        }
        catch(Exception e){
            message = "Address was input incorrectly. It should also not be blank.";
            request.setAttribute("message",message);
            request.getRequestDispatcher("salesrep.getDetails?srID="+salesRepID).forward(request,response);
            return;
        }
         
         
         String lastEdittedBy = ""+session.getAttribute("userName");
         preparedSQL = "update SalesRep set salesRepFirstName=?, salesRepLastName=?, "
                 + "salesRepMobileNumber=?, salesRepAddress=?, lastEdittedBy=? "
                 + "where salesRepID=?";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         if(salesRepFirstName==true){
             preparedSQL = "update SalesRep set salesRepFirstname=? where salesRepID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newSalesRepFirstName);
             ps.setInt(2,salesRepID);
             ps.executeUpdate();
             context.log("updated the salesRepFirstName");
         }
         if(salesRepLastName==true){
             preparedSQL = "update SalesRep set salesRepLastname=? where salesRepID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newSalesRepLastName);
             ps.setInt(2,salesRepID);
             ps.executeUpdate();
             context.log("updated the salesRepLastName");
         }
         if(salesRepMobileNumber==true){
             preparedSQL = "update SalesRep set salesRepMobileNumber=? where salesRepID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newSalesRepMobileNumber);
             ps.setInt(2,salesRepID);
             ps.executeUpdate();
             context.log("updated the salesRepMobileNumber");
         }
         if(salesRepAddress==true){
             preparedSQL = "update SalesRep set salesRepAddress=? where salesRepID=?";
             ps = conn.prepareStatement(preparedSQL);
             ps.setString(1,newSalesRepAddress);
             ps.setInt(2,salesRepID);
             ps.executeUpdate();
             context.log("updated the salesRepAddress");
         }
        
        preparedSQL = "update SalesRep set lastEdittedBy=? where salesRepID=?";
        ps = conn.prepareStatement(preparedSQL);
        ps.setString(1,lastEdittedBy);
        ps.setInt(2,salesRepID);
        ps.executeUpdate();
        context.log("updated the salesRep lastEdittedBy!");
         
         /*
         ps.setString(1,newSalesRepFirstName);
         ps.setString(2,newSalesRepLastName);
         ps.setString(3,newSalesRepMobileNumber);
         ps.setString(4,newSalesRepAddress);
         ps.setString(5,lastEdittedBy);
         ps.setInt(6,salesRepID);
         
         ps.executeUpdate();
         */
         message = "Sales Rep successfully editted!";
         request.setAttribute("message", message);
         request.setAttribute("srID", salesRepID);
         request.getRequestDispatcher("anotherSalesRep.jsp").forward(request,response);
         
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
