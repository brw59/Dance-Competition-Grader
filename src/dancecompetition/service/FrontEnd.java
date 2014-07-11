package dancecompetition.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

import java.util.prefs.*;

/**
 * This is the program that holds everything together.  It has the 
 * TabPane's on this level, and each TabPane has a TabDisplay.  It also
 * has the menu options on it, and the PublicWindow, which in turn has
 * the pointers to the two PublicDisplays.
 * @author Jeff
 *
 */
public class FrontEnd 
extends Application
{

	 private PublicWindow mPublicWindow;
	 private TabPane tabPane;
	 private Tab sTab;
	 private Tab mTab;
         private Tab sfTab;
         private Tab mfTab;
	 private PublicDisplay sPublicDisplay;
	 private PublicDisplay mPublicDisplay;
	 private TabDisplay sTabDisplay;
	 private TabDisplay mTabDisplay;
         private FinalTabDisplay sfTabDisplay;
         private FinalTabDisplay mfTabDisplay;
	 private MenuBar menuBar;
	 private boolean singleSelected;
	 private Menu mHelp;
	 private Menu mFile;
	 private MenuItem menuSave;
	 private MenuItem menuClear;
         private MenuItem menuPrint;
	 private MenuItem menuAbout;
	 private MenuItem menuExit;
	 private Stage mStage;
   
	/**
	 * start will run the program.  Creates 2 TabPane, PublicWindow,
	 * and 2 PublicDisplay's and sets them all to visible.
	 */
	public void start(Stage pStage)
	{
		/*
		 * set the standard view point on the single tab.  Changing this value
		 * will NOT make it so it starts on the other one.  If you do change which 
		 * one opens first, you need to change what singleSelected equals
		 */
		singleSelected = true;
		Group root = new Group();
		
		tabPane = new TabPane();
		
		sTab = new Tab("CallBacks - Single Dance");
		mTab = new Tab("CallBacks - Multiple Dance");
                
                sfTab = new Tab("Final - Single Dance");
                mfTab = new Tab("Final - Multiple Dances");
		
		sPublicDisplay = new PublicDisplay();
		mPublicDisplay = new PublicDisplay();
		sTabDisplay = new TabDisplay(true, sPublicDisplay, pStage);
		mTabDisplay = new TabDisplay(false, mPublicDisplay, pStage);
                
                //just added these two
                sfTabDisplay = new FinalTabDisplay(true, pStage); // make new tab display with no public view
                mfTabDisplay = new FinalTabDisplay(false, pStage);// make new tab display with no public view
		
                sTab.setContent(sTabDisplay);
		mTab.setContent(mTabDisplay);
                
                //just added these two
                sfTab.setContent(sfTabDisplay);
                mfTab.setContent(mfTabDisplay);
		
                tabPane.getTabs().add(sTab);
		tabPane.getTabs().add(mTab);
                tabPane.getTabs().add(sfTab);
                tabPane.getTabs().add(mfTab);
		mPublicWindow = new PublicWindow(sPublicDisplay, mPublicDisplay);
		
		 // prevent the tabs from being able to close
		sTab.setClosable(false);
		mTab.setClosable(false);
		sfTab.setClosable(false);
                mfTab.setClosable(false);
                
		//setting up the menuBar
		menuSave = new MenuItem("Save");
		menuClear = new MenuItem("Clear");
                menuPrint = new MenuItem("Print");
		menuExit = new MenuItem("Exit");
		mHelp = new Menu("Help");
		mFile = new Menu("File");
		menuAbout = new MenuItem("About");
		mFile.getItems().addAll(menuSave, menuClear, menuPrint, menuExit);
		menuBar = new MenuBar();
		mHelp.getItems().add(menuAbout);
		menuBar.getMenus().addAll(mFile, mHelp);
		String crt = "";
		/*
		String opt = System.get
		if (System.getProperties("os.name").contains("Mac"))
		{
			
		}*/
		menuSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		menuSave.setOnAction(new EventHandler<ActionEvent>()
		{
                    public void handle(ActionEvent e)
                    {
			saveDisplay();
                    }
		});
		menuClear.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
		menuClear.setOnAction(new EventHandler<ActionEvent>()
		{
                    public void handle(ActionEvent e)
                    {
			clear();
                    }
		});
                menuPrint.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
		menuPrint.setOnAction(new EventHandler<ActionEvent>()
		{
                    public void handle(ActionEvent e)
                    {
			print();
                    }
		});
		menuExit.setOnAction(new EventHandler<ActionEvent>()
		{
                    public void handle(ActionEvent e)
                    {
			close();
                    }
		});
		menuAbout.setOnAction(new EventHandler<ActionEvent>()
		{
                    public void handle(ActionEvent e)
                    {
			about();
                    }
		});
		
		/*
		 * end of menu creation
		 */
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(menuBar);
                ScrollPane scr = new ScrollPane();
                scr.setContent(tabPane);
                scr.setPrefSize(900, 625);
		borderPane.setCenter(scr);
                
                //ScrollPane scr = new ScrollPane();
                //scr.setContent(borderPane);
                //scr.setPrefSize(800, 800);
		root.getChildren().add(borderPane);
		
		Scene mScene = new Scene(root, 900, 650);
		mScene.setFill(Color.LIGHTGREY);
	
		pStage.setScene(mScene);
		
		pStage.setResizable(false);
		
		/*
		 * this catches a change on the single tab.  If it changes it will send a 
		 * signal to let the publicWindow know that it needs to change as well
		 */
		sTab.setOnSelectionChanged(new EventHandler<Event>()
		{
                    public void handle(Event e)
                    {
			singleSelected = !singleSelected;
			mPublicWindow.showTab(singleSelected);
                    }
		});
          
                sfTab.setOnSelectionChanged(new EventHandler<Event>()
		{
                    public void handle(Event e)
                    {
			singleSelected = !singleSelected;
			mPublicWindow.showTab(singleSelected);
                    }
		});
            
	   /*
	    * makes it so that both windows will close when the primary
	    * window closes
	    */
		pStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			@Override
			public void handle(WindowEvent e) 
			{
				close();
				
			}
		});
		
		pStage.setTitle("Dance Competition Scrutineer Buddy");
		mStage = pStage;
		setLocation();
		mStage.show();
	};
	
	/**
	 * clears and resets all of the required fields, just like the clear
	 * button.  But i need to know if this clears all fields or just 
	 * the one tab that is selected.
	 */
	public void clear()
	{
            if (singleSelected)
    		sTabDisplay.clear();
            else
		mTabDisplay.clear();
	}
	
	/**
	 * saves all of the information, just like it does when the info is
	 * pasted
	 */
	public void saveDisplay()
	{
            if (singleSelected)
                sTabDisplay.save();
            else			
                mTabDisplay.save();
	}
	
        /**
	 * prints the results, just like it does when the info is
	 * pasted
	 */
        public void print() {
            if (singleSelected)
                sfTabDisplay.print();
            else			
                mfTabDisplay.print();
            
        }
    
        
	/**
	 * starts up the GUI, This main is called from the Run class
	 * @param args there should not be any at all! but if there is... no idea
	 */
	public static void main(String[] args)
	{
		launch(args);
	}

	/**
	 * Just sets the location for the first window.  No use for users at all!
	 */
	public void setLocation()
	{
		Preferences prefs = Preferences.userNodeForPackage(FrontEnd.class);
		mStage.setX(prefs.getDouble("locationX", 60.0));
		mStage.setY(prefs.getDouble("locationY", 20.0));
	}
	
	/**
	 * calls the about dialog box
	 */
	public void about()
	{
		AboutWindow about = new AboutWindow();
	}
	
	/**
	 * requests closing everything that this program is in charge
	 * of
	 */
	public void close()
	{
		Platform.exit();
		System.exit(0);
	}
}
