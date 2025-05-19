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

/**
 * JavaFX controller for displaying detailed information about a book.
 *
 * This controller manages the display of all book details, including title,
 * author,
 * ISBN, year, category, total and available copies, description, and cover
 * image.
 * It also handles the borrow action for the book.
 *
 * Main features:
 * - Displays all details of a selected book.
 * - Shows the book's cover image or a default image if not available.
 * - Allows the user to initiate a borrow action for the book.
 * - Updates the CurrentUser context with the selected book and loan.
 *
 * Dependencies:
 * - Books: the book data model.
 * - Loan: the loan data model.
 * - Model: application model for view management.
 * - CurrentUser: utility for storing the current book and loan context.
 *
 * FXML requirements:
 * - TextFields: bookTitleTextField, bookAuthorTextField, isbnTextField,
 * yearTextField,
 * categoryTextField, totalCopiesTextField, availableCopiesTextField
 * - TextArea: bookDescriptionArea
 * - ImageView: bookImageView
 * - Buttons: borrowButton, returnButton
 */
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

  /**
   * The loan object associated with the current book.
   */
  private Loan loan = new Loan();

  /**
   * Initializes the controller and sets up button actions.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    borrowButton.setOnAction(_ -> {
      handleBorrowBook();
    });
  }

  /**
   * Sets the details of the book to be displayed in the view.
   * Populates all fields and updates the CurrentUser context.
   *
   * @param book the book whose details are to be displayed
   */
  public void setBookDetails(Books book) {
    if (book != null) {
      loan.setBookId(book.getBook_id());
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
      CurrentUser.setBook(book);
      CurrentUser.setLoan(loan);
    }
  }

  /**
   * Handles the borrow book action by showing a confirmation window.
   */
  private void handleBorrowBook() {
    Model.getModel().getViewFactory().showConfirmBorrowWindow();
  }
}
