package com.codedisaster.aoc;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class Day18 {

	private enum OpCode {
		snd(
				instr -> {
					sndPlayFreq = registers.get(instr.r).value;
					return 1;
				}),
		set(
				instr -> {
					Register r = registers.get(instr.r);
					if (Instruction.isReg(instr.v)) {
						r.value = registers.get(instr.v).value;
					} else {
						r.value = Integer.parseInt(instr.v);
					}
					return 1;
				}),
		add(
				instr -> {
					Register r = registers.get(instr.r);
					if (Instruction.isReg(instr.v)) {
						r.value += registers.get(instr.v).value;
					} else {
						r.value += Integer.parseInt(instr.v);
					}
					return 1;
				}),
		mul(
				instr -> {
					Register r = registers.get(instr.r);
					if (Instruction.isReg(instr.v)) {
						r.value *= registers.get(instr.v).value;
					} else {
						r.value *= Integer.parseInt(instr.v);
					}
					return 1;
				}),
		mod(
				instr -> {
					Register r = registers.get(instr.r);
					if (Instruction.isReg(instr.v)) {
						r.value = r.value % registers.get(instr.v).value;
					} else {
						r.value = r.value % Integer.parseInt(instr.v);
					}
					return 1;
				}),
		rcv(
				instr -> {
					Register r = registers.get(instr.r);
					if (r.value != 0) {
						r.value = sndPlayFreq;
						System.out.println("rcv: " + r.value);
					}
					return 1;
				}),
		jgz(
				instr -> {
					long cmp;
					long offs = 1;
					if (Instruction.isReg(instr.r)) {
						cmp = registers.get(instr.r).value;
					} else {
						cmp = Integer.parseInt(instr.r);
					}
					if (cmp > 0) {
						if (Instruction.isReg(instr.v)) {
							offs = registers.get(instr.v).value;
						} else {
							offs = Integer.parseInt(instr.v);
						}
					}
					return (int) offs;
				});

		private final Function<Instruction, Integer> fn;

		OpCode(Function<Instruction, Integer> fn) {
			this.fn = fn;
		}

		int run(Instruction instr) {
			return fn.apply(instr);
		}
	}

	private static class Instruction {

		final OpCode op;
		final String r;
		final String v;

		Instruction(String[] tokens) {
			op = OpCode.valueOf(tokens[0]);
			r = tokens[1];
			v = tokens.length > 2 ? tokens[2] : null;
		}

		static boolean isReg(String token) {
			return registers.containsKey(token);
		}
	}

	private static class Register {

		char name;
		long value;

	}

	private static List<Instruction> program = new ArrayList<>();
	private static Map<String, Register> registers = new HashMap<>();

	private static long sndPlayFreq;

	public static void main(String[] args) throws IOException {

		// parse input
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Day18.class.getResourceAsStream("/Day18.txt")));

		for (; ; ) {

			String line = reader.readLine();

			if (line == null || line.isEmpty()) {
				break;
			}

			String[] inputs = line.split(" ");
			program.add(new Instruction(inputs));
		}

		// registers
		registers.put("a", new Register());
		registers.put("b", new Register());
		registers.put("f", new Register());
		registers.put("i", new Register());
		registers.put("p", new Register());

		// part 1
		{
			int ip = 0;

			for (;;) {

				Instruction instr = program.get(ip);
				int jmp = instr.op.run(instr);

				ip += jmp;

				if (ip < 0 || ip >= program.size()) {
					break;
				}
			}
		}
	}

}
