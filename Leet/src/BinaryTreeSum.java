import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.tree.Tree;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class BinaryTreeSum {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BinaryTreeSum() {
    }

    private static int sum(BinaryTree<String> t) {
        int x = 0;
        int sum = 0;
        BinaryTree<String> lt = t.newInstance();
        BinaryTree<String> rt = t.newInstance();
        if (t.size() > 0) {
            x = Integer.parseInt(t.disassemble(lt, rt));
            sum = x + sum(lt) + sum(rt);
            t.assemble(Integer.toString(x), lt, rt);
        }
        return x;
    }

    private static void reverse(Tree<String> t) {
        if (t.size() > 0) {
            Sequence<Tree<String>> children = t.newSequenceOfTree();
            String root = t.disassemble(children);
            for (int i = 0; i < children.length(); i++) {
                reverse(children.entry(i));
            }
            children.flip();
            t.assemble(root, children);
        }
    }

    private static boolean isGreater(BinaryTree<Integer> t, int x) {

        boolean greater = true;
        if (t.size() > 0) {
            BinaryTree<Integer> lt = t.newInstance();
            BinaryTree<Integer> rt = t.newInstance();
            int root = t.disassemble(lt, rt);
            greater = x > root && isGreater(lt, x) && isGreater(rt, x);
            t.assemble(root, lt, rt);
        }

        return greater;
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        BinaryTree<Integer> t = new BinaryTree1<>();
        Sequence<BinaryTree<Integer>> seq = new Sequence1L<>();

        for (int i = 0; i < 3; i++) {
            BinaryTree<Integer> temp = t.newInstance();
            BinaryTree<Integer> temp2 = t.newInstance();
            BinaryTree<Integer> top = t.newInstance();
            temp.assemble((int) (Math.random()) * 20, t.newInstance(),
                    t.newInstance());
            temp2.assemble((int) (Math.random()) * 20, t.newInstance(),
                    t.newInstance());
            top.assemble((int) (Math.random()) * 20, temp, temp2);
            seq.add(i, top);
        }

        BinaryTree<Integer> temp1 = t.newInstance();
        BinaryTree<Integer> temp2 = t.newInstance();
        int root = seq.entry(0).disassemble(temp1, temp2);
        temp1.assemble((int) (Math.random() * 20), seq.entry(1),
                t.newInstance());
        temp2.assemble((int) (Math.random() * 20), t.newInstance(),
                seq.entry(2));
        t.assemble(root, temp1, temp2);

        out.println(t);
        out.println(isGreater(t, 14));

//        Tree<String> t = new Tree1<>();
//        Sequence<Tree<String>> children = t.newSequenceOfTree();
//        for (int i = 0; i < 5; i++) {
//            Tree<String> temp = new Tree1<>();
//            temp.assemble("" + i, t.newSequenceOfTree());
//            children.add(i, temp);
//        }
//        t.assemble("0", children);
//        out.println(t);
//        reverse(t);
//        out.println(t);

        in.close();
        out.close();
    }

}
