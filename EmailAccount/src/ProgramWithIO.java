import java.io.FileNotFoundException;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ProgramWithIO {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ProgramWithIO() {
    }

    public static String getNumerals(String line) {

        int[] times = new int[26];

        for (int i = 0; i < 26; i++) {
            times[i] = 0;
        }

        for (int j = 0; j < line.length(); j++) {
            int idx = line.charAt(j) - 'a';
            times[idx]++;
        }

        int zero = times[25];
        times[25] = 0;
        times[4] -= zero; // e
        times[17] -= zero; // r
        times[14] -= zero; // o

        int two = times[22];
        times[22] = 0;
        times[19] -= two;
        times[14] -= two;

        int six = times[23];
        times[23] = 0;
        times[18] -= six;
        times[8] -= six;

        int eight = times[6];
        times[6] = 0;
        times[4] -= eight;
        times[8] -= eight;
        times[7] -= eight;
        times[19] -= eight;

        int four = times[20];
        times[20] = 0;
        times[5] -= four;
        times[14] -= four;
        times[17] -= four;

        int five = times[5];
        times[5] = 0;
        times[8] -= five;
        times[21] -= five;
        times[4] -= five;

        int three = times[17];
        times[17] = 0;
        times[19] -= three;
        times[7] -= three;
        times[4] -= (three * 2);

        int seven = times[21];
        times[21] = 0;
        times[4] -= (seven * 2);
        times[18] -= seven;
        times[13] -= seven;

        int one = times[14];
        times[14] = 0;
        times[13] -= one;
        times[4] -= one;

        int nine = times[8];
        times[8] = 0;
        times[13] -= (nine * 2);
        times[4] -= nine;

        int[] nums = new int[10];
        nums[0] = zero;
        nums[1] = one;
        nums[2] = two;
        nums[3] = three;
        nums[4] = four;
        nums[5] = five;
        nums[6] = six;
        nums[7] = seven;
        nums[8] = eight;
        nums[9] = nine;

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            int time = nums[i];
            for (int j = 0; j < time; j++) {
                char num = Character.forDigit(i, 10);
                str.append(num);
            }
        }
        return str.toString();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
//        Scanner in = new Scanner(new File("file.txt"));
//
//        int count = 0;
//        String camel = "";
//        while (in.hasNext()) {
//            String line = in.nextLine();
//
//            if (count == 0 && !line.isEmpty()) {
//                camel = line.toLowerCase();
//                count++;
//            } else if (!line.isEmpty()) {
//                camel = camel + line.substring(0, 1).toUpperCase()
//                        + line.substring(1).toLowerCase();
//            } else {
//                System.out.println(camel);
//                count = 0;
//                camel = "";
//            }
//
//        }
//        System.out.println(camel);

        // zerozeroonetwofivesixeight 0012568
        String input = "iisrtoweezoterohenevizoxfg";

        String line = "rewvnosizfootheneenseentfnvenneeovswviiesoiuwontnetreeveg";
        // System.out.println(getNumerals(line));

        System.out.println(input);

    }

}
