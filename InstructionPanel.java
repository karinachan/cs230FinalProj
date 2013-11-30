//********************************************************************
//  EvaluationPanel.java
//Name: Karina and Alice
//Class: CS 230
//Date: 10/6/13
//Assignment 4
//Purpose: To create the tab where users evaluate the overall values based on their selected weighted values
//for academic, research, and publication ratings. 
//********************************************************************
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class InstructionPanel extends JPanel
{
  
  
  
  
  private JPanel cards, home, buttons, game, instructs, bio, instrpanel, aboutPanel, gamePanel;
  
  private JButton start, howto, about, instrback, aboutback, gameback;
  private JLabel titleLabel;
  //private JSlider academicsSlider, researchSlider, publicationsSlider;
  //private JLabel academicsLabel, researchLabel, publicationsLabel, highestLabel, titleLabel;
  
  public InstructionPanel()
  {
    
    //create a function that makes the GUI pages (so we don't have to type this out 5 million times?) 
    
    //instruction panel
    
    JLabel hi= new JLabel("TOMATOES");
    instrpanel = new JPanel(); 
    instrpanel.setVisible(false); //hidden until pressed
    instrpanel.add(hi); 
    instrback = new JButton ("Back"); 
    instrback.addActionListener(new ButtonListener()); 
    instrpanel.add(instrback);
    add(instrpanel);
    
    
    //game panel -- will need to be embellished and have it's own intricacies with button clicking etc. 
    
    gamePanel = new JPanel();
    JLabel hi1= new JLabel("POTATOES");
    gamePanel.setVisible(false); //hidden until pressed
    gamePanel.add(hi1); 
    gameback = new JButton ("Back"); 
    gameback.addActionListener(new ButtonListener()); 
    gamePanel.add(gameback);
    add(gamePanel);
    
    //about us panel
    aboutPanel = new JPanel();
    JLabel hi2= new JLabel("CABBAGE");
    aboutPanel.setVisible(false); //hidden until pressed
    aboutPanel.add(hi2); 
    aboutback = new JButton ("Back"); 
    aboutback.addActionListener(new ButtonListener()); 
    aboutPanel.add(aboutback);
    add(aboutPanel);
    
    //cards = new JPanel(new CardLayout()); 
    home = new JPanel(new BorderLayout()); 
    home.setBorder(new EmptyBorder(20,20,20,20));
    buttons = new JPanel (new FlowLayout());
    buttons.setBorder(new EmptyBorder(5,5,5,5)); 
    titleLabel = new JLabel ("<html><strong><center>CS LAND</center>" + 
                             "</strong><br></html>"); 
    titleLabel.setFont(new Font("Rockwell", Font.PLAIN, 40));
    start = new JButton ("Start"); 
    howto = new JButton ("How To"); 
    about = new JButton ("About Us"); 
    start.addActionListener(new ButtonListener()); 
    howto.addActionListener(new ButtonListener());
    about.addActionListener(new ButtonListener());
    home.add(titleLabel, BorderLayout.PAGE_START); 
    buttons.add(start);
    buttons.add(howto); 
    buttons.add(about);
    home.add(buttons, BorderLayout.CENTER); 
    add(home); 
  }
  
  
  
  
  /*private JPanel makeHowTo() { 
   JPanel panel = new JPanel(); 
   JLabel hi = new JLabel("hi"); 
   panel.add(hi); 
   return panel; 
   }
   */
  
  
  
  
  
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      //FOR THE ADD SCHOOL BUTTON
      
      if (event.getSource() == start) {
        System.out.println("Start"); 
        gamePanel.setVisible(true);
        home.setVisible(false);
        //launch the game
      } else if (event.getSource() == howto) { 
        System.out.println("How To"); 
        
        
        instrpanel.setVisible(true);
        home.setVisible(false);
        //this.getContentPane().add(makeHowTo());
        
        
        
        //home=makeHowTo();
        
        //go to howto page
      } else if (event.getSource() == about) { 
        System.out.println("About"); 
        aboutPanel.setVisible(true);
        home.setVisible(false);
        //go to about page
      }
      else if (event.getSource() == aboutback) {
        System.out.println("Return");
        home.setVisible(true);
        aboutPanel.setVisible(false);
        
        
    } else if (event.getSource() == gameback) {
        System.out.println("Return");
        home.setVisible(true);
        gamePanel.setVisible(false);
        
        
    } else if (event.getSource() == instrback) {
        System.out.println("Return");
        home.setVisible(true);
        instrpanel.setVisible(false);
        
        
    }
    
    }
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
