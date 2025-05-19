package com.example.librarymanager.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * JavaFX controller for displaying a borrow (loan) card in the UI.
 *
 * This controller manages the display of a single loan's information,
 * including:
 * - Book title
 * - Borrower's name
 * - Loan date and return date
 * - Number of books borrowed
 * - Loan status (on time or overdue)
 * - Return button for processing book returns
 *
 * Main features:
 * - Sets and displays all relevant loan data on the card.
 * - Updates the UI with appropriate icons and status colors.
 * - Provides getter methods for loan details.
 *
 * FXML requirements:
 * - Labels: bookTitleLabel, borrowerLabel, loanDateLabel, returnDateLabel,
 * statusLabel, numberofbookLabel
 * - Button: returnButton
 */
public class BorrowsCardController {
    @FXML
    private Label bookTitleLabel;
    @FXML
    private Label borrowerLabel;
    @FXML
    private Label loanDateLabel;
    @FXML
    private Label returnDateLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button returnButton;
    @FXML
    private Label numberofbookLabel;

    private String bookTitle;
    private String borrowerName;
    private String loanDate;
    private String returnDate;
    private boolean onTime;
    private int numberOfBook;

    /**
     * Initializes the controller.
     */
    public void initialize() {
        // Initialize controller
    }

    /**
     * Sets the loan data to be displayed on the card and updates the UI.
     *
     * @param bookTitle    the title of the borrowed book
     * @param borrowerName the name of the borrower
     * @param loanDate     the date the book was borrowed
     * @param returnDate   the date the book was returned (or due)
     * @param onTime       true if the book was returned on time, false if overdue
     * @param numberOfBook the number of books borrowed
     */
    public void setLoanData(String bookTitle, String borrowerName, String loanDate, String returnDate, boolean onTime,
            int numberOfBook) {
        this.bookTitle = bookTitle;
        this.borrowerName = borrowerName;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.onTime = onTime;
        this.numberOfBook = numberOfBook;
        updateUI();
    }

    /**
     * Updates the UI labels and status based on the current loan data.
     */
    private void updateUI() {
        bookTitleLabel.setText("📖 " + bookTitle);
        borrowerLabel.setText("👤 " + borrowerName);
        loanDateLabel.setText("📅 Borrowed at : " + loanDate);
        returnDateLabel.setText("🕑 Returned at : " + returnDate);
        numberofbookLabel.setText("📚 Number of books: " + numberOfBook);

        if (onTime) {
            statusLabel.setText("🟢 In time");
            statusLabel.getStyleClass().add("green");
        } else {
            statusLabel.setText("🔴 Overdue");
            statusLabel.getStyleClass().add("red");
        }
    }

    // Getters for loan details

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public boolean isOnTime() {
        return onTime;
    }

    public int getNumberOfBook() {
        return numberOfBook;
    }
}
