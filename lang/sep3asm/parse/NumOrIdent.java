package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class NumOrIdent extends Sep3asmParseRule {

    boolean minus = false;
    Sep3asmToken noi;

    public NumOrIdent(Sep3asmParseContext ctx) {
    }

    public static boolean isFirst(Sep3asmToken tk) {
        int type = tk.getType();
        return type == Sep3asmToken.TK_NUM || type == Sep3asmToken.TK_IDENT || type == Sep3asmToken.TK_MINUS;
    }

    @Override
    public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
        Sep3asmTokenizer ct = ctx.getTokenizer();
        Sep3asmToken tk = ct.getCurrentToken(ctx);
        // TODO: 消す
        System.out.println(tk.toExplainString());
        if (tk.getType() == Sep3asmToken.TK_MINUS) {
            minus = true;
            tk = ct.getNextToken(ctx);
        }
        if (tk.getType() == Sep3asmToken.TK_NUM || tk.getType() == Sep3asmToken.TK_IDENT) {
            // TODO: 消す
            System.out.println(tk.toExplainString());
            noi = tk;
            tk = ct.getNextToken(ctx);
            // TODO: 消す
            System.out.println("NumOrIndentの最奥部");
            System.out.println(tk.toExplainString());
        } else {
            ctx.fatalError(tk.toExplainString() + "数字か名前が続きます");
        }
    }

    public void pass1(Sep3asmParseContext pcx) throws FatalErrorException {
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }
}
