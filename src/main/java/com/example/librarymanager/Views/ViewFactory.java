package com.example.librarymanager.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewFactory {
    private final StringProperty sidebarSelectedMenuItem;
    private ScrollPane dashboardView;
    private BorderPane usersView;
    private BorderPane booksView;
    private AnchorPane borrowsView;
    private VBox helpView;

    public ViewFactory() {
        this.sidebarSelectedMenuItem = new SimpleStringProperty();
        // sidebarSelectedMenuItem = new SimpleStringProperty();
    }

    public StringProperty getSidebarSelectedMenuItem() {
        return sidebarSelectedMenuItem;
    }

    public ScrollPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Dash.fxml")).load();
            } catch (Exception e) {
                // throw new RuntimeException(e);
                e.printStackTrace();
            }
        }
        return dashboardView;

    }

    public BorderPane getBooksView() {
        if (booksView == null) {
            try {
                URL resourceUrl = getClass().getResource("/Fxml/Books.fxml");
                if (resourceUrl == null) {
                    System.err.println("Error: Could not find Books.fxml resource");
                    throw new IOException("Books.fxml resource not found");
                }
                FXMLLoader loader = new FXMLLoader(resourceUrl);
                booksView = loader.load();
            } catch (Exception e) {
                System.err.println("Error loading Books view: " + e.getMessage());
                e.printStackTrace();
                // Create a fallback view to avoid returning null
                booksView = new BorderPane();
                booksView.setCenter(new Label("Error loading Books view: " + e.getMessage()));
            }
        }
        return booksView;
    }

    public BorderPane getUsersView() { // Changed return type to BorderPane
        if (usersView == null) {
            try {
                usersView = new FXMLLoader(getClass().getResource("/Fxml/Users.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usersView;
    }

    public BorderPane getBorrowsView() {
        if (borrowsView == null) {
            try {
                URL resourceUrl = getClass().getResource("/Fxml/Borrows.fxml");
                if (resourceUrl == null) {
                    System.err.println("Error: Could not find Borrows.fxml resource");
                    throw new IOException("Borrows.fxml resource not found");
                }
                FXMLLoader loader = new FXMLLoader(resourceUrl);
                borrowsView = loader.load();
            } catch (Exception e) {
                System.err.println("Error loading Borrows view: " + e.getMessage());
                e.printStackTrace();
                // Create a fallback view to avoid returning null
                borrowsView = new AnchorPane();
                borrowsView.getChildren().add(new Label("Error loading Borrows view: " + e.getMessage()));
            }
        }
        // Wrap AnchorPane in BorderPane to maintain consistency with other views
        BorderPane wrapper = new BorderPane();
        wrapper.setCenter(borrowsView);
        return wrapper;
    }

    public BorderPane getHelpView() {
        if (helpView == null) {
            try {
                URL resourceUrl = getClass().getResource("/Fxml/Help.fxml");
                if (resourceUrl == null) {
                    System.err.println("Error: Could not find Help.fxml resource");
                    throw new IOException("Help.fxml resource not found");
                }
                FXMLLoader loader = new FXMLLoader(resourceUrl);
                helpView = loader.load();
            } catch (Exception e) {
                System.err.println("Error loading Help view: " + e.getMessage());
                e.printStackTrace();
                // Create a fallback view to avoid returning null
                helpView = new VBox();
                helpView.getChildren().add(new Label("Error loading Help view: " + e.getMessage()));
            }
        }
        // Wrap VBox in BorderPane to maintain consistency with other views
        BorderPane wrapper = new BorderPane();
        wrapper.setCenter(helpView);
        return wrapper;
    }

    public void showLoginWindow() {
        try {
            URL resourceUrl = getClass().getResource("/Fxml/Login.fxml");
            if (resourceUrl == null) {
                System.err.println("Error: Could not find Login.fxml resource");
                throw new IOException("Login.fxml resource not found");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            createStage(fxmlLoader);
        } catch (Exception e) {
            System.err.println("Error showing login window: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("Login Error", "Failed to load login window", e.getMessage());
        }
    }

    public void showRegistrationWindow() {
        try {
            URL resourceUrl = getClass().getResource("/Fxml/Registration.fxml");
            if (resourceUrl == null) {
                System.err.println("Error: Could not find Registration.fxml resource");
                throw new IOException("Registration.fxml resource not found");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            createStage(fxmlLoader);
        } catch (Exception e) {
            System.err.println("Error showing Registration window: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("Registration Error", "Failed to load Registration window", e.getMessage());
        }
    }

    public void showDashboardWindow() {
        try {
            URL resourceUrl = getClass().getResource("/Fxml/Dashboard.fxml");
            if (resourceUrl == null) {
                System.err.println("Error: Could not find Dashboard.fxml resource");
                throw new IOException("Dashboard.fxml resource not found");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            createStage(fxmlLoader);
        } catch (Exception e) {
            System.err.println("Error showing dashboard window: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("Dashboard Error", "Failed to load dashboard window", e.getMessage());
        }
    }

    public void showBooksWindow() {
        try {
            URL resourceUrl = getClass().getResource("/Fxml/Books.fxml");
            if (resourceUrl == null) {
                System.err.println("Error: Could not find Books.fxml resource");
                throw new IOException("Books.fxml resource not found");
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            createStage(loader);
        } catch (Exception e) {
            System.err.println("Error showing books window: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("Books Error", "Failed to load books window", e.getMessage());
        }
    }

    public void showConfirmBorrowWindow() {
        try {
            URL resourceUrl = getClass().getResource("/Fxml/ConfirmBorrow.fxml");
            if (resourceUrl == null) {
                System.err.println("Error: Could not find ConfirmBorrow.fxml resource");
                throw new IOException("ConfirmBorrow.fxml resource not found");
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);

            // Créer la scène et obtenir le controller avant de l'afficher
            Scene scene = new Scene(loader.load());
           
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BOOKER");
            stage.show();

        } catch (Exception e) {
            System.err.println("Error showing confirm borrow window: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("ConfirmBorrow Error", "Failed to load ConfirmBorrow window", e.getMessage());
        }
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

    /**
     * Shows an error dialog with the specified information
     * 
     * @param title   The title of the error dialog
     * @param header  The header text of the error dialog
     * @param content The content text of the error dialog
     */
    private void showErrorDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
