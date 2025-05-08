package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Books;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    @FXML
    private TextField imageNameField;

    private List<Books> books;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Initialize the grid with proper error handling
            if (bookGrid == null) {
                System.err.println("Error: bookGrid is null. FXML might not be loaded correctly.");
                return;
            }
            
            // Configure grid properties
            bookGrid.setHgap(20); // Set horizontal gap between columns
            bookGrid.setVgap(20); // Set vertical gap between rows
            bookGrid.setPadding(new Insets(20)); // Set padding around the grid
            
            // Initialize books list and populate grid
            books = new ArrayList<>(data());
            refreshBookGrid();
            
        } catch (Exception e) {
            System.err.println("Error initializing BookController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Books> data() {
        List<Books> books = new ArrayList<>();
        try {
            // Only add the book once
            Books book = new Books();
            book.setTitle("THE BOOK OF LEGENDS");
            book.setAuthor("Albert Einstein");
            book.setImage_path("Images/book1.jpg");
            
            // Verify if the image exists in the classpath
            if (getClass().getClassLoader().getResource(book.getImage_path()) == null) {
                System.err.println("Warning: Image not found in classpath: " + book.getImage_path());
                book.setImage_path(""); // Set empty path if image not found
            }
            
            // Add the book only once
            if (!books.contains(book)) {
                books.add(book);
            }
        } catch (Exception e) {
            System.err.println("Error creating sample book data: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @FXML
    void handleAddBook(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String category = categoryField.getText();
        String isbn = isbnField.getText();
        String year = yearField.getText();
        String totalCopies = totalCopiesField.getText();
        String availableCopies = availableCopiesField.getText();
        String imageName = imageNameField.getText();
    
        Books newBook = new Books();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setCategory(category);
        newBook.setIsbn(isbn);
        newBook.setYear(year);
        newBook.setTotal_copies(totalCopies);
        newBook.setAvailable_copies(availableCopies);
        if (imageName != null && !imageName.trim().isEmpty()) {
            newBook.setImage_path("Images/" + imageName.trim() + ".jpg");
        } else {
            newBook.setImage_path(""); // or set a default image path if desired
        }
    
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(newBook);
        refreshBookGrid();
    }

    @FXML
    void handleChooseImage(ActionEvent event) {
        // Implémentez cette méthode
    }

    private void refreshBookGrid() {
        bookGrid.getChildren().clear();
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
                
                if (bookController == null) {
                    System.err.println("Error: BookCardController is null");
                    continue;
                }
                
                bookController.setData(book);
                
                // Configure book card layout
                bookBox.setPrefWidth(200);
                bookBox.setPrefHeight(300);
                bookBox.setAlignment(Pos.CENTER);
                bookBox.setStyle("-fx-padding: 10;");
                
                // Add to grid with proper positioning
                GridPane.setMargin(bookBox, new Insets(10));
                bookGrid.add(bookBox, columns, rows);
                
                // Update grid position
                columns++;
                if (columns >= 4) {
                    columns = 0;
                    rows++;
                }
                
            } catch (IOException e) {
                System.err.println("Error loading book card: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}