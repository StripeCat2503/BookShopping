/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.dtos.PaymentMethodDTO;
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
public class PaymentMethodDAO {
    
    private final String SQL_GET_ALL_METHODS = "SELECT paymentMethodID, paymentMethodName FROM tblPaymentMethods";
    private final String SQL_GET_METHOD_BY_ID = "SELECT paymentMethodID, paymentMethodName FROM tblPaymentMethods "
            + "WHERE paymentMethodID = ?";
    
    
    public List<PaymentMethodDTO> getAllPaymentMethods() throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<PaymentMethodDTO> methods = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_ALL_METHODS);
                rs = stm.executeQuery();

                while (rs.next()) {
                    if (methods == null) {
                        methods = new ArrayList<>();
                    }
                    String paymentMethodID = rs.getString("paymentMethodID");
                    String paymentMethodName = rs.getString("paymentMethodName");
                  
                    PaymentMethodDTO method = new PaymentMethodDTO(paymentMethodID, paymentMethodName);

                    methods.add(method);
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

        return methods;
    }
    
    public PaymentMethodDTO getPaymentMethodByID(String paymentMethodID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        PaymentMethodDTO method = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_METHOD_BY_ID);
                stm.setString(1, paymentMethodID);
                rs = stm.executeQuery();

                if (rs.next()) {                    
                    
                    String paymentMethodName = rs.getString("paymentMethodName");            

                    method = new PaymentMethodDTO(paymentMethodID, paymentMethodName);

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

        return method;
    }
}
