package com.example.librarymanager.Controllers;

import com.example.librarymanager.Database.LoanTable;
import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.utils.Alertmessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.SQLException;

public class BorrowsController {

    @FXML
    private GridPane loanGrid;

    private  LoanTable loanTable = new LoanTable();

    @SuppressWarnings("unused")
    public  void  initialize() {
        // Exemple de données — remplace par ta base ou liste dynamique
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        List<Loan> loans = new ArrayList<>();

     try {
        loans = loanTable.listAll() ;
     } catch (SQLException e) {
       System.err.println("fail to fetch the loans"+e);
       Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
       return ;
     }
        
       
        int column = 0;
        int row = 0;

        for (Loan loan : loans) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/BorrowsCard.fxml"));
                VBox card = loader.load();

                // Get the controller and set data
                BorrowsCardController cardController = loader.getController();
                String bookTitle = "Book : " + loan.getBook_name(); 
                String borrowerName = "User : " + loan.getUser_name(); 
                String loanDate = loan.getBorrowedAt().format(formatter);
                String returnDate = loan.getDueAt().format(formatter);
                boolean onTime = !loan.getStatus().equalsIgnoreCase("overdue");
                
                cardController.setLoanData(
                    bookTitle,
                    borrowerName,
                    loanDate,
                    returnDate,
                    onTime
                );
                
                // Get the return button and set action
                Button returnBtn = (Button) card.lookup("#returnButton");
                returnBtn.setOnAction(event -> {
                  if(!loan.getStatus().equals("RETURNED")){
                    try {
                        loan.setReturnedAt(LocalDateTime.now());
                        loan.setStatus("RETURNED");
                        loanTable.Update(loan);
                       Alertmessage.showAlert(AlertType.INFORMATION, "Success", "returned!");
                    } catch (SQLException e) {
                       Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
                       System.err.println("fail to Update  the loan :"+e);
                    }
                    return ;
                  }
                  Alertmessage.showAlert(AlertType.ERROR, "Error", "already returned");
                });

                loanGrid.add(card, column, row);
                
                // Move to the next column, and if needed, to the next row
                column++;
                if (column == 4) {
                    column = 0;
                    row++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  

}
