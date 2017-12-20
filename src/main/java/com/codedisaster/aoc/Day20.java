package com.codedisaster.aoc;

import java.io.*;
import java.util.*;

public class Day20 {

	private static class Vector {

		String parse(String input) {

			int s = input.indexOf('<');
			int e = input.indexOf('>');

			String[] values = input.substring(s + 1, e).split(",");

			x = Long.parseLong(values[0]);
			y = Long.parseLong(values[1]);
			z = Long.parseLong(values[2]);

			return input.substring(e + 1);
		}

		void add(Vector v) {
			x += v.x;
			y += v.y;
			z += v.z;
		}

		long distance() {
			return Math.abs(x) + Math.abs(y) + Math.abs(z);
		}

		boolean collide(Vector v) {
			return x == v.x && y == v.y && z == v.z;
		}

		long x, y, z;
	}

	private static class Particle {

		Particle(String input) {
			input = p.parse(input);
			input = v.parse(input);
			a.parse(input);
		}

		void update() {
			v.add(a);
			p.add(v);
		}

		long distance() {
			return p.distance();
		}

		boolean collide(Particle other) {
			return p.collide(other.p);
		}

		final Vector p = new Vector();
		final Vector v = new Vector();
		final Vector a = new Vector();

		boolean active = true;
	}

	private static List<Particle> particles = new ArrayList<>();

	public static void main(String[] args) throws IOException {

		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					Day20.class.getResourceAsStream("/Day20.txt")));

			for (; ; ) {

				String line = reader.readLine();

				if (line == null || line.isEmpty()) {
					break;
				}

				particles.add(new Particle(line));
			}
		}

		{
			int closestIndex = -1;
			int closestIndexN = 0;

			int count = particles.size();
			int countActive = 0;

			while (closestIndexN < 1000) {

				long minD = Long.MAX_VALUE;
				int minI = -1;

				for (Particle particle : particles) {
					particle.update();
				}

				for (int i = 0; i < count; i++) {

					Particle particle = particles.get(i);

					if (!particle.active) {
						continue;
					}

					for (int j = i + 1; j < count; j++) {

						Particle other = particles.get(j);

						if (!other.active) {
							continue;
						}

						if (particle.collide(other)) {
							particle.active = false;
							other.active = false;
						}
					}
				}

				countActive = 0;

				for (int i = 0; i < count; i++) {

					Particle particle = particles.get(i);

					if (particle.active) {
						countActive++;
					}

					long d = particle.distance();

					if (minD > d) {
						minD = d;
						minI = i;
					}
				}

				if (closestIndex != minI) {
					closestIndex = minI;
					closestIndexN = 1;
				} else {
					closestIndexN++;
				}
			}

			System.out.println("stay closest: " + closestIndex);
			System.out.println("still active: " + countActive);
		}
	}

}
