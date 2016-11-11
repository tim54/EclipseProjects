package tmp;

public class BinaryTree {
	
	private class TreeNode {
		String data;
		int key;
		
		TreeNode leftNode;
		TreeNode rightNode;
		
		public TreeNode(int key, String data){
			this.key = key;
			this.data = data;
		}
	}
	
	TreeNode top;
	
	public void addNode(int key, String data) {
		
		TreeNode newNode = new TreeNode(key, data);
		
		if (top == null) {
			top = newNode;
		} else {
			
			TreeNode focusNode = top;
			TreeNode parent;
			
			while (true) {
				parent = focusNode;
				
				if (key < focusNode.key){
					focusNode = focusNode.leftNode;
					if (focusNode == null) {
						parent.leftNode = newNode;
						return;
					}
				} else {
					focusNode = focusNode.rightNode;
					if (focusNode == null) {
						parent.rightNode = newNode;
						return;
					}
				}
			}
			
		}
	}
	
	public void inOrderTraverseTree(TreeNode node){
		if (node != null) {
			inOrderTraverseTree(node.leftNode);
			System.out.println(node);
			inOrderTraverseTree(node.rightNode);
		}
	}
	
	public TreeNode findNode(int key){
		TreeNode focusNode = top;
		
		while (focusNode.key != key){
			if (key < focusNode.key) {
				focusNode = focusNode.leftNode;
			} else {
				focusNode = focusNode.rightNode;
			}
			
			if (focusNode == null)
				return null;
		}
		
		return focusNode;
	}

}
