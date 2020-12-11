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
public class UserRegisterValidationBean implements Serializable{
    private String userIdError;
    private String passwordError;
    private String fullNameError;
    private String emailError;
    private String addressError;
    private String phoneError;   

    public UserRegisterValidationBean() {
    }

    public String getUserIdError() {
        return userIdError;
    }

    public void setUserIdError(String userIdError) {
        this.userIdError = userIdError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
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
    
    private final String REGEX_USERNAME = "^[a-z]\\w{4,14}$";
    private final String REGEX_PASSWORD = "^[A-Z]\\w{4,14}$";
    private final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    private final String REGEX_PHONE = "[0-9]{10}";
    
    public boolean validateUser(UserDTO user){
        boolean isValidUser = true;
        
        if(user.getUserID().length() == 0){
            this.userIdError = "Please enter user id!";
            isValidUser = false;
        }
        if(user.getPassword().length() == 0){
            this.passwordError = "Please enter password!";
            isValidUser = false;
        }
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
        
        if(!user.getUserID().matches(REGEX_USERNAME)){
            this.userIdError = "User ID must start with a letter and contains 5 - 15 characters!";
            isValidUser = false;
        }
        if(!user.getPassword().matches(REGEX_PASSWORD)){
            this.passwordError = "Password must start with an uppercase letter and contains 5 - 15 characters!";
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
