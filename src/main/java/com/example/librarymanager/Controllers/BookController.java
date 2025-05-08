package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Books;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    @FXML
    private Button addBookButton;
    @FXML
    private TextField authorField;
    @FXML
    private TextField availableCopiesField;
    @FXML
    private TextField categoryField;
    @FXML
    private Button chooseImageButton;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField totalCopiesField;
    @FXML
    private TextField yearField;
    @FXML
    private GridPane bookGrid;

    private List<Books> books;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Initialize the grid with proper error handling
            if (bookGrid == null) {
                System.err.println("Error: bookGrid is null. FXML might not be loaded correctly.");
                return;
            }
            
            books = new ArrayList<>(data());
            
            int columns = 0;
            int rows = 0;
            
            for (Books book : books) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Fxml/BookCard.fxml"));
                    
                    if (fxmlLoader.getLocation() == null) {
                        System.err.println("Error: Could not find BookCard.fxml resource");
                        continue;
                    }
                    
                    VBox bookBox = fxmlLoader.load();
                    
                    BookCardController bookController = fxmlLoader.getController();
                    bookController.setData(book);
                    
                    if (columns == 3) {
                        columns = 0;
                        ++rows;
                    }
                    
                    bookGrid.add(bookBox, columns++, rows);
                    GridPane.setMargin(bookBox, new Insets(10));
                } catch (IOException e) {
                    System.err.println("Error loading book card: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing BookController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Books> data() {
        List<Books> books = new ArrayList<>();
        try {
            Books book = new Books();
            book.setTitle("THE BOOK OF LEGENDS");
            book.setAuthor("Albert Einstein");
            // Use a path that's guaranteed to be in the classpath
            book.setImage_path("Images/book1.jpg");
            
            // Verify if the image exists in the classpath
            if (getClass().getClassLoader().getResource(book.getImage_path()) == null) {
                System.err.println("Warning: Image not found in classpath: " + book.getImage_path());
                // Set a default or empty image path to avoid errors
                // book.setImage_path("");
            }
            
            books.add(book);
            
            // Add more books if needed
        } catch (Exception e) {
            System.err.println("Error creating sample book data: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @FXML
    void handleAddBook(ActionEvent event) {
        // Implement this method
    }

    @FXML
    void handleChooseImage(ActionEvent event) {
        // Implement this method
    }
}