/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.beans.UserRegisterValidationBean;
import com.app.daos.RoleDAO;
import com.app.daos.UserDAO;
import com.app.dtos.RoleDTO;
import com.app.dtos.UserDTO;
import com.app.routes.AppRouting;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author DuyNK
 */
public class RegisterServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);

    private final String REGISTER_PAGE = AppRouting.routes.get("register");
    private final String REGISTER_SUCCESS_PAGE = "register_success.html";

    private final String INSERT_ERROR = "Failed to register account!";
    private final String DUPLICATE_ERROR = "This user is already taken!";

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

        String userID = request.getParameter("txtUserID").trim();
        String password = request.getParameter("txtPassword");
        String confirmPassword = request.getParameter("txtConfirmPassword");
        String fullName = request.getParameter("txtFullName").trim();
        String email = request.getParameter("txtEmail").trim();
        String address = request.getParameter("txtAddress");
        String phoneNumber = request.getParameter("txtPhoneNumber").trim();
        Date createdDate = new Date(new java.util.Date().getTime());

        String url = REGISTER_PAGE;

        // get role id from db
        RoleDAO roleDAO = new RoleDAO();

        try {
            String roleID = roleDAO.getRoleIdByRoleName("User");
            if (roleID != null) {
                UserDTO newUser = new UserDTO(userID, password, confirmPassword, fullName, address, email, phoneNumber, createdDate, new RoleDTO(roleID, null));
                // validate user info
                UserRegisterValidationBean validationBean = new UserRegisterValidationBean();
                boolean isValidUser = validationBean.validateUser(newUser);
                if (isValidUser) {
                    UserDAO userDAO = new UserDAO();
                    // check duplicate user
                    boolean isExistedUser = userDAO.isExistedUser(newUser.getUserID());
                    if (isExistedUser) {
                        request.setAttribute("duplicateError", DUPLICATE_ERROR);
                    } else {
                        String insertedUserID = userDAO.insertUser(newUser);
                        if (insertedUserID != null) {
                            url = REGISTER_SUCCESS_PAGE;
                        } else {
                            request.setAttribute("insertError", INSERT_ERROR);
                        }
                    }

                } else {
                    // set validation error
                    request.setAttribute("userIdError", validationBean.getUserIdError());
                    request.setAttribute("passwordError", validationBean.getPasswordError());
                    request.setAttribute("confirmPasswordError", validationBean.getConfirmPasswordError());
                    request.setAttribute("fullNameError", validationBean.getFullNameError());
                    request.setAttribute("emailError", validationBean.getEmailError());
                    request.setAttribute("phoneError", validationBean.getPhoneError());
                    request.setAttribute("addressError", validationBean.getAddressError());
                }
                // store input data to request scope
                request.setAttribute("userID", newUser.getUserID());
                request.setAttribute("fullName", newUser.getFullName());
                request.setAttribute("address", newUser.getAddress());
                request.setAttribute("email", newUser.getEmail());
                request.setAttribute("phone", newUser.getPhoneNumber());
            }
        } catch (SQLException e) {
            LOGGER.error("Error: ", e);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
