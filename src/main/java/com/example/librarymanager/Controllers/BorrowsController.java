package com.example.librarymanager.Controllers;

import com.example.librarymanager.Database.LoanTable;
import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.utils.Alertmessage;
import com.example.librarymanager.utils.CurrentUser;

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

/**
 * JavaFX controller for managing and displaying the list of book loans
 * (borrows).
 *
 * This controller handles:
 * - Displaying all current loans in a grid layout.
 * - Listening for updates to the loan list and refreshing the UI accordingly.
 * - Handling the return of borrowed books, updating the database and UI.
 *
 * Main features:
 * - Loads all loans from the database and displays them as cards in a grid.
 * - Each card shows book title, borrower, loan date, return date, status, and a
 * return button.
 * - When a book is returned, updates the loan status and removes the card from
 * the grid.
 * - Provides a static method to trigger a refresh of the loan list from other
 * parts of the application.
 *
 * Dependencies:
 * - LoanTable: for loan database operations.
 * - BorrowsCardController: for displaying each loan as a card.
 * - Alertmessage: for user feedback.
 * - CurrentUser: for accessing the current user's information.
 *
 * FXML requirements:
 * - GridPane: loanGrid
 */
public class BorrowsController {

    @FXML
    private GridPane loanGrid;

    private LoanTable loanTable = new LoanTable();

    /**
     * Property used to trigger updates of the loan list.
     */
    private static BooleanProperty listIsUpdate = new SimpleBooleanProperty(false);

    /**
     * Initializes the controller, sets up the update listener, and loads the
     * initial loan list.
     */
    @SuppressWarnings("unused")
    public void initialize() {
        // Add a listener to refresh the list when triggered
        listIsUpdate.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                updateBorrowsList();
                listIsUpdate.set(false); // Reset after update
            }
        });

        updateBorrowsList(); // Initial load
    }

    /**
     * Loads all loans from the database and displays them as cards in the grid.
     * Sets up the return button for each loan card.
     */
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
                    try {
                        int loan_id = loanTable.getLoanId(CurrentUser.getUser().getUser_id(), loan.getBookId());
                        if (!loan.getStatus().equals("RETURNED") && loan_id == 0) {
                            try {
                                loan.setReturnedAt(LocalDateTime.now());
                                loan.setStatus("RETURNED");
                                loanTable.Update(loan);
                                Alertmessage.showAlert(AlertType.INFORMATION, "Success", "Returned");
                                // Remove the card from the grid after successful return
                                loanGrid.getChildren().remove(card);
                            } catch (SQLException e) {
                                Alertmessage.showAlert(AlertType.ERROR, "Error", "Internal error");
                                System.err.println("Fail to Update  the loan : " + e.getMessage());
                            }
                            return;
                        }
                        Alertmessage.showAlert(AlertType.ERROR, "Error", "Already returned");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
     * Public static method to trigger the update of the loan list from other parts
     * of the application.
     */
    public static void triggerUpdate() {
        listIsUpdate.set(true);
    }
}
