package com.codedisaster.aoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day19 {

	enum Dir {
		N,
		S,
		W,
		E
	}

	private static char[][] map;
	private static int width, height;

	private static boolean inside(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	private static char map(int x, int y) {
		if (inside(x, y)) {
			return map[x][y];
		}
		return '\0';
	}

	public static void main(String[] args) throws IOException {

		{
			List<String> lines = new ArrayList<>();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					Day19.class.getResourceAsStream("/Day19.txt")));

			for (; ; ) {

				String line = reader.readLine();

				if (line == null || line.isEmpty()) {
					break;
				}

				lines.add(line);
				width = Math.max(width, line.length());
			}

			height = lines.size();
			map = new char[width][height];

			int x, y = 0;

			for (String line : lines) {

				for (x = 0; x < line.length(); x++) {
					char c = line.charAt(x);
					map[x][y] = (c != ' ') ? c : '\0';
				}

				y++;
			}
		}

		{
			int x, y = 0;
			Dir d = Dir.S;

			int steps = 0;

			String letters = "";

			for (x = 0; x < width; x++) {
				if (map[x][0] != '\0') {
					break;
				}
			}

			for (;;) {

				if (!inside(x, y)) {
					break;
				}

				char c = map(x, y);

				if (c == '\0') {
					break;
				}

				switch (c) {

					case '-':
					case '|': {
						switch (d) {
							case N:
								y--;
								break;
							case S:
								y++;
								break;
							case E:
								x++;
								break;
							case W:
								x--;
								break;
						}
						break;
					}

					case '+': {
						switch (d) {
							case N:
							case S:
								if (map(x + 1, y) != '\0') {
									x++;
									d = Dir.E;
								} else {
									x--;
									d = Dir.W;
								}
								break;
							case W:
							case E:
								if (map(x, y + 1) != '\0') {
									y++;
									d = Dir.S;
								} else {
									y--;
									d = Dir.N;
								}
								break;
						}
						break;
					}

					default:
						letters = letters.concat(String.valueOf(c));
						switch (d) {
							case N:
								y--;
								break;
							case S:
								y++;
								break;
							case W:
								x--;
								break;
							case E:
								x++;
								break;
						}
						break;
				}

				steps++;
			}

			System.out.println("letters: " + letters);
			System.out.println("steps: " + steps);
		}
	}

}
