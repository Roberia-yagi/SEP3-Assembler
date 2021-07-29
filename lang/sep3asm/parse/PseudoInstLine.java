package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import lang.sep3asm.instruction.Sep3Instruction;

public class PseudoInstLine extends Sep3asmParseRule {

    private Sep3asmToken inst;
    int type;
    NumOrIdentList noilist;
    NumOrIdent noi;
    Sep3Instruction sep3inst;

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
        type = tk.getType();
        if (type == Sep3asmToken.TK_DOTWD) {
            inst = ct.getCurrentToken(ctx);
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
                ctx.fatalError(tk.toExplainString() + "数か名前が来ます");
            }
        } else if (type == Sep3asmToken.TK_END) {
            // TODO: 消す
            System.out.println("END前");
            System.out.println(tk.toExplainString());
            tk = ct.getNextToken(ctx);
            // TODO: 消す
            System.out.println("END後");
            System.out.println(tk.toExplainString());

        } else {
            ctx.fatalError(tk.toExplainString() + ".word .blkw . .endのいずれかが来ます");
        }
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
        // TODO: 消す
        System.out.println("PseudoInstLine pass1");
        if (type == Sep3asmToken.TK_DOTWD) {
            for (int i = 0; i < noilist.list.size(); i++) {
                ctx.addLocationCounter(1);
            }
        } else if (type == Sep3asmToken.TK_BLKW) {
            for (int i = 0; i < noilist.list.size(); i++) {
                NumOrIdent noi = noilist.list.get(i);
                if (noi.noi.getType() == Sep3asmToken.TK_NUM) {
                    int j = noi.noi.getIntValue();
                    if (noi.minus) {
                        ctx.fatalError("blkwに負の値は使えません");
                    }
                    ctx.addLocationCounter(j);
                } else if (noi.noi.getType() == Sep3asmToken.TK_IDENT) {
                    // TODO: 値が後に決まるラベル名がblkwに入っていた場合どのようにロケーションカウントを増やせばいいのかわからないから飛ばす
                    // String label = noi.noi.getText();
                    // e.setLabel(label);
                    // ctx.getSymbolTable().register(ident.getText(), e);
                    // ctx.getSymbolTable().resolve(label);
                }
            }
        } else if (type == Sep3asmToken.TK_DOT) {
            ctx.setLocationCounter(noi.noi.getIntValue());
        }
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
        if (type == Sep3asmToken.TK_DOTWD) {
            for (int i = 0; i < noilist.list.size(); i++) {
                sep3inst = pcx.getTokenizer().getInstruction(inst.getText(), pcx);
                if (noilist.list.get(i).noi.getType() == Sep3asmToken.TK_IDENT) {
                    pcx.getSymbolTable().resolve(noilist.list.get(i).noi.getText());
                }
            }
        } else if (type == Sep3asmToken.TK_BLKW) {
            for (int i = 0; i < noilist.list.size(); i++) {
                NumOrIdent noi = noilist.list.get(i);
                if (noi.noi.getType() == Sep3asmToken.TK_NUM) {
                    int j = noi.noi.getIntValue();
                    pcx.addLocationCounter(j);
                } else if (noi.noi.getType() == Sep3asmToken.TK_IDENT) {
                    // TODO: 値が後に決まるラベル名がblkwに入っていた場合どのようにロケーションカウントを増やせばいいのかわからないから飛ばす
                    pcx.addLocationCounter(pcx.getSymbolTable().resolve(noi.noi.getText()));
                }
            }
        } else if (type == Sep3asmToken.TK_DOT) {
            pcx.setLocationCounter(noi.noi.getIntValue());
        }

    }
}
