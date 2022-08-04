import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention
 *
 *             <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 *             </pre>
 *
 * @correspondence
 *
 *                 <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 *                 </pre>
 *
 * @author Michael Dodus, Ethan Tsou
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        // Empty representation: value of 0.
        this.rep = "";
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {
        this.createNewRep();
    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";
        // Use string and int concatenation
        String num = "" + i;
        if (i > 0) {
            this.rep = num;
        } else {
            this.createNewRep();
        }
    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*") : ""
                + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";
        // If the string is "0" set the rep to ""
        if (s.equals("0")) {
            this.createNewRep();
        } else {
            this.rep = s;
        }
    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        // If the NaturalNumber is 0 then don't call toString set the rep to ""
        if (n.compareTo(new NaturalNumber3()) > 0) {
            this.rep = n.toString();
        } else {
            this.createNewRep();
        }
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
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
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3 : ""
                + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";
        // How do we multiply by 10 and add a digit in String form?
        // Add the digit k to the end of the String.
        if (this.rep.equals("") && k == 0) {
            this.createNewRep();
        } else {
            this.rep = this.rep + k;
        }
    }

    @Override
    public final int divideBy10() {
        // How do we divide by 10 and remove the last digit in String form?
        // Get the last character from the String and make a substring.
        int num = 0;
        if (!this.rep.equals("")) {
            // Could also get as a character and call Character.getNumericValue()
            // Probably marginally quicker than using a string
            String digit = this.rep.substring(this.rep.length() - 1,
                    this.rep.length());
            this.rep = this.rep.substring(0, this.rep.length() - 1);
            num = Integer.parseInt(digit);
        }
        return num;
    }

    @Override
    public final boolean isZero() {
        boolean zero = false;
        // If the representation is empty it is zero set it to true
        if (this.rep.equals("")) {
            zero = true;
        }
        return zero;
    }
}
