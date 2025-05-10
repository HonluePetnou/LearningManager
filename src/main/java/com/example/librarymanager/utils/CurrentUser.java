package com.example.librarymanager.utils;

import com.example.librarymanager.Models.User;

/**
 * Cette classe est utilisée pour stocker et gérer les données de l'utilisateur
 * actuellement connecté à l'application. Elle permet de définir et de récupérer
 * l'utilisateur actif via des méthodes statiques.
 */
public class CurrentUser {
    private static User user ;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
    }
}
