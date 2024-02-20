import java.util.List;

//Very basic Linked List of VertexNode objects.
public class VertexList {
	
	private VertexNode head;
	
	public VertexList() {
		
		head=null;
	}
	
	protected VertexNode getHead() {
		return head;
	}
	
	public void addNode(VertexNode node){
		
		if(head==null) {
			head=node;
		}
		else {
			VertexNode temp=head;
		
			while(temp.getNext()!=null) {
				temp=temp.getNext();
			}
		
			temp.setNext(node);
		}
		
	}
	//finds and returns VertexNode with vertex with ID specified in param. returns
	//null if not found
	public VertexNode find(int id) {
		
		if(head==null) {
			return head;
		}
		if(head.getVertex().getID()==id) {
			return head;
		}
		
		VertexNode temp=head;
		
		while(temp.getNext()!=null) {//iterate through List to find match
			temp=temp.getNext();
			int check=temp.getVertex().getID();
			if(check==id) {
				return temp;
			}
		}
		return null;
	}
	//calls find to search for match, adds new VertexNode if no match found
	public Vertex findOrMake(int id) {
		
		VertexNode result=find(id);
		
		if(result!=null) {//match found, returns matched Vertex
			return result.getVertex();
		}
		else {//Vertex not found, create new vertex and add to list
			Vertex insert=new Vertex(id);
			VertexNode node=new VertexNode(insert);
			addNode(node);
			return insert;
		}
	}
	//creates a string listing adjacency lists of vertices with neighbors
	public String writeString() {
		String string="";
		VertexNode temp=head;
		
		while(temp!=null) {
			List<Vertex> list=temp.getVertex().getAdj();
			
			if(!(temp.getVertex().getAdj().isEmpty())){//split Vertex keys by ';'
				if(temp!=head) {
					string+=";";
				}
				string+= String.valueOf(temp.getVertex().getID())+":";
			
				for(int i=0; i<list.size();i++) {
					if(i!=0) {
						string+=","; //split edge keys by ','
					}	
					string+=String.valueOf(list.get(i).getID());
				}
			}
			temp=temp.getNext();
		}
		return string;
	}
	
	//resets color flag and pred pointer in vertices for BFS and DFS traversal methods
	protected void resetColor() {
		VertexNode temp = head;
		
		while(temp!=null) {
			if(temp.getVertex().getColor()!='w') {
				temp.getVertex().setColor('w');
			}
			if(temp.getVertex().getPred()!=null) {
				temp.getVertex().setPred(null);
			}
			temp=temp.getNext();
		}
	}
}