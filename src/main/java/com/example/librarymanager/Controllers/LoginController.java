package com.example.librarymanager.Controllers;

import com.example.librarymanager.Database.UserTable;
import com.example.librarymanager.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
        Login_err.setText("");
    }

    private void onLogin() {
        Stage stage = (Stage) Login_err.getScene().getWindow();
       try {
       String role = UserTable.Authenticate( Identifier.getText(), Password.getText());
        if (role == null || role.equals("MEMBER")) {
            Login_err.setText("Invalid email or password");
            throw new SQLException("Invalid email or password");
        }
       } catch (SQLException e) {
        Login_err.setText("Invalid email or password");
        System.err.println(e);
        return ;
       }   
        Model.getModel().getViewFactory().closeStage(stage);
        Model.getModel().getViewFactory().showDashboardWindow();
    }
}
