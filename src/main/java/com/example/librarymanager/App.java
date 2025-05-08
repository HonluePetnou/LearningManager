package com.example.librarymanager;

import com.example.librarymanager.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    static {
        // Add module opens for SQLite JDBC to avoid warnings
        try {
            // Ensure SQLite JDBC driver is loaded early
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQLite JDBC driver: " + e.getMessage());
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Model.getModel().getViewFactory().showLoginWindow();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
     