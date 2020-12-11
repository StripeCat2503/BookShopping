/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.beans.ProductValidationBean;
import com.app.daos.ProductCategoryDAO;
import com.app.daos.ProductDAO;
import com.app.dtos.ProductCategoryDTO;
import com.app.dtos.ProductDTO;
import com.app.utils.ValidationUtils;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DuyNK
 */
public class UpdateProductServlet extends HttpServlet {
    private final String UPDATE_PRODUCT_PAGE = "edit_product.jsp";
    private final String MANAGE_PRODUCT = "ManageProductServlet";
    private final String ERROR_PAGE = "not_found.html";

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
        String url = UPDATE_PRODUCT_PAGE;
        String productIDStr = request.getParameter("productID");

        try {
            ProductCategoryDAO dao = new ProductCategoryDAO();
            List<ProductCategoryDTO> categoryList = dao.getAllCategories();
            request.setAttribute("CATEGORY_LIST", categoryList);
            
            if(productIDStr != null){
                ProductDAO productDAO = new ProductDAO();
                int productID = Integer.parseInt(productIDStr);
                ProductDTO product = productDAO.getProductByID(productID);
                if(product != null){
                    request.setAttribute("PRODUCT", product);
                }
                else{
                    url = ERROR_PAGE;
                }
            }
            else{
                url = ERROR_PAGE;
            }

        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
        String productIDStr = request.getParameter("txtProductID");
        String productName = request.getParameter("txtProductName");
        String priceStr = request.getParameter("txtPrice");
        String quantityStr = request.getParameter("txtQuantity");
        boolean status = request.getParameter("slStatus").equals("1") ? true : false;
        String des = request.getParameter("txtDescription");
        int categoryID = Integer.parseInt(request.getParameter("slCategory"));
        
        String url = UPDATE_PRODUCT_PAGE;
        ProductValidationBean productValidationBean = new ProductValidationBean();
        boolean valid = true;
        double tmpPrice = -1;
        int tmpQuantity = -1;

        try {
            // valid quantity and price as number
            if (!ValidationUtils.isDouble(priceStr)) {
                valid = false;
                productValidationBean.setPriceErr("Invalid price!");
            }
            if (!ValidationUtils.isInteger(quantityStr)) {
                valid = false;
                productValidationBean.setQuantityErr("Invalid quantity!");
            }

            if (valid) {
                int productID = Integer.parseInt(productIDStr);
                double price = Double.parseDouble(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                tmpPrice = price;
                tmpQuantity = quantity;
                ProductDTO product = new ProductDTO(productID, productName, price, quantity, status, "", des, new ProductCategoryDTO(categoryID));

                boolean isValidProduct = productValidationBean.isValidProduct(product);
                if (isValidProduct) {
                    ProductDAO dao = new ProductDAO();
                    boolean updatedSuccess = dao.updateProduct(product);
                    if (updatedSuccess) {
                        request.setAttribute("SUCCESS_MSG", "Product has been updated successfully");
                        url = MANAGE_PRODUCT;
                    }
                } else {
                    valid = false;
                }
            }
            if (!valid) {
                int productID = Integer.parseInt(productIDStr);
                request.setAttribute("PRODUCT", new ProductDTO(productID, productName, tmpPrice, tmpQuantity, status, "", des, new ProductCategoryDTO(categoryID)));
                request.setAttribute("PRODUCT_ERROR", productValidationBean);
            }
            ProductCategoryDAO categoryDAO = new ProductCategoryDAO();
            List<ProductCategoryDTO> categoryList = categoryDAO.getAllCategories();
            request.setAttribute("CATEGORY_LIST", categoryList);
        } catch (Exception e) {

        } finally {
            if(valid){
                response.sendRedirect(url);
            }
            else{
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
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
