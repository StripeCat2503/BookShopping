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
public class ProductCategoryDTO {
    private int categoryID;
    private String categoryName;

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public ProductCategoryDTO(int categoryID) {
        this.categoryID = categoryID;
    }
    

    public ProductCategoryDTO(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }
    
    

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    
}
