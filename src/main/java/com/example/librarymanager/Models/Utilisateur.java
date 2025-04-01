package com.example.librarymanager.Models;

import java.util.ArrayList;

public abstract class Utilisateur {
   protected String nom ;
  protected  String prenom ;
  protected  String email ;
  protected  String motDePasse ;
  protected int id ;

  public Utilisateur (final String nom ,final String prenom ,final String email,final String motDepasse , final int id){
    this.nom = nom;
    this.prenom = prenom ;
    this.email = email ;
    this.motDePasse = motDepasse ;
    this.id =id ;
  }

  public void lire(){
   
  }

  public ArrayList<Livre> rechercher(String motachercher){
    ArrayList<Livre> resultat = new ArrayList<>();
   return resultat ;
  }

  public String getNom() {
    return nom;
  }
  public void setNom(String nom) {
    this.nom = nom;
  }
  public String getPrenom() {
    return prenom;
  }
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getMotDePasse() {
    return motDePasse;
  }
  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
}
