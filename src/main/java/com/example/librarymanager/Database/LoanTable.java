package com.example.librarymanager.Database;

import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoanTable implements Repository<Loan> {
  private static final String INSERT_LOAN = "INSERT INTO loans (book_id, user_id, due_at ,number_of_book ) VALUES (?, ?, ? , ?)";
  private static final String UPDATE_LOAN = "UPDATE loans SET status = ?, returned_at = ? WHERE loan_id = ?";
  private static final String DELETE_LOAN = "DELETE FROM loans WHERE loan_id = ?";
  private static final String SELECT_ALL_LOANS = "SELECT l.* , b.title , u.username FROM loans l join books b on l.book_id = b.book_id join users u on l.user_id = u.user_id where status = 'ONGOING' or status = 'OVERDUE' order by l.loan_id desc";
  private static final String COUNT_LOANS = "SELECT COUNT(*) FROM loans";
  private static final String SELECT_LOAN_ID = "SELECT loan_id FROM loans where ( user_id= ? AND book_id = ?) AND (status = 'ONGOING' or status = 'OVERDUE')";

  @Override
  public void create(Loan loan) throws SQLException {
    Connection conn = DatabaseUtils.getConnection();
    PreparedStatement stmt = conn.prepareStatement(INSERT_LOAN);
    stmt.setInt(1, loan.getBookId());
    stmt.setInt(2, loan.getUserId());
    if (loan.getDueAt() == null) {
      throw new SQLException("Due date cannot be null");
    }

    String formattedDate = loan.getDueAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ");
    stmt.setString(3, formattedDate);
    stmt.setInt(4, loan.getNumberOfBook());
    stmt.executeUpdate();
    stmt.close();
    conn.close();
  }

  @Override
  public void Update(Loan loan) throws SQLException {
    Connection conn = DatabaseUtils.getConnection();
    PreparedStatement stmt = conn.prepareStatement(UPDATE_LOAN);
    stmt.setString(1, loan.getStatus());

    if (loan.getReturnedAt() == null) {
      throw new SQLException("Returned date cannot be null");
    }

    String formattedDate = loan.getReturnedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ");
    stmt.setString(2, formattedDate);
    stmt.setInt(3, loan.getLoanId());
    stmt.executeUpdate();
    stmt.close();
    conn.close();
  }

  @Override
  public void Delete(int id) throws SQLException {
    Connection conn = DatabaseUtils.getConnection();
    PreparedStatement stmt = conn.prepareStatement(DELETE_LOAN);
    stmt.setInt(1, id);
    stmt.executeUpdate();
    stmt.close();
    conn.close();
  }

  @Override
  public List<Loan> listAll() throws SQLException {
    List<Loan> loans = new ArrayList<>();
    Connection conn = DatabaseUtils.getConnection();
    PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_LOANS);
    ResultSet rs = stmt.executeQuery();
    while (rs.next()) {
      Loan loan = new Loan();
      loan.setLoanId(rs.getInt("loan_id"));
      loan.setBook_name(rs.getString("title"));
      loan.setUser_name(rs.getString("username"));
      loan.setBookId(rs.getInt("book_id"));
      loan.setUserId(rs.getInt("user_id"));
      loan.setNumberOfBook(rs.getInt("number_of_book"));
      String borrowedDate = rs.getString("borrowed_at");
      if (borrowedDate != null && !borrowedDate.isEmpty()) {
        loan.setBorrowedAt(LocalDateTime.parse(borrowedDate.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
      }

      String dueAtStr = rs.getString("due_at");
      if (dueAtStr != null && !dueAtStr.isEmpty()) {
        loan.setDueAt(LocalDateTime.parse(dueAtStr.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
      }

      String returnedAtStr = rs.getString("returned_at");
      if (returnedAtStr != null && !returnedAtStr.isEmpty()) {
        loan.setReturnedAt(LocalDateTime.parse(returnedAtStr.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
      }

      loan.setStatus(rs.getString("status"));
      loans.add(loan);
    }
    rs.close();
    stmt.close();
    conn.close();
    return loans;
  }

  @Override
  public int Count() throws SQLException {
    Connection conn = DatabaseUtils.getConnection();
    PreparedStatement stmt = conn.prepareStatement(COUNT_LOANS);
    ResultSet rs = stmt.executeQuery();
    int count = 0;
    if (rs.next()) {
      count = rs.getInt(1);
    }
    rs.close();
    stmt.close();
    conn.close();
    return count;
  }

  public int getLoanId(int user_id, int book_id) throws SQLException {
    int id = 0;
    Connection conn = DatabaseUtils.getConnection();
    PreparedStatement stmt = conn.prepareStatement(SELECT_LOAN_ID);
    stmt.setInt(1, user_id);
    stmt.setInt(2, book_id);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
      id = rs.getInt(1);
    }
    rs.close();
    stmt.close();
    conn.close();
    return id;
  }
}
