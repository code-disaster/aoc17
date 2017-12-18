package com.codedisaster.aoc;

import java.io.*;
import java.util.*;

public class Day18 {

	@FunctionalInterface
	interface OpCodeFn {
		int apply(Program p, Program q, Instruction instr);
	}

	private enum OpCode {
		snd(
				(program, other, instr) -> {
					long v = program.registers.get(instr.r).value;
					other.queue.add(v);
					program.sent++;
					if (program.id == 1) {
						System.out.println("program #" + program.id + " sent value #" + program.sent + ": " + v);
					}
					return 1;
				}),
		set(
				(program, other, instr) -> {
					Register r = program.registers.get(instr.r);
					if (program.isReg(instr.v)) {
						r.value = program.registers.get(instr.v).value;
					} else {
						r.value = Integer.parseInt(instr.v);
					}
					return 1;
				}),
		add(
				(program, other, instr) -> {
					Register r = program.registers.get(instr.r);
					if (program.isReg(instr.v)) {
						r.value += program.registers.get(instr.v).value;
					} else {
						r.value += Integer.parseInt(instr.v);
					}
					return 1;
				}),
		mul(
				(program, other, instr) -> {
					Register r = program.registers.get(instr.r);
					if (program.isReg(instr.v)) {
						r.value *= program.registers.get(instr.v).value;
					} else {
						r.value *= Integer.parseInt(instr.v);
					}
					return 1;
				}),
		mod(
				(program, other, instr) -> {
					Register r = program.registers.get(instr.r);
					if (program.isReg(instr.v)) {
						r.value = r.value % program.registers.get(instr.v).value;
					} else {
						r.value = r.value % Integer.parseInt(instr.v);
					}
					return 1;
				}),
		rcv(
				(program, other, instr) -> {
					if (program.queue.isEmpty()) {
						return 0;
					}
					Register r = program.registers.get(instr.r);
					r.value = program.queue.remove();
					return 1;
				}),
		jgz(
				(program, other, instr) -> {
					long cmp;
					long offs = 1;
					if (program.isReg(instr.r)) {
						cmp = program.registers.get(instr.r).value;
					} else {
						cmp = Integer.parseInt(instr.r);
					}
					if (cmp > 0) {
						if (program.isReg(instr.v)) {
							offs = program.registers.get(instr.v).value;
						} else {
							offs = Integer.parseInt(instr.v);
						}
					}
					return (int) offs;
				});

		private final OpCodeFn fn;

		OpCode(OpCodeFn fn) {
			this.fn = fn;
		}

		int run(Program program, Program other, Instruction instr) {
			return fn.apply(program, other, instr);
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
	}

	private static class Register {

		Register(int value) {
			this.value = value;
		}

		long value;
	}

	private static class Program {

		final int id;
		int ip;
		Map<String, Register> registers = new HashMap<>();

		Queue<Long> queue = new ArrayDeque<>();
		int sent = 0;

		Program(int id) {
			this.id = id;
			registers.put("a", new Register(0));
			registers.put("b", new Register(0));
			registers.put("f", new Register(0));
			registers.put("i", new Register(0));
			registers.put("p", new Register(id));
		}

		boolean tick(Program other) {

			Instruction instr = code.get(ip);
			int jmp = instr.op.run(this, other, instr);

			ip += jmp;

			if (ip < 0 || ip >= code.size()) {
				return false;
			}

			return true;
		}

		boolean isReg(String token) {
			return registers.containsKey(token);
		}
	}

	private static List<Instruction> code = new ArrayList<>();

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
			code.add(new Instruction(inputs));
		}

		// programs
		Program p0 = new Program(0);
		Program p1 = new Program(1);

		// part 1
		for (; ; ) {

			boolean b0 = p0.tick(p1);
			boolean b1 = p1.tick(p0);

			if (!b0 || !b1) {
				break;
			}
		}
	}

}
