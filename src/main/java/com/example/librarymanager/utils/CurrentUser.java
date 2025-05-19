package com.example.librarymanager.utils;

import com.example.librarymanager.Models.Books;
import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.Models.User;

/**
 * Utility class for storing and managing the current user's session data.
 *
 * This class is used to store and access the currently logged-in user, as well
 * as
 * the current loan and book context for controller transactions. All fields and
 * methods
 * are static, allowing global access throughout the application.
 *
 * Main features:
 * - Stores the current User, Loan, and Books objects.
 * - Provides static getter and setter methods for each field.
 * - Facilitates data sharing between controllers during user sessions and
 * transactions.
 *
 * Usage:
 * - Use CurrentUser.setUser(user) to set the active user.
 * - Use CurrentUser.getUser() to retrieve the active user.
 * - Similarly, use setLoan/getLoan and setBook/getBook for loan and book
 * context.
 *
 * Dependencies:
 * - User: the user data model.
 * - Loan: the loan data model.
 * - Books: the book data model.
 */
public class CurrentUser {
    private static User user;
    private static Loan loan;
    private static Books book;

    public static Books getBook() {
        return book;
    }

    public static void setBook(Books book) {
        CurrentUser.book = book;
    }

    public static Loan getLoan() {
        return loan;
    }

    public static void setLoan(Loan loan) {
        CurrentUser.loan = loan;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
    }
}
