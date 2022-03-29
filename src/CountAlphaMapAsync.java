import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountAlphaMapAsync {
    private static final String LINE = "--------------------------------------------------------------------------------";

    // Custom dict file path
    private static final String filepathProp = System.getProperty("dict.filepath");

    public static void main(final String[] args) throws Exception {
        final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        final ExecutorService executor = Executors.newFixedThreadPool(16);
        Map<String, Long> counterMap = new ConcurrentHashMap<>();

        // Initialize counterMap
        IntStream.rangeClosed(65, 90).forEach(ch -> counterMap.put(Character.toString(ch), 0L));

        // Read the file and total number of words for each alphabet
        final Path filepath = filepathProp != null ? Paths.get(filepathProp) : Paths.get("data/english3.txt");
        List<String> wordList = Files.readAllLines(filepath);
        System.out.println(LINE);
        System.out.printf("\tTotal Words: %s\n", numberFormat.format(wordList.size()));
        System.out.println(LINE);

        // Count the alphabets
        final long startTime = System.currentTimeMillis();
        counterMap.forEach((k, v) -> executor.submit(() -> countAlpha(counterMap, wordList, k)));
        awaitTerminationAfterShutdown(executor);
        final long stopTime = System.currentTimeMillis();

        counterMap.forEach((k, v) -> System.out.printf("\t%s --> %s\n", k, numberFormat.format(v)));

        System.out.println("\n" + LINE);
        System.out.println("\tTime taken: " + (stopTime - startTime) + " ms");
        System.out.println(LINE);
        System.out.println();
        System.exit(0);
    }

    private static void countAlpha(Map<String, Long> counterMap, final List<String> wordList, final String prefix) {
        counterMap.put(prefix, wordList.stream().filter(word -> prefix.equalsIgnoreCase(word.substring(0, 1))).count());
    }

    private static void awaitTerminationAfterShutdown(final ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (final InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
