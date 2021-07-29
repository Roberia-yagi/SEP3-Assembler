package lang.sep3asm.instruction;

import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.parse.Operand;

public class OneOperandInstruction extends Sep3Instruction {
	public OneOperandInstruction(int opCode, int from, int to) {
		super(opCode, from, to);
	}

	public void generate(Sep3asmParseContext ctx, Operand op1, Operand op2) {
		// TODO: 消す
		System.out.println("INST 1B GENERATE");
		if (op2 != null) {
			int out = opCode | op2.to5bits();
			System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(out));
			ctx.addLocationCounter(1);
			if (op2.needExtraWord()) {
				out = op2.getExtraWord();
				System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(out));
				ctx.addLocationCounter(1);
			}
		} else if (op1 != null) {
			int out = opCode | op1.to5bits() << 5;
			System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(out));
			ctx.addLocationCounter(1);
			if (op1.needExtraWord()) {
				out = op1.getExtraWord();
				System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(out));
				ctx.addLocationCounter(1);
			}

		}
	}
}
