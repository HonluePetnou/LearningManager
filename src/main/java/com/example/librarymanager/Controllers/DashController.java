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
    @FXML
    private Button seeAllButton;
    @FXML
    private Button moreButton;

    private BooksTable booksTable = new BooksTable();
    private LoanTable loanTable = new LoanTable();
    private UserTable userTable = new UserTable();
    

    @SuppressWarnings({ "rawtypes", "unchecked","unused" })
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize navigation buttons
        seeAllButton.setOnAction(_ -> onSeeAllUsers());
        moreButton.setOnAction(_ -> onMoreBooks());

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
            Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
        }

       int [] age = getUserByAge() ;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Aged", age[3]),
                new PieChart.Data("Adult", age[2]),
                new PieChart.Data("Teenagers", age[1]),
                new PieChart.Data("Children", age[0]));
        
        //uncomment to use static data
        // ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        //         new PieChart.Data("Aged", 20),
        //         new PieChart.Data("Adult", 20),
        //         new PieChart.Data("Teenagers", 40),
        //         new PieChart.Data("Children", 20));

        pieChart.setData(pieChartData);
        pieChart.setTitle("Users by Age");

        XYChart.Series<Number, Number> borrowsSeries = new XYChart.Series<>();
        borrowsSeries.setName("Borrows");
   
        int [] borrowsByMonth = getBorrowbymonth();

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

        // Uncomment the following lines to use the static data

        // borrowsSeries.getData().add(new XYChart.Data("jan", 15));
        // borrowsSeries.getData().add(new XYChart.Data("fev", 18));
        // borrowsSeries.getData().add(new XYChart.Data("mar", 25));
        // borrowsSeries.getData().add(new XYChart.Data("apr", 15));
        // borrowsSeries.getData().add(new XYChart.Data("may", 18));
        // borrowsSeries.getData().add(new XYChart.Data("jun", 29));
        // borrowsSeries.getData().add(new XYChart.Data("jul", 50));
        // borrowsSeries.getData().add(new XYChart.Data("aug", 92));
        // borrowsSeries.getData().add(new XYChart.Data("sep", 53));
        // borrowsSeries.getData().add(new XYChart.Data("oct", 37));
        // borrowsSeries.getData().add(new XYChart.Data("nov", 2));
        // borrowsSeries.getData().add(new XYChart.Data("dec", 100));

        lineChart.getData().add(borrowsSeries);

    }

    private static int[] getUserByAge(){
       int [] ageByCategory = new int[4];
        try {
            UserTable userTable = new UserTable();
            for (var user : userTable.listAll()) {
                int age = 0 ;
                age =  LocalDate.now().getYear() - user.getBirthdate().getYear() ;
                if (age < 18) {
                   ageByCategory[0]++ ;
                } else if (age >= 18 && age < 30) {
                   ageByCategory[1]++ ;
                } else if (age >= 30 && age < 50) {
                   ageByCategory[2]++ ;
                } else {
                   ageByCategory[3]++ ;
                }
            }
        } catch (Exception e) {
            Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
            System.err.println("fail to list loans:"+e);
        }
        return ageByCategory ;
    }

    private void onSeeAllUsers() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Users");
    }

    private void onMoreBooks() {
        Model.getModel().getViewFactory().getSidebarSelectedMenuItem().set("Books");
    }

    private static int[] getBorrowbymonth(){
        int [] borrows = new int[12];
     try {
        LoanTable loanTable = new LoanTable();
        for (var loan : loanTable.listAll() ) {
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
        Alertmessage.showAlert(AlertType.ERROR, "Error", "internal error");
        System.err.println("fail to list loans:"+e);
     }
      return borrows ;
    }

}
