// classe utilitaire pour verifier le fonctionnement de la base de donnee via la console 
package com.example.librarymanager.utils;

import com.example.librarymanager.Database.*;
import com.example.librarymanager.Models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for testing database operations via the console.
 *
 * This class provides a simple command-line interface to test CRUD operations
 * for UserTable, LoanTable, and BooksTable. It allows you to create, list,
 * update, delete, and search users, loans, and books directly from the console.
 *
 * Main features:
 * - Interactive menu for selecting which table to test (User, Loan, Book).
 * - Sub-menus for each table to perform CRUD operations and authentication.
 * - Reads input from the user and displays results or error messages.
 *
 * Usage:
 * - Run the main method to start the interactive console tester.
 * - Follow the prompts to test various database operations.
 *
 * Dependencies:
 * - UserTable, LoanTable, BooksTable: for database operations.
 * - User, Loan, Books: data models.
 * - java.util.Scanner: for reading user input.
 * - java.time.LocalDateTime: for handling date/time fields.
 */
public class TestDatabase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserTable userTable = new UserTable();
        LoanTable loanTable = new LoanTable();
        BooksTable booksTable = new BooksTable();

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Test UserTable");
            System.out.println("2. Test LoanTable");
            System.out.println("3. Test BooksTable");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    testUserTable(userTable, scanner);
                    break;
                case 2:
                    testLoanTable(loanTable, scanner);
                    break;
                case 3:
                    testBooksTable(booksTable, scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void testUserTable(UserTable userTable, Scanner scanner) {
        System.out.println("Testing UserTable...");
        System.out.println("1. Create User");
        System.out.println("2. List All Users");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.println("5. Authenticate User");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter username:");
                String username = scanner.nextLine();
                System.out.println("Enter password:");
                String password = scanner.nextLine();
                System.out.println("Enter role:");
                String role = scanner.nextLine();
                System.out.println("Enter Email");
                String email = scanner.nextLine();
                User newUser = new User();
                newUser.setFullName(username);
                newUser.setPassword(password);
                newUser.setRole(role);
                newUser.setEmail(email);
                try {
                    userTable.create(newUser);
                } catch (Exception e) {
                    System.out.println("Error creating user: " + e.getMessage());
                    return;
                }
                System.out.println("User created.");
                break;
            case 2:
                try {
                    List<User> users = userTable.listAll();
                    // users.forEach(System.out::println);
                    System.out.println("List of users:");
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                        return;
                    }
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println(users.get(i).getFullName());
                    }
                } catch (Exception e) {
                    System.out.println("Error listing users: " + e.getMessage());
                    return;
                }

                break;
            case 3:
                System.out.println("Enter user ID to update:");
                int userId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter new username:");
                String newUsername = scanner.nextLine();
                System.out.println("Enter new password:");
                String newPassword = scanner.nextLine();
                System.out.println("Enter new role:");
                String newRole = scanner.nextLine();
                User updatedUser = new User();
                updatedUser.setUser_id(userId);
                updatedUser.setFullName(newUsername);
                updatedUser.setPassword(newPassword);
                updatedUser.setRole(newRole);
                try {
                    userTable.Update(updatedUser);
                } catch (Exception e) {
                    System.out.println("Error updating user: " + e.getMessage());
                    return;
                }
                System.out.println("User updated.");
                break;
            case 4:
                System.out.println("Enter user ID to delete:");
                int deleteUserId = scanner.nextInt();
                try {
                    userTable.Delete(deleteUserId);
                } catch (Exception e) {
                    System.out.println("Error deleting user: " + e.getMessage());
                    return;
                }
                System.out.println("User deleted.");
                break;
            case 5:
                System.out.println("Enter username:");
                String authUsername = scanner.nextLine();
                System.out.println("Enter password:");
                String authPassword = scanner.nextLine();
                try {
                    String authRole = UserTable.Authenticate(authUsername, authPassword).getRole();
                    if (authRole == null) {
                        System.out.println("Authentication failed.");
                        return;
                    }
                    System.out.println("Authenticated role: " + authRole);
                } catch (Exception e) {
                    System.out.println("Error authenticating user: " + e.getMessage());
                    return;
                }

                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void testLoanTable(LoanTable loanTable, Scanner scanner) {
        System.out.println("Testing LoanTable...");
        System.out.println("1. Create Loan");
        System.out.println("2. List All Loans");
        System.out.println("3. Update Loan");
        System.out.println("4. Delete Loan");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter book ID:");
                int bookId = scanner.nextInt();
                System.out.println("Enter user ID:");
                int userId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter due date (yyyy-MM-ddTHH:mm:ss):");
                String dueDateStr = scanner.nextLine();
                LocalDateTime dueDate = LocalDateTime.parse(dueDateStr);
                Loan newLoan = new Loan();
                newLoan.setBookId(bookId);
                newLoan.setUserId(userId);
                newLoan.setDueAt(dueDate);
                try {
                    loanTable.create(newLoan);
                    System.out.println("Loan created.");
                } catch (Exception e) {
                    System.out.println("Error creating loan: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    List<Loan> loans = loanTable.listAll();
                    // loans.forEach(System.out::println);
                    if (loans.isEmpty()) {
                        System.out.println("No loans found.");
                    } else {
                        for (int i = 0; i < loans.size(); i++) {
                            System.out.println("Loan ID: " + loans.get(i).getLoanId() +
                                    ", Borrowed At: " + loans.get(i).getBorrowedAt() +
                                    ", Status: " + loans.get(i).getStatus());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error listing loans: " + e.getMessage());
                }
                break;
            case 3:
                System.out.println("Enter loan ID to update:");
                int loanId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter new status:");
                String status = scanner.nextLine();
                System.out.println("Enter returned date (yyyy-MM-ddTHH:mm:ss):");
                String returnedDateStr = scanner.nextLine();
                LocalDateTime returnedDate = LocalDateTime.parse(returnedDateStr);
                Loan updatedLoan = new Loan();
                updatedLoan.setLoanId(loanId);
                updatedLoan.setStatus(status);
                updatedLoan.setReturnedAt(returnedDate);
                try {
                    loanTable.Update(updatedLoan);
                    System.out.println("Loan updated.");
                } catch (Exception e) {
                    System.out.println("Error updating loan: " + e.getMessage());
                }
                break;
            case 4:
                System.out.println("Enter loan ID to delete:");
                int deleteLoanId = scanner.nextInt();
                try {
                    loanTable.Delete(deleteLoanId);
                    System.out.println("Loan deleted.");
                } catch (Exception e) {
                    System.out.println("Error deleting loan: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void testBooksTable(BooksTable booksTable, Scanner scanner) {
        System.out.println("Testing BooksTable...");
        System.out.println("1. Add Book");
        System.out.println("2. List All Books");
        System.out.println("3. Update Book");
        System.out.println("4. Delete Book");
        System.out.println("5. Search Books");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter title:");
                String title = scanner.nextLine();
                System.out.println("Enter author:");
                String author = scanner.nextLine();
                System.out.println("Enter ISBN:");
                String isbn = scanner.nextLine();
                System.out.println("Enter category ID:");
                int categoryId = scanner.nextInt();
                System.out.println("Enter published year:");
                int publishedYear = scanner.nextInt();
                System.out.println("Enter total copies:");
                int copiesTotal = scanner.nextInt();
                System.out.println("Enter available copies:");
                int copiesAvailable = scanner.nextInt();
                scanner.nextLine();
                Books newBook = new Books();
                newBook.setTitle(title);
                newBook.setAuthor(author);
                newBook.setIsbn(isbn);
                newBook.setCategory_id(categoryId);
                newBook.setPublished_year(publishedYear);
                newBook.setCopies_total(copiesTotal);
                newBook.setCopies_available(copiesAvailable);
                try {
                    booksTable.create(newBook);
                    System.out.println("Book added.");
                } catch (Exception e) {
                    System.out.println("Error adding book: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    List<Books> books = booksTable.listAll();
                    // books.forEach(System.out::println);
                    if (books.isEmpty()) {
                        System.out.println("No books found.");
                    } else {
                        for (int i = 0; i < books.size(); i++) {
                            System.out.println(
                                    "Title: " + books.get(i).getTitle() + ", Author: " + books.get(i).getAuthor());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error listing books: " + e.getMessage());
                }
                break;
            case 3:
                System.out.println("Enter book ID to update:");
                int bookId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter new title:");
                String newTitle = scanner.nextLine();
                System.out.println("Enter new author:");
                String newAuthor = scanner.nextLine();
                System.out.println("Enter new ISBN:");
                String newIsbn = scanner.nextLine();
                System.out.println("Enter new category ID:");
                int newCategoryId = scanner.nextInt();
                System.out.println("Enter new published year:");
                int newPublishedYear = scanner.nextInt();
                System.out.println("Enter new total copies:");
                int newCopiesTotal = scanner.nextInt();
                System.out.println("Enter new available copies:");
                int newCopiesAvailable = scanner.nextInt();
                scanner.nextLine();
                Books updatedBook = new Books();
                updatedBook.setBook_id(bookId);
                updatedBook.setTitle(newTitle);
                updatedBook.setAuthor(newAuthor);
                updatedBook.setIsbn(newIsbn);
                updatedBook.setCategory_id(newCategoryId);
                updatedBook.setPublished_year(newPublishedYear);
                updatedBook.setCopies_total(newCopiesTotal);
                updatedBook.setCopies_available(newCopiesAvailable);
                try {
                    booksTable.Update(updatedBook);
                    System.out.println("Book updated.");
                } catch (Exception e) {
                    System.out.println("Error updating book: " + e.getMessage());
                }
                break;
            case 4:
                System.out.println("Enter book ID to delete:");
                int deleteBookId = scanner.nextInt();
                try {
                    booksTable.Delete(deleteBookId);
                    System.out.println("Book deleted.");
                } catch (Exception e) {
                    System.out.println("Error deleting book: " + e.getMessage());
                }
                break;
            case 5:
                System.out.println("Enter title to search (use % for wildcards):");
                String searchTitle = scanner.nextLine();
                System.out.println("Enter category to search (use % for wildcards):");
                String searchCategory = scanner.nextLine();
                System.out.println("Enter author to search (use % for wildcards):");
                String searchAuthor = scanner.nextLine();
                try {
                    List<Books> searchResults = booksTable.search(searchTitle, searchCategory, searchAuthor);
                    if (searchResults.isEmpty()) {
                        System.out.println("No books found matching your search criteria.");
                    } else {
                        searchResults.forEach(System.out::println);
                    }
                } catch (Exception e) {
                    System.out.println("Error searching books: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
