package com.example.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB{

    private static final String DRIVER = "driver-class-name";
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD= "password";
    private static ConnectionToDB connectionToDBInstance;

    public static ConnectionToDB initialize() {
        if (connectionToDBInstance == null) {
            connectionToDBInstance = new ConnectionToDB();
            loadDriver(PropertiesFileInit.getProperties(DRIVER));
        }
        return connectionToDBInstance;
    }

    private static void loadDriver(String driverClass) {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                    PropertiesFileInit.getProperties(URL),
                    PropertiesFileInit.getProperties(USERNAME),
                    PropertiesFileInit.getProperties(PASSWORD));

    }


}
