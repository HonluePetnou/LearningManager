package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField Identifier;
    public PasswordField Password;
    public Label LostPassword;
    public Button LoginButton;
    public Label RegistrationRedirect;
    public Label Login_err;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginButton.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) Login_err.getScene().getWindow();
        Model.getModel().getViewFactory().closeStage(stage);
        Model.getModel().getViewFactory().showDashboardWindow();
    }
}
