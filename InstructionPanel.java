
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class InstructionPanel{
  JPanel instrpanel;
  JButton instrback;
  public InstructionPanel(){
    JLabel hi= new JLabel("TOMATOES");
    instrpanel = new JPanel(new FlowLayout()); 
    instrpanel.setVisible(false); //hidden until pressed
    instrpanel.add(hi); 
    instrback = new JButton ("Back"); 
    instrback.addActionListener(new ButtonListener()); 
    instrpanel.add(instrback);
    add(instrpanel);
    
    
  }
  
}