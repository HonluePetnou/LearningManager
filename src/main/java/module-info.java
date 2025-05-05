module com.example.librarymanager {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
//    requires org.xerial.sqlitejdbc;
//    requires de.jensd.fx.glyphs.Fontawesome;
//    requires javafx.graphics;



    opens com.example.librarymanager to javafx.fxml;
    opens com.example.librarymanager.Controllers to javafx.fxml;
    exports com.example.librarymanager;
    exports com.example.librarymanager.Controllers;
    exports com.example.librarymanager.Controllers.Admin;
    exports com.example.librarymanager.Controllers.Client;
    exports com.example.librarymanager.Models;
    exports com.example.librarymanager.Views;
}