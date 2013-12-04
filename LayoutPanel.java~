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
  private InstructionPanel ip;
  private AboutPanel ap;
  private GamePanel gp;
  
  public LayoutPanel()
  {
    home = new JPanel(new BorderLayout()); 
    home.setBorder(new EmptyBorder(20,20,20,20));
    
    //create the hidden panels 
    ip= new InstructionPanel(); //creates the panels
    ap= new AboutPanel();
    gp= new GamePanel();
    
    ap.getButton().addActionListener(new ButtonListener());
    gp.getButton().addActionListener(new ButtonListener());
    ip.getButton().addActionListener(new ButtonListener());
    
    //add to home
    add(ip.getPanel()); //adding the individual panels to the home
    add(ap.getPanel());
    add(gp.getPanel());
    
    //create the content on home
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
    //add buttons to button panel
    buttons.add(start);
    buttons.add(howto); 
    buttons.add(about);
    
    //add button panel to home
    home.add(buttons, BorderLayout.CENTER); 
    
    //add home to layout panel
    add(home); 
  }
  
  public JPanel getPanel(){
    return home;
  }
  
  private class ButtonListener implements ActionListener {
    /*
     AboutPanel ap;
     InstructionPanel ip;
     GamePanel gp;
     */
    JButton set;
    
    /*
     public ButtonListener(JButton button){
     
     set=button;
     }
     
     */
    JPanel aboutPanel= ap.getPanel();
    JPanel instrPanel= ip.getPanel();
    JPanel gamePanel= gp.getPanel();
    JButton aboutButton= ap.getButton();
    JButton instrButton= ip.getButton();
    JButton gameButton= gp.getButton();
    
    public void actionPerformed(ActionEvent event){
      
      
      
      
      if (event.getSource() == start) {
        System.out.println("Start"); 
        gamePanel.setVisible(true);
        home.setVisible(false);
        
      } else if (event.getSource() == howto) { 
        System.out.println("How To"); 
        instrPanel.setVisible(true);
        home.setVisible(false);
        
      } else if (event.getSource() == about) { 
        System.out.println("About"); 
        aboutPanel.setVisible(true);
        home.setVisible(false);
        
      } else if (event.getSource()== aboutButton){
        System.out.println("Back");
        aboutPanel.setVisible(false);
        home.setVisible(true);
      } else if (event.getSource()== gameButton){
        System.out.println("Back");
        gamePanel.setVisible(false);
        home.setVisible(true);
      }  else if (event.getSource()== instrButton){
        System.out.println("Back");
        instrPanel.setVisible(false);
        home.setVisible(true);
      } 
      
      
      
      
    }
  }
  
  
  
}









