package algorithmsPortfolio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BasicGraph extends Graph {
	boolean debug;
	int numVertices;
	ArrayList<Edge> edges;
	boolean directed;
	
	/**
	 * constructor
	 */
	public BasicGraph() {
		super();
		this.debug = false;
		this.directed = true;
		this.numVertices = 0;
		edges = new ArrayList<Edge> ();
		if (debug) {
			System.out.println("new BasicGraph initialized");
		}
	}
	
	/**
	 * returns undirected version of graph
	 * @return undirected graph
	 */
	public BasicGraph getUndirGraph() {
		BasicGraph undirGraph = new BasicGraph();
		undirGraph.setEdges(this.edges);
		undirGraph.setDirected(false);
		return undirGraph;
	}
	
	/**
	 * gets list of edges
	 * @return list of edges in the graph
	 */
	public ArrayList<Edge> getEdges() {
		return this.edges;
	}
	
	@Override
	public void setDirected(boolean nowDirected) {
		super.setDirected(nowDirected);
		
		// if you are changing the graph to undirected and it already
		//has vertices, adds a new edge for each pre-existing edge if
		//it does not already exist
		int numEdges = edges.size();
		if (this.directed == false && !this.isEmpty()) {
			for (int i = 0; i < numEdges; i++) {
				if (debug) {
					System.out.println("edge " + i + " being checked" + "edges.size(): " + edges.size());
				}
				if (!hasEdge(edges.get(i).get(1), edges.get(i).get(0))) {
					addEdge(edges.get(i).get(1), edges.get(i).get(0));
				}
			}
		}
		
		if (debug) {
			System.out.println("method setDirected() completed");
		}
	}
	
	@Override
	public void setNumVertices(int newNumVertices) {
		super.setNumVertices(newNumVertices);
		this.numVertices = newNumVertices;
	}
	
	/**
	 * set edges
	 * @param newEdges edges
	 */
	public void setEdges(ArrayList<Edge> newEdges) {
		this.edges = newEdges;
	}
	
	@Override
	public void addEdge(int u, int v) {
		if (debug) {
			System.out.println("BasicGraph addEdge called");
			System.out.println("this.numVertices:" + this.numVertices);
		}
		super.addEdge(u, v);
		
		Edge edge = new Edge();
		edge.setEdge(u, v);
		if (debug) {
			System.out.println("edge (" + u +", " + v + ") added"
					+ "\nhasEdge(" + v + ", " + u + "): " + hasEdge(v, u));
		}
		this.edges.add(edge);
		if (!this.directed && !hasEdge(v, u)) {
			if (debug) {
				System.out.println("Graph is undirected and reverse edge does not exist");
			}
			Edge edgeUD = new Edge();
			edgeUD.setEdge(v, u);
			this.edges.add(edgeUD);
			if (debug) {
				System.out.println("reverse edge (" + v +", " + u + ") added");
			}
		}
	}
	
	/**
	 * remove vertex STUB
	 * @param v vertex to be removed
	 */
	public void removeVertex(int v) {
		//for (int i = 0; i )
	}
	
	@Override
	public boolean hasEdge(int u, int v) {
		if (debug) {
			System.out.println("hadEdge() called");
			System.out.println("this.numVertices:" + this.numVertices);
		}
		for (int i = 0; i < this.edges.size(); i++) {
			if ( this.edges.get(i).get(0) == u && this.edges.get(i).get(1) == v) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		if (this.edges.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		if (debug) {
			System.out.println("BasicGraph toString() called");
			System.out.println("this.numVertices: " + this.numVertices);
		}
		String string = "Vertices: " + this.numVertices;
		string += "\nEdges: ";
		for (int i = 0; i < edges.size(); i++) {
			string = string + "(" + edges.get(i).get(0) + ", " + edges.get(i).get(1) + ") ";
		}
		return string;
	}
	
	/**
	 * converts graph to an adjacency matrix
	 * @return adjacency matrix version of the graph
	 */
	public AdjMatrixGraph toAdjMatrix() {

		AdjMatrixGraph adjMatrix = new AdjMatrixGraph();
		adjMatrix.addVertices(this.numVertices);
		for (var i = 0; i < this.edges.size(); i++) {
			adjMatrix.addEdge(this.edges.get(i).get(0), this.edges.get(i).get(1));
		}
		
		return adjMatrix;
	}
	
	/**
	 * converts the graph to an adjacency list graph
	 * @return adjacency list version of the graph
	 */
	public AdjListGraph toAdjList() {
		if (debug) {
			System.out.println("BasicGraph toAdjList() called");
			System.out.println("this.numVertices: " + this.numVertices);
		}
		AdjListGraph adjList = new AdjListGraph();
		adjList.addVertices(this.numVertices);
		
		for (var i = 0; i < this.edges.size(); i++) {
			adjList.addEdge(this.edges.get(i).get(0), this.edges.get(i).get(1));
		}
		
		return adjList;
	}
	
	//Taken from Illeana's and Sivan's code

	/**
	 * for parsing graph from not-perfectly-formatted text file
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
	 * parse number of vertices
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
	 * @param filename
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
}
