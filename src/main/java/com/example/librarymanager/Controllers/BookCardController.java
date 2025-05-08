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
            if (books.getImage_path() != null && !books.getImage_path().isEmpty()) {
                try {
                    Image image = new Image(getClass().getClassLoader().getResourceAsStream(books.getImage_path()));
                    bookImage.setImage(image);
                } catch (Exception e) {
                    System.err.println("Error loading image: " + books.getImage_path() + " - " + e.getMessage());
                    // Load a default image or leave the current one
                }
            }
            
            bookTitle.setText(books.getTitle());
            bookAuthor.setText(books.getAuthor());
        } catch (Exception e) {
            System.err.println("Error setting book data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
