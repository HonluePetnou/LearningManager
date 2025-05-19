package com.example.librarymanager.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.librarymanager.Models.Category;
import com.example.librarymanager.utils.DatabaseUtils;

/**
 * Data access object (DAO) for managing categories in the database.
 *
 * This class provides CRUD operations for categories, as well as methods to
 * list all categories and count the total number of categories.
 *
 * Main features:
 * - Create, update, delete, and list all categories.
 * - Count the total number of categories in the database.
 *
 * Dependencies:
 * - Category: the category data model.
 * - DatabaseUtils: utility for obtaining database connections.
 * - Repository<Category>: interface for generic CRUD operations.
 *
 * SQL:
 * - Uses prepared statements for all queries.
 */
public class CategoryTable implements Repository<Category> {
    private static final String QUERY_LIST_ALL_CATEGORIES = "SELECT * FROM category;";
    private static final String QUERY_ADD_CATEGORY = "INSERT INTO category (name) VALUES (?);";
    private static final String QUERY_UPDATE_CATEGORY = "UPDATE category SET name = ? WHERE category_id = ?;";
    private static final String QUERY_DELETE_CATEGORY = "DELETE FROM category WHERE category_id = ?;";
    private static final String COUNT_CATEGORIES = "SELECT COUNT(*) FROM category;";

    /**
     * Adds a new category to the database.
     * 
     * @param category the category to add
     * @throws SQLException if a database error occurs
     */
    @Override
    public void create(Category category) throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_ADD_CATEGORY);
        stmt.setString(1, category.getName());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * Updates an existing category in the database.
     * 
     * @param category the category to update
     * @throws SQLException if a database error occurs
     */
    @Override
    public void Update(Category category) throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE_CATEGORY);
        stmt.setString(1, category.getName());
        stmt.setInt(2, category.getCategory_id());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * Deletes a category from the database by its ID.
     * 
     * @param id the category's ID
     * @throws SQLException if a database error occurs
     */
    @Override
    public void Delete(int id) throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_CATEGORY);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * Retrieves all categories from the database.
     * 
     * @return a list of all categories
     * @throws SQLException if a database error occurs
     */
    @Override
    public List<Category> listAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_LIST_ALL_CATEGORIES);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Category category = new Category();
            category.setCategory_id(rs.getInt("category_id"));
            category.setName(rs.getString("name"));
            categories.add(category);
        }
        rs.close();
        stmt.close();
        conn.close();
        return categories;
    }

    /**
     * Counts the total number of categories in the database.
     * 
     * @return the number of categories
     * @throws SQLException if a database error occurs
     */
    @Override
    public int Count() throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(COUNT_CATEGORIES);
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
