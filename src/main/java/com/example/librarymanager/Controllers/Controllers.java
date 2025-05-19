package com.example.librarymanager.Controllers;

import com.example.librarymanager.Views.ViewFactory;

/**
 * Abstract base controller class for JavaFX controllers in the application.
 *
 * This class provides a reference to the ViewFactory, which is used to manage
 * and switch between different views (windows/scenes) in the application.
 * All controllers that need access to the ViewFactory should extend this class.
 *
 * Main features:
 * - Stores a protected ViewFactory instance for use in subclasses.
 * - Provides a setter method to inject the ViewFactory.
 *
 * Usage:
 * - Extend this class in any controller that requires access to the
 * ViewFactory.
 * - Call setViewFactory(ViewFactory) to provide the ViewFactory instance.
 *
 * Dependencies:
 * - ViewFactory: for managing and displaying application views.
 */
public abstract class Controllers {
   protected ViewFactory viewFactory;

   /**
    * Sets the ViewFactory instance for this controller.
    * 
    * @param viewFactory the ViewFactory to be used by the controller
    */
   public void setViewFactory(ViewFactory viewFactory) {
      this.viewFactory = viewFactory;
   }
}
