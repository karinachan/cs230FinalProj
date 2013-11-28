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
import javax.swing.*;
import javax.swing.event.*;

public class InstructionPanel extends JPanel
{
   private JPanel controlsPanel, displaySchoolPanel;
   private JSlider academicsSlider, researchSlider, publicationsSlider;
   private JLabel academicsLabel, researchLabel, publicationsLabel, highestLabel, instructionsLabel;
   private GradSchools grad;

   //-----------------------------------------------------------------
   public EvaluationPanel(GradSchools g)
   {
     setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //sets layout
     this.grad = g; 
     
     instructionsLabel = new JLabel ("<html><strong>Instructions:</strong> Use the sliders to determine the weight for the categories"+
                                     " that you selected on the previous tab. <br>The school with the computed Overall rating with the selected weights will be shown."+
                                     "<br><br></html>"); //instructions for the user
     instructionsLabel.setFont(new Font("Rockwell", Font.PLAIN, 16));
      academicsSlider = new JSlider (JSlider.HORIZONTAL, 0, 5, 0); //creates the sliders
      academicsSlider.setMajorTickSpacing (1);
      
      academicsSlider.setPaintTicks (true);
      academicsSlider.setPaintLabels (true);
      academicsSlider.setAlignmentX (Component.LEFT_ALIGNMENT);

      researchSlider = new JSlider (JSlider.HORIZONTAL, 0, 5, 0);
      researchSlider.setMajorTickSpacing (1); //spaces by 1 each
      
      researchSlider.setPaintTicks (true);
      researchSlider.setPaintLabels (true);
      researchSlider.setAlignmentX (Component.LEFT_ALIGNMENT);

      publicationsSlider = new JSlider (JSlider.HORIZONTAL, 0, 5, 0);
      publicationsSlider.setMajorTickSpacing (1);
      publicationsSlider.setPaintTicks (true);
      publicationsSlider.setPaintLabels (true);
      publicationsSlider.setAlignmentX (Component.LEFT_ALIGNMENT);

      SliderListener listener = new SliderListener(); //sets up the listener for each slider, receives the value
      academicsSlider.addChangeListener(listener);
      researchSlider.addChangeListener (listener);
      publicationsSlider.addChangeListener(listener);

      academicsLabel = new JLabel ("Academics: ");
      academicsLabel.setFont(new Font("Rockwell", Font.PLAIN, 11));
      academicsLabel.setAlignmentX (Component.LEFT_ALIGNMENT);
      researchLabel = new JLabel ("Research: ");
      researchLabel.setFont(new Font("Rockwell", Font.PLAIN, 11));
      researchLabel.setAlignmentX (Component.LEFT_ALIGNMENT);
      publicationsLabel = new JLabel ("Publications: ");
      publicationsLabel.setFont(new Font("Rockwell", Font.PLAIN, 11));
      publicationsLabel.setAlignmentX (Component.LEFT_ALIGNMENT);

      controlsPanel = new JPanel(); //panel for all the sliders
      BoxLayout layout = new BoxLayout (controlsPanel, BoxLayout.X_AXIS);
      controlsPanel.setLayout (layout);
      controlsPanel.add (academicsLabel);
      controlsPanel.add (academicsSlider);
      controlsPanel.add (Box.createRigidArea (new Dimension (0, 20)));
      controlsPanel.add (researchLabel);
      controlsPanel.add (researchSlider);
      controlsPanel.add (Box.createRigidArea (new Dimension (0, 20)));
      controlsPanel.add (publicationsLabel);
      controlsPanel.add (publicationsSlider);
      
      
      
      highestLabel= new JLabel("");
      highestLabel.setFont(new Font("Rockwell", Font.PLAIN, 11)); //label for the school with the highest overall rating based on weighted values
      highestLabel.setAlignmentX (Component.LEFT_ALIGNMENT);
      
      displaySchoolPanel = new JPanel();
      displaySchoolPanel.add(highestLabel);
      

      add(instructionsLabel);
      add(controlsPanel);
      add(displaySchoolPanel);
   
   }

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

         grad.computeRatings(academicWeight, researchWeight, publicationsWeight); //puts the values into the function from gradschools
         grad.rankSchools("Overall");
         
         highestLabel.setText(grad.getTop().toString()); //sets the text of the highest label to the school with the highest overall ranking
         
      }
   }
}
