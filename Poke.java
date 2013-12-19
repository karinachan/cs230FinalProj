import java.awt.*;
import java.awt.event.*; // ***
import java.applet.*;  // applet.* ??
import java.util.*;
import javax.swing.*;  // ****
import javax.imageio.*; 
import java.io.*; 
import java.util.*;
import javafoundations.*;

import javax.swing.border.*;

public class Poke {
  
  // Although pokes are normally presented as having four pieces of state
  // (position, heading, color, and brush state), there is an important
  // fifth piece of state: the PokeWorld in which the poke lives. 
  // 
  // There may be multiple PokeWorlds in existence at any one time;
  // how does a poke know which one to live in? There are two ways:
  // 
  // (1) A PokeWorld instance can be explicitly provided to the Poke constructor.
  //     But since the world is not advertised as a piece of state, most
  //     users won't know about this option. 
  // 
  // (2) If no PokeWorld instance is explicitly provided to the Poke constructor,
  //     it implicitly uses the currently "active" PokeWorld, which is the
  //     one in which one of the following two actions has most recently occurred:
  //     1) The PokeWorld has been created
  //     2) A menu item has been pressed in the PokeWorld. 
  //     The class variable PokeWorld.currentWorld holds the currently active
  //     instance of PokeWorld. 
  
  private char direction = 's'; //determines which way the sprite should be facing
  private PokeWorld world;  // The world to which the Poke belongs.
  private Location position;   // Location of the Poke
  private static final int _defaultX = 1;
  private static final int _defaultY = 1;
  
  //KARINA EDITS 12/4/13
  private Pokemon yourPokemon; 
  
  private Direction heading;
  private Color color;       // Color of the Poke.
  private boolean trailmode = true;
  private static final boolean _defaultTrailmode = true;
  private static final Color _defaultColor = Color.red;
  private static final Color selectedPokeOutlineColor = Color.black;
  private static final Color unselectedPokeOutlineColor = Color.white;
  // private static final int inset = 3; // Number of pixels by which drawn poke is inset from cell edge
  private static final double insetFactor = 0.05; // Factor by which drawn poke is inset from cell
  
  public Poke(Pokemon buddy) {
    this(_defaultColor, _defaultX, _defaultY, PokeWorld.currentWorld);
    yourPokemon= buddy;
  }
  public Poke(Color c, int x, int y, PokeWorld w) {
    //  System.out.println ("New Poke.");
    color = c;
    position = new Location(x, y);
    heading = Direction.EAST;
    trailmode = _defaultTrailmode;
    world = w; 
    //System.out.println ("Getting world.");
    if (world == null) 
      throw new PokeException("PokeWorld of newly created poke is null!");
    else {
      //System.out.println ("Adding robot to world" + world);
    }
    // Careful! Must select poke before adding it 
    // (which will try to draw it, and may complain if not selected).
    world.selectPoke(this);
    ////System.out.println("This poke = " + this );
    world.add(this);
  }
  
  
  //KARINA EDIT 12/4/13
  public Pokemon getBPokemon(){
    return yourPokemon;
  }
  
  //EDITED BY KARINA- TRYING TO GET THE POKEMON
  
  public String toString () {
    return "[position = (" + position.x + ", " + position.y + ")"
      + "; heading = " + heading
      + "; color = " + color 
      + "; brushDown? " + trailmode
      +"]";
  }
  
  /***************************************
    * essentially the forward() method of Buggles
    **************************************/
  public void right() throws MoveException {
    this.direction='e'; 
    eastStep();
  }
  
  // [lyn, 9/2/07] eastStep() is the underlying primitive used by right() and forward(n)
  //moves the poke east
  public void eastStep() throws MoveException {
    if (world.wallInDirection(position, heading))
      throw new MoveException("");
    Location oldPosition = position;
    position = world.addCoordinates(position, heading.toLocation());
    world.pokeMoved(this, oldPosition, position);
  }
  
  /***************************************
    * essentially the backward() method of Buggles
    **************************************/
  public void left() throws MoveException {
    this.direction='w'; 
    westStep();
  }
  
  // [lyn, 9/2/07] westStep() is the underlying primitive used by left() and backward(n)
  //changed the way Pokes handle going backwards (instead of changing heading, just
  //hardcoded everything to be opposite) b/c otherwise the Poke would flip
  public void westStep() throws MoveException {
    //heading = heading.opposite();
    if (world.wallInDirection(position, heading.opposite())) 
      throw new MoveException("");
    Location oldPosition = position;
    position = world.addCoordinates(position, heading.opposite().toLocation());
    world.pokeMoved(this, oldPosition, position);
  }
  
  /*************************************
    * basically the same code as eastStep() and westStep() b/c 
    * Pokes actually move up and down (whereas Buggles only rotate)
    * difference between up() and eastStep() is up() moves based on 
    * the assumption that heading is heading.left()
    * **********************************/
  public void up() throws MoveException {
    this.direction='n';
    if (world.wallInDirection(position, heading.left()))
      throw new MoveException("FORWARD: Can't move through wall!");
    //if (trailmode)
    //world.markTrail(position, color);
    Location oldPosition = position;
    position = world.addCoordinates(position, heading.left().toLocation());
    world.pokeMoved(this, oldPosition, position);
  }
  
  /*************************************
    * basically the same code as eastStep() and westStep() b/c 
    * Pokes actually move up and down (whereas Buggles only rotate)
    * difference between up() and eastStep() is up() moves based on 
    * the assumption that heading is heading.right()
    * **********************************/
  public void down() throws MoveException {
    this.direction='s'; 
    if (world.wallInDirection(position, heading.right()))
      throw new MoveException("FORWARD: Can't move through wall!");
    //if (trailmode)
    //world.markTrail(position, color);
    Location oldPosition = position;
    position = world.addCoordinates(position, heading.right().toLocation());
    world.pokeMoved(this, oldPosition, position);
    ////System.out.println("down();");
  }
  
  public Location getPosition () {
    return position;
  }
  
  public Location position () {
    ////System.out.println("position();");
    // Non-waiting version of primitive
    return position;
  }
  
  public void setPosition (Location p) {
    // [lyn, 9/1/07] Added check that position is legal 
    if (! world.isLocationInGrid(p)) {
      throw new PokeException("SETPOSITION: Location not in grid -- " + p);
    } else {
      Location oldPosition = position;
      position = p; 
      world.pokeMoved(this,oldPosition,position);
    }
  }
  
  public Direction getHeading () {
    return heading;
  }
  
  public void setHeading (Direction d) {
    heading = d; 
    world.pokeChanged(this);
  }
  
  
  public boolean isFacingWall () {
    return world.wallInDirection(position, heading);
  }
  
  public boolean isOverBagel () {
    return world.isBagelAt(position);
  }
  
  public void pickUpBagel () {
    if (! world.isBagelAt(position)) 
      throw new PokeException("pickUpBagel: no bagel to pick up!");
    world.removeBagel(position);
  }
  
  public void dropBagel () {
    if (world.isBagelAt(position)) 
      throw new PokeException("dropBagel: already a bagel in this cell!");
    world.addBagel(position);
  }
  
  public String dropString (String s) {
    world.addString(position, s);
    return s; 
  }
  
  public int dropInt (int n) {
    world.addString(position, Integer.toString(n));
    return n;
  }
  
  public Color getColor () {
    return color;
  }
  
  public boolean isBrushDown() {
    return trailmode;
  }
  
  public void brushDown() {
    trailmode = true;
  }
  
  public void brushUp() {
    trailmode = false;
  }
  
  // [lyn, 11/11/06] Changed to use inset factor
  /***************************************************
    * draws the pokemon 
    * @param g, r          r is the rectangle that the graphic is drawn in 
    **************************************************/
  public void drawInRect(Graphics g, Rectangle r) {
    ////System.out.println("Draw in rect: g = " + g + "; rgt = " + r);
    // Draw myself in given rectangle of graphics.
    // Assume simple triangle shape for now; do something cuter in future.
    // LZ: changed them to Pikachus C:    
    // Compute triangle based on direction; there must be a cleverer way to do this!
    Point p1 = new Point(0,0);
    int insetX = (int) Math.floor(insetFactor * r.width); // unlike with bagels, no grid line to consider
    int insetY = (int) Math.floor(insetFactor * r.height); // unlike with bagels, no grid line to consider
    int width = r.width - 1; // [lyn, 11/11/06] Decrement so additions make sense in discrete grid
    int height = r.height - 1;
    p1.x = r.x + insetX;
    p1.y = r.y + insetY;
    g.setPaintMode();
    g.setColor(color);
    /*************************************************
      * to draw a Poke, you take the code for drawing a Buggle, 
      * but instead of making a triangle using math, we just impose
      * an Image on the rectangle
      ************************************************/
    try { 
      //accounts for when the Poke (user's character) is on the door (to make things beautiful)
      //hard coded b/c of time constraints
      if (getPosition().equals(new Location(9,9))) { 
        if (direction=='s') { 
          Image sprite = ImageIO.read(new File("pikas_door.png"));  
          g.drawImage(sprite, p1.x, p1.y, world); 
        } else if (direction=='w') { 
          Image sprite = ImageIO.read(new File("pikaw_door.png"));  
          g.drawImage(sprite, p1.x, p1.y, world);
        } else if (direction=='e') { 
          Image sprite = ImageIO.read(new File("pikae_door.png"));  
          g.drawImage(sprite, p1.x, p1.y, world);
        } else if (direction=='n') { 
          Image sprite = ImageIO.read(new File("pikan_door.png"));  
          g.drawImage(sprite, p1.x, p1.y, world);
        }
      } else {
        if (direction=='s') { 
          g.drawImage(yourPokemon.getSprites()[0], p1.x, p1.y, world); 
        } else if (direction=='w') { 
          g.drawImage(yourPokemon.getSprites()[1], p1.x, p1.y, world); 
        } else if (direction=='e') { 
          g.drawImage(yourPokemon.getSprites()[2], p1.x, p1.y, world); 
        } else if (direction=='n') { 
          g.drawImage(yourPokemon.getSprites()[3], p1.x, p1.y, world); 
        }
      }
    } catch (Exception e) { 
    }
  }
}