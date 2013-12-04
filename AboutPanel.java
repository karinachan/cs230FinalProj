
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class AboutPanel extends JPanel {
  
  public JPanel aboutPanel; 
  private JButton aboutback;
  
  
  
  
  public AboutPanel(){
    //System.out.println("HERE");
    aboutPanel = new JPanel(new FlowLayout());
    JLabel hi2= new JLabel("CABBAGE");
    
    aboutPanel.add(hi2); 
    aboutback = new JButton ("Back"); 
    
    //aboutback.addActionListener(new ButtonListener(aboutback)); 
    aboutPanel.add(aboutback);
    //aboutPanel.setVisible(false); //hidden until pressed
    
    //add(aboutPanel);
  }
  
  public JPanel getPanel(){
    return aboutPanel;
  }
  
   public JButton getButton(){
     return aboutback;
   }
  
   /*
  private class ButtonListener implements ActionListener {
    
    
    
    private JButton set;
    private LayoutPanel layout;
    
    public ButtonListener(JButton button){
      
      set=button;
    }
    
    
    
    public void actionPerformed(ActionEvent event){
      //FOR THE ADD SCHOOL BUTTON
      if (event.getSource()==set){
        layout.getPanel().setVisible(true);
        aboutPanel.setVisible(false);
        
        
        
      } 
      
      
    }
  }
  
  public static void main(String args[]){
    JFrame frame = new JFrame ("Test");
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);    
    frame.setSize(new Dimension(500,400)); 
    
    
    AboutPanel panel = new AboutPanel();
    JPanel aboutP= panel.getPanel();
    aboutP.setVisible(true);
    frame.getContentPane().add(panel.aboutPanel);
    
    
    //adds each individual panel on to the pane
    
    
    
    
    
    
//      frame.pack();
    frame.setVisible(true);
    frame.setResizable(false);
    
  }
  */
  
}
