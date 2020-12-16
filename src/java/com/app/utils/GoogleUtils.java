/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.utils;

import com.app.constants.GoogleConstant;
import com.app.dtos.GoogleUserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author DuyNK
 */
public class GoogleUtils {

    public static String getToken(final String code) throws ClientProtocolException, IOException {
        
        String response = Request.Post(GoogleConstant.LINK_GET_TOKEN)
                .bodyForm(Form.form()                    
                        .add("client_id", GoogleConstant.CLIENT_ID)
                        .add("client_secret", GoogleConstant.CLIENT_SECRECT)
                        .add("redirect_uri", GoogleConstant.REDIRECT_URI).add("code", code)
                        .add("grant_type", GoogleConstant.GRANT_TYPE).build())
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static GoogleUserDTO getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = GoogleConstant.LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        
        GoogleUserDTO user = new Gson().fromJson(response, GoogleUserDTO.class);
       
        return user;
    }
}
