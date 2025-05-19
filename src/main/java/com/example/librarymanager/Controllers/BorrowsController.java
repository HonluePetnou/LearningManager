package com.example.librarymanager.Controllers;

import com.example.librarymanager.Database.LoanTable;
import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.utils.Alertmessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

    private LoanTable loanTable = new LoanTable();

    private static BooleanProperty listIsUpdate = new SimpleBooleanProperty(false);

     @SuppressWarnings("unused")
    public void initialize() {
        // Ajouter le listener pour la mise à jour
        listIsUpdate.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                updateBorrowsList();
                listIsUpdate.set(false); // Réinitialiser après la mise à jour
            }
        });

        updateBorrowsList(); // Chargement initial
    }

    // Déplacer le code existant de initialize() dans cette nouvelle méthode
    @SuppressWarnings("unused")
    private void updateBorrowsList() {
        loanGrid.getChildren().clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        List<Loan> loans = new ArrayList<>();

        try {
            loans = loanTable.listAll();
        } catch (SQLException e) {
            System.err.println("fail to fetch the loans" + e);
            Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
            return;
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
                int numberOfBook = loan.getNumberOfBook();

                cardController.setLoanData(
                        bookTitle,
                        borrowerName,
                        loanDate,
                        returnDate,
                        onTime,
                        numberOfBook);

                // Get the return button and set action
                Button returnBtn = (Button) card.lookup("#returnButton");
                returnBtn.setOnAction(event -> {
                    if (!loan.getStatus().equals("RETURNED")) {
                        try {
                            loan.setReturnedAt(LocalDateTime.now());
                            loan.setStatus("RETURNED");
                            loanTable.Update(loan);
                            Alertmessage.showAlert(AlertType.INFORMATION, "Success", "rendu !");
                        } catch (SQLException e) {
                            Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
                            System.err.println("fail to Update  the loan :" + e);
                        }
                        return;
                    }
                    Alertmessage.showAlert(AlertType.ERROR, "Error", "deja rendu");
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

    /**
     * Méthode publique pour déclencher la mise à jour de la liste lors d'une modification des emprunts
     */
    public static void triggerUpdate() {
        listIsUpdate.set(true);
    }
}
