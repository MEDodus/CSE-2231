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
public final class ReverseInt {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ReverseInt() {
    }

    private static int reverse(int x) {
        int rev = 0;
        boolean greater = false;
        int lastRev = 0;
        if (x > 0) {
            while (x != 0) {
                lastRev = rev;
                rev = (rev * 10) + x % 10;
                x /= 10;
                greater = (rev / 10) != lastRev;
                if (greater) {
                    return 0;
                }
            }
        } else if (x < 0) {
            while (x != 0) {
                lastRev = rev;
                rev = -Math.abs((rev * 10) + (x % 10));
                x /= 10;
                greater = (rev / 10) != lastRev;
                if (greater) {
                    return 0;
                }
            }
        }
        return rev;
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        int x = -1234567899;
        out.println(reverse(x));
        in.close();
        out.close();
    }

}
