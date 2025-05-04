package com.example.librarymanager.Models;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    
    private static Connection connection;

    @SuppressWarnings("exports")
    public static Connection getConnection () throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:library.db");
            enableForeignKeys(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void enableForeignKeys(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
