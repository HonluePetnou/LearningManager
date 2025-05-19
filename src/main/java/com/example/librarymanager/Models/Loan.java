package com.example.librarymanager.Models;

import java.time.LocalDateTime;

/**
 * Data model class representing a loan (borrow) in the library system.
 *
 * This class encapsulates all properties of a loan, including its ID,
 * associated book and user,
 * loan dates, status, and additional display information.
 *
 * Main features:
 * - Getters and setters for all loan properties.
 * - Stores both IDs and display names for book and user.
 * - Tracks the number of books borrowed in a single loan.
 *
 * Fields:
 * - int loanId: Unique identifier for the loan.
 * - int bookId: ID of the borrowed book.
 * - int userId: ID of the user who borrowed the book.
 * - LocalDateTime borrowedAt: Date and time when the book was borrowed.
 * - LocalDateTime dueAt: Due date and time for returning the book.
 * - LocalDateTime returnedAt: Date and time when the book was returned.
 * - String status: Status of the loan (e.g., ONGOING, OVERDUE, RETURNED).
 * - String book_name: Title of the borrowed book (for display).
 * - String user_name: Name of the borrower (for display).
 * - int numberOfBook: Number of books borrowed in this loan.
 */
public class Loan {
    private int loanId;
    private int bookId;
    private int userId;
    private LocalDateTime borrowedAt;
    private LocalDateTime dueAt;
    private LocalDateTime returnedAt;
    private String status;
    private String book_name;
    private String user_name;
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
