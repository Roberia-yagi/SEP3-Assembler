package lang.sep3asm.instruction;

import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.parse.Operand;

public class TwoOperandInstruction extends Sep3Instruction {
	public TwoOperandInstruction(int opCode, int from, int to) {
		super(opCode, from, to);
	}
	public void generate(Sep3asmParseContext ctx, Operand op1, Operand op2) {
		int out = opCode | op1.to5bits() << 5 | op2.to5bits();
		System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(out));
		ctx.addLocationCounter(1);
		if (op1.needExtraWord()) {
			out = op1.getExtraWord();
			System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(out));
			ctx.addLocationCounter(1);
		}
	}
}
