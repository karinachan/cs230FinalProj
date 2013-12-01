import java.util.*;

public class PokemonBattle {
 final int loss=0; //the value of your health when you lose
 private Pokemon p1; //the self
 private Pokemon p2; //the professor
  /** Creates a pokemon game object that contains the variables associated with a game.
  */  
 private boolean result; //win is true 
 //private LinkedQueue visited; //the visited professors 
 private ArrayStack visited; 
 
  public PokemonBattle (Pokemon self, Pokemon opp) {
    p1=self;
    p2=opp;
    result = false;
    visited= new ArrayStack[1]; //no one in this stack yet. 
  }

  public Pokemon playPokemonBattle(Pokemon p1, Pokemon p2) {
    do {
      if (p1.getSPD()>=p2.getSPD()){
        p1.attack(p2);
        //System.out.println(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!");
        //System.out.println(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!");
        if (p1.getHP()<=loss && p2.getHP()<=loss) break; //so that if one becomes negative from the first attack, leave the if statement and check
        //to exit
        p2.attack(p1); 
        //System.out.println(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!");
        //System.out.println(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!");
        
      }
      else {
        p2.attack(p1);
       // System.out.println(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!");
        //System.out.println(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!");
        if (p1.getHP()<=loss && p2.getHP()<=loss) break; //check again and leave if it is
        p1.attack(p2);
        //System.out.println(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!");
        //System.out.println(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!");
      }
    } while (p1.getHP()>loss && p2.getHP()>loss);
      
    if (p1.getHP()==loss){ //returns the winner
      //a win battle method here 
      return p2;} 
    else{
      result= true;
      return p1;} //put the lost battle here 
    
  }
  
  
  private T lostBattle(boolean res){ //if p1 lost
    if (res){ //if you lose
      remove a node from the professor tree //go back a node
        return the top of the queue; //rebattle 
    } 
    else {
      return top of the queue; 
    } 
  }
    
    */
  
  
  
  public String toString() { 
   String s=p1.getTrainer()+"'s "+ p1.getNickName() + " is fighting against " + p2.getTrainer()+"'s "+ p2.getNickName() + ".\n"; 
   s+=playPokemonBattle(p1, p2).getNickName() + " wins!";
//   String s=p2.getTrainer() + " wants to fight!" +
//    p2.getTrainer() + " sent out " + p2.getNickName() + "!" + 
//    "Go! " + p1.getNickName() + "!"; 
   
   return s; 
  }
  
  
  
  public static void main (String[] args) { 
   Pokemon lyn = new Pokemon ("Buneary", "Angelica", "Lyn"); 
   System.out.println(lyn);
   Pokemon rhys = new Pokemon ("Mr. Mime", "Mime", "Rhys"); 
   System.out.println(rhys);
   PokemonBattle fight = new PokemonBattle(lyn, rhys);
   System.out.println("***\n"+fight); 
  }
}
  //if lose, then set the pokemon obj (if you lost) back a node on the tree of opponents
  //set back node in the land world also
  //increment the win for the pokemon 
  //increment number of people you've visited 
  
