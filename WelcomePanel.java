//Alice Wong and Karina Chan
//Assignment 4
//10/06/13

//********************************************************************
//  AboutPanel.java       Java Foundations
//
//  Represents the introduction panel for the GradLayout program.
//********************************************************************

import sun.audio.*; 
import java.net.*;
import java.io.*;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;



public class WelcomePanel extends JPanel
{ 
  
  public WelcomePanel()
  {
    setLayout(new BorderLayout()); //sets the layout to border layout
    setBackground (new Color (0, 0, 0)); //sets the color background
    
    //creates labels with text on them
    
    JLabel picLabel = new JLabel("<html><img src='http://i.imgur.com/K4hCVzc.png'></html>");
    add (picLabel, BorderLayout.CENTER);
    
    picLabel.setBorder(new EmptyBorder( 0, 50, 50, 50 ) );
    JLabel l1 = new JLabel ("<html> <strong> <h1 style='color: white; margin-left: 25px; margin-right: 25px; margin-top: 25px; margin-bottom: 0px;'> WELCOME TO POKEMON: WELLESLEY CS EDITION </h1> </strong>"
                              +"<h2 style='color: white; margin-left: 25px; margin-right: 25px; margin-top: 5px; '>Follow the tabs in order to play. GET YOUR CS DEGREE.</h2>");
    l1.setFont(new Font("Rockwell", Font.PLAIN, 16));
    
    JLabel l2 = new JLabel ("------------------------");
    JLabel l3 = new JLabel ("<html><h3 style='color: white; margin-left: 25px; margin-right: 25px; margin-top: 5px; margin-bottom: 25px; '> Created by Karina Chan and Laura Zeng</h3></html>");
    l3.setFont(new Font("Rockwell", Font.PLAIN, 16));
   /* try {
      System.out.println("Play music!"); 
      URL song = new URL ("http://cs.wellesley.edu/~lzeng/Final%20Project/Pokemon%20Yellow%20Gym%20Leader%20Theme.wav"); 
      HttpURLConnection urlConn = (HttpURLConnection)song.openConnection(); 
      urlConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
      InputStream audioSrc = urlConn.getInputStream(); 
      DataInputStream read = new DataInputStream(audioSrc); 
      AudioStream as = new AudioStream(read); 
      AudioPlayer.player.start(as); 
    } catch (Exception e) { 
      System.out.println(e); 
      System.out.println("Music is not playing."); 
    }
    */
    
    //adds labels and a picture
    add (l1, BorderLayout.PAGE_START);
    add (l3, BorderLayout.PAGE_END);
    //add(new JSeparator (JSeparator.VERTICAL));
    
  }
}
