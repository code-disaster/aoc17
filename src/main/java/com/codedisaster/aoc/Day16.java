package com.codedisaster.aoc;

import java.io.*;
import java.util.*;

public class Day16 {

	private interface Move {
		void apply(char[] programs);
	}

	private static class Spin implements Move {

		Spin(String input) {
			count = Integer.parseInt(input);
		}

		@Override
		public void apply(char[] programs) {
			for (int i = 0; i < count; i++) {
				shift(programs);
			}
		}

		private void shift(char[] programs) {
			char c0 = programs[programs.length - 1];
			for (int i = programs.length - 2; i >= 0; i--) {
				programs[i + 1] = programs[i];
			}
			programs[0] = c0;
		}

		private final int count;
	}

	private static class Exchange implements Move {

		Exchange(String input) {
			String[] p = input.split("/");
			pos0 = Integer.parseInt(p[0]);
			pos1 = Integer.parseInt(p[1]);
		}

		@Override
		public void apply(char[] programs) {
			char tmp = programs[pos0];
			programs[pos0] = programs[pos1];
			programs[pos1] = tmp;
		}

		private final int pos0, pos1;
	}

	private static class Partner implements Move {

		Partner(String input) {
			String[] p = input.split("/");
			program0 = p[0].charAt(0);
			program1 = p[1].charAt(0);
		}

		@Override
		public void apply(char[] programs) {
			int i0 = indexOf(programs, program0);
			int i1 = indexOf(programs, program1);
			char tmp = programs[i0];
			programs[i0] = programs[i1];
			programs[i1] = tmp;
		}

		private int indexOf(char[] programs, char name) {
			for (int i = 0; i < programs.length; i++) {
				if (programs[i] == name) {
					return i;
				}
			}
			throw new IllegalArgumentException();
		}

		private final char program0, program1;
	}

	private static Move parse(String input) {

		char op = input.charAt(0);
		String params = input.substring(1);

		switch (op) {
			case 's':
				return new Spin(params);

			case 'x':
				return new Exchange(params);

			case 'p':
				return new Partner(params);
		}

		throw new IllegalArgumentException();
	}

	public static void main(String[] args) throws IOException {

		// parse input
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day16.class.getResourceAsStream("/Day16.txt")));

		String line = reader.readLine();
		String[] inputs = line.split(",");

		List<Move> moves = new ArrayList<>(inputs.length);

		for (String s : inputs) {
			moves.add(parse(s));
		}

		// init
		char[] programs = new char[16];
		for (int i = 0; i < 16; i++) {
			programs[i] = (char) ('a' + i);
		}

		// part 1
		String first;
		{
			for (Move move : moves) {
				move.apply(programs);
			}

			first = String.valueOf(programs);
			System.out.println("order: " + first);
		}

		// part 2
		{
			int repeat = Integer.MAX_VALUE;
			int iterations = 1_000_000_000;

			for (int i = 1; i < iterations; i++) {

				for (Move move : moves) {
					move.apply(programs);
				}

				String result = String.valueOf(programs);

				if (result.equals(first)) {
					repeat = i;
					System.out.println("pattern found at index: " + repeat);
					break;
				}
			}

			int remain = iterations % repeat;

			for (int i = 1; i < remain; i++) {
				for (Move move : moves) {
					move.apply(programs);
				}
			}

			System.out.println("order after one billion: " + String.valueOf(programs));
		}
	}

}
