package com.codedisaster.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day17 {

	private static final int INPUT = 394;

	public static void main(String[] args) {

		{
			List<Integer> buffer = new ArrayList<>();
			buffer.add(0);

			int pos = 0;

			for (int iterations = 1; iterations <= 2017; iterations++) {

				pos = (pos + INPUT) % iterations;

				pos++;

				buffer.add(pos, iterations);
			}

			System.out.println("after 2017: " + buffer.get((pos + 1) % buffer.size()));
		}

		{
			int pos = 0;
			int valueAfterZero = 0;

			for (int iterations = 1; iterations <= 50_000_000; iterations++) {

				pos = (pos + INPUT) % iterations;

				if (pos == 0) {
					valueAfterZero = iterations;
				}

				pos++;
			}

			System.out.println("after '0': " + valueAfterZero);
		}
	}

}
