package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public BorderPane dash_Parent;

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Users" -> dash_Parent.setCenter(Model.getModel().getViewFactory().getUsersView());
                case "Books" -> dash_Parent.setCenter(Model.getModel().getViewFactory().getBooksView());
                default -> dash_Parent.setCenter(Model.getModel().getViewFactory().getDashboardView());
            }
        });
    }
}
