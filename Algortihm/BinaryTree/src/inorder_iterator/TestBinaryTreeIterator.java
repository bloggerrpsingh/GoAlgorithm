package inorder_iterator;


import java.util.ArrayList;

/** Returns the next integer a in the in-order traversal of the given binary tree.
 * For example, given a binary tree below,
 *       4
 *      / \
 *     2   6
 *    / \ / \
 *   1  3 5  7
 * the outputs will be 1, 2, 3, 4, 5, 6, 7. 
 */ 

public class TestBinaryTreeIterator {

	public static ArrayList<Integer> inorderTraversal(BinaryTreeNode root) {
		BinaryTreeInorderIterator iterator = new BinaryTreeInorderIterator(root);
		ArrayList<Integer> results = new ArrayList<Integer>();
		while (iterator.hasNext()) {
			results.add(iterator.next());
		}
		return results;
	}

	public static void main(String[] args) {
		BinaryTreeNode root = new BinaryTreeNode(4);
		root.left = new BinaryTreeNode(2);
		root.right = new BinaryTreeNode(6);
		root.left.left = new BinaryTreeNode(1);
		root.left.right = new BinaryTreeNode(3);
		root.right.left = new BinaryTreeNode(5);
		root.right.right = new BinaryTreeNode(7);

		ArrayList<Integer> result = inorderTraversal(root);
		System.out.println(result);

	}

}
