/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servlets;

import com.app.daos.RoleDAO;
import com.app.daos.UserDAO;
import com.app.dtos.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DuyNK
 */
public class RegisterServlet extends HttpServlet {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(RegisterServlet.class);

    private final String REGISTER_PAGE = "register.jsp";
    private final String REGISTER_SUCCESS_PAGE = "register_success.html";
    private final String REGISTER_FAILED_PAGE = "register_failed.html";
    

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
        response.sendRedirect(REGISTER_PAGE);
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

        String userID = request.getParameter("txtUserID");
        String password = request.getParameter("txtPassword");
        String fullName = request.getParameter("txtFullName");
        String email = request.getParameter("txtEmail");
        String address = request.getParameter("txtAddress");
        String phoneNumber = request.getParameter("txtPhoneNumber");
        Date createdDate = new Date(new java.util.Date().getTime());
        
        String url = REGISTER_FAILED_PAGE;

        // get role id from db
        RoleDAO roleDAO = new RoleDAO();

        try {
            String roleID = roleDAO.getRoleIdByRoleName("User");
            if(roleID != null){
                UserDTO newUser = new UserDTO(userID, password, fullName, address, email, phoneNumber, createdDate, roleID);
                UserDAO userDAO = new UserDAO();
                boolean isUserAdded = userDAO.insertUser(newUser);
                if(isUserAdded){
                    url = REGISTER_SUCCESS_PAGE;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("error when inserting user!", ex);
        }
        finally{
            response.sendRedirect(url);
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
