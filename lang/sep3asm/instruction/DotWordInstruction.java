package lang.sep3asm.instruction;

import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.parse.Operand;

public class DotWordInstruction extends Sep3Instruction {
    public DotWordInstruction(int opCode, int from, int to) {
        super(opCode, from, to);
    }

    public void generate(Sep3asmParseContext ctx, Operand op1, Operand op2) {
    }

}
