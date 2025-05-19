/**
 * Main entry point for the JavaFX Library Manager application.
 *
 * This class initializes the application, loads the SQLite JDBC driver,
 * and launches the JavaFX UI by displaying the login window.
 *
 * Main features:
 * - Loads the SQLite JDBC driver at startup to ensure database connectivity.
 * - Starts the JavaFX application and displays the login window.
 * - Handles and logs any startup errors.
 *
 * Usage:
 * - Run the main method to launch the application.
 * - The application will display the login window as the first screen.
 *
 * Dependencies:
 * - Model: for accessing the ViewFactory and managing application state.
 * - ViewFactory: for displaying the login window.
 * - JavaFX Application and Stage.
 */
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
