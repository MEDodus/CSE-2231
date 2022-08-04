import components.program.Program;
import components.queue.Queue;
import components.set.Set;
import components.set.Set1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ProgramSkeleton {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ProgramSkeleton() {
    }

    /**
     * Evaluates a Boolean expression and returns its value.
     *
     * @param tokens
     *            the {@code Queue<String>} that starts with a bool-expr string
     * @return value of the expression
     * @updates tokens
     * @requires [a bool-expr string is a prefix of tokens]
     * @ensures <pre>
     * valueOfBoolExpr =
     *   [value of longest bool-expr string at start of #tokens]  and
     * #tokens = [longest bool-expr string at start of #tokens] * tokens
     * </pre>
     */
    public static boolean valueOfBoolExpr(Queue<String> tokens) {
        boolean value = false;
        if (tokens.front().equals("T")) {
            value = true;
        } else if (tokens.front().equals("F")) {
            value = false;
        } else if (tokens.front().equals("NOT")) {
            tokens.dequeue(); /* NOT */
            tokens.dequeue(); /* ( */
            value = !valueOfBoolExpr(tokens);
            tokens.dequeue(); /* ) */
        } else if (tokens.front().equals("(")) {
            tokens.dequeue();
            value = valueOfBoolExpr(tokens);
            if (tokens.dequeue().equals("AND")) {
                value = value && valueOfBoolExpr(tokens);
            } else {
                value = value || valueOfBoolExpr(tokens);
            }
        }
        return value;
    }

    /**
     * BugsWorld possible cell states.
     */
    enum CellState {
        EMPTY, WALL, FRIEND, ENEMY;
    }

    /**
     * Returns whether the given integer is the byte code of a BugsWorld virtual
     * machine primitive instruction (MOVE, TURNLEFT, TURNRIGHT, INFECT, SKIP,
     * HALT).
     *
     * @param byteCode
     *            the integer to be checked
     * @return true if {@code byteCode} is the byte code of a primitive
     *         instruction or false otherwise
     * @ensures <pre>
     * isPrimitiveInstructionByteCode =
     *  [true iff byteCode is the byte code of a primitive instruction]
     * </pre>
     */
    private static boolean isPrimitiveInstructionByteCode(int byteCode) {

        /*
         * Make a set to contain all byte codes. Updates even if byte codes in
         * interface are updated.
         */
        Set<Integer> s = new Set1L<>();
        s.add(Program.Instruction.HALT.byteCode());
        s.add(Program.Instruction.SKIP.byteCode());
        s.add(Program.Instruction.INFECT.byteCode());
        s.add(Program.Instruction.MOVE.byteCode());
        s.add(Program.Instruction.TURNRIGHT.byteCode());
        s.add(Program.Instruction.TURNLEFT.byteCode());

        return s.contains(byteCode);
    }

    /**
     * Returns the value of the condition in the given conditional jump
     * {@code condJump} given what the bug sees {@code wbs}. Note that if
     * {@code condJump} is the byte code for the conditional jump
     * JUMP_IF_NOT_condition, the value returned is the value of the "condition"
     * part of the jump instruction.
     *
     * @param wbs
     *            the {@code CellState} indicating what the bug sees
     * @param condJump
     *            the byte code of a conditional jump
     * @return the value of the conditional jump condition
     * @requires [condJump is the byte code of a conditional jump]
     * @ensures <pre>
     * conditionalJumpCondition =
     *  [the value of the condition of condJump given what the bug sees wbs]
     * </pre>
     */
    private static boolean conditionalJumpCondition(CellState wbs,
            int condJump) {

        Set s = new Set1L<>();
        s.add(Program.Instruction.JUMP_IF_NOT_NEXT_IS_EMPTY.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_NEXT_IS_ENEMY.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_NEXT_IS_FRIEND.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_NEXT_IS_NOT_EMPTY.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_NEXT_IS_NOT_ENEMY.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_NEXT_IS_NOT_FRIEND.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_NEXT_IS_NOT_WALL.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_NEXT_IS_WALL.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_RANDOM.byteCode());
        s.add(Program.Instruction.JUMP_IF_NOT_TRUE.byteCode());

        boolean condition = false;

        if (condJump == Program.Instruction.JUMP_IF_NOT_NEXT_IS_EMPTY
                .byteCode()) {
            condition = false;
        }

        return s.contains(condJump);
    }

    /**
     * Returns the location of the next primitive instruction to execute in
     * compiled program {@code cp} given what the bug sees {@code wbs} and
     * starting from location {@code pc}.
     *
     * @param cp
     *            the compiled program
     * @param wbs
     *            the {@code CellState} indicating what the bug sees
     * @param pc
     *            the program counter
     * @return the location of the next primitive instruction to execute
     * @requires <pre>
     * [cp is a valid compiled BL program]  and
     * 0 <= pc < cp.length  and
     * [pc is the location of an instruction byte code in cp, that is, pc
     *  cannot be the location of an address]
     * </pre>
     * @ensures <pre>
     * [return the address of the next primitive instruction that
     *  should be executed in program cp given what the bug sees wbs and
     *  starting execution at address pc in program cp]
     * </pre>
     */
    public static int nextPrimitiveInstructionAddress(int[] cp, CellState wbs,
            int pc) {

        int counter = pc;

        while (!isPrimitiveInstructionByteCode(cp[counter])) {
            if (conditionalJumpCondition(wbs, cp[counter])) {
                counter = cp[counter + 1];
            } else {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Put your main program code here
         */
        int x = 10;

    }

}
