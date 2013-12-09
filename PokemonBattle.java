import java.util.*;
import javafoundations.*;
import javafoundations.exceptions.*;


public class PokemonBattle {
  final int loss=0; //the value of your health when you lose
  private Pokemon p1; //the self
  private Pokemon p2; //the professor
  /** Creates a pokemon game object that contains the variables associated with a game.
    */  
  private boolean result; //win is true 
  //private LinkedQueue visited; //the visited professors 
  
  private ProfessorTree <Pokemon> fam; 
  private int orighp;
  private Iterator<Pokemon> it; 
  private LinkedQueue<String> attackstats; 
  private String battleStatus; 
  private boolean hasWonYet;
  
  
  public PokemonBattle (Pokemon self, Pokemon opp) {
    hasWonYet=false;
    battleStatus="You've entered "+ opp.getTrainer() +"'s \nclassroom.\nPress 'A' to begin fighting!"; 
    
      p1=self;
      fam=self.createOpponents(); //creates the tree
      orighp=p1.getHP();
      p2=opp;
      result = false;
      attackstats= new LinkedQueue <String> (); 
      try { 
        if (self.getWon().peek().equals(opp)) {
          hasWonYet=true;
          battleStatus= "Beaten already, leave now.";
          //System.out.println(battleStatus);
        }
      } catch (EmptyCollectionException e){
      }
  }
  
  
//  public Pokemon playPokemonBattle(Pokemon p1, Pokemon p2) { //assuming p1 is YOU.
//    Pokemon winner; 
//    do {
//      if (p1.getSPD()>=p2.getSPD()){
//        p1.attack(p2);
//        /*System.out.println(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!");
//        System.out.println(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!");
//        */
//        if (p1.getHP()<=loss || p2.getHP()<=loss) break; //so that if one becomes negative from the first attack, leave the if statement and check
//        //to exit
//        p2.attack(p1); 
//        /*System.out.println(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!");
//        System.out.println(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!");
//        */
//      }
//      else {
//        p2.attack(p1);
//       /*System.out.println(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!");
//        System.out.println(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!");
//        */
//        if (p1.getHP()<=loss || p2.getHP()<=loss) break; //check again and leave if it is
//        p1.attack(p2);
//        /*
//        System.out.println(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!");
//        System.out.println(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!");
//        */
//      }
//    } while (p1.getHP()>loss && p2.getHP()>loss);
//    
//    if (p1.getHP()<=loss){ //if the health is less than or equal to 0
//      //System.out.println("MIME WINS");
//      winner= p2; 
//      result=false;} 
//    
//    else {  //if greater than 0, you win! 
//      winner= p1; //put the win battle here 
//      result=true;
//    }
//    
//    resultBattle();
//    return winner;
//      
//  }
//  
  
  
  
  public Pokemon playPokemonBattle() { //assuming p1 is YOU.
    System.out.println("1"); 
    Pokemon winner;  
    if (!hasWonYet){
      System.out.println("2"); 
      do {
          System.out.println("P1 exists YAY" + p1);           
          System.out.println("P2 exists YAY" + p2); 
        if (p1.getSPD()>=p2.getSPD()){
          p1.attack(p2);
          attackstats.enqueue(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!\n");
          attackstats.enqueue(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!\n");
          
          if (p1.getHP()<=loss || p2.getHP()<=loss) break; //so that if one becomes negative from the first attack, leave the if statement and check
          //to exit
          p2.attack(p1); 
          attackstats.enqueue(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!\n");
          attackstats.enqueue(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!\n");
          
        }
        else {
          p2.attack(p1);
          attackstats.enqueue(p2.getNickName()+ " had ATTACK OF "+p2.getATK()+"!\n");
          attackstats.enqueue(p1.getNickName()+ " now has HP OF "+p1.getHP()+"!\n");
          
          if (p1.getHP()<=loss || p2.getHP()<=loss) break; //check again and leave if it is
          p1.attack(p2);
          
          attackstats.enqueue(p1.getNickName()+ " had ATTACK OF "+p1.getATK()+"!\n");
          attackstats.enqueue(p2.getNickName()+ " now has HP OF "+p2.getHP()+"!\n");
          
        }
      } while (p1.getHP()>loss && p2.getHP()>loss);
      System.out.println("3"); 
      if (p1.getHP()<=loss){ //if the health is less than or equal to 0
        //System.out.println("MIME WINS");
        winner= p2; 
        result=false;
      } 
      
      else {  //if greater than 0, you win! 
        winner= p1; //put the win battle here 
        result=true;
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
  
  private ArrayStack resultBattle(Pokemon p2){ 
    Random rand = new Random();
    if (result){ //if you win
      p1.getWon().push(p2); //add your opponent
      //adding some moving onto the next room using the classroom graph      
      p1.setHP(orighp+rand.nextInt(50)+50); //hp increases at a random value between (50-100)
      p1.setATK(rand.nextInt(50)+50); //atk increases at a random value
      battleStatus= "You've beaten "+ p2.getTrainer()+"'s class! To the next professor!";
      hasWonYet=true;
    } else {
      p1.setHP(orighp/2); //the result of your battle if you lose is just half health. 
      //System.out.println("hi");
      battleStatus= "Come back again after refueling! I'll be in my office all day."; 
      hasWonYet=false;
    } 
    return p1.getWon();
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
    return attackstats;
  }
  
  public String toString(){
    String intro=p1.getTrainer()+"'s "+ p1.getNickName() + " is fighting against " + p2.getTrainer()+"'s "+ p2.getNickName() + ".\n"; 
    playPokemonBattle();
    LinkedQueue<String> temp= new LinkedQueue<String>();
    //System.out.println(attackstats);
    /*while (attackstats.size()>0){
     String element= attackstats.dequeue();
     intro+= element + "\n";
     temp.enqueue(element);
     }
     attackstats=temp;
     */
    intro+= playPokemonBattle().getNickName() + " wins!";
    
    return intro;
  }
  
  
  
  public static void main (String[] args) { 
    Pokemon lyn = new Pokemon ("Buneary", "Angelica", "Lyn"); 
    //System.out.println(lyn);
    Pokemon rhys = new Pokemon ("Mr. Mime", "Mime", "Rhys"); 
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

