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
    
    private static int rankNotRecursive(int val, int[] arr){
    	
    	int start = 0;
    	int end = arr.length - 1;
    	int median = end / 2;
    	
    	while (start != end){
    		if (val == arr[median]){
    			break;
    		} else if (val > arr[median]) {
    			start = median + 1;
    			median = start - (start - end) / 2;
    		} else {
    			end = median - 1;
    			median = end / 2;
    		}
    	}
    	
    	int res = (arr[median] == val) ? median : -1; 
    	
    	return res;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 1,12,23,34,55,61,67,88,89,101 };
        System.out.println("55: " + rank(55, arr));
        System.out.println("55: " + rankNotRecursive(55, arr));
        System.out.println("89: " + rank(89, arr));
        System.out.println("89: " + rankNotRecursive(89, arr));
        System.out.println("12: " + rank(12, arr));
        System.out.println("12: " + rankNotRecursive(12, arr));
        System.out.println("1: " + rank(1, arr));
        System.out.println("1: " + rankNotRecursive(1, arr));
        System.out.println("101: " + rank(101, arr));
        System.out.println("101: " + rankNotRecursive(101, arr));
        System.out.println("90: " + rank(90, arr));
        System.out.println("90: " + rankNotRecursive(90, arr));
	}

}
