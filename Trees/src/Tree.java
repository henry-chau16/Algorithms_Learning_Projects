import java.util.*;

public class Tree {
	
	private Node root;
	//Constructor
	public Tree() {
		root=null;
	}
	//Root Node Accessor
	public Node getRoot() {
		return root;
	}
	//BST insertion algorithm used for building BST (Only used to test and facilitate floor method
	public void addBST(Node a) {
		if(root==null) {
			root=a;
		}
		else {
			Node b=root;
			while(a.getParent()==null) {
				if(a.getKey()>=b.getKey()) { //always set duplicate keys as right child
					if(b.getRight()==null) { //so floor function works with a key value that
						b.setRight(a);		 //exists in current BST
						a.setParent(b);
					}
					else {
						b=b.getRight();
					}
				}
				else {	//if insert key is less than current key
					if(b.getLeft()==null) { 
						b.setLeft(a);
						a.setParent(b);
					}
					else {
						b=b.getLeft();
					}
				}
			}
		}
	}
	
	//Overload to take integer parameter 
	public void addBST(int a) {
		Node insert=new Node(a);
		addBST(insert);
	}
	
	//Standard level order (top-down, left-right) insertion algorithm for standard Binary trees
	public void add(Node a) {
		Queue<Node> Q = new LinkedList<>(); //similar to bfs, uses queue to insert Nodes
		Node b;								//by depth, left to right
		
		if(root==null) {
			root=a;
		}
		else {
			Q.add(root); //starts with root if root not null
		}
		
		while(!Q.isEmpty() && a.getParent()==null) { // queue keeps track of Node traversal
			b=Q.remove();
			
			if(b.getLeft()!=null) {	//if left node exists add it to queue
				Q.add(b.getLeft());
			}
			else {
				b.setLeft(a);		//else insert here as left child
				a.setParent(b);
			}
			
			if(b.getRight()!=null && a.getParent()==null) {	//if right node exists and insertion has yet to complete
				Q.add(b.getRight());						//add right node to queue
			}
			else if(a.getParent()==null) {					//else if there is space to the right, insert here as right
				b.setRight(a);								//child	
				a.setParent(b);
			}
		}
	}
	
	//Overload to take integer parameter
	public void add(int a) {
		Node insert=new Node(a);
		add(insert);
	}
	
	//Helper recursive method for balance method. Uses Depth-first traversal to recursively 
	//sum heights of right and left subtrees and checks their difference
	private int subtreeHeight(Node a) {
		
		int leftHeight; //keeps track of max depth of left subtree with respect to current node
		int rightHeight;//keeps track of max depth of right subtree with respect to current node
		
		if (a==null) { //DFS reaches past leaf node
			return 0;
		}
		
		leftHeight=subtreeHeight(a.getLeft());//recursive dfs left subtree
		if(leftHeight<0) {	//returns -1 if difference>1 is found at any given point in left subtree
			return -1;
		}
		rightHeight=subtreeHeight(a.getRight());//recursive dfs right subtree
		if(rightHeight<0) { //returns -1 if difference>1 is found at any given point in right subtree
			return -1;
		}
		if(Math.abs(leftHeight-rightHeight)>1) { //returns -1 if subtree depth difference > 1
			return -1;
		}
		else {
			if(leftHeight>rightHeight) { //returns greatest depth of either subtree and increments the max depth by 1
				return leftHeight+1;
			}
			else {
				return rightHeight+1;
			}
		}
	}
	
	//Method calls subtreeHeight to check for any depth difference > 1, returns false if there is
	public boolean balance() {
		if(subtreeHeight(root)>=0) { //calls recursive helper method for subtree depth comparison
			return true;
		}
		else {
			return false;
		}
	}
	
	//Helper method for floor method. Finds predecessor for given Node a. ASSUMES TREE IS BST
	private Node predecessor(Node a) {
		if(a.getLeft()!=null) {		//checks for existing left subtree and finds max key in subtree
			return max(a.getLeft()); 
		}
		Node b=a.getParent();
		while(b!=null && a==b.getLeft()) {	//iteratively traverses upward in tree to find predecessor if
			Node c=b.getParent();			//left subtree does not exist
			a=b;
			b=c;
		}
		return b;
	}
	
	//Helper method for predecessor method. finds max key value Node with subtree with root Node a.
	//ASSUMES TREE IS BST
	private Node max(Node a) {
		Node b = a;
		while(b.getRight()!=null){ //finds rightmost node in subtree
			b=b.getRight();
		}
		return b;
	}
	
	//floor method returns rightmost Node equal or less than given integer. WORKS ONLY ON BST
	public Node floor(int x) {
		
		Node y=new Node(x);		//creates new node from given integer
		addBST(y);				//adds temp node y to BST (works ONLY with BST)
		Node z=predecessor(y);	//saves predecessor of temp node into z (works even if key already exists)
		Node p=y.getParent();	//saves parent y was added to
		if(p.getRight()==y) {	//removes y from BST (works because insertion in BST is always
			p.setRight(null);	//at the leaf level
		}
		else {
			p.setLeft(null);
		}
		return z;				//returns saved predecessor Node
	}
	
	//Helper recursive method for keysAtDepth method. Uses Depth first traversal to recursively
	//traverse Nodes in order up to given depth d.
	private void depthTraversal(Node a, int d, int h, List<Integer> list) {
		
		if(a!=null && h<=d) {//int d = depth to print, int h keeps track of current depth in traversal
			depthTraversal(a.getLeft(), d, h+1, list);
			if(h==d) {
				list.add(a.getKey());//adds to List if h = d (traversal at correct depth)
			}
			depthTraversal(a.getRight(), d, h+1, list);
		}
	}
	
	//Returns a linked list of nodes in left to right order at given depth d.
	public List<Integer> keysAtDepth(int d) {
		
		LinkedList<Integer> list = new LinkedList<>();
		depthTraversal(root, d, 1, list);
		
		return list;
	}
	
	//Helper recursive method for buildFromArray method. Uses a queue and Depth first traversal to
	//insert Array values as keys matching in-order traversal of tree.
	private void traverseAdd(Queue<Integer> Q, Node b) {
		if(b!=null) {
			traverseAdd(Q ,b.getLeft());
			b.setKey(Q.remove());//queue maintains array order insertion into tree
			traverseAdd(Q, b.getRight());
		}
	}
	
	//Creates queue from given array to create a tree with in-order traversal matching Array order.
	public static Tree buildFromArray(int[] A) {
		Tree tree=new Tree();
		Queue<Integer> Q=new LinkedList<>();
		for(int  i=0; i<A.length; i++) {//build a queue out of Array A and a Binary tree
			tree.add(0);				//with same number of Nodes with dummy key values as
			Q.add(A[i]);				//elements in array A.
		}
		tree.traverseAdd(Q, tree.getRoot());
		
		return tree;
	}

	//prints tree elements (node key values) by depth using keysAtDepth method at each depth
	//in the tree (tests both keysAtDepth method and tree correctness)
	public void print() {
		for(int i=1; i<=maxDepth(root); i++) {
			System.out.println(keysAtDepth(i).toString());
		}
	}
	//Helper method for print method. Returns max depth of tree
	private int maxDepth(Node a) {
		int left;
		int right;
		
		if(a==null) {
			return 0;
		}
		left=maxDepth(a.getLeft());
		right=maxDepth(a.getRight());
		
		if(left>right) {
			return left+1;
		}
		else {
			return right+1;
		}
		
		
	}
}
	
	
	
	