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

public class BookDetailsController implements Initializable{
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

    private LoanTable loanTable = new LoanTable() ; 
    // private BookCardController bookCardController;

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
         borrowButton.setOnAction(event -> {
           handleBorrowBook();
        });
        returnButton.setOnAction(event -> {
          handleReturnBook() ;
        });
    }

    public void setBookDetails(Books book) {
        if (book != null) {
            loan.setBookId(book.getBook_id());
            bookTitleTextField.setText(book.getTitle());
            bookAuthorTextField.setText(book.getAuthor());
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

    private void handleReturnBook(){
      try {
        int loan_id = loanTable.getLoanId(CurrentUser.getUser().getUser_id(), loan.getBookId());// iD de l'utilisateur actuel
        if(loan_id == 0){
          Alertmessage.showAlert(AlertType.INFORMATION, "INFO", "pas emprunte");
          return ;
        }
        loan.setLoanId(loan_id);
        loan.setReturnedAt(LocalDateTime.now());
        loan.setStatus("RETURNED");
        loanTable.Update(loan);
        Alertmessage.showAlert(AlertType.INFORMATION, "INFO", "livre rendu");
      } catch (Exception e) {
        Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
         System.err.println("fail to Update  the loan :"+e);
      }
    }


    // public void initialize() throws SQLException {
    //     if (bookCardController != null) {
    //         Books book = BooksTable.getBookByTitle(bookCardController.getBookTitle());

    //         bookTitleTextField.setText(book.getTitle());
    //         bookAuthorTextField.setText(book.getAuthor());
    //         // bookDescriptionArea.setText(book.getDescription());
    //         bookImageView.setImage(new Image(getClass().getResourceAsStream(book.getImage_path())));
    //     } else {
    //         System.err.println("Error: bookCardController is null.");
    //     }
    // }

    // public void initialize() {

    //     Books book = bookCardController.getBook();

    //     bookTitleTextField.setText(book.getTitle());
    //     bookAuthorTextField.setText(book.getAuthor());
    //     // bookDescriptionArea.setText(book.getDescription());
    //     bookImageView.setImage(new Image(getClass().getResourceAsStream(book.getImage_path())));
    // }

}
