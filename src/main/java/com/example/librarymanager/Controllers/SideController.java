package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class SideController implements Initializable {
    public Button dashboardBtn;
    public Button bookBtn;
    public Button userBtn;
    public Button borrowBtn;
    public Button settingsBtn;
    public Button helpBtn;
    public Button logoutBtn;
    public Button copyrigthBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
    }
    @SuppressWarnings("unused")
    private void addListener(){
        dashboardBtn.setOnAction(event -> onDashboard());
        bookBtn.setOnAction(event -> onBooking());
        userBtn.setOnAction(event -> onUsers());
        borrowBtn.setOnAction(event -> onBorrows());
        settingsBtn.setOnAction(event -> onSettings());
        helpBtn.setOnAction(event -> onHelp());
        logoutBtn.setOnAction(event -> onLogout());
        copyrigthBtn.setOnAction(event -> onCopyright());

        // Add listener for sidebar item selection
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            updateButtonStyles(newValue);
        });

        // Set initial active state
        updateButtonStyles("Dashboard");
    }
    private void onDashboard(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Dashboard");
    }
    private void onUsers(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Users");
    }
    private void onBooking(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Books");
    }
    private void onBorrows(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Borrows");
    }
    private void onSettings(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Settings");
    }
    private void onHelp(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Help");
    }
    private void onLogout(){
        // Get the current stage from any button
        Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
        // Close the current stage
        Model.getModel().getViewFactory().closeStage(currentStage);
        // Show the login window
        Model.getModel().getViewFactory().showLoginWindow();
    }
    private void onCopyright(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Copyright");
    }

    private void updateButtonStyles(String selectedItem) {
        // Remove active class from all buttons
        dashboardBtn.getStyleClass().remove("active");
        bookBtn.getStyleClass().remove("active");
        userBtn.getStyleClass().remove("active");
        borrowBtn.getStyleClass().remove("active");
        settingsBtn.getStyleClass().remove("active");
        helpBtn.getStyleClass().remove("active");
        copyrigthBtn.getStyleClass().remove("active");

        // Add active class to selected button
        switch (selectedItem) {
            case "Dashboard" -> dashboardBtn.getStyleClass().add("active");
            case "Books" -> bookBtn.getStyleClass().add("active");
            case "Users" -> userBtn.getStyleClass().add("active");
            case "Borrows" -> borrowBtn.getStyleClass().add("active");
            case "Settings" -> settingsBtn.getStyleClass().add("active");
            case "Help" -> helpBtn.getStyleClass().add("active");
            case "Copyright" -> copyrigthBtn.getStyleClass().add("active");
        }
    }
}
