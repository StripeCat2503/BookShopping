/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.beans;

import com.app.dtos.UserDTO;
import java.io.Serializable;

/**
 *
 * @author DuyNK
 */
public class CheckoutValidationBean implements Serializable{

    private String fullNameError;
    private String emailError;
    private String addressError;
    private String phoneError;   

    public CheckoutValidationBean() {
    }


    public String getFullNameError() {
        return fullNameError;
    }

    public void setFullNameError(String fullNameError) {
        this.fullNameError = fullNameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }
    
    private final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    private final String REGEX_PHONE = "[0-9]{10}";
    
    public boolean validateUser(UserDTO user){
        boolean isValidUser = true;
              
        if(user.getFullName().length() == 0){
            this.fullNameError = "Please enter full name!";
            isValidUser = false;
        }
        if(user.getEmail().length() == 0){
            this.emailError = "Please enter email!";
            isValidUser = false;
        }
        if(user.getPhoneNumber().length() == 0){
            this.phoneError = "Please enter phone number!";
            isValidUser = false;
        }
        if(user.getAddress().length() == 0){
            this.addressError = "Please enter address!";
            isValidUser = false;
        }
      
        if(!user.getEmail().matches(REGEX_EMAIL)){
            this.emailError = "Invalid email!";
            isValidUser = false;
        }
        if(!user.getPhoneNumber().matches(REGEX_PHONE)){
            this.phoneError = "Invalid phone number!";
            isValidUser = false;
        }
        
        return isValidUser;
    }
}
