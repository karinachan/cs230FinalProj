//********************************************************************
//  GradLayout.java       Java Foundations
//
//********************************************************************
import javax.swing.*;
import java.awt.*; 

public class HomeGUI
{  
  //-----------------------------------------------------------------
  //  Sets up a frame containing a tabbed pane. The panel on each
  //  tab demonstrates a different layout manager.
  //-----------------------------------------------------------------
  public static void main (String[] args)
  {
    //THIS RUNS THE BUGGLEWORLD FROM THE FRAME APPLET NOT THE TABBED PANEL.
    BuggleWorld applet = new BuggleWorld();  
    JFrame.setDefaultLookAndFeelDecorated(true); // enable window decorations. 
        JFrame frame = new JFrame("POKEMON"); // create and set up the window.
        frame.setSize(700, 400); // Default frame size (should make these settable variables!)

 // [lyn. 8/30/07] Using EXIT_ON_CLOSE empirically exits all instances of an application.
 //   Use DISPOSE_ON_CLOSE to get rid of just one. 
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
        // [lyn. 8/30/07] Need to add to frame and make visible *before* init 
        //   so that attempts to reset dimensions will work. 
        frame.add(applet, BorderLayout.CENTER); // add applet to window
        //frame.setVisible(true); // display the window.
        applet.init(); // initialize the applet
       // [lyn. 8/30/07] Need to make visible again *after* init in case
       //   something like setDimensions is not called in init. 
        frame.setVisible(true); // display the window.
        
    /*
    JFrame frame = new JFrame ("HomeGUI");
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);    
    frame.setSize(new Dimension(500,400)); 
    
    
    LayoutPanel panel = new LayoutPanel();
    frame.getContentPane().add(panel);
    //adds each individual panel on to the pane
    */
    
    
    
    
    
//      frame.pack();
    frame.setVisible(true);
    frame.setResizable(false); //sets it so that user cannot resize the windows because users are not trustworthy
  }
}
