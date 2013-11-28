
import java.util.*;

public class PokemonBattle
 implements Comparable<T> {
 final int loss=0; //the value of your health when you lose
 private Pokemon p1; //the self
 private Pokemon professor; //the professor
  /** Creates a pokemon game object that contains the variables associated with a game.
  */  
 
  public PokemonBattle
(Pokemon self, Pokemon op) {
    p1=self;
    professor=op;
    
  }

  public Pokemon playPokemonBattle
 (String name, int numRounds) {
    while (p1.getHP()!=loss && professor.getHP()!=loss){ 
      if (p1.getSPD()>=professor.getSPD()){
        p1.attack(professor);
      } else {
        professor.attack(p1);
      }
    } 
    if (p1.getHP()==0){ //returns the winner
      return professor;} 
    else{
      return p1;}
    
  }
  
  //if lose, then set the pokemon obj (if you lost) back a node on the tree of opponents
  //set back node in the land world also
  //increment the win for the pokemon 
  //increment number of people you've visited 
  
  
  /** Start the homework by reading this method. 
    */
  public static void main (String args[]) {   
    // Create an instance of a new game and play the rounds
    
  }
}
