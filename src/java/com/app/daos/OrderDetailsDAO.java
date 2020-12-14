/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.dtos.OrderDetailsDTO;
import com.app.dtos.ProductDTO;
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
public class OrderDetailsDAO {
    private final String SQL_INSERT_ORDER_DETAIL = "INSERT INTO tblOrderDetails(productID, orderID, price, quantity) VALUES(?, ?, ?, ?)";
    private final String SQL_GET_ALL_BY_ORDER_ID = "SELECT p.productName, p.image, d.price, d.quantity "
            + "FROM tblOrderDetails AS d JOIN tblProducts AS p ON d.productID = p.productID "
            + "WHERE d.orderID = ?";
    

    public boolean insertOrderDetails(OrderDetailsDTO orderDetails) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean success = false;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_INSERT_ORDER_DETAIL);

                stm.setInt(1, orderDetails.getProduct().getProductID());
                stm.setInt(2, orderDetails.getOrderID());
                stm.setDouble(3, orderDetails.getPrice());              
                stm.setInt(4, orderDetails.getQuantity());              

                int rows = stm.executeUpdate();

                if (rows > 0) {
                    success = true;
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

        return success;
    }
    
    public List<OrderDetailsDTO> getAllOrderDetailsByOrderID(int orderID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<OrderDetailsDTO> details = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_ALL_BY_ORDER_ID);
                stm.setInt(1, orderID);
                rs = stm.executeQuery();

                while (rs.next()) {
                    if (details == null) {
                        details = new ArrayList<>();
                    }
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    
                    OrderDetailsDTO detail = new OrderDetailsDTO();
                    ProductDTO product = new ProductDTO();
                    product.setProductName(productName);
                    product.setImage(image);
                    product.setPrice(price);
                 
                    detail.setProduct(product);
                    detail.setPrice(price);
                    detail.setQuantity(quantity);
                    
                    details.add(detail);
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

        return details;
    }
}
