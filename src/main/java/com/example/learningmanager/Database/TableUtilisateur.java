package repository;

import model.Utilisateur;

public class TableUtilisateur implements BaseDeDonnee<Utilisateur> {
  @Override
  public Utilisateur Read(int id) {
      // TODO Auto-generated method stub
      return null;
  }
  @Override
  public void Update(Utilisateur utilisateur) {
      // TODO Auto-generated method stub
      
  }
 @Override
 public void Delete(int id) {
     // TODO Auto-generated method stub
     
 }
 @Override
 public void create(Utilisateur utilisateur) {
     // TODO Auto-generated method stub    
 }

 public static String Authentifier(String email , String Password){
    /**  cette fonction retourne le role de l'utilisateur s'il est enregistre dans 
     la Basededonne a defaut elle renvoie null */
   return null ;
 }
}
