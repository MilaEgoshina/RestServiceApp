package com.example.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB{

    private static final String DRIVER = "driver-class-name";
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD= "password";
    private static Connection connection;

    public static Connection get

    private static void driver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(
                    PropertiesFileInit.getProperties(URL),
                    PropertiesFileInit.getProperties(USERNAME),
                    PropertiesFileInit.getProperties(PASSWORD)
            );
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }


}
