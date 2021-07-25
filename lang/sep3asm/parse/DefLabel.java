package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class DefLabel extends Sep3asmParseRule {


    public DefLabel(Sep3asmParseContext ctx) {
    }

    public static boolean isFirst(Sep3asmToken tk) {
        return tk.getType() == Sep3asmToken.TK_COLON;
    }

    @Override
    public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
        //ここまで来てる時点で":"が確定してるからこれ以上のparseがいらない
    }

    public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
    }

    public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
    }

}
