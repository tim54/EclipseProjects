package home.algorithms.search;

import java.util.Comparator;

public class Insertion {
	
	private Insertion() { }
	
	public static void sort(Comparable[] a){
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = i; j > 0 && less(a[j], a[j - 1]); j--){
				exch(a, j, j - 1);
			}
		}
	}
	
	public static void sort(Object[] a, Comparator comparator) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = i; j > 0 && less(a[j], a[j - 1], comparator); j--){
				exch(a, j, j - 1);
			}
		}
	}
	
	private static boolean less(Comparable a, Comparable b){
		return a.compareTo(b) < 0;
	}
	
	private static boolean less(Object a, Object b, Comparator comparator) {
		return comparator.compare(a, b) < 0;
	}
	
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	public static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

	public static void main(String[] args) {
		String[] a = { "my string", "his string", "her string", "mmi string"};
		Insertion.sort(a);
		Insertion.show(a);
	}

}
