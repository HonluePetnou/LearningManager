package com.example.librarymanager.Controllers;

import com.example.librarymanager.Database.UserTable;
import com.example.librarymanager.Models.Model;
import com.example.librarymanager.Models.User;
import com.example.librarymanager.utils.CurrentUser;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the login view of the application.
 *
 * This controller manages user authentication, handles login attempts,
 * and provides navigation to the registration window.
 *
 * Main features:
 * - Authenticates users using their identifier and password.
 * - Displays error messages for invalid credentials or internal errors.
 * - Redirects to the registration window if the user does not have an account.
 * - Opens the main window upon successful login.
 *
 * Dependencies:
 * - UserTable: for user authentication.
 * - Model & ViewFactory: for window management and navigation.
 * - CurrentUser: for storing the authenticated user.
 *
 * FXML requirements:
 * - TextField: Identifier and Username
 * - PasswordField: Password
 * - Label: LostPassword, RegistrationRedirect, Login_err
 * - Button: LoginButton
 */
public class LoginController implements Initializable {
    public TextField Identifier;
    public PasswordField Password;
    public Label LostPassword;
    public Button LoginButton;
    public Label RegistrationRedirect;
    public Label Login_err;

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginButton.setOnAction(event -> onLogin());
        RegistrationRedirect.setOnMouseClicked(event -> RegistrationRedirectOnAction());
        Login_err.setText("");
    }

    /**
     * Handles the login button action.
     * Authenticates the user and opens the application if successful.
     * Displays an error message if authentication fails.
     */
    private void onLogin() {
        Stage stage = (Stage) Login_err.getScene().getWindow();
        try {
            User user = UserTable.Authenticate(Identifier.getText(), Password.getText());
            String role = user.getRole();
            CurrentUser.setUser(user);
            if (role == null || role.equals("MEMBER")) {
                Login_err.setText("Invalid identifier or password");
                return;
            }
            Model.getModel().getViewFactory().closeStage(stage);
            Model.getModel().getViewFactory().showMainWindow();
        } catch (SQLException e) {
            Login_err.setText("internal error");
            System.err.println("login error" + e);
            return;
        }
    }

    /**
     * Handles the registration redirect action.
     * Closes the login window and opens the registration window.
     */
    private void RegistrationRedirectOnAction() {
        Stage stage = (Stage) Login_err.getScene().getWindow();
        Model.getModel().getViewFactory().closeStage(stage);
        Model.getModel().getViewFactory().showRegistrationWindow();
    }
}
