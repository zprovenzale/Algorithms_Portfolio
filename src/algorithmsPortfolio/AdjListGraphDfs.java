package algorithmsPortfolio;

import java.util.*;


//Taken from Professor Streinu's code
public class AdjListGraphDfs extends AdjListGraph {
	private int[] visited; //0: unvisited (white), 1: visited (gray), 2: finished (black)
	private int[] prnt;
	char[][] edgeColors; //n is nothing, t is tree, b is back, f is forward, c is cross
	
	public AdjListGraphDfs() {
		super();
		if (this.debug) {
			System.out.println("new DFS AdjList created. getNumVertices():" + this.getNumVertices());
		}
	}
	
	/**
	 * gets list of visited vertices
	 * @return Array List of visited vertices;
	 */
	public ArrayList<Integer> getVisited() {
		ArrayList<Integer> areVisited = new ArrayList<Integer> ();
		for (int i = 0; i < this.getNumVertices(); i++) {
			if (visited[i] == 2) {
				areVisited.add(i);
			}
		}
		
		return areVisited;
	}
	
	@Override
	public int[] getDfsTree(int v) {
		return this.prnt;
	}
	
	@Override
	public void setAdjList(ArrayList<ArrayList<Integer>> newAdjList) {
		super.setAdjList(newAdjList);
		this.visited = new int[newAdjList.size()];
		prnt = new int[newAdjList.size()];
		for (int i = 0; i < newAdjList.size(); i++) {
			this.visited[i] = 0;
			prnt[i] = -1;
		}
		
		edgeColors = new char[this.getNumVertices()][this.getNumVertices()];
		for (int i = 0; i < this.getNumVertices(); i++) {
			for (int j = 0; j < this.getNumVertices(); j++) {
				edgeColors[i][j] = 'n';
			}
		}
	}
	
	/**
	 * set vertex as visited but not finished
	 * @param v the vertex to be set as visited
	 */
	public void setVisitedVertex(int v) {
		this.visited[v] = 1;
	}
	
	/**
	 * resets the array that keeps track of visited vertices to have no vertices visited
	 */
	public void resetVisited() {
		for (int i = 0; i < this.getNumVertices(); i++) {
			this.visited[i] = 0;
		}
		
		for (int i = 0; i < this.edgeColors.length; i++) {
			for (int j = 0; j < this.edgeColors.length; j++) {
			}
		}
	}
	
	/**
	 * returns whether a particular vertex is visited
	 * @param v vertex to be checked
	 * @return true if the vertex has been visited, false if not
	 */
	public boolean isVisited(int v) {
		if (this.visited[v] == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * check if anc is an ancestor of child
	 * @param anc possible ancestor
	 * @param des possible descendant
	 * @return  true or false, ancestor or not
	 */
	public boolean isAncestor(int anc, int des) {
		if (this.prnt[des] == -1) {
			return false;
		}
		if (this.prnt[des] == anc) {
			return true;
		}
		return isAncestor(anc, this.prnt[des]);
	}
	
	/**
	 * double check this is right
	 * @param v
	 * @return string version of tree
	 */
	public String treeToString(int v) {
		this.dfs(v);
		String string = "tree " + v + ": ";
		for (int i = 0; i < this.visited.length; i++) {
			string += " v" + i + ": " + visited[i];
		}
		return string;
		
	}
	
	/**
	 * runs dfs on the adjList
	 * @param v root vertex of the dfs
	 */
	public void dfs(int v) {

		if (isVisited(v)) {
			//System.out.println("vertex " + v + " was already visited");
			return;
		}
		
		setVisitedVertex(v);
		
		ArrayList<ArrayList<Integer>> adjList = this.getAdjList();
		
		for (int i = 0; i < adjList.get(v).size(); i++) {
			if (edgeColors[v][adjList.get(v).get(i)] != -1) {
				// System.out.println("WARNING: Edge already traversed");
			} else if (this.visited[i] == 0) {
				prnt[adjList.get(v).get(i)] = v; 
				edgeColors[v][adjList.get(v).get(i)] = 't';
			} else if (isAncestor(adjList.get(v).get(i), v)) {
				edgeColors[v][adjList.get(v).get(i)] = 'b';
			} else {
				edgeColors[v][adjList.get(v).get(i)] = 'c';
			}
					
			dfs(adjList.get(v).get(i));
		}
		
		this.visited[v] = 2;
		
	}
}
