package com.codedisaster.aoc;

import java.io.*;
import java.util.*;

public class Day07 {

	private static class Program {

		Program(String input) {

			String[] p = input.split("\\s+");

			name = p[0];
			weight = Integer.parseInt(p[1].substring(1, p[1].length() - 1));

			if (p.length > 3) {
				childNames = Arrays.copyOfRange(p, 3, p.length);
				for (int i = 0; i < childNames.length; i++) {
					childNames[i] = childNames[i].replace(",", "");
				}
			} else {
				childNames = new String[] {};
			}

			childNodes = new Program[childNames.length];
		}

		int getWeight() {
			int w = weight;
			for (Program node : childNodes) {
				w += node.getWeight();
			}
			return w;
		}

		boolean balanced() {
			if (childNodes.length == 0) {
				return true;
			}
			int w = childNodes[0].getWeight();
			for (int i = 1; i < childNodes.length; i++) {
				if (w != childNodes[i].getWeight()) {
					return false;
				}
			}
			return true;
		}

		int getBalancedWeight() {

			int wb = -1;

			for (Program node : childNodes) {

				int w = node.getWeight();

				if (wb == -1) {
					wb = w;
				} else if (wb == w) {
					break;
				}
			}

			for (Program node : childNodes) {

				int w = node.getWeight();

				if (w != wb) {
					int d = w - wb;
					wb = node.weight - d;
					break;
				}
			}

			return wb;
		}

		final String name;
		final int weight;

		final String[] childNames;
		final Program[] childNodes;
	}

	public static void main(String[] args) throws IOException {

		// parse input
		Map<String, Program> programs = new HashMap<>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day02.class.getResourceAsStream("/Day07.txt")));

		for (; ; ) {

			String line = reader.readLine();

			if (line == null || line.isEmpty()) {
				break;
			}

			Program program = new Program(line);
			programs.put(program.name, program);
		}

		// build tree
		for (Program program : programs.values()) {
			for (int i = 0; i < program.childNames.length; i++) {
				program.childNodes[i] = programs.get(program.childNames[i]);
			}
		}

		String root;

		// part 1
		{
			Set<String> set = new HashSet<>(programs.keySet());

			for (Program program : programs.values()) {
				for (String name : program.childNames) {
					set.remove(name);
				}
			}

			root = set.iterator().next();
			System.out.println("root: " + root);
		}

		// part 2
		{
			Set<String> set = new HashSet<>(programs.keySet());

			// remove balanced
			for (Program program : programs.values()) {
				if (program.balanced()) {
					set.remove(program.name);
				}
			}

			// remove root(s)
			while (set.size() > 1) {

				Program r = programs.get(root);

				for (String name : r.childNames) {
					if (set.contains(name)) {
						set.remove(root);
						root = name;
						break;
					}
				}
			}

			// find child not matching
			Program p = programs.get(set.iterator().next());

			/*System.out.print(p.name + " (" + p.weight + ", " + p.getWeight() + ") -> ");
			for (Program node : p.childNodes) {
				System.out.print(node.name + "(" + node.weight + ", " + node.getWeight() + "), ");
			}
			System.out.println();*/

			System.out.println("balanced weight: " + p.getBalancedWeight());
		}
	}

}
