package com.example.librarymanager.Controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import com.example.librarymanager.Database.LoanTable;
import com.example.librarymanager.Models.Books;
import com.example.librarymanager.Models.Loan;
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
            // bookDescriptionArea.setText(book.getDescription());
            bookDescriptionArea.setText("lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
            if (book.getImage_path() != null && !book.getImage_path().isEmpty()) {
                bookImageView.setImage(new Image(getClass().getResourceAsStream(book.getImage_path())));
            } else {
                bookImageView.setImage(new Image(getClass().getResourceAsStream("/Images/test1.jpg"))); // Image par défaut
            }
        }
    }

    private void  handleBorrowBook(){
      try {
        loan.setUserId(CurrentUser.getUser().getUser_id());
        System.out.println("user_id"+CurrentUser.getUser().getUser_id()); 
        loan.setDueAt(LocalDateTime.now().plus(1 , ChronoUnit.MONTHS)); // date de remise 
        loanTable.create(loan);
        Alertmessage.showAlert(AlertType.INFORMATION, "INFO", "livre emprunte avec success");
      } catch (Exception e) {
        System.err.println("fail to create loan:"+e);
        Alertmessage.showAlert(AlertType.ERROR , "ERROR", "internal error");
      }
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
