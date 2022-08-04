import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Michael Dodus, Ethan Tsou
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        boolean found = false;
        BinaryTree<T> lt = t.newInstance();
        BinaryTree<T> rt = t.newInstance();

        // Never in an empty tree. . . size > 0
        if (t.size() > 0) {
            // If the root is x then we found it change the boolean value.
            if (t.root().equals(x)) {
                found = true;
                // If not check either side of the tree.
            } else if (x.compareTo(t.root()) < 0) {
                T root = t.disassemble(lt, rt);
                found = isInTree(lt, x);
                t.assemble(root, lt, rt);
            } else {
                T root = t.disassemble(lt, rt);
                found = isInTree(rt, x);
                t.assemble(root, lt, rt);
            }
        }
        return found;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        BinaryTree<T> lt = t.newInstance();
        BinaryTree<T> rt = t.newInstance();

        // Recurse until we hit the empty tree.
        if (t.size() > 0) {
            // Check whether we are going to left or right tree.
            if (x.compareTo(t.root()) < 0) {
                T root = t.disassemble(lt, rt);
                insertInTree(lt, x);
                t.assemble(root, lt, rt);
            } else {
                T root = t.disassemble(lt, rt);
                insertInTree(rt, x);
                t.assemble(root, lt, rt);
            }
        } else {
            // Magic happens here, insert x as the root of the empty tree.
            t.assemble(x, lt, rt);
        }
    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        BinaryTree<T> lt = t.newInstance();
        BinaryTree<T> rt = t.newInstance();

        // requires size > 0 so this is valid.
        T root = t.disassemble(lt, rt);
        T min = root;

        // Always recurse into left tree (since l < root < r)
        if (lt.size() > 0) {
            min = removeSmallest(lt);
            t.assemble(root, lt, rt);
        } else {
            // If there are any values in a r tree below the smallest value.
            t.transferFrom(rt);
        }
        return min;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        BinaryTree<T> lt = t.newInstance();
        BinaryTree<T> rt = t.newInstance();

        // Valid since x is in labels(t) thus |t| > 0
        T remove = t.disassemble(lt, rt);
        T root = remove;

        if (x.equals(remove)) {
            if (rt.size() > 0) {
                T min = removeSmallest(rt);
                t.assemble(min, lt, rt);
            } else {
                t.transferFrom(lt);
            }
        } else if (x.compareTo(remove) < 0) {
            remove = removeFromTree(lt, x);
            t.assemble(root, lt, rt);
        } else {
            remove = removeFromTree(rt, x);
            t.assemble(root, lt, rt);
        }
        return remove;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        // Dynamic object implementation for our BinaryTree.
        this.tree = new BinaryTree1<>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        insertInTree(this.tree, x);
    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {
        // Does not break kernel purity since this is the BinaryTree's own size()
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
