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
public final class LargestPrefix {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private LargestPrefix() {
    }

    public static String largestPrefix(String[] strs) {

        StringBuilder sb = new StringBuilder();

        if (strs.length == 0) {
            return sb.toString();
        } else {

            int min = strs[0].length();

            for (int i = 1; i < strs.length; i++) {
                min = Math.min(min, strs[i].length());
            }

            for (int i = 0; i < min; i++) {
                char check = strs[0].charAt(i);
                for (String str : strs) {
                    if (str.charAt(i) != check) {
                        return sb.toString();
                    }
                }
                sb.append(check);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        String[] arr = { "panda", "pancreas", "palace", "pam" };
        out.println(largestPrefix(arr));
        in.close();
        out.close();
    }

}
