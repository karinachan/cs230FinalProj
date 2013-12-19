/** Karina Chan and Laura Zeng
  * CS 230 
  * Final Project
  * Pokemon.java
  * 
  * Creates a Pokemon object, which represents the Pokemon and the trainer.
  * We chose to have both the Pokemon and the trainer in one class because the
  * trainer doesn't do anything except provide a name and a sprite, so it was
  * more or less just extra information tacked on to a Pokemon. 
  * Pokemon have a name (the actual Pokemon name), nickname (the name a trainer
  * chooses for the Pokemon), HP, Attack, Speed, and trainer. The trainer also 
  * comes with sprites that are later used to draw the Pokes (which are basically Buggles). 
  * It's important to have the sprites in the Pokemon class because Pokes are moving
  * containers for Pokemon and a new Poke is made each time (leading to random sprites for
  * the professors), while Pokemon (taken in as a parameter) remains constant. The Pokemon
  * also has a WonList (all the professors that it's won against) and a VisitList (all 
  * the professors that it's visited). 
  * 
  */

import java.util.*; 
import java.io.*;
import java.awt.*; 
import javax.imageio.*; 
import javafoundations.*;

public class Pokemon { 
  private String name;
  private String nickName;
  private int hp; 
  private int atk; 
  private int spd; 
  private String trainer;
  private ArrayStack wonList; //here so that every battle, the pokemon keeps same stats (easier to save also)
  private ArrayStack visitList; 
  private Image[] sprites; 
  
  public Pokemon (String name, String nickName, String trainer, Image[] imgs) { 
    Random rand = new Random();
    this.name=name; 
    this.nickName=nickName; 
    this.trainer=trainer; 
    this.hp=rand.nextInt(200)+200; //random between 200-400; ; 
    this.atk=rand.nextInt(50)+50; //random between 50-100; ; 
    this.spd=rand.nextInt(50)+50; //random between 50-100; ;
    wonList= new ArrayStack(); //no one in this stack yet.
    visitList = new ArrayStack();
    sprites = imgs; 
  }
  
  public Pokemon (String name, String nickName, String trainer, 
                  int hp, int atk, int spd, Image[] imgs) { 
    this(name, nickName, trainer, imgs);
    this.hp=hp;
    this.atk=atk; 
    this.spd=spd; 
    wonList= new ArrayStack(); //no one in this stack yet.
    visitList = new ArrayStack();
    sprites = imgs; 
    
  }
  public Pokemon (String name, String nickName, String trainer, int spd, Image[] imgs){
    this(name, nickName, trainer, imgs);
    Random rand = new Random();
    //rewrites the previous numbers in the constructors 
    this.hp= rand.nextInt(700)+800; //random between 700-1500
    this.atk= rand.nextInt(150)+150; //random between 150-300
    this.spd= spd; //whatever is entered. done this way so that spd can be indicated, KBot can have the higher stats. 
    //perhaps after beating all the professors, you have a chance to level up. (recreate the character...?) 
    sprites=imgs; 
  }
  
  /** gets the sprites
    * @return sprites
    */
  public Image[] getSprites() { 
    return sprites; 
  }
  
  /** gets the wonList
    * @return wonList
    */
  public ArrayStack getWon(){
    return wonList;
  }
  
  /** gets size of wonList
    * @return wonList.size()
    */
  public int getWonSize(){
    return wonList.size();
  }
  
  /** gets visitList
    * @return visitList
    */
  public ArrayStack getVisit() { 
    return visitList; 
  }
  
  /** gets size of visitList
    * @return visitList.size()
    */
  public int getVisitSize() { 
    return visitList.size(); 
  }
  
  /** gets name of Pokemon
    * @return name
    */
  public String getName() { 
    return name; 
  } 
  
  /** gets nickName of Pokemon
    * @return nickName
    */
  public String getNickName() { 
    return nickName; 
  }
  
  /** gets trainer of Pokemon
    * @return trainer
    */
  public String getTrainer() { 
    return trainer; 
  }
  
  /** gets HP of Pokemon
    * @return hp
    */
  public int getHP() { 
    return hp; 
  }
  
  /** gets attack of Pokemon 
    * @return atk
    */
  public int getATK() { 
    return atk; 
  } 
  
  /** gets speed of Pokemon 
    * @return spd
    */
  public int getSPD() { 
    return spd; 
  }
  
  /** sets HP of Pokemon to backup
    * @param backup
    */
  public void setHP(int backup){
    this.hp = backup; //since the original will change (if you get here, 
                      //you might get the value of the damaged one + bonus) 
  }
  
  /** sets attack of Pokemon to current attack + increase
    * @param increase    amount that your attack increases
    */
  public void setATK(int increase){
    this.atk= this.getATK()+increase;
  }
  
  /** simulates one Pokemon (this) attacking another (p)
    * crit is a random integer (1-20) that gets added on to Pokemon this's 
    * attack base stat (which allows for more varied gameplay)
    * @param p     your opponent
    */
  public void attack(Pokemon p) {
    Random rand = new Random(); 
    int crit = rand.nextInt(20);
    //System.out.println("The crit is: " + crit); 
    int dmg = atk+crit; 
    p.hp-=dmg; 
  }
  
  /** String representation of Pokemon 
    * @return s
    */
  public String toString() { 
    String s="\nPokemon: " + name + 
      "\nNickname: " + nickName +
      "\nTrainer: " + trainer + 
      "\nHP: " + hp + 
      "\nAttack: " + atk + 
      "\nSpeed: " + spd; 
    return s; 
  }
  
  public static void main (String[] args) throws IOException { 
    Image sprite1 = ImageIO.read(new File("prof1.png"));  
    Pokemon p = new Pokemon ("Buneary", "Angelica", "Lyn", new Image[]{sprite1}); 
    System.out.println(p); 
    //p.createOpponents();
  }
  
}
