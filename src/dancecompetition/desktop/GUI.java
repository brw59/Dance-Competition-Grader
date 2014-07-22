package dancecompetition.desktop;

import dancecompetition.service.ImageGetter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * Sample GUI class.
 */
public class GUI
   extends Application
{
   /**
    * Holds the singleton instance.
    */
   private static GUI cInstance = null;

   /**
    * Gets the singleton instance.
    *
    * @return the singleton instance.
    */
   public static GUI getInstance()
   {
      return cInstance;
   }

   /**
    * Creates a new GUI object.
    */
   public GUI()
   {
      super();

      synchronized (GUI.class)
      {
         if (cInstance != null)
         {
            throw new UnsupportedOperationException(getClass() +
               " is singleton but constructor called more than once.");
         }

         cInstance = this;
      }
   }

   /**
    * Initializes the GUI.
    *
    * @param primaryStage the primary stage.
    */
   private void init(Stage primaryStage)
   {
      Group root = new Group();
      primaryStage.setScene(new Scene(root));

      VBox vbox = new VBox(10);
      Image image = ImageGetter.getImage("duckling.png");
      ImageView imageView = new ImageView(image);
      Label label = new Label("Rubbber Ducky, You're The One!", imageView);
      label.setContentDisplay(ContentDisplay.TOP);

      HBox hbox = new HBox(10);
      hbox.setAlignment(Pos.CENTER);
      Button button = new Button("Exit");
      button.setOnAction(Action.get(button.getText(), this));

      hbox.getChildren().add(button);
      vbox.getChildren().add(label);
      vbox.getChildren().add(hbox);
      root.getChildren().add(vbox);
   }

   /**
    * Starts the GUI.
    *
    * @param primaryStage the primary stage.
    */
   @Override
   public void start(Stage primaryStage)
   {
      init(primaryStage);
      primaryStage.show();
   }

   /**
    * Exits the application.
    */
   public void exit()
   {
      Action.showAll();
      Platform.exit();
      System.exit(0);
   }
}
