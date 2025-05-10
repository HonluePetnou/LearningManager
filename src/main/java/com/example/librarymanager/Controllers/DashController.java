package com.example.librarymanager.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.example.librarymanager.Database.BooksTable;
import com.example.librarymanager.Database.LoanTable;
import com.example.librarymanager.Database.UserTable;
import com.example.librarymanager.Models.User;
import com.example.librarymanager.utils.CurrentUser;

public class DashController implements Initializable {
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

    private BooksTable booksTable = new BooksTable();
    private LoanTable loanTable = new LoanTable();
    private UserTable userTable = new UserTable();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        User user = CurrentUser.getUser();
        String name;
        if (user == null || user.getFullName() == null) {
            name = "PAS CONNECTE";
        } else {
            name = user.getFullName();
        }
        username.setText("HI" + " " + name);
        localdate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a")).toString());
       
        try {
            totalBooksLabel.setText(numberFormatter.format(booksTable.Count()));
            borrowedBooksLabel.setText(numberFormatter.format(loanTable.Count()));
            activeUsersLabel.setText(numberFormatter.format(userTable.Count()));
        } catch (SQLException e) {
            System.err.println("erreur du calcul des totaux:" + e);
        }

       
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Aged", 20),
                new PieChart.Data("Adult", 20),
                new PieChart.Data("Teenagers", 40),
                new PieChart.Data("Children", 20));

        pieChart.setData(pieChartData);
        pieChart.setTitle("Users by Age");

        XYChart.Series<Number, Number> borrowsSeries = new XYChart.Series<>();
        borrowsSeries.setName("Borrows");

        borrowsSeries.getData().add(new XYChart.Data("jan", 15));
        borrowsSeries.getData().add(new XYChart.Data("fev", 18));
        borrowsSeries.getData().add(new XYChart.Data("mar", 25));
        borrowsSeries.getData().add(new XYChart.Data("apr", 15));
        borrowsSeries.getData().add(new XYChart.Data("may", 18));
        borrowsSeries.getData().add(new XYChart.Data("jun", 29));
        borrowsSeries.getData().add(new XYChart.Data("jul", 50));
        borrowsSeries.getData().add(new XYChart.Data("aug", 92));
        borrowsSeries.getData().add(new XYChart.Data("sep", 53));
        borrowsSeries.getData().add(new XYChart.Data("oct", 37));
        borrowsSeries.getData().add(new XYChart.Data("nov", 2));
        borrowsSeries.getData().add(new XYChart.Data("dec", 100));

        lineChart.getData().add(borrowsSeries);

    }
}
