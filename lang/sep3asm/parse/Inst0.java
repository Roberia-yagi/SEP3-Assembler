package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import lang.sep3asm.instruction.Sep3Instruction;

public class Inst0 extends Sep3asmParseRule {
	// inst0 ::= INST0 
	private Sep3asmToken inst;
	Sep3Instruction sep3inst;

	public Inst0(Sep3asmParseContext ctx) {
	}

    public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INST0;
    }
    
	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		inst = ct.getCurrentToken(ctx);
		ct.getNextToken(ctx);
	}

	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}

	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
	}
}

