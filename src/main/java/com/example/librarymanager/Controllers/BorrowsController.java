package com.example.librarymanager.Controllers;

import com.example.librarymanager.Controllers.BorrowsCardController;
import com.example.librarymanager.Models.Loan;
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

public class BorrowsController {

    @FXML
    private GridPane loanGrid;

    public void initialize() {
        // Exemple de données — remplace par ta base ou liste dynamique
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Création d'exemples de prêts
        List<Loan> loans = new ArrayList<>();
        
        Loan loan1 = new Loan();
        loan1.setBookId(1); // Supposons que c'est l'ID pour "Les Misérables"
        loan1.setUserId(1); // Supposons que c'est l'ID pour "Jean Dupont"
        loan1.setBorrowedAt(LocalDateTime.now());
        loan1.setDueAt(LocalDateTime.now().plusDays(14));
        loan1.setStatus("active");
        loans.add(loan1);
        
        Loan loan2 = new Loan();
        loan2.setBookId(2); // Supposons que c'est l'ID pour "Le Petit Prince"
        loan2.setUserId(2); // Supposons que c'est l'ID pour "Alice Nkoa"
        loan2.setBorrowedAt(LocalDateTime.now().minusDays(1));
        loan2.setDueAt(LocalDateTime.now().plusDays(13));
        loan2.setStatus("overdue");
        loans.add(loan2);
        
        Loan loan3 = new Loan();
        loan3.setBookId(3); // Supposons que c'est l'ID pour "L'Alchimiste"
        loan3.setUserId(3); // Supposons que c'est l'ID pour "Marc Njoya"
        loan3.setBorrowedAt(LocalDateTime.now().minusDays(2));
        loan3.setDueAt(LocalDateTime.now().plusDays(12));
        loan3.setStatus("active");
        loans.add(loan3);

        int column = 0;
        int row = 0;

        for (Loan loan : loans) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/BorrowsCard.fxml"));
                VBox card = loader.load();

                // Get the controller and set data
                BorrowsCardController cardController = loader.getController();
                // In a real app, you would fetch book and user details from their respective services
                String bookTitle = "Book ID: " + loan.getBookId(); // Replace with actual book title lookup
                String borrowerName = "User ID: " + loan.getUserId(); // Replace with actual user name lookup
                String loanDate = loan.getBorrowedAt().format(formatter);
                String returnDate = loan.getDueAt().format(formatter);
                boolean onTime = !loan.getStatus().equals("overdue");
                
                cardController.setLoanData(
                    bookTitle,
                    borrowerName,
                    loanDate,
                    returnDate,
                    onTime
                );
                
                // Get the return button and set action
                Button returnBtn = (Button) card.lookup("#returnButton");
                returnBtn.setOnAction(e -> {
                    // Action pour rendre un livre
                    System.out.println(bookTitle + " rendu !");
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
