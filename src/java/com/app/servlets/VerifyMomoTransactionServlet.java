/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.constants.GmailConstant;
import com.app.daos.OrderDAO;
import com.app.daos.OrderDetailsDAO;
import com.app.daos.ProductDAO;
import com.app.daos.UserDAO;
import com.app.dtos.CartDTO;
import com.app.dtos.OrderDTO;
import com.app.dtos.OrderDetailsDTO;
import com.app.dtos.ProductDTO;
import com.app.dtos.UserDTO;
import com.app.payments.momo.Momo;
import com.app.utils.EmailUtils;
import com.app.utils.MyUtils;
import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DuyNK
 */
public class VerifyMomoTransactionServlet extends HttpServlet {

    private final String SUCCESS = "order_success.html";
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

        HttpSession session = request.getSession();
        CartDTO cart = (CartDTO) session.getAttribute("PENDING_CART");
        UserDTO user = (UserDTO) session.getAttribute("PENDING_USER");
        OrderDTO order = (OrderDTO) session.getAttribute("PENDING_ORDER");

        boolean isAuthenticated = session != null && session.getAttribute("user") != null;

        try {
            String orderId = request.getParameter("orderId");
            String requestId = request.getParameter("requestId");
            QueryStatusTransactionResponse queryStatusTransactionResponse = QueryStatusTransaction.process(Momo.env, orderId, requestId);
            if (queryStatusTransactionResponse != null) {
                int errorCode = queryStatusTransactionResponse.getErrorCode();
                if (errorCode == 0) {
                    // error code = 0 mean MoMo transaction was paid successfully
                    // store order info to db

                    if (!isAuthenticated) {
                        UserDAO userDAO = new UserDAO();
                        // insert new guest user with randomly id if does not exist
                        userDAO.insertUser(user);
                    }

                    OrderDAO orderDAO = new OrderDAO();
                    order.setMoneyPaid(true);
                    int orderID = orderDAO.insertOrder(order);
                    if (orderID != -1) {
                        // insert order details
                        boolean insertOrderDetailsSuccess = false;
                        // if order insert success, then insert order details                       
                        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                        ProductDAO productDAO = new ProductDAO();

                        for (ProductDTO p : cart.getItems().values()) {
                            int productID = p.getProductID();
                            ProductDTO product = productDAO.getProductByID(productID);
                            double price = p.getPrice();
                            int quantity = p.getQuantity();
                            OrderDetailsDTO orderDetails = new OrderDetailsDTO(product, orderID, price, quantity);
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

                            // checkout success, then send order confirmation email to user
                            String subject = GmailConstant.SUBJECT;

                            // process html template to render in email                           
                            String content = orderMailHTML(cart, order);
                            String recipient = user.getEmail();
                            // send email
                            EmailUtils.sendEmail(recipient, subject, content);

                            session.removeAttribute("PENDING_CART");
                            session.removeAttribute("PENDING_USER");
                            session.removeAttribute("PENDING_ORDER");
                        }
                    }

                }
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String orderItemsHTML(CartDTO cart) throws IOException {
        int counter = 1;
        String html = "";
        String path = getServletContext().getRealPath("") + File.separator + "templates/order_item.html";
        for (ProductDTO p : cart.getItems().values()) {

            String itemTemplate = MyUtils.readHTMLFile(path);
            itemTemplate = itemTemplate.replace("{1}", String.valueOf(counter));
            itemTemplate = itemTemplate.replace("{2}", p.getProductName());
            itemTemplate = itemTemplate.replace("{3}", String.valueOf(p.getPrice()));
            itemTemplate = itemTemplate.replace("{4}", String.valueOf(p.getQuantity()));
            itemTemplate = itemTemplate.replace("{5}", String.valueOf(p.getQuantity() * p.getPrice()));

            html += itemTemplate;

            counter++;
        }

        return html;
    }

    private String orderMailHTML(CartDTO cart, OrderDTO order) throws IOException {
        String orderTemplatePath = getServletContext().getRealPath("") + File.separator + "templates/order_confirmation.html";
        String orderTemplate = MyUtils.readHTMLFile(orderTemplatePath);

        SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
        orderTemplate = orderTemplate.replace("{1}", fm.format(order.getOrderDate()));

        String orderTableTemplatePath = getServletContext().getRealPath("") + File.separator + "templates/order_table.html";
        String orderTableTemplate = MyUtils.readHTMLFile(orderTableTemplatePath);

        String orderItemsHTML = orderItemsHTML(cart);

        orderTableTemplate = orderTableTemplate.replace("{1}", orderItemsHTML);
        orderTableTemplate = orderTableTemplate.replace("{2}", String.valueOf(order.getTotalPrice()));

        orderTemplate = orderTemplate.replace("{2}", orderTableTemplate);

        return orderTemplate;
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
