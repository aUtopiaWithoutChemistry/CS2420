package lab09;

/**
 * Represents a generically-typed binary tree node. Each binary node contains data, a 
 * reference to the left child, and a reference to the right child.
 * 
 * @author CS 2420 course staff
 * @version March 7, 2025
 */
public class BinaryNode<Type> {

	private Type data;
	private BinaryNode<Type> leftChild;
	private BinaryNode<Type> rightChild;

	/**
	 * Creates a binary node with the given data and child references.
	 * 
	 * @param data - data to be contained in this node
	 * @param leftChild - reference to this node's left child
	 * @param rightChild - reference to this node's right child
	 */
	public BinaryNode(Type data, BinaryNode<Type> leftChild, BinaryNode<Type> rightChild) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	/**
	 * Creates a binary node with the given data, and both child references set to null.
	 * 
	 * @param data - data to be contained in this node
	 */
	public BinaryNode(Type data) {
		this(data, null, null);
	}

	/**
	 * Gets this binary node's data.
	 * 
	 * @return this node's data
	 */
	public Type getData() {
		return this.data;
	}

	/**
	 * (Re)sets this binary node's data.
	 * 
	 * @param data - data to be contained in this node 
	 */
	public void setData(Type data) {
		this.data = data;
	}

	/**
	 * Gets the reference to this binary node's left child.
	 * 
	 * @return reference to this node's left child
	 */
	public BinaryNode<Type> getLeftChild() {
		return this.leftChild;
	}
	
	/**
	 * Gets the reference to this binary node's right child.
	 * 
	 * @return reference to this node's right child
	 */
	public BinaryNode<Type> getRightChild() {
		return this.rightChild;
	}

	/**
	 * Sets this binary node's left child reference.
	 * 
	 * @param leftChild - reference for setting this node's left child
	 */
	public void setLeftChild(BinaryNode<Type> leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * Sets this binary node's right child reference.
	 * 
	 * @param rightChild - reference for setting this node's right child
	 */
	public void setRightChild(BinaryNode<Type> rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * Gets the reference to the leftmost node in the binary tree rooted at this binary node.
	 * 
	 * @return reference to the leftmost node in the binary tree rooted at this node
	 */
	public BinaryNode<Type> getLeftmostNode() {
		BinaryNode<Type> cur = this;
		while (this.leftChild != null) {
			cur = this.leftChild;
		}
		return cur;
	}

	/**
	 * Gets the reference to the rightmost node in the binary tree rooted at this binary node.
	 * 
	 * @return reference to the rightmost node in the binary tree rooted at this node
	 */
	public BinaryNode<Type> getRightmostNode() {
		BinaryNode<Type> cur = this;
		while (this.rightChild != null) {
			cur = this.rightChild;
		}
		return cur;
	}

	/**
	 * Gets the height of the binary tree rooted at this binary node, where the height of a 
	 * tree is the length of the longest path to a leaf node (e.g., a tree with a single 
	 * node has a height of zero).
	 * 
	 * @return the height of the binary tree rooted at this node
	 */
	public int height() {
		// base case : don't have both child
		if (this.rightChild == null && this.leftChild == null) return 0;
		// base case : don't have left child
		if (this.leftChild == null) return rightChild.height();
		// base case : don't have right child
		if (this.rightChild == null) return leftChild.height();
		// recursive call
		return Math.max(rightChild.height(), leftChild.height()) + 1;
	}
}