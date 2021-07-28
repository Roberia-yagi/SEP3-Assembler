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
	public static final int FROM = 001;
	public static final int TO = 002;
	Sep3asmToken register;
	int mode;
	NumOrIdent noi;

	public Operand(Sep3asmParseContext ctx) {
	}

	public static boolean isFirst(Sep3asmToken tk) {
		int type = tk.getType();
		return type == Sep3asmToken.TK_REGISTOR || type == Sep3asmToken.TK_BRACKETa || type == Sep3asmToken.TK_BRACKETb
				|| type == Sep3asmToken.TK_SHARP || type == Sep3asmToken.TK_MINUS || NumOrIdent.isFirst(tk);
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		Sep3asmToken tk = ct.getCurrentToken(ctx);
		int type = tk.getType();
		if (type == Sep3asmToken.TK_REGISTOR) {
			register = tk;
			mode = REGISTER;
			tk = ct.getNextToken(ctx);
		} else if (type == Sep3asmToken.TK_BRACKETa) {
			tk = ct.getNextToken(ctx);
			if (tk.getType() == Sep3asmToken.TK_REGISTOR) {
				tk = ct.getNextToken(ctx);
				if (tk.getType() == Sep3asmToken.TK_BRACKETb) {
					tk = ct.getNextToken(ctx);
					if (tk.getType() == Sep3asmToken.TK_PLUS) {
						mode = POSTINC;
						tk = ct.getNextToken(ctx);
					} else if (tk.getType() == Sep3asmToken.TK_NL) {
						mode = INDIRECT;
					}
				} else {
					ctx.fatalError(tk.toExplainString() + ")が抜けています");
				}
			} else {
				ctx.fatalError(tk.toExplainString() + "レジスタ名が来ます");
			}
		} else if (type == Sep3asmToken.TK_MINUS) {
			tk = ct.getNextToken(ctx);
			// TODO: 消す
			System.out.println("マイナスの後");
			System.out.println(tk.toExplainString());
			if (tk.getType() == Sep3asmToken.TK_BRACKETa) {
				tk = ct.getNextToken(ctx);
				if (tk.getType() == Sep3asmToken.TK_REGISTOR) {
					tk = ct.getNextToken(ctx);
					if (tk.getType() == Sep3asmToken.TK_BRACKETb) {
						mode = PREDEC;
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
			// TODO: 消す
			System.out.println("#のあと");
			System.out.println(tk.toExplainString());
			if (NumOrIdent.isFirst(tk)) {
				if (tk.getType() == Sep3asmToken.TK_NUM) {
					mode = IMM;
				} else if (tk.getType() == Sep3asmToken.TK_IDENT) {
					mode = LABEL;
				}
				noi = new NumOrIdent(ctx);
				noi.parse(ctx);
			} else {
				ctx.warning(tk.toExplainString() + "数か名前が来ます");
			}
		} else if (NumOrIdent.isFirst(tk)) {
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
		// 本来モードはpass1で決まるもの？
		// でも構文解析で決まっちゃうし，逆に構文解析じゃないと決められない気がする
	}

	// TODO: limit関数ではどうやってOperandクラス内でFromとToを区別してよいかわからなかったためクラスを分けた
	public void limit(int info, Sep3asmParseContext ctx, Sep3asmToken inst, int t, final String s)
			throws FatalErrorException {
		if ((mode & info) != 0x0) {
			ctx.fatalError(s + "不適切なアドレシングモードが含まれています");
		}
		if (t == FROM && mode == PREDEC && register.getRegisterNumber() != 6) {
			ctx.fatalError(s + "R6以外にプレデクリメントレジスタ間接アドレシングは使えません");
		}
		if (t == TO) {
			if ((mode == INDIRECT || mode == POSTINC) && register.getRegisterNumber() == 7) {
				ctx.fatalError(s + "R7に間接レジスタアドレシングは使えません");
			}
			if ((mode == PREDEC)) {
				ctx.fatalError(s + "プレデクリメントモードは使えません");
			}
		}
	}

	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
	}
}
