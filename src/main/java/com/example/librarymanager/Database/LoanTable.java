package com.example.librarymanager.Database;

import com.example.librarymanager.Models.Loan;
import com.example.librarymanager.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoanTable implements Repository<Loan> {
  private static final String INSERT_LOAN = "INSERT INTO loans (book_id, user_id, due_at) VALUES (?, ?, ?)";
  private static final String UPDATE_LOAN = "UPDATE loans SET status = ?, returned_at = ? WHERE loan_id = ?";
  private static final String DELETE_LOAN = "DELETE FROM loans WHERE loan_id = ?";
  private static final String SELECT_ALL_LOANS = "SELECT * FROM loans";

  @Override
  public void create(Loan loan) {
    try (Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(INSERT_LOAN)) {
      stmt.setInt(1, loan.getBookId());
      stmt.setInt(2, loan.getUserId());
      String formattedDate = loan.getDueAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ");
      stmt.setString(3, formattedDate);
      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Loan-create:"+e);  
    }
  }

  @Override
  public void Update(Loan loan) {
    try (Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(UPDATE_LOAN)) {
      stmt.setString(1, loan.getStatus());
      String formattedDate = loan.getReturnedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ");
      stmt.setString(2, formattedDate);
      stmt.setInt(3, loan.getLoanId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Loan-update:"+e);  
    }
  }

  @Override
  public void Delete(int id) {
    try (Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(DELETE_LOAN)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Loan-delete:"+e);  
    }
  }

  @Override
  public List<Loan> listAll() {
    List<Loan> loans = new ArrayList<>();
    try (Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_LOANS);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        Loan loan = new Loan();
        loan.setLoanId(rs.getInt("loan_id"));
        loan.setBookId(rs.getInt("book_id"));
        loan.setUserId(rs.getInt("user_id"));
        String borrowedDate = rs.getString("borrowed_at").replace(" ", "T");
        if (borrowedDate != null) {
          loan.setBorrowedAt(LocalDateTime.parse(borrowedDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        String dueAtStr = rs.getString("due_at").replace(" ", "T");
        if (dueAtStr != null) {
          loan.setDueAt(LocalDateTime.parse(dueAtStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        loans.add(loan);
      }
    } catch (SQLException e) {
      System.err.println("Loan-listAll:"+e);  
    }
    return loans;
  }
}
