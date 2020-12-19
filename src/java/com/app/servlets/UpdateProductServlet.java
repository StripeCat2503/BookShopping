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
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author DuyNK
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class UpdateProductServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UpdateProductServlet.class);

    private final String UPDATE_PRODUCT_PAGE = "edit_product.jsp";
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
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
        String url = ERROR_PAGE;

        try {
            int productID = Integer.parseInt(request.getParameter("productID"));
            ProductCategoryDAO dao = new ProductCategoryDAO();
            List<ProductCategoryDTO> categoryList = dao.getAllCategories();
            request.setAttribute("CATEGORY_LIST", categoryList);

            ProductDAO productDAO = new ProductDAO();

            ProductDTO product = productDAO.getProductByID(productID);
            if (product != null) {
                request.setAttribute("PRODUCT", product);
            }

            url = UPDATE_PRODUCT_PAGE;

        } catch (NumberFormatException | SQLException e) {
            LOGGER.error("Error: ", e);
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
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);

        String productIDStr = request.getParameter("txtProductID").trim();
        String productName = request.getParameter("txtProductName").trim();
        String priceStr = request.getParameter("txtPrice").trim();
        String quantityStr = request.getParameter("txtQuantity").trim();
        boolean status = request.getParameter("slStatus").trim().equals("1") ? true : false;
        String des = request.getParameter("txtDescription").trim();
        int categoryID = Integer.parseInt(request.getParameter("slCategory").trim());
        String oldImageUrl = request.getParameter("oldImgProduct").trim();
        oldImageUrl = oldImageUrl.trim().equals(MyConstants.DEFAULT_PRODUCT_IMAGE_URL) ? "" : oldImageUrl;
        
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");

        String url = ERROR_PAGE;

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

                // update product image
                String uploadDir = MyConstants.PRODUCT_IMAGE_DIR;
                String imgParam = "imgProduct";
                String imageUrl = MyUtils.uploadFile(request, imgParam, uploadDir);

                if (!imageUrl.isEmpty()) {
                    // remove old image file if exists
                    String basePath = getServletContext().getRealPath("");
                    String currentImagePath = basePath + File.separator + oldImageUrl;
                    MyUtils.deteteFile(currentImagePath);
                }

                imageUrl = imageUrl.isEmpty() ? oldImageUrl : imageUrl;

                ProductDTO product = new ProductDTO(productID, productName, price, quantity, status, imageUrl, des, new ProductCategoryDTO(categoryID), author, publisher);

                boolean isValidProduct = productValidationBean.isValidProduct(product);
                if (isValidProduct) {
                    ProductDAO dao = new ProductDAO();
                    boolean updatedSuccess = dao.updateProduct(product);
                    if (updatedSuccess) {
                        request.setAttribute("SUCCESS_MSG", "Product has been updated successfully");
                        request.setAttribute("PRODUCT", product);
                        url = UPDATE_PRODUCT_PAGE;
                    }
                } else {
                    valid = false;
                }
            }
            if (!valid) {
                int productID = Integer.parseInt(productIDStr);
                request.setAttribute("PRODUCT", new ProductDTO(productID, productName, tmpPrice, tmpQuantity, status, "", des, new ProductCategoryDTO(categoryID)));
                request.setAttribute("PRODUCT_ERROR", productValidationBean);
                url = UPDATE_PRODUCT_PAGE;
            }
            ProductCategoryDAO categoryDAO = new ProductCategoryDAO();
            List<ProductCategoryDTO> categoryList = categoryDAO.getAllCategories();
            request.setAttribute("CATEGORY_LIST", categoryList);
        } catch (IOException | NumberFormatException | SQLException | ServletException e) {
            LOGGER.error("Error: ", e);
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
