import components.naturalnumber.NaturalNumber;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author P. Bucci
 */
public final class HelloWorld {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private HelloWorld() {
        // no code needed here
    }

    private static int countDigits(NaturalNumber n, int d) {
        int cnt = 0;
        int digit = n.divideBy10();
        if (!n.isZero()) {
            cnt += countDigits(n, d);
        }
        if (digit == d) {
            cnt++;
        }
        return cnt;
    }

    private static int maxBranchingFactor(XMLTree t) {
        int max = 0;

        if (t.isTag()) {
            max = t.numberOfChildren();
            for (int i = 0; i < t.numberOfChildren(); i++) {
                int factor = maxBranchingFactor(t.child(i));
                if (factor > max) {
                    max = factor;
                }
            }
        }
        return max;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

//        NaturalNumber n = new NaturalNumber2(20200000);
//        out.println(countDigits(n, 0));

        Queue<String> s = new Queue1L<>();
        Queue<Integer> i = new Queue1L<>();
        out.println(s.equals(i));
        s.enqueue("1");
        i.enqueue(1);
        out.println(s.equals(i));

        out.close();
    }

}
