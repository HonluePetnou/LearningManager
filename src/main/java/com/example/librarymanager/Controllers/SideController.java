package com.example.librarymanager.Controllers;

import com.example.librarymanager.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class SideController implements Initializable {
    public Button dashboardBtn;
    public Button bookBtn;
    public Button userBtn;
    public Button borrowBtn;
    public Button settingsBtn;
    public Button helpBtn;
    public Button logoutBtn;
    public Button copyrigthBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
    }
    private void addListener(){
        dashboardBtn.setOnAction(event -> onDashboard());
        bookBtn.setOnAction(event -> onBooking());
        userBtn.setOnAction(event -> onUsers());
        borrowBtn.setOnAction(event -> onBorrows());
        settingsBtn.setOnAction(event -> onSettings());
        helpBtn.setOnAction(event -> onHelp());
        logoutBtn.setOnAction(event -> onLogout());
        copyrigthBtn.setOnAction(event -> onCopyright());

    }
    private void onDashboard(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Dashboard");
    }
    private void onUsers(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Users");
    }
    private void onBooking(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Books");
    }
    private void onBorrows(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Borrows");
    }
    private void onSettings(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Settings");
    }
    private void onHelp(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Help");
    }
    private void onLogout(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Logout");
    }
    private void onCopyright(){
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Copyright");
    }
}
