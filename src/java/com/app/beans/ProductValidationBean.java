/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.beans;

import com.app.dtos.ProductDTO;
import java.io.Serializable;
import java.sql.SQLException;

/**
 *
 * @author DuyNK
 */
public class ProductValidationBean implements Serializable{
    private String productNameErr;
    private String priceErr;
    private String quantityErr;
       
    private final String REGEX_NUMBER = "^[0-9]+$";       
    

    public ProductValidationBean() {
    }

    public String getProductNameErr() {
        return productNameErr;
    }

    public void setProductNameErr(String productNameErr) {
        this.productNameErr = productNameErr;
    }

    public String getPriceErr() {
        return priceErr;
    }

    public void setPriceErr(String priceErr) {
        this.priceErr = priceErr;
    }

    public String getQuantityErr() {
        return quantityErr;
    }

    public void setQuantityErr(String quantityErr) {
        this.quantityErr = quantityErr;
    }
    
    public boolean isValidProduct(ProductDTO product) throws SQLException{
        boolean isValid = true;
        
        if(product.getProductName().length() < 5 || product.getProductName().length() > 50){
            isValid = false;
            this.productNameErr = "Product's name must contains 5-50 characters!";
        }
        if(product.getPrice() < 0){
            isValid = false;
            this.priceErr = "Invalid price!";
        }
        if(product.getQuantity() < 0){
            isValid = false;
            this.quantityErr = "Invalid quantity!";
        }               
        
        return isValid;
    }
}
