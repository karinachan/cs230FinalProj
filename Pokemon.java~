import java.util.*; 

public class Pokemon { 
  private String name;
  private String nickName;
  private int hp; 
  private int atk; 
  private int spd; 
  private String trainer;
  private ProfessorTree <Pokemon> fam; 
  
  
  public Pokemon (String name, String nickName, String trainer) { 
    Random rand = new Random();
    
    this.name=name; 
    this.nickName=nickName; 
    this.trainer=trainer; 
    this.hp=rand.nextInt(200)+200; //random between 200-400; ; 
    this.atk=rand.nextInt(50)+50; //random between 50-100; ; 
    this.spd=rand.nextInt(50)+50; //random between 50-100; ;
  }
  
  public Pokemon (String name, String nickname, String trainer, int spd){
    this(name, nickname, trainer);
    Random rand = new Random();
    
    //rewrites the previous numbers in the constructors 
    this.hp= rand.nextInt(400)+400; //random between 400-800
    this.atk= rand.nextInt(80)+80; //random between 80-160
    this.spd= spd; //whatever is entered. done this way so that spd can be indicated, KBot can have the higher stats. 
    //perhaps after beating all the professors, you have a chance to level up. (recreate the character...?) 
  
  }
  
  public ProfessorTree<Pokemon> createOpponents(){
    Pokemon kBot= new Pokemon("Mewtwo", "Wendy","KBot", 100);
    Pokemon randy= new Pokemon("Dewgong", "CS240","Randy");
    Pokemon ellen= new Pokemon("Nidoqueen", "CS Dept Chair","Ellen");
    Pokemon scott= new Pokemon("Diglett", "Doge","Scott");
    Pokemon lyn= new Pokemon("Eevee", "Angelica","Lyn");
    Pokemon rhys= new Pokemon("Mr. Mime", "Scheme","Rhys");
    Pokemon darakhshan= new Pokemon("Abra", "Security","Darakhshan");
    Pokemon eni= new Pokemon("Ekans", "Python","Eni");
    Pokemon rita= new Pokemon("Oddish", "SoOdd","Rita");
    Pokemon takis= new Pokemon("Wortortle", "Captain Guy","Takis");
    Pokemon stella= new Pokemon("Seadra", "Ocean","Stella");
    Pokemon sohie= new Pokemon("Lapras", "Lucker","Sohie");
    Pokemon brian= new Pokemon("Dratini", "Dracomp","Brian");
    Pokemon jean= new Pokemon("Zapdos", "Gates","Jean");
    Pokemon orit= new Pokemon("Porygon", "HCI","Orit");
    
    
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
  
  
  //this is the attacking pokemon, and p is the pokemon being attacked
  //this produces a String that is used in attack() in PokemonBattle so 
  //that the user is able to track the progress of the battle
  //jk, no need for Strings
  public void attack(Pokemon p) {
    //String s=this.getNickName() + " attacked " + p; 
    //int pre_hp=p.hp; 
    p.hp-=this.atk; 
    //s+=p + "'s HP dropped from " + pre_hp + " to " + p.hp + "."; 
    //return s; 
  }
  
  
  
  public String toString() { 
    String s="\n****"+ "\nPokemon: " + name + 
      "\nNickname: " + nickName +
      "\nTrainer: " + trainer + 
      "\nHP: " + hp + 
      "\nAttack: " + atk + 
      "\nSpeed: " + spd; 
    return s; 
  }
  
  public static void main (String[] args) { 
    Pokemon p = new Pokemon ("Buneary", "Angelica", "Lyn"); 
    //System.out.println(p); 
    //p.createOpponents();
    System.out.println(p.createOpponents());
  }
  
}
