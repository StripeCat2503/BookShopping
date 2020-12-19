/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.constants.Role;
import com.app.daos.RoleDAO;
import com.app.daos.UserDAO;
import com.app.dtos.GoogleUserDTO;
import com.app.dtos.RoleDTO;
import com.app.dtos.UserDTO;
import com.app.utils.GoogleUtils;
import java.io.IOException;
import java.sql.Date;
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
public class LoginGoogleServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginGoogleServlet.class);

    private final String LOGIN_PAGE = "login.jsp";
    private final String HOME_SERVLET = "HomeServlet";

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

        String code = request.getParameter("code");

        String url = LOGIN_PAGE;

        try {
            if (code != null && !code.isEmpty()) {
                String token = GoogleUtils.getToken(code);
                GoogleUserDTO googleUser = GoogleUtils.getUserInfo(token);

                String userID = googleUser.getId();

                UserDAO userDAO = new UserDAO();

                UserDTO loggedInUser = userDAO.getUserByID(userID);
                HttpSession session = request.getSession();
                if (loggedInUser != null) {
                    session.setAttribute("user", loggedInUser);
                    url = HOME_SERVLET;
                } else {
                    String fullName = googleUser.getName();
                    String email = googleUser.getEmail();
                    Date createdDate = new Date(new java.util.Date().getTime());
                    RoleDAO roleDAO = new RoleDAO();
                    String roleID = roleDAO.getRoleIdByRoleName(Role.USER);
                    UserDTO newUser = new UserDTO(userID, "", fullName, "", email, "", createdDate, new RoleDTO(roleID, ""));

                    String createdUserID = userDAO.insertUser(newUser);
                    if (createdUserID != null) {
                        session.setAttribute("user", newUser);
                        url = HOME_SERVLET;
                    }
                }

            }
        } catch (IOException | SQLException e) {
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
