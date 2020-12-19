/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.daos.ProductDAO;
import com.app.dtos.CartDTO;
import com.app.dtos.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author DuyNK
 */
public class AddToCartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddToCartServlet.class);

    private final String HOME_SERVLET = "HomeServlet";
    private final String SEARCH_RESULT = "SearchProductServlet";
    private final String FAIL = "error.html";
    private final String PRODUCT_DETAILS_PAGE = "product_details.jsp";

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
        request.setCharacterEncoding("UTF-8");
        String url = FAIL;

        try {
            String quantityParam = request.getParameter("quantity");

            String searchValue = request.getParameter("q");
            int productID = Integer.parseInt(request.getParameter("productID"));
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            String imageUrl = request.getParameter("imgProduct");
            ProductDTO product = new ProductDTO();

            product.setProductID(productID);
            product.setProductName(productName);
            product.setPrice(price);
            product.setImage(imageUrl);
            product.setQuantity(1);

            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartDTO();
            }
            
            int quantity = 1;
            if (quantityParam != null) {
                quantity = Integer.parseInt(quantityParam);
            }          

            cart.addItemToCart(product, quantity);

            session.setAttribute("CART", cart);

            if (quantityParam == null) {
                url = HOME_SERVLET;

                if (searchValue != null && !searchValue.isEmpty()) {
                    url = SEARCH_RESULT;
                    request.setAttribute("SEARVH_VALUE", searchValue);
                }
            }
            else{
                ProductDAO dao = new ProductDAO();
                ProductDTO p = dao.getProductByID(productID);
                if(p != null){
                    request.setAttribute("PRODUCT", p);
                    url = PRODUCT_DETAILS_PAGE;
                }
            }

        } catch (NumberFormatException | SQLException e) {
            LOGGER.error("Error: ", e);
        } finally {
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
