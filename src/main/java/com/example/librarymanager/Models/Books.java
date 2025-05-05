package com.example.librarymanager.Models;

public class Books {
    private int book_id; 
    private String title; 
    private String author; 
    private String isbn; 
    private int category_id; 
    private int published_year; 
    private int copies_total; 
    private int copies_available; 

    public Books() {
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPublished_year() {
        return published_year;
    }

    public void setPublished_year(int published_year) {
        this.published_year = published_year;
    }

    public int getCopies_total() {
        return copies_total;
    }

    public void setCopies_total(int copies_total) {
        this.copies_total = copies_total;
    }

    public int getCopies_available() {
        return copies_available;
    }

    public void setCopies_available(int copies_available) {
        this.copies_available = copies_available;
    }
}
