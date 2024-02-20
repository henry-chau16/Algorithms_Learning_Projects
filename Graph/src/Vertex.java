import java.util.ArrayList;
import java.util.List;

//Vertex class -- functionally a node class
public class Vertex {

	int id; //node key
	char color;//flag for BFS and DFS traversals
	Vertex pred;//pointer for tree predecessor for DFS traversal
	List<Vertex> adj;// adjacency list
	
	int inDegree;//keeps track of incoming edge count for topSort
	
	public Vertex(int id) {
		this.id=id;
		adj=new ArrayList<>();
		pred = null;
		color = 'w';
		inDegree=0;
		
	}
	
	//MUTATORS
	
	protected void addAdj(Vertex i) {
		adj.add(i);
	}
	protected void removeAdj(Vertex i) {
		adj.remove(i);
	}
	protected void setPred(Vertex pred) {
		this.pred=pred;
	}
	protected void setColor(char c) {
		if(c=='w' || c=='W' || c=='g' || c=='G' || c=='b' || c=='B') {
			color=c;
		}
		else {
			System.out.println("Invalid Color choice");
		}
	}
	protected void incrDegree() {//increment inDegree by 1
		inDegree++;
	}
	
	//ACCESSORS 
	
	protected int getID() {
		return id;
	}
	protected List<Vertex> getAdj(){
		return adj;
	}
	protected char getColor() {
		return color;
	}
	protected Vertex getPred() {
		return pred;
	}
	protected int getInDegree() {
		return inDegree;
	}
}
