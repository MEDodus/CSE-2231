import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine4;
import components.utilities.Reporter;

/**
 * Tag cloud generator that takes input from text files and outputs user input
 * number of most frequent words in a html file.
 *
 * @author Michael Dodus, Ethan Tsou
 *
 */
public final class TagCloudGenerator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Voided method that initializes our empty set to hold all separator
     * characters.
     *
     * @return A {@code Set} containing all valid separators.
     * @ensures separators = separators * <seps>
     */
    private static Set<Character> createNewSet() {
        Set<Character> separators = new Set1L<>();
        char[] seps = { ',', '.', ':', ';', '!', '?', '"', '-', ' ', '(', ')',
                '[', ']', '/', '\\', '\t', '\n', '\r', '_', '\'', '`', '*' };
        for (int i = 0; i < seps.length; i++) {
            separators.add(seps[i]);
        }
        return separators;
    }

    /**
     * Key Comparator for {@code Map.Pair} to sort in lexographic ordering.
     *
     * @author Michael Dodus, Ethan Tsou
     *
     */
    private static final class MapPairKeyLT
            implements Comparator<Map.Pair<String, Integer>> {

        /**
         * Private constructor so this utility class cannot be instantiated.
         */
        private MapPairKeyLT() {
        }

        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o1.key().compareToIgnoreCase(o2.key());
        }
    }

    /**
     * Value Comparator for {@code Map.Pair} to sort in order descending integer
     * order.
     *
     * @author Michael Dodus, Ethan Tsou
     *
     */
    private static final class MapPairValueLT
            implements Comparator<Map.Pair<String, Integer>> {

        /**
         * Private constructor so this utility class cannot be instantiated.
         */
        private MapPairValueLT() {
        }

        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * @param file
     *            SimpleReader to read txt file input from.
     * @return words {@code Queue<String>} with the words without separators
     *         from the txt file
     * @requires {@code SimpleReader} file reads from a valid absolute path and
     *           the file contains words with separators defined in
     *           separateLines() by the list wordSeparators, whitespaces, and
     *           dashes.
     * @ensures The {@code Queue<String>} words is a Queue consisting of only
     *          valid implementer defined words.
     */
    private static Queue<String> getWords(SimpleReader file) {

        // Generate our set of valid separator characters.
        Set<Character> separators = createNewSet();
        Queue<String> words = new Queue1L<>();

        while (!file.atEOS()) {

            String line = file.nextLine();

            // Get words from each line read from the file.
            // Queue<String> wordLine = separateLines(line);
            int idx = 0;
            while (idx < line.length()) {
                String wordOrSeparator = nextWordOrSeparator(
                        line.substring(idx), separators);

                // Increase idx to next potential word or separator.
                idx += wordOrSeparator.length();
                if (!separators.contains(wordOrSeparator.charAt(0))) {
                    words.enqueue(wordOrSeparator);
                }
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
     * @return wordLine {@code Queue<String>} with only "valid" words.
     * @requires {@code String} line only contains words with separators defined
     *           in the list of wordSeparators.
     * @ensures {@code Queue<String>} will only contain valid words w/o
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
     * @param words
     *            {@code Queue<String>} words containing every "valid" word in
     *            the document.
     * @return wordCount {@code Map<String, Integer>} wordCount containing all
     *         words and their individual counts in the document.
     * @clears words
     * @ensures {@code Map<String, Integer>} wordCount only contains words once
     *          retrieved from the Queue<String> and their associated "count" in
     *          the document.
     */
    private static Map<String, Integer> getWordCount(Queue<String> words) {

        // Map to hold counts of all unique words.
        Map<String, Integer> wordCount = new Map1L<>();

        while (words.length() > 0) {
            String word = words.dequeue();
            if (!wordCount.hasKey(word)) {
                wordCount.add(word, 1);
            } else {
                wordCount.replaceValue(word, wordCount.value(word) + 1);
            }
        }
        return wordCount;
    }

    /**
     *
     * @param wordCount
     *            A {@code Map} containing all unique words and their respective
     *            counts in the read document.
     * @param n
     *            Integer value input by the user detailing how many frequented
     *            words to display.
     * @param maxNMin
     *            A {@code Sequence} containing the max frequency and min
     *            frequency words in a document.
     * @updates maxNMin to contain the max and min words.
     * @clears wordCount
     * @requires n >= 0
     * @ensures maxNMin = <1st most counted word> * <nth most counted word> and
     *          wordCount = <> and lexoCount = n most-counted words.
     * @return lexoSort A {@code SortingMachine} holding the most frequented "N"
     *         words and in lexographic order.
     */
    private static SortingMachine<Map.Pair<String, Integer>> getLexoAndCount(
            Map<String, Integer> wordCount, int n, Sequence<Integer> maxNMin) {

        // SortingMachine to compare specifically the counts of words.
        SortingMachine<Map.Pair<String, Integer>> countSort = new SortingMachine4<>(
                new MapPairValueLT());

        // Add all words to the SortingMachine
        while (wordCount.size() > 0) {
            countSort.add(wordCount.removeAny());
        }

        // Using quicksort here.
        countSort.changeToExtractionMode();

        /*
         * Now transfer only "N" sorted pairs into a SortingMachine for
         * lexographic ordering.
         */
        SortingMachine<Map.Pair<String, Integer>> lexoSort = new SortingMachine4<>(
                new MapPairKeyLT());

        // Add "N" words to the new SortingMachine.
        int count = 0;

        while (count < n) {
            if (count == 0) {
                Map.Pair<String, Integer> max = countSort.removeFirst();
                lexoSort.add(max);
                maxNMin.add(0, max.value());
            } else if (count == n - 1) {
                Map.Pair<String, Integer> min = countSort.removeFirst();
                lexoSort.add(min);
                maxNMin.add(0, min.value());
            } else {
                lexoSort.add(countSort.removeFirst());
            }
            count++;
        }

        // clear countSort free-up memory we don't need it.
        countSort.clear();

        // Sort lexoSort in alphabetical order.
        lexoSort.changeToExtractionMode();

        return lexoSort;
    }

    /**
     * @param n
     *            Integer value input by the user detailing how many most
     *            frequented words to retrieve from the document.
     * @param html
     *            SimpleWriter object to write to html file
     * @param path
     *            absolute path where html file is found
     * @requires {@code SimpleWriter} html is a valid path to write to and
     *           {@code String} output is the absolute path.
     * @ensures The html document will contain a well-formatted html header
     */
    private static void printHeader(SimpleWriter html, String path, int n) {
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
     * @param s
     *            A {@code SortingMachine} to retrieve our correctly ordered
     *            words from.
     * @param max
     *            Integer value for the most frequented word from "N" words.
     * @param min
     *            Integer value for the least frequented word from "N" words.
     * @param html
     *            html SimpleWriter object to write into a file.
     * @clears s
     * @requires {@code SimpleWriter} html is a valid path to write to.
     * @ensures The html document contains a well-formatted table containing all
     *          unique words in a document with a font-size varying from
     *          smallest with least frequented words to largest for most
     *          frequented words.
     */
    private static void printWordCount(SimpleWriter html,
            SortingMachine<Map.Pair<String, Integer>> s, int max, int min) {

        while (s.size() > 0) {
            Map.Pair<String, Integer> pair = s.removeFirst();

            int diff = max - min; // Range of count frequencies
            int wordSize = pair.value() - min; // Adjusted word size for range

            html.print("<span style=\"cursor:default\" class=\"f"
                    + getWordSize(diff, wordSize) + "\" title=\"count: "
                    + pair.value() + "\">" + pair.key() + "</span>" + " ");
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
     *            The {@code SimpleWriter} html to write into our html document.
     * @requires {@code SimpleWriter} html is a valid absolute path to write to.
     * @ensures The closing tags will be written into the html document to close
     *          the document.
     */
    private static void closeHTML(SimpleWriter html) {
        html.println("</p>");
        html.println("</div>");
        html.println("</body>");
        html.println("<html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Get user input for txt file path.
        out.print("Enter an absolute path to the input text file: ");
        String path = in.nextLine();

        final int dotTXT = 3; // length of string containing characters "txt"
        final int dotHTML = 4; // length of string containing characters "html"

        //check if the input is valid txt file extension.
        boolean checkTXT = path.substring(path.length() - dotTXT, path.length())
                .equals("txt");
        Reporter.assertElseFatalError(checkTXT, "Error has occurred. File: "
                + path + " does not satisfy .txt extension.");

        // Get user input for html file path.
        out.print("Enter an absolute path to the output html file: ");
        String output = in.nextLine();

        // check if the output is a valid html file extension.
        boolean checkHTML = output
                .substring(output.length() - dotHTML, output.length())
                .equals("html");
        Reporter.assertElseFatalError(checkHTML, "Error has occurred. File: "
                + output + " does not satisfy .html extension.");

        // Get user input for N-number of most frequent words.
        out.print("Enter a value (N) of how many most-frequent "
                + "words you would like displayed: ");
        int n = Integer.parseInt(in.nextLine());

        while (n <= 0) {
            out.print("Please enter a positive integer: ");
            n = Integer.parseInt(in.nextLine());
        }

        /*
         * Try to create files from valid extensions and throw and
         * AssertionError if the path does not exist.
         */
        SimpleReader file = new SimpleReader1L(path);
        SimpleWriter html = new SimpleWriter1L(output);

        // Get all the words in the document in a {@code Queue<String>}
        Queue<String> words = getWords(file);
        Map<String, Integer> wordCount = getWordCount(words);

        /*
         * Sort {@code Queue<String>} in lexographic order and print html page
         * with Queue to match with keys in our {@code Map<String, Integer>}
         * wordCount.
         */
        printHeader(html, path, n);

        // Sequence holding the min and max counts for the most frequent words.
        Sequence<Integer> maxNMin = new Sequence1L<>();

        // Get SortingMachine with "N" words with the most frequent counts.
        SortingMachine<Map.Pair<String, Integer>> s = getLexoAndCount(wordCount,
                n, maxNMin);

        // Print into html documents the word count table
        printWordCount(html, s, maxNMin.remove(1), maxNMin.remove(0));

        // Print closing tags into html document
        closeHTML(html);

        // Close any Writer/Reader objects open at the end of main
        html.close();
        in.close();
        out.close();
    }

}
