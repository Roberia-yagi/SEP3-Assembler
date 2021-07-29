package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class Line extends Sep3asmParseRule {
	// 行 ::= コメント行 '\n' | 名前先行行 '\n'| 命令行 '\n' | 疑似命令行 '\n'

	Sep3asmParseRule line;

	public Line(Sep3asmParseContext ctx) {
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_NL || NameFirstLine.isFirst(tk) || InstLine.isFirst(tk)
				|| PseudoInstLine.isFirst(tk);
	}

	@Override
	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		Sep3asmToken tk = ct.getCurrentToken(ctx);
		if (NameFirstLine.isFirst(tk) || InstLine.isFirst(tk) || PseudoInstLine.isFirst(tk)) {
			if (NameFirstLine.isFirst(tk)) {
				// TODO: 消す
				System.out.println("ラベル読んでNameFirstLineに入った");
				line = new NameFirstLine(ctx);
			} else if (InstLine.isFirst(tk)) {
				line = new InstLine(ctx);
			} else if (PseudoInstLine.isFirst(tk)) {
				line = new PseudoInstLine(ctx);
			}
			line.parse(ctx);
			tk = ct.getCurrentToken(ctx);
			// TODO: 消す
			System.out.println("改行前");
			System.out.println(tk.toExplainString());
			if (tk.getType() == Sep3asmToken.TK_NL) {
				ct.getNextToken(ctx);
			} else {
				ctx.fatalError(tk.toExplainString() + "改行されていません");
			}
		} else if (tk.getType() == Sep3asmToken.TK_NL) {
			ct.getNextToken(ctx);
		} else {
			ctx.fatalError(tk.toExplainString() + "改行されていません");
		}
	}

	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		// TODO: 消す
		System.out.println("line pass1");
		if (line != null) {
			line.pass1(ctx);
		}
	}

	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
