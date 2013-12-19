
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class InstructionPanel extends JPanel{
  private JPanel instrpanel;
  private JButton instrback;
  private LayoutPanel layout;

  public InstructionPanel(){

    JLabel hi= new JLabel("TOMATOES");
    instrpanel = new JPanel(new FlowLayout()); 
    
    instrpanel.add(hi); 
    instrback = new JButton("Back"); 
    //instrback.addActionListener(new ButtonListener(instrback)); 
    instrpanel.add(instrback);
    
    instrpanel.setVisible(false); //hidden until pressed
    
  }
  
  public JPanel getPanel(){
    
    return instrpanel;
  }
  
   public JButton getButton(){
     return instrback;
   }
  /*
  private class ButtonListener implements ActionListener {
    
    // LayoutPanel layout;
    
    JButton set;
    
    public ButtonListener(JButton button){
      
      
      set=button;
    }
    
    
    
    
    public void actionPerformed(ActionEvent event){
      //FOR THE ADD SCHOOL BUTTON
      if (event.getSource()==set){
        instrpanel.setVisible(false);
        layout.getPanel().setVisible(true);
        
        
        
      } 
      
      
    }*/
  }
  
  
  
  
