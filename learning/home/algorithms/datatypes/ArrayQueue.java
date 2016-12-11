package home.algorithms.datatypes;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class ArrayQueue<Item> implements Iterable<Item> {
	private Item q[];
	private int first;
	private int last;
	private int n;
	
	public ArrayQueue() {
		q = (Item[]) new Object[2];
		first = 0;
		last = 0;
		n = 0;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	private void resize(int capacity) {
		assert capacity >= n;
		Item temp[] = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++) {
			temp[i] = q[(first + i) % q.length];
		}
		q = temp;
		first = 0;
		last = n;
	}
	
	public void enqueue(Item item) {
		if (n == q.length) resize(2*q.length);
		q[last++] = item;
		if (last == q.length) last = 0;
		n++;
	}
	
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException();
		Item item = q[first];
		q[first] = null;
		n--;
		first++;
		if (first == q.length) first = 0;
		if (n > 0 && n == q.length / 4) resize(q.length / 2);
		return item;
	}
	
	public Item peek() {
		if (isEmpty()) throw new NoSuchElementException();
		return q[first];
	}
	
	public Iterator<Item> iterator() {
		return new ArrayQueueIterator();
	}
	
	private class ArrayQueueIterator implements Iterator<Item>{
		int i = 0;
		public boolean hasNext() { return i < n; }
		public void remove() { throw new UnsupportedOperationException(); }
		
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = q[(first + i) % q.length];
			i++;
			return item;
		}
	}

	public static void main(String[] args) {
		ArrayQueue<String> queue = new ArrayQueue<String>();
		queue.enqueue("Hello");
		queue.enqueue("World");
		queue.enqueue("how");
		queue.enqueue("are");
		queue.enqueue("you");

        for (String s : queue)
            StdOut.println(s);

        StdOut.println();
        
        queue.dequeue();
        queue.dequeue();
        
        for (String s : queue)
            StdOut.println(s);
        
	}

}
