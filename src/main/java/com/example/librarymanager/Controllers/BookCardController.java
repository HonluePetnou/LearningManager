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

public class BookCardController implements Initializable {

    @FXML
    private Button booksDetailsButton;

    @FXML
    private Button editBookButton;

    @FXML
    private Button deleteBookButton;

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    private String imagePath;
    private Books book; // Shared instance of the book
    private BooksTable booksTable;
    private BookController bookController;

    public void setBookController(BookController bookController) {
        this.bookController = bookController;
    }

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        booksDetailsButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/BookDetails.fxml"));
                Parent root = loader.load();

                // Obtenir le contrôleur de BookDetails
                BookDetailsController detailsController = loader.getController();

                // Passer les données du livre au contrôleur
                detailsController.setBookDetails(getBook()); // getBook() retourne un objet Books

                // Afficher la fenêtre des détails
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        );

        editBookButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/EditBook.fxml"));
                Parent root = loader.load();

                // Get the EditBookController
                EditBookController editBookController = loader.getController();

                // Pass the shared Books object and the BookCardController
                editBookController.setBookCardController(this);
                editBookController.setBookDetails(book); // getBook() retourne un objet Books

                // Show the edit form
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteBookButton.setOnAction(event -> {
            try {
                // Confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Book");
                alert.setHeaderText("Are you sure you want to delete this book?");
                alert.setContentText("This action cannot be undone.");
                if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                    return; // Exit if the user cancels
                }

                // Remove the book from the books list in BookController
                if (bookController != null) {
                    bookController.getBooks().remove(book);
                    System.out.println("Book successfully deleted from UI.");
                    // Refresh UI
                    bookController.refreshBookGrid();
                }

                // Delete the book from the database
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
        });
    }

    public void setData(Books books) {
        try {
            this.book = books; // Store the shared instance of the book
            
            // Safely load the image with error handling
            imagePath = books.getImage_path();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                    bookImage.setImage(image);
                } catch (Exception e) {
                    System.err.println("Error loading image: " + imagePath + " - " + e.getMessage());
                }
            } else if (imagePath == null && imagePath.isEmpty()) {
                // Load default image
                try {
                    Image defaultImage = new Image(getClass().getResource("/Images/test1.jpg").toExternalForm());
                    bookImage.setImage(defaultImage);
                } catch (Exception ex) {
                    System.err.println("Error loading default image: " + ex.getMessage());
                }
            }
            bookTitle.setText(books.getTitle());
            bookAuthor.setText(books.getAuthor());
        } catch (Exception e) {
            System.err.println("Error setting book data: " + e.getMessage());
        }
    }

    public Books getBook() {
        return book;
    }
    
}