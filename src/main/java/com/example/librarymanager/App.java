package com.example.librarymanager;

import com.example.librarymanager.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        Model.getModel().getViewFactory();
        Model.getModel().getViewFactory().showLoginWindow();
    }
}
     