/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

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
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class removeFromCartServlet extends HttpServlet {

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
        
        LinkedList<String> cart;
        LinkedList<String> prodNames;
        LinkedList<Float> prodPrices;
        LinkedList<Integer> quantity;
        LinkedList<Float> totalPrices;
        int foundIndex = 0;
        HttpSession session = request.getSession();
        try{
            if (session.getAttribute("cart") == null){
                cart = new LinkedList<String>();
                prodNames = new LinkedList<String>();
                prodPrices = new LinkedList<Float>();
                quantity = new LinkedList<Integer>();
                totalPrices = new LinkedList<Float>();
                context.log(">>cart created!");
                context.log("----cart size is: " + cart.size());
            }
            else{
                cart = (LinkedList<String>)(session.getAttribute("cart"));
                prodNames = (LinkedList<String>)(session.getAttribute("prodNames"));
                prodPrices = (LinkedList<Float>)(session.getAttribute("prodPrices"));
                quantity = (LinkedList<Integer>)(session.getAttribute("quantity"));
                totalPrices = (LinkedList<Float>)(session.getAttribute("totalPrices"));
                context.log("----cart size is: " + cart.size());
            }
            if(request.getParameter("prodName")!=null){
                String prodName = ""+request.getParameter("prodName");

                for(int i=0;i<prodNames.size();i++){
                    if((""+prodNames.get(i)).equals(prodName)){
                        foundIndex = i;
                    }
                }

                cart.remove(foundIndex);
                prodNames.remove(foundIndex);
                prodPrices.remove(foundIndex);
                if(quantity!=null && quantity.size()>foundIndex){quantity.remove(foundIndex);totalPrices.remove(foundIndex);}
                context.log("->>product removed from cart! size is now: " + cart.size());
            }

            session.setAttribute("cart", cart);
            session.setAttribute("prodNames", prodNames);
            session.setAttribute("prodPrices", prodPrices);
            session.setAttribute("quantity", quantity);
            session.setAttribute("totalPrices", totalPrices);
            request.setAttribute("forInvoice", "yes");
            request.getRequestDispatcher("viewCart.jsp").forward(request,response);


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
