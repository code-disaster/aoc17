package com.codedisaster.aoc;

import java.io.*;

public class Day11 {

	@SuppressWarnings("unused")
	private enum Dir {
		N(0, -1),
		NE(1, -1),
		SE(1, 0),
		S(0, 1),
		SW(-1, 1),
		NW(-1, 0);

		final Axial d;

		Dir(int q, int r) {
			d = new Axial(q, r);
		}

		static Dir fromString(String s) {
			return Dir.valueOf(s.toUpperCase());
		}
	}

	private static class Axial {

		int q;
		int r;

		Axial(int q, int r) {
			this.q = q;
			this.r = r;
		}

		void add(Axial other) {
			this.q += other.q;
			this.r += other.r;
		}

		int distance(Axial other) {
			return (Math.abs(this.q - other.q)
					+ Math.abs(this.q + this.r - other.q - other.r)
					+ Math.abs(this.r - other.r)) / 2;
		}

		private static final Axial ZERO = new Axial(0, 0);
	}

	public static void main(String[] args) throws IOException {

		// parse input
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day11.class.getResourceAsStream("/Day11.txt")));

		String line = reader.readLine();
		final String[] steps = line.split(",");

		// follow input
		Axial pos = new Axial(0, 0);
		int maxDist = 0;

		for (String step : steps) {
			pos.add(Dir.fromString(step).d);
			maxDist = Math.max(maxDist, pos.distance(Axial.ZERO));
		}

		System.out.println("distance: " + pos.distance(Axial.ZERO));
		System.out.println("max distance: " + maxDist);
	}

}
