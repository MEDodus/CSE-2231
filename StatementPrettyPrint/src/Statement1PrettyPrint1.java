import components.program.Program;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code prettyPrint} for
 * {@code Statement}.
 */
public final class Statement1PrettyPrint1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constructs into the given {@code Statement} the BLOCK statement read from
     * the given input file.
     *
     * @param fileName
     *            the name of the file containing 0 or more statements
     * @param s
     *            the constructed BLOCK statement
     * @replaces s
     * @requires <pre>
     * [fileName is the name of a file containing 0 or more valid BL statements]
     * </pre>
     * @ensures s = [BLOCK statement from file fileName]
     */
    private static void loadStatement(String fileName, Statement s) {
        SimpleReader in = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(in);
        s.parseBlock(tokens);
        in.close();
    }

    /**
     * Prints the given number of spaces to the given output stream.
     *
     * @param out
     *            the output stream
     * @param numSpaces
     *            the number of spaces to print
     * @updates out.content
     * @requires out.is_open and spaces >= 0
     * @ensures out.content = #out.content * [numSpaces spaces]
     */
    private static void printSpaces(SimpleWriter out, int numSpaces) {
        for (int i = 0; i < numSpaces; i++) {
            out.print(' ');
        }
    }

    /**
     * Converts c into the corresponding BL condition.
     *
     * @param c
     *            the Condition to convert
     * @return the BL condition corresponding to c
     * @ensures toStringCondition = [BL condition corresponding to c]
     */
    private static String toStringCondition(Condition c) {
        String result;
        switch (c) {
            case NEXT_IS_EMPTY: {
                result = "next-is-empty";
                break;
            }
            case NEXT_IS_NOT_EMPTY: {
                result = "next-is-not-empty";
                break;
            }
            case NEXT_IS_ENEMY: {
                result = "next-is-enemy";
                break;
            }
            case NEXT_IS_NOT_ENEMY: {
                result = "next-is-not-enemy";
                break;
            }
            case NEXT_IS_FRIEND: {
                result = "next-is-friend";
                break;
            }
            case NEXT_IS_NOT_FRIEND: {
                result = "next-is-not-friend";
                break;
            }
            case NEXT_IS_WALL: {
                result = "next-is-wall";
                break;
            }
            case NEXT_IS_NOT_WALL: {
                result = "next-is-not-wall";
                break;
            }
            case RANDOM: {
                result = "random";
                break;
            }
            case TRUE: {
                result = "true";
                break;
            }
            default: {
                // this will never happen...
                result = "";
                break;
            }
        }
        return result;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1PrettyPrint1() {
        super();
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    @Override
    public void prettyPrint(SimpleWriter out, int offset) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        assert offset >= 0 : "Violation of: 0 <= offset";

        switch (this.kind()) {
            case BLOCK: {

                int length = this.lengthOfBlock();
                for (int i = 0; i < length; i++) {
                    Statement subBlock = this.removeFromBlock(i);
                    subBlock.prettyPrint(out, offset);
                    this.addToBlock(i, subBlock);
                }

                break;
            }
            case IF: {

                printSpaces(out, offset);
                Statement copy = this.newInstance();
                Condition c = this.disassembleIf(copy);
                out.println("IF " + toStringCondition(c) + " THEN");
                copy.prettyPrint(out, offset + Program.INDENT_SIZE);
                this.assembleIf(c, copy);
                printSpaces(out, offset);
                out.println("END IF");

                break;
            }
            case IF_ELSE: {

                printSpaces(out, offset);
                Statement lt = this.newInstance();
                Statement rt = this.newInstance();
                Condition c = this.disassembleIfElse(lt, rt);
                out.println("IF " + toStringCondition(c) + " THEN");
                lt.prettyPrint(out, offset + Program.INDENT_SIZE);
                printSpaces(out, offset);
                out.println("ELSE");
                rt.prettyPrint(out, offset + Program.INDENT_SIZE);
                this.assembleIfElse(c, lt, rt);
                printSpaces(out, offset);
                out.println("END IF");

                break;
            }
            case WHILE: {

                printSpaces(out, offset);
                Statement subWhile = this.newInstance();
                Condition c = this.disassembleWhile(subWhile);
                out.println("WHILE " + toStringCondition(c) + " DO");
                subWhile.prettyPrint(out, offset + Program.INDENT_SIZE);
                this.assembleWhile(c, subWhile);
                printSpaces(out, offset);
                out.println("END WHILE");

                break;
            }
            case CALL: {

                printSpaces(out, offset);
                String instr = this.disassembleCall();
                out.println(instr);
                this.assembleCall(instr);

                break;
            }
            default: {
                // this will never happen...
                break;
            }
        }
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement file name: ");
        String fileName = in.nextLine();
        /*
         * Generate expected output in file "data/expected-output.txt"
         */
        out.println("*** Generating expected output ***");
        Statement s1 = new Statement1();
        loadStatement(fileName, s1);
        SimpleWriter ppOut = new SimpleWriter1L("data/expected-output.txt");
        s1.prettyPrint(ppOut, 2);
        ppOut.close();
        /*
         * Generate actual output in file "data/actual-output.txt"
         */
        out.println("*** Generating actual output ***");
        Statement s2 = new Statement1PrettyPrint1();
        loadStatement(fileName, s2);
        ppOut = new SimpleWriter1L("data/actual-output.txt");
        s2.prettyPrint(ppOut, 2);
        ppOut.close();
        /*
         * Check that prettyPrint restored the value of the statement
         */
        if (s2.equals(s1)) {
            out.println("Statement value restored correctly.");
        } else {
            out.println("Error: statement value was not restored.");
        }

        in.close();
        out.close();
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code SEPARATORS}) or "separator string" (maximal length string of
     * characters in {@code SEPARATORS}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection entries(SEPARATORS) = {}
     * then
     *   entries(nextWordOrSeparator) intersection entries(SEPARATORS) = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection entries(SEPARATORS) /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of entries(SEPARATORS)  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of entries(SEPARATORS))
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position) {

        String word = "";
        Set<Character> s = new Set1L<>();
        for (int i = 0; i < SEPARATORS.length(); i++) {
            s.add(SEPARATORS.charAt(i));
        }

        boolean first = s.contains(text.charAt(position));
        boolean encounter = first;

        int idx = position;
        while (idx < text.length() && first == encounter) {
            idx++;
            encounter = s.contains(text.charAt(idx));
        }

        word = text.substring(position, idx);
        return word;
    }

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Queue<String>}.
     *
     * @param in
     *            the input stream
     * @return the queue of tokens
     * @requires in.is_open
     * @ensures <pre>
     * tokens =
     *   [the non-whitespace tokens in #in.content] * <END_OF_INPUT>  and
     * in.content = <>
     * </pre>
     */
    public static Queue<String> tokens(SimpleReader in) {

        Queue<String> tokens = new Queue1L<>();
        String line = "";
        Set<Character> s = new Set1L<>();
        for (int i = 0; i < SEPARATORS.length(); i++) {
            s.add(SEPARATORS.charAt(i));
        }

        while (!in.atEOS()) {
            line = in.nextLine();
            int position = 0;
            while (line.length() > 0) {
                String potentialEntry = nextWordOrSeparator(line, position);
                line = line.substring(potentialEntry.length());
                if (!s.contains(potentialEntry.charAt(0))) {
                    tokens.enqueue(potentialEntry);
                }
            }
        }
        tokens.enqueue(END_OF_INPUT);

        return tokens;
    }

    public static final String END_OF_INPUT = "### END OF INPUT ###";
    private static final String SEPARATORS = " \t\n\r";

    /**
     * @param line
     *            String of characters read from user input txt file.
     * @return wordLine {@code Queue<String>} with only "valid" words.
     * @requires {@code String} line only contains words with separators defined
     *           in the list of wordSeparators.
     * @ensures {@code Queue<String>} will only contain valid words w/o
     *          separators.
     */
    public static String nextWordOrSeparator(String line,
            Set<Character> separators) {
        String word = "";
        int idx = 0;
        boolean first = separators.contains(line.charAt(0));
        boolean encounter = separators.contains(line.charAt(idx));
        while (idx < line.length() && first == encounter) {
            word += Character.toString(line.charAt(idx));
            idx++;
            if (idx < line.length()) {
                encounter = separators.contains(line.charAt(idx));
            }
        }
        return word.toLowerCase();
    }

}
