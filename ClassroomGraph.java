/*
 * Karina Chan
 * Midterm 3
 * CS 230
 * 11/24/13


********************************************************************
  * AdjMatGraphPlus.java  Sample Solutions of Exam3 
  * Implementation of the GraphPlus.java interface using adjacency matrix
  * of booleans. At the bottom with the new methods: DFS uses arraystack and 
  * BFS uses a linked queue to keep track of traversals. 
  * Topological sort makes use of the source/sink (used sinks in this one)
  * 
  * KNOWN FEATURES/BUGS: 
  * It handles unweighted graphs only, but it can be extended.
  * It does not handle operations involving non-existing vertices
  ********************************************************************/

import javafoundations.*;
import javafoundations.LinkedQueue; //for BFS
import javafoundations.ArrayStack; //for DFS
import java.util.*;
import java.io.*;


public class ClassroomGraph<T> implements Graph<T>
{
  private final int NOT_FOUND = -1;
  private final int DEFAULT_CAPACITY = 1; // Small so that we can test expand
  
  private int n;   // number of vertices in the graph
  private boolean[][] arcs;   // adjacency matrix of arcs
  private T[] vertices;   // values of vertices
  
  /******************************************************************
    Constructor. Creates an empty graph.
    ******************************************************************/
  public ClassroomGraph()
  {
    n = 0;
    this.arcs = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
  }
  
  
  /******************************************************************
    * Second constructor:
    * Creates a new graph using the data found in a .tgf file.
    If the file does not exist, a message is printed. 
    *****************************************************************/
  public ClassroomGraph(String tgf_file_name) {
    //reset current graph
    vertices = (T[]) (new Object[DEFAULT_CAPACITY]); 
    arcs = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    n = 0;
    
    try{
      
      Scanner fileReader = new Scanner(new File(tgf_file_name));
      while (!fileReader.next().equals("#")){
        T line = (T) fileReader.next();
        addVertex(line);
      }
      
      while (fileReader.hasNext()){
        int arcVertex1 = fileReader.nextInt();
        int arcVertex2 = fileReader.nextInt();
        //System.out.println ("Arc Vertex 1: " + arcVertex1);
        //System.out.println ("Arc Vertex 2: " + arcVertex2);
        addArc(vertices[arcVertex1 -1], vertices[arcVertex2 -1]);
      }
      
    } catch (IOException ex) {
      System.out.println(" ***(T)ERROR*** The file was not found: " + ex);
    }
  }
  
  /******************************************************************
    Returns true if the graph is empty and false otherwise. 
    ******************************************************************/
  public boolean isEmpty()
  {
    return (n == 0);
  }
  
  /******************************************************************
    Returns the number of vertices in the graph.
    ******************************************************************/
  public int n()
  { return n; }
  
  /******************************************************************
    Returns the number of arcs in the graph by counting them.
    ******************************************************************/
  public int m()
  {
    int total = 0;
    
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
      if (arcs[i][j]) total++; 
    return total; 
  }
  
  /******************************************************************
    Returns true iff a directed edge exists from v1 to v2.
    ******************************************************************/
  public boolean isArc (T vertex1, T vertex2)
    
  { //System.out.println(getIndex(vertex1));
    return arcs[getIndex(vertex1)][getIndex(vertex2)]; }
  
  
  /******************************************************************
    Helper. Returns true iff an arc exists between two given indices 
    ******************************************************************/
  private boolean isArc (int index1, int index2)
  {
    if (indexIsValid(index1) && indexIsValid(index2))
      return arcs[index1][index2] == true;
    else return false;
  }
  
  
  /******************************************************************
    Returns true iff an edge exists between two given vertices
    which means that two corresponding arcs exist in the graph
    ******************************************************************/
  public boolean isEdge (T vertex1, T vertex2){
    return (isArc(vertex1, vertex2) && isArc(vertex2, vertex1)); }
  
  
  /******************************************************************
    Returns true IFF the graph is undirected, that is, for every 
    pair of nodes i,j for which there is an arc, the opposite arc
    is also present in the graph.  
    ******************************************************************/
  public boolean isUndirected(){
    for (int i = 0; i < n(); i++)
      for (int j = 0; j < n(); j++)
      if (arcs[i][j])
      if (!arcs[j][i]) 
      return false;
    return true;
  };
  
  
  /******************************************************************
    Adds a vertex to the graph, expanding the capacity of the graph
    if necessary.  If the vertex already exists, it does not add it.
    ******************************************************************/
  public void addVertex (T vertex)
  {  if (getIndex(vertex) == NOT_FOUND) {
    if (n == vertices.length)
      expandCapacity();
    
    vertices[n] = vertex;
    for (int i = 0; i <= n; i++)
    {
      arcs[n][i] = false;
      arcs[i][n] = false;
    }      
    n++;
  }
  }
  
  /******************************************************************
    Helper. Creates new arrays to store the contents of the graph 
    withtwice the capacity.
    ******************************************************************/
  private void expandCapacity()
  {
    T[] largerVertices = (T[])(new Object[vertices.length*2]);
    boolean[][] largerAdjMatrix = 
      new boolean[vertices.length*2][vertices.length*2];
    
    for (int i = 0; i < n; i++)
    {
      for (int j = 0; j < n; j++)
      {
        largerAdjMatrix[i][j] = arcs[i][j];
      }
      largerVertices[i] = vertices[i];
    }
    
    vertices = largerVertices;
    arcs = largerAdjMatrix;
  }
  
  
  /******************************************************************
    Removes a single vertex with the given value from the graph.  
    Uses equals() for testing equality
    ******************************************************************/
  public void removeVertex (T vertex)
  {
    for (int i = 0; i < n; i++)
      if (vertex.equals(vertices[i]))
      removeVertex(i);
  }
  
  /******************************************************************
    Helper. Removes a vertex at the given index from the graph.   
    Note that this may affect the index values of other vertices.
    ******************************************************************/
  private void removeVertex (int index)
  {
    if (indexIsValid(index))
    {
      n--;
      
      for (int i = index; i < n; i++)
        vertices[i] = vertices[i+1];
      
      for (int i = index; i < n; i++)
        for (int j = 0; j <= n; j++)
        arcs[i][j] = arcs[i+1][j];
      
      for (int i = index; i < n; i++)
        for (int j = 0; j < n; j++)
        arcs[j][i] = arcs[j][i+1];
    }
  }
  
  /******************************************************************
    Inserts an edge between two vertices of the graph.
    If one or both vertices do not exist, ignores the addition.
    ******************************************************************/
  public void addEdge (T vertex1, T vertex2)
  {
    // getIndex will return NOT_FOUND if a vertex does not exist,
    // and the addArc calls will not insert it
    addArc (getIndex(vertex1), getIndex(vertex2));
    addArc (getIndex(vertex2), getIndex(vertex1));
  }
  
  /******************************************************************
    Inserts an arc from vertex1 to vertex2.
    If the vertices exist, else does not change the graph. 
    ******************************************************************/
  public void addArc (T vertex1, T vertex2){
    addArc(getIndex(vertex1), getIndex(vertex2));
  }
  
  /******************************************************************
    Helper. Inserts an edge between two vertices of the graph.
    ******************************************************************/
  private void addArc (int index1, int index2){
    if (indexIsValid(index1) && indexIsValid(index2))
      arcs[index1][index2] = true;
  }
  
  
  /******************************************************************
    Removes an edge between two vertices of the graph.
    If one or both vertices do not exist, ignores the removal.
    ******************************************************************/
  public void removeEdge (T vertex1, T vertex2)
  {
    removeArc (getIndex(vertex1), getIndex(vertex2));
    removeArc (getIndex(vertex2), getIndex(vertex1));
  }
  
  
  /******************************************************************
    Removes an arc from vertex v1 to vertex v2,
    if the vertices exist, else does not change the graph. 
    ******************************************************************/
  public void removeArc (T vertex1, T vertex2){
    removeArc (getIndex(vertex1), getIndex(vertex2)); 
  }
  
  /******************************************************************
    Helper. Removes an arc from index v1 to index v2.
    ******************************************************************/
  private void removeArc (int index1, int index2)
  {
    if (indexIsValid(index1) && indexIsValid(index2))
      arcs[index1][index2] = false;
  }
  
  
  
  /******************************************************************
    Returns the index value of the first occurrence of the vertex.
    Returns NOT_FOUND if the key is not found.
    ******************************************************************/
  private int getIndex(T vertex)
  {
    int index=NOT_FOUND;
    for (int i = 0; i < n; i++) {
      if (vertices[i].equals(vertex)) index=i;
    }
    return index;
    
  }
  
  /******************************************************************
    Returns the vertex object that is at a certain index
    ******************************************************************/
  private T getVertex(int v)
  {   return vertices[v]; 
  }
  
  /******************************************************************
    Returns true if the given index is valid. 
    ******************************************************************/
  private boolean indexIsValid(int index){  
    return ((index < n) && (index >= 0));  
  }
  
  /******************************************************************
    Retrieve from a graph the vertices x pointing to vertex v (x->v)
    and returns them onto a linked list
    ******************************************************************/
  
  public LinkedList<T> getPredecessors(T vertex){
    LinkedList<T> neighbors = new LinkedList<T>();
    
    int v = getIndex(vertex); 
    
    for (int i = 0; i < n; i++)
    {
      if (arcs[i][v])  
        neighbors.add(getVertex(i)); // if T then add i to linked list
    }    
    return neighbors;    
  }
  
  /******************************************************************
    * Retrieve from a graph the vertices x following vertex v (v->x)
    and returns them onto a linked list
    ******************************************************************/
  public LinkedList<T> getSuccessors(T vertex){
    LinkedList<T> neighbors = new LinkedList<T>();
    
    int v = getIndex(vertex); 
    
    for (int i = 0; i < n; i++)
    {
      if (arcs[v][i])  
        neighbors.add(getVertex(i)); // if T then add i to linked list
    }    
    return neighbors;    
  }
  
  /******************************************************************
    Returns a string representation of the graph. 
    ******************************************************************/
  public String toString()
  {
    if (n == 0)
      return "Graph is empty";
    
    String result = new String("");
    
    result += "Arcs\n";
    result += "-----\n";
    result += "i ";
    
    for (int i = 0; i < n; i++) 
    {
      result += "" + getVertex(i);
      if (i < 10)
        result += " ";
    }
    result += "\n";
    
    for (int i = 0; i < n; i++)
    {
      result += "" + getVertex(i) + " ";
      
      for (int j = 0; j < n; j++)
      {
        if (arcs[i][j])
          result += "1 ";
        else
          result += "- "; //just empty space
      }
      result += "\n";
    }
    
    return result;
  }
  
  
  
  /******************************************************************
    * Saves the current graph into a .tgf file.
    * If it cannot save the file, a message is printed. 
    *****************************************************************/
  

  public void saveTGF(String tgf_file_name) {
    try {
      PrintWriter writer = new PrintWriter(new File(tgf_file_name));
      
      //prints vertices by iterating through array "vertices"
      for (int i = 0; i < n(); i++){
        if (vertices[i] == null){
          break;
        }
        else{
          writer.print((i+1) + " " + vertices[i]);
          writer.println("");
        }
      }
      writer.print("#"); // Prepare to print the edges
      writer.println("");
      
      //prints arcs by iterating through 2D array
      for (int i = 0; i < n(); i++){
        for (int j = 0; j < n(); j++){
          if (arcs[i][j] == true){
            writer.print((i+1) + " " + (j+1));
            writer.println("");
          }
        }
      }
      writer.close();
    } catch (IOException ex) {
      System.out.println("***(T)ERROR*** The file could not be written: " + ex);
    }
  }
  

  

 
  
  /******************************************************************
    Testing program. 
    ******************************************************************/
  
  public static void main (String args[]){
    
   
    
  }
}