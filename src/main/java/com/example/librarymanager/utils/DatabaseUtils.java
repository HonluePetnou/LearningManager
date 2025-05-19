package com.example.librarymanager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 *
 * This class provides static methods to obtain and close connections to the
 * application's
 * SQLite database. It ensures the JDBC driver is loaded and enables foreign key
 * support
 * for SQLite connections.
 *
 * Main features:
 * - Loads the SQLite JDBC driver on class initialization.
 * - Provides a method to obtain a database connection.
 * - Enables foreign key constraints for each connection.
 * - Provides a method to safely close database connections.
 *
 * Usage:
 * - Use DatabaseUtils.getConnection() to obtain a new database connection.
 * - Use DatabaseUtils.closeConnection(conn) to close a connection.
 *
 * Dependencies:
 * - SQLite JDBC driver.
 * - java.sql.Connection, DriverManager, SQLException.
 */
public class DatabaseUtils {

    private static final String DB_URL = "jdbc:sqlite:library.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("DatabaseUtils:" + e);
        }
    }

    /**
     * Obtains a new connection to the SQLite database.
     * Enables foreign key constraints for the connection.
     * 
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Closes the given database connection if it is not null.
     * 
     * @param conn the Connection to close
     */
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
