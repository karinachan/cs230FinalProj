
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class GamePanel extends JPanel{
  JPanel gamePanel;
  JButton gameback;
  
  public GamePanel(){
    
    gamePanel = new JPanel(new BorderLayout());
    JLabel hi1= new JLabel("POTATOES");
    gamePanel.setVisible(false); //hidden until pressed
    gamePanel.add(hi1,BorderLayout.PAGE_START); 
    gameback = new JButton ("Back"); 
    gameback.addActionListener(new ButtonListener(gameback)); 
    gamePanel.add(gameback, BorderLayout.LINE_END);
    add(gamePanel);
    
  }
  
  
}