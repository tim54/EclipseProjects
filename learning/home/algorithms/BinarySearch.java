package home.algorithms;

public class BinarySearch {
	
    public static int rank(int val, int[] arr) {
        return rank(val, arr, 0, arr.length-1);
    }
    
    private static int rank(int val, int[] arr, int lo, int hi) {
        if (lo > hi) return -1;
        
        int mid = lo + (hi - lo) / 2;
        
        if (val < arr[mid]) {
            return rank(val, arr, lo, mid - 1);
        } else if (val > arr[mid]) {
            return rank(val, arr, mid + 1, hi);
        } else {
            return mid;
        }
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 1,12,23,34,55,61,67,88,89,101 };
        System.out.println(rank(55, arr));
	}

}
