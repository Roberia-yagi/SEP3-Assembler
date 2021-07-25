package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class NameFirstLine extends Sep3asmParseRule {

    Sep3asmToken ident;
    DefLabel def;
    PseudoInst inst;

    public NameFirstLine(Sep3asmParseContext ctx) {
    }

    public static boolean isFirst(Sep3asmToken tk) {
        return tk.getType() == Sep3asmToken.TK_IDENT;
    }

    @Override
    public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
        Sep3asmTokenizer ct = ctx.getTokenizer();
        Sep3asmToken tk = ct.getCurrentToken(ctx);
        ident = tk;
        tk = ct.getNextToken(ctx);
        if (DefLabel.isFirst(tk)) {
            def = new DefLabel(ctx);
            def.parse(ctx);
            tk = ct.getNextToken(ctx);
        } else if (PseudoInst.isFirst(tk)) {
            //TODO: 消す
            System.out.println("疑似命令 PseudoInstに入った");
            tk = ct.getNextToken(ctx);
            inst = new PseudoInst(ctx);
            inst.parse(ctx);
        } else {
            ctx.warning(tk.toExplainString() + ": または = が来ます");
        }
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }

}
