package com.example.librarymanager.Models;

import com.example.librarymanager.Views.ViewFactory;

/**
 * Singleton model class for managing application-wide state and access to the
 * ViewFactory.
 *
 * This class implements the Singleton pattern to ensure a single instance is
 * used
 * throughout the application. It provides access to the ViewFactory, which is
 * responsible
 * for managing and switching between different views (windows/scenes).
 *
 * Main features:
 * - Ensures only one instance of Model exists (Singleton pattern).
 * - Provides global access to the ViewFactory instance.
 *
 * Usage:
 * - Call Model.getModel() to obtain the singleton instance.
 * - Use getViewFactory() to access the application's ViewFactory.
 *
 * Dependencies:
 * - ViewFactory: for managing and displaying application views.
 */
public class Model {

    private ViewFactory viewFactory;
    private static Model model;

    // Private constructor for Singleton pattern
    private Model() {
        this.viewFactory = new ViewFactory();
    }

    /**
     * Returns the singleton instance of Model.
     * 
     * @return the Model instance
     */
    public static synchronized Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    /**
     * Returns the ViewFactory instance.
     * 
     * @return the ViewFactory
     */
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
