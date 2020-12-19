/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.dtos.OrderStatusDTO;
import com.app.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DuyNK
 */
public class OrderStatusDAO {
    private final String SQL_GET_STATUS_ID = "SELECT statusID FROM tblOrderStatus "
            + "WHERE statusName = ?";
    private final String SQL_GET_BY_ID = "SELECT statusID, statusName FROM tblOrderStatus "
            + "WHERE statusID = ?";
    private final String SQL_GET_ALL = "SELECT statusID, statusName FROM tblOrderStatus";    

    public String getOrderStatusID(String statusName) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String statusID = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_STATUS_ID);
                stm.setString(1, statusName);
                rs = stm.executeQuery();
                if (rs.next()) {
                    statusID = rs.getString("statusID");
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

        return statusID;
    }
    
    public OrderStatusDTO getOrderStatusByID(String statusID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        OrderStatusDTO status = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_BY_ID);
                stm.setString(1, statusID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String statusName = rs.getString("statusName");
                    status = new OrderStatusDTO(statusID, statusName);
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

        return status;
    }
    
    public List<OrderStatusDTO> getAllOrderStatus() throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<OrderStatusDTO> statusList = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_ALL);
                rs = stm.executeQuery();

                while (rs.next()) {
                    if (statusList == null) {
                        statusList = new ArrayList<>();
                    }
                    
                    String statusID = rs.getString("statusID");
                    String statusName = rs.getString("statusName");             
                    OrderStatusDTO status = new OrderStatusDTO(statusID, statusName);

                    statusList.add(status);
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

        return statusList;
    }
}
