package com.example.librarymanager.Database;


import com.example.librarymanager.Models.User;
import com.example.librarymanager.utils.DatabaseUtils;
import com.example.librarymanager.utils.hashpassword;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserTable implements Repository<User> {

    private static final String INSERT_USER = "INSERT INTO users (username, password_hash, role , phone , birthdate , gender , address , email) VALUES (?, ?, ?, ?, ?, ?, ? , ?)";
    private static final String UPDATE_USER = "UPDATE users SET username = ?, password_hash = ?, role = ? , phone = ? , birthdate = ? , gender = ? , address = ? , email = ? WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String AUTHENTICATION_QUERY = "SELECT role, password_hash FROM users WHERE email = ?";

    @Override
    public void create(User user) {
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_USER)) {
            stmt.setString(1, user.getFullName());
            String passwordHash = hashpassword.CrypterMotdePasse(user.getPassword());
            stmt.setString(2, passwordHash);
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getPhone());
            String birthdateparse ;
            if( user.getBirthdate() == null){
                birthdateparse   = "1700-01-01" ;
            }
            else{
                birthdateparse =user.getBirthdate().format(DateTimeFormatter.ISO_LOCAL_DATE);
            }   
            stmt.setString(5,birthdateparse);
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getAddress());
            stmt.setString(8, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("user-create:"+e);  
        }
    }

    @Override
    public void Update(User user) {
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(UPDATE_USER)) {
            stmt.setString(1, user.getFullName());
            String passwordHash = hashpassword.CrypterMotdePasse(user.getPassword());
            stmt.setString(2, passwordHash);
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getPhone());
            String birthdateformat =user.getBirthdate().format(DateTimeFormatter.ISO_LOCAL_DATE);
            stmt.setString(5,birthdateformat);
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getAddress());
            stmt.setString(8, user.getEmail());
            stmt.setInt(9, user.getUser_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("user-update:"+e);  
        }
    }

    @Override
    public void Delete(int id) {
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(DELETE_USER)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("user-delete:"+e);  
        }
    }

    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_USERS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFullName(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getInt("phone"));
                String birthdatestr = rs.getString("birthdate");
                if( rs.getString("birthdate")== null){
                    birthdatestr = "1700-01-01" ;
                }
                user.setBirthdate(LocalDate.parse(birthdatestr, DateTimeFormatter.ISO_DATE));
                user.setGender(rs.getString("gender"));
                user.setAddress("address");
                user.setEmail("email");
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Loan-listAll:"+e);  
        }
        return users;
    }

    public static String Authenticate(String email, String password) {
        String role = null;
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(AUTHENTICATION_QUERY)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (hashpassword.decrypterMotdePasse(password, storedHash)) {
                    role = rs.getString("role");
                }
            }
        } catch (SQLException e) {
            System.err.println("user-Authentifier:"+e);  
        }
        return role;
    }
}
