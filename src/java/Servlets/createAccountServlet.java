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
import java.util.*;
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
@WebServlet(name = "createAccountServlet", urlPatterns = {"/createAccountServlet"})
public class createAccountServlet extends HttpServlet {

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
        
        //DO NOT CHANGE THESE
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
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
         
         String preparedSQL = "select * from Account where userName=?";
         String message = "";
         
         //you don't change this
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         
         //check the user name
         String inputUsername = "";
         try{
             inputUsername = request.getParameter("usernameInput");
             if(inputUsername.length()>30){
                 message = "User name is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("account.getTypeStatus").forward(request,response);
                return;
             }
            }
            catch(Exception e){
                message = "User name was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("account.getTypeStatus").forward(request,response);
                return;
            }
         
         String inputPassword = "";
         try{
             inputPassword = request.getParameter("passwordInput");
             if(inputPassword.length()>255){
                 message = "Password is too long.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("account.getTypeStatus").forward(request,response);
                return;
             }
             
             if(inputPassword.length()<8){
                 message = "Password must be at least 8 characters.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("account.getTypeStatus").forward(request,response);
                return;
             }
             
             boolean digit = false;
             boolean character = false;
             boolean caps = false;
             char c;
             for(int i=0;i<inputPassword.length();i++){
                 c = inputPassword.charAt(i);
                if(Character.isDigit(c)){
                    digit = true;
                }
                if(!Character.isDigit(c) && !Character.isLetter(c)){
                    character = true;
                }
                if(Character.isUpperCase(c)){
                    caps = true;
                }
             }
             if(!(digit==true || character==true || caps==false)){
                 message = "Password must contain at least one digit and one special character.";
                 request.setAttribute("message",message);
                request.getRequestDispatcher("account.getTypeStatus").forward(request,response);
                return;
            }
             
            }
            catch(Exception e){
                message = "Password was input incorrectly. It should also not be blank.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("account.getTypeStatus").forward(request,response);
                return;
            }
         
         
         String inputPassword2 = request.getParameter("password2Input");
         if(!inputPassword.equals(inputPassword2)){
             message = "Passwords did not match";
             request.setAttribute("message", message);
             request.getRequestDispatcher("createAccount.jsp").forward(request,response);
             return;
         }
         int inputAccType = Integer.parseInt(request.getParameter("accTypeInput"));
         
         ps.setString(1,inputUsername);
         ResultSet dbData = ps.executeQuery();
         while(dbData.next()){
             if(dbData.getString("userName").equals(inputUsername)){
                message = "That username is already in use. Please enter a new one.";
                request.setAttribute("message",message);
                request.getRequestDispatcher("account.getDetails").forward(request,response);
                return;
             }
         }
         
         //                  Hash the password
        try{
        
        //Salts for the hashing
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
        
        Random rand = new Random();
        
        inputPassword = inputPassword + salts[rand.nextInt(10)];
        
            //              End Salting
            
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(inputPassword.getBytes());
        
        byte byteData[] = md.digest();
        
        StringBuffer sb = new StringBuffer();
        
        for(int i = 0; i < byteData.length; i++){
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        inputPassword = sb.toString();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        
        //                 End Hashing
         
         
         
         preparedSQL = "insert into Account(userName, password, accountStatus, accountType) values(?,?,?,?)";
         ps = conn.prepareStatement(preparedSQL, Statement.RETURN_GENERATED_KEYS);
         
         ps.setString(1,inputUsername);
         ps.setString(2,inputPassword);
         ps.setInt(3,1);
         ps.setInt(4,inputAccType);
         ps.executeUpdate();                   //at this point, you have already inserted into the database
         
         //get the generated account ID
         int accountIDInput;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                   accountIDInput = generatedKeys.getInt(1);
                   context.log("The accountID of the new account is: " + accountIDInput);
                }
               else {
                   throw new SQLException("--> no accountID retrieved");
               }
           }
         
         message = "Account successfully created!";
         request.setAttribute("message", message);
         request.setAttribute("accID",accountIDInput);
         request.getRequestDispatcher("anotherAccount.jsp").forward(request,response);
            
         
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
