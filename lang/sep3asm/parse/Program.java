package lang.sep3asm.parse;

import java.util.ArrayList;

import lang.*;
import lang.sep3asm.*;

public class Program extends Sep3asmParseRule {
	// プログラム ::= { 行 } EOF
	private ArrayList<Sep3asmParseRule> list;

	public Program(Sep3asmParseContext ctx) {
		list = new ArrayList<Sep3asmParseRule>();
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return Line.isFirst(tk) || tk.getType() == Sep3asmToken.TK_EOF;
	}

	@Override
	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		Sep3asmToken tk = ct.getCurrentToken(ctx);
		// TODO: 消す
		int counter = 0;
		while (Line.isFirst(tk)) {
			// TODO: 消す
			counter++;
			System.out.println(counter);
			System.out.println(tk.toExplainString());
			Sep3asmParseRule line = new Line(ctx);
			line.parse(ctx);
			list.add(line);
			tk = ct.getCurrentToken(ctx);
		}
		if (tk.getType() != Sep3asmToken.TK_EOF) {
			ctx.warning(tk.toExplainString() + "ファイルの終わりにゴミがあります");
		}
	}

	public void pass1(Sep3asmParseContext pcx) throws FatalErrorException {
		for (int i = 0; i < list.size(); i++) {
			// TODO : 消す
			System.out.print(i+1);
			System.out.println("行");
			Sep3asmParseRule line = list.get(i);
			line.pass1(pcx);
		}
		// TODO: 消す
		pcx.getSymbolTable().show();
	}

	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
