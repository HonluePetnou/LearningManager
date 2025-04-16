package com.example.librarymanager.Models;

import java.util.Date;

public class Notification {
    private String message ;
    private Date date ;
    public Notification( final String message , final Date date){
        this.message = message ;
        this.date = date ;
    }
    public void envoyer(){

    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
   
}
