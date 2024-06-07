package com.example.app.exception;

import java.sql.SQLException;

public class DataRepositoryException extends RuntimeException {
    public DataRepositoryException(String message) {
        super(message);
    }

    public DataRepositoryException(SQLException exception) {
        super(exception);
    }
}
