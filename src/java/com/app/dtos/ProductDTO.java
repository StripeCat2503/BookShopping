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
public class ProductDTO {

    private int productID;
    private String productName;
    private double price;
    private int quantity;
    private boolean status;
    private String image;
    private String description;
    private ProductCategoryDTO category;

    public ProductDTO() {
    }

    public ProductDTO(String productName, double price, int quantity, boolean status, String image, String description, ProductCategoryDTO category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.image = image;
        this.description = description;
        this.category = category;
    }

    public ProductDTO(int productID, String productName, double price, int quantity, boolean status, String image, String description, ProductCategoryDTO category) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.image = image;
        this.description = description;
        this.category = category;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(ProductCategoryDTO category) {
        this.category = category;
    }

    

}
