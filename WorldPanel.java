// Alice Wong and Karina Chan
// Assignment 4
// 10/06/13

//********************************************************************
//  AddSchoolPanel.java       Java Foundations
//
//  Purpose: To add schools into the database where the user can later evaluate in the next tab.
//********************************************************************


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class WorldPanel extends JPanel {
  
  private JLabel inputLabel, acNameLabel, reNameLabel, pubNameLabel, schoolNameLabel; 
  private JTextField schoolName;
  private JPanel inputFieldsPanel, schoolDisplayPanel, allschoolsPanel;
  private JComboBox academicDrop, researchDrop, publicationsDrop; 
  private JButton addSchoolButton;
  private GradSchools grad;
  private JTextArea schoolInfoTextArea, allSchoolsTextArea;
  private JScrollPane scrollPane;
  
  public AddSchoolPanel(GradSchools g)
  {
    this.grad = g; //gets the grad school array
    setLayout (new BorderLayout()); //sets the layout
    setBackground (new Color (175, 176, 185)); //sets the background color
    
    TempListener listener = new TempListener(); //temp listener
    String [] ratingNums= {"0","1","2","3","4","5"};  //strings for the numbers in the drop down box
    
    inputLabel = new JLabel ("Fill in the information to add a school, then click 'Add School'."); //Create a new label for instructions
    inputLabel.setFont(new Font("Rockwell", Font.PLAIN, 14)); //changes the font
    inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //aligns the text to the center
    
    // adds the instructions at the top
    add (inputLabel, BorderLayout.PAGE_START);
    
    addSchoolButton = new JButton("Add School"); //button for add school
    addSchoolButton.setFont(new Font("Rockwell", Font.PLAIN, 12));
    addSchoolButton.addActionListener(listener);
    
    //panel with the input parameters for the school info
    inputFieldsPanel= new JPanel(new GridLayout(9,1));
    inputFieldsPanel.setBackground (new Color (220, 226, 255));
    

    
    schoolNameLabel= new JLabel("School name: "); //label for name of school
    schoolNameLabel.setFont(new Font("Rockwell", Font.PLAIN, 12));
    schoolName = new JTextField (5); //creates the text field for the box
    schoolName.addActionListener(listener); //adds the listener
    

    
    //creating the dropdown menus
    acNameLabel = new JLabel ("Academic rating: ");
    acNameLabel.setFont(new Font("Rockwell", Font.PLAIN, 12));
    academicDrop= new JComboBox(ratingNums);
    reNameLabel = new JLabel ("Research rating: ");
    reNameLabel.setFont(new Font("Rockwell", Font.PLAIN, 12));
    researchDrop= new JComboBox(ratingNums);
    pubNameLabel= new JLabel("Publications rating: ");
    pubNameLabel.setFont(new Font("Rockwell", Font.PLAIN, 12));
    publicationsDrop= new JComboBox(ratingNums);
    
    
    //creates an add school button
    addSchoolButton = new JButton ("Add School");
    addSchoolButton.setFont(new Font("Rockwell", Font.PLAIN, 12));
    addSchoolButton.addActionListener(listener);
    
    //adding the parameter fields to the panel "inputFieldsPanel"
    inputFieldsPanel.add(schoolNameLabel);
    inputFieldsPanel.add (schoolName);
    inputFieldsPanel.add(acNameLabel);
    inputFieldsPanel.add(academicDrop);
    inputFieldsPanel.add(reNameLabel);
    inputFieldsPanel.add(researchDrop);
    inputFieldsPanel.add(pubNameLabel);
    inputFieldsPanel.add(publicationsDrop);
    inputFieldsPanel.add(addSchoolButton);
    add(inputFieldsPanel);
    
    //info displayed at the bottom
    schoolInfoTextArea = new JTextArea ("Information on the new school will appear here.");
    
    //makes it so that the text will automatically wrap as opposed to breaking words in half
    schoolInfoTextArea.setLineWrap(true);
    schoolInfoTextArea.setOpaque(false);
    schoolInfoTextArea.setWrapStyleWord(true);
    
    schoolDisplayPanel= new JPanel(); // new panel for the school information on the right
    
    schoolDisplayPanel.add(new JSeparator (JSeparator.VERTICAL), BorderLayout.LINE_START);
    schoolDisplayPanel.add (schoolInfoTextArea,BorderLayout.CENTER);
    schoolDisplayPanel.setPreferredSize(new Dimension(200,150));
    add(schoolDisplayPanel,BorderLayout.LINE_END);
    
    
    //creates a panel that will only show up when schools are entered 
    allschoolsPanel= new JPanel();
    allSchoolsTextArea = new JTextArea("");
    
    allSchoolsTextArea.setOpaque(false);
    allschoolsPanel.add(allSchoolsTextArea);
    scrollPane = new JScrollPane(allSchoolsTextArea); //scroll bar to preserve window shape
    scrollPane.setPreferredSize(new Dimension (200, 100));
    
    add(allschoolsPanel, BorderLayout.LINE_START);
    
    
    
  }
  private class TempListener implements ActionListener
  {
    
    public void actionPerformed (ActionEvent event)
    {
      
      if(event.getSource() == addSchoolButton){ //if button is clicked
        String text = schoolName.getText(); //get the school name that was inputted
        int academicRating=academicDrop.getSelectedIndex(); //get the ratings from the drop down menus
        int researchRating=researchDrop.getSelectedIndex();
        int publicationsRating= publicationsDrop.getSelectedIndex();
        text = (text+ "\n********** Academics Rating: "+ academicRating+" Research Rating: "+researchRating+
                " Publications Rating: "+publicationsRating); //set the text so that the user knows the info has been received
        schoolInfoTextArea.setText(text);
        
        grad.addSchool(schoolName.getText(), (academicRating), (researchRating), (publicationsRating)); //add the school into the grad schools array
        
        add(scrollPane, BorderLayout.LINE_START); //create the scroll pane in the line_start area every time clicked (refreshes)
        
        String all = "Schools entered:\n"; //loops to generate the list of schools 
        for(int i = 0; i < grad.numSchools; i++) {
          all = all+ (i+1) + ". "+ grad.schools[i].getName() + "\n";  //done by numSchools as opposed to the length of the school, due to previous erroring
        }
        allschoolsPanel.setPreferredSize(new Dimension(200, 100)); //setting the preferred size 
        allSchoolsTextArea.setText(all);
      } 
      
      
    }
  }
  
}

