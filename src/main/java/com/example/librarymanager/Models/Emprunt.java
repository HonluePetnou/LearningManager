package com.example.librarymanager.Models;

import java.util.Date;

public class Emprunt {
   private Date dateEmprunt ;
   private Date dateRetour ;
   private String statut ;
   
public Emprunt(final Date dateEmprunt , final Date dateRetour , final String statut){
   this.dateEmprunt =dateEmprunt ;
   this.dateRetour = dateRetour ;
   this.statut = statut ;
   }

  public void emprunterLivre(){
 
  }
  public void retournerlivre(){
  }
  
public Date getDateEmprunt() {
    return dateEmprunt;
}
public void setDateEmprunt(Date dateEmprunt) {
    this.dateEmprunt = dateEmprunt;
}
public Date getDateRetour() {
    return dateRetour;
}
public void setDateRetour(Date dateRetour) {
    this.dateRetour = dateRetour;
}
public String getStatut() {
    return statut;
}
public void setStatut(String statut) {
    this.statut = statut;
}
}
