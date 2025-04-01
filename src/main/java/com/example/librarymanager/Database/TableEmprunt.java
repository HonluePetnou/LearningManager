package com.example.librarymanager.Database;

import com.example.librarymanager.Models.Emprunt;;

public class TableEmprunt  implements BaseDeDonnee<Emprunt> {
 
  @Override
  public void Update(Emprunt emprunt) {
      
  }
  @Override
  public void Delete(int id) {
    
  }
  @Override
  public void create(Emprunt emprunt) {
        /** cette fonction permet d'ajouter un emprunt dans la base de donnees */
        // 1. verifier si l'emprunt existe deja 
        // 2. si non on l'ajoute a la base de donnees 
        // 3. sinon on renvoie une exception   
  }
}
