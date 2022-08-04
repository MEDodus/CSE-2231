import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.statement.Statement;

/**
 * Rename instruction method for Statement
 *
 * @author miked
 *
 */
public class ProgramRenameInstr extends Program1 {

    public static void renameInstruction(Program s, String str1, String str2) {

        Map<String, Statement> m = s.newContext();
        Map<String, Statement> copy = s.newContext();
        s.swapContext(m);

        while (m.size() > 0) {
            Map.Pair<String, Statement> p = m.removeAny();
            renameInstruction(p.value(), str1, str2);
        }

    }
}
