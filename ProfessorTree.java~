import java.util.*;
import javafoundations.*;
import javafoundations.LinkedQueue; //for BFS
import javafoundations.ArrayStack; //for DFS
import java.util.*;
import java.io.*;

public class ProfessorTree<T> implements Iterable<T>{
  private T[] tree; 
  private int count; 
  private final int NOT_FOUND=-1;
  private Object top;


  
  public ProfessorTree (T root) { 
    top= root;
    tree = (T[])new Object[]{top}; 
    count=1; 
  }
  
  //  Expands T[] tree when it's too small to accomodate new family
  private void expand() { 
    //decided to expand by 2 times + 1 whenever necessary (this  
    //works well b/c each new generation has as many members as 
    //the previous generation multiplied by 2, adding 1
    T[] temp = (T[])new Object[tree.length*2+1];
    for (int i=0; i<tree.length; i++) { 
      temp[i]=tree[i]; 
    }
    tree=temp; 
  }
  
  //  Returns the element stored in the root (aka CoTU) of the tree.
  public T getBoss() { 
    return tree[0]; 
  }
  
  //  indexOf() finds the index of the T target in the T[] tree
  //  it returns NOT_FOUND if the target does not exist in T[] tree
  private int indexOf (T target) { 
    for (int i=0; i<tree.length; i++) { 
      if (tree[i]!=null && tree[i].equals(target)) { 
        return i; 
      }
    }
    return NOT_FOUND; 
  }
  
  //  Returns the member that is the offspring of target.
  //  Returns null as the offspring of the root.
  public T getNextProf(T target) { 
    try { 
      //returns the T object stored in the ((index+1)/2)-1 slot 
      //no need to check for nulls b/c the offspring is the 
      //parent (in Tree terms) of the target
      //decided to use this formula instead of (index-1)/2 b/c 
      //this formula works for every case, while the other formula 
      //doesn't work for index 0
      return tree[((indexOf(target)+1)/2)-1];
    } catch (ArrayIndexOutOfBoundsException aioobe) { 
      /*if (getBoss().equals(target)) { 
        System.out.println("The target " + target + " does not have offspring."); 
      } else { 
        //if the target isn't the CoTU, and the program is running into aioobe, 
        //then that means the target does not exist
        System.out.println("The target " + target + " does not exist."); 
      }*/
      return null; 
    }
  }
  
  //  Returns the member that is the left child of target.
  //  Returns null if the left child does not exist.
  public T getLeft(T target) { 
    //(indexOf(target)*2)+1 is the left-side child (Tree term)
    //if the index of the left-side child (Tree term is empty, 
    //it should still return null
    try { 
      return tree[(indexOf(target)*2)+1]; 
    } catch (ArrayIndexOutOfBoundsException aioobe) { 
      return null; 
    }
  }
  
  //  Sets the left child of the tree target to lprof.
  //  It throws an exception if target is not already in the tree
  public void setLeft(T target, T lprof) { 
    try { 
      //checks to see if target exists
      if (indexOf(target)==NOT_FOUND) { 
        //System.out.println("The target " + target + " does not exist.");       
      } else { 
        int cIndex = (indexOf(target)*2)+1;
        //if T[] tree isn't big enough (ie: the length of the tree is 
        //less than or equal to the index necessary to house the lprof), 
        //then expand
        if (cIndex>=tree.length) {
          expand(); 
        }
        //if the target doesn't have a pater, then this is the first pater, so we
        //increment count; however, if the target does have a pater, then we still 
        //want to set tree[cIndex] to lprof, but we don't want to increment
        if (getLeft(target)==null) {
          count++; 
        }
        tree[cIndex]=lprof; 
      }
    } catch (ArrayIndexOutOfBoundsException aioobe) { 
      //System.out.println("The target " + target + " does not exist.");
    }
    
  }
  
  //  Returns the element that is the right child of target.
  //  Returns null if the right child does not exist
  public T getRight(T target) { 
    //(indexOf(target)*2)+2 is the right-side child (Tree term)
    //if the index of the right-side child (Tree term is empty, 
    //it should still return null
    try {
      return tree[(indexOf(target)*2)+2]; 
    } catch (ArrayIndexOutOfBoundsException aioobe) {
      return null;
    }
  }
  
  //  Sets the right child of target to rprof.
  //  It throws an exception if target is not already in the tree
  public void setRight(T target, T rprof) {
    try { 
      //checks to see if target exists
      if (indexOf(target)==NOT_FOUND) { 
        //System.out.println("The target " + target + " does not exist."); 
      } else {
        int cIndex = (indexOf(target)*2)+2;
        //if T[] tree isn't big enough (ie: the length of the tree is 
        //less than or equal to the index necessary to house the rprof), 
        //then expand
        if (cIndex>=tree.length) {
          expand(); 
        }
        //if the target doesn't have a mater, then this is the first mater, so we
        //increment count; however, if the target does have a mater, then we still 
        //want to set tree[cIndex] to rprof, but we don't want to increment count
        if (getRight(target)==null) {
          count++; 
        }
        tree[cIndex]=rprof; 
      }
    } catch (ArrayIndexOutOfBoundsException aioobe) { 
      //System.out.println("The target " + target + " does not exist.");
    }
  }
  
  //  Returns true if the tree contains an element that
  //  matches the specified target element and false otherwise.
  public boolean appears (T target) {
    //if the target exists in the array, then it must have an index 
    //so, as long as indexOf() produces something other than -1, 
    //target exists
    return (indexOf(target)>=0); 
  }
  
  // Returns true if the two members share a grandchild,
  // and false if they are not or if a shared grandchild does not exist
  // Two grandparents that share a grandchild are "inLaws"
  public boolean inLaws(T gp1, T gp2) { 
    try { 
      return (getNextProf(getNextProf(gp1)).equals(getNextProf(getNextProf(gp2)))); 
      //if we try to access an offspring that doesn't exist 
      //(ie: go beyond the root), then return false
    } catch (NullPointerException npe) { 
      return false; 
    }
  }
  
  //  Returns the number of members in this ancestral tree.
  public int size() { 
    return count; 
  }
  
  //  Returns the string representation of the binary tree,
  //  one line per level.
  public String toString() { 
    String s = "The CS department has " + count + " members:\n"; 
    Iterator<T> iter = iterator();
    int index=0; 
    int num=1; 
    while (iter.hasNext()) { 
      s+=iter.next(); 
      //this adds either a space or a line break in the String output
      //the program adds the line break in b/w generations
      //to determine where a generation stops/begins, the program takes
      //int n-1 (must subtract 1 b/c index refers to the index of the array 
      //that the object is in, whereas num refers to its count)
      if (index==num-1) { 
        s+="\n";
        //updates n so that it is now equal to the count at which the 
        //next generation begins
        num = (num*2)+1; 
      } else { 
        s+=" "; 
      }
      index++;
    }
    return s;   
  }
  
  
  //maybe write remove node 
  
  //  Set to return a level-order traversal of the tree (byGenerations())
  public Iterator<T> iterator() { 
    return byGenerations();
  }
  
  //  Returns an iterator that contains a level-order traversal
  //  on the ancestral tree.
  public Iterator<T> byGenerations() {
    LinkedQueue<BTNode<T>> queue = new LinkedQueue<BTNode<T>>();
    ArrayIterator<T> iter = new ArrayIterator<T>();
    for (int i=0; i<tree.length; i++) {
      queue.enqueue(new BTNode(tree[i]));
      while (!queue.isEmpty()) {
        BTNode<T> current = queue.dequeue();
        //middle
        iter.add (current.getElement());
        //left
        if (current.getLeft() != null)
          queue.enqueue(current.getLeft());
        //right
        if (current.getRight() != null)
          queue.enqueue(current.getRight());
      }
      }
      return iter;
  }
  
    public ProfessorTree<String> createsGameTree(){ //pokemon
    ProfessorTree<String> fam = new ProfessorTree<String>("KBot"); 
    //ordered by CS classes taught- and then arbitrarily ordered because there overlap in the dept..
    fam.setLeft("KBot", "Ellen"); 
    fam.setRight("KBot", "Randy"); 
    fam.setLeft("Ellen", "Scott"); 
    fam.setRight("Ellen", "Lyn"); 
    fam.setLeft("Scott", "Rhys"); 
    fam.setRight("Scott", "Darakhshan"); 
    fam.setLeft("Lyn", "Eni"); 
    fam.setRight("Lyn", "Rita"); 
    fam.setLeft("Randy", "Takis"); 
    fam.setRight("Randy", "Orit"); 
    fam.setLeft("Takis", "Stella"); 
    fam.setRight("Takis", "Brian"); 
    fam.setLeft("Orit", "Jean"); 
    fam.setRight("Orit", "Sohie"); 
    //System.out.println("fam.getRight("Orit"));
    //System.out.println(fam); 
    //System.out.println(fam.shuffle());
    //System.out.println(fam); 
    fam.shuffle(); 
    //System.out.println(fam);
    return fam; 
  }
    
  public ProfessorTree<T> shuffle(){
    ProfessorTree shuffled= new ProfessorTree (top);
    //T[] profs = (T[])new Object[count]; 
    T[] profs = tree; 
    Random r = new Random(); 
    
   
    for (int i=1; i<count; i++) { 
      int rand = r.nextInt(i)+1; //offset so that 0 is not included (shuffling everything that is not kbot) 
      T hold = profs[rand]; 
      profs[rand]=profs[i]; 
      profs[i]=hold; 
      
    
    }
  /*
    for (int i=0; i<count; i++) { 
      System.out.println(profs[i]); 
    }
    */ //this used to print out the professors in order
    return shuffled;
  }
     
  
  public static void main (String[] args) { 
    ProfessorTree<String> fam = new ProfessorTree<String>("KBot"); 
    //ordered by CS classes taught- and then arbitrarily ordered because there overlap in the dept..
    fam.setLeft("KBot", "Ellen"); 
    fam.setRight("KBot", "Randy"); 
    fam.setLeft("Ellen", "Scott"); 
    fam.setRight("Ellen", "Lyn"); 
    fam.setLeft("Scott", "Rhys"); 
    fam.setRight("Scott", "Darakhshan"); 
    fam.setLeft("Lyn", "Eni"); 
    fam.setRight("Lyn", "Rita"); 
    fam.setLeft("Randy", "Takis"); 
    fam.setRight("Randy", "Orit"); 
    fam.setLeft("Takis", "Stella"); 
    fam.setRight("Takis", "Brian"); 
    fam.setLeft("Orit", "Jean"); 
    fam.setRight("Orit", "Sohie"); 
    //System.out.println("fam.getRight("Orit"));
    //System.out.println(fam); 
    //System.out.println(fam.shuffle());
    System.out.println(fam); 
    fam.shuffle(); 
    System.out.println(fam);
  
  }
}
