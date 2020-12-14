/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.dtos.OrderDTO;
import com.app.dtos.UserDTO;
import com.app.utils.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    
    private final String SQL_GET_USER = "SELECT userID, fullName, email, address, phoneNumber, roleID "
            + "FROM tblUsers WHERE userID = ? AND password = ?";
    
    private final String SQL_GET_ROLE_NAME = "SELECT roleName FROM tblUsers AS u, tblRoles AS r WHERE "
            + "u.roleID = r.roleID AND u.roleID = ?";
    
    private final String SQL_CHECK_USER = "SELECT userID FROM tblUsers WHERE userID = ?";
    
    private final String SQL_GET_USER_BY_ID = "SELECT fullName, email, address, phoneNumber "
            + "FROM tblUsers WHERE userID = ?";

    public String insertUser(UserDTO user) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        String insertedUserID = null;

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

                int rows = stm.executeUpdate();
                
                if(rows > 0){
                    insertedUserID = user.getUserID();
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

        return insertedUserID;
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
                    user.setRoleID(rs.getString("roleID"));
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
    
    public String getUserRoleName(String roleID) throws SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String roleName = null;
        
        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_ROLE_NAME);
                stm.setString(1, roleID);
               
                rs = stm.executeQuery();
                
                if(rs.next()){
                    roleName = rs.getString("roleName");
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error while get role name of user!", e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return roleName;
    }
    
    public boolean isExistedUser(String userID) throws SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean isExisted = false;
        
        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_CHECK_USER);
                stm.setString(1, userID);
               
                rs = stm.executeQuery();
                
                if(rs.next()){
                    isExisted = true;
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error while check duplicate user!", e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return isExisted;
    }
    
    public UserDTO getUserByID(String userID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        UserDTO user = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_USER_BY_ID);
                stm.setString(1, userID);
                rs = stm.executeQuery();

                if (rs.next()) {                    
                    
                    String fullName = rs.getString("fullName");
                    String email = rs.getString("email");
                    String addr = rs.getString("address");
                    String phoneNumber = rs.getString("phoneNumber");
                    
                    user = new UserDTO("", "", fullName, addr, email, phoneNumber, null, "");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
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
