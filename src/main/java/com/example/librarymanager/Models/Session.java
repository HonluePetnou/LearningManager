package  com.example.librarymanager.Models;

public class Session {
   private int UserID ;
   private String role ;
  public Session (final int UserID , final String role){
     this.UserID = UserID ;
     this.role = role ;
  }

public void creerSession( String email , String motDePasse){

   }

   public void deconnecter(){
    this.role = null ;
    this.UserID = 0 ;
   }


public int getUserID() {
    return UserID;
}
public void setUserID(int userID) {
    UserID = userID;
}
public String getRole() {
    return role;
}
public void setRole(String role) {
    this.role = role;
}
}
