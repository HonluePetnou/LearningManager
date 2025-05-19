package com.example.librarymanager.Models;

import java.time.LocalDate;

/**
 * Data model class representing a user in the library system.
 *
 * This class encapsulates all properties of a user, including their ID, email,
 * password, role, full name, phone number, birthdate, gender, and address.
 *
 * Main features:
 * - Provides constructors for creating user instances.
 * - Getters and setters for all user properties.
 *
 * Fields:
 * - int user_id: Unique identifier for the user.
 * - String email: User's email address.
 * - String password: User's password (hashed in the database).
 * - String Role: User's role (e.g., ADMIN, MEMBER).
 * - String fullName: User's full name.
 * - int phone: User's phone number.
 * - LocalDate birthdate: User's birthdate.
 * - String gender: User's gender.
 * - String address: User's address.
 */
public class User {

  private int user_id;
  private String email;
  private String password;
  private String Role;
  private String fullName;
  private int phone;
  private LocalDate birthdate;
  private String gender;
  private String address;

  public User() {
  }

  /**
   * Constructs a new User with the specified details.
   * 
   * @param firstName User's first name
   * @param lastName  User's last name
   * @param email     User's email address
   * @param phone     User's phone number
   * @param birthdate User's birthdate
   * @param gender    User's gender
   * @param address   User's address
   */
  public User(String firstName, String lastName, String email, int phone,
      LocalDate birthdate, String gender, String address) {
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
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public int getPhone() {
    return phone;
  }

  public void setPhone(int phone) {
    this.phone = phone;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
