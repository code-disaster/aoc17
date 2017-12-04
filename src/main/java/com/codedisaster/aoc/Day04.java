package com.codedisaster.aoc;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Day04 {

	public static void main(String[] args) throws IOException {

		ArrayList<ArrayList<char[]>> phrases = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day02.class.getResourceAsStream("/Day04.txt")));

		for(;;) {

			String line = reader.readLine();

			if (line == null) {
				break;
			}

			String[] words = line.split("\\s+");

			if (words.length > 0) {

				ArrayList<char[]> chars = new ArrayList<>();

				for (String word : words) {
					chars.add(word.toCharArray());
				}

				phrases.add(chars);
			}
		}

		// part 1
		{
			int numValid = 0;

			for (ArrayList<char[]> phrase : phrases) {

				int len = phrase.size();
				boolean valid = true;

				for (int i = 0; valid && i < len - 1; i++) {
					for (int j = i + 1; valid && j < len; j++) {

						String s0 = new String(phrase.get(i));
						String s1 = new String(phrase.get(j));

						valid = !s0.equals(s1);
					}
				}

				if (valid) {
					numValid++;
				}
			}

			System.out.println("valid: " + numValid);
		}

		// sort
		{
			for (ArrayList<char[]> phrase : phrases) {
				for (char[] word : phrase) {
					Arrays.sort(word);
				}
			}
		}

		// part 2
		{
			int numValid = 0;

			for (ArrayList<char[]> phrase : phrases) {

				int len = phrase.size();
				boolean valid = true;

				for (int i = 0; valid && i < len - 1; i++) {
					for (int j = i + 1; valid && j < len; j++) {

						String s0 = new String(phrase.get(i));
						String s1 = new String(phrase.get(j));

						valid = !s0.equals(s1);
					}
				}

				if (valid) {
					numValid++;
				}
			}

			System.out.println("valid: " + numValid);
		}
	}

}
