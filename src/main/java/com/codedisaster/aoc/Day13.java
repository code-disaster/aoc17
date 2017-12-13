package com.codedisaster.aoc;

import java.io.*;
import java.util.*;

public class Day13 {

	private static class Layer {

		Layer(String input) {

			String[] p = input.split("\\s+");

			depth = Integer.parseInt(p[0].replace(":", ""));
			range = Integer.parseInt(p[1]);

			reset();
		}

		void reset() {
			scanner = 0;
			direction = 1;
		}

		void tick() {

			if (direction == 1) {
				++scanner;
				if (scanner == range - 1) {
					direction = -direction;
				}
			} else {
				--scanner;
				if (scanner == 0) {
					direction = -direction;
				}
			}
		}

		boolean caught() {
			return scanner == 0;
		}

		int score() {
			return depth * range;
		}

		final int depth;
		final int range;

		int scanner;
		int direction;
	}

	private static class Packet {

		Packet(int delay) {
			this.delay = delay;
		}

		void tick() {
			depth++;
		}

		boolean done() {
			return depth >= maxDepth;
		}

		final int delay;

		int depth = 0;
		boolean caught = false;
	}

	private static final Map<Integer, Layer> layers = new HashMap<>();
	private static final List<Packet> packets = new ArrayList<>();

	private static int maxDepth = 0;

	private static int send() {

		layers.forEach((d, l) -> l.reset());

		int score = 0;

		for (int step = 0; step <= maxDepth; step++) {

			Layer layer = layers.get(step);

			if (layer != null && layer.caught()) {
				score += layer.score();
			}

			layers.forEach((d, l) -> l.tick());
		}

		return score;
	}

	private static void wall(Packet packet) {

		Layer layer = layers.get(packet.depth);

		if (layer != null && layer.caught()) {
			packet.caught = true;
		}
	}

	public static void main(String[] args) throws IOException {

		// parse input
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day13.class.getResourceAsStream("/Day13.txt")));

		for (; ; ) {

			String line = reader.readLine();

			if (line == null || line.isEmpty()) {
				break;
			}

			Layer layer = new Layer(line);
			layers.put(layer.depth, layer);

			maxDepth = Math.max(layer.depth, maxDepth);
		}

		// part 1
		{
			int score = send();
			System.out.println("score: " + score);
		}

		// part 2
		{
			int delay = 10;
			Packet result = null;

			layers.forEach((d, l) -> l.reset());
			for (int i = 0; i < delay; i++) {
				layers.forEach((d, l) -> l.tick());
			}

			while (result == null) {

				packets.add(new Packet(delay));

				for (int i = packets.size() - 1; i >= 0; i--) {

					Packet p = packets.get(i);

					wall(p);

					if (p.caught) {
						packets.remove(p);
						continue;
					}

					if (p.done()) {
						result = p;
						break;
					}

					p.tick();
				}

				layers.forEach((d, l) -> l.tick());

				delay++;
			}

			System.out.println("delay: " + result.delay);
		}
	}

}
