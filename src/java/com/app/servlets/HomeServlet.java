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
import java.util.ArrayList;
import java.util.List;
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
public class HomeServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HomeServlet.class);
    
    private final String HOME_PAGE = "index.jsp";

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
        String url = HOME_PAGE;
        
        String categoryID = request.getParameter("category_id");
        HttpSession session = request.getSession();
        
        try {
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> products = dao.getAllProducts();           
            ProductCategoryDAO categoryDAO = new ProductCategoryDAO();
            List<ProductCategoryDTO> categories = categoryDAO.getAllCategories();
            // filter product by category
            if(categoryID != null){
                products = filterProductByCategory(products, Integer.parseInt(categoryID));
                
                session.setAttribute("CATEGORY_STATE", categoryID);
            }
            else{
                session.removeAttribute("CATEGORY_STATE");
            }
            
            request.setAttribute("CATEGORY_LIST", categories);
            request.setAttribute("PRODUCT_LIST", products);
        } catch (NumberFormatException | SQLException e) {
            LOGGER.error("Error: ", e);
        }
        finally{
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private List<ProductDTO> filterProductByCategory(List<ProductDTO> products, int categoryID){
        List<ProductDTO> results = null;
        
        for(ProductDTO p : products){
            if(p.getCategory().getCategoryID() == categoryID){
                if(results == null){
                    results = new ArrayList<>();                    
                }
                results.add(p);
            }
        }
        
        return results;
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
