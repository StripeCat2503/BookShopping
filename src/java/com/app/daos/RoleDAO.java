/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author DuyNK
 */
public class RoleDAO {
    
    private final String SQL_GET_ROLE_ID_BY_NAME = "SELECT roleID FROM tblRoles "
            + "WHERE roleName = ?";
    private final String SQL_GET_ROLE_NAME_BY_ID = "SELECT roleName FROM tblRoles "
            + "WHERE roleID = ?";
    
    public String getRoleIdByRoleName(String roleName) throws SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        String roleID = null;
        
        try {
            con = DBUtil.getConnection();
            if(con != null){
                stm = con.prepareStatement(SQL_GET_ROLE_ID_BY_NAME);
                stm.setString(1, roleName);
                rs = stm.executeQuery();
                if(rs.next()){
                    roleID = rs.getString("roleID");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(rs != null) rs.close();
            if(stm != null) stm.close();
            if(con != null) con.close();
        }
        
        return roleID;
    }
    
    public String getRoleNameByRoleID(String roleID) throws SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        String roleName = null;
        
        try {
            con = DBUtil.getConnection();
            if(con != null){
                stm = con.prepareStatement(SQL_GET_ROLE_NAME_BY_ID);
                stm.setString(1, roleID);
                rs = stm.executeQuery();
                if(rs.next()){
                    roleName = rs.getString("roleName");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(rs != null) rs.close();
            if(stm != null) stm.close();
            if(con != null) con.close();
        }
        
        return roleName;
    }
}
