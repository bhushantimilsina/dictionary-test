import java.util.stream.IntStream;

public class Tests {

    public static void main(String[] args) {

        // call the test methods here
        testGetSeqOfAlphabets();

        testSubstring();
    }

    static void testGetSeqOfAlphabets() {
        IntStream.rangeClosed(65, 90).forEach(c-> System.out.print(Character.toString(c)));
        System.out.println();
    }

    static void testSubstring() {
        String word = "Apple";
        System.out.println(word.substring(0,1));
    }
}
