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
  public void attack(Pokemon p) { 
      p.hp-=this.atk; 
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