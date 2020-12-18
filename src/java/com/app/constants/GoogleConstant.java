/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.constants;

/**
 *
 * @author DuyNK
 */
public class GoogleConstant {
    public static final String CLIENT_ID = "197892025877-qe8gqh72tj7v1rk77hle8m327ji98me6.apps.googleusercontent.com";
    public static final String CLIENT_SECRECT = "Vrvme2RhhtjjdsxIykrIpiJN";
    public static final String REDIRECT_URI = "http://localhost:8080/BookShopping/login-google";
    public static final String LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static final String LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo"
            + "?access_token=";
    public static final String GRANT_TYPE = "authorization_code";
}
