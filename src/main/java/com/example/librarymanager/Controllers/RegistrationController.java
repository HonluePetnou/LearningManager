package com.example.librarymanager.Controllers;
import java.sql.SQLException;

import com.example.librarymanager.Database.UserTable;
import com.example.librarymanager.Models.User;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.example.librarymanager.utils.Alertmessage;

public class RegistrationController {
    public Button RegisterButton;
    public Label LogInRedirect;
    public Label DoubleAuthentification;
    public PasswordField ConfirmedPassword;
    public PasswordField RegistrationPassword;
    public TextField RegistrationIdentifier;
    public TextField Username;
   
    public void RegisterButtonOnAction() {
        String username = Username.getText().trim();
        String password = RegistrationPassword.getText().trim();
        String confirmedPassword = ConfirmedPassword.getText().trim();
        String email = RegistrationIdentifier.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty() || email.isEmpty()) {
          Alertmessage.showAlert("ERROR","All fields are required.");
            return;
        }

        if (!password.equals(confirmedPassword)) {
            Alertmessage.showAlert("ERROR","The passwords are not the same");
            return;
        }
      try {
        User user = new User();
        user.setFullName(username);
        user.setPassword(confirmedPassword);
        user.setEmail(email);
        UserTable userTable = new UserTable() ;
        userTable.create(user);
      }catch(SQLException e){
        System.err.println("ERROR:"+e);
        Alertmessage.showAlert("ERROR","the Registration fail due to an internal problem");
        return ;
      }
       catch (Exception e) {
        System.err.println("ERROR:"+e);
        Alertmessage.showAlert("ERROR","the Registration fail due to an internal problem");
        return ;
      }
      LogInRedirectOnAction();
    }
    public void LogInRedirectOnAction() {
        // Redirect to login page
    }
}
