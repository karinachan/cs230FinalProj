
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class ExportPanel extends JPanel {
  
  public JPanel exportPanel; 
  private JButton exportback;
  
  
  
  
  public ExportPanel(){
    
    setLayout(new FlowLayout()); //sets the layout to border layout
    setBackground (new Color (255, 255, 255)); //sets the color background
    
    //creates labels with text on them
    JLabel l1 = new JLabel ("<html> <strong> YOU WON THE GAME:</strong><br>"
                              +"<br>Export your diploma if you wish! <br> *limited edition 2013 degree only*");
    l1.setFont(new Font("Rockwell", Font.PLAIN, 32));
   
    JLabel l2 = new JLabel ("------------------------");
    JLabel l3 = new JLabel ("Export your diploma!");
   
    //adds labels and a picture
    add (l1);
    //add(new JSeparator (JSeparator.VERTICAL), BorderLayout.LINE_START);
    
    JLabel picLabel = new JLabel("<html><img src='         '></html>");
    add (picLabel,BorderLayout.LINE_END);
    picLabel.setBorder(new EmptyBorder( 0, 0, 0, 50 ) );
    //System.out.println("HERE");
    
    /*exportPanel = new JPanel(new FlowLayout());
    JLabel hi2= new JLabel("CABBAGE");
    
    exportPanel.add(hi2); 
    exportback = new JButton ("Back"); 
    
    //exportback.addActionListener(new ButtonListener(exportback)); 
    exportPanel.add(exportback);
    //exportPanel.setVisible(false); //hidden until pressed
    
    //add(exportPanel);
    */
    
  }
  
  public JPanel getPanel(){
    return exportPanel;
  }
  
   public JButton getButton(){
     return exportback;
   }
  
  
  
}
