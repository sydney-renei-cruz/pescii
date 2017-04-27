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
@WebServlet(name = "addToCartServlet", urlPatterns = {"/addToCartServlet"})
public class addToCartServlet extends HttpServlet {

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
        
        String message = "";
        
        LinkedList<String> cart;
        LinkedList<String> prodNames;
        LinkedList<Float> prodPrices;
        LinkedList<Float> totalPrices;
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") == null){
            cart = new LinkedList<String>();
            prodNames = new LinkedList<String>();
            prodPrices = new LinkedList<Float>();
            totalPrices = new LinkedList<Float>();
            context.log(">>cart created!");
            context.log("----cart size is: " + cart.size());
        }
        else{
            cart = (LinkedList<String>)(session.getAttribute("cart"));
            prodNames = (LinkedList<String>)(session.getAttribute("prodNames"));
            prodPrices = (LinkedList<Float>)(session.getAttribute("prodPrices"));
            totalPrices = (LinkedList<Float>)(session.getAttribute("totalPrices"));
            context.log("----cart size is: " + cart.size());
        }
        if(request.getParameter("prodID")!=null){
            cart.add(request.getParameter("prodID"));
            prodNames.add(request.getParameter("prodName"));
            prodPrices.add(0 + Float.parseFloat(request.getParameter("prodPrice")));
            context.log("->>product added to cart! ID is: " + request.getParameter("prodID"));
        }
        if(request.getParameter("gotQuantity")!=null){
            LinkedList<Integer> quantity = new LinkedList<Integer>();
            for(int i=0; i<prodNames.size();i++){
                try{
                quantity.add(Integer.parseInt(request.getParameter(prodNames.get(i))));
                }
                catch(Exception e){
                    message = "Product quantity was input incorrectly. Please use whole numbers.";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("viewCart.jsp").forward(request,response);
                }
                totalPrices.add(quantity.get(i)*prodPrices.get(i));
                context.log("--->>>quantity is: " + quantity.get(i));
            }
            session.setAttribute("quantity", quantity);
            request.getRequestDispatcher("Servlets.getCustomerServlet").forward(request, response);
            return;
        }
        session.setAttribute("cart", cart);
        session.setAttribute("prodNames", prodNames);
        session.setAttribute("prodPrices", prodPrices);
        session.setAttribute("totalPrices", totalPrices);
        request.setAttribute("forInvoice", "yes");
        request.getRequestDispatcher("Servlets.getProductServlet").forward(request,response);
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
