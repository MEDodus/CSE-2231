import components.sequence.Sequence;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.tree.Tree;
import components.tree.Tree1;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class TreeWithRecursion {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TreeWithRecursion() {
    }

    private static int recursionIsTrue(Tree<String> t, boolean b) {

        int size = 0;
        if (b) {
            size = size(t);
        } else {
            size = loopSize(t);
        }
        return size;
    }

    private static int loopSize(Tree<String> t) {
        int size = 0;
        for (String s : t) {
            size++;
        }
        return size;
    }

    private static int size(Tree<String> t) {
        int size = 0;
        if (t.height() > 0) {
            size += 1;

            for (int i = 0; i < t.numberOfSubtrees(); i++) {
                Sequence<Tree<String>> s = t.newSequenceOfTree();
                String root = t.disassemble(s);
                size += size(s.entry(i));
                t.assemble(root, s);
            }
        }
        return size;
    }

    private static int height(Tree<String> t) {
        int height = 0;
        int max = 0;
        if (t.size() > 0) {
            for (int i = 0; i < t.numberOfSubtrees(); i++) {
                Sequence<Tree<String>> s = t.newSequenceOfTree();
                String root = t.disassemble(s);
                int sub = height(s.entry(i));
                if (sub > max) {
                    max = sub;
                }
                t.assemble(root, s);
            }

            height = 1 + max;
        }
        return height;
    }

    private static int max(Tree<Integer> t) {
        int max = t.root();
        if (t.size() > 0) {
            for (int i = 0; i < t.numberOfSubtrees(); i++) {
                Sequence<Tree<Integer>> s = t.newSequenceOfTree();
                int root = t.disassemble(s);
                int sub = max(s.entry(i));
                if (sub > max) {
                    max = sub;
                }
                t.assemble(root, s);
            }
        }
        return max;
    }

    private static <T> String treeToString(Tree<T> t) {
        String str = "";
        if (t.size() > 0) {
            str = str + t.root() + "(";
            for (int i = 0; i < t.numberOfSubtrees(); i++) {
                Sequence<Tree<T>> s = t.newSequenceOfTree();
                T root = t.disassemble(s);
                str = str + treeToString(s.entry(i));
                t.assemble(root, s);
            }
            str = str + ")";
        }
        return str;
    }

    public static Tree<Integer> copy(Tree<Integer> t) {
        Tree<Integer> copy = t.newInstance();
        if (t.size() > 0) {
            int root = t.root();
            Sequence<Tree<Integer>> s = t.newSequenceOfTree();
            Sequence<Tree<Integer>> s2 = t.newSequenceOfTree();
            for (int i = 0; i < t.numberOfSubtrees(); i++) {
                root = t.disassemble(s);
                Tree<Integer> subCopy = copy(s.entry(i));
                t.assemble(root, s);
                s2.add(0, subCopy);
                copy.assemble(root, s2);
            }
        }
        return copy;
    }

    /**
     *
     * @param root
     * @param args
     * @return Sequence<Tree<String>> of same dynamic type as Tree<String> root
     */
    private static Sequence<Tree<String>> createFromArgs(Tree<String> root,
            String... args) {
        Sequence<Tree<String>> s = root.newSequenceOfTree();
        for (String str : args) {
            Tree<String> t = new Tree1<>();
            t.assemble(str, t.newSequenceOfTree());
            s.add(0, t);
        }
        return s;
    }

    private static Sequence<Tree<Integer>> createFromArgs(Tree<Integer> root,
            Integer... args) {
        Sequence<Tree<Integer>> s = root.newSequenceOfTree();
        for (Integer x : args) {
            Tree<Integer> t = new Tree1<>();
            t.assemble(x, t.newSequenceOfTree());
            s.add(0, t);
        }
        return s;
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // First subtree
        Tree<String> t1 = new Tree1<>();
        t1.assemble("t1",
                createFromArgs(t1, "sub1", "sub2", "sub3", "sub4", "sub5"));

        out.println("t1.size() = " + t1.size());

        // Second subtree
        Tree<String> t2 = new Tree1<>();
        t2.assemble("t2", createFromArgs(t2, "sub1", "sub2", "sub3"));

        // Third subtree
        Tree<String> t3 = new Tree1<>();
        t3.assemble("t3", createFromArgs(t3, "sub1", "sub2", "sub3"));

        // Fourth subtree
        Tree<String> t4 = new Tree1<>();
        t4.assemble("t4", createFromArgs(t4, "sub1"));

        // Assemble all four together
        Tree<String> t = new Tree1<>();
        Sequence<Tree<String>> s = t.newSequenceOfTree();
        s.add(0, t1);
        s.add(0, t2);
        s.add(0, t3);
        s.add(0, t4);
        t.assemble("t", s);
        out.println(t);
        out.println();

        out.println("Iterative: " + recursionIsTrue(t, true));
        out.println(t);
        out.println();
        out.println("Recursive: " + recursionIsTrue(t, false));
        out.println(t);
        out.println();

        out.println(height(t));

        // First subtree
        Tree<Integer> z1 = new Tree1<>();
        z1.assemble(1, createFromArgs(z1, 3, 6, 2, 3, 1));

        out.println("t1.size() = " + t1.size());

        // Second subtree
        Tree<Integer> z2 = new Tree1<>();
        z2.assemble(3, createFromArgs(z2, 3, 4, 7));

        // Third subtree
        Tree<Integer> z3 = new Tree1<>();
        z3.assemble(3, createFromArgs(z3, 5, 8, 3));

        // Fourth subtree
        Tree<Integer> z4 = new Tree1<>();
        z4.assemble(13, createFromArgs(z4, 12));

        // Assemble all four together
        Tree<Integer> z = new Tree1<>();
        Sequence<Tree<Integer>> c = z.newSequenceOfTree();
        c.add(0, z1);
        c.add(0, z2);
        c.add(0, z3);
        c.add(0, z4);
        z.assemble(0, c);

        out.println();
        out.println(max(z));

        out.println();
        out.println(treeToString(z));

        Tree<Integer> copy = z.newInstance();

        out.println(z);

        copy = copy(z);

        out.println(copy);

        in.close();
        out.close();
    }

}
