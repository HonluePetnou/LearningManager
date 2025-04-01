package com.example.librarymanager.Database;

import com.example.librarymanager.Models.Utilisateur;

public class TableUtilisateur implements BaseDeDonnee<Utilisateur> {
 
  @Override
  public void Update(Utilisateur utilisateur) {
      
  }
 @Override
 public void Delete(int id) {

 }
 @Override
 public void create(Utilisateur utilisateur) {
     /** cette fonction permet d'ajouter un utilisateur dans la base de donnees */
     // 1. verifier si l'utilisateur existe deja 
     // 2. si non on l'ajoute a la base de donnees 
     // 3. sinon on renvoie une exception 
 }

 public static String Authentifier(String email , String Password){
    /**  cette fonction retourne le role de l'utilisateur s'il est enregistre dans 
     la Basededonne a defaut elle renvoie null */
   return null ;
 }
}
