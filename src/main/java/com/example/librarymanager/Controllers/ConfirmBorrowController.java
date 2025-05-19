package com.example.librarymanager.Controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.example.librarymanager.Database.BooksTable;
import com.example.librarymanager.Database.LoanTable;
import com.example.librarymanager.Models.Books;
import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.Models.Model;
import com.example.librarymanager.utils.Alertmessage;
import com.example.librarymanager.utils.CurrentUser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * JavaFX controller for confirming a book borrowing (loan) operation.
 *
 * This controller manages the confirmation form where a user can borrow a book.
 * It validates user input, checks book availability, creates the loan in the
 * database,
 * updates the available copies, and triggers a UI refresh.
 *
 * Main features:
 * - Validates all required fields (user ID, number of books, return date).
 * - Ensures the return date is in the future.
 * - Checks that the requested number of books does not exceed available copies.
 * - Prevents borrowing the same book multiple times by the same user.
 * - Creates a new loan record and updates the book's available copies in the
 * database.
 * - Shows user feedback via alerts.
 * - Closes the confirmation window upon successful borrowing.
 *
 * Dependencies:
 * - LoanTable: for loan database operations.
 * - BooksTable: for updating book availability.
 * - Alertmessage: for user feedback.
 * - CurrentUser: for accessing the current book and loan context.
 * - Model: for closing the window.
 * - BorrowsController: for triggering a refresh of the loan list.
 *
 * FXML requirements:
 * - Button: confirmbutton
 * - TextField: numberOfBook, userId
 * - DatePicker: returndate
 */
public class ConfirmBorrowController implements Initializable {

  @FXML
  private Button confirmbutton;

  @FXML
  private TextField numberOfBook;

  @FXML
  private DatePicker returndate;

  @FXML
  private TextField userId;

  private LoanTable loanTable = new LoanTable();

  private Loan loan = new Loan();

  private Books book = new Books();

  private BooksTable booksTable = new BooksTable();

  /**
   * Initializes the controller and sets up the confirm button action.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    confirmbutton.setOnAction(_ -> {
      if (confirmBorrow()) {
        closeStage();
      }
    });
  }

  /**
   * Validates the form, creates the loan, updates the book, and triggers UI
   * refresh.
   * 
   * @return true if the borrow operation was successful, false otherwise
   */
  private boolean confirmBorrow() {
    loan = CurrentUser.getLoan();
    book = CurrentUser.getBook();
    if (userId.getText().isEmpty() || numberOfBook.getText().isEmpty() || returndate.getValue() == null) {
      Alertmessage.showAlert(AlertType.ERROR, "ERROR", "All fields are required.");
      return false;
    }
    if (returndate.getValue().isBefore(LocalDateTime.now().toLocalDate())) {
      Alertmessage.showAlert(AlertType.ERROR, "ERROR", "The return date must be in the future.");
      return false;
    }
    if (Integer.parseInt(numberOfBook.getText()) > book.getCopies_available()) {
      Alertmessage.showAlert(AlertType.ERROR, "ERROR",
          "The number of books requested exceeds the available copies.(available:" + book.getCopies_available() + ")");
      return false;
    }

    try {
      book.setAvailable_copies(book.getCopies_available() - Integer.parseInt(numberOfBook.getText()));
      loan.setUserId(Integer.parseInt(userId.getText()));
      loan.setDueAt(returndate.getValue().atStartOfDay());
      loan.setBookId(book.getBook_id());
      loan.setNumberOfBook(Integer.parseInt(numberOfBook.getText()));

      if (loanTable.getLoanId(Integer.parseInt(userId.getText()), book.getBook_id()) != 0) {
        Alertmessage.showAlert(AlertType.INFORMATION, "INFO", "You have already borrowed this book.");
        return false;
      }
      loanTable.create(loan);
      booksTable.Update(book);
      Alertmessage.showAlert(AlertType.INFORMATION, "INFO", "Book borrowed successfully.");
      BorrowsController.triggerUpdate();
      return true;
    } catch (Exception e) {
      System.err.println("fail to create loan:" + e);
      Alertmessage.showAlert(AlertType.ERROR, "ERROR", "Internal error");
    }
    return false;
  }

  /**
   * Closes the confirmation window.
   */
  private void closeStage() {
    Stage stage = (Stage) numberOfBook.getScene().getWindow();
    Model.getModel().getViewFactory().closeStage(stage);
  }
}
