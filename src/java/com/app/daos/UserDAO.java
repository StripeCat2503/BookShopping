/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.dtos.UserDTO;
import com.app.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.apache.log4j.Logger;

/**
 *
 * @author DuyNK
 */
public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    private final String SQL_INSERT_USER = "INSERT INTO tblUsers(userID, password, fullName, "
            + "address, email, phoneNumber, roleID, createdDate) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final String SQL_GET_USER = "SELECT userID, fullName, email, address, phoneNumber "
            + "FROM tblUsers WHERE userID = ? AND password = ?";

    public boolean insertUser(UserDTO user) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        int rows = -1;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_INSERT_USER);
                stm.setString(1, user.getUserID());
                stm.setString(2, user.getPassword());
                stm.setString(3, user.getFullName());
                stm.setString(4, user.getAddress());
                stm.setString(5, user.getEmail());
                stm.setString(6, user.getPhoneNumber());
                stm.setString(7, user.getRoleID());
                stm.setTimestamp(8, new Timestamp(user.getCreatedDate().getTime()));

                rows = stm.executeUpdate();
            }

        } catch (Exception e) {
            LOGGER.error("Error while inserting user!", e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        if (rows > 0) {
            return true;
        } else {
            return false;
        }
        
    }
    
    public UserDTO authenticateUser(String userID, String password) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        UserDTO user = null;
        
        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_USER);
                stm.setString(1, userID);
                stm.setString(2, password);
               
                rs = stm.executeQuery();
                
                if(rs.next()){
                    user = new UserDTO();
                    user.setUserID(rs.getString("userID"));
                    user.setFullName(rs.getString("fullName"));
                    user.setAddress(rs.getString("address"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error while inserting user!", e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return user;
    }
}
