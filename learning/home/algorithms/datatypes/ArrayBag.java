package home.algorithms.datatypes;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.StdOut;

public class ArrayBag<Item> implements Iterable<Item> {

	private Item[] a;
	private int n;
	
	public ArrayBag() {
		a = (Item[]) new Object[2];
		n = 0;
	}
	
	public boolean isEmpty(){
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	private void resize(int capacity) {
		if (capacity >= n) {
			Item[] temp = (Item[]) new Object[capacity];
			for (int i = 0; i < n; i++) {
				temp[i] = a[i];
			}
			a = temp;
		}
	}
	
	public void add(Item item) {
		if (n == a.length) resize(2*a.length);
		a[n++] = item;
	}
	
	public Iterator<Item> iterator() {
		return new ArrayIterator();
	}
	
	private class ArrayIterator implements Iterator<Item>{
		private int i = 0;
		
		public boolean hasNext() { return i < n; }
		
		public void remove() { throw new UnsupportedOperationException(); }
		
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			return a[i++];
		}
	}
	
	public static void main(String[] args) {
		ArrayBag<String> bag = new ArrayBag<String>();
        bag.add("Hello");
        bag.add("World");
        bag.add("how");
        bag.add("are");
        bag.add("you");

        for (String s : bag)
            StdOut.println(s);

	}

}
