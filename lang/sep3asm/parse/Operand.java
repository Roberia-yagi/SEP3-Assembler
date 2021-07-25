package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class Operand extends Sep3asmParseRule {
	// operand ::= REG | LPAR REG RPAR | LPAR REG RPAR [PLUS] | MINUS LPAR REG RPAR
	// | SHARP numOrIdent | numOrIdent
	// TODO: とりあえず構文解析の段階ではオペランドは区別せず，意味解析でFとTでわけるのかな？
	public static final int REGISTER = 001;
	public static final int INDIRECT = 002;
	public static final int PREDEC = 004;
	public static final int POSTINC = 010;
	public static final int IMM = 020;
	public static final int LABEL = 040;
	Sep3asmToken register;
	int typeOfOperand;
	NumOrIdent noi;

	public Operand(Sep3asmParseContext ctx) {
	}

	public static boolean isFirst(Sep3asmToken tk) {
		int type = tk.getType();
		return type == Sep3asmToken.TK_REGISTOR || type == Sep3asmToken.TK_BRACKETa || type == Sep3asmToken.TK_BRACKETb
				|| type == Sep3asmToken.TK_SHARP || type == Sep3asmToken.TK_IDENT || type == Sep3asmToken.TK_MINUS;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		Sep3asmToken tk = ct.getCurrentToken(ctx);
		int type = tk.getType();
		if (type == Sep3asmToken.TK_REGISTOR) {
			register = tk;
			typeOfOperand = REGISTER;
			tk = ct.getNextToken(ctx);
		} else if (type == Sep3asmToken.TK_BRACKETa) {
			tk = ct.getNextToken(ctx);
			if (tk.getType() == Sep3asmToken.TK_REGISTOR) {
				tk = ct.getNextToken(ctx);
				if (tk.getType() == Sep3asmToken.TK_BRACKETb) {
					tk = ct.getNextToken(ctx);
					if (tk.getType() == Sep3asmToken.TK_PLUS) {
						typeOfOperand = POSTINC;
						tk = ct.getNextToken(ctx);
					} else if (tk.getType() == Sep3asmToken.TK_NL) {
						typeOfOperand = INDIRECT;
					}
				} else {
					ctx.warning(tk.toExplainString() + ")が来ます");
				}
			} else {
				ctx.warning(tk.toExplainString() + "レジスタ名が来ます");
			}
		} else if (type == Sep3asmToken.TK_MINUS) {
			tk = ct.getNextToken(ctx);
			//TODO: 消す
			System.out.println("マイナスの後");
			System.out.println(tk.toExplainString());
			if (tk.getType() == Sep3asmToken.TK_BRACKETa) {
				tk = ct.getNextToken(ctx);
				if (tk.getType() == Sep3asmToken.TK_REGISTOR) {
					tk = ct.getNextToken(ctx);
					if (tk.getType() == Sep3asmToken.TK_BRACKETb) {
						typeOfOperand = PREDEC;
						tk = ct.getNextToken(ctx);
					} else {
						ctx.warning(tk.toExplainString() + ")が来ます");
					}
				} else {
					ctx.warning(tk.toExplainString() + "レジスタ名が来ます");
				}
			} else {
				ctx.warning(tk.toExplainString() + "(が来ます");
			}
		} else if (type == Sep3asmToken.TK_SHARP) {
			tk = ct.getNextToken(ctx);
			//TODO: 消す
			System.out.println("#のあと");
			System.out.println(tk.toExplainString());
			if (NumOrIdent.isFirst(tk)) {
				if (tk.getType() == Sep3asmToken.TK_NUM) {
					typeOfOperand = IMM;
				} else if (tk.getType() == Sep3asmToken.TK_IDENT) {
					typeOfOperand = LABEL;
				}
				noi = new NumOrIdent(ctx);
				noi.parse(ctx);
			} else {
				ctx.warning(tk.toExplainString() + "数か名前が来ます");
			}
		} else if (type == Sep3asmToken.TK_IDENT) {
			noi = new NumOrIdent(ctx);
			noi.parse(ctx);
		} else {
			ctx.warning(tk.toExplainString() + "レジスタ名, (, -, #が来ます");
		}
	}

	private int fivebits;
	private boolean needExtraWord;
	private int extraWord;

	public boolean needExtraWord() {
		return needExtraWord;
	}

	public int to5bits() {
		return fivebits;
	}

	public int getExtraWord() {
		return extraWord;
	}

	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}

	public void limit(int info, Sep3asmParseContext ctx, Sep3asmToken inst, final String s) {
	}

	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
	}
}
