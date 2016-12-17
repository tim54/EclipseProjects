package home.algorithms.search;

public class Shell {
	
	public Shell() { }
	
	public static void sort(Comparable[] a) {
		int n = a.length;
		int h = 1;
		
		while (h < n/3) h = 3*h + 1;
		
		while (h >=1 ) {
			for (int i = h; i < n; i++) {
				for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
					swap(a, j, j - h);
				}
			}
			h /= 3;
		}
	}
	
	private static boolean less(Comparable v, Comparable w){
		return v.compareTo(w) < 0;
	}
	
	private static void swap(Comparable[] a, int i, int j) {
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

	public static void main(String[] args) {
		String[] a = { "my string", "his string", "her string", "mmi string"};
		Shell.sort(a);
		Shell.show(a);

	}

}
