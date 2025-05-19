package com.example.librarymanager.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.librarymanager.Database.UserTable;
import com.example.librarymanager.Models.Model;
import com.example.librarymanager.Models.User;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import com.example.librarymanager.utils.Alertmessage;

/**
 * JavaFX controller for the registration view of the application.
 *
 * This controller manages user registration, handles form validation,
 * and provides navigation to the login window.
 *
 * Main features:
 * - Registers a new user with username, email, and password.
 * - Validates that all fields are filled and passwords match.
 * - Displays error messages for invalid input or registration failure.
 * - Redirects to the login window after successful registration or on user
 * request.
 *
 * Dependencies:
 * - UserTable: for creating new users in the database.
 * - Model & ViewFactory: for window management and navigation.
 * - Alertmessage: for displaying error messages.
 *
 * FXML requirements:
 * - TextField: Username, RegistrationIdentifier (for email)
 * - PasswordField: RegistrationPassword, ConfirmedPassword
 * - Label: LogInRedirect, DoubleAuthentification
 * - Button: RegisterButton
 */
public class RegistrationController implements Initializable {
  public Button RegisterButton;
  public Label LogInRedirect;
  public Label DoubleAuthentification;
  public PasswordField ConfirmedPassword;
  public PasswordField RegistrationPassword;
  public TextField RegistrationIdentifier;
  public TextField Username;

  @SuppressWarnings("unused")
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    LogInRedirect.setOnMouseClicked(event -> LogInRedirectOnAction());
    RegisterButton.setOnAction(event -> RegisterButtonOnAction());
  }

  /**
   * Handles the registration button action.
   * Validates input, creates the user, and redirects to the login window on
   * success.
   */
  public void RegisterButtonOnAction() {
    String username = Username.getText().trim();
    String password = RegistrationPassword.getText().trim();
    String confirmedPassword = ConfirmedPassword.getText().trim();
    String email = RegistrationIdentifier.getText().trim();

    if (username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty() || email.isEmpty()) {
      Alertmessage.showAlert(AlertType.ERROR, "ERROR", "All fields are required.");
      return;
    }

    if (!password.equals(confirmedPassword)) {
      Alertmessage.showAlert(AlertType.ERROR, "ERROR", "The passwords are not the same");
      return;
    }
    try {
      User user = new User();
      user.setFullName(username);
      user.setPassword(confirmedPassword);
      user.setEmail(email);
      user.setRole("ADMIN");
      UserTable userTable = new UserTable();
      userTable.create(user);
    } catch (SQLException e) {
      System.err.println("ERROR:" + e);
      Alertmessage.showAlert(AlertType.ERROR, "ERROR", "The registration failed due to an internal problem");
      return;
    } catch (Exception e) {
      System.err.println("ERROR:" + e);
      Alertmessage.showAlert(AlertType.ERROR, "ERROR", "The registration failed due to an internal problem");
      return;
    }
    LogInRedirectOnAction();
  }

  /**
   * Handles the login redirect action.
   * Closes the registration window and opens the login window.
   */
  private void LogInRedirectOnAction() {
    Stage stage = (Stage) LogInRedirect.getScene().getWindow();
    Model.getModel().getViewFactory().closeStage(stage);
    Model.getModel().getViewFactory().showLoginWindow();
  }
}
