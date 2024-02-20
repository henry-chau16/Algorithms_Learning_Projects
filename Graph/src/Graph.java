import java.util.*;
import java.util.Map.Entry;

//Graph class with BFS DFS and topological sort methods
public class Graph {

	VertexList vertices;
	
	public Graph() {
		vertices=new VertexList();
	}
	
	//reads a string listing edges and returns a graph represented by the string
	public static Graph readFromString(String input) {
		
		Graph graph = new Graph();
		String[] edges = input.split(";");
		for (int i=0; i<edges.length; i++) {
			String[] parts=edges[i].split(",");
			try {
				int a=Integer.valueOf(parts[0]);
				int b=Integer.valueOf(parts[1]);
				graph.addEdge(a, b);
			}
			catch(NumberFormatException e) {
				System.out.println("invalid string format: "+input);
				return null;
			}
		}
		
		return graph;
	}
	//adds an edge between two vertices
	//if either vertex does not exist, first create and add vertex/vertices before 
	//creating edge
	public void addEdge(int i, int j) {
		
		Vertex a=vertices.findOrMake(i);
		Vertex b=vertices.findOrMake(j);
		
		a.addAdj(b);
		b.incrDegree();//increment indegree of second vertex 
		
	}
	
	//Calls VertexList method writeString to create a string of adjacency lists.
	public String writeToString() {
		String string=vertices.writeString();
		return string;
	}
	//convenience method, calls findOrMake on vertex list
	public Vertex find(int id) {
		
		return vertices.findOrMake(id);
	}
	//BFS using a queue, returns a tree graph of the BFS traversal
	public Graph BFS(Vertex s) {
		Graph g=new Graph();
		Queue<Vertex> q=new LinkedList<>();
		vertices.resetColor();
		
		if(vertices.find(s.getID())==null) {
			System.out.println("Vertex not found in existing graph; returning empty tree");
		}
		else {
			s.setColor('b');
			q.add(s);
			
			while(!q.isEmpty()) {//keep adding adjacent nodes to queue
				Vertex v=q.remove();
				for(int i=0;i<v.getAdj().size(); i++) {
					if(v.getAdj().get(i).getColor()=='w') {
						v.getAdj().get(i).setColor('b');
						q.add(v.getAdj().get(i));
						g.addEdge(v.getID(), v.getAdj().get(i).getID());//trace traversal on graph g
					}
				}
				
			}	
		}
		return g;
	}
	//iterative DFS with a stack. Returns a tree/forest (if disjoint) graph of the DFS
	//traversal
	public Graph itDFS() {
		Graph g=new Graph();
		vertices.resetColor();
		Stack<Vertex> s=new Stack<>();
		VertexNode temp=vertices.getHead();
		
		while(temp!=null) {
			if(temp.getVertex().getColor()=='w') {
				s.add(temp.getVertex());
				temp.getVertex().setColor('g');
			}
			while(!s.isEmpty()) {//add adjacent nodes to stack
				Vertex v=s.pop();
				if(v.getPred()!=null) {
					g.addEdge(v.getPred().getID(),v.getID());//trace traversal to graph g
				}
				for(int i=0; i<v.getAdj().size(); i++) {
					if(v.getAdj().get(i).getColor()=='w') {
						v.getAdj().get(i).setColor('g');
						s.add(v.getAdj().get(i));
						v.getAdj().get(i).setPred(v);
					}
				}
			}
			temp=temp.getNext();
		}
		return g;
	}
	
	//Recursive DFS method calling recursive helper method visitDFS (traversal order should
	//differ from iterative DFS)
	public Graph recDFS() {
		Graph g=new Graph();
		vertices.resetColor();
		VertexNode temp=vertices.getHead();
		
		while(temp!=null) {
			if(temp.getVertex().getColor()=='w') {
				temp.getVertex().setColor('g');
				visitDFS(g,temp.getVertex());
			}
			temp=temp.getNext();
		}
		
		return g;
	}
	
	//recursive helper method to facilitate recursive DFS
	private void visitDFS(Graph g, Vertex v) {
		
		if(v.getPred()!=null) {
			g.addEdge(v.getPred().getID(),v.getID());//traces traversal to graph g
		}
		for(int i=0; i<v.getAdj().size(); i++) {
			if(v.getAdj().get(i).getColor()=='w') {
				v.getAdj().get(i).setColor('g');
				v.getAdj().get(i).setPred(v);
				visitDFS(g,v.getAdj().get(i));
			}
		
		}
	}
	
	//topological sort using Khan's algorithm
	//returns a list of the vertex keys in topological order if graph is acyclic
	//returns null if there are cycles in graph
	public List<Integer> topSort(){
		
		List<Integer> ordering=new ArrayList<>();
		Queue<Vertex> q=new LinkedList<>();
		HashMap<Vertex, Integer> map=new HashMap<>();
		VertexNode temp=vertices.getHead();
		
		while(temp!=null) {//creating map of vertices and their indegree
			map.put(temp.getVertex(), temp.getVertex().getInDegree());
			temp=temp.getNext();
		}
		
		for (Entry<Vertex, Integer> e : map.entrySet())//add vertices with indegree  0
            if(e.getValue()==0) {					   //to queue
            	q.add(e.getKey());
            }
		
		while(!q.isEmpty()) {//remove from queue, then decrement indegree of adj nodes by 1
							 //add any nodes with indegree 0
			Vertex v = q.remove();
			ordering.add(v.getID());
			
			for(int i=0; i<v.getAdj().size(); i++) {
				
				Vertex a=v.getAdj().get(i);
				int dec=map.get(a)-1;
				map.put(v.getAdj().get(i), dec);
				if(dec==0) {
					q.add(v.getAdj().get(i));
				}
				
			}
		}
		for (Entry<Vertex, Integer> e : map.entrySet()) {//check to for any unprocessed vertices
			if(e.getValue()>0) {
				return null;//return null after detecting cycle
			}
		}
			
		return ordering;
		
	}
}
