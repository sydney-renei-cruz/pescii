/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.productBean;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "removeFromProdCartServlet", urlPatterns = {"/removeFromProdCartServlet"})
public class removeFromProdCartServlet extends HttpServlet {

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
        ServletContext context = request.getSession().getServletContext();
        
        LinkedList<productBean> prodCart;
        LinkedList<String> prodNames;
        LinkedList<Float> prodPrices;
        LinkedList<Integer> quantity;
        LinkedList<Float> totalPrices;
        int foundIndex = 0;
        HttpSession session = request.getSession();
        try{
            if (session.getAttribute("prodCart") == null){
                prodCart = new LinkedList<productBean>();
                context.log(">>prodCart created!");
                context.log("----prodCart size is: " + prodCart.size());
            }
            else{
                prodCart = (LinkedList<productBean>)(session.getAttribute("prodCart"));
                context.log("----prodCart size is: " + prodCart.size());
            }
            if(request.getParameter("prodID")!=null){
                String prodID = ""+request.getParameter("prodID");

                for(int i=0;i<prodCart.size();i++){
                    if(prodCart.get(i).getProductID().equals(prodID)){
                        foundIndex = i;
                    }
                }

                prodCart.remove(foundIndex);
                context.log("->>product removed from prodCart! size is now: " + prodCart.size());
            }

            session.setAttribute("prodCart", prodCart);
            //request.setAttribute("forLowStock", "yes");
            request.getRequestDispatcher("viewProdCart.jsp").forward(request,response);


        }
        catch(Exception ex){
            ex.printStackTrace();
            String message = "Something went wrong. Please try again or contact the administrator.";
            request.setAttribute("message", message);
            request.getRequestDispatcher("errorPage.jsp").forward(request,response);
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
