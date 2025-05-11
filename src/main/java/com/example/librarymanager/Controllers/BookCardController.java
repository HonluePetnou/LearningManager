package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Books;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookCardController {

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    public void setData(Books books) {
        try {
            // Safely load the image with error handling
            String imagePath = books.getImage_path();
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
            
            bookTitle.setText(books.getTitle());
            bookAuthor.setText(books.getAuthor());
        } catch (Exception e) {
            System.err.println("Error setting book data: " + e.getMessage());
        }
    }
}
