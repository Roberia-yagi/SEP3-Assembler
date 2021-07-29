package lang.sep3asm.instruction;

import lang.sep3asm.*;
import lang.sep3asm.parse.Operand;

public class RelativeJumpInstruction extends Sep3Instruction {
	public RelativeJumpInstruction(int opCode, int from, int to) {
		super(opCode, from, to);
	}

	public void generate(Sep3asmParseContext ctx, Operand op1, Operand op2) {
		int out = opCode | op1.to5bits() << 5;
		System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(out));
		if (op1.needExtraWord()) {
			ctx.addLocationCounter(1);
			int location = op1.getExtraWord();
			// TODO: 消す
			System.out.println("Label location");
			System.out.println(Integer.toHexString(location));
			System.out.println("Current location");
			System.out.println(Integer.toHexString(ctx.getLocationCounter() + 1));
			location -= ctx.getLocationCounter() + 1;
			location &= 0xFFFF;
			System.out.println(Integer.toHexString(ctx.getLocationCounter()) + ": " + Integer.toHexString(location));
		}
		ctx.addLocationCounter(1);
	}
}
