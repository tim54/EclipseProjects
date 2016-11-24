package home.algorithms.datatypes;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item> implements Iterable<Item>{
	private Node<Item> first;
	private int n;
	
	private static class Node<Item>{
		private Item item;
		private Node<Item> next;
	}
	
	public Stack(){
		first = null;
		n = 0;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int size() {
		return n;
	}
	
	public void push(Item item) {
		Node<Item> oldFirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldFirst;
		n++;
	}
	
	public Item pop() {
		if (isEmpty()) { throw new EmptyStackException(); }
		Item item = first.item;
		first = first.next;
		n--;
		return item;
	}
	
	public Item peek() {
		if (isEmpty()) { throw new EmptyStackException(); }
		return first.item;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Item item : this) {
			builder.append(item + " ");
		}
		return builder.toString();
	}
	
	public Iterator<Item> iterator() {
		return new StackIterator<Item>(first);
	}
	
	private class StackIterator<Item> implements Iterator<Item> {
		private Node<Item> current;
		
		public StackIterator(Node<Item> first) {
			current = first;
		}
		
		public boolean hasNext(){
			return current != null;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args) {
		Stack<String> myStack = new Stack<>();
		myStack.push("1");
		myStack.push("2");
		myStack.push("3");
		myStack.push("4");
		
		System.out.println("The stack is: " + myStack.toString());
		
		myStack.peek();
		
		System.out.println("The stack is: " + myStack.toString());
		
		myStack.pop();
		
		System.out.println("The stack is: " + myStack.toString());
		
		// TODO Auto-generated method stub

	}
}
