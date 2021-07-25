package lang.sep3asm;

import java.util.HashMap;

import lang.sep3asm.instruction.*;
import lang.sep3asm.parse.Operand;

public class Sep3asmTokenRule extends HashMap<String, TokenAssoc> {
	private static final long serialVersionUID = 6928902799089728690L;
	private static final int D = Operand.REGISTER; // レジスタアドレシングを許す
	private static final int I = Operand.INDIRECT; // レジスタ間接アドレシングを許す
	private static final int MI = Operand.PREDEC; // プレデクリメント・レジスタ間接アドレシングを許す
	private static final int IP = Operand.POSTINC; // ポストインクリメント・レジスタ間接アドレシングを許す
	private static final int IMM = Operand.IMM; // 即値（イミディエイト）を許す
	private static final int LABEL = Operand.LABEL; // 解析時のみ、ラベルを＃なしに書いてある

	// toオペランドに -(R?)を使うのは禁止
	// fromオペランドに -(R?) を使うときは、R6,SPに限定される
	// toオペランドに (R7), (R7)+ を使うのは禁止

	public Sep3asmTokenRule() {
		// 空白文字
		put(" ", new TokenAssoc(Sep3asmToken.TK_BLANK, null));
		put("\t", new TokenAssoc(Sep3asmToken.TK_BLANK, null));
		// 英数字
		put(".", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("_", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("0", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("1", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("2", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("3", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("4", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("5", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("6", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("7", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("8", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("9", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("a", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("b", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("c", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("d", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("e", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("f", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("g", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("h", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("i", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("j", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("k", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("l", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("m", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("n", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("o", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("p", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("q", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("r", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("s", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("t", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("u", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("v", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("w", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("x", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("y", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		put("z", new TokenAssoc(Sep3asmToken.TK_CHARA, null));
		// 特殊文字
		put(";", new TokenAssoc(Sep3asmToken.TK_SEMICOLON, null));
		put(":", new TokenAssoc(Sep3asmToken.TK_COLON, null));
		put("-", new TokenAssoc(Sep3asmToken.TK_MINUS, null));
		put(",", new TokenAssoc(Sep3asmToken.TK_COMMA, null));
		put("\n", new TokenAssoc(Sep3asmToken.TK_NL, null));
		// レジスタ
		put("r0", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("r1", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("r2", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("r3", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("r4", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("r5", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("r6", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("r7", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("psw", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("sp", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		put("pc", new TokenAssoc(Sep3asmToken.TK_REGISTOR, null));
		// データ転送命令
		put("mov", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x4000, D | I | MI | IP | IMM, D | I | IP)));
		// 演算命令
		put("clr", new TokenAssoc(Sep3asmToken.TK_INST1a, null));
		put("add", new TokenAssoc(Sep3asmToken.TK_INST2, null));
		put("sub", new TokenAssoc(Sep3asmToken.TK_INST2, null));
		put("cmp", new TokenAssoc(Sep3asmToken.TK_INST2, null));
		put("or", new TokenAssoc(Sep3asmToken.TK_INST2, null));
		put("xor", new TokenAssoc(Sep3asmToken.TK_INST2, null));
		put("and", new TokenAssoc(Sep3asmToken.TK_INST2, null));
		put("bit", new TokenAssoc(Sep3asmToken.TK_INST2, null));
		// 桁移動命令
		put("asl", new TokenAssoc(Sep3asmToken.TK_INST1a, null));
		put("asr", new TokenAssoc(Sep3asmToken.TK_INST1a, null));
		put("lsl", new TokenAssoc(Sep3asmToken.TK_INST1a, null));
		put("lsr", new TokenAssoc(Sep3asmToken.TK_INST1a, null));
		put("rsl", new TokenAssoc(Sep3asmToken.TK_INST1a, null));
		put("rsr", new TokenAssoc(Sep3asmToken.TK_INST1a, null));
		// 分岐命令
		put("jmp", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("rjp", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("brn", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("brz", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("brv", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("brc", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("rbn", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("rbz", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("rbv", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("rbc", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("jsr", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("rjs", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		put("ret", new TokenAssoc(Sep3asmToken.TK_INST0, null));
		// 割り込み命令
		put("rit", new TokenAssoc(Sep3asmToken.TK_INST0, null));
		put("svc", new TokenAssoc(Sep3asmToken.TK_INST1, null));
		// システム制御命令
		put("hlt", new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x0000, 0, 0)));
		put("nop", new TokenAssoc(Sep3asmToken.TK_INST0, null));
		put("rjs", new TokenAssoc(Sep3asmToken.TK_INST1,
				new RelativeJumpInstruction(0xB41E, D | I | MI | IP | IMM | LABEL, 0)));
		// 疑似命令
		put(".word", new TokenAssoc(Sep3asmToken.TK_DOTWD, null));
		put(".blkw", new TokenAssoc(Sep3asmToken.TK_BLKW, null));
		put(".end", new TokenAssoc(Sep3asmToken.TK_END, null));
	}
}
