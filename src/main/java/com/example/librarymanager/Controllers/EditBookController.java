package com.example.librarymanager.Controllers;

import java.sql.SQLException;

import com.example.librarymanager.Database.BooksTable;
import com.example.librarymanager.Models.Books;
import com.example.librarymanager.utils.Alertmessage;
import com.example.librarymanager.utils.FormValidation;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EditBookController {

    @FXML
    private TextField bookTitleTextField;

    @FXML
    private TextField bookAuthorTextField;

    @FXML
    private TextField isbnTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TextField totalCopiesTextField;

    @FXML
    private TextField availableCopiesTextField;

    @FXML
    private TextArea bookDescriptionArea;

    @FXML
    private ImageView bookImageView;

    @FXML
    private TextField imageTextField;

    @FXML
    private Button validateBtn;

    private BookCardController bookCardController;
    private BooksTable booksTable;
    private Books book;

    public void setBookDetails(Books book) {
        this.book = book;
        if (book != null) {
            bookTitleTextField.setText(book.getTitle());
            bookAuthorTextField.setText(book.getAuthor());
            isbnTextField.setText(book.getIsbn());
            yearTextField.setText(String.valueOf(book.getPublished_year()));
            categoryTextField.setText(String.valueOf(book.getCategory_id()));
            totalCopiesTextField.setText(String.valueOf(book.getCopies_total()));
            availableCopiesTextField.setText(String.valueOf(book.getCopies_available()));
            bookDescriptionArea.setText(book.getDescription());
            if (book.getImage_path() != null && !book.getImage_path().isEmpty()) {
                bookImageView.setImage(new Image(getClass().getResourceAsStream(book.getImage_path())));
            } else {
                bookImageView.setImage(new Image(getClass().getResourceAsStream("/Images/test1.jpg"))); // Image par défaut
            }
        } else {
            System.err.println("Error: book is null.");
        }
    }

    public void setBookCardController(BookCardController bookCardController) {
        this.bookCardController = bookCardController;
    }

    public void initialize() {
        validateBtn.setOnAction(_ -> {
            if (!FormValidation.isValidInput(bookTitleTextField, bookAuthorTextField, isbnTextField, yearTextField, categoryTextField, totalCopiesTextField, availableCopiesTextField, imageTextField, bookDescriptionArea)) {
                return; // Stop submission if validation fails
            }

            // Update the shared Books object
            book.setTitle(bookTitleTextField.getText());
            book.setAuthor(bookAuthorTextField.getText());
            book.setIsbn(isbnTextField.getText());
            book.setPublished_year(Integer.parseInt(yearTextField.getText()));
            book.setCategory_id(Integer.parseInt(categoryTextField.getText()));
            book.setCopies_total(Integer.parseInt(totalCopiesTextField.getText()));
            book.setCopies_available(Integer.parseInt(availableCopiesTextField.getText()));
            book.setImage_path("/Images/" + imageTextField.getText());
            book.setDescription(bookDescriptionArea.getText());

            // Update the book card display
            if (bookCardController != null) {
                bookCardController.setData(book);
                Alertmessage.showAlert(AlertType.INFORMATION, "Success", "Book info modified successfully");

            }

            // Save the changes to the database
            booksTable = new BooksTable();
            try {
                booksTable.Update(book);
            } catch (SQLException e) {
                System.err.println("Error updating book in database: " + e.getMessage());
            }

            // Fermer la fenêtre de modification
            Stage stage = (Stage) validateBtn.getScene().getWindow();
            stage.close();
        });
    }
    
}
