package com.codedisaster.aoc;

import java.io.*;
import java.util.ArrayList;

public class Day05 {

	private static int walk(ArrayList<Integer> offsets, int inc, int dec) {

		offsets = new ArrayList<>(offsets);

		int steps = 0;
		int index = 0;

		int size = offsets.size();

		while (index >= 0 && index < size) {

			int jmp = offsets.get(index);

			int d = (jmp < 3) ? inc : dec;
			offsets.set(index, jmp + d);

			index += jmp;

			steps++;
		}

		return steps;
	}

	public static void main(String[] args) throws IOException {

		ArrayList<Integer> offsets = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day02.class.getResourceAsStream("/Day05.txt")));

		for (; ; ) {

			String line = reader.readLine();

			if (line == null) {
				break;
			}

			offsets.add(Integer.parseInt(line));
		}

		// part 1
		{
			int steps = walk(offsets, 1, 1);
			System.out.println("steps: " + steps);
		}

		// part 2
		{
			int steps = walk(offsets, 1, -1);
			System.out.println("steps: " + steps);
		}
	}

}
