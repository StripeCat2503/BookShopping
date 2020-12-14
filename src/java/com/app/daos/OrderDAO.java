/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.dtos.OrderDTO;
import com.app.utils.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DuyNK
 */
public class OrderDAO {

    private final String SQL_INSERT_ORDER = "INSERT INTO tblOrders(userID, totalPrice, orderDate, paymentMethodID) VALUES(?, ?, ?, ?)";
    
    private final String SQL_GET_ALL_ORDERS = "SELECT orderID, userID, totalPrice, orderDate, paymentMethodID "
            + "FROM tblOrders";
    
    private final String SQL_GET_ORDER_BY_ID = "SELECT orderID, userID, totalPrice, orderDate, paymentMethodID "
            + "FROM tblOrders WHERE orderID = ?";
          
    public int insertOrder(OrderDTO order) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        int insertedOrderID = -1;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                // return order id if inserting new order executed successfully
                stm = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, order.getUserID());
                stm.setDouble(2, order.getTotalPrice());
                stm.setDate(3, order.getOrderDate());              
                stm.setString(4, order.getPaymentMethodID());              

                int rows = stm.executeUpdate();

                if (rows > 0) {
                    ResultSet generatedKeys = stm.getGeneratedKeys();
                    if(generatedKeys.next()){
                        insertedOrderID = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return insertedOrderID;
    }
    
    public List<OrderDTO> getAllOrders() throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<OrderDTO> orderList = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_ALL_ORDERS);
                rs = stm.executeQuery();

                while (rs.next()) {
                    if (orderList == null) {
                        orderList = new ArrayList<>();
                    }
                    int orderID = rs.getInt("orderID");
                    String userID = rs.getString("userID");
                    double totalPrice = rs.getDouble("totalPrice");
                    Date orderDate = rs.getDate("orderDate");
                    String paymentMethodID = rs.getString("paymentMethodID");            

                    OrderDTO order = new OrderDTO(orderID, userID, totalPrice, orderDate, paymentMethodID);

                    orderList.add(order);
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

        return orderList;
    }
    
    public OrderDTO getOrderByID(int orderID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        OrderDTO order = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_ORDER_BY_ID);
                stm.setInt(1, orderID);
                rs = stm.executeQuery();

                if (rs.next()) {                    
                    
                    String userID = rs.getString("userID");
                    double totalPrice = rs.getDouble("totalPrice");
                    Date orderDate = rs.getDate("orderDate");
                    String paymentMethodID = rs.getString("paymentMethodID");            

                    order = new OrderDTO(orderID, userID, totalPrice, orderDate, paymentMethodID);

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

        return order;
    }
}
