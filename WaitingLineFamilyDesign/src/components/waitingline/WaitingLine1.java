package components.waitingline;

import java.util.Iterator;

import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;

/**
 * A class.
 *
 * @author Michael Dodus, Ethan Tsou
 *
 * @param <T>
 */
public class WaitingLine1<T> extends WaitingLineSecondary<T> {

    /**
     * Private member, holds all data in Queue.
     */
    private Queue<T> rep;

    /**
     * Private member, keeps track of non-matching entries.
     */
    private Set<T> container;

    /**
     * Create an initial representation of this.
     */
    private void createNewRep() {
        this.rep = new Queue1L<>();
        this.container = new Set1L<>();
    }

    /**
     * Public constructor for this.
     */
    public WaitingLine1() {
        this.createNewRep();
    }

    /**
     * Adds {@code T} x to the WaitingLine.
     */
    @Override
    public void addToLine(T x) {
        assert !this.container.contains(x);

        this.rep.enqueue(x);
        this.container.add(x);
    }

    /**
     * Removes {@code T} from the front of the WaitingLine.
     */
    @Override
    public T removeFromLine() {
        assert this.rep.length() > 0;

        T x = this.rep.dequeue();
        this.container.remove(x);
        return x;
    }

    /**
     * Reports the length of the WaitingLine.
     */
    @Override
    public int length() {

        return this.rep.length();
    }

    /**
     * Clears the WaitingLine back to an initial representation.
     */
    @Override
    public void clear() {
        this.createNewRep();
    }

    @SuppressWarnings("unchecked")
    @Override
    public final WaitingLine<T> newInstance() {

        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void transferFrom(WaitingLine<T> source) {
        assert source != null : "Violation of:" + " source is not null";
        assert source != this : "Violation of:" + " source is not this";
        assert source instanceof WaitingLine1<?> : ""
                + "Violation of: source is of dynamic"
                + " type WaitingLine1<?>";

        WaitingLine1<T> instance = (WaitingLine1<T>) source;
        this.rep = instance.rep;
        this.container = instance.container;
        instance.createNewRep();

    }

    /**
     * Returns an iterator over the WaitingLine.
     */
    @Override
    public Iterator<T> iterator() {

        return this.rep.iterator();
    }

}
