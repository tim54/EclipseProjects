package home.algorithms.binarylogic;

public class BinaryLogic {
	
	static final int flagAllOff = 0;  //         000...00000000 (empty mask)
	static final int flagbit1 = 1;    // 2^^0    000...00000001
	static final int flagbit2 = 2;    // 2^^1    000...00000010
	static final int flagbit3 = 4;    // 2^^2    000...00000100
	static final int flagbit4 = 8;    // 2^^3    000...00001000
	static final int flagbit5 = 16;   // 2^^4    000...00010000
	static final int flagbit6 = 32;   // 2^^5    000...00100000
	static final int flagbit7 = 64;   // 2^^6    000...01000000
	static final int flagbit8 = 128;  // 2^^7    000...10000000
	
	public static int intBytesCount(int number) {
		int counter = 0;
		int res = 0;
		int bytes = Integer.BYTES;
		
		while (number != 0) {
			if ((number & flagbit1) == 1) {
				counter++;
			}
			number = number >> 1;
		}
		
		return counter;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int number = 1000;
		System.out.println(Integer.bitCount(number));
		System.out.println(intBytesCount(number));

	}

}
