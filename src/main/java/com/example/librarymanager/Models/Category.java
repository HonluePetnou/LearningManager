package com.example.librarymanager.Models;

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
