package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import lang.sep3asm.instruction.Sep3Instruction;

public class Inst1b extends Sep3asmParseRule {
	private Sep3asmToken inst;
	private Operand op;
	Sep3Instruction sep3inst;

	public Inst1b(Sep3asmParseContext ctx) {
	}

    public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INST1b;
    }

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		inst = ct.getCurrentToken(ctx);
		Sep3asmToken tk = ct.getNextToken(ctx);
		if (Operand.isFirst(tk)) {
			op = new Operand(ctx);
			op.parse(ctx);
			tk = ct.getCurrentToken(ctx);
		} else {
			ctx.warning(tk.toExplainString() + "オペランドが来ます");
		}
	}

	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}

    public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
    }
}
