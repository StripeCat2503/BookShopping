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
public class UserDTO {

    private String userID;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String address;
    private String email;
    private String phoneNumber;
    private Date createdDate;
    private RoleDTO role;

    public UserDTO() {
    }

    public UserDTO(String userID, String password, String fullName, String address, String email, String phoneNumber, Date createdDate, RoleDTO role) {
        this.userID = userID;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.role = role;
    }

    public UserDTO(String userID, String password, String confirmPassword, String fullName, String address, String email, String phoneNumber, Date createdDate, RoleDTO role) {
        this.userID = userID;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.role = role;
    }
    

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


}
