
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
    JFrame frame = new JFrame ("HomeGUI");
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);    
    frame.setSize(new Dimension(500,400)); 
    
    
    LayoutPanel panel = new LayoutPanel(schools);
    frame.getContentPane().add(panel);
    //adds each individual panel on to the pane
    // tp.addTab ("Instructions", new InstructionPanel());
    // tp.addTab("Start", new InstructionPanel());
    
    
    
    
    
    
//      frame.pack();
    frame.setVisible(true);
    frame.setResizable(false); //sets it so that user cannot resize the windows because users are not trustworthy
  }
}
