package dancecompetition.service;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class is a window that displays information
 * about the program - authors, etc..
 */
class AboutWindow
{
   /**
    * The window that holds the "about" info
    */
   Stage mWindow;

   /**
    * Constructor for AboutWindow
    */
   public AboutWindow()
   {
      VBox vbox = new VBox(8); // spacing = 8
      
      String callBackDevelopers = "Call Back Development by:"
              + "\n\tJeff Bickmore <jbickeff@gmail.com>"
              + "\n\tMackenzie Bodily <mwbodily@gmail.com>"
              + "\n\tKevin Hardy <console.beep@gmail.com>";
      String thankYou = "\n\nThanks for using the Dance Competition Grader!\n"
       + "\nSpecial thanks to:"
              + "\n\tBrother Felt for sponsoring development"
              + "\n\tBrother Neff and Brother Burton for assisting in development"
              + "\n\tAnd users like you!"
       + "\n\nPlease use this program again sometime."
       + "\n\nIf you would like a new program created contact"
       + "\nBrother Neff or Brother Burton with your \"Project proposal\".\n"
              + "\nThis product was developed by students at"
              + "\nBrigham Young University - Idaho";
      String danceCompDevelopers = "Dance Competition Grader Development by:"
              + "\n\tBenjamin Walker <learn.wisdom@icloud.com>"
              + "\n\tDavid Donley <drdonley53@yahoo.com>"
              + "\n\tJames Jorgensen <j.jorgensent@gmail.com>";
      String singleFinalDevelopers = "Single Final Lite Development by:"
              + "\n\tAdam Harris"
              + "\n\tChris Vergeray"
              + "\n\tKyle Deweese";
      String date = "Dates of Development:\n"
              + "\tSpring 2014 - Dance Competition Grader\n"
              + "\tWinter 2014 - Call Back Buddy\n"
              + "\tFall 2013 - Single Final Lite";
      vbox.getChildren().add(
    	         new Label("Dance Competition Grader\n\n" + danceCompDevelopers + "\n\n" + callBackDevelopers + "\n\n"+
    	        		  singleFinalDevelopers + "\n\n" + date + "\n" + thankYou));
      //set up window
      mWindow = new Stage();
      ScrollPane scr = new ScrollPane();
      scr.setPrefSize(600, 300);
      scr.setContent(vbox);
      Scene mScene = new Scene(scr, 600, 300);
      mScene.setFill(Color.LIGHTGRAY);
      mWindow.setScene(mScene);
      mWindow.setTitle("About Dance Comptetition Grader");
      mWindow.show();
   }
}
