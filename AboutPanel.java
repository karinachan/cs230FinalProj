
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class AboutPanel extends JPanel {
  
  JPanel aboutPanel; 
  JButton aboutback;
 
  
  public AboutPanel(){
    
    aboutPanel = new JPanel(new FlowLayout());
    JLabel hi2= new JLabel("CABBAGE");
    aboutPanel.setVisible(false); //hidden until pressed
    aboutPanel.add(hi2); 
    aboutback = new JButton ("Back"); 
    ButtonListener trying= new ButtonListener(aboutback);
    aboutback.addActionListener(trying); 
    aboutPanel.add(aboutback);
    add(aboutPanel);
    
  }
  
}