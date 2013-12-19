/** Karina Chan and Laura Zeng
  * CS 230 
  * Final Project
  * PokeWorld.java
  * 
  * The GUI of our project. Based off BuggleWorld. We decided against
  * extending BuggleWorld because of time constraints and because our code
  * is a big enough departure from BuggleWorld that we would have to change
  * private/protected/public on many of the BuggleWorld methods in order to 
  * get the program to run. 
  */


/****************************************************************
  * To avoid confusion with Lyn's code, our comments will be prefaced with 
  * a large asterisk box like this one. Only methods that we made changes to 
  * have these comment boxes; if we did not make changes to a method, we did 
  * not add additional comments. 
  * *************************************************************/

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

//**************************************************************************
public class PokeWorld extends JApplet 
  implements ActionListener {
  
  public int rows = 9;    // Number of rows.
  public int cols = 9;    // Number of columns.
  public Vector pokes;    // The pokes in the world.
  public boolean bagels [] [];    // Bagels (at most one per cell).
  public String strings [] [];    //[9/6/04] Strings (at most one per cell).
  public Color marks [] [];     // The trail marks in the world;
  public boolean horizontalWalls [] [];  // leftmost points of horizontal wall segments.
  public boolean verticalWalls [] [];  // bottommost points of vertical wall segments.
  private boolean boundariesAreWalls = true;
  private final static Color backgroundColor = Color.black;
  private final static Color buttonBackgroundColor = Color.white;
  public static PokeWorld currentWorld; // The currently active PokeWorld
  // (the one most recently created or in which 
  //  a menu item has been pressed.)
  // This is used as an implicit argument to the 
  // Poke constructor, to indicate the world in
  // which the new poke will live. 
  public boolean inReset = false; // [lyn, 9/2/07] Tracks whether or not we are in the middle of a reset. 
  private PokeGrid grid;
  private JPanel infoPanel; // **** was Panel
  private JPanel controlPanel;
  private JPanel setPositionPanel;
  private JPanel setHeadingPanel;
  private JPanel setColorPanel;
  private JPanel setDelayPanel; // [9/6/04]
  public PokeExecuter exec;
  /*************************************************************
    * We have two Pokes (which are containers for Pokemon that behave like Buggles)
    * your character and the professor you're facing against
    *************************************************************/
  private Poke selectedPoke, prof;  // Currently selected poke.
  // Menu items apply to this poke. 
  // By default, it's the most recently created poke. 
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
  private int pokeDelay = 0; // [9/6/04] Amount of time poke waits between moves.
  // [9/6/04] Can be used to slow down poke. 
  private boolean debugOn = false;
  
  /*********************************************************
    * This is the scrolling text area in the top-right corner of the GUI
    * It displays all the battles and the interactions. 
    *********************************************************/
  private JTextArea scrollText; 
  private JScrollPane scrollBox;
  
  /**********************************************************
    * All the components we need for battling
    *********************************************************/
  private Pokemon p1, p2, tempPoke; //temp stores the original values and p1 stores the other values
  private PokemonBattle battle; 
  private LinkedQueue<String> battleLog; //the log of the battle (displayed in scroll box)
  private int battleCounter=0; //counters that track when we display what text
  private int battleLength;
  private boolean inBattle=false; 
  private boolean battled=false; 
  private String first; //first statement
  private String username; //the inputted name
  private ProfessorTree<Pokemon> tree; 
  private Iterator treeOrder; 
  private int treeCounter=0;
  
  public void debugPrintln(String s) {
    if (debugOn) 
      System.out.println("Debug: " + s);
  }
  
  //----------------------------------------------------------------------
  /*** [lyn, 8/22/07] New code for running an applet as an application ***/
  
  public static void main (String[] args) {
    runAsApplication(new PokeWorld(), "PokeWorld"); 
  }
  
  /****************************************************************
    * runs the GUI 
    * @param applet         PokeWorld
    * @param name           name that goes in GUI title
    * *************************************************************/
  public static void runAsApplication (final PokeWorld applet, final String name) {  
    // Schedule a job for the event-dispatching thread:
    // creating and showing this poke world applet. 
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() { // this is Java's thread run() method, not PokeWorlds!
        JFrame.setDefaultLookAndFeelDecorated(true); // enable window decorations. 
        JFrame frame = new JFrame(name); // create and set up the window.
        /****************************************************************
          * Changed frame size
          * *************************************************************/
        frame.setSize(800, 600); // Default frame size (should make these settable variables!)
        //dont't reset the size
        frame.setResizable(false); 
        // [lyn. 8/30/07] Using EXIT_ON_CLOSE empirically exits all instances of an application.
        //   Use DISPOSE_ON_CLOSE to get rid of just one. 
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
        
        // [lyn. 8/30/07] Need to add to frame and make visible *before* init 
        //   so that attempts to reset dimensions will work. 
        
        /****************************************************************
          * Changed GUI so that we have tabbed panes
          * *************************************************************/
        JPanel test= new JPanel(new BorderLayout()); //instead of frame, made the test the borderlayout
        WelcomePanel wel= new WelcomePanel();
        JTabbedPane tp = new JTabbedPane();
        //added pane here
        tp.add("Welcome!",wel); //tab for welcome
        test.add(applet, BorderLayout.CENTER); // add applet to window
        tp.add("Play", test); //tab for playing 
        tp.add("About Us", new AboutPanel());
        frame.add(tp);
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
  
  /****************************************************************
    * initializes the applet
    * we tagged on the creation of the ProfessorTree here b/c
    * the ProfessorTree only needs to be created once (at the beginning)
    * *************************************************************/
  public void init() {
    debugPrintln("Start init();");
    currentWorld = this; // Make this world the current "active" world.
    // Used as the default world in which new Pokes will live.
    debugPrintln("setup();");
    setup(); // One-time initializations from PokeWorld subclasses, 
    // such as setDimensions and creation of ParameterFrames.
    // Any such initializations cannot depend on the state of PokeWorld
    // or its grid, since these haven't been created yet. 
    createGUI(); // Create the PokeWorld GUI. This allocates space for the grid,
    // but does not draw it yet. 
    debugPrintln("Making executer");
    exec = new PokeExecuter(this); // PokeExecuter for the RUN thread. 
    
    /****************************************************************
      * Create the ProfessorTree once the game starts
      * Also creates your Pokemon once the game starts (b/c your 
      * Pokemon doesn't change through the game, you only need to 
      * create it once at the start)
      * *************************************************************/
    try { 
      tree = new ProfessorTree<Pokemon>(); 
      tree = tree.createOpponents(); 
      treeOrder = tree.iterator();
      username= JOptionPane.showInputDialog("Enter your name: "); 
      Image sprite1 = ImageIO.read(new File("pikas.png"));
      Image sprite2 = ImageIO.read(new File("pikaw.png"));
      Image sprite3 = ImageIO.read(new File("pikae.png"));
      Image sprite4 = ImageIO.read(new File("pikan.png"));
      Image[] sprites = {sprite1, sprite2, sprite3, sprite4}; 
      
      p1 = new Pokemon("Pikachu","Pika", username, 250, 100, 100, sprites);
    } catch (IOException ioe) { 
    }
    inReset = true; // [lyn, 9/2/07] Track whether we're in reset, for cellChanged();
    //   This *cannot* be done in reset() itself, since that's overridable.    
    reset(); // Sets the state of PokeWorld. 
    // This can be overridden by subclasses, but all overriding methods 
    // must call super.reset(). Any work done before super.reset() should
    // only set instance variables, since the world state has not be reset yet. 
    // But work done after super.reset() can use the world state
    // (e.g., walls, bagels, pokes, etc.).
    // Note that reset() can also be called explicitly by pressing the reset button.
    inReset = false; // [lyn, 9/2/07] Track whether we're in reset, for cellChanged();    
    // Note: grid.paint() is not a part of reset() itself, because reset() is overridable
    // by programmer, and don't want to draw grid until know all of the state changes. 
    //grid.paint(); // draw the PokeWorld grid after all state updates have been made.
    
  }
  
  
  public void setup () {
    // Default is to do nothing. Intended to be overridden by subclasses. 
    // Typical actions in setup() are setDimensions() and creating a ParameterFrame.
  }
  
  
  // Sets the rows and cols of this PokeWorld, but does not cause anything to be drawn
  // or redrawn. The drawing is done by the reset() method. 
  public void setDimensions (int cols, int rows) {
    debugPrintln("Start setDimensions(" + cols + ", " + rows + ")");
    this.cols = cols;
    this.rows = rows;
  }
  // [lyn, 9/1/07] Long-awaited restructuring of init() and reset()!
  
  /****************************************************************
    * Every time you beat a professor/lose to a professor, the world is reset. 
    * We also reset all the battle counters and booleans
    * *************************************************************/
  public void reset() {
    if (treeOrder.hasNext()) {
      /****************************************************************
        * Selects your next opponent
        * important to do this in reset b/c it changes every time you win
        * *************************************************************/
      if (p1.getWonSize()==p1.getVisitSize()) { //if the number of wins != number of visits, then 
        //you haven't won against your most recent opponent and must face them again
        p2 = (Pokemon)treeOrder.next();
      }
    }
    debugPrintln("reset()");
    initializeWalls();
    marks = new Color [cols+1] [rows+1]; // Entries will be null unless set otherwise.
    /*********************************************************************
      * creates the red "door" in the upper-right corner
      * ******************************************************************/
    markTrail(new Location(9,9), Color.red); 
    initializeBagels();
    initializeStrings(); //[9/6/04]
    pokes = new Vector();
    /****************************************************************
      * sets up counters and booleans used to determine when battle is over
      * *************************************************************/
    //resets battleCounter to 0 so that the next battle sequence is shown
    battleCounter=0; 
    //clears scrollText
    scrollText.setText(""); 
    //every time we reset, we haven't actually battled yet
    battled=false; 
    /****************************************************************
      * creates "stadium" (walls)
      * *************************************************************/
    //creates boundaries and professor poke
    createStadium(this.getGraphics(),9,9); 
    //grid.paint(); 
    Location start = new Location (5,1); 
    
    /****************************************************************
      * creates Professor
      * again, it's important to create a new one each time b/c, when 
      * you win, you face off against a new professor
      * *************************************************************/
    selectedPoke = new Poke(p2); 
    selectedPoke.setPosition(new Location(5,8)); 
    selectedPoke = new Poke(p1); //default stats are average
    selectedPoke.setPosition(start); 
    initBattle(); 
    scrollText.setText("You are in "+p2.getTrainer()+"'s classroom! \nPress A to interact.\nPress B for instructions.\n");
    //scrollText.setText(scrollText.getText()+ "Your professor is "+ p2.getTrainer() +"!");
    exec.reset();
    debugPrintln("Finish PokeWorld.reset()");
  } 
  
  /****************************************************************
    * creates the GUI (main game panel w/ PokeWorld)
    * *************************************************************/ 
  public void createGUI() {
    //initBattle(); 
    grid = new PokeGrid(this);  // Make grid in which pokes are displayed
    this.makeInfoPanel(); // Make panel for instructions to the selected poke
    this.makeControlPanel(); // Make panel for controlling execution of the poke program
    // this.makeOutput();  // Make the area for displaying textual feedback
    //this.makeGameControlPanel(); 
    Container c = getContentPane(); //****
    debugPrintln("Setting world layout");
    c.setLayout(new BorderLayout() );
    setBackground( backgroundColor );
    //puts the overworld in the center
    c.add(grid, BorderLayout.CENTER); 
    //puts the controls on the right side of the GUI
    c.add(infoPanel, BorderLayout.EAST); 
    //c.add(controlPanel, BorderLayout.PAGE_END); 
  }
  
  /****************************************************************
    * creates the infoPanel that is the right-hand of the GUI
    * resembles a GameBoy console
    * contains the scroll box, A and B buttons, and the direction pad
    * *************************************************************/ 
  private void makeInfoPanel() {
    infoPanel = new JPanel();
    //to accomodate the JLabel scrollbox, 2 buttons, and gamepad
    infoPanel.setLayout(new GridLayout(3,3));
    infoPanel.setBackground(Color.green);
    //creates the scrollbox where game text is displayed
    newInstructionPanelScroll("Welcome to Pokemon CS Land\n"); 
    //2 buttons
    newInstructionPanelItemPair("A", "B");  
    //gamePad
    gamePad("UP","RIGHT","DOWN","LEFT");
  }
  
  /****************************************************************
    * creates scroll box
    * @param s      the text that goes in the scroll box
    * *************************************************************/ 
  private void newInstructionPanelScroll (String s) {
    scrollText = new JTextArea(s); 
    scrollText.setEditable(false); 
    scrollText.setLineWrap(true);
    scrollText.setWrapStyleWord(true);
    //puts scrollText (which we change throughout the game) in a ScrollPane 
    //(allowing the player to see past interactions)
    scrollBox = new JScrollPane(scrollText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    //scrollBox.setBorder(new EmptyBorder(10, 10, 10, 10));  
    infoPanel.add(scrollBox);
  }
  
  /****************************************************************
    * creates gamepad (direction pad) 
    * @param n, e, s, w     the text that goes on the buttons on the gamepad
    * *************************************************************/ 
  private void gamePad(String n, String e, String s, String w) { 
    JPanel p = new JPanel(); 
    p.setLayout(new GridLayout(3,3)); 
    p.setBackground(buttonBackgroundColor); 
    //4 buttons (up, down, left, right)
    JButton bU = new JButton(n); 
    JButton bR = new JButton (e); 
    JButton bD = new JButton (s); 
    JButton bL = new JButton (w); 
    //empty JPanels to make the gamePad pretty
    JPanel e1 = new JPanel(); 
    JPanel e2 = new JPanel(); 
    JPanel e3 = new JPanel();
    JPanel e4 = new JPanel();
    JPanel e5 = new JPanel();
    bU.setBackground(buttonBackgroundColor); 
    bR.setBackground(buttonBackgroundColor); 
    bD.setBackground(buttonBackgroundColor); 
    bL.setBackground(buttonBackgroundColor); 
    e1.setBackground(buttonBackgroundColor); 
    e2.setBackground(buttonBackgroundColor); 
    e3.setBackground(buttonBackgroundColor); 
    e4.setBackground(buttonBackgroundColor); 
    e5.setBackground(buttonBackgroundColor); 
    p.add(e1); 
    p.add(bU); 
    p.add(e2); 
    p.add(bL); 
    p.add(e3); 
    p.add(bR);
    p.add(e4); 
    p.add(bD);
    p.add(e5); 
    infoPanel.add(p); 
    bU.addActionListener(this); 
    bR.addActionListener(this); 
    bD.addActionListener(this); 
    bL.addActionListener(this); 
  }
  
  private void newInstructionPanelItemPair (String s1, String s2) {
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(1,2));
    p.setBackground(buttonBackgroundColor);
    JButton b1 = new JButton(s1);
    b1.setBackground(buttonBackgroundColor);
    JButton b2 = new JButton(s2);
    b2.setBackground(buttonBackgroundColor);
    p.add(b1);
    p.add(b2);
    infoPanel.add(p);
    b1.addActionListener(this); // ***
    b2.addActionListener(this);
  }
  
  public void newInstructionPanelItem (Component c) {
    c.setBackground(buttonBackgroundColor);
    infoPanel.add(c);
  }
  
  private void makeControlPanel () {
    controlPanel = new JPanel();
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
  
  // [lyn, 9/1/07] Note: although it might be nice to handle menu button presses
  //   by anonymous inner classes, one advantage of using actionPerformed is
  //   that it makes it easy to set the currentWorld variable for every such action. 
  // 
  // *** old *** public boolean action(Event event, Object arg) {
  //  try {
  /****************************************************************
    * the actionlistener instructions
    * @param event        whatever the event is
    * *************************************************************/ 
  public void actionPerformed( ActionEvent event ) { 
    
    Object src = event.getSource(); //[9/6/04]
    String arg = event.getActionCommand(); // ***   
    // [lyn, 9/1/07] Setting currentWorld when menu item pressed
    //    gives better support for multiple worlds. In particular,
    //    new Poke() will create a poke in the world in which 
    //    one of the following two actions has most recently occurred:
    //    (1) the world was created
    //    (2) a menu item in the world was pressed. 
    // 
    //    The only case where this behavior won't be correct is if 
    //    a long-running run() method invoked by a RUN method 
    //    invokes new Poke() after the user, in parallel, has
    //    created a new world or pressed a menu button in another world. 
    //    The only way to avoid such problems in general is to 
    //    invoke new Poke(<world>), where <world> is an explicit
    //    PokeWorld argument for the correct PokeWorld instance. 
    currentWorld = this; 
    // [lyn, 9/2/07] Reset this variable, in case it's gotten out of sync: 
    inReset = false; 
    debugPrintln("actionPerformed: command = " + arg + "; src = " + src);
    if (arg.equals("Run")) {
      debugPrintln("Calling paint from run");
      // //System.out.println("Run");
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
      grid.paint(); // draw the PokeWorld grid after all state updates have been made. 
      /****************************************************************
        * The B button contains our instructions. The player can't access the 
        * instructions mid-battle. 
        * *************************************************************/ 
    } else if (arg.equals("B")) {
      //can't access B button in the middle of a battle
      if (inBattle) { 
        String s = scrollText.getText(); 
        scrollText.setText(s + "You can't press B now! You're in the middle of a battle!\n"); 
      } else { 
        scrollText.setText("How to play:\n1. Go up to the professor and battle by pressing A.\n2. Your health will "+
                           "decrease by 50% if you lose. If you win, your attack and your health stats will go up!\n\n" + 
                           "******WHEN YOU WIN, GO TO THE PORTAL AND PRESS A TO GO TO THE NEXT PROFESSOR"+
                           "*******\n\n");
      }
      /****************************************************************
        * The A button handles most of the interactions (talking, battling, and teleporting)
        * Most of the actions are hard coded depending on where the Poke is on the grid. 
        * *************************************************************/
    } else if (arg.equals("A")) { 
      /****************************************************************
        * when the player is next to the professor, execute this code when 
        the player presses aButton to talk to the professor
        * *************************************************************/ 
      if (selectedPoke.getPosition().equals(new Location(5,7))) { 
        /*  code for the music (works, but causes lag)
         try {
         //System.out.println("Play music!"); 
         URL song = new URL ("http://cs.wellesley.edu/~lzeng/Final%20Project/Pokemon%20Yellow%20Gym%20Leader%20Theme.wav"); 
         HttpURLConnection urlConn = (HttpURLConnection)song.openConnection(); 
         urlConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
         InputStream audioSrc = urlConn.getInputStream(); 
         DataInputStream read = new DataInputStream(audioSrc); 
         AudioStream as = new AudioStream(read); 
         AudioPlayer.player.start(as); 
         } catch (Exception e) { 
         //System.out.println(e); 
         //System.out.println("Music is not playing."); 
         }
         */
        if (battleCounter<=battleLength) {  //so it goes to one above
          if (battleCounter==0) {  //so the battle status prints first hu hu hu
            inBattle=true; 
            scrollText.setText(first+"\n");  //have a new line
            //scrollText.setText(battleLog.dequeue());
          } else { 
            String s = scrollText.getText();
            scrollText.setText(s + battleLog.dequeue()); 
          }
          battleCounter++; 
        } else { 
          battled=true; 
          inBattle=false; 
          if (!battle.hasWonYet()) { //if you lose, we tell you, then we reset
            JOptionPane.showMessageDialog(currentWorld, battle.getStatus());
            reset();
          } else { 
            if (!treeOrder.hasNext()){ //you've won the entire game!
              JOptionPane.showMessageDialog(currentWorld, "YOU WON THE GAME OMG OMG OMG! Check the folder for your diploma!");
              try { //prints your diploma in html file
                PrintWriter writer = new PrintWriter("diploma_" + username+ ".html", "UTF-8");
                writer.println("<!DOCTYPE html><html><head>"); 
                writer.println("<link href='http://fonts.googleapis.com/css?family=Press+Start+2P' rel='stylesheet' type='text/css'>"); 
                writer.println("<style>body { background-color:#ffffff;}"); 
                writer.println("h1 { color:black; text-align:center; font-family: 'Press Start 2P', cursive; font-size:80px;}"); 
                writer.println("h1.padding {  padding:7px 0px;}"); 
                writer.println("p { text-align:center; font-family: 'Press Start 2P', cursive; font-size:18px; word-spacing:1px;}"); 
                writer.println("p.padding { padding:20px 0px;}"); 
                writer.println("footer { position:fixed; left:0px; bottom:0px; height:30px; width:100%; padding:10px 0px;"); 
                writer.println(" text-align:center; font-family: 'Press Start 2P', cursive; font-size:20px;}"); 
                writer.println(" img {    display: block;    margin-left: auto;    margin-right: auto;    border: 10px solid #021a40;    }"); 
                writer.println("</style></head><body><br><br><br><h1 class='padding'>CONGRATULATIONS </h1>"); 
                writer.println("<p class='padding'>On behalf of Wellesley College CS department, <br>you have earned a Bachelor of Pokemons"); 
                writer.println(" Computer Science degree <br><br><br>"); 
                writer.println("<img src='http://static2.wikia.nocookie.net/__cb20130701024612/trollpasta/images/b/b3/PIKACHU-pikachu-29274386-861-927_(1).jpg'"); 
                writer.println(" alt='happy pikachu!' width='300' height='300'><br><p class='padding'>Love,<br>KBot</p>"); 
                writer.println("</body></html>"); 
                
                writer.close();
              } catch (Exception e) {
                System.out.println("No diploma, sorry. You won though!");
              }
            } else { //you've won, but you need to go to the next prof
              JOptionPane.showMessageDialog(currentWorld, battle.getStatus()); //tells you 
              horizontalWalls[7][4]=false; //opens the "door" 
              grid.paintGrid(); //paints so that the open door is seen
            }
          }
          //temp= p1;
        }
      } else if (selectedPoke.getPosition().equals(new Location(5,8))){
        String s = scrollText.getText(); 
        scrollText.setText(s + "Hey! Stop standing on me!\n"); 
      } else if (selectedPoke.getPosition().equals(new Location(9,9))) {
        reset(); 
      } else { 
        //this text will display if you're in a random place on the grid
        String s = scrollText.getText(); 
        if (battle.hasWonYet() && battled) { //after you win, you're told to leave
          scrollText.setText(s + "Proceed to the next professor!\n"); 
        } else { //if you haven't won yet, you're asked to come closer to the prof to battle
          scrollText.setText(s + "Please come closer!\n"); 
        }
        //if you've won the whole game, you're asked to go look for your diploma
        if (!treeOrder.hasNext()) scrollText.setText("Check the folder for your diploma!\n");
      }
      //if you're in a battle, we decided that you can't move
    } else if (arg.equals("RIGHT")) {
      clearOutput();
      if (!inBattle){
        try { 
          selectedPoke().right();
        } catch (MoveException me) { 
        } 
      } else {
        String s= scrollText.getText();
        scrollText.setText(s+"\nCan't run away now!\n");
      }
    } else if (arg.equals("LEFT")) {
      clearOutput();
      if (!inBattle) {
        try { 
          selectedPoke().left();
        } catch (MoveException me) { 
        } 
      } else { 
        String s= scrollText.getText();
        scrollText.setText(s+"\nCan't run away now!\n");
        
      }
    } else if (arg.equals("UP")) {
      clearOutput();
      if(!inBattle){
        try { 
          selectedPoke().up();
        } catch (MoveException me) { 
        } 
      } else {String s= scrollText.getText();
        scrollText.setText(s+"\nCan't run away now!\n");
        
      }
    } else if (arg.equals("DOWN")) {
      clearOutput();
      if (!inBattle){
        try { 
          selectedPoke().down();
        } catch (MoveException me) { 
        } 
      } else {String s= scrollText.getText();
        scrollText.setText(s+"\nCan't run away now!\n");
        
      }
    }
  }
  
  protected String locationString (Location p) {
    return p.toString();
  }
  
  protected String directionString (Direction d) {
    return "Direction[dir=" + d + "]"; 
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
    // //System.out.println("printError: " + s);
    String message = "Poke Error: " + s;
    //System.out.println(message);
    // Output is broken for now.
    // output.setForeground(Color.red);
    // output.setText(message);
  }
  
  public void printValue (String s) {
    // Print value in output area.
    // //System.out.println("printValue: " + s);
    String message = "Value: " + s;
    //System.out.println(message);
    // Output is broken for now.
    // output.setForeground(Color.blue);
    // output.setText(message);
  }
  
  public void printInstruction (String s) {
    // Print instruction in output area.
    // //System.out.println("printInstruction: " + s);
    //System.out.println(s);
    // Output is broken for now.
    // output.setForeground(Color.black);
    // output.setText(s);
  }
  
  
  public void clearOutput() {
    ////System.out.println("clearOutput: ");
    //output.setText("");
  }
  
  /*******************************************************
    * returns the selected Poke
    * @return selectedPoke        your Poke
    ******************************************************/
  public Poke selectedPoke () {
    // Return the currentPoke, or yell if it's null.
    if (selectedPoke == null) {
      throw new PokeException ("No poke is selected!");
    } else {
      return selectedPoke;
    }
  }
  
  protected void selectPoke (Poke b) {
    Poke previousSelected = selectedPoke;
    selectedPoke = b;
    this.pokeChanged(selectedPoke);
    if (previousSelected != null) {
      this.pokeChanged(previousSelected);
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
      throw new PokeException(
                              "addHorizontalWall: point out of range -- ("
                                + x + ", " + y + ")");
    }
  }
  
  // [lyn, 11/11/06] Fixed fencepost error in this method 
  public void addVerticalWall (int x, int y) {
    if ((0 <= x) && (x <= cols) && (0 <= y) && (y < rows)) {
      verticalWalls[x][y] = true;
    } else {
      throw new PokeException(
                              "addVerticalWall: point out of range -- ("
                                + x + ", " + y + ")");
    }
  }
  
  // public void start () {
  //   // //System.out.println("start()");
  //  //debugPrintln("Calling PokeGraid.paint() from start()");
  //  //**** grid.paint(); // **** this caused problems - too soon? 
  // }
  
  public void start () {
    // //System.out.println("start()");
    debugPrintln("Calling PokeGraid.paint() from start()");
    grid.paint();  
  }
  
  public void paint( Graphics g ) {
    //debugPrintln("PokeWorld asked by system to paint(g).");
    super.paint( g );
  }
  
  public void markTrail(Location p, Color c) {
    marks[p.x][p.y] = c;
  }
  
  
  // public void paintComponent( Graphics g ) { // need to extend JPanel for this?
  //  //debugPrintln("PokeWorld asked by system to paint(g).");
  //  super.paintComponent( g );
  // }
  
  public void run () {
    // This is a hook that subclasses can override without having to worry 
    // about other operations that might be performed by start().
    // By default, does nothing. 
    // this.printError("Default run() behavior of PokeWorld is to do nothing.");
  }
  
  // New predicate for checking if location is in the poke grid
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
    /* //System.out.println("Add coords: p1 = " + p1 
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
    // //System.out.println("getStringAt(" + p + ") = " + result);
    return result;
  } 
  
  public void add (Poke b) {
    // //System.out.println("Pokes=" + pokes );
    pokes.addElement(b);
    debugPrintln("Calling PokeGrid.draw() from PokeWorld.add(Poke)");
    grid.draw(b);
  }
  
  /*******************************************************
    * initializes the battle
    * creates the PokemonBattle, creates the battleLog (which is displayed
    * in the scrollbox)
    ******************************************************/
  public void initBattle() { 
    battle = new PokemonBattle(p1, p2);
    first= battle.getStatus(); //the FIRST STATEMENT 
    battle.playPokemonBattle(); 
    battleLog = battle.getAttackStat(); 
    battleLength = battleLog.size(); 
  }
  /*******************************************************
    * draws a professor sprite that's boxed in by walls (therefore, the 
    * player can't move on top of the professor)
    ******************************************************/
  public void createStadium(Graphics g, int width, int height) { 
    int x = 5; 
    int y = 8; 
    
    //cages in the professor
    verticalWalls[x][y-1] = true;
    verticalWalls[x-1][y-1] = true; 
    horizontalWalls[x-1][y] = true; 
    //horizontalWalls[x-1][y-1] = true; 
    
    //sets up the stadium
    for (int i=0; i<4; i++) { 
      verticalWalls[x-1-i][y-2-i] = true; 
      verticalWalls[x+i][y-2-i] = true; 
      horizontalWalls[x-2-i][y-2-i] = true; 
      horizontalWalls[x+i][y-2-i] = true; 
    }
    //Poke prof = new Poke(); 
    grid.paint(); 
    try { 
      Random rand = new Random(); 
      //randomly chooses a prof image
      Image sprite = ImageIO.read(new File("prof4.png"));
      Image[] sprites = {sprite}; //prof doesn't turn and only uses one Image 
      prof= new Poke(p2);
      Location profLoc = new Location (x,y); 
      prof.setPosition(profLoc); 
    } catch (Exception e) { 
    }
  }
  
  public void placeBagels(int bagels, int width, int height)
  {
    //Randomizer rand = new Randomizer();
    if (bagels > 0) {
      //int x = rand.intBetween(1, width);
      //int y = rand.intBetween(2, height);
      placeBagels(bagels, width, height);
    }
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
      throw new PokeException("Shouldn't happen: wallInDirection");
    }
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
  
  void pokeChanged(Poke b) {
    debugPrintln("Calling drawCell from pokeChanged ");
    grid.drawCell(b.position());
  }
  
  public void pokeMoved(Poke b, Location oldpos, Location newpos) {
    // We have been informed that B has moved from oldpos to newpos.
    // For multiple pokes, should move poke to top!
    debugPrintln("Calling drawCell (1) from pokeMoved");
    grid.drawCell(oldpos);
    debugPrintln("Calling drawCell (2) from pokeMoved");
    grid.drawCell(newpos);
  }
  
  public void draw (Poke b) {
    debugPrintln("Draw poke " + b);
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
  
  // [lyn,11/11/06] Modified to handle null case correctly
  public Color markColorAt(Location p) {
    Color c = marks[p.x][p.y];
    if (c == null) {
      return grid.getFloorColor();
    } else {
      return c;
    }
  }
  
}
