package algorithmsPortfolio;

import java.util.*;

public class AdjMatrixGraph extends Graph {
	private ArrayList<ArrayList<Integer>> adjMatrix;
	private int numVertices;
	private boolean directed;
	private boolean debug;
	
	/**
	 * constructor
	 */
	public AdjMatrixGraph() {
		super();
		this.adjMatrix = new ArrayList<ArrayList<Integer>> ();
	}
	
	/**
	 * returns undirected version of the adjacency matrix graph
	 * @return undirected adjacency matrix graph
	 */
	public AdjMatrixGraph getUndirAdjMatrixGraph() {
		AdjMatrixGraph undirAdjMatrixGraph = new AdjMatrixGraph();
		undirAdjMatrixGraph.setAdjMatrix(adjMatrix);
		undirAdjMatrixGraph.setDirected(false);
		return undirAdjMatrixGraph;
	}
	
	/**
	 * returns the adjacency matrix as a 2d ArrayList of Integers
	 * @return adjMatrix
	 */
	public ArrayList<ArrayList<Integer>> getAdjMatrix() {
		return this.adjMatrix;
	}
	
	public void setDirected(boolean directed) {
		super.setDirected(directed);
		
		// if you are changing the graph to undirected and it already
		//has vertices, adds a new edge for each pre-existing edge if
		//it does not already exist
		if (this.directed == false && !this.isEmpty()) {
			for (int i = 0; i < numVertices; i++) {
				for (int j = 0; j < numVertices; j++) {
					if (hasEdge(i, j) && !hasEdge(j, i)) {
						addEdge(j, i);
					}
				}
			}
		}	
	}
	
	/**
	 * sets the value of adjMatrix (a 2d ArrayList of Integers)
	 * @param newAdjMatrix
	 */
	public void setAdjMatrix(ArrayList<ArrayList<Integer>> newAdjMatrix) {
		if (!this.isEmpty()) {
			System.out.println("Warning: previous adjacency matrix overwritten by setAdjMatrix");
		}

		this.adjMatrix = newAdjMatrix;
		this.numVertices = this.adjMatrix.size();
	}
	
	@Override
	public void addVertices(int numNewVertices) {
		super.addVertices(numNewVertices);
		for (int n = 0; n < numNewVertices; n++) {
			this.adjMatrix.add(new ArrayList<Integer> ());
			for (int i = 0; i < this.numVertices; i++) {
				this.adjMatrix.get(numVertices).add(0);
			}
			
			for (int i = 0; i <= this.numVertices; i++) {
				this.adjMatrix.get(i).add(0);
			}
		}
		
		if (debug) {
			System.out.println(numNewVertices + " vertices added. numVertices: " + this.numVertices + 
					" adjMatrix.size(): " + adjMatrix.size());
		}
	}
	
	@Override
	public void addEdge(int u, int v) {
		super.addEdge(u, v);
		if (debug) {
			System.out.println("addEdge called, u: " + u + " v: " + v);
		}
		this.adjMatrix.get(u).set(v, 1);
		if (!directed) {
			this.adjMatrix.get(v).set(u, 1);
		}
	}	

	/**
	 * removes vertex v
	 * @param v vertex
	 */
	public void removeVertex(int v) {
		//invalid input
		if (v >= numVertices || v < 0) {
			return;
		}
		
		this.adjMatrix.remove(v);
		
		for (int i = 0; i < this.numVertices - 1; i++) {
			this.adjMatrix.get(i).remove(v);
		}
		
		this.numVertices -= 1;
	}
	
	/**
	 * remove edge
	 * @param u first vertex
	 * @param v second vertex
	 */
	public void removeEdge(int u, int v) {
		//invalid input
		if (u >= numVertices || u < 0 || v > numVertices || v < 0) {
			System.out.println("Warning: invalid input for removeEdge");
			return;
		}
		
		this.adjMatrix.get(u).set(v, 0);
		this.adjMatrix.get(v).set(u, 0);
	}
	
	@Override
	public boolean hasEdge(int u, int v) {
		for (int i = 0; i < adjMatrix.size(); i++) {
			if (adjMatrix.get(u).get(i) == v) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isEmpty() {

		for (var i = 0; i < adjMatrix.size(); i++) {
			for (var j = 0; j < adjMatrix.size(); i++) {
				if (adjMatrix.get(i).get(j) == 1) {
					 return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * converts adjacency matrix to a string
	 * @return string
	 */
	public String toString() {
		String string = new String();
		string += ' ';
		string += ' ';
		for (int i = 0; i < this.numVertices; i++) {
			string += i;
			string += ' ';
		}
		if (debug) {
			System.out.println("serializing started. string:" + string + "numVertices: " + numVertices + 
					" adjMatrix.size(): " + adjMatrix.size());
		}
		for (int i = 0; i < this.numVertices; i++) {
			string += '\n';
			string += i;
			string += ' ';
			for (int j = 0; j < numVertices; j++) {
				string += this.adjMatrix.get(i).get(j);
				string += ' ';
			}
			
		}
		
		return string;
	}
	
	/**
	 * converts to graph
	 * @return Graph
	 */
	public BasicGraph toGraph() {
		BasicGraph basicGraph = new BasicGraph();
		basicGraph.addVertices(this.adjMatrix.size());
		
		for (var i = 0; i < this.adjMatrix.size(); i++) {
			for (var j = 0; j < this.adjMatrix.size(); j++) {
				if (adjMatrix.get(i).get(j) == 1) {
					basicGraph.addEdge(i, j);
				}
			}
		}
		
		return basicGraph;
	}
	
	/**
	 * converts to adjacency list
	 * @return adjListGraph
	 */
	public AdjListGraph toAdjList() {
		AdjListGraph adjList = new AdjListGraph();
		adjList.addVertices(this.adjMatrix.size());
		
		for (var i = 0; i < this.adjMatrix.size(); i++) {
			for (var j = 0; j < this.adjMatrix.size(); j++) {
				if (adjMatrix.get(i).get(j) == 1) {
					adjList.addEdge(i, j);
				}
			}
		}
		
		return adjList;
	}
}
