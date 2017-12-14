package com.codedisaster.aoc;

public class Day14 {

	private static final String INPUT = "amgozmfv";
	private static final int[][] lists = new int[2][256];

	private static final int[] PATTERNS = {
			0, 0, 0, 0,
			0, 0, 0, 1,
			0, 0, 1, 0,
			0, 0, 1, 1,
			0, 1, 0, 0,
			0, 1, 0, 1,
			0, 1, 1, 0,
			0, 1, 1, 1,
			1, 0, 0, 0,
			1, 0, 0, 1,
			1, 0, 1, 0,
			1, 0, 1, 1,
			1, 1, 0, 0,
			1, 1, 0, 1,
			1, 1, 1, 0,
			1, 1, 1, 1,
	};

	private static final int[] BITS = {
			0,
			1,
			1,
			2,
			1,
			2,
			2,
			3,
			1,
			2,
			2,
			3,
			2,
			3,
			3,
			4,
	};

	private static final byte[][] squares = new byte[128][128];

	private static void reset(int[] src) {
		for (int i = 0; i < 256; i++) {
			src[i] = i;
		}
	}

	private static void reverse(int[] src, int[] dst, int pos, int len) {
		for (int i = 0; i < len; i++) {
			int s = (pos + i) % src.length;
			int d = (pos + (len - 1) - i) % dst.length;
			dst[d] = src[s];
		}
	}

	private static byte[] hash(String input) {

		int inputLen = input.length();

		char[] chars = new char[inputLen + 5];
		input.getChars(0, inputLen, chars, 0);

		chars[inputLen++] = (char) 17;
		chars[inputLen++] = (char) 31;
		chars[inputLen++] = (char) 73;
		chars[inputLen++] = (char) 47;
		chars[inputLen++] = (char) 23;

		int pos = 0;
		int skip = 0;

		reset(lists[0]);
		int idx = 0;

		for (int r = 0; r < 64; r++) {

			for (int i = 0; i < inputLen; i++) {

				int len = chars[i];

				int[] src = lists[idx];
				int[] dst = lists[1 - idx];

				System.arraycopy(src, 0, dst, 0, 256);

				reverse(src, dst, pos, len);

				pos = (pos + len + skip) % 256;
				skip++;

				idx = 1 - idx;
			}
		}

		int[] result = lists[idx];
		byte[] hash = new byte[16];

		for (int i = 0; i < 16; i++) {
			int j = i * 16;
			hash[i] = (byte) result[j++];
			for (int k = 1; k < 16; k++) {
				hash[i] ^= (byte) result[j++];
			}
		}

		return hash;
	}

	private static int getNumberOfBits(byte[] bytes) {

		int n = 0;

		for (byte b : bytes) {
			int n2 = b & 0xff;
			n += BITS[n2 & 0xf];
			n += BITS[n2 >> 4];
		}

		return n;
	}

	private static void setSq(int x, int y, int v) {
		squares[x][y] = (byte) (v & 0xff);
	}

	private static void fillSquares(byte[] bytes, int row) {

		for (int i = 0; i < bytes.length; i++) {

			int n = bytes[i] & 0xff;
			int col = i * 8;

			int ph = (n >> 4) * 4;
			setSq(col++, row, PATTERNS[ph++]);
			setSq(col++, row, PATTERNS[ph++]);
			setSq(col++, row, PATTERNS[ph++]);
			setSq(col++, row, PATTERNS[ph]);

			int pl = (n & 0xf) * 4;
			setSq(col++, row, PATTERNS[pl++]);
			setSq(col++, row, PATTERNS[pl++]);
			setSq(col++, row, PATTERNS[pl++]);
			setSq(col, row, PATTERNS[pl]);
		}
	}



	private static void clearSquares(int x, int y) {

		if (x < 0 || x > 127 || y < 0 || y > 127) {
			return;
		}

		if (squares[x][y] == 0) {
			return;
		}

		setSq(x, y, 0);

		clearSquares(x + 1, y);
		clearSquares(x - 1, y);
		clearSquares(x, y + 1);
		clearSquares(x, y - 1);
	}

	private static boolean extractRegion() {

		for (int y = 0; y < 128; y++) {
			for (int x = 0; x < 128; x++) {
				if (squares[x][y] != 0) {
					clearSquares(x, y);
					return true;
				}
			}
		}

		return false;
	}

	/*private static void printRegions() {

		for (int y = 0; y < 128; y++) {
			for (int x = 0; x < 128; x++) {
				if (squares[x][y] != 0) {
					System.out.print('#');
				} else {
					System.out.print('.');
				}
			}
			System.out.println();
		}
	}*/

	public static void main(String[] args) {

		// part 1
		{
			int bits = 0;

			for (int row = 0; row < 128; row++) {

				String input = INPUT + "-" + row;

				byte[] h = hash(input);
				fillSquares(h, row);

				bits += getNumberOfBits(h);
			}

			System.out.println("squares used: " + bits);
		}

		// part 2
		{
			//printRegions();

			int regions = 0;
			while (extractRegion()) {
				regions++;
			}

			System.out.println("regions: " + regions);
		}
	}

}
