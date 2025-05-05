package com.example.librarymanager.Views;

// import com.example.librarymanager.Controllers.DashController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// import java.io.IOException;

public class ViewFactory {
    private final StringProperty sidebarSelectedMenuItem;
    private ScrollPane dashboardView;
    private BorderPane usersView;

    public ViewFactory() {
        this.sidebarSelectedMenuItem = new SimpleStringProperty();
//        sidebarSelectedMenuItem = new SimpleStringProperty();
    }

    public StringProperty getSidebarSelectedMenuItem() {
        return sidebarSelectedMenuItem;
    }

    public ScrollPane  getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Dash.fxml")).load();
            } catch (Exception e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
        }
        return dashboardView;

    }
    public BorderPane getUsersView() {  // Changed return type to BorderPane
        if (usersView == null) {
            try {
                usersView = new FXMLLoader(getClass().getResource("/Fxml/Users.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usersView;
    }
    public void showLoginWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(fxmlLoader);
    }
    public void showDashboardWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml"));

//        primaryStage.setFullScreen(true);
//        DashController dashController = new DashController();
//        fxmlLoader.setController(dashController);
        createStage(fxmlLoader);
    }

    private void createStage(FXMLLoader fxmlLoader) {
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BOOKER");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
