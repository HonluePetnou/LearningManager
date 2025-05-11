package com.example.librarymanager.Controllers;

import java.sql.SQLException;

import com.example.librarymanager.Database.BooksTable;
import com.example.librarymanager.Models.Books;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookDetailsController {
    @FXML
    private TextField bookTitleTextField;

    @FXML
    private TextField bookAuthorTextField;

    @FXML
    private TextArea bookDescriptionArea;

    @FXML
    private ImageView bookImageView;

    @FXML
    private Button borrowButton;

    @FXML
    private Button returnButton;

    // private BookCardController bookCardController;

    public void setBookDetails(Books book) {
        if (book != null) {
            bookTitleTextField.setText(book.getTitle());
            bookAuthorTextField.setText(book.getAuthor());
            // bookDescriptionArea.setText(book.getDescription());
            bookDescriptionArea.setText("lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
            if (book.getImage_path() != null && !book.getImage_path().isEmpty()) {
                bookImageView.setImage(new Image(getClass().getResourceAsStream(book.getImage_path())));
            } else {
                bookImageView.setImage(new Image(getClass().getResourceAsStream("/Images/test1.jpg"))); // Image par défaut
            }
        }
    }

    // public void initialize() throws SQLException {
    //     if (bookCardController != null) {
    //         Books book = BooksTable.getBookByTitle(bookCardController.getBookTitle());

    //         bookTitleTextField.setText(book.getTitle());
    //         bookAuthorTextField.setText(book.getAuthor());
    //         // bookDescriptionArea.setText(book.getDescription());
    //         bookImageView.setImage(new Image(getClass().getResourceAsStream(book.getImage_path())));
    //     } else {
    //         System.err.println("Error: bookCardController is null.");
    //     }
    // }

    // public void initialize() {

    //     Books book = bookCardController.getBook();

    //     bookTitleTextField.setText(book.getTitle());
    //     bookAuthorTextField.setText(book.getAuthor());
    //     // bookDescriptionArea.setText(book.getDescription());
    //     bookImageView.setImage(new Image(getClass().getResourceAsStream(book.getImage_path())));
    // }

}
