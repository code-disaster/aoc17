package com.codedisaster.aoc;

import java.math.BigInteger;
import java.util.*;

public class Day06 {

	private static final int[] INPUT = {
			11, 11, 13, 7, 0, 15, 5, 5, 4, 4, 1, 1, 7, 1, 15, 11
	};

	private static BigInteger hash(int[] memory) {
		BigInteger h = BigInteger.ZERO;
		for (int banks : memory) {
			assert banks < 32;
			h = h.shiftLeft(5).or(BigInteger.valueOf(banks));
		}
		return h;
	}

	private static int max(int[] memory) {

		int idx = 0;
		int max = memory[0];

		for (int i = 1; i < memory.length; i++) {
			if (max < memory[i]) {
				idx = i;
				max = memory[i];
			}
		}

		return idx;
	}

	private static void redistribute(int[] memory, int index) {

		int banks = memory[index];
		memory[index] = 0;

		for (int i = 0; i < banks; i++) {
			index = (index + 1) % memory.length;
			memory[index]++;
		}
	}

	public static void main(String[] args) {

		int[] memory = Arrays.copyOf(INPUT, INPUT.length);
		BigInteger hash = hash(memory);

		Map<BigInteger, Integer> hashes = new HashMap<>();
		int cycles = 0;

		// part 1
		{
			do {

				hashes.put(hash, cycles);

				redistribute(memory, max(memory));

				cycles++;

				hash = hash(memory);

			} while (!hashes.containsKey(hash));

			System.out.println("cycles: " + cycles);
		}

		// part 2
		{
			int seen = hashes.get(hash);
			System.out.println("seen after: " + (cycles - seen));
		}
	}

}
