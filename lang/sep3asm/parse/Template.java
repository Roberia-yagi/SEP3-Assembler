package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class Template extends Sep3asmParseRule {


    public Template(Sep3asmParseContext ctx) {
    }

    public static boolean isFirst(Sep3asmToken tk) {
        // return CommentLine.isFirst(tk) || tk.getType() == Sep3asmToken.TK_EOF;
        return false;
    }

    @Override
    public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
        Sep3asmTokenizer ct = ctx.getTokenizer();
        Sep3asmToken tk = ct.getCurrentToken(ctx);
        while (Line.isFirst(tk)) {
            Sep3asmParseRule line = new Line(ctx);
            line.parse(ctx);
            tk = ct.getCurrentToken(ctx);
        }
        if (tk.getType() != Sep3asmToken.TK_EOF) {
            ctx.warning(tk.toExplainString() + "ファイルの終わりにゴミがあります");
        }
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }

}