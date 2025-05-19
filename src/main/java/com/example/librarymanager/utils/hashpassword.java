package com.example.librarymanager.utils;

/**
 * Utility class for password hashing and verification.
 *
 * This class provides static methods to securely hash passwords and verify
 * password matches
 * using the BCrypt algorithm.
 *
 * Main features:
 * - Hashes plain text passwords using BCrypt with a salt.
 * - Verifies if a plain text password matches a hashed password.
 *
 * Usage:
 * - Use hashpassword.CrypterMotdePasse(password) to hash a password before
 * storing it.
 * - Use hashpassword.decrypterMotdePasse(enteredPassword, storedHash) to verify
 * a password.
 *
 * Dependencies:
 * - BCrypt library for password hashing and verification.
 */
public class hashpassword {

   /**
    * Hashes a plain text password using BCrypt.
    * 
    * @param motdepasse the plain text password
    * @return the hashed password
    */
   public static String CrypterMotdePasse(String motdepasse) {
      String hashed = BCrypt.hashpw(motdepasse, BCrypt.gensalt(12));
      return hashed;
   }

   /**
    * Verifies if the entered password matches the stored hashed password.
    * 
    * @param motdepasseEntre  the entered plain text password
    * @param motdepasseStocke the stored hashed password
    * @return true if the password matches, false otherwise
    */
   public static boolean decrypterMotdePasse(String motdepasseEntre, String motdepasseStocke) {
      return BCrypt.checkpw(motdepasseEntre, motdepasseStocke);
   }
}