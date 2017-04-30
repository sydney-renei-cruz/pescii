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
@WebServlet(name = "removeFromROCartServlet", urlPatterns = {"/removeFromROCartServlet"})
public class removeFromROCartServlet extends HttpServlet {

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
        
        LinkedList<String> ROcart;
        LinkedList<String> ROprodNames;
        LinkedList<String> ROsuppNames;
        LinkedList<String> ROsuppIDs;
        LinkedList<Float> ROprodPrices;
        LinkedList<Integer> ROquantity;
        LinkedList<Float> ROtotalPrices;
        int foundIndex = 0;
        HttpSession session = request.getSession();
        try{
            if (session.getAttribute("ROcart") == null){
                ROcart = new LinkedList<String>();
                ROprodNames = new LinkedList<String>();
                ROprodPrices = new LinkedList<Float>();
                ROquantity = new LinkedList<Integer>();
                ROtotalPrices = new LinkedList<Float>();
                context.log(">>cart created!");
                context.log("----ROcart size is: " + ROcart.size());
            }
            else{
                ROcart = (LinkedList<String>)(session.getAttribute("ROcart"));
                ROprodNames = (LinkedList<String>)(session.getAttribute("ROprodNames"));
                ROprodPrices = (LinkedList<Float>)(session.getAttribute("ROprodPrices"));
                ROquantity = (LinkedList<Integer>)(session.getAttribute("ROquantity"));
                ROtotalPrices = (LinkedList<Float>)(session.getAttribute("ROtotalPrices"));
                context.log("----ROcart size is: " + ROcart.size());
            }
            if(request.getParameter("prodName")!=null){
                String prodName = ""+request.getParameter("prodName");

                for(int i=0;i<ROprodNames.size();i++){
                    if((""+ROprodNames.get(i)).equals(prodName)){
                        foundIndex = i;
                    }
                }

                ROcart.remove(foundIndex);
                ROprodNames.remove(foundIndex);
                ROprodPrices.remove(foundIndex);
                if(ROquantity!=null && ROquantity.size()>foundIndex){
                    ROquantity.remove(foundIndex);
                    ROtotalPrices.remove(foundIndex);}
                context.log("->>product removed from ROcart! size is now: " + ROcart.size());
            }

            session.setAttribute("ROcart", ROcart);
            session.setAttribute("ROprodNames", ROprodNames);
            session.setAttribute("ROprodPrices", ROprodPrices);
            session.setAttribute("ROquantity", ROquantity);
            session.setAttribute("ROtotalPrices", ROtotalPrices);
            request.setAttribute("ROforInvoice", "yes");
            request.getRequestDispatcher("viewROCart.jsp").forward(request,response);

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
