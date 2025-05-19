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

public class ConfirmBorrowController implements Initializable{

    @FXML
    private Button confirmbutton;

    @FXML
    private TextField numberOfBook;

    @FXML
    private DatePicker returndate;

    @FXML
    private TextField userId;

    private LoanTable loanTable = new LoanTable() ;

    private Loan loan = new Loan() ;

    private Books book = new Books() ;

    private BooksTable booksTable = new BooksTable();

  @SuppressWarnings("unused")
@Override
   public void initialize(URL url, ResourceBundle resourceBundle){
      confirmbutton.setOnAction( event ->{
           if( confirmBorrow()){
             closeStage();
           }
      });
   }

 private boolean confirmBorrow(){
    loan = CurrentUser.getLoan() ;
    book = CurrentUser.getBook() ;
    if(userId.getText().isEmpty() || numberOfBook.getText().isEmpty() || returndate.getValue() == null){
        Alertmessage.showAlert(AlertType.ERROR, "ERROR", "All fields are required.");
        return false;
    }
    if(returndate.getValue().isBefore(LocalDateTime.now().toLocalDate())){
        Alertmessage.showAlert(AlertType.ERROR, "ERROR", "The return date must be in the future.");
        return false;
    }
    if(Integer.parseInt(numberOfBook.getText()) > book.getCopies_available()){
        Alertmessage.showAlert(AlertType.ERROR, "ERROR", "The number of books requested exceeds the available copies.(available:"+book.getCopies_available()+")");
        return false;
    }
 
    try {
        book.setAvailable_copies(book.getCopies_available() - Integer.parseInt(numberOfBook.getText()));
        loan.setUserId(Integer.parseInt(userId.getText()));
        loan.setDueAt(returndate.getValue().atStartOfDay());
        loanTable.create(loan);
        booksTable.Update(book);
        Alertmessage.showAlert(AlertType.INFORMATION, "INFO", "livre emprunte avec success");
      } catch (Exception e) {
        System.err.println("fail to create loan:"+e);
        Alertmessage.showAlert(AlertType.ERROR , "ERROR", "internal error");
      }
    return true ;
   }

    private void closeStage(){
    Stage stage = (Stage) numberOfBook.getScene().getWindow();
    Model.getModel().getViewFactory().closeStage(stage);
   }
}
  