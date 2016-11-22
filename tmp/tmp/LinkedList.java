package tmp;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {
	
	private class Node {
		private T data;
		private Node next;
		
		public Node(T data){
			this.data = data;
		}
	}
	
	private Node first;
	private int n;
	
	public LinkedList(T data){
		Node newNode = new Node(data);
		if (first == null) {
			first = newNode;
		} else {
			newNode.next = first;
			first = newNode;
		}
		n++;
	}
	
	public Iterator<T> iterator(){
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<T>{
		private Node current = first;
		
		public boolean hasNext() { return current != null; }
		public void remove() { throw new UnsupportedOperationException(); }
		
		public T next() {
			if (!hasNext()) { throw new NoSuchElementException(); }
			T data = current.data;
			current = current.next;
			return data;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
