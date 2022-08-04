import java.util.ArrayList;
import java.util.List;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Sum {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */

    private static int sum(int n) {
        int sum = 0;
        if (n > 0) {
            if (n % 5 != 0 && n % 7 != 0) {
                sum = n;
                sum += sum(n - 1);
            } else {
                sum += sum(n - 1);
            }
        }
        return sum;
    }

    private static int sigma(int n) {
        int sum = 0;
        for (int i = n; i > 0; i--) {
            if (n % 5 != 0 && n % 7 != 0) {
                sum += n;
            }
            n--;
        }
        return sum;
    }

    private static int comb(int n) {
        int perm = 0;
        if (n > 2) {
            perm += comb(n - 2) + comb(n - 1);
        } else {
            perm = n;
        }
        return perm;
    }

    public static List<String> generateParenthesis(int n) {

        List<String> l = new ArrayList<>();

        if (n > 2) {
            List<String> temp = generateParenthesis(n - 1);
            for (int i = 0; i < temp.size(); i++) {
                l.add("(" + temp.get(i) + ")");
                l.add("()" + temp.get(i));
                l.add(temp.get(i) + "()");
            }
            l.remove(l.size() - 1);
        } else if (n == 2) {
            l.add("(())");
            l.add("()()");
        } else if (n == 1) {
            l.add("()");
        }
        return l;
    }

    private Sum() {
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter an integer value from 1-17: ");
        int x = in.nextInteger();
        while (x > 0 && x < 18) {
            out.println();
            out.println();
            List<String> paren = generateParenthesis(x);
            for (int i = 0; i < paren.size(); i++) {
                out.println(paren.get(i));
            }
            out.println("Number of unique combinations: " + paren.size());
            out.print("Enter an integer value from 1-17: ");
            x = in.nextInteger();
        }

        in.close();
        out.close();
    }

}
