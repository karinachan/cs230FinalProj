import java.util.*; 

public class Pokemon { 
  private String name;
  private String nickName;
  private int hp; 
  private int atk; 
  private int spd; 
  private String trainer; 
  
  public Pokemon (String name, String nickName, String trainer, int hp, int atk, int spd) { 
    Random rand = new Random();
    this.name=name; 
    this.nickName=nickName; 
    this.trainer=trainer; 
    this.hp=hp; 
    this.atk=atk; 
    this.spd=spd; 
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
    String s="Pokemon: " + name + 
      "\nNickname: " + nickName +
      "\nTrainer: " + trainer + 
      "\nHP: " + hp + 
      "\nAttack: " + atk + 
      "\nSpeed: " + spd; 
    return s; 
  }
  
  public static void main (String[] args) { 
    Pokemon p = new Pokemon ("Buneary", "Angelica", "Lyn", 100, 20, 50); 
    System.out.println(p); 
  }
  
}
