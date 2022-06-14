package algorithmsPortfolio;

import java.util.ArrayList;

public class Vertex {
	private int name;
	private Vertex parent;
	private int level;
	private int [] children;
	private ArrayList<Integer> sumWeight;
	
	//Constructors
	
	public Vertex(int name) {
		this.name = name;
	}
	
	//Getters
	/**
	 * get vertex name
	 * @return name
	 */
	public int getName() {
		return this.name;
	}
	
	/**
	 * get vertex parent name
	 * @return parent
	 */
	public Vertex getParent() {
		return this.parent;
	}
	
	/**
	 * get vertex level
	 * @return level
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * get vertex's children names
	 * @return array of children
	 */
	public int[] getChildren() {
		return children;
	}
	
	/**
	 * get edge weight
	 * @param source vertex
	 * @return weight
	 */
	public int getWeight(int source) {
		if (source >= this.sumWeight.size()); {
			this.setWeight(source, Integer.MAX_VALUE);
		}
		return this.sumWeight.get(source);
	}
	
	//Setters
	
	/**
	 * set vertex name
	 * @param newName name
	 */
	public void setName(int newName) {
		this.name = newName;
	}
	
	/**
	 * set vertex parent
	 * @param newParent parent
	 */
	public void setParent(Vertex newParent) {
		this.parent = newParent;
	}
	
	/**
	 * set vertex level
	 * @param newLevel level
	 */
	public void setLevel(int newLevel) {
		this.level = newLevel;
	}
	
	/**
	 * set vertex's children 
	 * @param newChildren children
	 */
	public void setChildren (int[] newChildren) {
		this.children = new int[newChildren.length];
		this.children = newChildren;
	}
	
	/**
	 * set edge weight
	 * @param source vertex
	 * @param newWeight
	 */
	public void setWeight(int source, int newWeight) {
		if (this.sumWeight.size() < source) {
			this.sumWeight.add(Integer.MAX_VALUE);
			this.setWeight(source, newWeight);
		}
		this.sumWeight.set(source, newWeight);
	}
}
