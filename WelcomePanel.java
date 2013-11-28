//Alice Wong and Karina Chan
//Assignment 4
//10/06/13

//********************************************************************
//  AboutPanel.java       Java Foundations
//
//  Represents the introduction panel for the GradLayout program.
//********************************************************************

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AboutPanel extends JPanel
{ 

  public WelcomePanel()
  {
    setLayout(new BorderLayout()); //sets the layout to border layout
    setBackground (new Color (237, 219, 255)); //sets the color background
    
    //creates labels with text on them
    JLabel l1 = new JLabel ("<html> <strong> HOW TO USE THIS GUI:</strong><br>Follow the tabs from left to right to add and sort grad schools."
                              +"<br>------------------------<br>Created by Karina Chan and Alice Wong");
    l1.setFont(new Font("Rockwell", Font.PLAIN, 16));
   
    JLabel l2 = new JLabel ("------------------------");
    JLabel l3 = new JLabel ("Created by Karina Chan and Alice Wong");
   
    //adds labels and a picture
    add (l1);
    add(new JSeparator (JSeparator.VERTICAL), BorderLayout.LINE_START);
    
    JLabel picLabel = new JLabel("<html><img src='https://i.chzbgr.com/mediumSquare/1814227712/1F7D4A47/1'></html>");
    add (picLabel,BorderLayout.LINE_END);
    picLabel.setBorder(new EmptyBorder( 0, 0, 0, 50 ) );
  }
}