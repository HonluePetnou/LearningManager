package com.example.librarymanager.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.librarymanager.Database.BooksTable;
import com.example.librarymanager.Models.Books;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * JavaFX controller for displaying a book card in the UI.
 *
 * This controller manages the display of a book's information (title, author,
 * image)
 * and provides actions to view details, edit, or delete the book.
 *
 * Main features:
 * - Displays book title, author, and cover image.
 * - Opens a details window for the book.
 * - Opens an edit form for the book.
 * - Deletes the book (with confirmation), updates the database and UI.
 *
 * Dependencies:
 * - BookController: to refresh the grid after deletion.
 * - BooksTable: for database operations.
 * - Books: data model representing a book.
 *
 * Usage:
 * - Call setData(Books) to initialize the card with a book.
 * - Call setBookController(BookController) to enable UI updates after deletion.
 *
 * Expected FXML:
 * - Buttons: booksDetailsButton, editBookButton, deleteBookButton
 * - Labels: bookTitle, bookAuthor
 * - ImageView: bookImage
 */
public class BookCardController implements Initializable {

    /**
     * Button to show book details.
     */
    @FXML
    private Button booksDetailsButton;

    /**
     * Button to edit the book.
     */
    @FXML
    private Button editBookButton;

    /**
     * Button to delete the book.
     */
    @FXML
    private Button deleteBookButton;

    /**
     * Label displaying the book's author.
     */
    @FXML
    private Label bookAuthor;

    /**
     * ImageView displaying the book's cover.
     */
    @FXML
    private ImageView bookImage;

    /**
     * Label displaying the book's title.
     */
    @FXML
    private Label bookTitle;

    /**
     * The book instance displayed by this card.
     */
    private Books book;

    /**
     * Access to the books table for database operations.
     */
    private BooksTable booksTable;

    /**
     * Main controller for managing the book grid.
     */
    private BookController bookController;

    /**
     * Injects the main controller for grid management.
     * 
     * @param bookController the main controller
     */
    public void setBookController(BookController bookController) {
        this.bookController = bookController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization if needed
    }

    /**
     * Opens the details window for the book.
     * 
     * @param book the book to display
     */
    private void detailsBook(Books book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/BookDetails.fxml"));
            Parent root = loader.load();
            BookDetailsController detailsController = loader.getController();
            detailsController.setBookDetails(book);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the edit form for the book.
     * 
     * @param book the book to edit
     */
    private void editBook(Books book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/EditBook.fxml"));
            Parent root = loader.load();
            EditBookController editBookController = loader.getController();
            editBookController.setBookCardController(this);
            editBookController.setBookDetails(book);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the book after confirmation, updates the database and UI.
     * 
     * @param book the book to delete
     */
    private void deleteBook(Books book) {
        try {
            // User confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Book");
            alert.setHeaderText("Are you sure you want to delete this book?");
            alert.setContentText("This action cannot be undone.");
            if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            // Remove from UI
            if (bookController != null) {
                bookController.getBooks().remove(book);
                System.out.println("Book successfully deleted from UI.");
                bookController.refreshBookGrid();
            }

            // Remove from database
            booksTable = new BooksTable();
            try {
                booksTable.Delete(book.getBook_id());
                System.out.println("Book " + book.getTitle() + " successfully deleted from database.");
            } catch (SQLException e) {
                System.err.println("Error deleting book from database: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an image from the given path, or a default image if not found.
     * 
     * @param path the image path
     * @return the loaded image
     */
    private Image loadImage(String path) {
        try {
            if (path != null && !path.isEmpty()) {
                URL imageUrl = getClass().getResource(path);
                if (imageUrl != null) {
                    return new Image(imageUrl.toExternalForm());
                }
            }
            // Default image
            URL defaultImageUrl = getClass().getResource("/Images/test1.jpg");
            if (defaultImageUrl != null) {
                return new Image(defaultImageUrl.toExternalForm());
            }
            throw new IllegalStateException("Neither book image nor default image could be loaded");
        } catch (Exception e) {
            System.err.println("Error loading image: " + path + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Initializes the card with the book data and configures the buttons.
     * 
     * @param books the book to display
     */
    public void setData(Books books) {
        try {
            this.book = books;
            booksDetailsButton.setOnAction(_ -> detailsBook(book));
            editBookButton.setOnAction(_ -> editBook(book));
            deleteBookButton.setOnAction(_ -> deleteBook(book));
            Image image = loadImage(books.getImage_path());
            if (image != null) {
                bookImage.setImage(image);
            }
            bookTitle.setText(books.getTitle());
            bookAuthor.setText(books.getAuthor());
        } catch (Exception e) {
            System.err.println("Error setting book data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}