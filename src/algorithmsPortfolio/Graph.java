package algorithmsPortfolio;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

abstract class Graph {
	boolean debug;	
	boolean directed;
	ArrayList<Vertex> vertices;
	ArrayList<ArrayList<Edge>> edges;
	
	/**
	 * constructor
	 */
	public Graph() {
		this.debug = true;
		this.directed = true; //graph is directed by default
		this.vertices = new ArrayList<Vertex> ();
		this.edges = new ArrayList<ArrayList<Edge>> ();
		if (debug) {
			System.out.println("new Graph initialized");
		}
	}
	
	//Getters
	
	/**
	 * gets number of vertices
	 * @return number of vertices in the graph
	 */
	public int getNumVertices() {
		return this.vertices.size();
	}
	
	//Setters
	
	/**
	 * sets graph to be directed or not
	 * @param nowDirected true or false value indicating whether the graph should become directed or undirected
	 */
	public void setDirected(boolean nowDirected) {

		if (debug) {
			System.out.println("setDirected(" + nowDirected + ") called" );
		}
		
		//if no change is made, do nothing
		if (this.directed == nowDirected) {
			if (debug) {
				System.out.println("this.directed was not changed, method returned early");
			}
			
			return;
		}
		
		//If an undirected graph is becoming directed and it already has edges,
		//prints a warning.
		if (!this.directed && nowDirected && !isEmpty()) {
			System.out.println("Warning: You are making an undirected graph directed."
							+ " All edges previously added will go in both directions"
							+ " unless removed.");
		}
		
		//change whether the graph is directed or not
		this.directed = nowDirected;
		if (debug) {
			System.out.println("this.directed changed to " + this.directed);
		}
	}
	
	/**
	 * changes the number of vertices, (can add vertices but not remove them)
	 * @param newNumVertices number of vertices to be set
	 */
	public void setNumVertices(int newNumVertices) {
		if (debug) {
			System.out.println("Graph setNumVertices called");
			System.out.println("newNumVertices: " + newNumVertices);
		}
		//if the new number of vertices is less than the old number 
		//print a warning and return without changing the vertices
		if (newNumVertices < this.getNumVertices()) {
			System.out.println("Warning: tried to set " + (this.getNumVertices() - newNumVertices) + 
					" fewer vertices. Please select vertices to remove");
			return;
		}
		
		//add the number of vertices needed
		this.addVertices(newNumVertices - this.getNumVertices());
		if (debug) {
			System.out.println("Graph setNumVertices finished");
			System.out.println("this.numVertices: " + this.getNumVertices());
		}
		
	}
	
	/**
	 * adds vertices to the graph
	 * @param numNewVertices the number of vertices to be added
	 */
	public void addVertices(int numNewVertices) {
		int numVertices = this.getNumVertices();
		for (int i = numVertices; i < numNewVertices; i++) {
			Vertex newVertex = new Vertex(i);
			this.vertices.add(newVertex);
			this.edges.add(new ArrayList<Edge> ());
			for (int j = 0; j < this.getNumVertices(); j++) {
				this.edges.get(i).add(new Edge());
				if (debug) {
					System.out.println("edge created: " + i + j);
				}
			}
			for (int j = 0; j < this.getNumVertices(); j++) {
				this.edges.get(j).add(new Edge ());
				if (debug) {
					System.out.println("edge created: " + j + (this.edges.get(j).size() - 1));
				}
			}
			if (debug) {
				System.out.println("added Vertex: " + i 
						+ "\nedges.size(): " + this.edges.size() 
						+ "\nedges.get(0).size(): " + this.edges.get(0).size()
						+ "\nedges.get(" + (this.getNumVertices() - 1) + ").size(): " + edges.get(this.getNumVertices() - 1).size());
			}
		}
	}
	
	/**
	 * adds an edge to the graph
	 * @param u first vertex of the edge
	 * @param v second vertex of the edge
	 */	
	public void addEdge(int u, int v, int weight) {
		if (debug) {
			System.out.println("Graph addEdge called " + u + v);
		}
		if(this.hasEdge(u, v)) {
			System.out.println("Warning: Edge already exists");
			return;
		}
		
		this.edges.get(u).set(v, new Edge(u, v, weight));
	}

	public void addEdge(int u, int v) {
		addEdge(u, v, 0);
	}
	
	/**
	 * returns true if the edge is in the graph, otherwise returns false
	 * @param u first vertex
	 * @param v second vertex
	 * @return hasEdge true if edge is in graph false if not
	 */
	abstract boolean hasEdge(int u, int v);
	
	/**
	 * returns true if graph is directed, false if undirected
	 * @return isDirected
	 */
	public boolean isDirected() {
		return this.directed;
	}
	
	/**
	 * returns true if there are no edges in the graph, otherwise returns false
	 * @return isEmpty
	 */
	abstract boolean isEmpty();
	
	/**
	 * converts the graph to a string
	 * @return string version of the graph
	 */
	public String toString() { //This should be abstract but thats not allowed cause toString is a parent method
		return "Graph toString called. This is a problem";
	}
	
	/**
	 * prints graph as a string
	 */
	public void print() {
		System.out.println(this.toString());
	}
	
	/**
	 * reads a graph from a file. code from Sivan
	 * @param filename name of file
	 */
	public void readFromFile(String filename){

		 try {
		 File graphFile = new File(filename);
		 Scanner myReader = new Scanner(graphFile);
		 // The first line is just a bracket, so we can skip it
		 myReader.nextLine();
		 // The second line has the number of vertices
		 String data = myReader.nextLine();
		 String cleanData = "";
		 for (int i = 0; i < data.length()-1; i++){
		 cleanData += data.charAt(i);
		 }
		 // Initialize the graph with the proper number of vertices
		 setNumVertices(Integer.parseInt(cleanData));
		 // The next line is just a bracket, so we can skip it
		 myReader.nextLine();
		 // The next line is the edges
		 data = myReader.nextLine();
		 String u = "";
		 String v = "";
		 boolean buildingU = true;
		 for (int i = 0; i < data.length(); i++){
		 if (data.charAt(i) == '{'){
		 buildingU = true;
		 u = "";
		 v = "";
		 }
		 else if (data.charAt(i)=='}'){
		 addEdge(Integer.parseInt(u)-1, Integer.parseInt(v)-1);
		 }
		 else if (data.charAt(i)==','){
		 buildingU = false;
		 }
		 else if (buildingU){
		 u += data.charAt(i);
		 }
		 else {
		 v += data.charAt(i);
		 }
		 }
		 // At this point, we have gotten all the information we need from the file
		 myReader.close();
		 } catch (FileNotFoundException e) {
		 System.out.println("An error occurred.");
		 e.printStackTrace();
		 }
		 }
	
	/**
	 * writes graph to a file. Code from Sivan
	 * @param filename name of file
	 */
	public void writeToFile(String filename){
		 try {
			 FileWriter myWriter = new FileWriter(filename);
			 myWriter.write(this.toString());
			 myWriter.close();
			} catch (IOException e) {
			 System.out.println("An error occurred.");
			 e.printStackTrace();
			}
	}
}
