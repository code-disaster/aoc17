package com.codedisaster.aoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day09 {

	private static class Group {

		int from = -1;
		int to = -1;
		int score = -1;

		int numNonCanceled = 0;

		final List<Group> children = new ArrayList<>();

		void parse(String input, int from) {

			int idx = from + 1;

			for (; ; ) {

				char ch = input.charAt(idx);

				if (ch == '{') {
					// new group
					Group child = new Group();
					child.parse(input, idx);
					children.add(child);
					idx = child.to + 1;
				} else if (ch == '}') {
					// end group
					break;
				} else if (ch == ',') {
					// next group or garbage
					idx++;
				} else if (ch == '<') {
					// garbage
					for (; ; ) {
						idx++;
						ch = input.charAt(idx);
						if (ch == '!') {
							idx++;
						} else if (ch == '>') {
							break;
						} else {
							numNonCanceled++;
						}
					}
				} else if (ch == '>') {
					idx++;
				}
			}

			this.from = from;
			this.to = idx;

			assert input.charAt(this.from) == '{';
			assert input.charAt(this.to) == '}';
		}

		void score(int score) {
			this.score = score;
			for (Group child : children) {
				child.score(score + 1);
			}
		}

		int getTotalScore() {
			int n = score;
			for (Group child : children) {
				n += child.getTotalScore();
			}
			return n;
		}

		int getNumNonCanceled() {
			int n = numNonCanceled;
			for (Group child : children) {
				n += child.getNumNonCanceled();
			}
			return n;
		}
	}

	public static void main(String[] args) throws IOException {

		// parse input
		StringBuilder input = new StringBuilder();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day02.class.getResourceAsStream("/Day09.txt")));

		for (; ; ) {

			String line = reader.readLine();

			if (line == null || line.isEmpty()) {
				break;
			}

			input.append(line);
		}

		Group root = new Group();
		root.parse(input.toString(), 0);
		root.score(1);

		// part 1
		{
			int totalScore = root.getTotalScore();
			System.out.println("total score: " + totalScore);
		}

		// part 2
		{
			int nonCanceled = root.getNumNonCanceled();
			System.out.println("non-canceled: " + nonCanceled);
		}
	}

}
