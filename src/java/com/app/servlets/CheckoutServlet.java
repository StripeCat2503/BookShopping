/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.beans.UserRegisterValidationBean;
import com.app.daos.OrderDAO;
import com.app.daos.OrderDetailsDAO;
import com.app.daos.ProductDAO;
import com.app.daos.RoleDAO;
import com.app.daos.UserDAO;
import com.app.dtos.CartDTO;
import com.app.dtos.OrderDTO;
import com.app.dtos.OrderDetailsDTO;
import com.app.dtos.ProductDTO;
import com.app.dtos.UserDTO;
import com.app.routes.AppRouting;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DuyNK
 */
public class CheckoutServlet extends HttpServlet {

    private final String SUCCESS = "order_success.html";
    private final String ERROR = "error.html";
    private final String CHECKOUT_PAGE = AppRouting.routes.get("checkout");

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
        HttpSession session = request.getSession(false);
        boolean isAuthenticated = session != null && session.getAttribute("user") != null;

        String paymentMethodID = request.getParameter("paymentMethod");

        String url = ERROR;

        try {
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                ProductDAO productDAO = new ProductDAO();

                // calculate the total amount of order
                double orderTotal = 0;
                for (ProductDTO p : cart.getItems().values()) {
                    orderTotal += p.getPrice() * p.getQuantity();
                }

                // check quantity in cart is less than available quantity of product in db               
                Map<Integer, String> productErr = null;

                for (ProductDTO p : cart.getItems().values()) {
                    int quantityInCart = p.getQuantity();

                    ProductDTO currentProduct = productDAO.getProductByID(p.getProductID());
                    int quantityInDB = currentProduct.getQuantity();

                    if (quantityInCart > quantityInDB) {
                        if (productErr == null) {
                            productErr = new HashMap<>();
                        }

                        String errMsg = "<strong>" + currentProduct.getProductName()
                                + "</strong> is not available!<br><br>"
                                + "AVAILABLE QUANTITY: " + "<strong>" + currentProduct.getQuantity() + "</strong>";

                        productErr.put(currentProduct.getProductID(), errMsg);
                    }
                }

                OrderDTO newOrder = null;

                Date orderDate = new Date(new java.util.Date().getTime());

                if (productErr == null) {
                    if (isAuthenticated) {
                        UserDTO loggedInUser = (UserDTO) session.getAttribute("user");
                        String userID = loggedInUser.getUserID();
                        newOrder = new OrderDTO(userID, orderTotal, orderDate, paymentMethodID);

                    } else {
                        // get user info from client
                        String customerName = request.getParameter("customerName");
                        String email = request.getParameter("email");
                        String phoneNumber = request.getParameter("phone");
                        String addr = request.getParameter("address");

                        // create new user with randomly user id
                        UserDAO userDAO = new UserDAO();
                        String userID = "guest-" + UUID.randomUUID().toString();
                        // get role id with name 'User'
                        RoleDAO roleDAO = new RoleDAO();
                        String roleID = roleDAO.getRoleIdByRoleName("User");
                        Date createdDate = new Date(new java.util.Date().getTime());

                        UserDTO user = new UserDTO(userID, "Defaultpwd123", customerName, addr, email, phoneNumber, createdDate, roleID);
                        UserRegisterValidationBean validationBean = new UserRegisterValidationBean();
                        boolean isValidUser = validationBean.validateUser(user);
                        if (isValidUser) {
                            String insertedUserID = userDAO.insertUser(user);
                            if (insertedUserID != null) {
                                newOrder = new OrderDTO(userID, orderTotal, orderDate, paymentMethodID);
                            }
                        } else {
                            request.setAttribute("fullNameError", validationBean.getFullNameError());
                            request.setAttribute("emailError", validationBean.getEmailError());
                            request.setAttribute("phoneError", validationBean.getPhoneError());
                            request.setAttribute("addressError", validationBean.getAddressError());

                            request.setAttribute("fullName", user.getFullName());
                            request.setAttribute("address", user.getAddress());
                            request.setAttribute("email", user.getEmail());
                            request.setAttribute("phone", user.getPhoneNumber());

                            url = CHECKOUT_PAGE;
                        }

                    }
                } else {
                    request.setAttribute("PRODUCT_ERR", productErr);
                    url = CHECKOUT_PAGE;
                }

                if (newOrder != null) {
                    // insert new order to db
                    OrderDAO orderDAO = new OrderDAO();

                    int insertedOrderID = orderDAO.insertOrder(newOrder);
                    if (insertedOrderID != -1) {
                        boolean insertOrderDetailsSuccess = false;
                        // if order insert success, then insert order details                       
                        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

                        for (ProductDTO p : cart.getItems().values()) {
                            int productID = p.getProductID();
                            ProductDTO product = productDAO.getProductByID(productID);
                            double price = p.getPrice();
                            int quantity = p.getQuantity();
                            OrderDetailsDTO orderDetails = new OrderDetailsDTO(product, insertedOrderID, price, quantity);
                            insertOrderDetailsSuccess = orderDetailsDAO.insertOrderDetails(orderDetails);
                            if (insertOrderDetailsSuccess) {
                                // update quantity of product
                                product.setQuantity(product.getQuantity() - quantity);
                                productDAO.updateProduct(product);
                            }
                        }

                        if (insertOrderDetailsSuccess) {
                            url = SUCCESS;
                            session.removeAttribute("CART");
                        }

                    }
                }

            }

        } catch (Exception e) {
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
