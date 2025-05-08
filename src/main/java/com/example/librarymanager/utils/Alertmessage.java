package com.example.librarymanager.utils;

import javafx.scene.control.Alert;

public class Alertmessage {
      public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
