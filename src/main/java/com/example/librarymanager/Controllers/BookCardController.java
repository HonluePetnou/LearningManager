package com.example.librarymanager.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.librarymanager.Models.Books;
import com.example.librarymanager.Models.Model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BookCardController implements Initializable {

    @FXML
    private Button BooksDetailsButton;

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    private int id;
    private int year;
    private int category_id;
    private int copies_total;
    private int copies_available;
    private String isbn;
    private String imagePath;

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BooksDetailsButton.setOnAction(event -> {
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
    }

    public void setData(Books books) {
        try {
            // Safely load the image with error handling
            imagePath = books.getImage_path();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                    bookImage.setImage(image);
                } catch (Exception e) {
                    System.err.println("Error loading image: " + imagePath + " - " + e.getMessage());
                    // Load default image
                    try {
                        Image defaultImage = new Image(getClass().getResource("/Images/test1.jpg").toExternalForm());
                        bookImage.setImage(defaultImage);
                    } catch (Exception ex) {
                        System.err.println("Error loading default image: " + ex.getMessage());
                    }
                }
            }
            
            // id = books.getBook_id();
            bookTitle.setText(books.getTitle());
            bookAuthor.setText(books.getAuthor());
            // isbn = books.getIsbn();
            // category_id = books.getCategory_id();
            // year = books.getPublished_year();
            // copies_total = books.getCopies_total();
            // copies_available = books.getCopies_available();
            imagePath = books.getImage_path();
        } catch (Exception e) {
            System.err.println("Error setting book data: " + e.getMessage());
        }
    }

    public String getBookTitle() {
        return bookTitle.getText();
    }

    public Books getBook() {
        return new Books(id, bookTitle.getText(), bookAuthor.getText(), isbn, category_id, year, copies_total, copies_available, imagePath);
    }
    
}