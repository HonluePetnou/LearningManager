package com.example.librarymanager.utils;

import javafx.scene.control.Alert;

/**
 * Utility class for displaying alert messages in the application.
 *
 * This class provides a static method to show JavaFX alert dialogs of various
 * types
 * (information, warning, error, etc.) with a specified title and message.
 *
 * Main features:
 * - Displays an alert dialog with the given type, title, and message.
 * - Sets the header text to null for a cleaner dialog appearance.
 * - Waits for the user to close the alert before continuing.
 *
 * Usage:
 * Alertmessage.showAlert(AlertType.INFORMATION, "Title", "Message");
 *
 * Dependencies:
 * - JavaFX Alert and AlertType.
 */
public class Alertmessage {
  /**
   * Displays a JavaFX alert dialog with the specified type, title, and message.
   * 
   * @param type    the type of alert (e.g., INFORMATION, WARNING, ERROR)
   * @param title   the title of the alert dialog
   * @param message the message to display in the alert dialog
   */
  public static void showAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
