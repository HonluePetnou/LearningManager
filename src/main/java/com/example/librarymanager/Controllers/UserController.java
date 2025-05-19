package com.example.librarymanager.Controllers;

import com.example.librarymanager.Database.UserTable;
import com.example.librarymanager.Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.librarymanager.utils.Alertmessage;

/**
 * JavaFX controller for managing users in the application.
 *
 * This controller handles the user management view, including displaying all
 * users,
 * adding new users, searching, and deleting users from the system.
 *
 * Main features:
 * - Displays all users in a TableView with columns for ID, name, email, phone,
 * birthdate, gender, and address.
 * - Allows adding a new user via a form.
 * - Allows searching users by name, email, phone, or address.
 * - Allows deleting users directly from the table.
 * - Validates input fields before adding a user.
 * - Clears the form after adding a user.
 *
 * Dependencies:
 * - UserTable: for database operations related to users.
 * - User: the user data model.
 * - Alertmessage: for displaying error messages.
 *
 * FXML requirements:
 * - TableView: userTableView
 * - TableColumns: idCol, fullNameCol, emailCol, phoneCol, birthdateCol,
 * genderCol, addressCol, deleteCol
 * - TextFields: authorField (first name), titleField (last name), yearField
 * (email), yearField1 (phone), yearField11 (address), searchField
 * - DatePicker: birthdatePicker
 * - ToggleGroup: genderGroup
 * - RadioButtons: maleRadio, femaleRadio
 */
public class UserController implements Initializable {

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, Integer> idCol;
    @FXML
    private TableColumn<User, String> fullNameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, Integer> phoneCol;
    @FXML
    private TableColumn<User, LocalDate> birthdateCol;
    @FXML
    private TableColumn<User, String> genderCol;
    @FXML
    private TableColumn<User, String> addressCol;
    @FXML
    private TableColumn<User, Boolean> deleteCol;

    // Form fields
    @FXML
    private TextField authorField; // First Name
    @FXML
    private TextField titleField; // Last Name
    @FXML
    private TextField yearField; // Email
    @FXML
    private TextField yearField1; // Phone
    @FXML
    private TextField yearField11; // Address
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private TextField searchField;

    private UserTable userTable = new UserTable();
    private int numberOfUsers = 0;

    /**
     * Initializes the controller, configures the table columns, sets up gender
     * selection,
     * and loads the initial list of users.
     */
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

        // Configure delete button in table
        deleteCol.setCellFactory(_ -> new TableCell<>() {
            private final Button deleteButton = new Button("❌");

            {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");
                deleteButton.setOnAction(_ -> {
                    User user = getTableView().getItems().get(getIndex());
                    userTableView.getItems().remove(user);
                    try {
                        userTable.Delete(user.getUser_id());
                    } catch (SQLException e) {
                        Alertmessage.showAlert(AlertType.ERROR, "Error", "Failed to delete user: " + e.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Load initial data
        numberOfUsers = getInitialList().size();
        userTableView.setItems(getInitialList());
    }

    /**
     * Handles the add user button action.
     * Validates input, creates a new user, adds it to the database and table, and
     * clears the form.
     * 
     * @param event the action event
     */
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
                Alertmessage.showAlert(AlertType.ERROR, "Error", "All fields are required");
                return;
            }

            // Create and add new user
            User newUser = new User(firstName, lastName, email, phone, birthdate, gender, address);
            newUser.setRole("MEMBER");
            newUser.setPassword("defaultpassword");
            userTable.create(newUser);
            numberOfUsers++;
            newUser.setUser_id(numberOfUsers);
            userTableView.getItems().add(newUser);

            // Clear form
            clearFormFields();

        } catch (NumberFormatException e) {
            Alertmessage.showAlert(AlertType.ERROR, "Error", "Please enter a valid phone number");
        } catch (SQLException e) {
            Alertmessage.showAlert(AlertType.ERROR, "Error ", "ADD NEW USER  FAIL");
        } catch (Exception e) {
            Alertmessage.showAlert(AlertType.ERROR, "Error", e.getMessage());
        }
    }

    /**
     * Handles the search action.
     * Filters the user list based on the search text.
     * 
     * @param event the action event
     */
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

    /**
     * Clears all fields in the add user form.
     */
    private void clearFormFields() {
        authorField.clear();
        titleField.clear();
        yearField.clear();
        yearField1.clear();
        yearField11.clear();
        birthdatePicker.setValue(null);
        genderGroup.selectToggle(null);
    }

    /**
     * Loads the initial list of users from the database.
     * 
     * @return ObservableList of users
     */
    private ObservableList<User> getInitialList() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            List<User> userList = new ArrayList<>();
            userList = userTable.listAll();
            for (User user : userList) {
                users.add(user);
            }
        } catch (SQLException e) {
            Alertmessage.showAlert(AlertType.ERROR, "Error", "FAIL TO FETCH DATA ");
        } catch (Exception e) {
            Alertmessage.showAlert(AlertType.ERROR, "Error", "FAIL TO FETCH DATA :" + e.getMessage());
        }
        return users;
    }
}