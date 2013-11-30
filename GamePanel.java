
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 

public class GamePanel extends JPanel{
  private JPanel gamePanel;
  private JButton gameback;
  private LayoutPanel layout;
   
  public GamePanel(){
    
    gamePanel = new JPanel(new BorderLayout());
    
    JLabel hi1= new JLabel("POTATOES");
    gamePanel.add(hi1,BorderLayout.PAGE_START); 
    gameback = new JButton ("Back"); 
    //gameback.addActionListener(new ButtonListener(gameback)); 
    gamePanel.add(gameback, BorderLayout.LINE_END);
    gamePanel.setVisible(false); //hidden until pressed
    
  }
  
   public JPanel getPanel(){
    return gamePanel;
  }
      
   public JButton getButton(){
     return gameback;
   }
   /*
  private class ButtonListener implements ActionListener {
  
  //LayoutPanel layout;
  
  JButton set;
  
  public ButtonListener(JButton button){

    
    set=button;
  }
  
  
  
  
  public void actionPerformed(ActionEvent event){
    //FOR THE ADD SCHOOL BUTTON
    if (event.getSource()==set){
      layout.setVisible(true);
      gamePanel.setVisible(false);
      
      
      
    } 
      /*
       if (event.getSource() == start) {
       System.out.println("Start"); 
       game.setVisible(true);
       layout.setVisible(false);
       //launch the game
       } else if (event.getSource() == howto) { 
       System.out.println("How To"); 
       
       
       instr.setVisible(true);
       layout.setVisible(false);
       //this.getContentPane().add(makeHowTo());
       
       
       
       //home=makeHowTo();
       
       //go to howto page
       } else if (event.getSource() == about) { 
       System.out.println("About"); 
       about.setVisible(true);
       layout.setVisible(false);
       //go to about page
       }
       else if (event.getSource() == aboutback) {
       System.out.println("Return");
       layout.setVisible(true);
       about.setVisible(false);
       
       
       } else if (event.getSource() == gameback) {
       System.out.println("Return");
       layout.setVisible(true);
       game.setVisible(false);
       
       
       } else if (event.getSource() == instrback) {
       System.out.println("Return");
       layout.setVisible(true);
       instr.setVisible(false);
       
       
       }
       */
      
    } 
  