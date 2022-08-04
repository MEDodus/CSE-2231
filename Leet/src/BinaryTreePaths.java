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
public final class BinaryTreePaths {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BinaryTreePaths() {
    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> path = new ArrayList<>();

        if (root != null){


        }
        path.
        return path;
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(), new TreeNode(1)),
                new TreeNode(3, new TreeNode(4, new TreeNode(), new TreeNode()),
                        new TreeNode(5)));

        in.close();
        out.close();
    }

}
