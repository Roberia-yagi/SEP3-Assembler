package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import lang.sep3asm.instruction.Sep3Instruction;

public class Inst2 extends Sep3asmParseRule {
	private Sep3asmToken inst;
	private Operand op1, op2;
	Sep3Instruction sep3inst;

	public Inst2(Sep3asmParseContext ctx) {
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INST2;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		inst = ct.getCurrentToken(ctx);
		// TODO: 消す
		System.out.println("2オペランドの命令");
		System.out.println(inst.toExplainString());
		Sep3asmToken tk = ct.getNextToken(ctx);
		if (Operand.isFirst(tk)) {
			// TODO: 消す
			System.out.println("2オペランドのFROM");
			System.out.println(tk.toExplainString());
			op1 = new Operand(ctx);
			op1.parse(ctx);
			tk = ct.getCurrentToken(ctx);
			// TODO: 消す
			System.out.println("2オペランドのFROMのあと");
			System.out.println(tk.toExplainString());
			if (tk.getType() == Sep3asmToken.TK_COMMA) {
				tk = ct.getNextToken(ctx);
			} else {
				ctx.warning(tk.toExplainString() + ",が抜けていますので補いました");
			}
			// TODO: 消す
			System.out.println("2オペランドのTO");
			System.out.println(tk.toExplainString());
			if (Operand.isFirst(tk)) {
				op2 = new Operand(ctx);
				op2.parse(ctx);
			} else {
				ctx.warning(tk.toExplainString() + "オペランドが来ます");
			}
		} else {
			ctx.fatalError(tk.toExplainString() + "オペランドが来ます");
		}
	}

	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		sep3inst = ctx.getTokenizer().getInstruction(inst.getText(), ctx);
		if (op1 != null) {
			op1.pass1(ctx);
			op1.limit(sep3inst.getOp1Info(), ctx, inst, Operand.FROM, "fromオペランドとして");
			if (op1.needExtraWord()) {
				ctx.addLocationCounter(2);
			} else {
				ctx.addLocationCounter(1);
			}
		}
		if (op2 != null) {
			op2.pass1(ctx);
			op2.limit(sep3inst.getOp2Info(), ctx, inst, Operand.TO, "toオペランドとして");
		}
	}

	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		if (op1 != null) {
			op1.pass2(ctx);
		}
		if (op2 != null) {
			op2.pass2(ctx);
		}
		sep3inst.generate(ctx, op1, op2);
	}
}
