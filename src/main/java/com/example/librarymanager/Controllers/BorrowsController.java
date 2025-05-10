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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.SQLException;

public class BorrowsController {

    @FXML
    private GridPane loanGrid;

    private  LoanTable loanTable = new LoanTable();

    @SuppressWarnings("unused")
    public void initialize() {
        // Exemple de données — remplace par ta base ou liste dynamique
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        List<Loan> loans = new ArrayList<>();

     try {
        loans = loanTable.listAll() ;
     } catch (SQLException e) {
       System.err.println("fail to fetch the loans"+e);
       Alertmessage.showAlert("Error", "internal error");
       return ;
     }
        
          // Création d'exemples de prêts
        // Loan loan1 = new Loan();
        // loan1.setBookId(1); // Supposons que c'est l'ID pour "Les Misérables"
        // loan1.setUserId(1); // Supposons que c'est l'ID pour "Jean Dupont"
        // loan1.setBorrowedAt(LocalDateTime.now());
        // loan1.setDueAt(LocalDateTime.now().plusDays(14));
        // loan1.setStatus("active");
        // loans.add(loan1);
        
        // Loan loan2 = new Loan();
        // loan2.setBookId(2); // Supposons que c'est l'ID pour "Le Petit Prince"
        // loan2.setUserId(2); // Supposons que c'est l'ID pour "Alice Nkoa"
        // loan2.setBorrowedAt(LocalDateTime.now().minusDays(1));
        // loan2.setDueAt(LocalDateTime.now().plusDays(13));
        // loan2.setStatus("overdue");
        // loans.add(loan2);
        
        // Loan loan3 = new Loan();
        // loan3.setBookId(3); // Supposons que c'est l'ID pour "L'Alchimiste"
        // loan3.setUserId(3); // Supposons que c'est l'ID pour "Marc Njoya"
        // loan3.setBorrowedAt(LocalDateTime.now().minusDays(2));
        // loan3.setDueAt(LocalDateTime.now().plusDays(12));
        // loan3.setStatus("active");
        // loans.add(loan3);

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
                       Alertmessage.showAlert("Success", "rendu !");
                    } catch (SQLException e) {
                       Alertmessage.showAlert("Error", "internal error");
                       System.err.println("fail to Update  the loan :"+e);
                    }
                    return ;
                  }
                  Alertmessage.showAlert("Error", "deja rendu");
                });

                loanGrid.add(card, column, row);
                
                // Move to the next column, and if needed, to the next row
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
