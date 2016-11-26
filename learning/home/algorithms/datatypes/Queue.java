package home.algorithms.datatypes;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
	private Node<Item> first;
	private Node<Item> last;
	private int n;
	
	private class Node<Item> {
		private Item item;
		private Node<Item> next;
	}
	
	public Queue(){
		first = null;
		last = null;
		n = 0;
	}
	
	public boolean isEmpty() { return first == null; }
	
	public int size() { return n; }
	
	public Item peek() {
		if (isEmpty()) throw new NoSuchElementException();
		return first.item;
	}
	
	public void enqueue(Item item){
		Node<Item> oldLast = last;
		last = new Node<>();
		last.item = item;
		if (isEmpty()) first = last;
		else 		   oldLast.next = last;
		n++;
	}
	
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException();
		Item item = first.item;
		first = first.next;
		n--;
		if (isEmpty()) last = null;
		return item;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Item item : this) {
			str.append(item + " ");
		}
		return str.toString();
	}
	
	public Iterator<Item> iterator() {
		return new QueueIterator<Item>(first);
	}
	
	private class QueueIterator<Item> implements Iterator<Item> {
		Node<Item> current;
		
		public QueueIterator(Node<Item> first){
			current = first;
		}
		
		public boolean hasNext() { return current != null; }
		
		public void remove() { throw new UnsupportedOperationException(); }
		
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
		
		
	}

	public static void main(String[] args) {
		Queue<Integer> myQueue = new Queue<>();
		myQueue.enqueue(2);
		myQueue.enqueue(3);
		myQueue.enqueue(4);
		
		System.out.println("Queue is: " + myQueue.toString());
		
		myQueue.dequeue();
		
		System.out.println("Queue is: " + myQueue.toString());
		
		myQueue.dequeue();
		myQueue.dequeue();
		//myQueue.dequeue();
		//myQueue.dequeue();

	}

}
