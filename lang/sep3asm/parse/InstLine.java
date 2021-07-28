package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class InstLine extends Sep3asmParseRule {

    Sep3asmParseRule inst;

    public InstLine(Sep3asmParseContext ctx) {
    }

    public static boolean isFirst(Sep3asmToken tk) {
        return Inst0.isFirst(tk) || Inst1a.isFirst(tk) || Inst1b.isFirst(tk) || Inst2.isFirst(tk);
    }

    @Override
    public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
        Sep3asmTokenizer ct = ctx.getTokenizer();
        Sep3asmToken tk = ct.getCurrentToken(ctx);
        if (Inst0.isFirst(tk)) {
            inst = new Inst0(ctx);
        } else if (Inst1a.isFirst(tk)) {
            inst = new Inst1a(ctx);
        } else if (Inst1b.isFirst(tk)) {
            inst = new Inst1b(ctx);
        } else if (Inst2.isFirst(tk)) {
            inst = new Inst2(ctx);
            // TODO: なにかする？
        }
        inst.parse(ctx);
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
        inst.pass1(ctx);
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }

}
