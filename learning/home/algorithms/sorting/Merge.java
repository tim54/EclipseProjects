package home.algorithms.sorting;

public class Merge {
	
	public Merge() { }
	
	/*Top-down mergesort uses between ½NlgN and NlgN compares to
	sort any array of length N.
	Another way to understand Proposition F is to examine the tree drawn below, where
	each node depicts a subarray for which sort() does a merge(). The tree has precisely
	n levels. For k from 0 to n  1, the kth level from the top depicts 2k subarrays, each of
	length 2nk, each of which thus requires at most 2nk compares for the merge. Thus we
	have 2k · 2nk = 2n total cost for each of the n levels, for a total of n 2n = N lgN.
	Top-down mergesort uses at most 6N lg N array accesses to sort an array of length N.
	Each merge uses at most 6N array accesses (2N for the copy, 2N for the move back, and at 
	most 2N for compares).*/
	public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi){
		assert isSorted(a, hi, mid);
		assert isSorted(a, mid + 1, hi);
		
		for (int i = lo; i <= hi; i++) {
			aux[i] = a[i];
		}
		
		int i = lo; int j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if      (i > mid) 				a[k] = aux[j++];
			else if (j > hi)  				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))  a[k] = aux[j++];
			else 							a[k] = aux[i++];
		}
		
		assert isSorted(a, lo, hi);
	}
	
	public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (lo >= hi) return;
		int mid = lo - (lo - hi) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}
	
	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
	}
	
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}
	
	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++){
			if (less(a[i], a[i - 1])) return false;
		}
		return true;
	}
	
	private static void merge(Comparable[] a, int[] index, int[] aux, int lo, int mid, int hi) {

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = index[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)                    index[k] = aux[j++];
            else if (j > hi)                     index[k] = aux[i++];
            else if (less(a[aux[j]], a[aux[i]])) index[k] = aux[j++];
            else                                 index[k] = aux[i++];
        }
    }
	
	public static int[] indexSort(Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];
        for (int i = 0; i < n; i++)
            index[i] = i;

        int[] aux = new int[n];
        sort(a, index, aux, 0, n-1);
        return index;
    }
	
	public static void sort(Comparable[] a, int[] index, int[] aux, int lo, int hi) {
		if (lo >= hi) return;
		int mid = lo + (hi - lo) / 2;
		sort(a, index, aux, lo, mid);
		sort(a, index, aux, mid + 1, hi);
		merge(a, index, aux, lo, mid, hi);
	}
	
	public static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

	public static void main(String[] args) {
		String[] a = { "my string", "his string", "her string", "mmi string"};
		Merge.sort(a);
		Merge.show(a);
		
		String[] b = { "my string", "his string", "her string", "mmi string"};
		int[] c = Merge.indexSort(b);
		for (int i = 0; i < c.length; i++) {
			System.out.println(c[i]);
		}
	}

}
