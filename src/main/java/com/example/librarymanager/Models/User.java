package com.example.librarymanager.Models;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class User {

  private static int nextId = 1; // Static counter for auto-incrementing IDs

  private int user_id;  
  private String email; 
  private String password; 
  private String Role ;
  private String fullName;
  private int phone;
  private LocalDate birthdate;
  private String gender;
  private String address;

  public User() { }

  public User(String firstName, String lastName, String email, int phone,
              LocalDate birthdate, String gender, String address) {
    this.user_id = nextId++;
    this.fullName = firstName + " " + lastName;
    this.email = email;
    this.phone = phone;
    this.birthdate = birthdate;
    this.gender = gender;
    this.address = address;
  }


   // Getters & Setters
  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public String getRole() {
    return Role;
  }

  public void setRole(String role) {
    Role = role;
  }

  public String getFullName() {
     return fullName; }
  public void setFullName(String fullName) { 
    this.fullName = fullName; }
  public int getPhone() { 
    return phone; }
  public void setPhone(int phone) { 
    this.phone = phone; }
  public LocalDate getBirthdate() { 
    return birthdate; }
  public void setBirthdate(LocalDate birthdate) { 
    this.birthdate = birthdate; }
  public String getGender() { 
    return gender; }
  public void setGender(String gender) { 
    this.gender = gender; }
  public String getAddress() { 
    return address; }
  public void setAddress(String address) { 
    this.address = address; }

  // Example data
  public static ObservableList<User> getInitialList() {
    nextId = 1; // Reset counter when creating new list
    User u1 = new User("Chris", "James", "chris@mail.com", 699123456,
            LocalDate.of(1995, 6, 12), "Male", "Douala");
    User u2 = new User("Anna", "Smith", "anna@mail.com", 678987654,
            LocalDate.of(1998, 3, 22), "Female", "Yaoundé");
    User u3 = new User("Jake", "Brown", "jake@mail.com", 690456789,
            LocalDate.of(1992, 11, 7), "Male", "Bafoussam");
    return FXCollections.observableArrayList(u1, u2, u3);
  }

  // TableColumn definitions
  public static TableColumn<User, Integer> getIdCol() {
    TableColumn<User, Integer> col = new TableColumn<>("ID");
    col.setCellValueFactory(new PropertyValueFactory<>("id"));
    return col;
  }

  public static TableColumn<User, String> getFullNameCol() {
    TableColumn<User, String> col = new TableColumn<>("Full Name");
    col.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    return col;
  }

  public static TableColumn<User, String> getEmailCol(){
    TableColumn<User, String> col = new TableColumn<>("Email");
    col.setCellValueFactory(new PropertyValueFactory<>("email"));
    return col;
  }

  public static TableColumn<User, Integer> getPhoneCol(){
    TableColumn<User, Integer> col = new TableColumn<>("Phone");
    col.setCellValueFactory(new PropertyValueFactory<>("phone"));
    return col;
  }

  public static TableColumn<User, LocalDate> getBirthdateCol(){
    TableColumn<User, LocalDate> col = new TableColumn<>("Birthdate");
    col.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    return col;
  }

  public static TableColumn<User, String> getGenderCol(){
    TableColumn<User, String> col = new TableColumn<>("Gender");
    col.setCellValueFactory(new PropertyValueFactory<>("gender"));
    return col;
  }

  public static TableColumn<User, String> getAddressCol(){
    TableColumn<User, String> col = new TableColumn<>("Address");
    col.setCellValueFactory(new PropertyValueFactory<>("address"));
    return col;
  }
}
