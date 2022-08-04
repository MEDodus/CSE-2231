import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
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
public final class NNtoString extends NaturalNumber2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    public NNtoString(int i) {
        super(i);
    }

    @Override
    public String toString() {
        String s = "";
        if (!this.isZero()) {
            int digit = this.divideBy10();
            s = this.toString() + Integer.toString(digit);
            this.multiplyBy10(digit);
        }
        return s;
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        NaturalNumber n = new NNtoString(12345);
        out.println(n.toString());
        in.close();
        out.close();
    }

}
