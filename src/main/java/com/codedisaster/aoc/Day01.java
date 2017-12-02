package com.codedisaster.aoc;

import java.io.*;

public class Day01 {

	public static void main(String[] args) throws IOException {

		int len = 1;

		// part 1
		{
			InputStream is = Day01.class.getResourceAsStream("/Day01.txt");

			int sum = 0;
			boolean eof = false;

			int b0 = is.read();
			int b = b0, bn;

			while (!eof) {

				bn = is.read();

				if (bn == -1) {
					bn = b0;
					eof = true;
				} else {
					len++;
				}

				if (b == bn) {
					sum += b - '0';
				}

				b = bn;
			}

			System.out.println("part 1: sum=" + sum);
		}

		// part 2
		{
			InputStream is2 = Day01.class.getResourceAsStream("/Day01.txt");

			byte[] bytes = new byte[len];
			is2.read(bytes);

			int sum = 0;
			int halfLen = bytes.length / 2;

			for (int i = 0; i < halfLen; i++) {

				if (bytes[i] == bytes[i + halfLen]) {
					sum += 2 * (bytes[i] - '0');
				}
			}

			System.out.println("part 2: sum=" + sum);
		}
	}
}
