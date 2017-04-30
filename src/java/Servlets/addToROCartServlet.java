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
@WebServlet(name = "addToROCartServlet", urlPatterns = {"/addToROCartServlet"})
public class addToROCartServlet extends HttpServlet {

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
        
        LinkedList<String> ROcart;
        LinkedList<String> ROprodNames;
        LinkedList<Float> ROprodPrices;
        LinkedList<Float> ROtotalPrices;
        HttpSession session = request.getSession();
        
        try{
            String cartSession = "";//+session.getAttribute("cartType");
            if(session.getAttribute("cartType")==null || !session.getAttribute("cartType").equals("restock") || request.getParameter("getQuantity")!=null){
                session.setAttribute("cart", null);
                session.setAttribute("prodNames", null);
                session.setAttribute("quantity", null);
                session.setAttribute("cartType", "restock");
                session.setAttribute("suppID",request.getParameter("suppID"));
            }


            if (session.getAttribute("ROcart") == null){
                context.log("now creating ROcart");
                ROcart = new LinkedList<String>();    //this contains the productID
                ROprodNames = new LinkedList<String>();
                ROprodPrices = new LinkedList<Float>();
                ROtotalPrices = new LinkedList<Float>();
                context.log(">>cart created!");
                context.log("----ROcart size is: " + ROcart.size());
            }
            else{
                ROcart = (LinkedList<String>)(session.getAttribute("ROcart"));
                ROprodNames = (LinkedList<String>)(session.getAttribute("ROprodNames"));
                ROprodPrices = (LinkedList<Float>)(session.getAttribute("ROprodPrices"));
                ROtotalPrices = (LinkedList<Float>)(session.getAttribute("ROtotalPrices"));
                context.log("----The ROcart size is: " + ROcart.size());
            }
            if(request.getParameter("prodID")!=null){
                ROcart.add(request.getParameter("prodID"));
                ROprodNames.add(request.getParameter("prodName"));
                ROprodPrices.add(0 + Float.parseFloat(request.getParameter("prodPrice")));
                context.log("->>product added to ROcart! ID is: " + request.getParameter("prodID"));
            }
            if(request.getParameter("gotQuantity")!=null){
                LinkedList<Integer> ROquantity = new LinkedList<Integer>();
                for(int i=0; i<ROprodNames.size();i++){
                    try{
                        context.log("product name from prodName is: "+request.getParameter(ROprodNames.get(i)));
                        //String pq = request.getParameter(ROprodNames.get(i));
                        //int prodQuant = Integer.parseInt(request.getParameter(ROprodNames.get(i)));
                        //int prodQuant = Integer.parseInt(pq);
                        context.log("I made it?...");
                        ROquantity.add(Integer.parseInt(request.getParameter(ROprodNames.get(i))));
                        if(Integer.parseInt(request.getParameter(ROprodNames.get(i)))<0){
                            message = "Product quantity was input incorrectly. Please use whole numbers.";
                            request.setAttribute("message",message);
                            context.log("returning to viewROCart.jsp 1");
                            request.getRequestDispatcher("viewROCart.jsp").forward(request,response);
                            return;
                        }
                    }
                    catch(Exception e){
                        message = "Product quantity was input incorrectly. Please use whole numbers.";
                        request.setAttribute("message",message);
                        context.log("returning to viewROCart.jsp 2");
                        request.getRequestDispatcher("viewROCart.jsp").forward(request,response);
                        return;
                    }
                    context.log("ROquantity.size() is: "+ROquantity.size()+" and i = "+i);
                    ROtotalPrices.add(ROquantity.get(i)*ROprodPrices.get(i));
                    context.log("--->>>ROquantity is: " + ROquantity.get(i));
                }
                session.setAttribute("ROquantity", ROquantity);
                context.log("cartType in addToCartServlet is: "+session.getAttribute("cartType"));
                //request.getRequestDispatcher("createRestockOrder.jsp").forward(request,response);
                request.getRequestDispatcher("restockOrder.getStatus").forward(request,response);

                return;
            }

                session.setAttribute("ROcart", ROcart);
                session.setAttribute("ROprodNames", ROprodNames);
                session.setAttribute("ROprodPrices", ROprodPrices);
                session.setAttribute("ROtotalPrices", ROtotalPrices);
                request.setAttribute("forRestock", "yes");

                if(request.getParameter("getQuantity")!=null){
                    request.getRequestDispatcher("viewROCart.jsp").forward(request,response);
                    return;
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
