package com.example.librarymanager.Controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.example.librarymanager.Database.LoanTable;
import com.example.librarymanager.Models.Books;
import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.Models.Model;
import com.example.librarymanager.utils.Alertmessage;
import com.example.librarymanager.utils.CurrentUser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookDetailsController implements Initializable {
  @FXML
  private TextField bookTitleTextField;

  @FXML
  private TextField bookAuthorTextField;

  @FXML
  private TextArea bookDescriptionArea;

  @FXML
  private ImageView bookImageView;

  @FXML
  private Button borrowButton;

  @FXML
  private Button returnButton;

  private Loan loan = new Loan();

  private LoanTable loanTable = new LoanTable();
  // private BookCardController bookCardController;

  @SuppressWarnings("unused")
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    borrowButton.setOnAction(event -> {
      handleBorrowBook();
    });
    returnButton.setOnAction(event -> {
      handleReturnBook();
    });
  }

  public void setBookDetails(Books book) {
    if (book != null) {
      loan.setBookId(book.getBook_id());
      bookTitleTextField.setText(book.getTitle());
      bookAuthorTextField.setText(book.getAuthor());
      bookDescriptionArea.setText(book.getDescription());
      Image image = loadImage(book.getImage_path());
      if (image != null) {
        bookImageView.setImage(image);
      }
      CurrentUser.setBook(book);
      CurrentUser.setLoan(loan);
    }
  }

  private Image loadImage(String path) {
    try {
      if (path != null && !path.isEmpty()) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
          return new Image(imageUrl.toExternalForm());
        }
      }
      // Load default image if path is null, empty or resource not found
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

  private void handleBorrowBook() {
    Model.getModel().getViewFactory().showConfirmBorrowWindow();
  }

  private void handleReturnBook() {
    try {
      int loan_id = loanTable.getLoanId(CurrentUser.getUser().getUser_id(), loan.getBookId());
      if (loan_id == 0) {
        Alertmessage.showAlert(AlertType.INFORMATION, "INFO", "pas emprunte");
        return;
      }
      loan.setLoanId(loan_id);
      loan.setReturnedAt(LocalDateTime.now());
      loan.setStatus("RETURNED");
      loanTable.Update(loan);
      BorrowsController.triggerUpdate();
      Alertmessage.showAlert(AlertType.INFORMATION, "INFO", "livre rendu");
    } catch (Exception e) {
      Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
      System.err.println("fail to Update  the loan :" + e);
    }
  }

}
