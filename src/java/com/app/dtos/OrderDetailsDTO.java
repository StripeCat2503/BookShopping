/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dtos;

/**
 *
 * @author DuyNK
 */
public class OrderDetailsDTO {
    private int orderDetailsID;
    private ProductDTO product;
    private int orderID;
    private double price;
    private int quantity;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(ProductDTO product, int orderID, double price, int quantity) {
        this.product = product;
        this.orderID = orderID;
        this.price = price;
        this.quantity = quantity;
    }
   
    public int getOrderDetailsID() {
        return orderDetailsID;
    }

    public void setOrderDetailsID(int orderDetailsID) {
        this.orderDetailsID = orderDetailsID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
    
    
}
