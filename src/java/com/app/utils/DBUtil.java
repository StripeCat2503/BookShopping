/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DuyNK
 */
public class DBUtil {
    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    private static final String IP = "localhost";
    private static final int PORT = 1433;
    private static final String DB_NAME = "BookShopping";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName(JDBC_DRIVER);
        Connection con = null;
        String url = "jdbc:sqlserver://" + IP + ":" + PORT + ";databaseName=" + DB_NAME;
                
        con = DriverManager.getConnection(url, USER, PASSWORD);
        
        return con;
    }
}
