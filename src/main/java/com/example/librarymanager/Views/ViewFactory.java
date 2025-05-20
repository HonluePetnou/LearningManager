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

/**
 * Factory class for managing and displaying application views (windows/scenes).
 *
 * This class provides methods to load, cache, and display different JavaFX
 * views
 * such as Dashboard, Books, Users, Borrows, and Help. It also manages the
 * currently
 * selected sidebar menu item and provides utility methods for opening and
 * closing
 * application windows.
 *
 * Main features:
 * - Loads and caches FXML views for efficient switching between scenes.
 * - Provides methods to show main application windows (login, registration,
 * dashboard, books, confirm borrow).
 * - Handles errors gracefully by displaying error dialogs if FXML resources are
 * missing or fail to load.
 * - Maintains a StringProperty for the currently selected sidebar menu item.
 * - Ensures consistent view wrapping (e.g., wrapping AnchorPane or VBox in
 * BorderPane).
 *
 * Usage:
 * - Call getDashboardView(), getBooksView(), getUsersView(), getBorrowsView(),
 * or getHelpView() to retrieve views.
 * - Use showLoginWindow(), showRegistrationWindow(), showMainWindow(),
 * showBooksWindow(), or showConfirmBorrowWindow() to open windows.
 * - Use closeStage(Stage) to close a window.
 * - Use getSidebarSelectedMenuItem() to observe or change the selected sidebar
 * menu item.
 *
 * Dependencies:
 * - JavaFX: FXMLLoader, Scene, Stage, Alert, and layout classes.
 * - FXML files: Located in the /Fxml/ directory.
 */
public class ViewFactory {
    private final StringProperty sidebarSelectedMenuItem;
    private ScrollPane dashboardView;
    private BorderPane usersView;
    private BorderPane booksView;
    private AnchorPane borrowsView;
    private AnchorPane settingsView;
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
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            } catch (Exception e) {
                // throw new RuntimeE xception(e);
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

    public BorderPane getSettingsView() {
        if (settingsView == null) {
            try {
                settingsView = new FXMLLoader(getClass().getResource("/Fxml/Settings.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
                settingsView = new AnchorPane();
                settingsView.getChildren().add(new Label("Error loading Settings view: " + e.getMessage()));
            }
        }
        BorderPane wrapper = new BorderPane();
        wrapper.setCenter(settingsView);
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

    public void showMainWindow() {
        try {
            URL resourceUrl = getClass().getResource("/Fxml/Main.fxml");
            if (resourceUrl == null) {
                System.err.println("Error: Could not find Main.fxml resource");
                throw new IOException("Main.fxml resource not found");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            createStage(fxmlLoader);
        } catch (Exception e) {
            System.err.println("Error showing main window: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("Application Error", "Failed to load main window", e.getMessage());
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
