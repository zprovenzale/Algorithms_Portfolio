package algorithmsPortfolio;
//-----------------------------------------------------
// Author: 		Zampa Provenzale
// Date: 		April 28, 2021
// Description:	Java code to represent an edge in a graph. The edge can be weighted or unweighted.
//-----------------------------------------------------
public class Edge {
	private int[] edge;
	private int weight;
	
	//Constructors

	/**
	 * creates an Edge object to represent an edge between two vertices
	 */
	public Edge() {
		edge = new int[2];
	}
	
	/**
	 * creates an Edge object to represent an edge between the two vertices
	 * 
	 * @param v1 first vertex
	 * @param v2 second vertex
	 */
	public Edge(int v1, int v2) {
		edge = new int[2];
		this.setEdge(v1, v2);
	}
	
	/**
	 * creates an Edge object to represent a weighted edge between the two vertices
	 * 
	 * @param v1 vertex 1
	 * @param v2 vertex 2
	 * @param newWeight of edge
	 */
	public Edge(int v1, int v2, int newWeight) {
		edge = new int[2];
		this.setEdge(v1, v2);
		this.setWeight(newWeight);
	}
	

	/**
	 * @param i (get vertex 0 or vertex 1)
	 * @return that name of that vertex
	 */
	public int get(int i) {
		return edge[i];
	}
	
	/**
	 * @return weight of the edge
	 */
	public int getWeight() {
		return weight;
	}
	
	//Setters

	/**
	 * sets this edge's vertices
	 * 
	 * @param v1 first vertex
	 * @param v2 second vertex
	 */
	public void setEdge(int v1, int v2) {
		this.edge[0] = v1;
		this.edge[1] = v2;
	}
	
	/**
	 * sets this edge's weight to the given weight
	 * 
	 * @param newWeight
	 */
	public void setWeight(int newWeight) {

		this.weight = newWeight;
	}
}
