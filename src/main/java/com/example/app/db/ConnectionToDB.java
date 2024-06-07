package com.example.app.db;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionToDB implements ConnectionInterface{

    private static final String DRIVER_CLASS = "db.driver-class-name";
    private static final String URL = "db.url";
    private static final String USERNAME = "db.username";
    private static final String PASSWORD= "db.password";

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }


}
