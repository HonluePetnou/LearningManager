package com.example.librarymanager.Models;

import java.time.LocalDateTime;


public class Loan {
    private int loanId; 
    private int bookId; 
    private int userId; 
    private LocalDateTime borrowedAt; 
    private LocalDateTime dueAt; 
    private LocalDateTime returnedAt; 
    private String status; 
    private String book_name ;
    private String user_name ;
    private int numberOfBook;

    public Loan() {
    }

    
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBook_name() {
        return book_name;
    }


    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }


    public String getUser_name() {
        return user_name;
    }


    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getNumberOfBook() {
        return numberOfBook;
    }


    public void setNumberOfBook(int numberOfBook) {
        this.numberOfBook = numberOfBook;
    }
}
