/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.dtos.CartDTO;
import com.app.dtos.ProductDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DuyNK
 */
public class AddToCartServlet extends HttpServlet {
    private final String SUCCESS = "index.jsp";
    private final String FAIL = "error.html";

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
        String url = FAIL;
        
        try {
            int productID = Integer.parseInt(request.getParameter("productID"));
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            String imageUrl = request.getParameter("imgProduct");
            ProductDTO product = new ProductDTO();
            
            product.setProductID(productID);
            product.setProductName(productName);
            product.setPrice(price);
            product.setImage(imageUrl);          
            
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if(cart == null){
                cart = new CartDTO();
            }
            
            int currentQuantity = 1;
            if(cart.getItems() != null && !cart.getItems().isEmpty() && cart.getItems().get(productID) != null){
                currentQuantity = cart.getItems().get(productID).getQuantity();
            }
            
            product.setQuantity(currentQuantity);
            
            cart.addItemToCart(product);
            
            session.setAttribute("CART", cart);
            url = SUCCESS;
            
        } catch (Exception e) {
        }
        finally{
            request.getRequestDispatcher(url).forward(request, response);
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
