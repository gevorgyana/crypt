package lab_1;

import junit.framework.TestCase;

import java.math.BigInteger;
import java.security.SecureRandom;

public class FermatTestTest extends TestCase {
    private final SecureRandom random = new SecureRandom();

    public void testIsPrime() {
        final int bitLength = 512;

        int iteration = 100;
        for (int i = 0; i < iteration; ++i) {
            final BigInteger primeNumber = BigInteger.probablePrime(bitLength, random);
            final BigInteger noPrimeNumber = primeNumber.add(BigInteger.ONE);

            assertTrue(FermatTest.isPrime(primeNumber, random));
            assertFalse(FermatTest.isPrime(noPrimeNumber, random));
        }
    }
}