private class ButtonListener implements ActionListener {
  
  public ButtonListener(){
    
  } 
  
  
  public void actionPerformed(ActionEvent event){
    //FOR THE ADD SCHOOL BUTTON
    
    if (event.getSource() == start) {
      System.out.println("Start"); 
      gamePanel.setVisible(true);
      home.setVisible(false);
      //launch the game
    } else if (event.getSource() == howto) { 
      System.out.println("How To"); 
      
      
      instrpanel.setVisible(true);
      home.setVisible(false);
      //this.getContentPane().add(makeHowTo());
      
      
      
      //home=makeHowTo();
      
      //go to howto page
    } else if (event.getSource() == about) { 
      System.out.println("About"); 
      aboutPanel.setVisible(true);
      home.setVisible(false);
      //go to about page
    }
    else if (event.getSource() == aboutback) {
      System.out.println("Return");
      home.setVisible(true);
      aboutPanel.setVisible(false);
      
      
    } else if (event.getSource() == gameback) {
      System.out.println("Return");
      home.setVisible(true);
      gamePanel.setVisible(false);
      
      
    } else if (event.getSource() == instrback) {
      System.out.println("Return");
      home.setVisible(true);
      instrpanel.setVisible(false);
      
      
    }
    
  }
}

/*  
 //*****************************************************************
 //  Represents the listener for all three sliders.
 //*****************************************************************
 private class SliderListener implements ChangeListener
 {
 private int academicWeight, researchWeight, publicationsWeight;
 
 //--------------------------------------------------------------
 //  Gets the value of each slider, then updates the labels and
 //  the color panel.
 //--------------------------------------------------------------
 public void stateChanged (ChangeEvent event)
 {
 academicWeight = academicsSlider.getValue(); //gets values from the slider
 researchWeight = researchSlider.getValue();
 publicationsWeight = publicationsSlider.getValue();
 
 
 
 }
 }*/
