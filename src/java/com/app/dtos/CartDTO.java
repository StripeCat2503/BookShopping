/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dtos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DuyNK
 */
public class CartDTO {
    private Map<Integer, ProductDTO> items;
    private double total;
    private int numberOfItems;

    public Map<Integer, ProductDTO> getItems() {
        return this.items;
    }

    public CartDTO() {
        total = 0;
        numberOfItems = 0;
    }

    public void addItemToCart(ProductDTO product){
        if(this.items == null){
            this.items = new HashMap<>();
        }
        
        if(this.items.containsKey(product.getProductID())){
            int currentQuantity = product.getQuantity();
            product.setQuantity(currentQuantity + 1);
        }
        
        this.items.put(product.getProductID(), product);
        updateTotal();
        numberOfItems = this.items.size();
    }
    
    public void removeItemFromCart(int productID){
        if(this.items == null) return;
        
        if(this.items.containsKey(productID)){
            this.items.remove(productID);
            updateTotal();
            numberOfItems = this.items.size();
        }
    }
    
    public void updateCart(int productID, int newQuantity){
        if(this.items == null) return;
        
        if(this.items.containsKey(productID)){
            ProductDTO p = this.items.get(productID);
            p.setQuantity(newQuantity);
            this.items.replace(productID, p);
            updateTotal();
        }
    }
    
    private void updateTotal(){
        if(this.items != null){
            this.total = 0;
            for(ProductDTO p : this.items.values()){
                this.total += p.getPrice() * p.getQuantity();
            }
        }
    }

    public double getTotal() {
        return total;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    
}
