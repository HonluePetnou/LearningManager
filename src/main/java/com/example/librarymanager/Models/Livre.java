package com.example.librarymanager.Models;

public class Livre {
  private String titre ;
  private  String auteur ;
  private String isbn ;
  private  int annee ;
  private String categorie ;
  public Livre(  final String titre ,final  String auteur ,final String isbn ,final int annee ,final String categorie )
  {
     this.titre = titre ;
     this.auteur =auteur ;
     this.isbn = isbn ;
     this.categorie = categorie ;
     this.annee = annee ;
    }

 public boolean disponibilite(){
    
  return true ;
 }


public String getTitre() {
    return titre;
}
public void setTitre(String titre) {
    this.titre = titre;
}
public String getAuteur() {
    return auteur;
}
public void setAuteur(String auteur) {
    this.auteur = auteur;
}
public String getIsbn() {
    return isbn;
}
public void setIsbn(String isbn) {
    this.isbn = isbn;
}
public int getAnnee() {
    return annee;
}
public void setAnnee(int annee) {
    this.annee = annee;
}
public String getCategorie() {
    return categorie;
}
public void setCategorie(String categorie) {
    this.categorie = categorie;
}

}
