package home.algorithms;

class Node {

	Node next = null;
	int data;

	public Node(int d) {
		data = d;
	}

	void appendToTail(int d) {
		Node end = new Node(d);
		Node n = this;
		while (n.next != null) {
			n = n.next;
		}
		n. next = end;
	}

	Node deleteNode(Node head, int d) {
		Node n = head;

		if (n.data == d) {
			return head.next; /* moved head */
		}

		while (n.next != null) {
			if (n.next.data == d) {
				n.next = n.next.next;
				return head; /* head didn't change */
			}
			n = n.next;
		}
		return head;
	}
}

public class LinkedList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Node myNode = new Node(1);
		myNode.appendToTail(2);
		myNode.appendToTail(3);
		myNode.deleteNode(myNode, 3);
	}

}
