
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class InstructionPanel extends JPanel{


  public InstructionPanel(){
    setLayout(new FlowLayout()); //sets the layout to border layout
    setBackground (new Color (255, 255, 255)); //sets the color background
    
    //creates labels with text on them
    JLabel l1 = new JLabel ("<html> <strong> HOW TO PLAY:</strong><br>");
    l1.setFont(new Font("Rockwell", Font.PLAIN, 16));
   
    JLabel l2 = new JLabel ("1. Click the A button to interact with characters. <br>2. To battle, go up to the professor and press A."+
    "<br>3. If you win, a pop up will tell you so and you will progress to the next professor.<br>4. If you do not, rebattle the professor."+
    "<br>5. When you win, check the directory for a diploma that you can print out!");
   l2.setFont(new Font("Rockwell", Font.PLAIN, 10));
    add (l1);
    add (l2);

  }
  }
  
  
  
  
  
  
