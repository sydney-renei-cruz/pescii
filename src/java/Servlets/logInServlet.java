/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "logInServlet", urlPatterns = {"/logInServlet"})
public class logInServlet extends HttpServlet {

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
        
        RequestDispatcher rd = null;
        
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("usernameInput");
        String password = request.getParameter("passwordInput");
        
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
         stmt = conn.createStatement();
         //---------------
         String preparedSQL = "select * from Account where userName = ?";
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         ps.setString(1, username);
         ResultSet dbData = ps.executeQuery();
         String message = "";
         HttpSession session = request.getSession();
         String dbPassword = "";       
         
         //10 salts here
         String[] salts = new String[10];
         salts[0] = "7LsDFJ9oHjDnfUr12";
         salts[1] = "K8oMilIOi0ji43amS";
         salts[2] = "AFIOUVAJNONVASJja";
         salts[3] = "nVaWIdsj19Aij63df";
         salts[4] = "uahRksD47kljnJN9k";
         salts[5] = "dMna7sY01jfIoaPlY";
         salts[6] = "Wg480ioAjEdsf31Ka";
         salts[7] = "gMutRHj70ubQnjB67";
         salts[8] = "gnQiaOhfXquh82z74";
         salts[9] = "mKvqn7834wHjk1kLa";
              
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         
         
         if (!dbData.isBeforeFirst()){  //if the account couldn't be found
             //response.sendRedirect("index.jsp");
             message = "Username or password is incorrect.";
             request.setAttribute("message", message);
             request.getRequestDispatcher("logIn.jsp").forward(request, response);
             return;
         }
         
         else { //if the account is found
            dbData.next();
            dbPassword = dbData.getString("password");
         context.log("The username is -----> " + dbData.getString("userName"));
            
         }
         
            for(int i = 0; i <= 9; i++){
                String pwplaceholder = password + salts[i];
                
                md.update(pwplaceholder.getBytes());
        
                byte byteData[] = md.digest();
        
                StringBuffer sb = new StringBuffer();
        
                for(int y = 0; y < byteData.length; y++){
                    sb.append(Integer.toString((byteData[y] & 0xff) + 0x100, 16).substring(1));
                }

                context.log("password is: ----->  " + password);
                context.log("sb is: ------> " + sb.toString());
                //      correct will become TRUE if a match is found
                if(dbPassword.equals(sb.toString())){
                    //status = rs.getBoolean("account_status");

                if((dbData.getInt("accountStatus"))==2){
                    message = "The specified account is deactivated and unusable.";
                    //response.sendRedirect("index.jsp");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("logIn.jsp").forward(request, response);

                }
                else{
                    message = "did it! Username is "+username+"!";
                    session.setAttribute("accountID", dbData.getInt("accountID"));
                    session.setAttribute("userName", username);
                    session.setAttribute("accountType", ""+dbData.getInt("accountType"));
                    session.setAttribute("state", "logged in");
                    request.getRequestDispatcher("homePage.jsp").forward(request,response);
                }

                    
                    //session.setMaxInactiveInterval(30*60);

                }
            }            
            
            if(!dbData.previous()){
                session.setAttribute("error_message", "Incorrect username or password");
                response.sendRedirect("logIn.jsp");
            }
            
            
         
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
