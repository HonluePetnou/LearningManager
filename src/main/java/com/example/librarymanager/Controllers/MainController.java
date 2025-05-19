package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the main view of the application.
 *
 * This controller manages the main layout of the application, including
 * switching
 * the central content area based on the selected menu item from the sidebar.
 *
 * Main features:
 * - Listens for changes in the sidebar menu selection.
 * - Dynamically updates the center of the application with the corresponding
 * view
 * (Users, Books, Borrows, Help, or Dashboard).
 *
 * Dependencies:
 * - Model: provides access to the ViewFactory and the different views.
 * - ViewFactory: supplies the views to be displayed in the application.
 *
 * FXML requirements:
 * - BorderPane: dash_Parent (the main layout container for the application)
 */
public class MainController implements Initializable {
    public BorderPane dash_Parent;

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Users" -> dash_Parent.setCenter(Model.getModel().getViewFactory().getUsersView());
                case "Books" -> dash_Parent.setCenter(Model.getModel().getViewFactory().getBooksView());
                case "Borrows" -> dash_Parent.setCenter(Model.getModel().getViewFactory().getBorrowsView());
                case "Help" -> dash_Parent.setCenter(Model.getModel().getViewFactory().getHelpView());
                default -> dash_Parent.setCenter(Model.getModel().getViewFactory().getDashboardView());
            }
        });
    }
}
