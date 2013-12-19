/** Karina Chan and Laura Zeng
  * CS 230 
  * Final Project
  * AboutPanel.java
  * 
  * Creates the "About Us" panel for our PokeWorld GUI. 
  */

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class AboutPanel extends JPanel {
  
  public JPanel aboutPanel; 
  private JButton aboutBack;
  
  public AboutPanel(){
    setLayout(new FlowLayout()); //sets the layout to border layout
    setBackground (new Color (255, 255, 255)); //sets the color background
    
    //creates labels with text on them
    JLabel title = new JLabel ("<html> <strong> ABOUT:</strong><br>"
                              +"<br>------------------------<br>Created by Karina Chan and Laura Zeng");
    title.setFont(new Font("Rockwell", Font.PLAIN, 16));    
//    JLabel divider = new JLabel ("------------------------");
//    JLabel aboutText = new JLabel ("About US; we are narcissists and g00d spellerz");
    
    //adds labels and a picture
    add(title);    
    JLabel picLabel = new JLabel("<html><img src='http://bit.ly/1bgeVvc'></html>");
    add (picLabel,BorderLayout.LINE_END);
    picLabel.setBorder(new EmptyBorder( 0, 0, 0, 50 ) );
  }
  
  /** 
   * returns the AboutPanel
   * @return JPanel
   */
  public JPanel getPanel(){
    return aboutPanel;
  }
  
  /**
   * returns the aboutBack button
   * @return aboutBack
   */
  public JButton getButton(){
    return aboutBack;
  }
}
  
