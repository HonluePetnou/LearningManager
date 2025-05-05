package com.example.librarymanager.Controllers;

import com.example.librarymanager.Views.ViewFactory;



public abstract class Controllers {
 protected  ViewFactory viewFactory  ;

 public void  setViewFactory(ViewFactory viewFactory){
    this.viewFactory = viewFactory ;
 };
}
