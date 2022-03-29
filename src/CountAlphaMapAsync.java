import java.nio.charset.StandardCharsets;
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

public class CountAlphaMapAsync {
    private static final String LINE = "--------------------------------------------------------------------------------";

    // Custom dict file path
    private static final String filepathProp = System.getProperty("dict.filepath");

    // args0: Thread pool size
    public static void main(final String[] args) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Specify total pool size as parameter");
        final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        final ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(args[0]));
        Map<Character, Long> counterMap = new ConcurrentHashMap<>();

        char[] alphabets = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        // Initialize counterMap
        //IntStream.rangeClosed(65, 90).forEach(ch -> counterMap.put(Character.toString(ch), 0L));

        // Read the file and total number of words for each alphabet
        final Path filepath = filepathProp != null ? Paths.get(filepathProp) : Paths.get("data/english3.txt");
        List<String> wordList = Files.readAllLines(filepath, StandardCharsets.UTF_8);
        System.out.println(LINE);
        System.out.printf("\tTotal Words: %s\n", numberFormat.format(wordList.size()));
        System.out.println(LINE);

        // Count the alphabets
        final long startTime = System.nanoTime();
        //counterMap.forEach((k, v) -> executor.submit(() -> countAlpha(counterMap, wordList, k)));
        for (final char alpha : alphabets) {
            executor.submit(() -> countAlpha(counterMap, wordList, alpha));
        }
        awaitTerminationAfterShutdown(executor);
        final long stopTime = System.nanoTime();

        counterMap.forEach((k, v) -> System.out.printf("\t%s --> %s\n", k, numberFormat.format(v)));

        System.out.println("\n" + LINE);
        System.out.println("\tTime taken: " + (stopTime - startTime) / 1000000 + " ms");
        System.out.println(LINE);
        System.out.println();
        System.exit(0);
    }

    private static void countAlpha(Map<Character, Long> counterMap, final List<String> wordList, final char prefix) {
        counterMap.put(prefix, wordList.stream().filter(word -> prefix == word.charAt(0)).count());
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
