package components.waitingline;

/**
 * {@code QueueKernel} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code Queue} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Reports the front of line of {@code this}.
     *
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code frontOfLine}
     * @requires this /= <>
     * @ensures <frontOfLine> is prefix of this
     */
    T frontOfLine();

    /**
     * Replaces the front of {@code this} with {@code x}, and returns the old
     * front.
     *
     * @param x
     *            the new front entry
     * @return the old front entry
     * @aliases reference {@code x}
     * @updates this
     * @requires this /= <> and x is not contained in this
     * @ensures <pre>
     * <replaceFront> is prefix of #this  and
     * this = <x> * #this[1, |#this|)
     * </pre>
     */
    T replaceFrontOfLine(T x);

    /**
     * Concatenates ("appends") {@code q} to the end of {@code this}.
     *
     * @param q
     *            the {@code WaitingLine} to be appended to the end of
     *            {@code this}
     * @updates this
     * @requires {@code q} is not a subset of {@code this}.
     * @clears q
     * @ensures this = #this * #q
     */
    void appendLine(WaitingLine<T> q);

    /**
     * Adds to waitingline {@code x} to position {@code pos} if x is not
     * contained in {@code this}.
     *
     * @updates this
     * @param x
     *            the {@code T} to be added to the position in Line.
     * @param pos
     *            the position in which to add {@code T} to.
     * @requires x is not contained in this.
     * @aliases reference {@code x}
     * @ensures <pre>
     * this = <beginning> * x * <end> where beginning and end.
     * contain zero or more elements.
     * </pre>
     */
    void addPersonToLine(T x, int pos);

}
