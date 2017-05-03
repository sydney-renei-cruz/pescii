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
        
        //LinkedList<String> cart;
        LinkedList<productBean> cart;
        LinkedList<String> prodNames;
        LinkedList<Float> prodPrices;
        LinkedList<Float> totalPrices;
        HttpSession session = request.getSession();
        try{
            String cartSession = ""+session.getAttribute("cartType");
            
             if(session.getAttribute("cartType")==null || !session.getAttribute("cartType").equals("invoice") || request.getParameter("getQuantity")!=null){
                session.setAttribute("ROcart", null);
                session.setAttribute("ROprodNames", null);
                session.setAttribute("ROquantity", null);
                session.setAttribute("ROtotalPrices", null);
                session.setAttribute("cartType", "invoice");
                session.setAttribute("suppID",null);
                
                session.setAttribute("prodCart", null);
            }
            
            if (session.getAttribute("cart") == null){
                cart = new LinkedList<productBean>();
                totalPrices = new LinkedList<Float>();
                context.log(">>cart created!");
                context.log("----cart size is: " + cart.size());
            }
            else{
                cart = (LinkedList<productBean>)(session.getAttribute("cart"));
                totalPrices = (LinkedList<Float>)(session.getAttribute("totalPrices"));
                
                context.log("----cart size is: " + cart.size());
            }
            if(request.getParameter("prodID")!=null){
                context.log("adding items to cart!");
                productBean product = new productBean();
                
                product.setProductID(request.getParameter("prodID"));
                product.setProductName(request.getParameter("prodName"));
                product.setProductPrice(Float.parseFloat(request.getParameter("prodPrice")));
                cart.add(product);
                session.setAttribute("cart", cart);
                context.log("->>product added to cart! ID is: " + request.getParameter("prodID"));
            }
            if(request.getParameter("gotQuantity")!=null){
                LinkedList<Integer> quantity = new LinkedList<Integer>();
                for(int i=0; i<cart.size();i++){
                    try{
                        quantity.add(Integer.parseInt(request.getParameter(cart.get(i).getProductName())));
                        if(Integer.parseInt(request.getParameter(cart.get(i).getProductName()))<0){
                            message = "Product quantity was input incorrectly. Please use whole numbers.";
                            request.setAttribute("message",message);
                            context.log("returning to viewCart.jsp 1");
                            request.getRequestDispatcher("viewCart.jsp").forward(request,response);
                            return;
                        }
                    }
                    catch(Exception e){
                        message = "Product quantity was input incorrectly. Please use whole numbers.";
                        request.setAttribute("message",message);
                        context.log("returning to viewCart.jsp 2");
                        request.getRequestDispatcher("viewCart.jsp").forward(request,response);
                        return;
                    }
                    totalPrices.add(quantity.get(i)*cart.get(i).getProductPrice());
                    context.log("--->>>quantity is: " + quantity.get(i));
                }
                session.setAttribute("quantity", quantity);
                context.log("cartType in addToCartServlet is: "+session.getAttribute("cartType"));
                request.getRequestDispatcher("Servlets.getCustomerServlet").forward(request, response);
                return;
            }
            context.log("cartType in addToCartServlet 2 is: "+session.getAttribute("cartType"));
            session.setAttribute("cart", cart);
            session.setAttribute("totalPrices", totalPrices);
            request.setAttribute("forInvoice", "yes");
            request.getRequestDispatcher("Servlets.getProductServlet").forward(request,response);
        }
        catch(Exception ex){
            ex.printStackTrace();
            message = "Something went wrong. Please try again or contact the administrator.";
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
