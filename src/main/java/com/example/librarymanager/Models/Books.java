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
    private String image_path;  // ✅ ajout du champ image

    public Books() {
    }

    public Books(int book_id, String title, String author, String isbn, int category_id, int published_year, int copies_total, int copies_available, String image_path) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category_id = category_id;
        this.published_year = published_year;
        this.copies_total = copies_total;
        this.copies_available = copies_available;
        this.image_path = image_path;
    }

    // Getters et Setters

    public int getBook_id() { return book_id; }
    public void setBook_id(int book_id) { this.book_id = book_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }

    public int getPublished_year() { return published_year; }
    public void setPublished_year(int published_year) { this.published_year = published_year; }

    public int getCopies_total() { return copies_total; }
    public void setCopies_total(int copies_total) { this.copies_total = copies_total; }

    public int getCopies_available() { return copies_available; }
    public void setCopies_available(int copies_available) { this.copies_available = copies_available; }

    public String getImage_path() { return image_path; }
    public void setImage_path(String image_path) { this.image_path = image_path; }
}