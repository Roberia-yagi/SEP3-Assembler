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
		// 特殊文字
		put(":", new TokenAssoc(Sep3asmToken.TK_COLON, null));
		put(".", new TokenAssoc(Sep3asmToken.TK_DOT, null));
		put("=", new TokenAssoc(Sep3asmToken.TK_EQUAL, null));
		put("-", new TokenAssoc(Sep3asmToken.TK_MINUS, null));
		put("+", new TokenAssoc(Sep3asmToken.TK_PLUS, null));
		put(",", new TokenAssoc(Sep3asmToken.TK_COMMA, null));
		put("\n", new TokenAssoc(Sep3asmToken.TK_NL, null));
		put("(", new TokenAssoc(Sep3asmToken.TK_BRACKETa, null));
		put(")", new TokenAssoc(Sep3asmToken.TK_BRACKETb, null));
		put("#", new TokenAssoc(Sep3asmToken.TK_SHARP, null));
		// レジスタ
		put("r0", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 0));
		put("r1", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 1));
		put("r2", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 2));
		put("r3", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 3));
		put("r4", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 4));
		put("r5", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 5));
		put("r6", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 6));
		put("r7", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 7));
		put("psw", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 5));
		put("sp", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 6));
		put("pc", new TokenAssoc(Sep3asmToken.TK_REGISTOR, 7));
		// データ転送命令
		put("mov", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x4000, D | I | MI | IP | IMM, D | I | IP)));
		// 演算命令
		put("clr", new TokenAssoc(Sep3asmToken.TK_INST1a, new OneOperandInstruction(0x1000, 0, D | I | IP)));
		put("add", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x5000, D | I | MI | IP | IMM, D | I | IP)));
		put("sub", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x6000, D | I | MI | IP | IMM, D | I | IP)));
		put("cmp", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x6C00, D | I | MI | IP | IMM, D | I | IP)));
		put("or", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x8000, D | I | MI | IP | IMM, D | I | IP)));
		put("xor", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x8400, D | I | MI | IP | IMM, D | I | IP)));
		put("and", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x8800, D | I | MI | IP | IMM, D | I | IP)));
		put("bit", new TokenAssoc(Sep3asmToken.TK_INST2,
				new TwoOperandInstruction(0x8C00, D | I | MI | IP | IMM, D | I | IP)));
		// 桁移動命令
		put("asl", new TokenAssoc(Sep3asmToken.TK_INST1a, new ShiftInstruction(0x2000, 0, D | I | IP)));
		put("asr", new TokenAssoc(Sep3asmToken.TK_INST1a, new ShiftInstruction(0x2400, 0, D | I | IP)));
		put("lsl", new TokenAssoc(Sep3asmToken.TK_INST1a, new ShiftInstruction(0x3000, 0, D | I | IP)));
		put("lsr", new TokenAssoc(Sep3asmToken.TK_INST1a, new ShiftInstruction(0x3400, 0, D | I | IP)));
		put("rsl", new TokenAssoc(Sep3asmToken.TK_INST1a, new ShiftInstruction(0x3800, 0, D | I | IP)));
		put("rsr", new TokenAssoc(Sep3asmToken.TK_INST1a, new ShiftInstruction(0x3C00, 0, D | I | IP)));
		// 分岐命令
		put("jmp", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new AbsoluteJumpInstruction(0x4407, D | I | MI | IP | IMM | LABEL, 0)));
		put("rjp", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new RelativeJumpInstruction(0x5407, D | I | MI | IP | IMM | LABEL, 0)));
		put("brn", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new AbsoluteJumpInstruction(0xC007, D | I | MI | IP | IMM | LABEL, 0)));
		put("brz", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new AbsoluteJumpInstruction(0xC407, D | I | MI | IP | IMM | LABEL, 0)));
		put("brv", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new AbsoluteJumpInstruction(0xC807, D | I | MI | IP | IMM | LABEL, 0)));
		put("brc", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new AbsoluteJumpInstruction(0xCC07, D | I | MI | IP | IMM | LABEL, 0)));
		put("rbn", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new RelativeJumpInstruction(0xE007, D | I | MI | IP | IMM | LABEL, 0)));
		put("rbz", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new RelativeJumpInstruction(0xE407, D | I | MI | IP | IMM | LABEL, 0)));
		put("rbv", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new RelativeJumpInstruction(0xE807, D | I | MI | IP | IMM | LABEL, 0)));
		put("rbc", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new RelativeJumpInstruction(0xEC07, D | I | MI | IP | IMM | LABEL, 0)));
		put("jsr", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new AbsoluteJumpInstruction(0xB01E, D | I | MI | IP | IMM | LABEL, 0)));
		put("rjs", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new RelativeJumpInstruction(0xB41E, D | I | MI | IP | IMM | LABEL, 0)));
		put("ret", new TokenAssoc(Sep3asmToken.TK_INST0,
				new AbsoluteJumpInstruction(0x4AC7, 0, 0)));
		// 割り込み命令
		put("svc", new TokenAssoc(Sep3asmToken.TK_INST1b,
				new OneOperandInstruction(0xB81E, D | I | MI | IP | IMM | LABEL, 0)));
		put("rit", new TokenAssoc(Sep3asmToken.TK_INST0,
				new ZeroOperandInstruction(0x4EC7, 0, 0)));
		// システム制御命令
		put("hlt", new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x0000, 0, 0)));
		put("nop", new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x7000, 0, 0)));
		// 疑似命令
		put(".word", new TokenAssoc(Sep3asmToken.TK_DOTWD, null));
		put(".blkw", new TokenAssoc(Sep3asmToken.TK_BLKW, null));
		put(".end", new TokenAssoc(Sep3asmToken.TK_END, null));
	}
}
