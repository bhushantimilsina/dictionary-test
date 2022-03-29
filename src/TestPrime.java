import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class TestPrime {
    public static void main(String[] args) {
        BigInteger num = BigInteger.ZERO;
        final SecureRandom secureRandom = new SecureRandom();
        final Random random = new Random();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            num = BigInteger.probablePrime(64, random);
        }
        long stopTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (stopTime - startTime) + "ms");
    }
}
