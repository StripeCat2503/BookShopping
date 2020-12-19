/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.routes;

import com.app.utils.MyUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static List<String> adminRoutes = null;
    public static List<String> userRoutes = null;
    public static List<String> guestRoutes = null;

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
                
                in.close();
            }    
            
            if(adminRoutes == null){
                adminRoutes = new ArrayList<>();
                String routeData = MyUtils.readTextFile("admin-routes.txt");
                for(String r : routeData.split("\n")){
                    adminRoutes.add(r.replace("\r", ""));
                }
            }
            if(userRoutes == null){
                userRoutes = new ArrayList<>();
                String routeData = MyUtils.readTextFile("user-routes.txt");
                for(String r : routeData.split("\n")){
                    userRoutes.add(r.replace("\r", ""));
                }
            }
            if(guestRoutes == null){
                guestRoutes = new ArrayList<>();
                String routeData = MyUtils.readTextFile("guest-routes.txt");
                for(String r : routeData.split("\n")){
                    guestRoutes.add(r.replace("\r", ""));
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AppRouting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
