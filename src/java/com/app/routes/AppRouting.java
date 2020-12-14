/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.routes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DuyNK
 */
public class AppRouting {

    public static Map<String, String> routes = null;

    public static void initRoutes() {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            if (routes == null) {
                routes = new HashMap<>();
                InputStream in = loader.getResourceAsStream("routes.properties");
                Properties properties = new Properties();
                properties.load(in);
                for(Object key : properties.keySet()){
                    routes.put(key.toString(), properties.getProperty(key.toString()));
                }
            }         
            
        } catch (IOException ex) {
            Logger.getLogger(AppRouting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
