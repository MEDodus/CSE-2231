import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import components.map.Map;
import components.naturalnumber.NaturalNumber;
import components.xmltree.XMLTree;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author P. Bucci
 */
public final class ZyBooks {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private ZyBooks() {
        // no code needed here
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires [the salaries in map are positive] and raisePercent > 0
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    private static void giveRaise(components.map.Map<String, Integer> map,
            char initial, int raisePercent) {

        int size = map.size();
        int count = 0;

        while (count != size) {
            Map.Pair<String, Integer> pair = map.removeAny();
            if (pair.key().charAt(0) == initial) {
                double multiple = (100 + raisePercent) / 100.00;
                int salary = (int) (multiple * pair.value());
                map.add(pair.key(), salary);
            } else {
                map.add(pair.key(), pair.value());
            }
            count++;
        }
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires <pre>
     * [the salaries in map are positive]  and  raisePercent > 0  and
     * [the dynamic types of map and of all objects reachable from map
     *  (including any objects returned by operations (such as entrySet() and,
     *  from there, iterator()), and so on, recursively) support all
     *  optional operations]
     * </pre>
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    private static void giveRaise(java.util.Map<String, Integer> map,
            char initial, int raisePercent) {

        for (java.util.Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().charAt(0) == initial) {
                double multiple = (100 + raisePercent) / 100.00;
                int salary = (int) (multiple * entry.getValue());
                entry.setValue(salary);
            }
        }

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
     *            the command line arguments; unused here... used here actually
     */
    public static void main(String[] args) throws IOException {

        BufferedReader input = new BufferedReader(new FileReader(args[0]));
        PrintWriter output = new PrintWriter(
                new BufferedWriter(new FileWriter(args[1])));

        String s = input.readLine();
        while (s != null) {
            output.println(s);
            s = input.readLine();
        }

        input.close();
        output.close();
    }

}
