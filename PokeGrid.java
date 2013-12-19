/**********************************************
  * This is all the code from BuggleWorld that Karina and I didn't touch (we 
  * changed everything from "buggle" to "poke," so "BuggleGrid" changed to "PokeGrid," 
  * but we didn't make changes to the code). For this reason, we did not comment this code
  *********************************************/

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

public class PokeGrid extends Canvas //{
  implements MouseListener, MouseMotionListener { // ***
  
  /* A rectangular area of the PokeWorld applet that displays the state of the world */
  
  public PokeWorld world;
  // private Graphics gfx; // Graphics context of this grid
  private int cellWidth;
  private int cellHeight;
  private Rectangle gridRect;
  private final static Color floorColor = Color.white;
  private final static Color gridLineColor = Color.black;
  private final static Color bagelColor = new Color (200,100,50);
  private final static Color wallColor = Color.black;
  private Point lastHorizontalWall;
  private Point lastVerticalWall;
  private Graphics gfx; 
  // [lyn, 11/13/06] Apple's VM has subtle drawing differences from Sun's that makes bagels look bad.
  private String vmVendor = System.getProperty("java.vm.vendor"); 
  
  // [lyn, 11/11/06] Added this
  public Color getFloorColor() {
    return floorColor;
  }
  
  public void init () { // ***
    this.addMouseListener(this);  // ***
    this.addMouseMotionListener(this); // ***
  }
  
  public PokeGrid(PokeWorld bw) {
    world = bw;
    // Note: do *not* call makeGrid() here, since Canvas has not yet been allocated real-estate on screen.
  }
  
  public void makeGrid() {
    //Dimension d = new Dimension (450,450); 
    Dimension d = getSize(); // ?? size(); // *** size() has been depreciated *** 
    cellWidth = d.width / world.cols;  
    cellHeight = d.height / world.rows; 
    // [lyn, 11/11/06] Modified to center grid 
    int gridWidth = world.cols * cellWidth + 1; // + 1 accounts for last grid line
    int gridHeight = world.rows * cellHeight + 1;
    int gridX = (d.width - gridWidth)/2;
    int gridY = (d.height - gridHeight)/2;
    gridRect = new Rectangle(gridX, gridY, gridWidth, gridHeight);
  }
  
  // [lyn, 11/11/06] Modified to center grid 
  public Location cellOrigin (Location p) {
    // Returns the graphics coordinate of the upper left corner of the cell at coord p.
    // Cell coordinates range from (1,1) to (cols, rows), from lower left to upper right.
    return new Location (gridRect.x + (p.x - 1) * cellWidth, 
                         gridRect.y + (world.rows - p.y) * cellHeight);
  }
  
  public Rectangle cellRectangle (Location p) {
    // Cell coordinates range from (1,1) to (cols, rows), from lower left to upper right.
    Location origin = cellOrigin(p);
    // Account for width of grid line in rectangle dimensions. 
    // (Don't include grid lines in rectangle.)
    return new Rectangle(origin.x + 1, origin.y + 1, cellWidth - 1, cellHeight - 1);
  }
  
  public void paintGrid() {
    world.debugPrintln("PokeGrid.paintGrid()");
    
    makeGrid();
    gfx = this.getGraphics();
    
    // [lyn, 11/11/06] Paint the rectangle on which the centered grid will be displayed.
    Dimension canvasSize = this.getSize();
    
    // [lyn, 9/2/07] Assume that gfx already defined by makeGrid(). Have guaranteed
    //   that makeGrid() is always called before paintGrid().
    gfx.setColor(gridLineColor);
    gfx.fillRect(0, 0, canvasSize.width, canvasSize.height);
    
    
    
    
    // [lyn, 11/11/06] Now display the grid itself.
    gfx.setColor(floorColor);
    gfx.fillRect(gridRect.x, gridRect.y, gridRect.width, gridRect.height);
    int left = gridRect.x;
    int right = left + gridRect.width -1;
    int top = gridRect.y;
    int bottom = top + gridRect.height - 1;
    gfx.setColor(gridLineColor);
    
    
    
    
    //making the background
    try { 
      Image bg = ImageIO.read(new File("tile.png"));
      for (int i=0; i<10; i++) { 
        //drawInCell(bg, new Location(i,9)); 
      }
    } catch (Exception e) { 
    }
    
    
    // Paint horizontal grid lines
    for (int j = 0; j <= world.rows; j++) {
      gfx.drawLine(left, gridRect.y + j * cellHeight, right, gridRect.y + j * cellHeight);
    }
    // Paint vertical grid lines
    for (int i = 0; i <= world.cols; i++) {
      gfx.drawLine(gridRect.x + i * cellWidth, top, gridRect.x + i * cellWidth, bottom);
    }
    
    // Could say the following, but it is better (from visual perspective)
    // to break up into more primitive operations.  
    /*for (int i=1; i<=world.rows; i++) {
     for (int j=1; j<=world.cols; j++) {
     world.debugPrintln("Calling drawCell from paintGrid");
     drawCell(new Point(i,j));
     }} 
     */ 
    
    // Paint trails & bagels & strings
    world.debugPrintln("in paintGrid: painting trails and bagels");
    // [lyn, 11/11/06] fixed bug -- cols and rows were swapped here!
    // This was the cause of the bug that forced all grids to be square.
    for (int i=1; i<=world.cols; i++) {
      for (int j=1; j<=world.rows; j++) {
        Location p = new Location(i,j);
        world.debugPrintln("in paintGrid: testing mark at (" + i + ", " + j + ")");
        
        // Fill the background color of the cell.
        // It's either the floor color or the trail color. 
        Color c = world.markColorAt(p);
        if (!c.equals(floorColor)) // [lyn, 11/11/06] Modified b/c markColorAt no longer returns null
          drawMark(c, p);
        
        // Draw any bagels
        if (world.isBagelAt(p)) 
          drawBagel(p, c);
        
        // Draw any strings
        if (world.isStringAt(p)) {
          drawString(p, c);
        }
        
      }
    } 
    
    // Paint walls
    world.debugPrintln("in paintGrid: painting walls");
    
    // [lyn, 11/11/06] Split into horizontal and vertical walls to avoid fencepost errors
    // Paint horizontal walls:
    for (int x = 0; x < world.cols; x++) {
      for (int y = 0; y <= world.rows; y++) {
        world.debugPrintln("in paintGrid: testing horizontal wall at (" + x + ", " + y + ")");
        if(world.horizontalWalls[x][y]) {
          drawHorizontalWall(x,y);
        } 
      }
    }
    // Paint vertical walls:   
    for (int x = 0; x <= world.cols; x++) {
      for (int y = 0; y < world.rows; y++) {
        world.debugPrintln("in paintGrid: testing vertical wall at (" + x + ", " + y + ")");
        if(world.verticalWalls[x][y]) {
          drawVerticalWall(x,y);
        } 
      }
    }
    
    // Paint pokes 
    Enumeration bugs = world.pokes.elements();
    while ( bugs.hasMoreElements() ) {
      Poke next = (Poke) bugs.nextElement();
      this.draw(next);
    }
    // [9/6/04] Causes a painting loop!
    // world.debugPrintln("calling repaint() from PokeGrid.paintGrid()");
    // repaint(); //***** needed this ****
  }
  
  
  
  
  public void paintCell (Graphics g, Image img, int x, int y) {
    ////System.out.println("Draw in rect: g = " + g + "; rgt = " + r);
    // Draw myself in given rectangle of graphics.
    // Assume simple triangle shape for now; do something cuter in future.
    
    // Compute triangle based on direction; there must be a cleverer way to do this!
    /*Point p1 = new Point(0,0);
     double insetFactor = 0.5;
     int insetX = (int) Math.floor(insetFactor * r.width); // unlike with bagels, no grid line to consider
     int insetY = (int) Math.floor(insetFactor * r.height); // unlike with bagels, no grid line to consider
     int width = r.width - 1; // [lyn, 11/11/06] Decrement so additions make sense in discrete grid
     int height = r.height - 1;
     p1.x = r.x + insetX;
     p1.y = r.y + insetY;
     */
    g.setPaintMode();
    try { 
      Image sprite = img; 
      g.drawImage(sprite, x, y, this); 
    } catch (Exception e) { 
    }
  }
  
  
  
  
  
  
  public void drawHorizontalWall(int x, int y) {
    // Graphics gfx = this.getGraphics();
    Point wp = wallOrigin(new Point(x,y));
    if (world.horizontalWalls[x][y]) {
      gfx.setColor(wallColor);
      gfx.drawLine(wp.x, wp.y-1, wp.x + cellWidth, wp.y-1);
      gfx.drawLine(wp.x, wp.y, wp.x + cellWidth, wp.y);
      gfx.drawLine(wp.x, wp.y+1, wp.x + cellWidth, wp.y+1);
    } else {
      gfx.setColor(gridLineColor);
      gfx.drawLine(wp.x, wp.y, wp.x + cellWidth, wp.y);
    }
  }
  
  public void drawVerticalWall(int x, int y) {
    // Graphics gfx = this.getGraphics();
    Point wp = wallOrigin(new Point(x,y));
    if (world.verticalWalls[x][y]) {
      gfx.setColor(wallColor);
      gfx.drawLine(wp.x-1, wp.y - cellHeight, wp.x-1 , wp.y);
      gfx.drawLine(wp.x, wp.y - cellHeight, wp.x , wp.y);
      gfx.drawLine(wp.x+1, wp.y - cellHeight, wp.x+1 , wp.y);
    } else {
      gfx.setColor(gridLineColor);
      gfx.drawLine(wp.x, wp.y - cellHeight, wp.x , wp.y);
    }
  }
  
  // [lyn, 11/11/06] Modified to include gridRect.x and gridRect.y
  public Point wallOrigin (Point p) {
    // Returns the graphics coordinate of the wall point denoted by coordinate p.
    // Wall coordinates range from (0,0) to (cols, rows), from lower left to upper right.
    return new Point(gridRect.x + p.x * cellWidth, 
                     gridRect.y + (world.rows - p.y) * cellHeight);
  }
  
  
  public void paint() {
    world.debugPrintln("PokeGrid paint();");
    this.paint( gfx ); //this.getGraphics()
  }
  
  public void paint( Graphics g ) {
    
    // super.paint(g); //****?? paintComponent??
    world.debugPrintln("PokeGrid paint(Graphics g);");
    paintGrid();
    // //System.out.println("Start Paint");
    // resize();
    // Paint floor:
    ////System.out.println("Begin printing all pokes.");
    Enumeration bugs = world.pokes.elements();
    ////System.out.println("Begin printing all pokes1.");
    while (bugs.hasMoreElements()) {
      ////System.out.println("Begin printing all pokes2.");
      Poke b = (Poke) bugs.nextElement();
      ////System.out.println("Begin printing all pokes3.");
      world.debugPrintln("Calling drawPoke from PokeGrid.paint(Graphics g)");
      this.draw(b);
    }
    ////System.out.println("End printing all pokes.");
    // //System.out.println("Stop Paint");
  }
  
  public void draw( Poke b ) {
    ////System.out.println("Draw poke " + b);
    ////System.out.println("Position = " + nullify(b.position()));
    world.debugPrintln("Calling drawCell from draw(Poke)");
    
    drawCell(b.position());
  }
  
  public String nullify (Object obj) {
    if (obj == null)
      return "null";
    else
      return obj.toString();
  }
  
  public void drawCell (Location p) {
    world.debugPrintln("Draw cell " + p);
    if (gfx != null) { // [lyn, 9/2/07] Ignore drawCell() request if called when gfx is null
      //   (say, by an addBagel() in reset(), before grid.paint() is called. 
      Color c = world.markColorAt(p);
      //Color c = null; 
      ////System.out.println("Mark color = " + nullify(c));
      
      // [lyn, 11/11/06] Optimized the following as part of cleaning up markColorAt
      drawMark(c, p);
      
      /*
       // if (!c.equals(floorColor)) {
       // drawMark(c, p);
       
       // Fill the background color of the cell.
       // It's either the floor color or the trail color. 
       Rectangle r = this.cellRectangle(p);
       if (gfx == null) {
       Graphics gfx = this.getGraphics();
       }
       gfx.setColor(c);
       gfx.setPaintMode();
       gfx.fillRect(r.x, r.y, r.width, r.height);
       */
      // Draw any pokes in this cell
      drawPokesAt(p);
      
      // Redraw any walls adjoining this cell
      if (world.horizontalWalls[p.x-1][p.y-1]) {
        drawHorizontalWall(p.x-1, p.y-1);
      }
      if (world.verticalWalls[p.x-1][p.y-1]) {
        drawVerticalWall(p.x-1, p.y-1);
      }
      if (world.horizontalWalls[p.x-1][p.y]) {
        drawHorizontalWall(p.x-1, p.y);
      }
      if (world.verticalWalls[p.x][p.y-1]) {
        drawVerticalWall(p.x, p.y-1);
      }
      // Experiment to see if yielding fixes drawing problem in step. It doesn't.
      ////System.out.println("drawCell yielding.");
      //**** Thread.yield(); //***
      ////System.out.println("drawCell after yield.");
    }
  }
  
  public void drawInCell(Image img, Location p) { 
    Rectangle r = cellRectangle(p); 
    gfx.drawImage(img, r.x, r.y, world); 
  }
  
  public void drawMark (Color c, Location p) {
    // Really want stipple pattern here -- how to get it?
    // Graphics gfx = this.getGraphics();
    world.debugPrintln("drawMark(" + c + ", " + p + ")");
    Rectangle r = cellRectangle(p);
    gfx.setColor(c);
    gfx.setPaintMode();
    gfx.fillRect(r.x, r.y, r.width, r.height);
  }
  
  public void drawBagel(Location p, Color background) {
    world.debugPrintln("drawBagel(" + p + ", " + background + ")");
    double insetFactor = 0.05; 
    double holeFactor = 0.35;
    // [lyn, 11/11/06] Changed the following so that they are not constants but proportional.
    // Also, they are exactly 1 for small cells, but > 1 for larger cells. 
    int insetX = (int) Math.ceil(insetFactor*cellWidth); 
    int insetY = (int) Math.ceil(insetFactor*cellHeight);
    Location origin = cellOrigin(p);
    int bagelX = origin.x + insetX;
    int bagelY = origin.y + insetY; 
    int bagelWidth = cellWidth + 1 - (2*insetX);
    int bagelHeight = cellHeight + 1 - (2*insetY);
    int holeWidth = (int) (holeFactor*bagelWidth);
    int holeHeight = (int) (holeFactor*bagelHeight);
    int holeX = bagelX + ((bagelWidth - holeWidth)/2);
    int holeY = bagelY + ((bagelHeight - holeHeight)/2);
    // Graphics gfx = this.getGraphics();
    gfx.setColor(bagelColor);
    if (vmVendor.equals("\"Apple Computer, Inc.\"")) {
      // Apple's VM has buggy oval-filling code that makes bagels look bad. 
      // This corrects for the bug. 
      // [lyn, 9/3/07] This looks best on Apple: 
      gfx.fillOval(bagelX+1, bagelY+1, bagelWidth-3, bagelHeight-3);
      gfx.setColor(background);
      gfx.fillOval(holeX+1 , holeY+1, holeWidth-3, holeHeight-3);
    } else {
      // gfx.fillOval(bagelX, bagelY, bagelWidth, bagelHeight);
      // gfx.setColor(background);
      // gfx.fillOval(holeX , holeY, holeWidth, holeHeight);
      // [lyn, 9/3/07] This looks better in Linux: 
      gfx.fillOval(bagelX, bagelY, bagelWidth-1, bagelHeight-1);
      gfx.setColor(background);
      gfx.fillOval(holeX , holeY, holeWidth-1, holeHeight-1);
    }
    gfx.setColor(Color.black);
    gfx.drawOval(bagelX, bagelY, bagelWidth-1, bagelHeight-1);
    gfx.drawOval(holeX , holeY, holeWidth-1, holeHeight-1);      
  }
  
  public void drawString(Location p, Color background) {
    world.debugPrintln("drawString(" + p + ", " + background + ")");
    int inset = 3;
    String s = world.getStringAt(p);
    Location origin = cellOrigin(p);
    int stringX = origin.x + inset;
    int stringY = origin.y + cellHeight - inset; 
    gfx.setColor(Color.black);
    gfx.drawString(s, stringX, stringY);
  }
  
  public void drawPokesAt (Location p) {
    // //System.out.println("drawPokesAt " + p);
    // //System.out.println("pokes = " + nullify(world.pokes));
    /*for (int i = world.pokes.size() - 1; i >= 0; i--) { 
     Poke b = (Poke) world.pokes.elementAt(i);
     if (b.getPosition().equals(p)) 
     b.drawInRect(this.getGraphics(), cellRectangle(p));
     break;
     }*/
    // Draw all pokes at current position.
    for (int i = 0; i < world.pokes.size(); i++) { 
      Poke b = (Poke) world.pokes.elementAt(i);
      if (b.position().equals(p)) 
        b.drawInRect( //this.getGraphics(),
                     gfx, 
                     cellRectangle(p) );
    }
  }
  
  // This method is called when the user clicks the mouse to start a scribble.
  // *** 1.0 public boolean mouseDown(Event e, int x, int y) {
  public void mousePressed(MouseEvent e) {
    int x = e.getX(), y = e.getY();  // ***
    if ( mouseInHorizontalWall(x,y) ) {
      Point p = horizontalWallAt(x,y);
      world.horizontalWalls[p.x][p.y]=!world.horizontalWalls[p.x][p.y];
      if (p == lastHorizontalWall) {
        gfx.setXORMode(Color.white);
        drawHorizontalWall(p.x,p.y);
      }
      if (world.horizontalWalls[p.x][p.y]) {
        gfx.setPaintMode();
        drawHorizontalWall(p.x,p.y);
      }
      gfx.setXORMode(Color.white);
      drawHorizontalWall(p.x,p.y);
    } else if (mouseInVerticalWall(x,y)) {
      Point p = verticalWallAt(x,y);
      world.verticalWalls[p.x][p.y]=!world.verticalWalls[p.x][p.y];
      drawVerticalWall(p.x,p.y);
      if (p == lastVerticalWall) {
        gfx.setXORMode(Color.white);
        drawVerticalWall(p.x,p.y);
      }
      if (world.verticalWalls[p.x][p.y]) {
        gfx.setPaintMode();
        drawVerticalWall(p.x,p.y);
      }
      gfx.setXORMode(Color.white);
      drawVerticalWall(p.x,p.y);
    }
    // ***return true;
  }
  
  // *** 1.0    public boolean mouseMove(Event e, int x, int y) {
  public void mouseDragged(MouseEvent e) {
    // This is hard to comprehend an inefficient.
    // Certainly there must be a better way of handling this!
    int x = e.getX(), y = e.getY();  // ***
    if (mouseInHorizontalWall(x,y)) {
      if (lastVerticalWall != null) {
        mouseLeavesVerticalWall(lastVerticalWall);
        lastVerticalWall = null;
      }
      Point h = horizontalWallAt(x,y);
      if (!(h.equals(lastHorizontalWall))) {
        if (lastHorizontalWall != null) {
          mouseLeavesHorizontalWall(lastHorizontalWall);
        }
        lastHorizontalWall = h;
        mouseEntersHorizontalWall(lastHorizontalWall);
      }
    } else if (mouseInVerticalWall(x,y)) {
      if (lastHorizontalWall != null) {
        mouseLeavesHorizontalWall(lastHorizontalWall);
        lastHorizontalWall = null;
      }
      Point v = verticalWallAt(x,y);
      if (!(v.equals(lastVerticalWall))) {
        if (lastVerticalWall != null) {
          mouseLeavesVerticalWall(lastVerticalWall);
        }
        lastVerticalWall = v;
        mouseEntersVerticalWall(v);
      }
    } else if (lastVerticalWall != null) {
      mouseLeavesVerticalWall(lastVerticalWall);
      lastVerticalWall = null;
    } else if (lastHorizontalWall != null) {
      mouseLeavesHorizontalWall(lastHorizontalWall);
      lastHorizontalWall = null;
    }
    // *** return true;
  }
  
  public void mouseEntersHorizontalWall(Point p) {
    // Graphics gfx = this.getGraphics();
    gfx.setXORMode(Color.white);
    drawHorizontalWall(p.x, p.y);
  }
  
  // ***The other, unused methods of the MouseListener interface.
  public void mouseReleased(MouseEvent e) {;}
  public void mouseClicked(MouseEvent e) {;}
  public void mouseEntered(MouseEvent e) {;}
  public void mouseExited(MouseEvent e) {;}
  
  // ***The other method of the MouseMotionListener interface.
  public void mouseMoved(MouseEvent e) {;}   
  
  public void mouseLeavesHorizontalWall(Point p) {
    // Graphics gfx = this.getGraphics();
    gfx.setXORMode(Color.white);
    drawHorizontalWall(p.x, p.y);
  }
  
  public void mouseEntersVerticalWall(Point p) {
    // Graphics gfx = this.getGraphics();
    gfx.setXORMode(Color.white);
    drawVerticalWall(p.x, p.y);
  }
  
  public void mouseLeavesVerticalWall(Point p) {
    // Graphics gfx = this.getGraphics();
    gfx.setXORMode(Color.white);
    drawVerticalWall(p.x, p.y);
  }
  
  public boolean mouseInHorizontalWall(int x, int y) {
    int probe = (y + 1) % cellHeight;
    return ((0 <= probe) && (probe <= 2));
  }
  
  public boolean mouseInVerticalWall(int x, int y) {
    int probe = (x + 1) % cellWidth;
    return ((0 <= probe) && (probe <= 2));
  }
  
  public Point horizontalWallAt(int x, int y) {
    return new Point (x/cellWidth, world.cols - ((y+1)/cellHeight));
  }
  
  public Point verticalWallAt(int x, int y) {
    return new Point ((x+1)/cellWidth, world.cols - (y/cellHeight) - 1);
  }
  
  /*
   // This method is called when the user drags the mouse.
   public boolean mouseDrag(Event e, int x, int y) {
   // //System.out.println("Mouse Drag Event" + "; x = " + x + "; y = " + y);
   return true;
   }*/
}

//**************************************************************************
class PokeExecuter {
  // Control the executiong of pokes. Allow pokes to be stepped,
  // be run until explicitly paused, or be reset. 
  
  private PokeRunner runner;   // Encapsulates running into object suitable for thread. 
  // Make only one of these.
  private Thread thread; // Make new thread every time reset.
  //private javax.swing.Timer timer; // **** Make new thread every time reset.
  private PokeWorld world; 
  private boolean stepMode = false;
  private boolean isFirstSteppedInstruction = true;
  private String currentInstruction;
  private static int state;
  private static final int UNSTARTED = 0;
  private static final int RUNNING = 1; // Running or scheduled to be run. 
  private static final int YIELDED = 2;
  private static final int SUSPENDED = 3;
  private static final int DELAY = 30; // ****
  private static boolean execDebug = false;
  
  public PokeExecuter(PokeWorld w) {
    world = w;
    runner = new PokeRunner(w);
    init();
  }
  
  // [9/6/04]
  public void execDebugPrintln(String s) {
    if (execDebug) {
      //System.out.println("Exec debug: " + s);
    }
  }
  
  public void init () {
    thread = new Thread(runner);
    //timer = new javax.swing.Timer(DELAY, null); // ****
    //****?? getContentPane().add( new ReboundPanel(timer) );
    state = UNSTARTED;
    execDebugPrintln("init: state set to UNSTARTED");
    stepMode = false;
    execDebugPrintln("init: stepMode is false");
    currentInstruction = null;
    isFirstSteppedInstruction = true;
  }
  
  public void run() {
    world.debugPrintln("run()");
    // Run pokes until done or sent an explicit stop message.
    stepMode = false;
    execDebugPrintln("run: set stepMode to false");
    go();
  }
  
  public void step() {
    execDebugPrintln("step()");
    stepMode = true;
    execDebugPrintln("step: set stepMode to true.");
    if ((! isFirstSteppedInstruction) && (! (currentInstruction.equals("")))) {
      world.printInstruction(currentInstruction); 
      currentInstruction = ""; // Needed so don't print anything when no more instructions. 
    }  
    go();
  }
  
  private void go() { 
    execDebugPrintln("go: STATE  = " + stateString());
    if (state == UNSTARTED) {
      execDebugPrintln("go: starting thread");
      state = RUNNING; // [9/6/04]
      execDebugPrintln("go: state set to RUNNING");
      thread.start();
      execDebugPrintln("go: after starting thread");
    } else if (state == SUSPENDED) {
      execDebugPrintln("go: resuming thread");
      state = RUNNING;
      execDebugPrintln("go: state set to RUNNING");
      thread.resume(); // [9/6/04] Schedules poke thread to run again (but doesn't run yet).
      execDebugPrintln("go: after resuming thread");
    } else {
      // Already running or yielded -- ignore. 
      // //System.out.println("Poke Execution Error -- unexpected state in go(): " + stateString());
    }
  }
  
  public void pause() {
    execDebugPrintln("pause()");
    if ((state == UNSTARTED) || (state == SUSPENDED)) {
      // Do nothing
    } else {
      if ( thread.isAlive() ) {  
        //if clause added by alice (01/22/03) in order to avoid SecurityException by suspending a dead thread
        execDebugPrintln("pause: suspending thread " + stateString());
        thread.suspend(); // [9/6/04]
        state = SUSPENDED; // [9/6/04]
        execDebugPrintln("pause: state set to SUSPENDED");
      }
      isFirstSteppedInstruction = true;
      execDebugPrintln("pause: after suspending thread " + stateString());
    }
  }
  
  
  public void reset() {
    execDebugPrintln("reset()");
    if (state==UNSTARTED) {
      // do nothing
    } else { 
      if (thread.isAlive() ) { //[9/6/04]
        //additional clause added by alice (01/22/03) in order to avoid SecurityException by suspending a dead thread
        execDebugPrintln("reset: stopping thread " + stateString());
        thread.stop();
      }
      execDebugPrintln("reset: resetting runner.");
      runner.reset();
      init();
    }
  }
  
  public String stateString () {
    if (state == UNSTARTED) {
      return "UNSTARTED";
    } else if (state == RUNNING) {
      return "RUNNNING";
    } else if (state == YIELDED) {
      return "YIELDED";
    } else if (state == SUSPENDED) {
      return "SUSPENDED";
    } else {
      return "UNKNOWN";
    }
  }
  
}

//**************************************************************************
class PokeRunner implements Runnable {
  /* A way to encapsulate the behavior of the pokes into a thread-like object */
  
  private PokeWorld world;
  private boolean done = false;
  
  public PokeRunner(PokeWorld w) {
    // //System.out.println("new Poke(runner)");
    world = w;
  }
  
  public void run () {
    // //System.out.println("BuggelRunner run();");
    world.run(); 
    done = true;
  }
  
  public boolean isDone() {
    return done;
  }
  
  public void reset () {
    // //System.out.println("BuggelRunner reset();");
    done = false;
  }
  
}

//**************************************************************************
class Direction {
  
  private int dir;
  
  public static final Direction NORTH = new Direction(0);
  public static final Direction EAST = new Direction(1);
  public static final Direction SOUTH = new Direction(2);
  public static final Direction WEST = new Direction(3);
  
  private static final Location northLoc = new Location(0,1);
  private static final Location eastLoc = new Location(1, 0);
  private static final Location southLoc = new Location(0, -1);
  private static final Location westLoc = new Location(-1, 0);
  
  private static final Direction[] rights = {EAST, SOUTH, WEST, NORTH};
  private static final Direction[] lefts = {WEST, NORTH, EAST, SOUTH};
  private static final Direction[] opposites = {SOUTH, WEST, NORTH, EAST};
  private static final Location[] locations = {northLoc, eastLoc, southLoc, westLoc};
  private static final String[] strings = {"NORTH", "EAST", "SOUTH", "WEST"};
  
  private Direction(int d) {
    dir = d;
  }
  
  public boolean equals (Direction d) {
    return dir == d.dir;
  }
  
  // Carefully define the following so that == works as well as .equals
  public Direction right() {
    return rights[dir];
  }
  
  public Direction left() {
    return lefts[dir];
  }
  
  public Direction opposite() {
    return opposites[dir];
  }
  
  public Location toLocation() {
    return locations[dir];
  }
  
  public String toString () {
    return strings[dir];
  } 
}

//**************************************************************************
// [lyn, 8/22/07] New class for immutable points. PokeWorld
//   now uses these rather than the mutable Point class to 
//   avoid some knotty Poke contract issues with immutable points. 

class Location {
  
  // Immutable (really, write-once) instance variables
  public final int x; 
  public final int y; 
  
  // Constructor method
  public Location(int initx, int inity) {
    x = initx;
    y = inity;
  }
  
  // Instance methods
  
  public boolean equals (Object obj) {
    if (obj instanceof Location) {
      Location loc = (Location) obj;
      return (x == loc.x) && (y == loc.y);
    } else {
      return false;
    }
  }
  
  public Point toPoint () {
    return new Point(x,y); 
  }
  
  // Displaying as a string: 
  public String toString() {
    return "Location(x=" + x + ",y=" + y + ")";
  }
  
  // Class methods
  
  public Location fromPoint (Point p) {
    return new Location(p.x, p.y); 
  }
  
  public Point toPoint (Location loc) {
    return new Point(loc.x, loc.y); 
  }
  
}

//**************************************************************************
class PokeException extends RuntimeException {
  
  public PokeException (String msg) {
    super(msg);
  }
  
}

class MoveException extends Exception { 
  public MoveException (String msg) { 
    super(msg); 
  }
}
