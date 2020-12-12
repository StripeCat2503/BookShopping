/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.beans.ProductValidationBean;
import com.app.constants.MyConstants;
import com.app.daos.ProductCategoryDAO;
import com.app.daos.ProductDAO;
import com.app.dtos.ProductCategoryDTO;
import com.app.dtos.ProductDTO;
import com.app.utils.MyUtils;
import com.app.utils.ValidationUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DuyNK
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class AddProductServlet extends HttpServlet {

    private final String ADD_PRODUCT_PAGE = "add_product.jsp";

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

        String url = ADD_PRODUCT_PAGE;

        try {
            ProductCategoryDAO dao = new ProductCategoryDAO();
            List<ProductCategoryDTO> categoryList = dao.getAllCategories();
            request.setAttribute("CATEGORY_LIST", categoryList);

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

        String productName = request.getParameter("txtProductName").trim();
        String priceStr = request.getParameter("txtPrice").trim();
        String quantityStr = request.getParameter("txtQuantity").trim();
        boolean status = request.getParameter("slStatus").equals("1") ? true : false;
        String des = request.getParameter("txtDescription").trim();
        int categoryID = Integer.parseInt(request.getParameter("slCategory"));
        String url = ADD_PRODUCT_PAGE;
        ProductValidationBean productValidationBean = new ProductValidationBean();
        boolean valid = true;
        ProductDAO productDAO = new ProductDAO();

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
                double price = Double.parseDouble(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                ProductDTO checkProduct = new ProductDTO(productName, price, quantity, status, "", des, new ProductCategoryDTO(categoryID));

                boolean isValidProduct = productValidationBean.isValidProduct(checkProduct);

                if (isValidProduct) {
                    boolean isExisted = productDAO.isExistedProduct(checkProduct.getProductName());
                    if (isExisted) {
                        valid = false;
                        productValidationBean.setProductNameErr("This product name is already existed!");
                    }
                } else {
                    valid = false;
                }
            }
            if (!valid) {
                request.setAttribute("PRODUCT", new ProductDTO(productName, 0, 0, status, "", des, new ProductCategoryDTO(categoryID)));
                request.setAttribute("PRODUCT_ERROR", productValidationBean);
            } else {
                
                // upload image then return an image url
                String uploadDir = MyConstants.PRODUCT_IMAGE_DIR;
                String imgParam = "imgProduct";
                String imageUrl = MyUtils.uploadFile(request, imgParam, uploadDir);
                double price = Double.parseDouble(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                ProductDTO product = new ProductDTO(productName, price, quantity, status, imageUrl, des, new ProductCategoryDTO(categoryID));
                boolean addedSuccess = productDAO.insertProduct(product);
                if (addedSuccess) {
                    request.setAttribute("SUCCESS_MSG", "Product has been added successfully");
                }
            }

            ProductCategoryDAO categoryDAO = new ProductCategoryDAO();
            List<ProductCategoryDTO> categoryList = categoryDAO.getAllCategories();
            request.setAttribute("CATEGORY_LIST", categoryList);
        } catch (Exception e) {

        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
