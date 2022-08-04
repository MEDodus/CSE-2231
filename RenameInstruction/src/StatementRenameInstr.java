import components.map.Map;
import components.program.Program;
import components.statement.Statement;
import components.statement.Statement1;

/**
 * Rename instruction method for Statement
 *
 * @author miked
 *
 */
public class StatementRenameInstr extends Statement1 {

    /**
     * Implementation of renameInstruction for Statement.
     *
     * @param s
     * @param str1
     * @param str2
     */
    public static void renameInstruction(Statement s, String str1,
            String str2) {
        switch (s.kind()) {
            case BLOCK:
                int length = s.lengthOfBlock();
                for (int i = 0; i < length; i++) {
                    Statement sub = s.removeFromBlock(i);
                    renameInstruction(sub, str1, str2);
                    s.addToBlock(i, sub);
                }
                break;
            case IF:
                Statement subIf = s.removeFromBlock(0);
                renameInstruction(subIf, str1, str2);
                s.addToBlock(0, subIf);
                break;
            case IF_ELSE:
                Statement lt = s.removeFromBlock(0);
                Statement rt = s.removeFromBlock(1);
                renameInstruction(lt, str1, str2);
                renameInstruction(rt, str1, str2);
                s.addToBlock(0, lt);
                s.addToBlock(1, rt);
                break;
            case WHILE:
                Statement subWhile = s.removeFromBlock(0);
                renameInstruction(subWhile, str1, str2);
                s.addToBlock(0, subWhile);
                break;
            case CALL:
                String instr = s.disassembleCall();
                if (instr.equals(str1)) {
                    instr = str2;
                }
                s.assembleCall(instr);
                break;
            default:
                break;
        }
    }

    /**
     * Implementation of renameInstruction for Program.
     *
     * @param s
     * @param str1
     * @param str2
     */
    public static void renameInstruction(Program s, String str1, String str2) {

        Map<String, Statement> m = s.newContext();
        Map<String, Statement> copy = s.newContext();
        s.swapContext(m);

        while (m.size() > 0) {
            Map.Pair<String, Statement> p = m.removeAny();
            if (p.key().equals(str1)) {
                copy.add(str2, p.value());
            } else {
                renameInstruction(p.value(), str1, str2);
                copy.add(p.key(), p.value());
            }
        }

        Statement body = s.newBody();
        s.swapBody(body);
        renameInstruction(body, str1, str2);
        s.swapBody(body);

    }
}
