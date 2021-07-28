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
            // TODO: 消す
            System.out.println("疑似命令 PseudoInstに入った");
            tk = ct.getNextToken(ctx);
            inst = new PseudoInst(ctx);
            inst.parse(ctx);
        } else {
            ctx.fatalError(tk.toExplainString() + ": または = が来ます");
        }
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
        LabelEntry e = new LabelEntry();
        // ラベル定義であった場合
        if (def != null) {
            e.setInteger(ctx.getLocationCounter());
            ctx.getSymbolTable().register(ident.getText(), e);
        } else if (inst != null) {
            NumOrIdent noi = inst.noi;
            if (noi.noi.getType() == Sep3asmToken.TK_NUM) {
                int i = noi.noi.getIntValue();
                if (noi.minus) {
                    i *= -1;
                }
                e.setInteger(i);
                ctx.getSymbolTable().register(ident.getText(), e);
                ctx.getSymbolTable().resolve(ident.getText());
            } else if (noi.noi.getType() == Sep3asmToken.TK_IDENT) {
                String label = noi.noi.getText();
                e.setLabel(label);
                ctx.getSymbolTable().register(ident.getText(), e);
                ctx.getSymbolTable().resolve(label);
            }

        }
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }

}
