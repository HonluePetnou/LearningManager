/**
 * Java module descriptor for the Library Manager application.
 *
 * This file defines the module dependencies, opened packages, and exported
 * packages
 * for the JavaFX-based library management system.
 *
 * Main features:
 * - Declares required modules for JavaFX, SQL, and desktop integration.
 * - Opens packages to JavaFX FXML for reflection and controller access.
 * - Exports core packages for use by other modules or libraries.
 *
 * Usage:
 * - Ensure all required modules are listed for successful compilation and
 * runtime.
 * - Uncomment additional requires statements if using external libraries (e.g.,
 * SQLite JDBC, FontAwesome).
 *
 * Dependencies:
 * - javafx.controls, javafx.fxml, javafx.graphics: For JavaFX UI components.
 * - java.sql: For database connectivity.
 * - java.desktop: For desktop integration.
 *
 * Opened Packages:
 * - com.example.librarymanager, com.example.librarymanager.Controllers: For
 * FXML and controller access.
 *
 * Exported Packages:
 * - com.example.librarymanager, com.example.librarymanager.Controllers,
 * com.example.librarymanager.Models, com.example.librarymanager.Views
 */
module com.example.librarymanager {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;

    opens com.example.librarymanager to javafx.fxml;
    opens com.example.librarymanager.Controllers to javafx.fxml;

    exports com.example.librarymanager;
    exports com.example.librarymanager.Controllers;
    exports com.example.librarymanager.Models;
    exports com.example.librarymanager.Views;
}