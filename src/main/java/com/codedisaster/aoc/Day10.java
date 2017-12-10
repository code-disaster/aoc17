package com.codedisaster.aoc;

public class Day10 {

	private static final int[] LENGTHS = {
			183, 0, 31, 146, 254, 240, 223, 150, 2, 206, 161, 1, 255, 232, 199, 88
	};

	private static final String INPUT = "183,0,31,146,254,240,223,150,2,206,161,1,255,232,199,88";

	private static final int[][] listBuf = new int[2][256];
	private static int listIdx = 0;

	private static void reverse(int[] src, int[] dst, int pos, int len) {
		for (int i = 0; i < len; i++) {
			int s = (pos + i) % src.length;
			int d = (pos + (len - 1) - i) % dst.length;
			dst[d] = src[s];
		}
	}

	public static void main(String[] args) {

		// part 1
		{
			int[] list = listBuf[0];
			for (int i = 0; i < list.length; i++) {
				list[i] = i;
			}

			int pos = 0;
			int skip = 0;

			for (int length : LENGTHS) {

				int[] src = listBuf[listIdx];
				int[] dst = listBuf[1 - listIdx];

				// copy
				System.arraycopy(src, 0, dst, 0, src.length);

				// reverse
				reverse(src, dst, pos, length);

				// forward+skip
				pos = (pos + length + skip) % src.length;
				skip++;

				// flip
				listIdx = 1 - listIdx;
			}

			int[] result = listBuf[listIdx];
			System.out.println("result: " + (result[0] * result[1]));
		}

		// part 2
		{
			int[] list = listBuf[0];
			for (int i = 0; i < list.length; i++) {
				list[i] = i;
			}

			int pos = 0;
			int skip = 0;

			int inputLen = INPUT.length();
			char[] input = new char[inputLen + 5];
			INPUT.getChars(0, inputLen, input, 0);

			input[inputLen++] = (char) 17;
			input[inputLen++] = (char) 31;
			input[inputLen++] = (char) 73;
			input[inputLen++] = (char) 47;
			input[inputLen++] = (char) 23;

			for (int r = 0; r < 64; r++) {
				for (int i = 0; i < inputLen; i++) {

					int length = input[i];

					int[] src = listBuf[listIdx];
					int[] dst = listBuf[1 - listIdx];

					// copy
					System.arraycopy(src, 0, dst, 0, src.length);

					// reverse
					reverse(src, dst, pos, length);

					// forward+skip
					pos = (pos + length + skip) % src.length;
					skip++;

					// flip
					listIdx = 1 - listIdx;
				}
			}

			int[] result = listBuf[listIdx];
			byte[] hash = new byte[16];

			System.out.print("hash: ");

			for (int i = 0; i < 16; i++) {
				int idx = i * 16;
				hash[i] = (byte) result[idx++];
				for (int j = 1; j < 16; j++) {
					hash[i] ^= (byte) result[idx++];
				}
				System.out.print(String.format("%02x", hash[i]));
			}

			System.out.println();
		}
	}

}
