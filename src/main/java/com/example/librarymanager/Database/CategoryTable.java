package com.example.librarymanager.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.librarymanager.Models.Category;
import com.example.librarymanager.utils.DatabaseUtils;

public class CategoryTable implements Repository<Category> {
    private static final String QUERY_LIST_ALL_CATEGORIES = "SELECT * FROM category;";
    private static final String QUERY_ADD_CATEGORY = "INSERT INTO category (name) VALUES (?);";
    private static final String QUERY_UPDATE_CATEGORY = "UPDATE category SET name = ? WHERE category_id = ?;";
    private static final String QUERY_DELETE_CATEGORY = "DELETE FROM category WHERE category_id = ?;";

    @Override
    public void create(Category category) throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_ADD_CATEGORY);
        stmt.setString(1, category.getName());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

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

    @Override
    public void Delete(int id) throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_CATEGORY);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
   @Override
   public List<Category> listAll() throws SQLException{
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
}
