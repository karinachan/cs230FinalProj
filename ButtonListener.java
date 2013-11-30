import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class ButtonListener implements ActionListener {
  
  /*AboutPanel about;
   InstructionPanel instr;
   GamePanel game;
   LayoutPanel layout;
   */
  
  
  
  //JPanel about, instr, game, layout;
  JButton set;
  public ButtonListener(JButton boop){
    set=boop;
  } 
  
  
  
  
  public void actionPerformed(ActionEvent event){
    //FOR THE ADD SCHOOL BUTTON
    if (event.getSource()==set){
      System.out.println("yes!");
    }
    /*
     if (event.getSource() == start) {
     System.out.println("Start"); 
     game.setVisible(true);
     layout.setVisible(false);
     //launch the game
     } else if (event.getSource() == howto) { 
     System.out.println("How To"); 
     
     
     instr.setVisible(true);
     layout.setVisible(false);
     //this.getContentPane().add(makeHowTo());
     
     
     
     //home=makeHowTo();
     
     //go to howto page
     } else if (event.getSource() == about) { 
     System.out.println("About"); 
     about.setVisible(true);
     layout.setVisible(false);
     //go to about page
     }
     else if (event.getSource() == aboutback) {
     System.out.println("Return");
     layout.setVisible(true);
     about.setVisible(false);
     
     
     } else if (event.getSource() == gameback) {
     System.out.println("Return");
     layout.setVisible(true);
     game.setVisible(false);
     
     
     } else if (event.getSource() == instrback) {
     System.out.println("Return");
     layout.setVisible(true);
     instr.setVisible(false);
     
     
     }
     */
    
  }
}

/*  
 //*****************************************************************
 //  Represents the listener for all three sliders.
 //*****************************************************************
 private class SliderListener implements ChangeListener
 {
 private int academicWeight, researchWeight, publicationsWeight;
 
 //--------------------------------------------------------------
 //  Gets the value of each slider, then updates the labels and
 //  the color panel.
 //--------------------------------------------------------------
 public void stateChanged (ChangeEvent event)
 {
 academicWeight = academicsSlider.getValue(); //gets values from the slider
 researchWeight = researchSlider.getValue();
 publicationsWeight = publicationsSlider.getValue();
 
 
 
 }
 }*/
