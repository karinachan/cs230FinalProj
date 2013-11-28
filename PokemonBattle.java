import java.util.*;

public class PokemonBattle
 implements Comparable<T> {
 final int loss=0; //the value of your health when you lose
 private Pokemon p1; //the self
 private Pokemon p2; //the professor
  /** Creates a pokemon game object that contains the variables associated with a game.
  */  
 
  public PokemonBattle
(Pokemon self, Pokemon opp) {
    p1=self;
    p2=opp;
    
  }

  public Pokemon playPokemonBattle
 (String name, int numRounds) {
    while (p1.getHP()!=loss && p2.getHP()!=loss){ 
      if (p1.getSPD()>=p2.getSPD()){
        p1.attack(p2);
      } else {
        p2.attack(p1);
      }
    } 
    if (p1.getHP()==loss){ //returns the winner
      return p2;} 
    else{
      return p1;}
    
  }
  
  public String toString() { 
   String s="hi"; 
   return s; 
  }
  
  public static void main (String[] args) { 
   String s=p2.getTrainer() + " wants to fight!" +
    p2.getTrainer() + " sent out " + p2.getNickName() + "!" + 
    "Go! " + p1.getNickName() + "!"; 
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
