package lang.sep3asm.parse;

import java.util.ArrayList;

import lang.*;
import lang.sep3asm.*;

public class NumOrIdentList extends Sep3asmParseRule {

    ArrayList<NumOrIdent> list;

    public NumOrIdentList(Sep3asmParseContext ctx) {
        list = new ArrayList<NumOrIdent>();
    }

    public static boolean isFirst(Sep3asmToken tk) {
        return NumOrIdent.isFirst(tk);
    }

    @Override
    public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
        Sep3asmTokenizer ct = ctx.getTokenizer();
        Sep3asmToken tk = ct.getCurrentToken(ctx);
        NumOrIdent noi = new NumOrIdent(ctx);
        noi.parse(ctx);
        list.add(noi);
        tk = ct.getCurrentToken(ctx);
        while (tk.getType() == Sep3asmToken.TK_COMMA) {
            tk = ct.getNextToken(ctx);
            noi = new NumOrIdent(ctx);
            noi.parse(ctx);
            list.add(noi);
            tk = ct.getCurrentToken(ctx);
        }
    }

    public void pass1(Sep3asmParseContext pcx) throws FatalErrorException {
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }
}
