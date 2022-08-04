import components.queue.Queue;
import components.queue.Queue1L;

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

    public static boolean valueOfBoolExpr(Queue<String> tokens) {
        boolean value = false; // Initialize to false variable
        if (!tokens.front().equals("### END OF INPUT ###")) { // Check if we still need to recurse
            if (tokens.front().equals("NOT")) { // If tokens.dequeue() == "NOT"
                tokens.dequeue(); // dequeue "NOT"
                tokens.dequeue(); // dequeue "("
                value = !valueOfBoolExpr(tokens); // NOT the exp
                tokens.dequeue(); // dequeue ")"
            } else if (tokens.front().equals("(")) { // If tokens.dequeue() == "("
                tokens.dequeue(); // dequeue "("
                value = valueOfBoolExpr(tokens); // followed by bool-exp grab the value.
                if (tokens.dequeue().equals("AND")) { // then binary-op either "AND" or "OR"
                    value = value && valueOfBoolExpr(tokens); // and previous and following exp together
                } else {
                    value = value || valueOfBoolExpr(tokens); // or previous and following exp together
                }
                tokens.dequeue(); // dequeue ")"
            } else if (tokens.front().equals("T")) {
                tokens.dequeue();
                return true; // must be true could return here.
            } else {
                tokens.dequeue();
                return false; // must be false could return here.
            }
        }
        return value;
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
        Queue<String> tokens = new Queue1L<>();
        tokens.enqueue("NOT");
        tokens.enqueue("(");
        tokens.enqueue("F");
        tokens.enqueue(")");
        tokens.enqueue("### END OF INPUT ###");
        System.out.println(valueOfBoolExpr(tokens));

        tokens.clear();
        tokens.enqueue("NOT");
        tokens.enqueue("(");
        tokens.enqueue("F");
        tokens.enqueue("AND");
        tokens.enqueue("T");
        tokens.enqueue(")");
        tokens.enqueue("### END OF INPUT ###");
        System.out.println(valueOfBoolExpr(tokens));

        tokens.clear();
        tokens.enqueue("T");
        tokens.enqueue("OR");
        tokens.enqueue("F");
        tokens.enqueue("AND");
        tokens.enqueue("NOT");
        tokens.enqueue("(");
        tokens.enqueue("F");
        tokens.enqueue("AND");
        tokens.enqueue("F");
        tokens.enqueue(")");
        tokens.enqueue("OR");
        tokens.enqueue("F");
        tokens.enqueue("### END OF INPUT ###");
        System.out.println(valueOfBoolExpr(tokens));

        tokens.clear();
        tokens.enqueue("T");
        tokens.enqueue("### END OF INPUT ###");
        System.out.println(valueOfBoolExpr(tokens));
    }

}
