import java.util.*;
import javafoundations.*;


public class PokemonBattle {
 final int loss=0; //the value of your health when you lose
 private Pokemon p1; //the self
 private Pokemon p2; //the professor
  /** Creates a pokemon game object that contains the variables associated with a game.
  */  
 private boolean result; //win is true 
 //private LinkedQueue visited; //the visited professors 
 private ArrayStack visited; 
 private ProfessorTree <Pokemon> fam; 
 private int orighp;
 private Iterator<Pokemon> it; 
 private LinkedQueue<String> attackstats; 
 
  public PokemonBattle (Pokemon self, Pokemon opp) {
    p1=self;
    fam=self.createOpponents(); //creates the tree
    orighp=p1.getHP();
    p2=opp;
    result = false;
    visited= new ArrayStack(); //no one in this stack yet.
    attackstats= new LinkedQueue <String> (); 
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
  
  
  
  public Pokemon playPokemonBattle(Pokemon p1, Pokemon p2) { //assuming p1 is YOU.
    Pokemon winner;  
    do {
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
    
    if (p1.getHP()<=loss){ //if the health is less than or equal to 0
      //System.out.println("MIME WINS");
      winner= p2; 
      result=false;} 
    
    else {  //if greater than 0, you win! 
      winner= p1; //put the win battle here 
      result=true;
    }
    
    resultBattle();
    return winner;
      
  }
  
  private ArrayStack resultBattle(){ 
    Random rand = new Random();
    if (result){ //if you win
      visited.push(p2); //add your opponent
      
      //adding some moving onto the next room using the classroom graph
      
      p1.setHP(orighp+rand.nextInt(50)+50); //hp increases at a random value between (50-100)
      p1.setATK(rand.nextInt(50)+50); //atk increases at a random value
      
    } 
    else {
      
      p1.setHP(orighp/2); //the result of your battle if you lose is just half health. 
      //System.out.println("hi");
    } 
    return visited;
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
    playPokemonBattle(p1, p2);
    LinkedQueue<String> temp= new LinkedQueue<String>();
    //System.out.println(attackstats);
    /*while (attackstats.size()>0){
      String element= attackstats.dequeue();
      intro+= element + "\n";
      temp.enqueue(element);
    }
    attackstats=temp;
    */
    intro+= playPokemonBattle(p1, p2).getNickName() + " wins!";
    
    return intro;
  }
  
  
  
  public static void main (String[] args) { 
   Pokemon lyn = new Pokemon ("Buneary", "Angelica", "Lyn"); 
   System.out.println(lyn);
   Pokemon rhys = new Pokemon ("Mr. Mime", "Mime", "Rhys"); 
   System.out.println(rhys);
   PokemonBattle fight = new PokemonBattle(lyn, rhys);
   System.out.println("***\n"+fight); 
   System.out.println(lyn);
  }
}

  //if lose, then set the pokemon obj (if you lost) back a node on the tree of opponents
  //set back node in the land world also
  //increment the win for the pokemon 
  //increment number of people you've visited 
  
