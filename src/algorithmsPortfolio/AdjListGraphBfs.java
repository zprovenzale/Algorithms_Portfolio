package algorithmsPortfolio;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AdjListGraphBfs extends AdjListGraph {
	Vertex root;
	LinkedList<Vertex> queue;
	
	public void bfs(Vertex root) {
		root.setLevel(0);
		queue = new LinkedList<Vertex> ();
		queue.add(root);
		while (queue.size() > 0) {
			Vertex v = queue.poll();
			for (int i = 0; i < this.getChildren(v).size(); i++) {
				int vName = this.getAdjList().get(v.getName()).get(i);
				this.vertices.get(vName).setParent(v);
				this.vertices.get(vName).setLevel(v.getLevel() + 1);
				queue.add(vertices.get(vName));
			}
		}
	}
	
	public void shortestPath(Vertex root) {
		queue = new LinkedList<Vertex> ();
		int weight = 0;
		queue.add(root);
		while (queue.size() > 0) {
			Vertex v = queue.poll();
			for (int i = 0; i < this.getChildren(v).size(); i++) {
				int uName = this.getAdjList().get(v.getName()).get(i);
				//set parent
				this.vertices.get(uName).setParent(v);
				//calculate weight
				weight = v.getWeight(root.getName()) + this.edges.get(v.getName()).get(i).getWeight();
				if (weight < this.vertices.get(uName).getWeight(root.getName())) {
					this.vertices.get(uName).setWeight(root.getName(), weight);
				}
				queue.add(vertices.get(uName));
			}
		}
	}
	
	public String bfsToString() {
		if  (debug) {
			System.out.println("bfsToString() called");
		}
		 String string = "";
		 string += "{";
		 this.bfs(this.vertices.get(0));
		 for (int level = 1; level < this.getNumVertices(); level++) {
			 string += "{";
			 for (int i = 0; i < this.getNumVertices(); i++) {
				 if (this.vertices.get(0).getLevel() == 1) {
					 string += i + ","; 
				 }
			 }
			 string += "}";
		 }
		 string += "}";
		 System.out.println("bfsToString() returning");
		 return string;
	}
	
	public void writeBfsToMathematicaFile(String filename){
		 try {
		 FileWriter myWriter = new FileWriter(filename);
		 myWriter.write(this.bfsToString());
		 myWriter.close();
		 } catch (IOException e) {
		 System.out.println("An error occurred.");
		 e.printStackTrace();
		 }
	 }
}
