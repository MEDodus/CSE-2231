package components.waitingline;

import java.util.Iterator;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Layered implementations of secondary methods for {@code Queue}.
 *
 * <p>
 * Assuming execution-time performance of O(1) for method {@code iterator} and
 * its return value's method {@code next}, execution-time performance of
 * {@code frontOfLine} as implemented in this class is O(1). Execution-time
 * performance of {@code replaceFrontOfLine} in this class is O(|{@code this}|).
 * Execution-time performance of {@code appendToLine} as implemented in this
 * class is O(|{@code q}|). Execution-time performance of {@code sort} as
 * implemented in this class is O(|{@code this}| log |{@code this}|) expected,
 * O(|{@code this}|^2) worst case.
 *
 * @param <T>
 *            type of {@code WaitingLine} entries
 */
public abstract class WaitingLineSecondary<T> implements WaitingLine<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /*
     * 2221/2231 assignment code deleted.
     */

    /*
     * Public members ---------------------------------------------------------
     */

    /*
     * Common methods (from Object) -------------------------------------------
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WaitingLine<?>)) {
            return false;
        }
        WaitingLine<?> w = (WaitingLine<?>) obj;
        if (this.length() != w.length()) {
            return false;
        }
        Iterator<T> it1 = this.iterator();
        Iterator<?> it2 = w.iterator();
        while (it1.hasNext()) {
            T x1 = it1.next();
            Object x2 = it2.next();
            if (!x1.equals(x2)) {
                return false;
            }
        }
        return true;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int hashCode() {
        final int samples = 2;
        final int a = 37;
        final int b = 17;
        int result = 0;
        int n = 0;
        Iterator<T> it = this.iterator();
        while (n < samples && it.hasNext()) {
            n++;
            T x = it.next();
            result = a * result + b * x.hashCode();
        }
        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }
        result.append(">");
        return result.toString();
    }

    /*
     * Other non-kernel methods -----------------------------------------------
     */

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public T frontOfLine() {

        Iterator<T> it = this.iterator();
        T x = it.next();
        return x;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public T replaceFrontOfLine(T x) {

        Queue<T> temp = new Queue1L<>();
        temp.enqueue(x);
        T replaced = this.removeFromLine();
        while (this.length() > 0) {
            temp.enqueue(this.removeFromLine());
        }
        while (temp.length() > 0) {
            this.addToLine(temp.dequeue());
        }
        return replaced;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void appendLine(WaitingLine<T> q) {

        while (q.length() > 0) {
            this.addToLine(q.removeFromLine());
        }
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void addPersonToLine(T x, int pos) {

        Queue<T> temp = new Queue1L<>();
        while (this.length() != pos) {
            temp.enqueue(this.removeFromLine());
        }
        this.addToLine(x);
        while (temp.length() > 0) {
            this.addToLine(temp.dequeue());
        }

    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int findInLine(T x) {

        T remove = this.removeFromLine();
        WaitingLine<T> temp = this.newInstance();
        temp.addToLine(x);
        while (!remove.equals(x)) {
            remove = this.removeFromLine();
            temp.addToLine(x);
        }
        int position = temp.length();
        while (temp.length() > 0) {
            this.addToLine(temp.dequeue());
        }
        return position;
    }

}
