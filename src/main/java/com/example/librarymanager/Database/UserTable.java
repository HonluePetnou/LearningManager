package com.example.librarymanager.Database;

import com.example.librarymanager.Models.User;
import com.example.librarymanager.utils.DatabaseUtils;
import com.example.librarymanager.utils.hashpassword;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) for managing users in the database.
 *
 * This class provides CRUD operations for users, as well as methods for
 * authentication,
 * listing all users, and counting users by role.
 *
 * Main features:
 * - Create, update, delete, and list all users.
 * - Authenticate users by email and password.
 * - Count the total number of users with the role "MEMBER".
 *
 * Dependencies:
 * - User: the user data model.
 * - DatabaseUtils: utility for obtaining database connections.
 * - hashpassword: utility for password hashing and verification.
 * - Repository<User>: interface for generic CRUD operations.
 *
 * SQL:
 * - Uses prepared statements for all queries.
 */
public class UserTable implements Repository<User> {

    private static final String INSERT_USER = "INSERT INTO users (username, password_hash, role , phone , birthdate , gender , address , email) VALUES (?, ?, ?, ?, ?, ?, ? , ?)";
    private static final String UPDATE_USER = "UPDATE users SET username = ?, password_hash = ?, role = ? , phone = ? , birthdate = ? , gender = ? , address = ? , email = ? WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users where role= ?";
    private static final String AUTHENTICATION_QUERY = "SELECT role, password_hash , username , user_id , email FROM users WHERE email = ?";
    private static final String COUNT_USERS_BY_ROLE = "SELECT COUNT(*) FROM users WHERE role = 'MEMBER'";

    /**
     * Adds a new user to the database.
     * 
     * @param user the user to add
     * @throws SQLException if a database error occurs
     */
    @Override
    public void create(User user) throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(INSERT_USER);
        stmt.setString(1, user.getFullName());
        String passwordHash = hashpassword.CrypterMotdePasse(user.getPassword());
        stmt.setString(2, passwordHash);
        stmt.setString(3, user.getRole());
        stmt.setInt(4, user.getPhone());
        String birthdateparse;
        if (user.getBirthdate() == null) {
            birthdateparse = null;
        } else {
            birthdateparse = user.getBirthdate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        stmt.setString(5, birthdateparse);
        stmt.setString(6, user.getGender());
        stmt.setString(7, user.getAddress());
        stmt.setString(8, user.getEmail());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * Updates an existing user in the database.
     * 
     * @param user the user to update
     * @throws SQLException if a database error occurs
     */
    @Override
    public void Update(User user) throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(UPDATE_USER);
        stmt.setString(1, user.getFullName());
        String passwordHash = hashpassword.CrypterMotdePasse(user.getPassword());
        stmt.setString(2, passwordHash);
        stmt.setString(3, user.getRole());
        stmt.setInt(4, user.getPhone());
        String birthdateformat = user.getBirthdate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        stmt.setString(5, birthdateformat);
        stmt.setString(6, user.getGender());
        stmt.setString(7, user.getAddress());
        stmt.setString(8, user.getEmail());
        stmt.setInt(9, user.getUser_id());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * Deletes a user from the database by their ID.
     * 
     * @param id the user's ID
     * @throws SQLException if a database error occurs
     */
    @Override
    public void Delete(int id) throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(DELETE_USER);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * Retrieves all users with the role "MEMBER" from the database.
     * 
     * @return a list of users
     * @throws SQLException if a database error occurs
     */
    @Override
    public List<User> listAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_USERS);
        stmt.setString(1, "MEMBER");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setUser_id(rs.getInt("user_id"));
            user.setFullName(rs.getString("username"));
            user.setRole(rs.getString("role"));
            user.setPhone(rs.getInt("phone"));
            String birthdatestr = rs.getString("birthdate");
            if (rs.getString("birthdate") == null) {
                user.setBirthdate(null);
            } else {
                user.setBirthdate(LocalDate.parse(birthdatestr, DateTimeFormatter.ISO_DATE));
            }
            user.setGender(rs.getString("gender"));
            user.setAddress(rs.getString("address"));
            user.setEmail(rs.getString("email"));
            users.add(user);
        }
        rs.close();
        stmt.close();
        conn.close();
        return users;
    }

    /**
     * Authenticates a user by email and password.
     * 
     * @param email    the user's email
     * @param password the user's password
     * @return the authenticated User object, or a User with null role if
     *         authentication fails
     * @throws SQLException if a database error occurs
     */
    public static User Authenticate(String email, String password) throws SQLException {
        User user = new User();
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(AUTHENTICATION_QUERY);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String storedHash = rs.getString("password_hash");
            if (hashpassword.decrypterMotdePasse(password, storedHash)) {
                user.setUser_id(rs.getInt("user_id"));
                user.setRole(rs.getString("role"));
                user.setFullName(rs.getString("username"));
            } else {
                user.setRole(null);
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        return user;
    }

    /**
     * Counts the total number of users with the role "MEMBER".
     * 
     * @return the number of users
     * @throws SQLException if a database error occurs
     */
    @Override
    public int Count() throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(COUNT_USERS_BY_ROLE);
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
}
