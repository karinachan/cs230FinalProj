//********************************************************************
//LayoutPanel.java
//Name: Karina and Alice
//Class: CS 230
//********************************************************************
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class LayoutPanel extends JPanel
{
  
  private JPanel cards, home, buttons, game, instructs, bio;//, instrpanel, aboutPanel, gamePanel;
  
  private JButton start, howto, about;//, instrback, aboutback, gameback;
  private JLabel titleLabel;
  //private JSlider academicsSlider, researchSlider, publicationsSlider;
  //private JLabel academicsLabel, researchLabel, publicationsLabel, highestLabel, titleLabel;
  
  private InstructionPanel ip;
  private AboutPanel ap;
  private GamePanel gp;
  
  public LayoutPanel()
  {
   ip= new InstructionPanel();
   ap= new AboutPanel();
   gp= new GamePanel();
    //create a function that makes the GUI pages (so we don't have to type this out 5 million times?) 
    
    //instruction panel
     
    //what if we put this in another file for better organization
    /*JLabel hi= new JLabel("TOMATOES");
     instrpanel = new JPanel(new FlowLayout()); 
     instrpanel.setVisible(false); //hidden until pressed
     instrpanel.add(hi); 
     instrback = new JButton ("Back"); 
     instrback.addActionListener(new ButtonListener()); 
     instrpanel.add(instrback);
     add(instrpanel);
     */ 
    
    //game panel -- will need to be embellished and have it's own intricacies with button clicking etc. 
    
    /*
     gamePanel = new JPanel(new BorderLayout());
     JLabel hi1= new JLabel("POTATOES");
     gamePanel.setVisible(false); //hidden until pressed
     gamePanel.add(hi1,BorderLayout.PAGE_START); 
     gameback = new JButton ("Back"); 
     gameback.addActionListener(new ButtonListener()); 
     gamePanel.add(gameback, BorderLayout.LINE_END);
     add(gamePanel);
     
     */
    
    
    //about us panel
    
    /*
     aboutPanel = new JPanel(new FlowLayout());
     JLabel hi2= new JLabel("CABBAGE");
     aboutPanel.setVisible(false); //hidden until pressed
     aboutPanel.add(hi2); 
     aboutback = new JButton ("Back"); 
     aboutback.addActionListener(new ButtonListener()); 
     aboutPanel.add(aboutback);
     add(aboutPanel);
     
     */
    
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
    start.addActionListener(new ButtonListener(start)); 
    howto.addActionListener(new ButtonListener(howto));
    about.addActionListener(new ButtonListener(about));
    home.add(titleLabel, BorderLayout.PAGE_START); 
    buttons.add(start);
    buttons.add(howto); 
    buttons.add(about);
    home.add(buttons, BorderLayout.CENTER); 
    add(home); 
  }
  
  
}
  
  /*private JPanel makeHowTo() { 
   JPanel panel = new JPanel(); 
   JLabel hi = new JLabel("hi"); 
   panel.add(hi); 
   return panel; 
   }
   */
  
  
  
  