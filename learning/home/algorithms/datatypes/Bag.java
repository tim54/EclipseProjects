package home.algorithms.datatypes;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item>{
	
	private Node first;
	private Node end;
	private int n;
	
	private class Node {
		private Item item;
		private Node next;
	}
	
	public Bag() {
		first = null;
		end = null;
		n = 0;
	}
	
	public Bag(Bag<Item> list){
		for (Item item : list){
			this.add(item);
		}
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int size() {
		return n;
	}
	
	public void add(Item item){
		Node newNode = new Node();
		newNode.item = item;
		
		if (end != null) {
			end.next = newNode;
			end = newNode;
		} else {
			first = end = newNode;
		}
		
	}
	
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private Node current = first;
		
		public boolean hasNext() { return current != null; }
		public void remove() { throw new UnsupportedOperationException(); }
		
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		
		Node focusNode = first;
		str.append("List is: ");
		while (focusNode.next != null) {
			str.append(focusNode.item.toString() + " ");
			focusNode = focusNode.next;
		}
		str.append(focusNode.item.toString() + ";");
		return str.toString();
	}
	
	public void revertList(){
		end = first;
		Node focusNode = first;
		Node previousNode = null;
		Node tmp = new Node();
		
		while(focusNode.next != null){
			previousNode = focusNode;
			focusNode = focusNode.next;
			
			if (previousNode == first){
				tmp.next = previousNode;
				previousNode.next = null;
			} else {
				previousNode.next = tmp.next;
				tmp.next = previousNode;
			}
		}
		focusNode.next = previousNode;
		first = focusNode;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Bag<Integer> intList = new Bag<>();
		intList.add(1);
		intList.add(2);
		intList.add(3);
		intList.add(4);
		
		System.out.println(intList.toString());
		intList.revertList();
		System.out.println(intList.toString());
	}

}
