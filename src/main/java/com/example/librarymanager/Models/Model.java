package com.example.librarymanager.Models;

import com.example.librarymanager.Views.ViewFactory;

public class Model {

    private ViewFactory viewFactory;
    private static Model model;

    // Constructeur privé pour le pattern Singleton
    private Model() {
        this.viewFactory = new ViewFactory();
    }

    // Méthode pour récupérer l'instance unique de Model
    public static synchronized Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    // Getter pour récupérer l'instance de ViewFactory
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
