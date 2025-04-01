package com.example.librarymanager.Models;

public class Lecteur extends Utilisateur {
  public Lecteur(final String nom , final String prenom ,final String email , final String motDePasse , final int id){
    super(nom, prenom, email, motDePasse, id);
  }
  public boolean connexion(){
  return true ;
  }
  public void inscription(){
  
  }
}
