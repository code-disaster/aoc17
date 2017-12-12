package com.codedisaster.aoc;

import java.io.*;
import java.util.*;

public class Day12 {

	private static class Program {

		Program(String input) {

			String[] p = input.split("\\s+");

			id = Integer.parseInt(p[0]);

			pipes = new int[Math.max(0, p.length - 2)];
			for (int i = 0; i < p.length - 2; i++) {
				String pid = p[2 + i].replace(",", "");
				pipes[i] = Integer.parseInt(pid);
			}
		}

		void collect(Map<Integer, Program> map, Set<Integer> set) {

			for (int pipe : pipes) {

				if (set.contains(pipe)) {
					continue;
				}

				set.add(pipe);
				map.get(pipe).collect(map, set);
			}
		}

		final int id;
		final int[] pipes;
	}

	private static final Map<Integer, Program> programs = new HashMap<>();

	public static void main(String[] args) throws IOException {

		// parse input
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day12.class.getResourceAsStream("/Day12.txt")));

		for (; ; ) {

			String line = reader.readLine();

			if (line == null || line.isEmpty()) {
				break;
			}

			Program program = new Program(line);
			programs.put(program.id, program);
		}

		// part 1
		{
			Set<Integer> set = new HashSet<>();
			int numConnectedTo0 = 0;

			for (Map.Entry<Integer, Program> entry : programs.entrySet()) {

				set.clear();
				entry.getValue().collect(programs, set);

				if (set.contains(0)) {
					numConnectedTo0++;
				}
			}

			System.out.println("programs connected to ID 0: " + numConnectedTo0);
		}

		// part 2
		{
			Set<Integer> set = new HashSet<>();
			Set<Integer> allSet = new HashSet<>(programs.keySet());
			int numGroups = 0;

			while (!allSet.isEmpty()) {

				int pid = allSet.iterator().next();
				allSet.remove(pid);

				set.clear();
				programs.get(pid).collect(programs, set);

				for (Integer gid : set) {
					allSet.remove(gid);
				}

				numGroups++;
			}

			System.out.println("groups: " + numGroups);
		}

	}

}
