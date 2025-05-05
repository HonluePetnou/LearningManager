package com.example.librarymanager.utils;
public class hashpassword {
    
     public static String CrypterMotdePasse(String motdepasse){
        String hashed = BCrypt.hashpw(motdepasse, BCrypt.gensalt(12));
        return hashed ;
     }

     public static boolean decrypterMotdePasse(String motdepasseEntre ,String motdepasseStocke){
        return BCrypt.checkpw(motdepasseEntre, motdepasseStocke);
     }
    }