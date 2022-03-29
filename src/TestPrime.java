import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.IntStream;

public class TestPrime {
    public static void main(String[] args) {
        //BigInteger num = BigInteger.ZERO;
        final SecureRandom secureRandom = new SecureRandom();
        final Random random = new Random();

        long startTime = System.nanoTime();
        /*for (int i = 0; i < 10000; i++) {
            num = BigInteger.probablePrime(64, random);
        }*/
        IntStream.range(0, 10000).forEach(c -> BigInteger.probablePrime(64, random));

        long stopTime = System.nanoTime();
        System.out.println("Time taken: " + (stopTime - startTime) / 1000000 + "ms");
    }
}
