/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.utils;

/**
 *
 * @author DuyNK
 */
public class ValidationUtils {
    public static boolean isInteger(String n){
        try {
            int number = Integer.parseInt(n);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean isDouble(String n){
        try {
            double number = Double.parseDouble(n);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
