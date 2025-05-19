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

/**
 * JavaFX controller for editing a book's information.
 *
 * This controller manages the edit book form, allowing users to update book
 * details
 * such as title, author, ISBN, year, category, total and available copies,
 * image, and description.
 * It validates user input, updates the book in the database, and refreshes the
 * UI.
 *
 * Main features:
 * - Loads the selected book's details into the form fields.
 * - Validates all required fields before saving changes.
 * - Updates the Books object and saves changes to the database.
 * - Updates the corresponding book card in the UI.
 * - Closes the edit window upon successful update.
 *
 * Dependencies:
 * - BooksTable: for updating book information in the database.
 * - BookCardController: for refreshing the book card display.
 * - Alertmessage: for user feedback.
 * - FormValidation: for input validation.
 *
 * FXML requirements:
 * - TextFields: bookTitleTextField, bookAuthorTextField, isbnTextField,
 * yearTextField,
 * categoryTextField, totalCopiesTextField, availableCopiesTextField,
 * imageTextField
 * - TextArea: bookDescriptionArea
 * - ImageView: bookImageView
 * - Button: validateBtn
 */
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

    /**
     * Loads the book details into the form fields for editing.
     * 
     * @param book the book to edit
     */
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
                bookImageView.setImage(new Image(getClass().getResourceAsStream("/Images/test1.jpg"))); // Default image
            }
        } else {
            System.err.println("Error: book is null.");
        }
    }

    /**
     * Injects the BookCardController to allow UI refresh after editing.
     * 
     * @param bookCardController the BookCardController instance
     */
    public void setBookCardController(BookCardController bookCardController) {
        this.bookCardController = bookCardController;
    }

    /**
     * Initializes the controller and sets up the validate button action.
     * Validates input, updates the book, saves changes to the database, and
     * refreshes the UI.
     */
    public void initialize() {
        validateBtn.setOnAction(_ -> {
            if (!FormValidation.isValidInput(bookTitleTextField, bookAuthorTextField, isbnTextField, yearTextField,
                    categoryTextField, totalCopiesTextField, availableCopiesTextField, imageTextField,
                    bookDescriptionArea)) {
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

            // Close the edit window
            Stage stage = (Stage) validateBtn.getScene().getWindow();
            stage.close();
        });
    }
}
