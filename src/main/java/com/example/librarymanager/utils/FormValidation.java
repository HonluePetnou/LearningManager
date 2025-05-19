package com.example.librarymanager.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public class FormValidation {

    public static boolean isValidInput(TextField bookTitleTextField, TextField bookAuthorTextField, TextField isbnTextField, TextField yearTextField, TextField categoryTextField, TextField totalCopiesTextField, TextField availableCopiesTextField, TextField imageTextField, TextArea bookDescriptionArea) 
    {
        StringBuilder errorMessage = new StringBuilder();

        if (bookTitleTextField.getText() == null || bookTitleTextField.getText().trim().isEmpty()) {
            errorMessage.append("Title is required.\n");
        }
        if (bookAuthorTextField.getText() == null || bookAuthorTextField.getText().trim().isEmpty()) {
            errorMessage.append("Author is required.\n");
        }
        if (isbnTextField.getText() == null || isbnTextField.getText().trim().isEmpty()) {
            errorMessage.append("ISBN is required.\n");
        }
        if (yearTextField.getText() == null || yearTextField.getText().trim().isEmpty()) {
            errorMessage.append("Year is required.\n");
        } else {
            try {
                int year = Integer.parseInt(yearTextField.getText());
                if (year <= 0) {
                    errorMessage.append("Year must be a positive number.\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Year must be a valid number.\n");
            }
        }
        if (categoryTextField.getText() == null || categoryTextField.getText().trim().isEmpty()) {
            errorMessage.append("Category is required.\n");
        } 
        if (totalCopiesTextField.getText() == null || totalCopiesTextField.getText().trim().isEmpty()) {
            errorMessage.append("Total copies are required.\n");
        } else {
            try {
                int totalCopies = Integer.parseInt(totalCopiesTextField.getText());
                if (totalCopies <= 0) {
                    errorMessage.append("Total copies must be a positive number.\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Total copies must be a valid number.\n");
            }
        }
        if (availableCopiesTextField.getText() == null || availableCopiesTextField.getText().trim().isEmpty()) {
            errorMessage.append("Available copies are required.\n");
        } else {
            try {
                int availableCopies = Integer.parseInt(availableCopiesTextField.getText());
                if (availableCopies < 0) {
                    errorMessage.append("Available copies cannot be negative.\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Available copies must be a valid number.\n");
            }
        }
        if (imageTextField.getText() == null || imageTextField.getText().trim().isEmpty()) {
            errorMessage.append("Image name is required.\n");
        }

        if (bookDescriptionArea.getText() == null || bookDescriptionArea.getText().trim().isEmpty()) {
            errorMessage.append("Description is required.\n");
        }

        if (errorMessage.length() > 0) {
            Alertmessage.showAlert(AlertType.ERROR, "Validation Error", errorMessage.toString());
            return false;
        }

        return true;
    }
    
}
