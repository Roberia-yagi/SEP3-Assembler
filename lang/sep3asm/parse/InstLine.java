package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class InstLine extends Sep3asmParseRule {

    Inst0 inst0;
    Inst1a inst1a;
    Inst1b inst1b;
    Inst2 inst2;
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
            inst0 = new Inst0(ctx);
            inst0.parse(ctx);
        } else if (Inst1a.isFirst(tk)) {
            inst1a = new Inst1a(ctx);
            inst1a.parse(ctx);
        } else if (Inst1b.isFirst(tk)) {
            inst1b = new Inst1b(ctx);
            inst1b.parse(ctx);
        } else if (Inst2.isFirst(tk)) {
            inst2 = new Inst2(ctx);
            inst2.parse(ctx);
            // TODO: なにかする？
        }
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }

}

