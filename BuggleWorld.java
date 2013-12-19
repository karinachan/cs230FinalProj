/**************************************************************************
Name: BuggleWorld.java

Description: 
  Implementation of CS111 Buggle World classes. 
  BuggleWorld is a Java microworld of robotic creatures inspired 
  by Seymour Papert's Logo Turtles and Richard Pattis's Karel the Robot.

History: 

 ----------------------------------------------------------------------
  [lyn, 10/01/07] Added isVerticalWall, isHorizontalWall
  ----------------------------------------------------------------------
  [lyn, 9/18/07] Added the "add" instance method to the Location class. 
  ----------------------------------------------------------------------
  [brian, 9/13/07] The "setCellColor" method was accidentally named
                   "setCellCell". The method name was changed to
                   "setCellColor" as intended.
  ----------------------------------------------------------------------
  [lyn, 9/11/07] Modified forward(n) and backward(n) to handle
                 negative steps (used by HuggleWorld). 
  ----------------------------------------------------------------------
  [lyn, 8/30/07--9/3/07]

  This is the 9/3/07 version of BuggleWorld for Fall'07.
  No further changes are anticipated this semester.

  * Added [1/22/03] notes from thread fixes by Alice Tiao and Elena Machkasova.

  * Made long-awaited simplifications/changes to the setup()/reset() model.

    + setup() is called exactly once (during init()), before reset() is called. 
      The default setup() does nothing; it is expected to be overridden by subclasses
      to BuggleWorld. When setup() is called, the only BuggleWorld state it should change is 
      ROWS and COLS, which can be done by setDimensions(<cols>,<rows>). 
      The setup() method is also used to perform one-time initializations, like
      creating ParameterFrames.

    + reset() is called during init() and whenver RESET button is pressed to 
      reset the state of BuggleWorld. It is assumed that the ROWS and COLS
      instance variables are appropriately set *before* reset() is called
      (say, by a call to setup() or in an overridden reset() method before
      the call to super.reset().) reset() sets the state of walls, buggles,
      bagels, and grid cells, but does *not* display any of this state;
      that's the job of grid.paint(), which is invoked after reset() completes. 

      Subclasses of BuggleWorld may override the reset() method to perform
      additional changes to state, such as creating walls and bagels. 
      Any such overridden methods *must* call super.reset() to perform the
      basic state initialization. No BuggleWorld state (other than ROWS and COLS) 
      can be assumed to exist before super.reset() is called, but arbitrary state
      changes can be performed after super.reset() is called (by which time,
      BuggleWorld is set to handle walls, bagels, buggles, etc.). Note that
      reset() should never try to display any changes in the grid; this is 
      handled by grid.paint() *after* reset() is called. The wall and bagel
      addition code "knows" not to draw anything during a call to reset(). 

    With the above changes, initState(), resetHookBefore(), and resetHookAfter()
    are not longer defined. Moreover, setDimensions() has been changed to simply
    update ROWS and COLS and *not* to perform any drawing. My testing on a wide 
    variety of BuggleWorld subclasses indicates that the new setup() and reset()
    can handle all behavior previously accomplished with initState(), resetHookBefore(), 
    resetHookAfter(), and the drawing version of setDimensions().

  * Improved problem with "new Buggle()" misbehaving in the case where there
    are multiple BuggleWorld instances "alive" at the same time. 
    The problem is that each Buggle needs to know *which* BuggleWorld it lives
    in when it's created. Although there can be an explicit BuggleWorld parameter
    to the Buggle constructor, this is normally left implicit; by default,
    its value is the contents of the class variable BuggleWorld.currentWorld. 

    It used to be that currentWorld was set to the most recently-created BuggleWorld.
    This was fine when there was only one BuggleWorld, but the move to 
    BuggleWorld applications encourages the use of multiple BuggleWorlds at the same time. 
    So I changed things so that currentWorld is also set to the world in which 
    a menu item is pressed. The only case where currentWorld won't be correct is if 
    a long-running run() method invoked by a RUN menu item invokes new Buggle() after 
    the user, in parallel, has created a new world or pressed a menu button in another world. 
    The only way to avoid such problems in general is to invoke new Buggle(<world>), 
    where <world> is an explicit BuggleWorld argument for the correct BuggleWorld instance. 

  * In setPosition(), added check that argument is a legal location.

  * Fixed bug that closing one BuggleWorld frame would close all BuggleWorld frames
    by changing JFrame.EXIT_ON_CLOSE to WindowConstants.DISPOSE_ON_CLOSE

  * Introduce Buggle command setCellColor(Color c) for what we've traditionally called
    paintCell(Color c). We should teach the new name in the future, though I've left
    the old one behind for upward compatibility. 

  * For consistency, made the following Buggle commands steppable:
    brushUp(), brushDown(), getColor(), setCellColor(Color c), paintCell(Color c), 

  * Fixed inner circle of bagel. Looks bad in Linux. 

  * Now treat forward(n) and backward(n) as atomic in terms of stepping/printing
    behavior. (Before, they were treated as n iterations of forward() or backward().)

  * Fixed bagel drawing on both Linux and Mac.

  * Updated contract to be consistent with above changes. 
  
  New TO-DO list: 
  * Test that window-closing fix works in all environments.
  * Test standard applications/applets on all environments 
    + As part of this, develop a page with all the standard worlds.
    + On Linux, the following applets don't work:
      - BagelBuggleWorld: Reset doesn't work. Can't be just ParameterFrame,
        since MazeBagelWorld works fine. 
      - HarvestWorld: Reset doesn't work. 


  * Fix deprecation problem with thread methods and unchecked problem with vector addElement. 
  * Check PS1 code/description and PS2 code/description and post these.
  * Add menu items for 
    + paintCell() (perhaps also call it setCellColor?)
    + getCellColor()
    + withCompanion()
    + toString()? 
    + dropInt()
    + dropString()
    + setDimensions
  * Add a checkbox that will print out all primitives executed in Run mode. 
  * Have mode for printing out all primitive operations. (might help to give buggles 
    unique names (e.g., B1, B2, etc.) so can distinguish operations on them. 
  * Change maze generation algorithm a la Jackie Weber project. 
  * change coordinate menus in setPosition menu to update 
    when dimensions change. 
  * Make buggles selectable and draggable with the mouse. 
  * Allow drawing walls with mouse. 
  * Create documentation with JavaDoc. 

  ----------------------------------------------------------------------
  [lyn, 8/22/07] 
  * Added runAsApplication() and main() methods to allow running 
    BuggleWorld as an application instead of as an applet. This is especially 
    useful for Dr. Java, which won't run applets directly. 
    Any subclass FooBuggleWorld of BuggleWorld can also be run as an application 
    by defining in it a main() method having the form:

       public static void main (String[] args) {
         runAsApplication(new FooBuggleWorld(), "FooBuggleWorld"); 
       }
  * Created a new Location class for immutable points and change Buggle and 
    BuggleWorld to use Locations rather than points. This simplifies the 
    Buggle contract, which no longer has to be careful about copying points. 
  * Improved implementation of Direction class.
  * Made code indentation consistent. 
  ----------------------------------------------------------------------
  [lyn, 11/[11-13]/06] 
   * Fixed markColorAt to return floorColor rather than null;
     this guarantees that getCellColor() never returns null.
   * Fixed fencepost error in wall initialization for grids
   * Removed unnecessary array initialization in wall initialization for grids
   * Fixed fencepost error in bagel drawing; also made inset smaller.
   * Fixed fencepost error in cellRectangle (pixel width is cellWidth-1, not -2,
     and similarly for cellHeight).
   * Improved drawCell not to paint rectangle twice.
   * Modified the drawing of the grid so that it is centered in window
   * Changed buggle drawing to use proportional insets
   * Added more delay menu options with finer-grained control over small delays
   * Parameterized bagel drawing over java.vm.vendor because Apple's VM has 
     a fencepost error in oval filling that makes bagels look terrible
     (especially in small grids).
  ----------------------------------------------------------------------
  [lyn, 9/6/04] Merged Wellesley changes with Bob Martin's version.
  ----------------------------------------------------------------------
  April, 2003: Modified again by Bob Martin at Middlebury for the Swing classes.
  He says "this time I tried to put 4 stars **** on the relevant lines"
  ----------------------------------------------------------------------
  [lyn, 7/01/03]: Lyn made Alice and Elena's thread fixes to this old version
   of buggle world that is used to make RandomBagelWorld. At some point
   need to bring all bagel worlds together into one bagel world!
 ----------------------------------------------------------------------
  [alice tiao '03 & Elena Machkasova, 1/22/03]
   * Added conditional to BuggleExecuter.reset() to "if (! (state==UNSTARTED)) && thread.isAlive())" from just 
    "if (! state == UNSTARTED )" in order to avoid SecurityExecption from trying to stop a dead thread.
   * Added "if( thread.isAlive() )" conditional to BuggleExecuter.pause() for the same SecurityException as above.
   * Problems remaining: Pressing buttons in this sequence: 
      "run + pause + reset" before run is complete &
     "run + pause + run" before run is complete occasionally
    causes applet to freeze.  Approach to solution: determine all states of thread entering pause, run, and reset.
  ----------------------------------------------------------------------
  [lyn, 2/12/00] 
  * Commented out mouse methods for drawing walls, which didn't work anyway and caused
    spurious array out of bounds errors.
  * Fixed fencepost error in BuggleGrid.drawMark(), which was missing one pixel in x and y direction.
    This was really noticeable for small grid cells, like those in FontWorld of PS2.
  * Cleaned up implementation of BuggleGrid.drawCell().
  * Fixed fencepost error in drawing border of grid that was obvious for small grid cells.
  * Fixed fencepost error in drawing of buggles, bagels, and strings and made default insets smaller.
  ----------------------------------------------------------------------
  [lyn, 2/10/00] added the following primitives:
     public Color getCellColor();
     public void setBrushDown(boolean b);
     public boolean isOverInt()
     public int pickUpInt();
     public String dropString();
     public boolean isOverString();
     public String pickUpString();
  ----------------------------------------------------------------------
  1999ish: Modified for Java 1.1 by Bob Martin at Middlebury for CX121: He says,
    "I tried to put 3 stars *** on the lines/section I modified"
  ----------------------------------------------------------------------
  [lyn, fall'99] added public int dropInt (int n)
  ----------------------------------------------------------------------
  [Franklyn Turbak (lyn), fall'97] Created BuggleWorld from scratch, 
  inspired by Logo Turtles, Pattis's Karel the Robot, and a similar
  world that Randy and Takis were using in CS110 around this time. 
  The name "Buggle" was the brainchild of my  pregnant wife, Lisa, who gave it 
  as the answer to "what's a cute name for these triangular creatures". 
  "Buggle" became one of the nicknames of our first child, Ohana. 

The "Still Would Be Nice To Do" List: 
 * Make left Buggle menu an optional separate window. 
 * Build in a console into Buggle world so don't have to depend on Java's console.
 * Fix output so that doesn't generate spurious updates. 
 * Make step do the right thing when no more steps to be taken.
 * Allow other instructions to be executed via menu between steps.
   (they are currently allowed but not handled correctly).
 * Draw buggles & bagels using coordinate transformations!
 * Give buggles faces!
 * Could change wall representation to be list/vector of walls (= vertical/horizontal + point)
 * Fix multiple redisplays when reset.
**************************************************************************/

import java.awt.*;
import java.awt.event.*; // ***
import java.applet.*;  // applet.* ??
import java.util.*;
import javax.swing.*;  // ****

//**************************************************************************
public class BuggleWorld extends JApplet
  implements ActionListener {
 
  public int rows = 9;    // Number of rows.
  public int cols = 9;    // Number of columns.
  public Vector buggles;    // The buggles in the world.
  public boolean bagels [] [];    // Bagels (at most one per cell).
  public String strings [] [];    //[9/6/04] Strings (at most one per cell).
  public Color marks [] [];     // The trail marks in the world;
  public boolean horizontalWalls [] [];  // leftmost points of horizontal wall segments.
  public boolean verticalWalls [] [];  // bottommost points of vertical wall segments.
  private boolean boundariesAreWalls = true;
  private final static Color backgroundColor = Color.green;
  private final static Color buttonBackgroundColor = Color.white;
  public static BuggleWorld currentWorld; // The currently active BuggleWorld
                                          // (the one most recently created or in which 
                                          //  a menu item has been pressed.)
                                          // This is used as an implicit argument to the 
                                          // Buggle constructor, to indicate the world in
                                          // which the new buggle will live. 
  public boolean inReset = false; // [lyn, 9/2/07] Tracks whether or not we are in the middle of a reset. 
  private BuggleGrid grid;
  private JPanel instructionPanel; // **** was Panel
  private JPanel controlPanel;
  private JPanel setPositionPanel;
  private JPanel setHeadingPanel;
  private JPanel setColorPanel;
  private JPanel setDelayPanel; // [9/6/04]
  //private JLabel output;
  public BuggleExecuter exec;
  private Buggle selectedBuggle;  // Currently selected buggle.
  // Menu items apply to this buggle. 
  // By default, it's the most recently created buggle. 
  // Color selected in colorChoices menu
  private int selectedX = 1; 
  private int selectedY = 1; 
  private Direction selectedHeading = Direction.EAST; 
  private Color selectedColor = Color.red; 
  //private Choice colorChoices;
  private JComboBox colorChoices; // ****
  //private Choice headingChoices;
  private JComboBox headingChoices; // ****
  //private Choice xChoices;
  //private Choice yChoices;
  private JComboBox xChoices; // ****
  private JComboBox yChoices; // ****
  private JComboBox delayChoices; // [9/6/04]
  private int buggleDelay = 0; // [9/6/04] Amount of time buggle waits between moves.
  // [9/6/04] Can be used to slow down buggle. 
  private boolean debugOn = false;
  
  public void debugPrintln(String s) {
    if (debugOn) 
      System.out.println("Debug: " + s);
  }
  
  //----------------------------------------------------------------------
  /*** [lyn, 8/22/07] New code for running an applet as an application ***/
  
  public static void main (String[] args) {
    runAsApplication(new BuggleWorld(), "BuggleWorld"); 
  }
  
  public static void runAsApplication (final BuggleWorld applet, final String name) {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this buggle world applet. 
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() { // this is Java's thread run() method, not BuggleWorlds!
        JFrame.setDefaultLookAndFeelDecorated(true); // enable window decorations. 
        JFrame frame = new JFrame(name); // create and set up the window.
        frame.setSize(700, 400); // Default frame size (should make these settable variables!)

 // [lyn. 8/30/07] Using EXIT_ON_CLOSE empirically exits all instances of an application.
 //   Use DISPOSE_ON_CLOSE to get rid of just one. 
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 

        // [lyn. 8/30/07] Need to add to frame and make visible *before* init 
        //   so that attempts to reset dimensions will work. 
        frame.add(applet, BorderLayout.CENTER); // add applet to window
        frame.setVisible(true); // display the window.
        applet.init(); // initialize the applet
       // [lyn. 8/30/07] Need to make visible again *after* init in case
       //   something like setDimensions is not called in init. 
        frame.setVisible(true); // display the window.
 
      }
    });
  }
  //----------------------------------------------------------------------

  // [lyn, 9/1/07] Long-awaited restructuring of init() and reset()!
  public void init() {
    debugPrintln("Start init();");
    currentWorld = this; // Make this world the current "active" world.
                         // Used as the default world in which new Buggles will live.
    debugPrintln("setup();");
    setup(); // One-time initializations from BuggleWorld subclasses, 
             // such as setDimensions and creation of ParameterFrames.
             // Any such initializations cannot depend on the state of BuggleWorld
             // or its grid, since these haven't been created yet. 
    createGUI(); // Create the BuggleWorld GUI. This allocates space for the grid,
                 // but does not draw it yet. 
    debugPrintln("Making executer");
    exec = new BuggleExecuter(this); // BuggleExecuter for the RUN thread. 
    inReset = true; // [lyn, 9/2/07] Track whether we're in reset, for cellChanged();
                    //   This *cannot* be done in reset() itself, since that's overridable.
    reset(); // Sets the state of BuggleWorld. 
             // This can be overridden by subclasses, but all overriding methods 
             // must call super.reset(). Any work done before super.reset() should
             // only set instance variables, since the world state has not be reset yet. 
             // But work done after super.reset() can use the world state
             // (e.g., walls, bagels, buggles, etc.).
             // Note that reset() can also be called explicitly by pressing the reset button.
    inReset = false; // [lyn, 9/2/07] Track whether we're in reset, for cellChanged();
    // Note: grid.paint() is not a part of reset() itself, because reset() is overridable
    // by programmer, and don't want to draw grid until know all of the state changes. 
    grid.paint(); // draw the BuggleWorld grid after all state updates have been made. 
  }


  public void setup () {
    // Default is to do nothing. Intended to be overridden by subclasses. 
    // Typical actions in setup() are setDimensions() and creating a ParameterFrame.
  }


  // Sets the rows and cols of this BuggleWorld, but does not cause anything to be drawn
  // or redrawn. The drawing is done by the reset() method. 
  public void setDimensions (int cols, int rows) {
    debugPrintln("Start setDimensions(" + cols + ", " + rows + ")");
    this.cols = cols;
    this.rows = rows;
  }

  // [lyn, 9/1/07] Long-awaited restructuring of init() and reset()!
  // reset() is the key BuggleWorld state-setting method, called both by init() 
  // and pressing the RESET button. 
  // It is assumed that rows and cols are appropriately set before calling reset(),
  // that the BuggleExecuter exec already exists, and that all user-interface components
  // have been created. This method is responsible for creating the state associated
  // with BuggleWorld (cells, walls, bagels, buggles, etc.) and displaying the state 
  // in the grid. 
  public void reset() {
    debugPrintln("reset()");
    initializeWalls();
    marks = new Color [cols+1] [rows+1]; // Entries will be null unless set otherwise.
    initializeBagels();
    initializeStrings(); //[9/6/04]
    buggles = new Vector();
    selectedBuggle = null;
    exec.reset();
    debugPrintln("Finish BuggleWorld.reset()");
  } 
  
  // Creates the BuggleWorld GUI
  public void createGUI() {
    grid = new BuggleGrid(this);  // Make grid in which buggles are displayed
    this.makeInstructionPanel(); // Make panel for instructions to the selected buggle
    this.makeControlPanel(); // Make panel for controlling execution of the buggle program
    // this.makeOutput();  // Make the area for displaying textual feedback

    Container c = getContentPane(); //****
    debugPrintln("Setting world layout");
    c.setLayout( new BorderLayout() );
    setBackground( backgroundColor );
    c.add( "Center", grid );
    c.add( "West", instructionPanel );
    c.add( "South", controlPanel ); 
    // this.add("North", output);
  }

  private void makeInstructionPanel() {
    // System.out.println("Making instruction panel");
    instructionPanel = new JPanel();
    //Container cp = instructionPanel.getContentPane(); //****
    //cp.setLayout(new GridLayout(11,1));
    instructionPanel.setLayout(new GridLayout(12,1));
    instructionPanel.setBackground(Color.green);
    
    newInstructionPanelItem("new Buggle()");
    newInstructionPanelItemPair("forward()", "backward()");
    newInstructionPanelItemPair("left()","right()");
    newInstructionPanelItemPair("getPosition()", "getHeading()");
    newInstructionPanelItemPair("getColor()", "isBrushDown()");
    newInstructionPanelItemPair("isFacingWall()", "isOverBagel()");
    
    //xChoices = new Choice(); // ****
    String xNames[] = 
      // [lyn, 9/1/07] Change list to include one beyond default width,
      // to allow testing illegal location in setPosition.
      {"1","2","3","4","5","6","7","8","9","10"};
    xChoices = new JComboBox( xNames );
    xChoices.setMaximumRowCount( 8 );
    xChoices.addActionListener(this); 
    //for (int x = 1; x <= 8; x++) {
    // xChoices.add(Integer.toString(x));
    //}
    
    //yChoices = new Choice(); // ****
    String yNames[] = 
      // [lyn, 9/1/07] Change list to include one beyond default width,
      // to allow testing illegal location in setPosition.
      {"1","2","3","4","5","6","7","8","9","10"};
    yChoices = new JComboBox( yNames );
    yChoices.setMaximumRowCount( 8 );
    yChoices.addActionListener(this); 
    //for (int y = 1; y <= 8; y++) {
    // yChoices.add(Integer.toString(y));
    //}
    
    setPositionPanel = new JPanel();
    //Container c2 = setPositionPanel.getContentPane(); //****
    //c2.setLayout(new FlowLayout());
    setPositionPanel.setLayout(new FlowLayout());
    JButton setPositionButton = new JButton("setPosition");
    setPositionButton.setBackground(buttonBackgroundColor);
    setPositionPanel.add(setPositionButton);
    setPositionButton.addActionListener(this); // ***
    setPositionPanel.add(new JLabel("(new Location("));
    setPositionPanel.add(xChoices);
    setPositionPanel.add(new JLabel(","));
    setPositionPanel.add(yChoices);
    setPositionPanel.add(new JLabel("))"));
    newInstructionPanelItem(setPositionPanel);
    
    
    //headingChoices = new Choice(); // ****
    String hNames[] = 
      {"Direction.EAST","Direction.NORTH","Direction.WEST","Direction.SOUTH"};
    headingChoices = new JComboBox( hNames );
    headingChoices.setMaximumRowCount( 4 );
    headingChoices.addActionListener(this); 
    //headingChoices.add("Direction.EAST");
    //headingChoices.add("Direction.NORTH");
    //headingChoices.add("Direction.WEST");
    //headingChoices.add("Direction.SOUTH");
    
    setHeadingPanel = new JPanel();
    //Container c3 = setHeadingPanel.getContentPane(); //****
    //c3.setLayout(new FlowLayout());
    setHeadingPanel.setLayout(new FlowLayout());
    JButton setHeadingButton = new JButton("setHeading");
    setHeadingButton.setBackground(buttonBackgroundColor);
    setHeadingPanel.add(setHeadingButton);
    setHeadingButton.addActionListener(this); // ***
    setHeadingPanel.add(new JLabel("("));
    setHeadingPanel.add(headingChoices);
    setHeadingPanel.add(new JLabel(")"));
    newInstructionPanelItem(setHeadingPanel);
    
    //colorChoices = new Choice(); // ****
    String cNames[] = 
      {"Color.red","Color.green","Color.blue","Color.yellow","Color.cyan","Color.magenta"};
    colorChoices = new JComboBox( cNames );
    colorChoices.setMaximumRowCount( 6 );
    colorChoices.addActionListener(this); 
    //colorChoices.add("Color.red");
    //colorChoices.add("Color.green"); // also line 363: cast getSelectedItem()
    //colorChoices.add("Color.blue");
    //colorChoices.add("Color.yellow");
    //colorChoices.add("Color.cyan");
    //colorChoices.add("Color.magenta");
    
    //??colorChoices.setBackground(selectedColor);
    
    setColorPanel = new JPanel();
    //Container c4 = setColorPanel.getContentPane(); //****
    //c4.setLayout(new FlowLayout());
    setColorPanel.setLayout(new FlowLayout());
    JButton setColorButton = new JButton("setColor");
    setColorButton.setBackground(buttonBackgroundColor);
    setColorPanel.add(setColorButton);
    setColorButton.addActionListener(this); // ***
    setColorPanel.add(new JLabel("("));
    setColorPanel.add(colorChoices);
    setColorPanel.add(new JLabel(")"));
    newInstructionPanelItem(setColorPanel);
    
    
    // [lyn, 9/6/04] All delay stuff is new 
    // [lyn, 11/11/06] Allow more smaller values with delay.
    String dNames[] = 
      {"0","1","2","4","8","16","32","64","128","256","512","1024"};
    // {"50","100","200","400", "800"};
    delayChoices = new JComboBox( dNames );
    delayChoices.setMaximumRowCount( 6 );
   delayChoices.addActionListener(this); 
    
    setDelayPanel = new JPanel();
    setDelayPanel.setLayout(new FlowLayout());
    JButton setDelayButton = new JButton("setDelay");
    setDelayButton.setBackground(buttonBackgroundColor);
    setDelayPanel.add(setDelayButton);
    setDelayButton.addActionListener(this); // ***
    setDelayPanel.add(new JLabel("("));
    setDelayPanel.add(delayChoices);
    setDelayPanel.add(new JLabel(")"));
    newInstructionPanelItem(setDelayPanel);
    
    newInstructionPanelItemPair("brushDown()","brushUp()");
    // newInstructionPanelItem("brushDown()");
    // newInstructionPanelItem("brushUp()");
    newInstructionPanelItemPair("dropBagel()", "pickUpBagel()");
  }
  
  private void newInstructionPanelItem (String s) {
    JButton b = new JButton(s);
    b.setBackground(buttonBackgroundColor);
    instructionPanel.add(b);
    b.addActionListener(this); // ***
  }
  
  private void newInstructionPanelItemPair (String s1, String s2) {
    JPanel p = new JPanel();
    //Container c = p.getContentPane(); //****
    //c.setLayout(new FlowLayout());
    p.setLayout(new GridLayout(1,2));
    p.setBackground(buttonBackgroundColor);
    JButton b1 = new JButton(s1);
    b1.setBackground(buttonBackgroundColor);
    JButton b2 = new JButton(s2);
    b2.setBackground(buttonBackgroundColor);
    p.add(b1);
    p.add(b2);
    instructionPanel.add(p);
    b1.addActionListener(this); // ***
    b2.addActionListener(this);
  }
  
  public void newInstructionPanelItem (Component c) {
    c.setBackground(buttonBackgroundColor);
    instructionPanel.add(c);
  }
  
  private void makeControlPanel () {
    // System.out.println("Making control panel");
    controlPanel = new JPanel();
    //Container c = controlPanel.getContentPane(); //****
    //c.setLayout(new FlowLayout());
    controlPanel.setLayout(new GridLayout(1,3));
    controlPanel.setBackground(Color.blue);
    
    newControlPanelItem("Step");
    newControlPanelItem("Run");
    newControlPanelItem("Pause");
    newControlPanelItem("Reset");
  }
  
  private void newControlPanelItem (String s) {
    JButton b = new JButton(s);
    b.setBackground(buttonBackgroundColor);
    controlPanel.add(b);
    b.addActionListener(this); // ***
  }
  
  private void makeOutput() {
    // The following may not be true -- it may be an artifact of my misunderstanding,
    // since Labels give exacly the same problem.
    
    // TextFields are broken in Cafe!
    // When use TextFields here, spurious paints are generated and
    // strange things happen to the graphics contexts of other components.
    //output = new TextField("", 30);
    //output.setBackground(Color.white);
    //output.setForeground(Color.blue);
    //output.setEditable(false);
    
    
    // output = new JLabel("Output");
    // output.setAlignment(JLabel.CENTER);
  }
  
  
  /* *** old version ***
     public boolean handleEvent(Event evt) {
     if (evt.id == Event.WINDOW_DESTROY) {
     System.exit(0);
     }
     return super.handleEvent(evt);  
     }
  */
  
  // [lyn, 9/1/07] Note: although it might be nice to handle menu button presses
  //   by anonymous inner classes, one advantage of using actionPerformed is
  //   that it makes it easy to set the currentWorld variable for every such action. 
  // 
  // *** old *** public boolean action(Event event, Object arg) {
  //  try {
  public void actionPerformed( ActionEvent event ) {    
    Object src = event.getSource(); //[9/6/04]
    String arg = event.getActionCommand(); // ***   
    // [lyn, 9/1/07] Setting currentWorld when menu item pressed
    //    gives better support for multiple worlds. In particular,
    //    new Buggle() will create a buggle in the world in which 
    //    one of the following two actions has most recently occurred:
    //    (1) the world was created
    //    (2) a menu item in the world was pressed. 
    // 
    //    The only case where this behavior won't be correct is if 
    //    a long-running run() method invoked by a RUN method 
    //    invokes new Buggle() after the user, in parallel, has
    //    created a new world or pressed a menu button in another world. 
    //    The only way to avoid such problems in general is to 
    //    invoke new Buggle(<world>), where <world> is an explicit
    //    BuggleWorld argument for the correct BuggleWorld instance. 
    currentWorld = this; 
    // [lyn, 9/2/07] Reset this variable, in case it's gotten out of sync: 
    inReset = false; 
    debugPrintln("actionPerformed: command = " + arg + "; src = " + src);
    if (arg.equals("Run")) {
      debugPrintln("Calling paint from run");
      // System.out.println("Run");
      // initState();
      // grid.paint(); // changed **** // [lyn, 9/1/07] don't think this is necessary.
      exec.run();
    } else if (arg.equals("Step")) {
      exec.step();
    } else if (arg.equals("Pause")) {
      exec.pause();
    } else if (arg.equals("Reset")) {
      inReset = true; // [lyn, 9/2/07] Track whether we're in reset, for cellChanged();
                      //   This *cannot* be done in reset() itself, since that's overridable.
      reset(); // [lyn, 9/1/07] this is now very simple
      inReset = false; // [lyn, 9/2/07] Track whether we're in reset, for cellChanged();
      // Note: grid.paint() is not a part of reset() itself, because reset() is overridable
      // by programmer, and don't want to draw grid until know all of the state changes. 
      grid.paint(); // draw the BuggleWorld grid after all state updates have been made. 
    } else if (arg.equals("new Buggle()")) {
      clearOutput();
      Buggle b = new Buggle(); // Note: new will automatically make b the selected buggle.
    } else if (arg.equals("forward()")) {
      clearOutput();
      selectedBuggle().forward();
    } else if (arg.equals("backward()")) {
      clearOutput();
      selectedBuggle().backward();
    } else if (arg.equals("left()")) {
      clearOutput();
      selectedBuggle().left();
    } else if (arg.equals("right()")) {
      clearOutput();
      selectedBuggle().right();
    } else if (arg.equals("getPosition()")) {
      clearOutput();
      Location p = selectedBuggle().getPosition();
      this.printValue(this.locationString(p));
    } else if (arg.equals("getHeading()")) {
      clearOutput();
      Direction d = selectedBuggle().getHeading();
      this.printValue(this.directionString(d));
    } else if (arg.equals("getColor()")) {
      clearOutput();
      Color c = selectedBuggle().getColor();
      this.printValue(this.colorString(c));
    } else if (arg.equals("isBrushDown()")) {
      clearOutput();
      boolean b = selectedBuggle().isBrushDown();
      this.printValue(this.booleanString(b));
      
    } else if (arg.equals("setPosition")) {
      clearOutput();
      String arg4 = (String)xChoices.getSelectedItem(); // *** & ****     
      selectedX = Integer.parseInt((String) arg4, 10);
      arg4 = (String)yChoices.getSelectedItem(); // ***     
      selectedY = Integer.parseInt((String) arg4, 10);
      selectedBuggle().setPosition( new Location(selectedX, selectedY) );
      
    } else if (arg.equals("setHeading")) {
      clearOutput();
      String arg3 = (String)headingChoices.getSelectedItem(); // ***     
      //selectedBuggle().setHeading(selectedHeading);
      
      if (arg3.equals("Direction.NORTH")) {
 //selectedHeading = Direction.NORTH;
 selectedBuggle().setHeading( Direction.NORTH );
      } else if (arg3.equals("Direction.EAST")) {
 //selectedHeading = Direction.EAST;
 selectedBuggle().setHeading( Direction.EAST );
      } else if (arg3.equals("Direction.SOUTH")) {
 selectedBuggle().setHeading( Direction.SOUTH );
 //selectedHeading = Direction.SOUTH;
      } else if (arg3.equals("Direction.WEST")) {
 selectedBuggle().setHeading( Direction.WEST );
 //selectedHeading = Direction.WEST;
      }
      
    } else if (arg.equals("setDelay") || (src == delayChoices)) { // [lyn, 9/6/04]
      clearOutput();
      int delay = Integer.parseInt((String) delayChoices.getSelectedItem());
      setBuggleDelay(delay);
      
    } else if (arg.equals("setColor")) {
      clearOutput();
      String arg2 = (String)colorChoices.getSelectedItem(); // ***     
      //selectedBuggle().setColor( Color.blue );
      // *** old selectedBuggle().setColor(selectedColor);
      if (arg2.equals("Color.red")) {
 //selectedColor = Color.red;
 selectedBuggle().setColor( Color.red );
      } else if (arg2.equals("Color.green")) {
 //selectedColor = Color.green;
 selectedBuggle().setColor( Color.green );
      } else if (arg2.equals("Color.blue")) {
 //selectedColor = Color.blue;
 selectedBuggle().setColor( Color.blue );
      } else if (arg2.equals("Color.yellow")) {
 //selectedColor = Color.yellow;
 selectedBuggle().setColor( Color.yellow );
      } else if (arg2.equals("Color.cyan")) {
 //selectedColor = Color.cyan;
 selectedBuggle().setColor( Color.cyan );
      } else if (arg2.equals("Color.magenta")) {
 //selectedColor = Color.magenta;
 selectedBuggle().setColor( Color.magenta );
      }
      // *** old *** } else if (event.target == xChoices) {
    } else if (src == xChoices) {
      //String arg4 = xChoices.getSelectedItem(); // ***     
      //selectedX = Integer.parseInt((String) arg4, 10);
      //xAction(arg4);
    } else if (src == yChoices) {
      //String arg5 = yChoices.getSelectedItem(); // ***     
      //yAction(arg5);
    } else if (src == headingChoices) {
      //headingAction(arg);
    } else if (src == colorChoices) {
      colorAction(arg);
    } else if (arg.equals("brushDown()")) {
      clearOutput();
      selectedBuggle().brushDown();
    } else if (arg.equals("brushUp()")) {
      clearOutput();
      selectedBuggle().brushUp();
    } else if (arg.equals("isFacingWall()")) {
      clearOutput();
      boolean b = selectedBuggle().isFacingWall();
      this.printValue(this.booleanString(b));
    } else if (arg.equals("isOverBagel()")) {
      clearOutput();
      boolean b = selectedBuggle().isOverBagel();
      this.printValue(this.booleanString(b));
    } else if (arg.equals("pickUpBagel()")) {
      clearOutput();
      selectedBuggle().pickUpBagel();
    } else if (arg.equals("dropBagel()")) {
      clearOutput();
      selectedBuggle().dropBagel();
    } else {
      System.out.println("Unhandled arg: " + arg);
      // *** return super.action(event, arg);
    }
    //repaint(); //****
    //this.paint(); // ***
    // *** old *** return true;
    //} catch (BuggleException e) {
    // printError(e.getMessage());
    // return true;
    //}
  }
  
  private void xAction (Object arg) {
    selectedX = Integer.parseInt((String) arg, 10);
  }
  
  private void yAction (Object arg) {
    selectedY = Integer.parseInt((String) arg, 10);
  }
  
  private void headingAction (Object arg) {
    //if (arg.equals("Direction.NORTH")) {
    // selectedHeading = Direction.NORTH;
    //} else if (arg.equals("Direction.EAST")) {
    // selectedHeading = Direction.EAST;
    //} else if (arg.equals("Direction.SOUTH")) {
    // selectedHeading = Direction.SOUTH;
    //} else if (arg.equals("Direction.WEST")) {
    // selectedHeading = Direction.WEST;
    //}
  }
  
  private void colorAction (Object arg) {
    /*
      arg = colorChoices.getSelectedItem(); // ***     
      //selectedBuggle().setColor( Color.blue );
      
      if (arg.equals("Color.red")) {
      selectedColor = Color.red;
      } else if (arg.equals("Color.green")) {
      selectedColor = Color.green;
      } else if (arg.equals("Color.blue")) {
      selectedColor = Color.blue;
      } else if (arg.equals("Color.yellow")) {
      selectedColor = Color.yellow;
      } else if (arg.equals("Color.cyan")) {
      selectedColor = Color.cyan;
      } else if (arg.equals("Color.magenta")) {
      selectedColor = Color.magenta;
      } */
    
    //colorChoices.setBackground( selectedColor );
  }
  
  
  protected String locationString (Location p) {
    return p.toString();
  }
  
  protected String directionString (Direction d) {
    return "Direction[dir=" + d + "]"; 
  }
  
  protected String colorString (Color c) {
    return "Color[red=" + c.getRed() 
      + ", blue=" + c.getBlue()
      + ", green=" + c.getGreen()
      + "]";
  }
  
  private String booleanString (boolean b) {
    if (b) {
      return "true";
    } else {
      return "false";
    }
  }
  
  public void printError (String s) {
    // Print error message in output area.
    // System.out.println("printError: " + s);
    String message = "Buggle Error: " + s;
    System.out.println(message);
    // Output is broken for now.
    // output.setForeground(Color.red);
    // output.setText(message);
  }
  
  public void printValue (String s) {
    // Print value in output area.
    // System.out.println("printValue: " + s);
    String message = "Value: " + s;
    System.out.println(message);
    // Output is broken for now.
    // output.setForeground(Color.blue);
    // output.setText(message);
  }
  
  public void printInstruction (String s) {
    // Print instruction in output area.
    // System.out.println("printInstruction: " + s);
    System.out.println(s);
    // Output is broken for now.
    // output.setForeground(Color.black);
    // output.setText(s);
  }
  
  
  public void clearOutput() {
    //System.out.println("clearOutput: ");
    //output.setText("");
  }
  
  
  public Buggle selectedBuggle () {
    // Return the currentBuggle, or yell if it's null.
    if (selectedBuggle == null) {
      throw new BuggleException ("No buggle is selected!");
    } else {
      return selectedBuggle;
    }
  }
  
  //*** to here
  
  protected void selectBuggle (Buggle b) {
    Buggle previousSelected = selectedBuggle;
    selectedBuggle = b;
    this.buggleChanged(selectedBuggle);
    if (previousSelected != null) {
      this.buggleChanged(previousSelected);
    }
  }
  
  private void initializeBagels() {
    bagels = new boolean [cols] [rows];
    for (int x = 0; x < cols; x ++) {
      for (int y = 0; y < rows; y++) {
 bagels[x][y] = false;
      }
    }
  }
  
  private void initializeStrings() {//[9/6/04]
    debugPrintln("intializeStrings(); cols = " + cols + "; rows = " + rows);
    strings = new String [cols] [rows];
  }
  
  protected void initializeWalls () {
    debugPrintln("Start initializeWalls");
    // [lyn, 11/11/06] Fixed the wall dimensions.
    // Also, since array slots are filled with false by default,
    // there's no need to explicitly initialize them. 
    debugPrintln("horizontalWalls = new boolean[" + cols + "],[" + (rows+1) + "]");
    debugPrintln("verticalWalls = new boolean[" + (cols+1) + "],[" + rows + "]");
    horizontalWalls = new boolean [cols] [rows+1];
    verticalWalls = new boolean [cols+1] [rows];
    
    // [lyn, 11/11/06] Fixed the wall initialization, which had fencepost errors before
    // Add boundaries at walls if specified. 
    debugPrintln("Initializing horizontalWalls");
    for (int x = 0; x < cols; x++) {
      horizontalWalls[x][0] = boundariesAreWalls;
      horizontalWalls[x][rows] = boundariesAreWalls;
    }
    debugPrintln("Initializing verticalWalls");
    for (int y = 0; y < rows; y++) {
      verticalWalls[0][y] = boundariesAreWalls;
      verticalWalls[cols][y] = boundariesAreWalls;
    }
    debugPrintln("Finish initializeWalls");
  }
  
  // [lyn, 11/11/06] Fixed fencepost error in this method
  public void addHorizontalWall (int x, int y) {
    if ((0 <= x) && (x < cols) && (0 <= y) && (y <= rows)) {
      horizontalWalls[x][y] = true;
    } else {
      throw new BuggleException(
    "addHorizontalWall: point out of range -- ("
    + x + ", " + y + ")");
    }
  }
  
  // [lyn, 11/11/06] Fixed fencepost error in this method 
  public void addVerticalWall (int x, int y) {
    if ((0 <= x) && (x <= cols) && (0 <= y) && (y < rows)) {
      verticalWalls[x][y] = true;
    } else {
      throw new BuggleException(
    "addVerticalWall: point out of range -- ("
    + x + ", " + y + ")");
    }
  }
  
  // public void start () {
  //   // System.out.println("start()");
  //  //debugPrintln("Calling BuggleGraid.paint() from start()");
  //  //**** grid.paint(); // **** this caused problems - too soon? 
  // }
  
  public void start () {
    // System.out.println("start()");
    debugPrintln("Calling BuggleGraid.paint() from start()");
    grid.paint();  
  }
  
  public void paint( Graphics g ) {
    //debugPrintln("BuggleWorld asked by system to paint(g).");
    super.paint( g );
  }
  
  // public void paintComponent( Graphics g ) { // need to extend JPanel for this?
  //  //debugPrintln("BuggleWorld asked by system to paint(g).");
  //  super.paintComponent( g );
  // }
  
  public void run () {
    // This is a hook that subclasses can override without having to worry 
    // about other operations that might be performed by start().
    // By default, does nothing. 
    // this.printError("Default run() behavior of BuggleWorld is to do nothing.");
  }

  // New predicate for checking if location is in the buggle grid
  public boolean isLocationInGrid (Location p) {
    return (p.x > 0) && (p.x <= cols) && (p.y > 0) && (p.y <= rows);
  }
  
  public Location addCoordinates(Location p1, Location p2) {
    int newx = ((p1.x + p2.x - 1) % cols) + 1;
    if (newx <= 0) 
      newx = newx + cols;
    int newy = ((p1.y + p2.y - 1) % rows) + 1;
    if (newy <= 0) 
      newy = newy + rows;
    Location p = new Location(newx, newy);
    /* System.out.println("Add coords: p1 = " + p1 
       + "; p2 = " + p2 
       + "; result = " + p); */
    return p;
  }
  
  public boolean isBagelAt(Location p) {
    return bagels[p.x - 1][p.y - 1];
  }
  
  public void addBagel(Location p) {
    bagels[p.x - 1][p.y - 1] = true;
    cellChanged(p);
  }
  
  
  public void addBagel(int x, int y) {
    // This version is called from interface and does *not* update visual rep.
    bagels[x - 1][y - 1] = true;
  }
  
  public void removeBagel(Location p) {
    bagels[p.x - 1][p.y - 1] = false;
    cellChanged(p);
  }
  
  public void removeBagel(int x, int y) {
    // This version is called from interface and does *not* updated visual rep.
    bagels[x - 1][y - 1] = false;
  }
  
  
  public void addString(Location p, String s) { //[9/6/04]
    strings[p.x - 1][p.y - 1] = s;
    cellChanged(p);
  }
  
  public boolean isStringAt(Location p) { // [9/6/04]
    return (strings[p.x - 1][p.y - 1]) != null;
  }
  
  public String getStringAt(Location p) { // [9/6/04]
    String s = strings[p.x - 1][p.y - 1];
    String result;
    if (s == null) {
      result = "";
    } else {
      result= s;
    }
    // System.out.println("getStringAt(" + p + ") = " + result);
    return result;
  } 
  
  public void add (Buggle b) {
    // System.out.println("Buggles=" + buggles );
    buggles.addElement(b);
    debugPrintln("Calling BuggleGrid.draw() from BuggleWorld.add(Buggle)");
    grid.draw(b);
  }
  
  public boolean wallInDirection(Location p, Direction d) {
    // Indicate whether there is a wall in the d direction of the grid cell at point p.
    if (d == Direction.NORTH) {
      return horizontalWalls[p.x-1][p.y];
    } else if (d == Direction.EAST) {
      return verticalWalls[p.x][p.y-1];
    } else if (d == Direction.SOUTH) {
      return horizontalWalls[p.x-1][p.y-1];
    } else if (d == Direction.WEST) {
      return verticalWalls[p.x-1][p.y-1];
    } else {
      throw new BuggleException("Shouldn't happen: wallInDirection");
    }
  }
  
 public boolean isHorizontalWallAt(int x, int y) {
  return horizontalWalls[x][y];
 }
 
 public boolean isVerticalWallAt(int x, int y) {
  return verticalWalls[x][y];
 }
  
  void cellChanged(Location p) {
    // [lyn, 9/2/07] Only want to display changes if not reseting the state. 
    //   Otherwise, can see undesirable artifacts -- e.g., bagel creation --
    //   between time when Reset button is pressed and time when grid is redrawn
    //   for the new state. 
    if (! inReset) {
      debugPrintln("Calling drawCell from cellChanged " + p);
      grid.drawCell(p);
    }
  }
  
  void buggleChanged(Buggle b) {
    debugPrintln("Calling drawCell from buggleChanged ");
    grid.drawCell(b.position());
  }
  
  public void buggleMoved(Buggle b, Location oldpos, Location newpos) {
    // We have been informed that B has moved from oldpos to newpos.
    // For multiple buggles, should move buggle to top!
    debugPrintln("Calling drawCell (1) from buggleMoved");
    grid.drawCell(oldpos);
    debugPrintln("Calling drawCell (2) from buggleMoved");
    grid.drawCell(newpos);
  }
  
  /* 
  public void addBagel(Location p) {
    // Adds a bagel at coordinate p. Assumes at most one bagel at any position. 
    // So adding a bagel to a cell with a bagel already in it has no effect. 
    bagels.addElement(p); // represent a bagel as a point.
  }
  
  public void removeBagel(Location p) {
    for (int i = bagels.size() - 1; i >= 0; i--) {
      if (((Location) bagels.elementAt(i)).equals(p)) {
 bagels.removeElementAt(i);
      }
    }
  }
  
  public boolean bagelAt(Location p) {
    for (int i = bagels.size() - 1; i >= 0; i--) {
      if (((Location) bagels.elementAt(i)).equals(p)) {
 return true;
      }
    }
    return false;
  }
  */
  
  public void draw (Buggle b) {
    debugPrintln("Draw buggle " + b);
    debugPrintln("Position = " + nullify(b.position()));
    grid.draw(b);
    //repaint(); //****
  }
  
  private String nullify (Object obj) {
    if (obj == null)
      return "null";
    else
      return obj.toString();
  }
  
  public void markTrail(Location p, Color c) {
    marks[p.x][p.y] = c;
  }

  // [lyn,11/11/06] Modified to handle null case correctly
  public Color markColorAt(Location p) {
    Color c = marks[p.x][p.y];
    if (c == null) {
      return grid.getFloorColor();
    } else {
      return c;
    }
  }
  
  protected boolean withCompanion (Buggle b) {
    Location pos = b.position();
    for (int i = 0; i < buggles.size(); i++) {
      Buggle x = (Buggle) buggles.elementAt(i);
      if (pos.equals(x.position()) && (x != b)) 
 return true;
    }
    return false;
  }
  
  protected int getBuggleDelay () { //[9/6/04]
    return buggleDelay;
  }
  
  private void setBuggleDelay (int i) { //[9/6/04]
    buggleDelay = i; 
  }
  
}

//**************************************************************************
class BuggleGrid extends Canvas //{
  implements MouseListener, MouseMotionListener { // ***
  
  /* A rectangular area of the BuggleWorld applet that displays the state of the world */
  
  public BuggleWorld world;
  // private Graphics gfx; // Graphics context of this grid
  private int cellWidth;
  private int cellHeight;
  private Rectangle gridRect;
  private final static Color floorColor = Color.white;
  private final static Color gridLineColor = Color.green;
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
  
  public BuggleGrid(BuggleWorld bw) {
    world = bw;
    // Note: do *not* call makeGrid() here, since Canvas has not yet been allocated real-estate on screen.
  }
  
  public void makeGrid() {
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
    world.debugPrintln("BuggleGrid.paintGrid()");

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
    
    // Paint buggles 
    Enumeration bugs = world.buggles.elements();
    while ( bugs.hasMoreElements() ) {
      Buggle next = (Buggle) bugs.nextElement();
      this.draw(next);
    }
    // [9/6/04] Causes a painting loop!
    // world.debugPrintln("calling repaint() from BuggleGrid.paintGrid()");
    // repaint(); //***** needed this ****
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
    world.debugPrintln("BuggleGrid paint();");
    this.paint( gfx ); //this.getGraphics()
  }
  
  public void paint( Graphics g ) {
    // super.paint(g); //****?? paintComponent??
    world.debugPrintln("BuggleGrid paint(Graphics g);");
    paintGrid();
    // System.out.println("Start Paint");
    // resize();
    // Paint floor:
    //System.out.println("Begin printing all buggles.");
    Enumeration bugs = world.buggles.elements();
    //System.out.println("Begin printing all buggles1.");
    while (bugs.hasMoreElements()) {
      //System.out.println("Begin printing all buggles2.");
      Buggle b = (Buggle) bugs.nextElement();
      //System.out.println("Begin printing all buggles3.");
      world.debugPrintln("Calling drawBuggle from BuggleGrid.paint(Graphics g)");
      this.draw(b);
    }
    //System.out.println("End printing all buggles.");
    // System.out.println("Stop Paint");
  }
  
  public void draw( Buggle b ) {
    //System.out.println("Draw buggle " + b);
    //System.out.println("Position = " + nullify(b.position()));
    world.debugPrintln("Calling drawCell from draw(Buggle)");
    
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
      //System.out.println("Mark color = " + nullify(c));
    
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
    
      // Draw any bagels
      if (world.isBagelAt(p)) {
 drawBagel(p, c);
      }
    
      // Draw any strings [9/6/04]
      if (world.isStringAt(p)) {
 drawString(p, c);
      }
    
      // Draw any buggles in this cell
      drawBugglesAt(p);
      
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
      //System.out.println("drawCell yielding.");
      //**** Thread.yield(); //***
      //System.out.println("drawCell after yield.");
    }
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
  
  public void drawBugglesAt (Location p) {
    // System.out.println("drawBugglesAt " + p);
    // System.out.println("buggles = " + nullify(world.buggles));
    /*for (int i = world.buggles.size() - 1; i >= 0; i--) { 
      Buggle b = (Buggle) world.buggles.elementAt(i);
      if (b.getPosition().equals(p)) 
      b.drawInRect(this.getGraphics(), cellRectangle(p));
      break;
      }*/
    // Draw all buggles at current position.
    for (int i = 0; i < world.buggles.size(); i++) { 
      Buggle b = (Buggle) world.buggles.elementAt(i);
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
  // System.out.println("Mouse Drag Event" + "; x = " + x + "; y = " + y);
  return true;
  }*/
}

//**************************************************************************
class BuggleExecuter {
  // Control the executiong of buggles. Allow buggles to be stepped,
  // be run until explicitly paused, or be reset. 
  
  private BuggleRunner runner;   // Encapsulates running into object suitable for thread. 
  // Make only one of these.
  private Thread thread; // Make new thread every time reset.
  //private javax.swing.Timer timer; // **** Make new thread every time reset.
  private BuggleWorld world; 
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
  
  public BuggleExecuter(BuggleWorld w) {
    world = w;
    runner = new BuggleRunner(w);
    init();
  }
  
  // [9/6/04]
  public void execDebugPrintln(String s) {
    if (execDebug) {
      System.out.println("Exec debug: " + s);
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
    // Run buggles until done or sent an explicit stop message.
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
      thread.resume(); // [9/6/04] Schedules buggle thread to run again (but doesn't run yet).
      execDebugPrintln("go: after resuming thread");
    } else {
      // Already running or yielded -- ignore. 
      // System.out.println("Buggle Execution Error -- unexpected state in go(): " + stateString());
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
  
  public void waitIfNecessary(String instruction) {
    // Buggles call this method when about to perform the next primitive.
    // Message is a string documenting what the next primitive is. 
    
    // This is the only method that the thread being controlled by the
    // executer actually calls.  So must be careful to update state *before*
    // suspending thread!
    
    // If not in step mode, return immediately.
    // If in step mode, wait until next step is indicated. 
    // First instruction needs to be treated specially. 
    execDebugPrintln("waitIfNecessary: " + instruction);
    int delay = world.getBuggleDelay();
    if (delay > 0) {
      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
      }
    }
    if (state != RUNNING) { // [9/6/04]
      // Don't do anything special -- buggle routines were called during initialization.
      execDebugPrintln("waitIfNecessary: called when not running");
    } else {
      if (stepMode) {
        if (isFirstSteppedInstruction) {
          // No instruction is pending, so execute this one by returning. 
          execDebugPrintln("waitIfNecessary: printing first instruction.");
          world.printInstruction(instruction);
          isFirstSteppedInstruction = false;
        } else { // [9/6/04]
          // Careful: perform all thread updates *before* suspending thread!
          currentInstruction = instruction;
          execDebugPrintln("waitIfNecessary: suspending thread " + stateString());
          state = SUSPENDED;
          execDebugPrintln("waitIfNecessary: state set to SUSPENDED");
          stepMode = false;
          execDebugPrintln("waitIfNecessary: stepMode set to false");
          thread.suspend(); // Note: this unschedules the current thread!
          execDebugPrintln("waitIfNecessary: after suspending thread " + stateString());
        }
      } else { // [9/6/04]
        execDebugPrintln("waitIfNecessary: yielding");
        state = YIELDED;
        execDebugPrintln("waitIfNecessary: state set to YIELDED");
        Thread.yield(); // Let interface run no matter what
        state = RUNNING;
        execDebugPrintln("waitIfNecessary: state set to RUNNING");
        execDebugPrintln("waitIfNecessary: after yielding");
      }
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
class BuggleRunner implements Runnable {
  /* A way to encapsulate the behavior of the buggles into a thread-like object */
  
  private BuggleWorld world;
  private boolean done = false;
  
  public BuggleRunner(BuggleWorld w) {
    // System.out.println("new Buggle(runner)");
    world = w;
  }
  
  public void run () {
    // System.out.println("BuggelRunner run();");
    world.run(); 
    done = true;
  }
  
  public boolean isDone() {
    return done;
  }
  
  public void reset () {
    // System.out.println("BuggelRunner reset();");
    done = false;
  }
  
}

//**************************************************************************
class Buggle {

  // Although buggles are normally presented as having four pieces of state
  // (position, heading, color, and brush state), there is an important
  // fifth piece of state: the BuggleWorld in which the buggle lives. 
  // 
  // There may be multiple BuggleWorlds in existence at any one time;
  // how does a buggle know which one to live in? There are two ways:
  // 
  // (1) A BuggleWorld instance can be explicitly provided to the Buggle constructor.
  //     But since the world is not advertised as a piece of state, most
  //     users won't know about this option. 
  // 
  // (2) If no BuggleWorld instance is explicitly provided to the Buggle constructor,
  //     it implicitly uses the currently "active" BuggleWorld, which is the
  //     one in which one of the following two actions has most recently occurred:
  //     1) The BuggleWorld has been created
  //     2) A menu item has been pressed in the BuggleWorld. 
  //     The class variable BuggleWorld.currentWorld holds the currently active
  //     instance of BuggleWorld. 
  
  private BuggleWorld world;  // The world to which the Buggle belongs.
  private Location position;   // Location of the Buggle
  private static final int _defaultX = 1;
  private static final int _defaultY = 1;
  private Direction heading;
  private Color color;       // Color of the Buggle.
  private boolean trailmode = true;
  private static final boolean _defaultTrailmode = true;
  private static final Color _defaultColor = Color.red;
  private static final Color selectedBuggleOutlineColor = Color.black;
  private static final Color unselectedBuggleOutlineColor = Color.white;
  // private static final int inset = 3; // Number of pixels by which drawn buggle is inset from cell edge
  private static final double insetFactor = 0.05; // Factor by which drawn buggle is inset from cell
  private BuggleExecuter exec;

  public Buggle() {
    this(_defaultColor, _defaultX, _defaultY, BuggleWorld.currentWorld);
  }

  public Buggle (BuggleWorld w) {
    this(_defaultColor, _defaultX, _defaultY, w);
  }
  
  public Buggle(int x, int y) {
    this(_defaultColor, x, y, BuggleWorld.currentWorld);
  }
  
  public Buggle(Color c) {
    this(c, _defaultX, _defaultY, BuggleWorld.currentWorld);
  }
  
  public Buggle(Color c, int x, int y, BuggleWorld w) {
    //  System.out.println ("New Buggle.");
    color = c;
    position = new Location(x, y);
    heading = Direction.EAST;
    trailmode = _defaultTrailmode;
    world = w; 
    //System.out.println ("Getting world.");
    if (world == null) 
      throw new BuggleException("BuggleWorld of newly created buggle is null!");
    else {
      //System.out.println ("Adding robot to world" + world);
    }
    exec = world.exec; // Cache executer locally. 
    exec.waitIfNecessary("new Buggle()");
    // Careful! Must select buggle before adding it 
    // (which will try to draw it, and may complain if not selected).
    world.selectBuggle(this);
    //System.out.println("This buggle = " + this );
    world.add(this);
  }
  
  public String toString () {
    return "[position = (" + position.x + ", " + position.y + ")"
      + "; heading = " + heading
      + "; color = " + color 
      + "; brushDown? " + trailmode
      +"]";
  }
  
  // [lyn, 9/2/07] Modified forward(n) and backward(n) to appear as 
  //   atomic steps for stepping purposes. 
  // [lyn, 9/11/07] Modified forward(n) and backward(n) to handle
  //   negative steps (used by HuggleWorld). 
  public void forward (int n) throws BuggleException {
    exec.waitIfNecessary("forward(" + n + ")");
    if (n >= 0) {
      for (int i = 0; i < n; i++){
 forwardStep();
      }
    } else {
      for (int i = 0; i > n; i--){
 backwardStep();
      }
    }
  }
  
  public void forward () throws BuggleException {
    exec.waitIfNecessary("forward()");
    forwardStep();
  }

  // [lyn, 9/2/07] forwardStep() is the underlying primitive used by forward() and forward(n)
  public void forwardStep () throws BuggleException {
    if (world.wallInDirection(position, heading))
      throw new BuggleException("FORWARD: Can't move through wall!");
    if (trailmode)
      world.markTrail(position, color);
    Location oldPosition = position;
    position = world.addCoordinates(position, heading.toLocation());
    world.buggleMoved(this, oldPosition, position);
  }

  public Color getCellColor() {
    // Return the color in the cell under the buggle. 
    exec.waitIfNecessary("getCellColor()");
    return world.markColorAt(position); // [lyn, 11/11/06] Now guaranteed to return color, never null
  }
  
  // [lyn, 9/1/07] Introduce setCellColor() as a synonym for what we've
  //    traditionally called paintCell(). 
  public void setCellColor(Color c) {
    exec.waitIfNecessary("setCellColor(" + world.colorString(c) + ")");
    // Paint the current grid cell with the given color
    world.markTrail(position, c); 
    world.buggleChanged(this);
  }

  // [lyn, 9/1/07] Made paintCell() a stepable command. 
  public void paintCell(Color c) {
    exec.waitIfNecessary("paintCell(" + world.colorString(c) + ")");    
    // Paint the current grid cell with the given color
    world.markTrail(position, c); 
    world.buggleChanged(this);
  }

  // [lyn, 9/2/07] Modified forward(n) and backward(n) to appear as 
  //   atomic steps for stepping purposes. 
  // [lyn, 9/11/07] Modified forward(n) and backward(n) to handle
  //   negative steps (used by HuggleWorld). 
  public void backward (int n) throws BuggleException {
    exec.waitIfNecessary("backward(" + n + ")");
    if (n >= 0) {
      for (int i = 0; i < n; i++){
 backwardStep();
      }
    } else {
      for (int i = 0; i > n; i--){
 forwardStep();
      }
    }
  }
  
  public void backward () throws BuggleException {
    exec.waitIfNecessary("backward()");
    backwardStep();
  }

  // [lyn, 9/2/07] backwardStep() is the underlying primitive used by backward() and backward(n)
  public void backwardStep () throws BuggleException {
    heading = heading.opposite();
    if (world.wallInDirection(position, heading))
      throw new BuggleException("BACKWARD: Can't move through wall!");
    if (trailmode)
      world.markTrail(position, color);
    Location oldPosition = position;
    position = world.addCoordinates(position, heading.toLocation());
    heading = heading.opposite();
    world.buggleMoved(this, oldPosition, position);
  }
  
  public Location getPosition () {
    //System.out.println("position();");
    exec.waitIfNecessary("getPosition()");
    return position;
  }
  
  public Location position () {
    //System.out.println("position();");
    // Non-waiting version of primitive
    return position;
  }
  
  public void setPosition (Location p) {
    //System.out.println("position();");
    exec.waitIfNecessary("setPosition(" + world.locationString(p) + ")");
    // [lyn, 9/1/07] Added check that position is legal 
    if (! world.isLocationInGrid(p)) {
      throw new BuggleException("SETPOSITION: Location not in grid -- " + p);
    } else {
      Location oldPosition = position;
      position = p; 
      world.buggleMoved(this,oldPosition,position);
    }
  }
  
  public Direction getHeading () {
    exec.waitIfNecessary("getHeading()");
    // System.out.println("heading();");
    return heading;
  }
  
  public void setHeading (Direction d) {
    //System.out.println("position();");
    exec.waitIfNecessary("setHeading()");
    heading = d; 
    world.buggleChanged(this);
  }
  
  public void left() {
    exec.waitIfNecessary("left()");
    heading = heading.left();
    world.buggleChanged(this);
  }
  
  public void right() {
    //System.out.println("right();");
    exec.waitIfNecessary("right()");
    heading = heading.right();
    world.buggleChanged(this);
  }
  
  public boolean isFacingWall () {
    //System.out.println("facingWall();");
    exec.waitIfNecessary("isFacingWall()");
    return world.wallInDirection(position, heading);
  }
  
  public boolean isOverBagel () {
    //System.out.println("overBagel();");
    exec.waitIfNecessary("isOverBagel()");
    return world.isBagelAt(position);
  }
  
  public void pickUpBagel () {
    exec.waitIfNecessary("pickUpBagel()");
    if (! world.isBagelAt(position)) 
      throw new BuggleException("pickUpBagel: no bagel to pick up!");
    world.removeBagel(position);
    // Handled by removeBagel.
    // cellChanged(position);
  }
  
  public void dropBagel () {
    exec.waitIfNecessary("dropBagel()");
    if (world.isBagelAt(position)) 
      throw new BuggleException("dropBagel: already a bagel in this cell!");
    world.addBagel(position);
    // Handled by removeBagel.
    // cellChanged(position);
  }
  
  public String dropString (String s) {
    exec.waitIfNecessary("dropString()");
    world.addString(position, s);
    return s; 
  }
  
  public int dropInt (int n) {
    exec.waitIfNecessary("dropInt()");
    world.addString(position, Integer.toString(n));
    return n;
  }
  
  public boolean withCompanion () {
    exec.waitIfNecessary("withCompanion()");
    return world.withCompanion(this);
  }
  
  public Color getColor () {
    exec.waitIfNecessary("getColor()");
    return color;
  }
  
  public void setColor (Color c) {
    exec.waitIfNecessary("setColor(" + world.colorString(c) + ")");
    color = c;
    world.buggleChanged(this);
  }
  
  public boolean isBrushDown() {
    return trailmode;
  }
  
  public void brushDown() {
    exec.waitIfNecessary("brushDown()");
    trailmode = true;
  }
  
  public void brushUp() {
    exec.waitIfNecessary("brushUp()");
    trailmode = false;
  }
  
  // [lyn, 11/11/06] Changed to use inset factor
  public void drawInRect(Graphics g, Rectangle r) {
    //System.out.println("Draw in rect: g = " + g + "; rgt = " + r);
    // Draw myself in given rectangle of graphics.
    // Assume simple triangle shape for now; do something cuter in future.
    
    // Compute triangle based on direction; there must be a cleverer way to do this!
    Point p1 = new Point(0,0);
    Point p2 = new Point(0,0);
    Point p3 = new Point(0,0);
    int insetX = (int) Math.floor(insetFactor * r.width); // unlike with bagels, no grid line to consider
    int insetY = (int) Math.floor(insetFactor * r.height); // unlike with bagels, no grid line to consider
    int width = r.width - 1; // [lyn, 11/11/06] Decrement so additions make sense in discrete grid
    int height = r.height - 1;
    if (heading == Direction.SOUTH) {
      p1.x = r.x + width - insetX;
      p1.y = r.y + insetY;
      p2.x = r.x + insetX;
      p2.y = r.y + insetY;
      p3.x = r.x + (width / 2);
      p3.y = r.y + height - insetY;
    } else if (heading == Direction.EAST) {
      p1.x = r.x + insetX;
      p1.y = r.y + insetY;
      p2.x = r.x + insetX;
      p2.y = r.y + height - insetY;
      p3.x = r.x + width - insetX;
      p3.y = r.y + (height/2);
    } else if (heading == Direction.NORTH) {
      p1.x = r.x + insetX;
      p1.y = r.y + height - insetY;
      p2.x = r.x + width - insetX;
      p2.y = r.y + height - insetY;
      p3.x = r.x + (width/2);
      p3.y = r.y + insetY;
    } else if (heading == Direction.WEST) {
      p1.x = r.x + width - insetX;
      p1.y = r.y + height - insetY;
      p2.x = r.x + width - insetX;
      p2.y = r.y + insetY;
      p3.x = r.x + insetX;
      p3.y = r.y + (height/2);
    }
    // Handle off-by-one bug in Symantec cafe polygons.
    // (Symantec cafe treats polygon coords as 1-based, not 0-based).
    // p1.x = p1.x - 1;
    // p1.y = p1.y - 1;
    // p2.x = p2.x - 1;
    // p2.y = p2.y - 1;
    // p3.x = p3.x - 1;
    // p3.y = p3.y - 1;
    int [] xs = {p1.x, p2.x, p3.x,p1.x};
    int [] ys = {p1.y, p2.y, p3.y,p1.y};
    g.setPaintMode();
    g.setColor(color);
    //System.out.println("p1 = " + p1 + "; p2 = " + p2 + "; p3 = " + p3);
    g.fillPolygon(xs, ys, 4);
    if (this == world.selectedBuggle()) {
      g.setColor(selectedBuggleOutlineColor);
    } else {
      g.setColor(unselectedBuggleOutlineColor);
    }
    g.drawPolygon(xs, ys, 4);
  }
  
}


//
////**************************************************************************
//class Direction {
//  
//  private int dir;
//  
//  public static final Direction NORTH = new Direction(0);
//  public static final Direction EAST = new Direction(1);
//  public static final Direction SOUTH = new Direction(2);
//  public static final Direction WEST = new Direction(3);
//  
//  private static final Location northLoc = new Location(0,1);
//  private static final Location eastLoc = new Location(1, 0);
//  private static final Location southLoc = new Location(0, -1);
//  private static final Location westLoc = new Location(-1, 0);
//  
//  private static final Direction[] rights = {EAST, SOUTH, WEST, NORTH};
//  private static final Direction[] lefts = {WEST, NORTH, EAST, SOUTH};
//  private static final Direction[] opposites = {SOUTH, WEST, NORTH, EAST};
//  private static final Location[] locations = {northLoc, eastLoc, southLoc, westLoc};
//  private static final String[] strings = {"NORTH", "EAST", "SOUTH", "WEST"};
//  
//  private Direction(int d) {
//    dir = d;
//  }
//  
//  public boolean equals (Direction d) {
//    return dir == d.dir;
//  }
//  
//  // Carefully define the following so that == works as well as .equals
//  public Direction right() {
//    return rights[dir];
//    /* if this == NORTH
//       return EAST;
//       else if this == EAST
//       return SOUTH;
//       else if this == SOUTH
//       return WEST;
//       else if this == WEST
//       return NORTH; */
//  }
//  
//  public Direction left() {
//    return lefts[dir];
//    /*
//      if this == NORTH
//      return WEST;
//      else if this == EAST
//      return NORTH;
//      else if this == SOUTH
//      return EAST;
//      else if this == WEST
//      return SOUTH;
//    */
//  }
//  
//  public Direction opposite() {
//    return opposites[dir];
//  }
//  
//  public Location toLocation() {
//    return locations[dir];
//  }
//  
//  public String toString () {
//    return strings[dir];
//    /*
//      if (dir == 0)
//      return "NORTH";
//      else if (dir == 1)
//      return "EAST";
//      else if (dir == 2)
//      return "SOUTH";
//      else if (dir == 3)
//      return "WEST";
//      else
//      return "Unknown direction";
//    */
//  } 
//}
//
////**************************************************************************
//// [lyn, 7/18/07] Added the add method.
//// [lyn, 8/22/07] New class for immutable points. BuggleWorld
////   now uses these rather than the mutable Point class to 
////   avoid some knotty Buggle contract issues with immutable points. 
//
//class Location {
//  
//  // Immutable (really, write-once) instance variables
//  public final int x; 
//  public final int y; 
//  
//  // Constructor method
//  public Location(int initx, int inity) {
//    x = initx;
//    y = inity;
//  }
//
//  // Instance methods
//  
//  public boolean equals (Object obj) {
//    if (obj instanceof Location) {
//      Location loc = (Location) obj;
//      return (x == loc.x) && (y == loc.y);
//    } else {
//      return false;
//    }
//  }
//
//  public Point toPoint () {
//    return new Point(x,y); 
//  }
//  
//  // Displaying as a string: 
//  public String toString() {
//    return "Location(x=" + x + ",y=" + y + ")";
//  }
//
//  // Returns a new location that sums the respective components of 
//  // loc and this location. 
//  public Location add (Location loc) {
//    return new Location (this.x + loc.x, this.y + loc.y);
//  }
//
//  // Class methods
//
//  public Location fromPoint (Point p) {
//    return new Location(p.x, p.y); 
//  }
//
//  public Point toPoint (Location loc) {
//    return new Point(loc.x, loc.y); 
//  }
//  
//}
//
////**************************************************************************
//class BuggleException extends RuntimeException {
//  
//  public BuggleException (String msg) {
//    super(msg);
//  }
//  
//}
