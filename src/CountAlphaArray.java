import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CountAlphaArray {

    private static final String LINE = "--------------------------------------------------------------------------------";
    private static final String[] alphabets = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static int[] counts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    // Custom dict file path
    private static final String filepathProp = System.getProperty("dict.filepath");

    public static void main(String[] args) throws IOException {

        // Read the file and total number of words for each alphabet
        final Path filepath = filepathProp != null ? Paths.get(filepathProp) : Paths.get("data/english3.txt");

        List<String> wordList = Files.readAllLines(filepath);
        System.out.println(LINE);
        System.out.printf("\tTotal Words: %s\n", NumberFormat.getNumberInstance(Locale.US).format(wordList.size()));
        System.out.println(LINE);

        final long startTime = System.currentTimeMillis();
        for (final String prefix : alphabets) {
            countAlpha(wordList, prefix);
        }
        final long stopTime = System.currentTimeMillis();

        int prefixIndex = 0;
        for (final String alphabet : alphabets) {
            System.out.printf("\t%s --> %s\n", alphabet, NumberFormat.getNumberInstance(Locale.US).format(counts[prefixIndex++]));
        }

        System.out.println("\n" + LINE);
        System.out.println("\tTime taken: " + (stopTime - startTime) + " ms");
        System.out.println(LINE);
        System.out.println();
        System.exit(0);
    }

    private static void countAlpha(final List<String> wordList, final String prefix) {
        final int prefixIndex = switch (prefix.toUpperCase(Locale.ROOT)) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            case "E" -> 4;
            case "F" -> 5;
            case "G" -> 6;
            case "H" -> 7;
            case "I" -> 8;
            case "J" -> 9;
            case "K" -> 10;
            case "L" -> 11;
            case "M" -> 12;
            case "N" -> 13;
            case "O" -> 14;
            case "P" -> 15;
            case "Q" -> 16;
            case "R" -> 17;
            case "S" -> 18;
            case "T" -> 19;
            case "U" -> 20;
            case "V" -> 21;
            case "W" -> 22;
            case "X" -> 23;
            case "Y" -> 24;
            case "Z" -> 25;
            default -> -1;
        };
        for (final String word : wordList) {
            if (word.toUpperCase(Locale.ROOT).startsWith(prefix))
                ++counts[prefixIndex];
        }
    }
}
