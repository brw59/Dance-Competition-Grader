package dancecompetition.service;

import dancecompetition.system.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The Controller for the Main GUI.
 * @author Kyle
 */
public class MainController
   implements Initializable
{
   /**
    * The current save file/ destination save will use.
    */
   private File currentSave;

   /**
    * The main area of the display, used for adding children
    */
   //@FXML
   //private Pane mainarea = new Pane();
   public Pane mainarea = new Pane();

   /**
    * ab choicebox selects if there is an a or b heat in the final
    */
  // @FXML
   private ChoiceBox<?> ab;

   /**
    * age is the age group of the dancers in the current competition
    */
  // @FXML
   private ChoiceBox<?> age;

   /**
    * couples selects the number of couples in the heat
    */
   //@FXML
   private ChoiceBox<?> couples;

   /**
    * dance is the style of dance for the current heat
    */
   //@FXML
   private ChoiceBox<?> dance;

   /**
    * judges is the number of judges active on the current heat
    */
  // @FXML
   private ChoiceBox<?> judges;

   /**
    * level is the competition level of the dance
    */
   //@FXML
   private ChoiceBox<?> level;

   /**
    * output is the text area where the calculations will be displayed.
    */
   //@FXML
   //private TextArea output = new TextArea();
   public TextArea output = new TextArea();

   /**
    * textBoxes are the text fields where the couple numbers will be entered
    */
   //@FXML
   private ArrayList<TextField> textBoxes = new ArrayList();

   /**
    * the placement box, simply a location to place placement labels
    */
   //@FXML
   //private VBox placementBox = new VBox();
    public VBox placementBox = new VBox();
   /**
    * judges box, simply a location to place the judge letter labels
    */
   //@FXML
   public HBox judgesBox = new HBox();
   //private HBox judgesBox = new HBox();

   /**
    * Judges names labels, stored in an array for easier insertion
    */
   //@FXML
   private ArrayList<Label> judgeNames = new ArrayList();

   /**
    * Placement labels, stored in an array for easier insertion
    */
   //@FXML
   private ArrayList<Label> couplePlace = new ArrayList();

   /**
    * calculate button, used to request focus to force proper traversal order
    */
   //@FXML
   private Button calculateButton;

   /**
    * statusMsg label, line 1
    */
   //@FXML
   private Label statusMSG = new Label();

   /**
    * statusMSG2 label, line 2
    */
   //@FXML
   private Label statusMSG2 = new Label();

   /**
    * Places, an array of strings to be used for the placement labels
    */
   private final String[] places =
      { "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th" };

   /**
    * Judges names, an array of strings to be used for judges labels
    */
   private final String[] judgesnames =
      { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K" };

   /**
    * Count, used to aid the setup of traversal key events
    */
   private int count = 0;
   private int NumJudges;
   private int NumCouples;
   private String Age;
   private String AB;
   private String DanceStyle;
   private String Level;
   /**
    * handleAcceptButton
    *   pulls values from the choice boxes, calls setHeat() and generateTable()
    *
    * @param event An on click event generated from the GUI by the user.
    */
   /*public void setMainController(int numCouple, int numJudges, String mAge, String dancestyle, String level, String ab){
       NumJudges = numJudges;
       NumCouples = numCouple;
       Age = mAge;
       DanceStyle = dancestyle;
       Level = level;
       AB = ab;
   }*/

   /**
    * Creates the table of text fields as an arraylist of textfields
    *
    * @param judges represents the current number of Judges for this heat.
    * @param couples represents the current number of Couples for this heat.
    */
   
   
   public MainController(int njudges, int ncouples, String mAge, String dancestyle, String level, String ab)
   {
       NumJudges = njudges;
       NumCouples = ncouples;
       Age = mAge;
       DanceStyle = dancestyle;
       Level = level;
       AB = ab;
       
      judgeNames = new ArrayList();
      couplePlace = new ArrayList();
      textBoxes = new ArrayList();

      //initialize the array of judges labels
      judgeNames = generateLabel(njudges, 50, judgesnames);
      couplePlace = generateLabel(ncouples, 25, places);

      int layoutx = 50;

      //initialize the array of text fields. They have unique id's
      //and positioning.
      for (int x = 0; x < njudges; x++)
      {
         int layouty = 25;

         for (int y = 0; y < ncouples; y++)
         {
            TextField toAdd = new TextField();
            toAdd.setLayoutX(layoutx);
            toAdd.setLayoutY(layouty);
            toAdd.setPrefColumnCount(3);
            toAdd.setId("box" + x + y);
            textBoxes.add(toAdd);
            layouty += 25;
         }

         layoutx += 50;
      }

      //Add all created data to the GUI
     judgesBox.getChildren().addAll(judgeNames);
     placementBox.getChildren().addAll(couplePlace);
     mainarea.getChildren().addAll(textBoxes);

      //attempts to create the key listner to use the return/enter key for
      //traversal
     EventHandler traverser =
         new EventHandler<KeyEvent>()
         {
            @Override
            public void handle(KeyEvent event)
            {
               int cell = hasFocus(textBoxes);
               System.err.println(cell);

               if (event.getCode() == KeyCode.ENTER)
               {
                  requestFocus(textBoxes.get(cell + 1));
                  System.out.println(textBoxes.get(cell + 1).getId());
               }

               event.consume();
            }
         };
     
      count = 0;

      for (int b = 0; b < ((njudges * ncouples) - 1); b++)
      {
         count = b;
         System.out.println(count);
         textBoxes.get(b).removeEventHandler(KeyEvent.KEY_RELEASED, traverser);
         textBoxes.get(b).addEventHandler(KeyEvent.KEY_RELEASED, traverser);
      }
   }
   
   
   
   
   public void handleAcceptButton(ActionEvent event)
   {
      String tempStyle = DanceStyle;
      String tempLevel = Level;
      String tempAB = AB;
      String tempAge = Age;
      int tempJudges = NumJudges;
      int tempCouples = NumCouples;
      SingleFinalLite.getInstance().getData()
                     .setHeat(tempAge, tempLevel, tempStyle, tempJudges,
         tempCouples, tempAB);
      // clear a portion of the fields
      statusMSG.setText("");
      statusMSG2.setText("");

      if (! textBoxes.isEmpty())
      {
         mainarea.getChildren().removeAll(textBoxes);
      }

      if (! judgeNames.isEmpty())
      {
         judgesBox.getChildren().removeAll(judgeNames);
      }

      if (! couplePlace.isEmpty())
      {
         placementBox.getChildren().removeAll(couplePlace);
      }

      generateTable(tempJudges, tempCouples);
      textBoxes.get(0).requestFocus();

      //setup last text box to traverse to the calculate button on a keyEvent
      textBoxes.get((tempJudges * tempCouples) - 1).addEventHandler(KeyEvent.KEY_RELEASED,
         new EventHandler<KeyEvent>()
         {
            @Override
            public void handle(KeyEvent event)
            {
               if (event.getCode() == KeyCode.ENTER)
               {
                  calculateButton.requestFocus();
               }
            }
         });
   }

   /**
    * Generates the labels arrays to be used on the table of text fields
    *
    * @param numLabels represents the number of labels to be generated.
    * @param width represents how wide each label will be.
    * @param listData An array representing the string values of the labels for 
    *                 speed.
    *
    * @return the arrayList of labels.
    */
   public ArrayList generateLabel(int numLabels, int width, String[] listData)
   {
      ArrayList labels = new ArrayList();

      for (int a = 0; a < numLabels; a++)
      {
         Label letter = new Label();
         String thisLetter = "";
         thisLetter += (listData[a]);
         letter.setText(thisLetter);
         letter.setMinHeight(25);
         letter.setMinWidth(width);
         letter.setAlignment(Pos.CENTER);
         labels.add(letter);
      }

      return labels;
   }

   /**
    * Creates the table of text fields as an arraylist of textfields
    *
    * @param judges represents the current number of Judges for this heat.
    * @param couples represents the current number of Couples for this heat.
    */
   
   
   public void generateTable(int njudges, int ncouples)
   {
       NumJudges = njudges;
       NumCouples = ncouples;
      judgeNames = new ArrayList();
      couplePlace = new ArrayList();
      textBoxes = new ArrayList();

      //initialize the array of judges labels
      judgeNames = generateLabel(njudges, 50, judgesnames);
      couplePlace = generateLabel(ncouples, 25, places);

      int layoutx = 365;

      //initialize the array of text fields. They have unique id's
      //and positioning.
      for (int x = 0; x < njudges; x++)
      {
         int layouty = 0;

         for (int y = 0; y < ncouples; y++)
         {
            TextField toAdd = new TextField();
            toAdd.setLayoutX(layoutx);
            toAdd.setLayoutY(layouty);
            toAdd.setPrefColumnCount(3);
            toAdd.setId("box" + x + y);
            textBoxes.add(toAdd);
            layouty += 25;
         }

         layoutx += 50;
      }

      //Add all created data to the GUI
     judgesBox.getChildren().addAll(judgeNames);
     placementBox.getChildren().addAll(couplePlace);
     mainarea.getChildren().addAll(textBoxes);

      //attempts to create the key listner to use the return/enter key for
      //traversal
     EventHandler traverser =
         new EventHandler<KeyEvent>()
         {
            @Override
            public void handle(KeyEvent event)
            {
               int cell = hasFocus(textBoxes);
               System.err.println(cell);

               if (event.getCode() == KeyCode.ENTER)
               {
                  requestFocus(textBoxes.get(cell + 1));
                  System.out.println(textBoxes.get(cell + 1).getId());
               }

               event.consume();
            }
         };
     
      count = 0;

      for (int b = 0; b < ((njudges * ncouples) - 1); b++)
      {
         count = b;
         System.out.println(count);
         textBoxes.get(b).removeEventHandler(KeyEvent.KEY_RELEASED, traverser);
         textBoxes.get(b).addEventHandler(KeyEvent.KEY_RELEASED, traverser);
      }
   }
   ;
   

   /**
    * Function which requests the focus for a certain node
    *
    * @param node A Java Node object (in this case a text box).
    */
   private void requestFocus(final Node node)
   {
      Platform.runLater(new Runnable()
         {
            @Override
            public void run()
            {
               node.requestFocus();
               System.out.println(node.getId());
            }
         });
   }

   /**
    * Returns the index of the object which has focus
    *
    * @param list A list of text boxes.
    *
    * @return The location of the node which has focus at this time.
    */
   private int hasFocus(ArrayList list)
   {
      Node currentNode;
      currentNode = (Node) list.get(0);

      int location = 0;

      while (! currentNode.isFocused())
      {
         currentNode = (Node) list.get(location + 1);
         location++;
      }

      return location;
   }

   /**
    * First confirms valid data has been entered using the TextBoxParser
    * then calls the appropriate implementRules functions and outputs the
    * result.
    *
    * @param event An on click event generated from the GUI by the user.
    */
   //@FXML
   public void handleCalcButton(int first, Boolean isSingle, ActionEvent event, TextArea mCalculations)
   {
      String tempStyle = DanceStyle;
      String tempLevel = Level;
      String tempAB = AB; // or number of dances for multi-dance
      String tempAge = Age;
      int tempJudges = NumJudges;
      int tempCouples = NumCouples;
      output = mCalculations;
      SingleFinalLite.getInstance().getData()
                     .setHeat(tempAge, tempLevel, tempStyle, tempJudges,
         tempCouples, tempAB);
       
      //Setup needed data
      ArrayList<Integer> result;
      boolean errorA;
      boolean errorB;
      boolean errorC;
      ArrayList toCheck = textBoxes;

      //Make sure errors and internal data are cleared
    if (first == 0) {
        TextBoxParser.clearResult();
        statusMSG.setText("");
        statusMSG2.setText("");
    }

      //Call error checking functions
      errorA = TextBoxParser.checkNumbers(toCheck, tempJudges, tempCouples);
      errorB = TextBoxParser.checkBounds(toCheck, tempJudges, tempCouples);
      errorC = ! TextBoxParser.validateCoupleNumbers(toCheck, tempJudges,
            tempCouples);

      if (errorA || errorB || errorC)
      {
         mainarea.getChildren().removeAll(textBoxes);
         textBoxes = TextBoxParser.getParsedBoxes();
         mainarea.getChildren().addAll(textBoxes);
         statusMSG.setText("Check the values entered above!");

         return;
      }

      //output the result int the output field
      result = TextBoxParser.getResult();
      output.setEditable(false);
      SingleFinalLite.getInstance().getData().loadCouples(result);
      SingleFinalLite.getInstance().implementRules();
      output.setText(output.getText() + new DisplayStringBuilder().buildTable(
            SingleFinalLite.getInstance().getData()));
   }

   /**
    * Clears the dynamic contents of the GUI. does not clear the number of
    * judges or couples.
    *
    * @param event An on click event generated from the GUI by the user.
    */
   @FXML
   public void handleClear(ActionEvent event)
   {
      dance.getSelectionModel().clearAndSelect(0);
      age.getSelectionModel().clearAndSelect(0);
      level.getSelectionModel().clearAndSelect(0);
      ab.getSelectionModel().clearAndSelect(0);
      mainarea.getChildren().removeAll(textBoxes);
      judgesBox.getChildren().removeAll(judgeNames);
      placementBox.getChildren().removeAll(couplePlace);
      output.setText("");
      statusMSG.setText("");
      statusMSG2.setText("");
   }

   /**
    * Handles the saving of files sending in the current save file
    * NOTE: Function is not used in current deployment. Has been left in
    *       as another option.
    *
    * @param event An on click event generated from the GUI by the user.
    */
   @FXML
   public void handleSave(ActionEvent event)
   {
      String message = SingleFinalLite.getInstance().save(currentSave);
      statusMSG.setText(message);
   }

   /**
    * Calls the file chooser dialog and sets the current save file
    * NOTE: Function does not call save, it only sets the filename
    *
    * @param event An on click event generated from the GUI by the user.
    */
   @FXML
   public void setSave(ActionEvent event)
   {
      FileChooser fileChooser = new FileChooser();

      //Set extension filter
      FileChooser.ExtensionFilter extFilter =
         new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
      fileChooser.getExtensionFilters().add(extFilter);
      fileChooser.setInitialFileName("default.txt");
      fileChooser.setTitle("Set Save File");

      //Show save file dialog
      currentSave = fileChooser.showSaveDialog(SingleFinalLite.getInstance()
                                                              .getStage());

      if (currentSave == null)
      {
         statusMSG2.setText("Save File was Not Set!");
         return;
      }

      statusMSG2.setText("Save File Has Been Set!");
   }

   /**
    * Function created as an all purpose function to facilitate a single
    * button press. first saves the file and then attempts to open the print
    * dialog. currently print functionality is buggy.
    *
    * @param event An on click event generated from the GUI by the user.
    */
   @FXML
   public void handleSaveandPrint(ActionEvent event)
   {
      //Save the results 
      String messageA = SingleFinalLite.getInstance().save(currentSave);
      statusMSG2.setText(messageA);

      //Attempt to print
      Runtime runtime = Runtime.getRuntime();
      File printerPath =
         new File(Printer.class.getProtectionDomain().getCodeSource()
                               .getLocation().getPath());
      System.out.println(printerPath.getAbsolutePath());

      try
      {
         String command =
            "java -cp " + printerPath.getAbsolutePath() +
            " singlefinallite.system.Printer tempPrint.sfl";

         System.out.println("Executing command \"" + command + "\"");

         File tempPrint = new File("tempPrint.sfl");
         FileWriter fw = new FileWriter(tempPrint, true);
         fw.write(new DisplayStringBuilder().buildTable(
               SingleFinalLite.getInstance().getData()));
         fw.close();

         //          Process running = runtime.exec(command);
         //running.waitFor();
         String message = "";

         //switch(running.exitValue())
         switch (new Shell().command(command))
         {
            case -1:
               message = "Unknown Printer Error";

               break;

            case 1:
               message = "Failed to Print";

               break;

            case 2:
               message = "Print Cancelled";

               break;

            case 3:
               message = "Print Sucessful";

               break;

            case 4:
               message = "Failed to launch print dialog";

               break;

            default:
               message = "Unknown print error";

               break;
         }

         tempPrint.delete();
         statusMSG.setText(message);
      }
      catch (Exception e)
      {
         System.out.println("failed to print " + e.getMessage());
         statusMSG.setText("Failed to Print Results.");
      }
   }

   /**
    * Provides the initial setup of the GUI, sets the default selections.
    *
    * @param url unused url variable.
    * @param rb unused resource bundle variable.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb)
   {
       assert ab != null : "fx:id=\"ab\" was not injected: check your FXML file 'MainGUI.fxml'.";
       assert age != null : "fx:id=\"age\" was not injected: check your FXML file 'MainGUI.fxml'.";
       assert couples != null : "fx:id=\"couples\" was not injected: check your FXML file 'MainGUI.fxml'.";
       assert dance != null : "fx:id=\"dance\" was not injected: check your FXML file 'MainGUI.fxml'.";
       assert judges != null : "fx:id=\"judges\" was not injected: check your FXML file 'MainGUI.fxml'.";
       assert level != null : "fx:id=\"level\" was not injected: check your FXML file 'MainGUI.fxml'.";

      dance.getSelectionModel().select(0);
      level.getSelectionModel().select(0);
      age.getSelectionModel().select(0);
      ab.getSelectionModel().select(0);
      statusMSG.setLayoutX(161);
      statusMSG.setLayoutY(282);
      statusMSG.setStyle("-fx-font-size:14pt");
      mainarea.getChildren().add(statusMSG);
      statusMSG2.setLayoutX(161);
      statusMSG2.setLayoutY(297);
      statusMSG2.setStyle("-fx-font-size:14pt");
      mainarea.getChildren().add(statusMSG2);
   }
}
