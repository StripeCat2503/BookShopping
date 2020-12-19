/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.beans.CheckoutValidationBean;
import com.app.daos.UserDAO;
import com.app.dtos.UserDTO;
import java.io.IOException;
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
public class UpdateProfileServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UpdateProductServlet.class);

    private final String SUCCESS = "user_profile.jsp";
    private final String ERROR = "user_profile.jsp";

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

            String profileWarning = request.getParameter("warn");
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");

            UserDAO dao = new UserDAO();
            UserDTO user = dao.getUserByID(userID);

            if (user != null) {
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                user.setAddress(address);
                CheckoutValidationBean bean = new CheckoutValidationBean();
                
                boolean isValidInfo = bean.validateUser(user);
                if (isValidInfo) {

                    boolean success = dao.updateUser(user);
                    if (success) {
                        url = SUCCESS;
                        request.setAttribute("MSG", "Your profile has been updated");
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                    }
                } else {
                    url = ERROR;
                    request.setAttribute("ERROR", bean);
                    request.setAttribute("PROFILE_ERROR", profileWarning);
                }

            }
        } catch (SQLException e) {
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
