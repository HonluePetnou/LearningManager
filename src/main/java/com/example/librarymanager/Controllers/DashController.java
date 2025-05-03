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
import java.util.ResourceBundle;

public class DashController implements Initializable {
    public Label totalBooksLabel;
    public Label borrowedBooksLabel;
    public Label activeUsersLabel;
    public VBox membersContainer;
    public VBox table;
    public PieChart pieChart;
    public LineChart lineChart;

    @FXML
//    private LineChart<String, Number> lineChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(
                new PieChart.Data("Aged",20),
                new PieChart.Data("Adult",20),
                new PieChart.Data("Teenagers",40),
                new PieChart.Data("Children",20));

                pieChart.setData(pieChartData);
                pieChart.setTitle("Users by Age");

//                 lineChart
        XYChart.Series<Number, Number> borrowsSeries = new XYChart.Series<>();
        borrowsSeries.setName("Borrows");

        borrowsSeries.getData().add(new XYChart.Data("jan",15));
        borrowsSeries.getData().add(new XYChart.Data("fev",18));
        borrowsSeries.getData().add(new XYChart.Data("mar",25));
        borrowsSeries.getData().add(new XYChart.Data("apr",15));
        borrowsSeries.getData().add(new XYChart.Data("may",18));
        borrowsSeries.getData().add(new XYChart.Data("jun",29));
        borrowsSeries.getData().add(new XYChart.Data("jul",50));
        borrowsSeries.getData().add(new XYChart.Data("aug",92));
        borrowsSeries.getData().add(new XYChart.Data("sep",53));
        borrowsSeries.getData().add(new XYChart.Data("oct",37));
        borrowsSeries.getData().add(new XYChart.Data("nov",2));
        borrowsSeries.getData().add(new XYChart.Data("dec",100));

        lineChart.getData().add(borrowsSeries);

    }
}
