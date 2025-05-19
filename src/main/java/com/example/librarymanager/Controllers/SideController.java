package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the sidebar navigation of the application.
 *
 * This controller manages the sidebar menu, allowing users to navigate between
 * different sections of the application (Dashboard, Books, Users, Borrows, Settings, Help).
 * It also handles the logout action and updates the visual state of the sidebar buttons.
 *
 * Main features:
 * - Handles navigation button clicks and updates the selected menu item.
 * - Listens for changes in the selected menu item and updates button styles accordingly.
 * - Handles logout by closing the current window and opening the login window.
 *
 * Dependencies:
 * - Model & ViewFactory: for managing navigation and window transitions.
 *
 * FXML requirements:
 * - Buttons: dashboardBtn, bookBtn, userBtn, borrowBtn, settingsBtn, helpBtn, logoutBtn
 * - Label: copyrigthBtn
 */
public class SideController implements Initializable {
    public Button dashboardBtn;
    public Button bookBtn;
    public Button userBtn;
    public Button borrowBtn;
    public Button settingsBtn;
    public Button helpBtn;
    public Button logoutBtn;
    public Label copyrigthBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
    }

    /**
     * Sets up event listeners for sidebar buttons and menu item selection.
     */
    @SuppressWarnings("unused")
    private void addListener() {
        dashboardBtn.setOnAction(event -> onDashboard());
        bookBtn.setOnAction(event -> onBooking());
        userBtn.setOnAction(event -> onUsers());
        borrowBtn.setOnAction(event -> onBorrows());
        settingsBtn.setOnAction(event -> onSettings());
        helpBtn.setOnAction(event -> onHelp());
        logoutBtn.setOnAction(event -> onLogout());

        // Add listener for sidebar item selection
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            updateButtonStyles(newValue);
        });

        // Set initial active state
        updateButtonStyles("Dashboard");
    }

    /**
     * Handles navigation to the Dashboard view.
     */
    private void onDashboard() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Dashboard");
    }

    /**
     * Handles navigation to the Users view.
     */
    private void onUsers() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Users");
    }

    /**
     * Handles navigation to the Books view.
     */
    private void onBooking() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Books");
    }

    /**
     * Handles navigation to the Borrows view.
     */
    private void onBorrows() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Borrows");
    }

    /**
     * Handles navigation to the Settings view.
     */
    private void onSettings() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Settings");
    }

    /**
     * Handles navigation to the Help view.
     */
    private void onHelp() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Help");
    }

    /**
     * Handles the logout action: closes the current window and opens the login window.
     */
    private void onLogout() {
        // Get the current stage from any button
        Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
        // Close the current stage
        Model.getModel().getViewFactory().closeStage(currentStage);
        // Show the login window
        Model.getModel().getViewFactory().showLoginWindow();
    }

    /**
     * Updates the visual style of sidebar buttons based on the selected menu item.
     * @param selectedItem the currently selected menu item
     */
    private void updateButtonStyles(String selectedItem) {
        // Remove active class from all buttons
        dashboardBtn.getStyleClass().remove("active");
        bookBtn.getStyleClass().remove("active");
        userBtn.getStyleClass().remove("active");
        borrowBtn.getStyleClass().remove("active");
        settingsBtn.getStyleClass().remove("active");
        helpBtn.getStyleClass().remove("active");

        // Add active class to selected button
        switch (selectedItem) {
            case "Dashboard" -> dashboardBtn.getStyleClass().add("active");
            case "Books" -> bookBtn.getStyleClass().add("active");
            case "Users" -> userBtn.getStyleClass().add("active");
            case "Borrows" -> borrowBtn.getStyleClass().add("active");
            case "Settings" -> settingsBtn.getStyleClass().add("active");
            case "Help" -> helpBtn.getStyleClass().add("active");
        }
    }
}
