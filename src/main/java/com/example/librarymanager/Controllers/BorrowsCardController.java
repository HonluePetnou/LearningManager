package com.example.librarymanager.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

   

    public void initialize() {
        // Initialize controller
    }
    
    public void setLoanData(String bookTitle, String borrowerName, String loanDate, String returnDate, boolean onTime , int numberOfBook) {
        this.bookTitle = bookTitle;
        this.borrowerName = borrowerName;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.onTime = onTime;
        this.numberOfBook = numberOfBook;
        updateUI();
    }
    
    private void updateUI() {
        bookTitleLabel.setText("📖 " + bookTitle);
        borrowerLabel.setText("👤 " + borrowerName);
        loanDateLabel.setText("📅 Emprunt : " + loanDate);
        returnDateLabel.setText("🕑 Retour : " + returnDate);
        numberofbookLabel.setText("📚 Nombre de livre : " + numberOfBook);
        
        if (onTime) {
            statusLabel.setText("🟢 À temps");
            statusLabel.getStyleClass().add("green");
        } else {
            statusLabel.setText("🔴 En retard");
            statusLabel.getStyleClass().add("red");
        }
    }

    public String getBookTitle() { return bookTitle; }
    public String getBorrowerName() { return borrowerName; }
    public String getLoanDate() { return loanDate; }
    public String getReturnDate() { return returnDate; }
    public boolean isOnTime() { return onTime; }
    public int getNumberOfBook() {
        return numberOfBook;
    }
}
