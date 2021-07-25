package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class PseudoInst extends Sep3asmParseRule {

    NumOrIdent noi;
    public PseudoInst(Sep3asmParseContext ctx) {
    }

    public static boolean isFirst(Sep3asmToken tk) {
       return tk.getType() == Sep3asmToken.TK_EQUAL;
    }

    @Override
    public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
        Sep3asmTokenizer ct = ctx.getTokenizer();
        Sep3asmToken tk = ct.getCurrentToken(ctx);
        // TODO: 消す
        System.out.println(tk.toExplainString());
        if (NumOrIdent.isFirst(tk)) {
            noi = new NumOrIdent(ctx);
            noi.parse(ctx);
        }
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }

}