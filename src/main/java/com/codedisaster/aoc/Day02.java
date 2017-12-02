package com.codedisaster.aoc;

import java.io.*;
import java.util.ArrayList;

public class Day02 {

	public static void main(String[] args) throws IOException {

		ArrayList<ArrayList<Integer>> sheet = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day02.class.getResourceAsStream("/Day02.txt")));

		for(;;) {

			String line = reader.readLine();

			if (line == null) {
				break;
			}

			String[] values = line.split("\\s+");
			ArrayList<Integer> row = new ArrayList<>(values.length);

			for (String value : values) {
				row.add(Integer.parseInt(value));
			}

			sheet.add(row);
		}

		// part 1
		{
			int hash = 0;

			for (ArrayList<Integer> row : sheet) {

				int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

				for (Integer value : row) {
					min = Math.min(min, value);
					max = Math.max(max, value);
				}

				hash += max - min;
			}

			System.out.println("part 1: hash=" + hash);
		}

		// part 2
		{
			int hash = 0;

			for (ArrayList<Integer> row : sheet) {

				int len = row.size();
				boolean found = false;

				for (int i = 0; !found && i < len; i++) {

					for (int j = 0; !found && j < len; j++) {

						if (i == j) {
							continue;
						}

						int v0 = row.get(i);
						int v1 = row.get(j);

						if (v0 < v1) {
							int swap = v0; v0 = v1; v1 = swap;
						}

						if (v0 % v1 == 0) {
							hash += v0 / v1;
							found = true;
						}
					}
				}
			}

			System.out.println("part 2: hash=" + hash);
		}
	}

}
