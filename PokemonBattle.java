import java.util.*;
import javafoundations.*;
import javafoundations.exceptions.*;
import javax.swing.*; 
import java.io.*;
import java.awt.*; 
import javax.imageio.*; 

public class PokemonBattle {
  final int loss=0; //the value of your health when you lose
  private Pokemon p1; //the self
  private Pokemon p2; //the professor
  /** Creates a pokemon game object that contains the variables associated with a game.
    */  
  private boolean youWin; //win is true 
  //private LinkedQueue visited; //the visited professors 
  private int origHP;
  private LinkedQueue<String> attackStats; 
  private String battleStatus; 
  private boolean hasWonYet;
  
  public PokemonBattle (Pokemon self, Pokemon opp) {
    hasWonYet=false;
    battleStatus="You've entered "+ opp.getTrainer() +"'s \nclassroom.\nPress 'A' to begin fighting!"; 
    p1=self;
    origHP=p1.getHP();
    p2=opp;
    youWin = false;
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
  
  public Pokemon playPokemonBattle() { //assuming p1 is YOU.
    System.out.println("1"); 
    Pokemon winner;  
    String battleMessage="Battle between " + p1.getTrainer() + "'s " + p1.getName() + ", " + p1.getNickName() + 
      ", and " + p2.getTrainer() + "'s " + p2.getName() + ", " + p2.getNickName() + ", begins!\n"; 
    attackStats.enqueue(battleMessage); 
    if (!hasWonYet){
      System.out.println("2"); 
      do {
        System.out.println("P1 exists YAY" + p1);           
        System.out.println("P2 exists YAY" + p2); 
        if (p1.getSPD()>=p2.getSPD()){
          p1.attack(p2);
          attackStats.enqueue(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!\n");
          attackStats.enqueue(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!\n");
          
          if (p1.getHP()<=loss || p2.getHP()<=loss) break; //so that if one becomes negative from the first attack, leave the if statement and check
          //to exit
          p2.attack(p1); 
          attackStats.enqueue(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!\n");
          attackStats.enqueue(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!\n");
          
        }
        else {
          p2.attack(p1);
          attackStats.enqueue(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!\n");
          attackStats.enqueue(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!\n");
          
          if (p1.getHP()<=loss || p2.getHP()<=loss) break; //check again and leave if it is
          p1.attack(p2);
          
          attackStats.enqueue(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!\n");
          attackStats.enqueue(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!\n");
          
        }
      } while (p1.getHP()>loss && p2.getHP()>loss);
      System.out.println("3"); 
      if (p1.getHP()<=loss){ //if the health is less than or equal to 0
        //System.out.println("MIME WINS");
        winner= p2; 
        youWin=false;
      }
      else {  //if greater than 0, you win! 
        winner= p1; //put the win battle here 
        youWin=true;
        //hasWonYet=true;
      }
      resultBattle(p2);
    } else {
      battleStatus= "You've beaten my class already!\n To the next one!"; 
      winner=p1;
      
    }
    return winner;
  }
  
  public boolean hasWonYet(){
    return hasWonYet;
  }
  
  private void resultBattle(Pokemon p2){ 
    Random rand = new Random();
      if (p1.getVisitSize()==0 || !p1.getVisit().peek().equals(p2)) {
        p1.getVisit().push(p2); //always counts as visited, no matter what the result
      }
    
      if (youWin){ //if you win
        p1.getWon().push(p2); //add your opponent
        //adding some moving onto the next room using the classroom graph   
      p1.setATK(rand.nextInt(30)); //atk increases at a random value
      attackStats.enqueue(p1.getNickName() + "'s base attack has increased to " + p1.getATK() + "!\n"); 
      attackStats.enqueue(p1.getNickName() + "'s HP has increased to " + p1.getHP() + "!\n"); 
      p1.setHP(origHP+rand.nextInt(30)); //hp increases at a random value between (30-50)
      battleStatus= "You've beaten "+ p2.getTrainer()+"'s class! To the next professor!";
      hasWonYet=true;
    } else {
      p1.setHP(origHP/2); //the result of your battle if you lose is just half health, so you don't die
      //System.out.println("hi");
      battleStatus= "Come back again after refueling! I'll be in my office all day."; 
      hasWonYet=false;
    } 
    //return p1.getWon();
  }
  
  public String getStatus(){
    return battleStatus;
  }
  
  
  /*
   public String toString() { 
   String s=p1.getTrainer()+"'s "+ p1.getNickName() + " is fighting against " + p2.getTrainer()+"'s "+ p2.getNickName() + ".\n"; 
   s+=playPokemonBattle(p1, p2).getNickName() + " wins!";
   //   String s=p2.getTrainer() + " wants to fight!" +
   //    p2.getTrainer() + " sent out " + p2.getNickName() + "!" + 
   //    "Go! " + p1.getNickName() + "!"; 
   
   return s; 
   }
   */
  public LinkedQueue <String> getAttackStat(){
    return attackStats;
  }
  
  public String toString(){
    String intro=p1.getTrainer()+"'s "+ p1.getNickName() + " is fighting against " + p2.getTrainer()+"'s "+ p2.getNickName() + ".\n"; 
    playPokemonBattle();
    LinkedQueue<String> temp= new LinkedQueue<String>();
    //System.out.println(attackStats);
    /*while (attackStats.size()>0){
     String element= attackStats.dequeue();
     intro+= element + "\n";
     temp.enqueue(element);
     }
     attackStats=temp;
     */
    intro+= playPokemonBattle().getNickName() + " wins!";
    
    return intro;
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

