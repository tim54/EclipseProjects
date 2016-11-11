package tmp;

public class LinkedList {
	
	private class Node {
		private String string;
		
		public Node next = null;
		
		public Node(){
			string = "";
			next = null;
		}
		
		public Node(String string) {
			this.string = string;
		}
	}
	
	private Node beg = null;

	public void LinkedList(String string) {
		if (beg == null) {
			beg = new Node(string);
		}
	}
	
	public void add(String string) {
		if (beg == null) {
			beg = new Node(string);
		} else {
			Node tmp = new Node();
			while (true) {
				if (tmp.next != null){
					tmp = tmp.next;
				} else {
					tmp.next = new Node(string);
					return;
				}
			}
		}
	}
	
	public void addBefore(String string, String before) {
		Node newNode = new Node(string);
		if (beg == null) {
			beg = newNode;
		} else {
			Node tmp = new Node();
			while (tmp.next != null){
				if (tmp.string.equals(before)){
					newNode.next = tmp.next;
					tmp.next = newNode;
					return;
				}
				tmp = tmp.next;
			}
			tmp.next = newNode;
		}
	}
	
	public void revertList(){
		Node focusNode = beg;
		Node previousNode = null;
		Node tmp = null;
		
		while(focusNode.next != null){
			previousNode = focusNode;
			focusNode = focusNode.next;
			
			if (previousNode == beg){
				previousNode.next = null;
				tmp = previousNode;
			} else {
				previousNode.next = tmp;
				tmp = previousNode;
			}
		}
		
		focusNode.next = previousNode;
	}

}
