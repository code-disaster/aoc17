package com.codedisaster.aoc;

import java.io.*;
import java.util.*;

public class Day08 {

	@FunctionalInterface
	interface CmpFn {
		boolean test(int vReg, int vCmp);
	}

	private enum Cmp {

		EQ("==", ((vReg, vCmp) -> vReg == vCmp)),
		NEQ("!=", ((vReg, vCmp) -> vReg != vCmp)),
		LESS("<", ((vReg, vCmp) -> vReg < vCmp)),
		GREATER(">", ((vReg, vCmp) -> vReg > vCmp)),
		LEQ("<=", ((vReg, vCmp) -> vReg <= vCmp)),
		GEQ(">=", ((vReg, vCmp) -> vReg >= vCmp));

		private final String sig;
		private final CmpFn test;

		Cmp(String sig, CmpFn test) {
			this.sig = sig;
			this.test = test;
		}

		boolean test(int vReg, int vCmp) {
			return test.test(vReg, vCmp);
		}

		static Cmp bySig(String sig) throws IOException {
			for (Cmp cmp : values()) {
				if (cmp.sig.equals(sig)) {
					return cmp;
				}
			}
			throw new IOException();
		}
	}

	private static class Op {

		final String reg;
		final boolean add;
		final int regValue;

		final String cmpReg;
		final Cmp cmp;
		final int cmpValue;

		Op(String line) throws IOException {

			String[] tokens = line.split("\\s+");

			reg = tokens[0];
			add = tokens[1].equals("inc");
			regValue = Integer.parseInt(tokens[2]);
			// "if"
			cmpReg = tokens[4];
			cmp = Cmp.bySig(tokens[5]);
			cmpValue = Integer.parseInt(tokens[6]);
		}
	}

	private static Map<String, Integer> registers = new HashMap<>();
	private static List<Op> ops = new ArrayList<>();

	private static int largest() {
		int max = Integer.MIN_VALUE;
		for (Map.Entry<String, Integer> reg : registers.entrySet()) {
			max = Math.max(max, reg.getValue());
		}
		return max;
	}

	public static void main(String[] args) throws IOException {

		// parse input
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day02.class.getResourceAsStream("/Day08.txt")));

		for (; ; ) {

			String line = reader.readLine();

			if (line == null || line.isEmpty()) {
				break;
			}

			Op op = new Op(line);
			ops.add(op);
			registers.put(op.reg, 0);
		}

		// part 1 & 2
		{
			int anyMax = Integer.MIN_VALUE;

			for (Op op : ops) {

				int vIn = registers.get(op.reg);
				int vOut = vIn + (op.add ? op.regValue : -op.regValue);

				int vCmp = registers.get(op.cmpReg);
				boolean cond = op.cmp.test(vCmp, op.cmpValue);

				if (cond) {
					registers.put(op.reg, vOut);
				}

				anyMax = Math.max(anyMax, largest());
			}

			System.out.println("largest: " + largest());
			System.out.println("any largest: " + anyMax);
		}
	}

}
