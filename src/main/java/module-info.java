module com.example.librarymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
//    requires org.xerial.sqlitejdbc;
//    requires de.jensd.fx.glyphs.Fontawesome;
//    requires javafx.graphics;



    opens com.example.librarymanager to javafx.fxml;
    exports com.example.librarymanager;
    exports com.example.librarymanager.Controllers;
    exports com.example.librarymanager.Controllers.Admin;
    exports com.example.librarymanager.Controllers.Client;
    exports com.example.librarymanager.Models;
    exports com.example.librarymanager.Views;
}