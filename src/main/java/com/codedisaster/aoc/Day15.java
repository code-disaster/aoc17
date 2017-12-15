package com.codedisaster.aoc;

public class Day15 {

	private static class Generator {

		Generator(long seed, long factor) {
			this.value = seed;
			this.factor = factor;
		}

		long next() {
			long n = value * factor;
			value = n % 2147483647;
			return value;
		}

		private long value;
		private final long factor;
	}

	public static void main(String[] args) {

		// part 1
		{
			Generator a = new Generator(289, 16807);
			Generator b = new Generator(629, 48271);

			int matches = 0;

			for (long i = 0; i < 40_000_000; i++) {

				long v0 = a.next();
				long v1 = b.next();

				if ((v0 & 0xffff) == (v1 & 0xffff)) {
					matches++;
				}
			}

			System.out.println("matches #1: " + matches);
		}

		// part 2
		{
			Generator a = new Generator(289, 16807);
			Generator b = new Generator(629, 48271);

			int matches = 0;

			for (long i = 0; i < 5_000_000; i++) {

				long v0, v1;

				do {
					v0 = a.next();
				} while ((v0 % 4) != 0);

				do {
					v1 = b.next();
				} while ((v1 % 8) != 0);

				if ((v0 & 0xffff) == (v1 & 0xffff)) {
					matches++;
				}
			}

			System.out.println("matches #2: " + matches);
		}
	}

}
