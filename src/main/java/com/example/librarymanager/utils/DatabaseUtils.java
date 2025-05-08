package com.example.librarymanager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private static final String DB_URL = "jdbc:sqlite:library.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("DatabaseUtils:"+e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            // Enable foreign keys for SQLite
            try (java.sql.Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            } catch (SQLException e) {
                System.err.println("Failed to enable foreign keys: " + e.getMessage());
                // Continue even if this fails
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to get database connection: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
