/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.beans.CheckoutValidationBean;
import com.app.constants.GmailConstant;
import com.app.constants.MomoInfo;
import com.app.constants.MomoPartnerInfo;
import com.app.constants.OrderStatus;
import com.app.constants.Role;
import com.app.daos.OrderDAO;
import com.app.daos.OrderDetailsDAO;
import com.app.daos.OrderStatusDAO;
import com.app.daos.PaymentMethodDAO;
import com.app.daos.ProductDAO;
import com.app.daos.RoleDAO;
import com.app.daos.UserDAO;
import com.app.dtos.CartDTO;
import com.app.dtos.OrderDTO;
import com.app.dtos.OrderDetailsDTO;
import com.app.dtos.OrderStatusDTO;
import com.app.dtos.PaymentMethodDTO;
import com.app.dtos.ProductDTO;
import com.app.dtos.RoleDTO;
import com.app.dtos.UserDTO;
import com.app.payments.momo.Momo;
import com.app.utils.EmailUtils;
import com.app.utils.MyUtils;
import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
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
    private final String CHECKOUT_PAGE = "checkout.jsp";
    private final String PROFILE_PAGE = "user_profile.jsp";

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

    private Map<Integer, String> checkProductQuantity(CartDTO cart) throws SQLException {
        ProductDAO productDAO = new ProductDAO();

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

        return productErr;
    }

    private String orderItemsHTML(CartDTO cart) throws IOException {
        int counter = 1;
        String html = "";
        String path = getServletContext().getRealPath("") + File.separator + "templates/order_item.html";
        for (ProductDTO p : cart.getItems().values()) {

            String itemTemplate = MyUtils.readHTMLFile(path);
            itemTemplate = itemTemplate.replace("{1}", String.valueOf(counter));
            itemTemplate = itemTemplate.replace("{2}", p.getProductName());
            itemTemplate = itemTemplate.replace("{3}", String.valueOf((long) p.getPrice()));
            itemTemplate = itemTemplate.replace("{4}", String.valueOf(p.getQuantity()));
            itemTemplate = itemTemplate.replace("{5}", String.valueOf(p.getQuantity() * (long) p.getPrice()));

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
        orderTableTemplate = orderTableTemplate.replace("{2}", String.valueOf((long) order.getTotalPrice()));

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
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
        String url = CHECKOUT_PAGE;

        HttpSession session = request.getSession(false);
        boolean isAuthenticated = session != null && session.getAttribute("user") != null;

        try {
            if (isAuthenticated) {
                // validation checkout info
                CheckoutValidationBean checkoutValidationBean = new CheckoutValidationBean();
                UserDTO user = (UserDTO) session.getAttribute("user");
                boolean isValidCheckoutInfo = checkoutValidationBean.validateUser(user);
                if(!isValidCheckoutInfo){
                    url = PROFILE_PAGE;
                    request.setAttribute("PROFILE_ERROR", "Your checkout info is not valid, please update profile!");
                }
            }
            
            if (session.getAttribute("PAYMENT_METHODS") == null) {
                PaymentMethodDAO methodDAO = new PaymentMethodDAO();
                List<PaymentMethodDTO> methods = methodDAO.getAllPaymentMethods();
                session.setAttribute("PAYMENT_METHODS", methods);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        HttpSession session = request.getSession(false);
        boolean isAuthenticated = session != null && session.getAttribute("user") != null;

        String url = ERROR;

        // validation obj
        CheckoutValidationBean checkoutValidationBean = new CheckoutValidationBean();

        // cart
        CartDTO cart = (CartDTO) session.getAttribute("CART");
        Map<Integer, String> productErrors = null;

        boolean redirectFlag = false;
        boolean isNewUser = false;
        boolean productIsAvailable = true;
        boolean cartIsAvailable = session != null && cart != null && cart.getItems() != null && !cart.getItems().isEmpty();
        boolean isValidUserInfo = true;

        try {
            String randomUserID = "guest-" + UUID.randomUUID().toString();

            // get user info from client
            String paymentMethodID = request.getParameter("paymentMethod");

            PaymentMethodDAO methodDAO = new PaymentMethodDAO();
            String paymentName = methodDAO.getPaymentMethodByID(paymentMethodID).getPaymentMethodName();

            String customerName = request.getParameter("customerName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phone");
            String addr = request.getParameter("address");

            // handle user info
            // get role id with name 'User'
            RoleDAO roleDAO = new RoleDAO();
            String roleID = roleDAO.getRoleIdByRoleName(Role.USER);
            UserDTO user = new UserDTO(null, null, customerName, addr, email, phoneNumber, null, new RoleDTO(roleID, null));
            if (isAuthenticated) {
                String userID = ((UserDTO) session.getAttribute("user")).getUserID();
                user.setUserID(userID);
                isNewUser = false;
            } else {

                Date createdDate = new Date(new java.util.Date().getTime());
                user.setUserID(randomUserID);
                user.setPassword("Defaultpwd123");
                user.setCreatedDate(createdDate);

                isNewUser = true;
            }

            isValidUserInfo = checkoutValidationBean.validateUser(user);

            // check quantity in cart is valid with available quantity in db
            if (cartIsAvailable) {
                productErrors = checkProductQuantity(cart);
                productIsAvailable = (productErrors == null);
            }

            if (cartIsAvailable && productIsAvailable && isValidUserInfo) {
                OrderDTO newOrder = null;
                String userID = user.getUserID();
                Date orderDate = new Date(new java.util.Date().getTime());
                double total = cart.getTotal();

                // set status of the order is : received
                OrderStatusDAO statusDAO = new OrderStatusDAO();
                String statusID = statusDAO.getOrderStatusID(OrderStatus.RECEIVED);
                newOrder = new OrderDTO(userID, total, orderDate, paymentMethodID, new OrderStatusDTO(statusID, null), false);

                if (paymentName.equals(MomoInfo.PAYMENT_NAME)) {
                    // store pending order to session, if Momo payment success then get order from session then store to db
                    session.setAttribute("PENDING_ORDER", newOrder);
                    session.setAttribute("PENDING_USER", user);
                    session.setAttribute("PENDING_CART", cart);
                    String orderID = String.valueOf(System.currentTimeMillis());
                    long requestID = System.currentTimeMillis();
                    long amount = (long) newOrder.getTotalPrice();
                    String orderInfo = MomoPartnerInfo.ORDER_INFO;
                    String returnUrl = MomoPartnerInfo.RETURN_URL;
                    String notifyUrl = MomoPartnerInfo.NOTIFY_URL;
                    CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(Momo.env, orderID, String.valueOf(requestID),
                            String.valueOf(amount), orderInfo, returnUrl, notifyUrl, "");
                    if (captureMoMoResponse != null) {
                        String payUrl = captureMoMoResponse.getPayUrl();
                        url = payUrl;
                        redirectFlag = true;
                    }
                } else {
                    if (isNewUser) {
                        UserDAO userDAO = new UserDAO();
                        userDAO.insertUser(user);
                    }

                    OrderDAO orderDAO = new OrderDAO();
                    int orderID = orderDAO.insertOrder(newOrder);
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
                            String content = orderMailHTML(cart, newOrder);
                            String recipient = email;
                            // send email
                            EmailUtils.sendEmail(recipient, subject, content);

                        }
                    }
                }

            } else {
                request.setAttribute("PRODUCT_ERR", productErrors);

                request.setAttribute("fullNameError", checkoutValidationBean.getFullNameError());
                request.setAttribute("emailError", checkoutValidationBean.getEmailError());
                request.setAttribute("phoneError", checkoutValidationBean.getPhoneError());
                request.setAttribute("addressError", checkoutValidationBean.getAddressError());

                request.setAttribute("fullName", user.getFullName());
                request.setAttribute("address", user.getAddress());
                request.setAttribute("email", user.getEmail());
                request.setAttribute("phone", user.getPhoneNumber());

                url = CHECKOUT_PAGE;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (!redirectFlag) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect(url);
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
