

//********************************************************************
//  GradLayout.java       Java Foundations
//
//********************************************************************

import javax.swing.*;

public class HomeGUI
{
  //private GradSchools grad;
  
   //-----------------------------------------------------------------
   //  Sets up a frame containing a tabbed pane. The panel on each
   //  tab demonstrates a different layout manager.
   //-----------------------------------------------------------------
   public static void main (String[] args)
   {
      JFrame frame = new JFrame ("HomeGUI");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

      Pokemon pokemon = new Pokemon();
      
      JTabbedPane tp = new JTabbedPane(); //new tabbed pane
      //adds each individual panel on to the pane
      tp.addTab ("Welcome!", new WelcomePanel()); 
      tp.addTab ("Play Game", new playPanel(pokemon));//put the buggle panel here
      tp.addTab ("Instructions", new instructionPanel(grad));
      tp.addTab ("About Us", new AboutPanel()); 

      frame.getContentPane().add(tp);

      frame.pack();
      frame.setVisible(true);
      frame.setResizable(false); //sets it so that user cannot resize the windows because users are not trustworthy
   }
}
