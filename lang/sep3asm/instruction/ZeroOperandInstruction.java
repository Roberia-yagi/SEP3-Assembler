package lang.sep3asm.instruction;

import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.parse.Operand;

public class ZeroOperandInstruction extends Sep3Instruction {
	public ZeroOperandInstruction(int opCode, int from, int to) {
		super(opCode, from, to);
	}

	public void generate(Sep3asmParseContext ctx, Operand op1, Operand op2) {
		int out = opCode;
		System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(out));
		ctx.addLocationCounter(1);
	}
}
