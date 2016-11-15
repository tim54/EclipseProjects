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
	
	public static int repeatedArithmeticShift(int x, int count) {
		for (int i = 0; i < count; i++) {
			x >>= 1;
			System.out.println(x);
		}
		return x;
	}

	public static int repeatedLogicalShift(int x, int count) {
		for (int i = 0; i < count; i++) {
			x >>>= 1;
			System.out.println(x);
		}
		return x;
	}
	
	public static boolean getBit(int num, int i) {
		return ((num & (1 << (i - 1))) != 0);
	}
	
	public static int setBit(int num, int i) {
		return num | (1 << (i - 1));
	}
	
	public static int clearBit(int num, int i) {
		int mask = ~(1 << (i - 1));
		return num & mask;
	}
	
	public static int clearBitsMSBthroughI(int num, int i) {
		int mask = (1 << i) - 1;
		return num & mask;
	}
	
	// To clear all bits from i through 0 (inclusive), we take a sequence of all1s (which is -1) and shift it left by i + 1 bits.
	public static int clearBitslthrough0(int num, int i) {
		int mask = (-1 << (i + 1));
		return num & mask;
	}
	
	int updateBit(int num, int i, boolean bitIs1) {
		int value = bitIs1 ? 1 : 0;
		int mask = ~(1 << i);
		return (num & mask) | (value << i);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int number = 1000;
		System.out.println(Integer.bitCount(number));
		System.out.println(intBytesCount(number));
		
		repeatedArithmeticShift(-number, 10);
		repeatedLogicalShift(-number, 10);
		
		System.out.println(getBit(flagbit3, 2));
		System.out.println(getBit(flagbit3, 3));
		System.out.println(getBit(flagbit3, 4));
		
		System.out.println(getBit(setBit(0, 3), 4));
		System.out.println(getBit(setBit(0, 4), 4));
		System.out.println(getBit(setBit(0, 5), 4));

	}

}
