/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dtos;

import java.sql.Date;

/**
 *
 * @author DuyNK
 */
public class OrderDTO {
    private int orderID;
    private String userID;
    private double totalPrice;
    private Date orderDate;
    private String paymentMethodID;

    public OrderDTO() {
    }

    public OrderDTO(int orderID, String userID, double totalPrice, Date orderDate, String paymentMethodID) {
        this.orderID = orderID;
        this.userID = userID;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.paymentMethodID = paymentMethodID;
    }

    public OrderDTO(String userID, double totalPrice, Date orderDate, String paymentMethodID) {
        this.userID = userID;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.paymentMethodID = paymentMethodID;
    }

    

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethodID() {
        return paymentMethodID;
    }

    public void setPaymentMethodID(String paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }
    
    
}
