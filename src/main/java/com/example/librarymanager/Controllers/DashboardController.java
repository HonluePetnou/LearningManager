package com.example.librarymanager.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.example.librarymanager.Database.BooksTable;
import com.example.librarymanager.Database.LoanTable;
import com.example.librarymanager.Database.UserTable;
import com.example.librarymanager.Models.Model;
import com.example.librarymanager.Models.User;
import com.example.librarymanager.utils.Alertmessage;
import com.example.librarymanager.utils.CurrentUser;

/**
 * JavaFX controller for the dashboard analytics view of the application.
 *
 * This controller manages the display of key statistics and charts on the
 * dashboard,
 * including total books, borrowed books, active users, user age distribution,
 * and
 * borrow trends by month.
 *
 * Main features:
 * - Displays total books, borrowed books, and active users.
 * - Shows a pie chart of users by age category (Children, Teenagers, Adults,
 * Aged).
 * - Shows a line chart of borrows per month.
 * - Displays the current user's name and the current date/time.
 * - Provides navigation buttons to see all users or more books.
 *
 * Dependencies:
 * - BooksTable: for retrieving book statistics.
 * - LoanTable: for retrieving loan statistics.
 * - UserTable: for retrieving user statistics.
 * - Model: for navigation and view management.
 * - CurrentUser: for accessing the current user's information.
 * - Alertmessage: for displaying error messages.
 *
 * FXML requirements:
 * - Labels: totalBooksLabel, borrowedBooksLabel, activeUsersLabel, username,
 * localdate
 * - VBox: membersContainer, table
 * - PieChart: pieChart
 * - LineChart: lineChart
 * - Buttons: seeAllButton, moreButton
 */
public class DashboardController implements Initializable {
    private final NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

    public Label totalBooksLabel;
    public Label borrowedBooksLabel;
    public Label activeUsersLabel;
    public VBox membersContainer;
    public VBox table;
    public PieChart pieChart;
    @SuppressWarnings("rawtypes")
    public LineChart lineChart;

    @FXML
    private Label username;
    @FXML
    private Label localdate;
    @FXML
    private Button seeAllButton;
    @FXML
    private Button moreButton;

    private BooksTable booksTable = new BooksTable();
    private LoanTable loanTable = new LoanTable();
    private UserTable userTable = new UserTable();

    /**
     * Initializes the dashboard, loads statistics, and sets up charts and
     * navigation.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize navigation buttons
        seeAllButton.setOnAction(_ -> onSeeAllUsers());
        moreButton.setOnAction(_ -> onMoreBooks());

        User user = CurrentUser.getUser();
        String name;
        if (user == null || user.getFullName() == null) {
            name = "NOT CONNECTED";
        } else {
            name = user.getFullName();
        }
        username.setText("HI " + name);
        localdate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a")));

        try {
            totalBooksLabel.setText(numberFormatter.format(booksTable.Count()));
            borrowedBooksLabel.setText(numberFormatter.format(loanTable.Count()));
            activeUsersLabel.setText(numberFormatter.format(userTable.Count()));
        } catch (SQLException e) {
            System.err.println("Error calculating totals: " + e);
            Alertmessage.showAlert(AlertType.ERROR, "Error", "Internal error");
        }

        int[] age = getUserByAge();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Aged", age[3]),
                new PieChart.Data("Adult", age[2]),
                new PieChart.Data("Teenagers", age[1]),
                new PieChart.Data("Children", age[0]));

        pieChart.setData(pieChartData);
        pieChart.setTitle("Users by Age");

        XYChart.Series<Number, Number> borrowsSeries = new XYChart.Series<>();
        borrowsSeries.setName("Borrows");

        int[] borrowsByMonth = getBorrowbymonth();

        borrowsSeries.getData().add(new XYChart.Data("jan", borrowsByMonth[0]));
        borrowsSeries.getData().add(new XYChart.Data("fev", borrowsByMonth[1]));
        borrowsSeries.getData().add(new XYChart.Data("mar", borrowsByMonth[2]));
        borrowsSeries.getData().add(new XYChart.Data("apr", borrowsByMonth[3]));
        borrowsSeries.getData().add(new XYChart.Data("may", borrowsByMonth[4]));
        borrowsSeries.getData().add(new XYChart.Data("jun", borrowsByMonth[5]));
        borrowsSeries.getData().add(new XYChart.Data("jul", borrowsByMonth[6]));
        borrowsSeries.getData().add(new XYChart.Data("aug", borrowsByMonth[7]));
        borrowsSeries.getData().add(new XYChart.Data("sep", borrowsByMonth[8]));
        borrowsSeries.getData().add(new XYChart.Data("oct", borrowsByMonth[9]));
        borrowsSeries.getData().add(new XYChart.Data("nov", borrowsByMonth[10]));
        borrowsSeries.getData().add(new XYChart.Data("dec", borrowsByMonth[11]));

        lineChart.getData().add(borrowsSeries);
    }

    /**
     * Returns an array with the number of users in each age category.
     * 
     * @return int[] with counts for Children, Teenagers, Adults, Aged
     */
    private static int[] getUserByAge() {
        int[] ageByCategory = new int[4];
        try {
            UserTable userTable = new UserTable();
            for (var user : userTable.listAll()) {
                int age = LocalDate.now().getYear() - user.getBirthdate().getYear();
                if (age < 18) {
                    ageByCategory[0]++;
                } else if (age >= 18 && age < 30) {
                    ageByCategory[1]++;
                } else if (age >= 30 && age < 50) {
                    ageByCategory[2]++;
                } else {
                    ageByCategory[3]++;
                }
            }
        } catch (Exception e) {
            Alertmessage.showAlert(AlertType.ERROR, "Error", "Internal error");
            System.err.println("Failed to list users: " + e);
        }
        return ageByCategory;
    }

    /**
     * Navigates to the Users view when the "See All" button is clicked.
     */
    private void onSeeAllUsers() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Users");
    }

    /**
     * Navigates to the Books view when the "More" button is clicked.
     */
    private void onMoreBooks() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Books");
    }

    /**
     * Returns an array with the number of borrows for each month.
     * 
     * @return int[] with borrow counts for each month (Jan-Dec)
     */
    private static int[] getBorrowbymonth() {
        int[] borrows = new int[12];
        try {
            LoanTable loanTable = new LoanTable();
            for (var loan : loanTable.listAll()) {
                Month month = loan.getBorrowedAt().getMonth();
                switch (month) {
                    case JANUARY:
                        borrows[0]++;
                        break;
                    case FEBRUARY:
                        borrows[1]++;
                        break;
                    case MARCH:
                        borrows[2]++;
                        break;
                    case APRIL:
                        borrows[3]++;
                        break;
                    case MAY:
                        borrows[4]++;
                        break;
                    case JUNE:
                        borrows[5]++;
                        break;
                    case JULY:
                        borrows[6]++;
                        break;
                    case AUGUST:
                        borrows[7]++;
                        break;
                    case SEPTEMBER:
                        borrows[8]++;
                        break;
                    case OCTOBER:
                        borrows[9]++;
                        break;
                    case NOVEMBER:
                        borrows[10]++;
                        break;
                    case DECEMBER:
                        borrows[11]++;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            Alertmessage.showAlert(AlertType.ERROR, "Error", "Internal error");
            System.err.println("Failed to list loans: " + e);
        }
        return borrows;
    }
}
