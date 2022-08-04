import components.set.Set;
import components.set.Set2;
import components.statement.Statement;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

                // TODO - fill in case
                int k = s.lengthOfBlock();
                for (int i = 0; i < k; i++) {
                    Statement bloc = s.removeFromBlock(i);
                    count += countOfPrimitiveCalls(bloc);
                    s.addToBlock(i, bloc);
                }

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                // TODO - fill in case
                Statement sub = s.newInstance();
                Statement.Condition condition = s.disassembleIf(sub);
                count = countOfPrimitiveCalls(sub);
                s.assembleIf(condition, sub);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                // TODO - fill in case
                Statement lt = s.newInstance();
                Statement rt = s.newInstance();
                Statement.Condition condition = s.disassembleIfElse(lt, rt);
                count = countOfPrimitiveCalls(lt) + countOfPrimitiveCalls(rt);
                s.assembleIfElse(condition, lt, rt);

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                // TODO - fill in case
                Statement sub = s.newInstance();
                Statement.Condition condition = s.disassembleWhile(sub);
                count = countOfPrimitiveCalls(sub);
                s.assembleWhile(condition, sub);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                // TODO - fill in case
                String instruction = s.disassembleCall();
                Set<String> set = createPrimitiveSet();
                if (set.contains(instruction)) {
                    count += 1;
                }
                s.assembleCall(instruction);
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
    }

    /**
     * Reports the number of calls to a given instruction, {@code instr}, in a
     * given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @param instr
     *            the instruction name
     * @return the number of calls to {@code instr} in {@code s}
     * @ensures countOfInstructionCalls = [number of calls to instr in s]
     */
    public static int countOfInstructionCalls(Statement s, String instr) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

                // TODO - fill in case
                int k = s.lengthOfBlock();
                for (int i = 0; i < k; i++) {
                    Statement bloc = s.removeFromBlock(i);
                    count += countOfInstructionCalls(bloc, instr);
                    s.addToBlock(i, bloc);
                }

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                // TODO - fill in case
                Statement sub = s.newInstance();
                Statement.Condition condition = s.disassembleIf(sub);
                count = countOfInstructionCalls(sub, instr);
                s.assembleIf(condition, sub);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                // TODO - fill in case
                Statement lt = s.newInstance();
                Statement rt = s.newInstance();
                Statement.Condition condition = s.disassembleIfElse(lt, rt);
                count = countOfInstructionCalls(lt, instr)
                        + countOfInstructionCalls(rt, instr);
                s.assembleIfElse(condition, lt, rt);

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                // TODO - fill in case
                Statement sub = s.newInstance();
                Statement.Condition condition = s.disassembleWhile(sub);
                count = countOfInstructionCalls(sub, instr);
                s.assembleWhile(condition, sub);

                break;
            }
            case CALL: {
                String instruction = s.disassembleCall();
                if (instruction.equals(instr)) {
                    count += 1;
                }
                s.assembleCall(instruction);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
    }

    /**
     *
     * @return set {@code Set} containing the strings of primitive instructions.
     */
    private static Set<String> createPrimitiveSet() {
        Set<String> set = new Set2<>();
        String[] arr = { "move", "turnleft", "turnright", "skip", "infect" };
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        return set;
    }

    private static Set<String> createInstructionSet() {
        Set<String> set = new Set2<>();
        String[] arr = { "" };
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        return set;
    }

    /**
     * Refactors the given {@code Statement} by renaming every occurrence of
     * instruction {@code oldName} to {@code newName}. Every other statement is
     * left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates s
     * @requires [newName is a valid IDENTIFIER]
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of oldName is
     *   replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Statement s, String oldName,
            String newName) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

                // TODO - fill in case
                int k = s.lengthOfBlock();
                for (int i = 0; i < k; i++) {
                    Statement bloc = s.removeFromBlock(i);
                    renameInstruction(bloc, oldName, newName);
                    s.addToBlock(i, bloc);
                }

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                // TODO - fill in case
                Statement sub = s.newInstance();
                Statement.Condition condition = s.disassembleIf(sub);
                renameInstruction(sub, oldName, newName);
                s.assembleIf(condition, sub);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                // TODO - fill in case
                Statement lt = s.newInstance();
                Statement rt = s.newInstance();
                Statement.Condition condition = s.disassembleIfElse(lt, rt);
                if (condition.) {

                }
                renameInstruction(lt, oldName, newName);
                renameInstruction(rt, oldName, newName);
                s.assembleIfElse(condition, lt, rt);

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                // TODO - fill in case
                Statement sub = s.newInstance();
                Statement.Condition condition = s.disassembleWhile(sub);
                renameInstruction(sub, oldName, newName);
                s.assembleWhile(condition, sub);

                break;
            }
            case CALL: {
                String instruction = s.disassembleCall();
                if (instruction.equals(oldName)) {
                    instruction = newName;
                }
                s.assembleCall(instruction);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
    }

    /**
     * Refactors the given {@code Statement} so that every IF_ELSE statement
     * with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,
     * NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent
     * IF_ELSE with the opposite condition and the "then" and "else" BLOCKs
     * switched. Every other statement is left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @updates s
     * @ensures <pre>
     * s = [#s refactored so that IF_ELSE statements with "not"
     *   conditions are simplified so the "not" is removed]
     * </pre>
     */
    public static void simplifyIfElse(Statement s) {
        switch (s.kind()) {
            case BLOCK: {

                // TODO - fill in case
                int k = s.lengthOfBlock();
                for (int i = 0; i < k; i++) {
                    Statement sub = s.removeFromBlock(i);
                    simplifyIfElse(sub);
                    s.addToBlock(i, sub);
                }
                break;
            }
            case IF: {

                // TODO - fill in case
                Statement sub = s.newInstance();
                Statement.Condition condition = s.disassembleIf(sub);
                simplifyIfElse(sub);
                s.assembleIf(condition, sub);
                break;
            }
            case IF_ELSE: {

                // TODO - fill in case
                Statement lt = s.newInstance();
                Statement rt = s.newInstance();
                // Put the subtrees in their respective locations.
                Statement.Condition condition = s.disassembleIfElse(lt, rt);
                switch (condition) {
                    case NEXT_IS_NOT_EMPTY:
                        condition = Statement.Condition.NEXT_IS_EMPTY;
                        // Swap locations of "if" and "else" since condition is negated.
                        s.assembleIfElse(condition, rt, lt);
                        break;
                    case NEXT_IS_NOT_ENEMY:
                        condition = Statement.Condition.NEXT_IS_ENEMY;
                        // Swap locations of "if" and "else" since condition is negated.
                        s.assembleIfElse(condition, rt, lt);
                        break;
                    case NEXT_IS_NOT_FRIEND:
                        condition = Statement.Condition.NEXT_IS_FRIEND;
                        // Swap locations of "if" and "else" since condition is negated.
                        s.assembleIfElse(condition, rt, lt);
                        break;
                    case NEXT_IS_NOT_WALL:
                        condition = Statement.Condition.NEXT_IS_WALL;
                        // Swap locations of "if" and "else" since condition is negated.
                        s.assembleIfElse(condition, rt, lt);
                        break;
                    default:
                        s.assembleIfElse(condition, lt, rt);
                        break;
                }
                break;
            }
            case WHILE: {

                // TODO - fill in case
                Statement sub = s.newInstance();
                Statement.Condition condition = s.disassembleWhile(sub);
                simplifyIfElse(sub);
                s.assembleWhile(condition, sub);
                break;
            }
            case CALL: {
                // nothing to do here...can you explain why?
                /*
                 * Calls always occur below the control structures, thus, once
                 * we get to the control structures we don't have to check
                 * further into the calls.
                 */
                break;
            }
            default: {
                // this will never happen...can you explain why?
                /*
                 * A Statement component is only built up of the above enums...
                 * thus, there will never be another enum type in a valid
                 * Statement object.
                 */
                break;
            }
        }
    }
}
