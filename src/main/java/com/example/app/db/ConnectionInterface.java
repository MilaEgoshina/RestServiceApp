package com.example.app.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionInterface {

    Connection getConnection() throws SQLException;
}
