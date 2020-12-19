/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author DuyNK
 */
public class DBUtil {  
    
    public static Connection getConnection() throws SQLException, NamingException{
        Connection con = null;
        
        Context initContext = new InitialContext();
        Context context = (Context) initContext.lookup("java:comp/env");
        DataSource ds = (DataSource) context.lookup("BookShoppingDB");
                
        con = ds.getConnection();
        
        return con;
    }
}
