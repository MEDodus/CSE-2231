import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * Tag cloud generator that takes input from text files and outputs user input
 * number of most frequent words in a html file using standard Java components.
 * Design principle was to exit program for most debilitating errors.
 *
 * @author Michael Dodus, Ethan Tsou
 *
 */
public final class TagCloudGeneratorJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGeneratorJava() {
    }

    /**
     * Voided method that initializes our empty set to hold all separator
     * characters.
     *
     * @return A {@code Set} containing all valid separators.
     * @ensures separators = separators * <seps>
     */
    private static Set<Character> createNewSet() {
        Set<Character> separators = new HashSet<>();
        char[] seps = { ',', '.', ':', ';', '!', '?', '"', '-', ' ', '(', ')',
                '[', ']', '/', '\\', '\t', '\n', '\r', '_', '\'', '`', '*' };
        for (int i = 0; i < seps.length; i++) {
            separators.add(seps[i]);
        }
        return separators;
    }

    /**
     * Key Comparator for {@code Map.Entry} to sort in lexographic ordering.
     *
     * @author Michael Dodus, Ethan Tsou
     *
     */
    private static final class MapPairKeyLT
            implements Comparator<Map.Entry<String, Integer>> {

        /**
         * Private constructor so this utility class cannot be instantiated.
         */
        private MapPairKeyLT() {
        }

        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    }

    /**
     * Value Comparator for {@code Map.Entry} to sort in order descending
     * integer order.
     *
     * @author Michael Dodus, Ethan Tsou
     *
     */
    private static final class MapPairValueLT
            implements Comparator<Map.Entry<String, Integer>> {

        /**
         * Private constructor so this utility class cannot be instantiated.
         */
        private MapPairValueLT() {
        }

        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }

    /**
     * @param file
     *            {@code BufferedReader} file to read txt file input from.
     * @return words {@code List<String>} with the words without separators from
     *         the txt file
     * @throws IOException
     * @requires {@code BufferedReader} file reads from a valid absolute path
     *           and the file contains words with separators defined in
     *           nextWordOrSeparator() by the list wordSeparators, whitespaces,
     *           and dashes.
     * @ensures The {@code List<String>} words is a List consisting of only
     *          valid implementer defined words.
     */
    private static List<String> getWords(BufferedReader file) {

        // Generate our set of valid separator characters.
        Set<Character> separators = createNewSet();
        List<String> words = new ArrayList<>();

        String line = null; // Must be initialized outside of the block.
        try {
            line = file.readLine();
        } catch (IOException e) {
            /*
             * We will not abort here because if there is an error the line will
             * stay null.
             */
            System.err.println("Error reading line from file. At"
                    + " end of file or corrupted file data.");
        }
        while (line != null) {

            // Get words from each line read from the file.
            int idx = 0;
            while (idx < line.length()) {
                String wordOrSeparator = nextWordOrSeparator(
                        line.substring(idx), separators);

                // Increase idx to next potential word or separator.
                idx += wordOrSeparator.length();
                if (!separators.contains(wordOrSeparator.charAt(0))) {
                    words.add(wordOrSeparator);
                }
            }
            try {
                line = file.readLine();
            } catch (IOException e) {
                System.err.println("Error reading from file.");
            }
        }
        return words;
    }

    /**
     * @param separators
     *            A {@code Set} containing characters that are considered
     *            characters.
     * @param line
     *            String of characters read from user input txt file.
     * @return wordLine {@code List<String>} with only "valid" words.
     * @requires {@code String} line only contains words with separators defined
     *           in the list of wordSeparators.
     * @ensures {@code List<String>} will only contain valid words w/o
     *          separators.
     */
    public static String nextWordOrSeparator(String line,
            Set<Character> separators) {

        // Append valid word or separator characters to StringBuilder.
        StringBuilder word = new StringBuilder();
        int idx = 0;
        while (idx < line.length()
                && separators.contains(line.charAt(0)) == separators
                        .contains(line.charAt(idx))) {
            word.append(line.charAt(idx));
            idx++;
        }
        return word.toString().toLowerCase();
    }

    /**
     * @param maxNMin
     *            {@code List<Integer>} containing the smallest and largest
     *            counts out of n words.
     * @param n
     *            integer for the number of largest counts of words to get.
     * @param words
     *            {@code List<String>} words containing every "valid" word in
     *            the document.
     * @return wordCount {@code List<Map.Entry<String, Integer>>} sorted
     *         containing all n words and their individual counts in the
     *         document sorted alphabetically.
     * @clears words
     * @ensures {@code List<Map.Entry<String, Integer>>} sorted only contains
     *          the n most frequent words given from the List<String> words.
     */
    private static List<Map.Entry<String, Integer>> getWordCount(
            List<String> words, List<Integer> maxNMin, int n) {

        // max and min word counts in map
        int min = 0;
        int max = 0;

        // Map to hold counts of all unique words.
        Map<String, Integer> wordCount = new HashMap<>();

        while (words.size() > 0) {
            String word = words.remove(0);
            if (!wordCount.containsKey(word)) {
                wordCount.put(word, 1);
            } else {
                wordCount.replace(word, wordCount.get(word) + 1);
            }
        }

        /*
         * Create a List of Map.Entry such that it is sorted by word counts
         * given by the Comparator implementing class MapPairValueLT.
         */
        List<Map.Entry<String, Integer>> sorted = new ArrayList<Entry<String, Integer>>(
                wordCount.entrySet());
        sorted.sort(new MapPairValueLT());

        /*
         * Remove all unncecessary entries. If size < n error will be reported
         * in main.
         */
        while (sorted.size() > n) {
            sorted.remove(0);
        }

        /*
         * after we sort the map we will get the smallest word count from the
         * entries we want. Also, if an empty file is used no index error is
         * thrown.
         */
        if (sorted.size() > 0 && sorted.size() == n) {
            min = sorted.get(0).getValue();
            max = sorted.get(n - 1).getValue();
        }

        maxNMin.add(max);
        maxNMin.add(min);

        // Sort in alphabetical order.
        sorted.sort(new MapPairKeyLT());

        return sorted;
    }

    /**
     * @param n
     *            Integer value input by the user detailing how many most
     *            frequented words to retrieve from the document.
     * @param html
     *            PrintWriter object to write to html file
     * @param path
     *            absolute path where html file is found
     * @requires {@code PrintWriter} html is a valid path to write to and
     *           {@code String} output is the absolute path.
     * @ensures The html document will contain a well-formatted html header
     */
    private static void printHeader(PrintWriter html, String path, int n) {
        // Array with html header tags as well as css styles for word table.
        String[] header = { "<!DOCTYPE html>", "<html>", "<head>",
                "<title>Top " + n + " words in " + path + "</title>",
                "<link rel=\"stylesheet\" "
                        + "href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/"
                        + "assignments/projects/tag-cloud-generator/data/tagcloud.css\"",
                "type=\"text/css\">",
                "<link rel=\"stylesheet\" href=\"tagcloud.css\"",
                "type=\"text/css\">", "</head>", "<body>",
                "<h2>Top " + n + " words in " + path + "</h2>", "<hr>",
                "<div class=\"cdiv\">", "<p class=\"cbox\">" };
        for (int i = 0; i < header.length; i++) {
            html.println(header[i]);
        }
    }

    /**
     * @clears words
     * @param list
     *            A {@code List<Map.Entry<String, Integer>>} list holding all n
     *            words and their associated counts.
     * @param max
     *            Integer value for the most frequented word from "N" words.
     * @param min
     *            Integer value for the least frequented word from "N" words.
     * @param html
     *            html PrintWriter object to write into a file.
     * @clears s
     * @requires {@code PrintWriter} html is a valid path to write to.
     * @ensures The html document contains a well-formatted table containing all
     *          unique words in a document with a font-size varying from
     *          smallest with least frequented words to largest for most
     *          frequented words.
     */
    private static void printWordCount(PrintWriter html,
            List<Map.Entry<String, Integer>> list, int max, int min) {

        while (list.size() > 0) {
            Map.Entry<String, Integer> entry = list.remove(0);

            int diff = max - min; // Range of count frequencies
            if (max == min) {
                diff = 1; // Ensure output works when word counts are the same.
            }
            int wordSize = entry.getValue() - min; // Adjusted word size for range

            html.print("<span style=\"cursor:default\" class=\"f"
                    + getWordSize(diff, wordSize) + "\" title=\"count: "
                    + entry.getValue() + "\">" + entry.getKey() + "</span>"
                    + " ");
        }
    }

    /**
     *
     * @param diff
     *            Integer value for the integer difference between the least
     *            counted "N" words and the most counted "N" word.
     * @param wordSize
     *            Integer value for the integer value of the least counter "N"
     *            word.
     * @requires diff >= 0 and wordSize >= 0
     * @return gets the font-size for the integers for a word passed in.
     */
    private static int getWordSize(int diff, int wordSize) {

        // css styles font-size starts at f11.
        final int cssAdjustment = 11;

        // css styles font-size ends at f48 so 37 for the options.
        final int options = 37;

        // calculate integer font-size
        int scale = (options * wordSize) / diff;

        return scale + cssAdjustment; // add adjustment so it starts at 11 not 0.
    }

    /**
     * @param html
     *            The {@code PrintWriter} html to write into our html document.
     * @requires {@code PrintWriter} html is a valid absolute path to write to.
     * @ensures The closing tags will be written into the html document to close
     *          the document.
     */
    private static void closeHTML(PrintWriter html) {
        html.println("</p>");
        html.println("</div>");
        html.println("</body>");
        html.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // Get user input for txt file path.
        System.out.print("Enter an absolute path to the input text file: ");
        String path = in.nextLine();

        // Get user input for html file path.
        System.out.print("Enter an absolute path to the output html file: ");
        String output = in.nextLine();

        // Get user input for N-number of most frequent words.
        System.out.print("Enter a value (N) of how many most-frequent "
                + "words you would like displayed: ");
        int n = 0;

        // Catch number format error a re-prompt user if invalid input arises.
        while (n <= 0) {
            System.out.print("Please enter a positive integer: ");
            n = 0;
            try {
                n = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.err.println(
                        "Error, string must be a sequence of digits [0-9] < 2^31-1");
            }
        }

        // FileReader/BufferedReader
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            System.err.println(
                    "Error file not found... invalid path. Aborting program.");
            in.close();
            return;
        }

        // Get all the words in the document in a {@code Queue<String>}
        List<String> words = getWords(file);

        // No longer need file resource open.
        try {
            file.close();
        } catch (IOException e1) {
            // We don't want a resource leak so abort here. User can try again.
            System.err.println("Error closing file: " + path);
            in.close();
            return;
        }

        List<Integer> maxNMin = new ArrayList<>();

        /*
         * Method will put all data into a List only if there are <= n words in
         * the Map the program will be aborted if not.
         */
        List<Map.Entry<String, Integer>> wordCount = getWordCount(words,
                maxNMin, n);

        // If the user inputs from an empty file or n larger than available exit.
        if (wordCount.size() != n) {
            System.err.println(
                    "Aborted program execution. Document had a total of "
                            + wordCount.size() + " words and n: " + n
                            + " was greater. Please enter an integer in range.");
            in.close();
            return;
        }

        // FileWriter/BufferedWriter/PrintWriter
        PrintWriter html = null;
        try {
            html = new PrintWriter(new BufferedWriter(new FileWriter(output)));
        } catch (IOException e) {
            System.err.println("Error printing to file... invalid path.");
            in.close();
            return;
        }

        printHeader(html, path, n);
        // Print into html documents the word count table
        printWordCount(html, wordCount, maxNMin.remove(0), maxNMin.remove(0));
        // Print closing tags into html document
        closeHTML(html);

        // Close any Writer/Reader objects open at the end of main.
        html.close();
        in.close();
    }

}
