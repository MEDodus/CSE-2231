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
public final class Add {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Add() {
    }

    private static String add(String s1, String s2, int carry) {

        int sum = 0;
        String result = "";
        if (s1.length() > 0 && s2.length() > 0) {
            int d1 = Character.getNumericValue(s1.charAt(s1.length() - 1));
            int d2 = Character.getNumericValue(s2.charAt(s2.length() - 1));
            if (carry == 1) {
                if (d1 + d2 + carry > 9) {
                    carry = 1;
                    sum = (d1 + d2 + carry) % 10;
                } else {
                    sum = d1 + d2 + carry;
                    carry = 0;
                }
            } else {
                if (d1 + d2 > 9) {
                    carry = 1;
                    sum = (d1 + d2) % 10;
                } else {
                    carry = 0;
                    sum = d1 + d2;
                }
            }
            result = Integer.toString(sum);
            result = add(s1.substring(0, s1.length() - 1),
                    s2.substring(0, s2.length() - 1), carry) + result;
        } else if (s1.length() > 0 || s2.length() > 0) {
            if (s1.length() > 0) {
                int d1 = Character.getNumericValue(s1.charAt(s1.length() - 1));
                int d2 = Character.getNumericValue(result.charAt(0));
                if (carry == 1) {
                    if (d1 + d2 + carry > 9) {
                        carry = 1;
                        sum = (d1 + d2 + carry) % 10;
                    } else {
                        sum = d1 + d2 + carry;
                        carry = 0;
                    }
                } else {
                    if (d1 + d2 > 9) {
                        carry = 1;
                        sum = (d1 + d2) % 10;
                    } else {
                        carry = 0;
                        sum = d1 + d2;
                    }
                }
                if (carry == 1) {
                    result = "1" + result;
                    result = add(s1.substring(0, s1.length() - 1), result, 0);
                } else {
                    result = s1.substring(0, s1.length() - 1)
                            + Integer.toString(sum) + result.substring(1);
                }
            } else if (s2.length() > 0) {
                int d1 = Character.getNumericValue(s2.charAt(s2.length() - 1));
                int d2 = Character.getNumericValue(result.charAt(0));
                if (carry == 1) {
                    if (d1 + d2 + carry > 9) {
                        carry = 1;
                        sum = (d1 + d2 + carry) % 10;
                    } else {
                        sum = d1 + d2 + carry;
                        carry = 0;
                    }
                } else {
                    if (d1 + d2 > 9) {
                        carry = 1;
                        sum = (d1 + d2) % 10;
                    } else {
                        carry = 0;
                        sum = d1 + d2;
                    }
                }
                if (carry == 1) {
                    result = "1" + result;
                    result = add(s2.substring(0, s2.length() - 1), result, 0);
                } else {
                    result = s2.substring(0, s2.length() - 1)
                            + Integer.toString(sum) + result.substring(1);
                }
            }
        }
        return result;
    }

    private static String addition(String s1, String s2, int carry) {

        String result = "";
        if (s1.length() > 0 && s2.length() > 0) {
            int digit1 = s1.length() > 0
                    ? Character.getNumericValue(s1.charAt(s1.length() - 1))
                    : 0;
            int digit2 = s2.length() > 0
                    ? Character.getNumericValue(s2.charAt(s2.length() - 1))
                    : 0;
            int sum = 0;

            if (digit1 + digit2 + carry > 9) {
                carry = 1;
                sum = (digit1 + digit2 + carry) % 10;
            } else {
                sum = digit1 + digit2 + carry;
                carry = 0;
            }

            if (s1.length() > s2.length()) {
                result = add(s1)
            } else if (s2.length() > s1.length()) {

            } else {

            }
        }
        return result;
    }

    private static String fibonacci(String input) {
        int size = Integer.parseInt(input) + 1;
        String[] strs = new String[size];
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                strs[i] = "0";
            } else if (i == 1) {
                strs[i] = "1";
            } else {
                strs[i] = add(strs[i - 1], strs[i - 2], 0);
            }
        }

        return strs[size - 1];
    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        String s = "julia@hackerrank.com";
        boolean b = s.matches("@hackerrank.com");
        out.println(b);
    }

}
