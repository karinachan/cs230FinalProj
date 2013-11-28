
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
      InstructionPanel panel = new InstructionPanel();
      frame.getContentPane().add(panel);
      

      
//      frame.pack();
      frame.setVisible(true);
      frame.setResizable(false); //sets it so that user cannot resize the windows because users are not trustworthy
   }
}
