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
  private JPanel cards, home, buttons, game, instructs, bio;
  private JButton start, howto, about;
  private JLabel titleLabel;
  //private JSlider academicsSlider, researchSlider, publicationsSlider;
  //private JLabel academicsLabel, researchLabel, publicationsLabel, highestLabel, titleLabel;
  
  public InstructionPanel()
  {
    cards = new JPanel(new CardLayout()); 
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
  
  private JPanel makeHowTo() { 
    JPanel panel = new JPanel(); 
    JLabel hi = new JLabel("hi"); 
    panel.add(hi); 
    return panel; 
  }
  

  
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      //FOR THE ADD SCHOOL BUTTON
      if (event.getSource() == start) {
        System.out.println("Start"); 
        //launch the game
      } else if (event.getSource() == howto) { 
        System.out.println("How To"); 
        home=makeHowTo();
        //go to howto page
      } else if (event.getSource() == about) { 
        System.out.println("About"); 
        //go to about page
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
