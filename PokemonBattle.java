/** Karina Chan and Laura Zeng
  * CS 230 
  * Final Project
  * PokemonBattle.java
  * 
  * This class represents a Pokemon Battle. 
  * 
  * 
  */
import java.util.*;
import javafoundations.*;
import javafoundations.exceptions.*;
import javax.swing.*; 
import java.io.*;
import java.awt.*; 
import javax.imageio.*; 

public class PokemonBattle {
  final int LOSS=0; //the value of your health when you lose
  private Pokemon p1; //the self
  private Pokemon p2; //the professor
  private boolean youWin; //win is true 
  private int origHP;
  private LinkedQueue<String> attackStats; //queue that we use in PokeWorld
                                           //to represent the events of the battle
  private String battleStatus; 
  private boolean hasWonYet;
  
  public PokemonBattle (Pokemon self, Pokemon opp) {
    hasWonYet=false;
    //initial status is that you've just entered a class
    battleStatus="You've entered "+ opp.getTrainer() + 
      "'s \nclassroom.\nPress 'A' to begin fighting!"; 
    p1=self;
    origHP=p1.getHP(); //saves the original HP
    p2=opp;
    youWin = false; //haven't won yet
    attackStats= new LinkedQueue <String> (); 
    try { 
      if (self.getWon().peek().equals(opp)) {
        hasWonYet=true;
        battleStatus= "Beaten already, leave now.";
        //System.out.println(battleStatus);
      }
    } catch (EmptyCollectionException e){
    }
  }
  
  /** represents a Pokemon battle between p1 and p2
    * the Pokemon go back and forth attacking each other (subtract atk of attacker
    * from the attacked Pokemon's hp) until one has an hp that is <0, which 
    * determines the winner
    */
  public void playPokemonBattle() { //assuming p1 is YOU.
    Pokemon winner;  
    String battleMessage="Battle between " + p1.getTrainer() + "'s " + p1.getName() + ", " + p1.getNickName() + 
      ", and " + p2.getTrainer() + "'s " + p2.getName() + ", " + p2.getNickName() + ", begins!\n"; 
    attackStats.enqueue(battleMessage); 
    if (!hasWonYet){ //if you haven't won yet
      do {
        //the fast Pokemon goes first (if it's a tie, you go first)
        if (p1.getSPD()>=p2.getSPD()){
          p1.attack(p2);
          attackStats.enqueue(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!\n");
          attackStats.enqueue(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!\n");
          if (p1.getHP()<=LOSS || p2.getHP()<=LOSS) break; //so that if one becomes negative from the first attack, 
          //leave the if statement and check to exit
          p2.attack(p1); 
          attackStats.enqueue(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!\n");
          attackStats.enqueue(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!\n");
        } else {
          p2.attack(p1);
          attackStats.enqueue(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!\n");
          attackStats.enqueue(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!\n");
          if (p1.getHP()<=LOSS || p2.getHP()<=LOSS) break; //check again and leave if it is
          p1.attack(p2);
          attackStats.enqueue(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!\n");
          attackStats.enqueue(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!\n");          
        }
        //keep going as long as no one has lost
      } while (p1.getHP()>LOSS && p2.getHP()>LOSS);
      if (p1.getHP()<=LOSS){ //if the health is less than or equal to 0, you lose
        winner= p2; 
        youWin=false;
      } else {  //if greater than 0, you win! 
        winner= p1; //put the win battle here 
        youWin=true;
      }
      resultBattle(p2);
    } else { //when you've won...
      battleStatus= "You've beaten my class already!\n To the next one!"; 
      winner=p1;
    }
  }
  
  /** get if you've won yet
    * @return hasWonYet
    */
  public boolean hasWonYet(){
    return hasWonYet;
  }
  
  /** handles the result of the PokeBattle
    * sets the visitList and wonList, changes the battleStatus, and 
    * changes the Pokemon's stats (increase if you won, decrease if you lost) 
    * @param p2     your opponent
    */
  private void resultBattle(Pokemon p2){ 
    Random rand = new Random();
    //if your visitList is empty, push p2
    //if your visitList doesn't already have p2, push p2
    //this prevents multiple pushes of the same prof
    if (p1.getVisitSize()==0 || !p1.getVisit().peek().equals(p2)) {
      p1.getVisit().push(p2); //always counts as visited, no matter what the result
    }
    if (youWin){ //if you win
      p1.getWon().push(p2); //add your opponent
      p1.setATK(rand.nextInt(30)); //atk increases at a random value (0-30)
      attackStats.enqueue(p1.getNickName() + "'s base attack has increased to " + p1.getATK() + "!\n"); 
      attackStats.enqueue(p1.getNickName() + "'s HP has increased to " + p1.getHP() + "!\n"); 
      p1.setHP(origHP+rand.nextInt(30)); //hp increases at a random value between (0-30)
      battleStatus= "You've beaten "+ p2.getTrainer()+"'s class! To the next professor!";
      hasWonYet=true;
    } else {
      p1.setHP(origHP/2); //the result of your battle if you lose is just half health, so you don't die
      battleStatus= "Come back again after refueling! I'll be in my office all day."; 
      hasWonYet=false;
    } 
  }
  
  /** gets the battleStatus
    * @return battleStatus
    */
  public String getStatus(){
    return battleStatus;
  }
  
  /** gets attackStats
    * @return attackStats
    */
  public LinkedQueue <String> getAttackStat(){
    return attackStats;
  }
  
  /** String representation of battle
    * @return s
    */
  public String toString(){
    String s=p1.getTrainer()+"'s "+ p1.getNickName() + " is fighting against " + 
      p2.getTrainer()+"'s "+ p2.getNickName() + ".\n"; 
    playPokemonBattle();
    s+="You have won?: " + youWin; 
    return s;
  }
  
  public static void main (String[] args) throws IOException { 
    Image sprite1 = ImageIO.read(new File("prof1.png"));
    
    Pokemon lyn = new Pokemon ("Buneary", "Angelica", "Lyn", new Image[]{sprite1}); 
    //System.out.println(lyn);
    Pokemon rhys = new Pokemon ("Mr. Mime", "Mime", "Rhys", new Image[]{sprite1}); 
    //System.out.println(rhys);
    PokemonBattle fight1 = new PokemonBattle(lyn, rhys);
    fight1.playPokemonBattle(); 
    /*System.out.println(fight1.getStatus());
     System.out.println("***\n"+fight1); 
     System.out.println(lyn);
     System.out.println(lyn.getWon());
     System.out.println(fight1.getStatus());
     PokemonBattle fight2= new PokemonBattle(lyn, rhys); //will return a statement in the interpretor
     System.out.println(lyn.getWon());
     System.out.println(fight2.getStatus());
     */
  }
}

//if lose, then set the pokemon obj (if you lost) back a node on the tree of opponents
//set back node in the land world also
//increment the win for the pokemon 
//increment number of people you've visited 

