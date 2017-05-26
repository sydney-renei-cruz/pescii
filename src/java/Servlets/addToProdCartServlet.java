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
@WebServlet(name = "addToProdCartServlet", urlPatterns = {"/addToProdCartServlet"})
public class addToProdCartServlet extends HttpServlet {

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
        String message = "";
        
        LinkedList<productBean> prodCart;
        LinkedList<String> prodNames;
        LinkedList<Float> prodPrices;
        //LinkedList<Float> totalPrices;
        LinkedList<String> suppNames;
        LinkedList<String> brands;
        LinkedList<String> productClasses;
        LinkedList<String> colors;
        LinkedList<Integer> lowstocks;
        
        LinkedList<productBean> products;
        
        HttpSession session = request.getSession();
        
        try{
            String cartSession = "";//+session.getAttribute("cartType");
            if(session.getAttribute("cartType")==null || !session.getAttribute("cartType").equals("lowstockLevel") || request.getParameter("getQuantity")!=null){
                
                session.setAttribute("cart", null);
                session.setAttribute("prodNames", null);
                session.setAttribute("quantity", null);
                
                session.setAttribute("ROcart", null);
                session.setAttribute("ROprodNames", null);
                session.setAttribute("ROquantity", null);
                session.setAttribute("ROtotalPrices", null);
                session.setAttribute("suppID", null);
                
                session.setAttribute("cartType", "lowstockLevel");
            }


            if (session.getAttribute("prodCart") == null){
                context.log("now creating prodCart");
                prodCart = new LinkedList<productBean>();    //this contains the productID
                context.log(">>prodCart created!");
                context.log("----prodCart size is: " + prodCart.size());
            }
            else{
                prodCart = (LinkedList<productBean>)(session.getAttribute("prodCart"));
                prodNames = (LinkedList<String>)(session.getAttribute("prodNames"));
                prodPrices = (LinkedList<Float>)(session.getAttribute("prodPrices"));
                suppNames = (LinkedList<String>)(session.getAttribute("suppNames"));
                brands = (LinkedList<String>)(session.getAttribute("brands"));
                productClasses = (LinkedList<String>)(session.getAttribute("productClasses"));
                colors = (LinkedList<String>)(session.getAttribute("colors"));
                lowstocks = (LinkedList<Integer>)(session.getAttribute("lowStock"));
                context.log("----The prodCart size is: " + prodCart.size());
            }
            if(request.getParameter("prodID")!=null){
                context.log("adding items to prodCart!");
                productBean product = new productBean();
                
                product.setProductID(request.getParameter("prodID"));
                product.setProductName(request.getParameter("prodName"));
                product.setRestockPrice(Float.parseFloat(request.getParameter("prodPrice")));
                product.setLowStock(Integer.parseInt(request.getParameter("lowStock")));
                product.setBrand(request.getParameter("brand"));
                product.setProductClassName(request.getParameter("prodClass"));
                product.setColor(request.getParameter("color"));
                product.setSupplierName(request.getParameter("suppName"));
                prodCart.add(product);
                session.setAttribute("prodCart", prodCart);
                
            }
            
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
