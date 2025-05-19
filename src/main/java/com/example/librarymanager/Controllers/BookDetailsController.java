package com.example.librarymanager.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.librarymanager.Models.Books;
import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.Models.Model;
import com.example.librarymanager.utils.CurrentUser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookDetailsController implements Initializable {
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
  private Button borrowButton;

  @FXML
  private Button returnButton;

    private Loan loan = new Loan();

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
         borrowButton.setOnAction(event -> {
           handleBorrowBook();
        });
    }

    public void setBookDetails(Books book) {
        if (book != null) {
            loan.setBookId(book.getBook_id());
            bookTitleTextField.setText(book.getTitle());
            bookAuthorTextField.setText(book.getAuthor());
            isbnTextField.setText(book.getIsbn());
            yearTextField.setText(String.valueOf(book.getPublished_year()));
            categoryTextField.setText(String.valueOf(book.getCategory_id()));
            totalCopiesTextField.setText(String.valueOf(book.getCopies_total()));
            totalCopiesTextField.setText(String.valueOf(book.getCopies_available()));
            bookDescriptionArea.setText(book.getDescription());
            if (book.getImage_path() != null && !book.getImage_path().isEmpty()) {
                bookImageView.setImage(new Image(getClass().getResourceAsStream(book.getImage_path())));
            } else {
                bookImageView.setImage(new Image(getClass().getResourceAsStream("/Images/test1.jpg"))); // Image par défaut
            }
             CurrentUser.setBook(book);
             CurrentUser.setLoan(loan);
        }
    }

    private void  handleBorrowBook(){
      Model.getModel().getViewFactory().showConfirmBorrowWindow();
    }
}
