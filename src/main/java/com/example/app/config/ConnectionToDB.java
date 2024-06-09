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

    private ConnectionToDB(){
    }
    public static ConnectionToDB initializeConnection(){

        ConnectionToDB localInstance = new ConnectionToDB();

        try {
            Class.forName(PropertiesFileInit.getProperties(DRIVER));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return localInstance;

    }

    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(
                    PropertiesFileInit.getProperties(URL),
                    PropertiesFileInit.getProperties(USERNAME),
                    PropertiesFileInit.getProperties(PASSWORD)
            );
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return connection;
    }


}
