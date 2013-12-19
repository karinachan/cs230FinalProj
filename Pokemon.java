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
  private ProfessorTree <Pokemon> fam; 
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
  
    public Image[] getSprites() { 
      return sprites; 
    }

  public ArrayStack getWon(){
    return wonList;
  }
  
  public int getWonSize(){
    return wonList.size();
  }
  
  public ArrayStack getVisit() { 
    return visitList; 
  }
  public int getVisitSize() { 
    return visitList.size(); 
  }
  
  
  
  public ProfessorTree<Pokemon> createOpponents() throws IOException {
    Image sprite1 = ImageIO.read(new File("prof1.png"));
    Image sprite2 = ImageIO.read(new File("prof2.png"));
    Image sprite3 = ImageIO.read(new File("prof3.png"));
    Image sprite4 = ImageIO.read(new File("prof4.png"));
    
    Pokemon kBot= new Pokemon("Mewtwo", "Wendy","KBot", 100, new Image[]{sprite3});
    Pokemon randy= new Pokemon("Dewgong", "CS240","Randy", new Image[]{sprite4});
    Pokemon ellen= new Pokemon("Nidoqueen", "CS Dept Chair","Ellen", new Image[]{sprite1});
    Pokemon scott= new Pokemon("Diglett", "Doge","Scott", new Image[]{sprite2});
    Pokemon lyn= new Pokemon("Eevee", "Angelica","Lyn", new Image[]{sprite3});
    Pokemon rhys= new Pokemon("Mr. Mime", "Scheme","Rhys", new Image[]{sprite4});
    Pokemon darakhshan= new Pokemon("Abra", "Security","Darakhshan", new Image[]{sprite1});
    Pokemon eni= new Pokemon("Ekans", "Python","Eni", new Image[]{sprite2});
    Pokemon rita= new Pokemon("Oddish", "SoOdd","Rita", new Image[]{sprite3});
    Pokemon takis= new Pokemon("Wartortle", "Captain Guy","Takis", new Image[]{sprite4});
    Pokemon stella= new Pokemon("Seadra", "Ocean","Stella", new Image[]{sprite1});
    Pokemon sohie= new Pokemon("Lapras", "Lucker","Sohie", new Image[]{sprite2});
    Pokemon brian= new Pokemon("Dratini", "Dracomp","Brian", new Image[]{sprite3});
    Pokemon jean= new Pokemon("Zapdos", "Gates","Jean", new Image[]{sprite4});
    Pokemon orit= new Pokemon("Porygon", "HCI","Orit", new Image[]{sprite1});

    
    
    fam= new ProfessorTree(kBot); //creates the hierarchy (changes every game)
    fam.setLeft(kBot, ellen); 
    fam.setRight(kBot, randy); 
    fam.setLeft(ellen, scott); 
    fam.setRight(ellen, lyn); 
    fam.setLeft(scott, rhys); 
    fam.setRight(scott, darakhshan); 
    fam.setLeft(lyn, eni); 
    fam.setRight(lyn, rita); 
    fam.setLeft(randy, takis); 
    fam.setRight(randy, orit); 
    fam.setLeft(takis, stella); 
    fam.setRight(takis, brian); 
    fam.setLeft(orit, jean); 
    fam.setRight(orit, sohie);  
    
    fam.shuffle(); 
    
    
    //System.out.println(fam);
    return fam;
  }
  
  public String getName() { 
    return name; 
  } 
  public String getNickName() { 
    return nickName; 
  }
  public String getTrainer() { 
    return trainer; 
  }
  public int getHP() { 
    return hp; 
  }
  public int getATK() { 
    return atk; 
  } 
  public int getSPD() { 
    return spd; 
  }
  
  public void setHP(int backup){
    this.hp = backup; //since the original will change (if you get here, you might get the value of the damaged one + bonus) 
  }
  
  public void setATK(int increase){
    this.atk= this.getATK()+increase;
  }
  
  //this is the attacking pokemon, and p is the pokemon being attacked
  //this produces a String that is used in attack() in PokemonBattle so 
  //that the user is able to track the progress of the battle
  //jk, no need for Strings
  public void attack(Pokemon p) {
    //String s=this.getNickName() + " attacked " + p; 
    //int pre_hp=p.hp; 
    //adds more randomness to the battle, making it more exciting
    Random rand = new Random(); 
    int crit = rand.nextInt(20);
    System.out.println("The crit is: " + crit); 
    int dmg = atk+crit; 
    p.hp-=dmg; 
    //s+=p + "'s HP dropped from " + pre_hp + " to " + p.hp + "."; 
    //return s; 
  }
  
  
  
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
    //System.out.println(p); 
    //p.createOpponents();
    System.out.println(p.createOpponents());
  }
  
}
