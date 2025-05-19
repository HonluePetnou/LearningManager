# Library Manager

A modern JavaFX application for managing a library, supporting book, user, and loan management with a graphical interface and SQLite database.

---

## Features

- **User Authentication**: Login and registration with hashed passwords and role-based access (Admin/Member).
- **Book Management**: Add, edit, delete, and search books. View book details and manage book categories.
- **User Management**: Add, edit, delete, and search users. View user details and manage user roles.
- **Loan Management**: Borrow and return books, track loan status, and view loan history.
- **Dashboard**: Visual statistics, charts, and highlights for books, users, and borrows.
- **Help & Contact**: Developer contact cards and help section.
- **Responsive UI**: Built with JavaFX, FXML, and custom CSS for a modern look.

---

## Requirements

- Java 24
- JavaFX SDK
- SQLite JDBC driver
- Jens Deters' JavaFX Glyphs FontAwesome
- JavaFX graphics module

---

## Project Structure

- `src/main/java` - Java source code
  - `com.example.librarymanager` - Main application package
    - `Database` - database manipulating classes
    - `Controllers` - MVC controller classes
    - `Models` - Data model classes
    - `Views` - View classes
- `src/main/resources` - Application resources
  - `fxml` - FXML layout files
- `target` - Build output directory

## Setup

1. Ensure you have Java 8 or later installed
2. Install the required dependencies
3. Build the project using your IDE or build tool
4. Run the `com.example.librarymanager.App` class

## Contributing

Please submit bug reports and feature requests through the issue tracker. Pull requests are welcome.

## License

none yet
