package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class PseudoInstLine extends Sep3asmParseRule {

    NumOrIdentList noilist;
    NumOrIdent noi;

    // TODO: 定数作ってどの分岐をしたかを持っておくべき
    public PseudoInstLine(Sep3asmParseContext ctx) {
    }

    public static boolean isFirst(Sep3asmToken tk) {
        int type = tk.getType();
        return type == Sep3asmToken.TK_DOTWD || type == Sep3asmToken.TK_BLKW || type == Sep3asmToken.TK_DOT
                || type == Sep3asmToken.TK_END;
    }

    @Override
    public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
        Sep3asmTokenizer ct = ctx.getTokenizer();
        Sep3asmToken tk = ct.getCurrentToken(ctx);
        // TODO: 消す
        System.out.println(tk.toExplainString());
        int type = tk.getType();
        if (type == Sep3asmToken.TK_DOTWD) {
            tk = ct.getNextToken(ctx);
            noilist = new NumOrIdentList(ctx);
            noilist.parse(ctx);
        } else if (type == Sep3asmToken.TK_BLKW) {
            tk = ct.getNextToken(ctx);
            noilist = new NumOrIdentList(ctx);
            noilist.parse(ctx);
        } else if (type == Sep3asmToken.TK_DOT) {
            tk = ct.getNextToken(ctx);
            // TODO: 消す
            System.out.println(tk.toExplainString());
            if (tk.getType() == Sep3asmToken.TK_EQUAL) {
                tk = ct.getNextToken(ctx);
                // TODO: 消す
                System.out.println(tk.toExplainString());
                noi = new NumOrIdent(ctx);
                noi.parse(ctx);
                tk = ct.getCurrentToken(ctx);
                // TODO: 消す
                System.out.println("PseudoInstLineの最後");
                System.out.println(tk.toExplainString());
            } else {
                ctx.warning(tk.toExplainString() + "数か名前が来ます");
            }
        } else if (type == Sep3asmToken.TK_END) {
            //TODO: 消す
            System.out.println("END前");
            System.out.println(tk.toExplainString());
            tk = ct.getNextToken(ctx);
            //TODO: 消す
            System.out.println("END後");
            System.out.println(tk.toExplainString());

        } else {
            ctx.warning(tk.toExplainString() + ".word .blkw . .endのいずれかが来ます");
        }
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }

}
