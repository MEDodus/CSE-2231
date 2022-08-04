public class MostNDigits {

    /**
     * Private constructor so this utility class can't be instantiated.
     */
    private MostNDigits() {
    }

    public static int atMostNGivenDigitSet(String[] digits, int n) {
        int numDigits = digits.length;
        // get num from tens
        int ones = digits.length;

        if (n > 0) {
            numDigits += atMostNGivenDigitSet(digits, n / 10);
        }

        return numDigits;
    }

}
