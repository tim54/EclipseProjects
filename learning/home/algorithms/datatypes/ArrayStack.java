package home.algorithms.datatypes;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class ArrayStack<Item> implements Iterable<Item> {
	private Item a[];
	private int n;
	
	public ArrayStack() {
		a = (Item []) new Object[2];
		n = 0;
	}
	
	public boolean isEmpty() { return n == 0;}
	
	public int size() { return n; }
	
	private void resize(int capacity) {
		assert capacity >= n;
		
		Item tmp[] = (Item []) new Object[capacity];
		for (int i = 0; i < n; i++) { tmp[i] = a[i]; }
		a = tmp;
	}
	
	public void push(Item item) {
		if (n == a.length) resize(2*a.length);
		a[n++] = item;
	}
	
	public Item pop() {
		if (isEmpty()) throw new NoSuchElementException();
		Item item = a[n - 1];
		a[n - 1] = null;
		n--;
		if (n > 0 && n == a.length/4) resize(a.length/2);
		return item;
	}
	
	public Item peek() {
		if (isEmpty()) throw new NoSuchElementException();
		return a[n - 1];
	}
	
	public Iterator<Item> iterator () { return new ArrayStackIterator(); }
	
	private class ArrayStackIterator implements Iterator<Item> {
		private int i;
		
		public ArrayStackIterator() {
			i = n - 1;
		}
		
		public boolean hasNext() { return i >= 0; }
		
		public void remove() { throw new UnsupportedOperationException(); }
		
		public Item next() {
			if (!hasNext()) throw new EmptyStackException();
			return a[i--];
		}
	}
	

	public static void main(String[] args) {
		ArrayStack<String> stack = new ArrayStack<String>();
		stack.push("Hello");
		stack.push("World");
		stack.push("how");
		stack.push("are");
		stack.push("you");

        for (String s : stack)
            StdOut.println(s);

        StdOut.println();
        
        stack.pop();
        stack.pop();
        
        for (String s : stack)
            StdOut.println(s);

	}

}
