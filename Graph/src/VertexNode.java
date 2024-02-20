//Class functions as element node that references a vertex, collected by VertexList
public class VertexNode {
	
	Vertex vert; //points to vertex
	VertexNode next;
	
	public VertexNode(int i) {
		vert=new Vertex(i);
		next=null;
	}
	
	public VertexNode(Vertex vert) {
		this.vert=vert;
		next=null;
	}
	
	//ACCESSORS
	protected Vertex getVertex() {
		return vert;
	}
	protected VertexNode getNext() {
		return next;
	}
	
	//MUTATOR
	protected void setNext(VertexNode next) {
		this.next=next;
	}
	


	
}
