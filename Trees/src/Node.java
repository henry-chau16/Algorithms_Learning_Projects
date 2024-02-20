public class Node { // Basic Tree Node class with single integer key

	int key;
	Node parent, left, right;
	
	protected Node(int key) {
		this.key=key;
		parent=null;
		left=null;
		right=null;
	}
	
	// MUTATORS
	protected void setParent(Node parent) {
		this.parent=parent;
	}
	
	protected void setLeft(Node left) {
		this.left=left;
	}
	
	protected void setRight(Node right) {
		this.right=right;
	}
	
	protected void setKey(int key) {
		this.key=key;
	}
	
	// ACCESSORS
	protected Node getParent() {
		return parent;
	}
	
	protected Node getLeft() {
		return left;
	}
	
	protected Node getRight() {
		return right;
	}
	
	protected int getKey() {
		return key;
	}
	
	//PRINT KEY
	
	protected void print() {
		System.out.println(key);
	}
	
}
