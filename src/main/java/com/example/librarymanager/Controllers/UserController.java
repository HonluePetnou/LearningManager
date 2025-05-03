package com.example.librarymanager.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    public VBox listView;
    public BorderPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void handleAddBook(ActionEvent event) {
        System.out.println("Ajout de livre déclenché !");
    }
    @FXML
    public void handleSearch(ActionEvent event) {
        // ton code ici
    }
}
