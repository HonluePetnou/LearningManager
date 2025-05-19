package com.example.librarymanager.Models;

/**
 * Data model class representing a book category.
 *
 * This class encapsulates the properties of a category, including its unique ID
 * and name.
 *
 * Main features:
 * - Provides constructors for creating category instances.
 * - Getters and setters for all category properties.
 *
 * Fields:
 * - int category_id: Unique identifier for the category.
 * - String name: Name of the category.
 */
public class Category {
    private int category_id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
