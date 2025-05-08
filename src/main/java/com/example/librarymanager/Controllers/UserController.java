package com.example.librarymanager.Controllers;

import com.example.librarymanager.Database.UserTable;
import com.example.librarymanager.Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.librarymanager.utils.Alertmessage;

public class UserController implements Initializable {

    @FXML private TableView<User> userTableView;
    @FXML private TableColumn<User, Integer> idCol;
    @FXML private TableColumn<User, String> fullNameCol;
    @FXML private TableColumn<User, String> emailCol;
    @FXML private TableColumn<User, Integer> phoneCol;
    @FXML private TableColumn<User, LocalDate> birthdateCol;
    @FXML private TableColumn<User, String> genderCol;
    @FXML private TableColumn<User, String> addressCol;

    // Match these with FXML field names
    @FXML private TextField authorField;      // First Name
    @FXML private TextField titleField;       // Last Name
    @FXML private TextField yearField;        // Email
    @FXML private TextField yearField1;       // Phone
    @FXML private TextField yearField11;      // Address
    @FXML private DatePicker birthdatePicker;
    @FXML private ToggleGroup genderGroup;
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;
    @FXML private TextField searchField;

   private  UserTable userTable = new UserTable();
   private  int numberOfUsers = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize gender selection
        genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);

        // Configure table columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        birthdateCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Load initial data
        numberOfUsers = getInitialList().size();
        userTableView.setItems(getInitialList());
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        try {
            // Get values from form (using FXML field names)
            String firstName = authorField.getText().trim();
            String lastName = titleField.getText().trim();
            String email = yearField.getText().trim();
            int phone = Integer.parseInt(yearField1.getText().trim());
            String address = yearField11.getText().trim();
            LocalDate birthdate = birthdatePicker.getValue();
            String gender = maleRadio.isSelected() ? "Male" : "Female";

            // Validate required fields
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                    address.isEmpty() || birthdate == null) {
                Alertmessage.showAlert("Error", "All fields are required");
                return;
            }

            // Create and add new user
            User newUser = new User(firstName, lastName, email, phone, birthdate, gender, address);
            // add to database
            newUser.setRole("MEMBER");
            newUser.setPassword("defaultpassword");
            userTable.create(newUser);
            // add to table view
            numberOfUsers++;
            newUser.setUser_id(numberOfUsers);
            userTableView.getItems().add(newUser);

            // Clear form
            clearFormFields();

        } catch (NumberFormatException e) {
            Alertmessage.showAlert("Error", "Please enter a valid phone number");
        } 
        catch(SQLException e){
            Alertmessage.showAlert("Error ", "ADD NEW USER  FAIL");
        }
        catch (Exception e) {
            Alertmessage.showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            userTableView.setItems(getInitialList());
            return;
        }

        ObservableList<User> filteredList = FXCollections.observableArrayList();
        for (User user : userTableView.getItems()) {
            if (user.getFullName().toLowerCase().contains(searchText) ||
                    user.getEmail().toLowerCase().contains(searchText) ||
                    String.valueOf(user.getPhone()).contains(searchText) ||
                    user.getAddress().toLowerCase().contains(searchText)) {
                filteredList.add(user);
            }
        }
        userTableView.setItems(filteredList);
    }

    private void clearFormFields() {
        authorField.clear();
        titleField.clear();
        yearField.clear();
        yearField1.clear();
        yearField11.clear();
        birthdatePicker.setValue(null);
        genderGroup.selectToggle(null);
    }

  

    private ObservableList<User> getInitialList() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            List<User> userList = new ArrayList<>();
            userList = userTable.listAll();
            for (User user : userList) {
                users.add(user);
            }
          } catch (SQLException e) {
            Alertmessage.showAlert("Error", "FAIL TO FETCH DATA ");
          }   
          catch (Exception e){
            Alertmessage.showAlert("Error", "FAIL TO FETCH DATA :"+e.getMessage());
          }
          return users;
        }    
}