package com.codedisaster.aoc;

public class Day03 {

	private enum Direction {
		RIGHT(1, 0),
		UP(0, 1),
		LEFT(-1, 0),
		DOWN(0, -1);

		final int dx, dy;
		private static final Direction[] values = values();

		Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		Direction next() {
			return values[(ordinal() + 1) % values.length];
		}
	}

	private static final int INPUT = 325489;
	private static int[][] grid;

	private static void setGrid(int x, int y, int value) {
		int halfSize = (grid.length - 1) / 2;
		grid[x + halfSize][y + halfSize] = value;
	}

	private static int getGrid(int x, int y) {
		int size = grid.length;
		int halfSize = (size - 1) / 2;
		x += halfSize;
		y += halfSize;
		if (x < 0 || x >= size || y < 0 || y >= size) {
			return 0;
		}
		return grid[x][y];
	}

	private interface Listener {
		boolean test(int x, int y, int step);
		void apply(int x, int y);
		void accept(int x, int y, int radius);
	}

	private static void walk(Listener listener) {

		int step = 1;
		int radius = 0;

		int x = 0, y = 0;
		Direction direction = Direction.RIGHT;

		while (listener.test(x, y, step)) {

			if (direction.dx != 0) {
				x += direction.dx;
				if (Math.abs(x) > radius) {
					direction = direction.next();
				}
			} else {
				y += direction.dy;
				if (Math.abs(y) > radius) {
					direction = direction.next();
					if (direction == Direction.RIGHT) {
						radius++;
					}
				}
			}

			listener.apply(x, y);

			step++;
		}

		listener.accept(x, y, radius);
	}

	public static void main(String[] args) {

		// part 1
		{
			walk(new Listener() {

				@Override
				public boolean test(int x, int y, int step) {
					return step < INPUT;
				}

				@Override
				public void apply(int x, int y) {

				}

				@Override
				public void accept(int x, int y, int radius) {

					int distance = Math.abs(x) + Math.abs(y);
					System.out.println("distance: " + distance);

					int gridSize = radius * 2 + 1;
					grid = new int[gridSize][gridSize];
				}
			});
		}

		// part 2
		{
			setGrid(0, 0, 1);

			walk(new Listener() {

				@Override
				public boolean test(int x, int y, int step) {
					return getGrid(x, y) <= INPUT;
				}

				@Override
				public void apply(int x, int y) {
					int sum = 0;
					for (int yy = y - 1; yy <= y + 1; yy++) {
						for (int xx = x - 1; xx <= x + 1; xx++) {
							sum += getGrid(xx, yy);
						}
					}
					setGrid(x, y, sum);
				}

				@Override
				public void accept(int x, int y, int radius) {
					int value = getGrid(x, y);
					System.out.println("value: " + value);
				}
			});
		}

	}

}
