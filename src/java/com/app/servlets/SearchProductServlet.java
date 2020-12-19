/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.daos.ProductCategoryDAO;
import com.app.daos.ProductDAO;
import com.app.dtos.ProductCategoryDTO;
import com.app.dtos.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author DuyNK
 */
public class SearchProductServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SearchProductServlet.class);
    
    private final String USER_SEARCH_RESULT_PAGE = "product_search_result.jsp";
    private final String ERROR = "error.html";
    

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
        String url = ERROR;
        
        try {
            String searchValue = request.getParameter("q");
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> results = dao.searchProductsByName(searchValue);
            ProductCategoryDAO categoryDAO = new ProductCategoryDAO();
            List<ProductCategoryDTO> categories = categoryDAO.getAllCategories();
            request.setAttribute("SEARCH_PRODUCTS", results);     
            request.setAttribute("SEARCH_VALUE", searchValue);
            request.setAttribute("CATEGORY_LIST", categories);
            
            url = USER_SEARCH_RESULT_PAGE;
            
        } catch (SQLException e) {
            LOGGER.error("Error: ", e);
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
