package algorithmsPortfolio;

import java.util.*;
import java.io.*;

public class AdjListGraph extends Graph {
	private ArrayList<ArrayList<Integer>> adjList;
	
	/**
	 * constructor: creates an empty 2d array list of integers to hold the adjacency list.
	 * Graph is directed by default.
	 */
	public AdjListGraph() {

		super();
		this.debug = false;
		this.directed = true;
		this.vertices = new ArrayList<Vertex> ();
		this.adjList = new ArrayList<ArrayList<Integer>> (0);
		if (debug) {
			System.out.println("new AdjListGraph initialized");
		}
	}
	
	/**
	 * returns the adjacency list
	 * @return adjList
	 */
	public ArrayList<ArrayList<Integer>> getAdjList() {
		return this.adjList;	
	}
	
	/**
	 * get a vertex's children vertices
	 * @param v vertex
	 * @return ArrayList of integers of vertices who are children of v
	 */
	public ArrayList<Integer> getChildren(Vertex v) {
		return this.adjList.get(v.getName());
	}
	
	/**
	 * returns undirected version of AdjList
	 * @return Undirected AdjList
	 */
	public AdjListGraph getUndirAdjListGraph() {

		AdjListGraph undirAdjListGraph = new AdjListGraph();
		undirAdjListGraph.setNumVertices(this.getNumVertices());
		undirAdjListGraph.setAdjList(this.adjList);
		undirAdjListGraph.setDirected(false);
		return undirAdjListGraph;
	}
	
	/**
	 * returns dfs version of adjList
	 * @return dfs adjList graph
	 */
	public AdjListGraphDfs getDfsAdjListGraph() {

		AdjListGraphDfs dfsGraph = new AdjListGraphDfs();
		dfsGraph.setNumVertices(this.getNumVertices());
		dfsGraph.setAdjList(this.adjList);
		return dfsGraph;
	}
	
	/**
	 * gets Connected Components
	 * @return connected components in a 2d ArrayList of integers 
	 * which is a list of lists of vertices that are connected
	 */
	public ArrayList<ArrayList<Integer>> getConnectedComponents() {

		ArrayList<ArrayList<Integer>> connComps = new ArrayList<ArrayList<Integer>> (0);
		boolean found = false;
		AdjListGraphDfs dfsGraph = getDfsAdjListGraph();
		dfsGraph.setDirected(false);
		for (int v = 0; v < this.getNumVertices(); v++) {
			found = false;
			for (int i = 0; i < connComps.size(); i++) {
				for (int j = 0; j < connComps.get(i).size(); j++) {
					if (connComps.get(i).get(j) == v) {
						found = true;
					}
				}
			}
			
			if (!found) {
				dfsGraph.resetVisited();
				dfsGraph.dfs(v);
				connComps.add(dfsGraph.getVisited());
			}
			
			if (debug) {
				System.out.println("v" + v + " found: " + found);
			}
		}
		
		return connComps;
	}
	
	/**
	 * returns dfs tree
	 * @param v the vertex that will be the root of the dfs tree
	 * @return dfs tree in the form of an array where each index represents a vertex that logs that vertex's parent
	 */
	public int[] getDfsTree(int v) {
		AdjListGraphDfs dfsGraph = new AdjListGraphDfs();
		dfsGraph.setAdjList(this.adjList);
		dfsGraph.dfs(v);
		return dfsGraph.getDfsTree(v);
	}
	
	/**
	 * get a vertex that does have an edge from that vertex to any other vertex
	 * @return a vertex with out degree zero
	 */
	public int getOutDegreeZeroVertex() {
		for (int i = 0; i < this.adjList.size(); i++) {
			if (adjList.get(i).size() == 0) {
				return i;
			}
		       }
		return -1;

	}
	
	/**
	 *  gets graph with all directed edges going the opposit direction
	 * @return reverse AdjListGraph
	 */
	public AdjListGraph getReverseGraph() {
		AdjListGraph reverseGraph = new AdjListGraph();
		reverseGraph.addVertices(this.getNumVertices());
		for (int u = 0; u < this.getNumVertices(); u++) {
			for (int v = 0; v < this.adjList.get(u).size(); v++) {
				reverseGraph.addEdge(v, u);
			}
		}
		
		return reverseGraph;
	}
	
	/**
	 * gets strongly connected components
	 * @return ArrayList of ArrayList of integers of vertices who are strongly connected
	 */
	public ArrayList<ArrayList<Integer>> getStrongConnectComp() {

		AdjListGraphDfs dfsGraph = new AdjListGraphDfs();
		dfsGraph.setAdjList(this.adjList);
		ArrayList<ArrayList<Integer>> stronglyConnected = new ArrayList<ArrayList<Integer>>(this.getNumVertices());
		for (int i = 0; i < this.getNumVertices(); i++) {
			stronglyConnected.add(new ArrayList<Integer> (0));
		}
		for (int i = 0; i < this.getNumVertices(); i++) {
			for (int j = 0; j < this.getNumVertices(); j++) {
				dfsGraph.resetVisited(); 
				dfsGraph.dfs(i);
				if (dfsGraph.isVisited(j)) {
					dfsGraph.resetVisited();
					dfsGraph.dfs(j);
					if (dfsGraph.isVisited(i)) {
						stronglyConnected.get(i).add(j);
					}
				}
			}
		}
		
		ArrayList<ArrayList<Integer>> stronglyConnectedComponents = new ArrayList<ArrayList<Integer>>(0);
		boolean[] found = new boolean[this.getNumVertices()];
		for (int i = 0; i < this.getNumVertices(); i++) {
			found[i] = false;
		}
		
		for (int i = 0; i < stronglyConnected.size(); i++) {
			if (!found[i]) {
				stronglyConnectedComponents.add(stronglyConnected.get(i));
				found[i] = true;
				for (int j = 0; j < stronglyConnected.get(i).size(); j++) {
					found[stronglyConnected.get(i).get(j)] = true;
				}
			}
		}
		
		return stronglyConnectedComponents;
	}
	
	@Override
	public void setDirected(boolean directed) {

		super.setDirected(directed);
		
		// if you are changing the graph to undirected and it already
		//has vertices, adds a new edge for each pre-existing edge if
		//it does not already exist
		if (this.directed == false && !this.isEmpty()) {
			for (int i = 0; i < adjList.size(); i++) {
				for (int j = 0; j < adjList.get(i).size(); j++) {
					if (!hasEdge(adjList.get(i).get(j), i)) {
						addEdge(adjList.get(i).get(j), i);
					}
				}
			}
		}
	}

	/**
	 * sets adjList
	 * @param newAdjList adjacency list to be set 
	 */
	public void setAdjList(ArrayList<ArrayList<Integer>> newAdjList) {
		addVertices(newAdjList.size());
		this.adjList = newAdjList;
		if (debug) {
			System.out.println("setAdjList called. number of vertices initialized: " + newAdjList.size());
		}
	}
	
	@Override
	public void addVertices(int numNewVertices) {

		if (debug) {
			System.out.println("AdjListGraph addVertices called");
			System.out.println("numNewVertices: " + numNewVertices);
		}
		super.addVertices(numNewVertices);
		for (int i = 0; i < numNewVertices; i++) {
			this.adjList.add(new ArrayList<Integer> (0));
			if (debug) {
				System.out.println("new vertex added to adjList");
			}
		}
		if (debug) {
			System.out.println("adjList.size(): " + adjList.size());
		}
	}
	
	@Override
	public void addEdge(int u, int v) {


		super.addEdge(u, v);
		
		if (this.hasEdge(u, v)) { //if edge is already in graph doesn't add it again
			return;
		}
		
		this.adjList.get(u).add(v); //adds edge to graph
		 
		if (!this.directed && !this.hasEdge(v, u)) { //if undirected add the reverse edge
			this.adjList.get(v).add(u);
			if (debug) {
				System.out.println("Graph is undirected and the new edge reverse does not exist");
			}
		}
	}	
	
	/**
	 * removes edge from graph
	 * @param u first vertex
	 * @param v second vertex
	 */
	public void removeEdge(int u, int v) {
		for (int i = 0; i < this.adjList.get(u).size(); i++) {
			if (this.adjList.get(u).get(i) == v) {
				this.adjList.get(u).remove(i);
			}
		}
		
		//if the graph is undirected also remove the reverse edge
		if (!this.directed) {
			for (int i = 0; i < this.adjList.get(v).size(); i++) {
				if (this.adjList.get(v).get(i) == u) {
					this.adjList.get(v).remove(i);
				}
			}
		}
	}
	
	/**
	 * removes a vertex from the adjacency list and all edges connected to that vertex
	 * @param v the vertex being removed
	 */
	public void removeVertex(int v) {

		this.adjList.remove(v);
		int numVertices = this.getNumVertices();
		for (int i = 0; i < numVertices - 1; i++) {
			for (int j = 0; i < this.adjList.get(i).size(); j++) {
				if (this.adjList.get(i).get(j) == v) {
					this.adjList.get(i).remove(j);
				}
			}
		}
		
	}
	
	@Override
	public boolean hasEdge(int u, int v) {

		if (u > this.getNumVertices()) {
			return false;
		}
		for (int i = 0; i < adjList.get(u).size(); i++) {
			if (adjList.get(u).get(i) == v) {
				return true;
			}
		}

		return false;
	}

	/**
	 * returns true if graph is connected, false if not
	 * @return isConnected
	 */
	public boolean isConnected() {

		if (debug) {
			System.out.println("isConnected() called");
		}
		AdjListGraphDfs dfsAdjList = this.getDfsAdjListGraph();
		dfsAdjList.setNumVertices(this.getNumVertices());
		dfsAdjList.setAdjList(this.adjList);
		dfsAdjList.setDirected(false);
		dfsAdjList.dfs(0);
		
		for (int i = 0; i < this.getNumVertices(); i++) {
			if (!dfsAdjList.isVisited(i)) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean isEmpty() {

		for (int i = 0; i < this.adjList.size(); i++) {
			if (this.adjList.get(i).size() > 0) {
				return false;
			}
		}
		return true;
	}	
	
	//toStrings and prints
	
	@Override
	public String toString() {

		String string = new String();
		int numVertices = this.getNumVertices();
		for (int i = 0; i < numVertices; i++) {
			string = string + i + ':' + ' ';
			for (int j = 0; j < this.adjList.get(i).size(); j++) {
				string += this.adjList.get(i).get(j);
				string += ' ';
			}
			string += '\n';
		}
		
		return string;
	}
	
	/**
	 * converts lists of strongly connected components to a string
	 * @return string of strongly connected components
	 */
	public String strongConnCompsToString() {
		ArrayList<ArrayList<Integer>> connComps = this.getStrongConnectComp();
		String string = new String();
		string = "Strongly Connected components:";
		for (int i = 0; i < connComps.size(); i++) {
			string += "\n{";
			for (int j = 0; j < connComps.get(i).size(); j++) {
				if (j != 0) {
					string += ", ";
				}
				string += connComps.get(i).get(j);
			}
			string += "}";
		}
		
		return string;
	}
	
	/**
	 * prints connected components
	 */
	public void printConnComps() {

		ArrayList<ArrayList<Integer>> connComps = this.getConnectedComponents();
		String string = new String();
		string = "Connected components:";
		for (int i = 0; i < connComps.size(); i++) {
			string += "\nComponent: ";
			for (int j = 0; j < connComps.get(i).size(); j++) {
				string += connComps.get(i).get(j) + " ";
			}
		}
		
		System.out.println(string);
	}
	
	/**
	 * prints strongly connected components
	 */
	public void printStrongConnComps() {
		System.out.println(this.strongConnCompsToString());
	}

	//Converters
	
	/**
	 * converts adjacency list to Graph
	 * @return Graph
	 */
	public BasicGraph toGraph() {
		BasicGraph basicGraph = new BasicGraph();
		basicGraph.addVertices(this.adjList.size());
		
		for (var i = 0; i < this.adjList.size(); i++) {
			for (var j = 0; j < this.adjList.get(i).size(); j++) {
				basicGraph.addEdge(i, adjList.get(i).get(j));
			}
		}
		
		return basicGraph;
	}
	
	/**
	 * converts adjacency list to matrix
	 * @return adjacency Matrix
	 */
	public AdjMatrixGraph toAdjMatrix() {
		AdjMatrixGraph adjMatrix = new AdjMatrixGraph();
		adjMatrix.addVertices(adjList.size());
		for (int i = 0; i < adjList.size(); i++) {
			for (int j = 0; j < adjList.get(i).size(); j++) {
				adjMatrix.addEdge(i, adjList.get(i).get(j));
			}
		}
		
		return adjMatrix;
	}
	
	//write to and from files, from Ileana's and Sivan's code
	
	/**
	 * private for parsing graph from not-perfectly-formatted text file
	 * @param line
	 * @param myReader
	 * @return empty string
	 */
	private String skipEmptyLines(String line, Scanner myReader){
	 while (myReader.hasNextLine())
	 {
	 if (line.length() == 0){
	 line = myReader.nextLine();
	 } else {
	 String[] tokens = line.split(" ");
	 if (tokens.length > 0){ return line;
	 } 
	 }
	 };
	 
	 return "";
	 }
	
	/**
	 * get number of vertices
	 * @param line
	 * @param myReader
	 * @return line
	 */
	private String parseNrVerts(String line, Scanner myReader){
	 
		 line = skipEmptyLines(line, myReader);
		 if (line.length() == 0) {
		 // reached the end of file without nr vertices
		 // must init the graph as empty, somehow
		 // must close the file
		 myReader.close();
		 return "";
		 }
			 
		 String[] nrVertsStringList = line.split(" ");
		 
		 int nrV=Integer.parseInt(nrVertsStringList[0]);
		 this.setNumVertices(nrV);
		 line = myReader.nextLine();
		 line = skipEmptyLines(line, myReader);
		 return line;
	 } 
	
	/**
	 * parse file
	 * @param myReader
	 */
	private void parseFile(Scanner myReader){
		 // The first line has the number of nrVertices
		 String line = myReader.nextLine();
		 
		 line = parseNrVerts(line,myReader);
		 
		 if (line.length() == 0) {
		 // reached the end of file without edges
		 // must init the graph as empty, somehow
		 // must close the file
		 myReader.close();
		 return;
		 }
		 // Now attempt to process the edges
		 // Next lines till the end of file is one edge per line
		 
		 int nrEdges = 0;
		 while (myReader.hasNextLine()){
		 nrEdges++;
		 String[] edgeString = line.split(" ");
		 String u = edgeString[0];
		 String v = edgeString[1];
		 addEdge(Integer.parseInt(u)-1, Integer.parseInt(v)-1);
		 line = myReader.nextLine(); }
	 
	 }

	/**
	 * read from text file
	 * @param filename name of file
	 */
	 public void readFromTxtFile(String filename){
	 
		 try {
		 File graphFile = new File(filename);
		 Scanner myReader = new Scanner(graphFile);
		 parseFile(myReader);
		 myReader.close();
		 } catch (FileNotFoundException e) {
		 System.out.println("ERROR: DGraphEdges, readFromTxtFile: file not found.");
		 e.printStackTrace();
		 }
	 } 
	 
	 /**
	  * write to text file
	  * @param filename name of file
	  */
	 public void writeToTextFile(String filename){
		 try {
		 FileWriter myWriter = new FileWriter(filename);
		 myWriter.write(toString());
		 myWriter.close();
		 } catch (IOException e) {
		 System.out.println("An error occurred.");
		 e.printStackTrace();
		 }
	 }
	 
	 /**
	  * write to mathematica file
	  * @param filename name of file
	  */
	 public void writeToMathematicaFile(String filename){
		 try {
		 FileWriter myWriter = new FileWriter(filename);
		 myWriter.write(strongConnCompsToString());
		 myWriter.close();
		 } catch (IOException e) {
		 System.out.println("An error occurred.");
		 e.printStackTrace();
		 }
	 }
}


