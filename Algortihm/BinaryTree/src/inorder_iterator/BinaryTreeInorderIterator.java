package inorder_iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BinaryTreeInorderIterator implements Iterator<Integer> {
	private Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();

	private void pushLeftChildren(BinaryTreeNode cur) {
		while (cur != null) {
			stack.push(cur);
			cur = cur.left;
		}
	}

	public BinaryTreeInorderIterator(BinaryTreeNode root) {
		pushLeftChildren(root);
	}

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException("All nodes have been visited!");
		}

		BinaryTreeNode res = stack.pop();
		pushLeftChildren(res.right);

		return res.val;
	}

}

class BinaryTreeNode {

	BinaryTreeNode left;
	BinaryTreeNode right;
	int val;

	public BinaryTreeNode(int val) {
		this.val = val;
		this.left = this.right = null;
	}
}