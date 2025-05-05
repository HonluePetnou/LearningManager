package com.example.librarymanager.Database;
import com.example.librarymanager.Models.Books;

import com.example.librarymanager.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksTable implements Repository<Books> {

    private static final String QUERY_LIST_ALL_BOOKS = "SELECT b.* , c.name as category FROM books b  left join category c on b.category_id = c.category_id ;";
    private static final String QUERY_SEARCH_BOOKS = "SELECT b.*, c.name as category FROM books b LEFT JOIN category c ON b.category_id = c.category_id WHERE b.title LIKE ? AND c.name LIKE ? AND b.author LIKE ?;";
    private static final String QUERY_ADD_BOOK = "INSERT INTO books (title, author, isbn, category_id, published_year, copies_total, copies_available) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String QUERY_UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, isbn = ?, category_id = ?, published_year = ?, copies_total = ?, copies_available = ? WHERE book_id = ?;";
    private static final String QUERY_DELETE_BOOK = "DELETE FROM books WHERE book_id = ?;";

    @Override
    public void create(Books book) {
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QUERY_ADD_BOOK)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getCategory_id());
            stmt.setInt(5, book.getPublished_year());
            stmt.setInt(6, book.getCopies_total());
            stmt.setInt(7, book.getCopies_available());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("BooksTable-create:"+e);
        }
    }

    @Override      
    public void Update(Books book) {
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE_BOOK)) 
             {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getCategory_id());
            stmt.setInt(5, book.getPublished_year());
            stmt.setInt(6, book.getCopies_total());
            stmt.setInt(7, book.getCopies_available());
            stmt.setInt(8, book.getBook_id());          
              stmt.executeUpdate();        } 
              catch (SQLException e) {           
                System.err.println("BooksTable-update:"+e);     
                 }   
                  }

    @Override
    public void Delete(int id) {
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_BOOK)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("BooksTable-Delete:"+e);    
        }
    }

    @Override
    public List<Books> listAll() {
        List<Books> books = new ArrayList<>();
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QUERY_LIST_ALL_BOOKS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Books book = new Books();
                book.setBook_id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory_id(rs.getInt("category_id"));
                book.setPublished_year(rs.getInt("published_year"));
                book.setCopies_total(rs.getInt("copies_total"));
                book.setCopies_available(rs.getInt("copies_available"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("BooksTable-listAll:"+e);  
        }
        return books;
    }

    public List<Books> search(String title, String category, String author) {
        List<Books> books = new ArrayList<>();
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QUERY_SEARCH_BOOKS)) {
            stmt.setString(1, "%" + title + "%");
            stmt.setString(2, "%" + category + "%");
            stmt.setString(3, "%" + author + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Books book = new Books();
                book.setBook_id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory_id(rs.getInt("category_id"));
                book.setPublished_year(rs.getInt("published_year"));
                book.setCopies_total(rs.getInt("copies_total"));
                book.setCopies_available(rs.getInt("copies_available"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("BooksTable-search:"+e);  
        }
        return books;
    }

   
} 