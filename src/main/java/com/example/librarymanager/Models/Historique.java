package com.example.librarymanager.Models;

public class Historique {
   private int Userid ;
   private int livreid ;
   private Emprunt Emprunt ;
   public Historique( final int Userid ,final int livreid ,final Emprunt Emprunt )
   {
    this.Emprunt = Emprunt ;
    this.Userid = Userid ;
    this.livreid = livreid ;
   }
public int getUserid() {
    return Userid;
}
public void setUserid(int userid) {
    Userid = userid;
}
public int getLivreid() {
    return livreid;
}
public void setLivreid(int livreid) {
    this.livreid = livreid;
}
public Emprunt getEmprunt() {
    return Emprunt;
}
public void setEmprunt(Emprunt emprunt) {
    Emprunt = emprunt;
}
   
}
