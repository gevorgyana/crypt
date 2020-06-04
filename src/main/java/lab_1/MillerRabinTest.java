package lab_1;

import java.math.BigInteger;
import java.security.SecureRandom;

public class MillerRabinTest {
    private MillerRabinTest() {

    }

    static public boolean isPrime(final BigInteger number, final SecureRandom random) {
        if (BigInteger.ONE.equals(number) || BigInteger.ZERO.equals(number)) {
            return false;
        }

        final BigInteger two = BigInteger.TWO;
        final BigInteger one = BigInteger.ONE;
        if (two.equals(number)) {
            return true;
        }
        if (BigInteger.ZERO.equals(number.mod(two))) {
            return false;
        }

        // number = (2 ^ s) * d + 1
        BigInteger d = number.subtract(one);
        while (BigInteger.ZERO.equals(d.mod(two))) {
            d = d.divide(two);
        }

        final int iterations = 100;
        for (int i = 0; i < iterations; ++i) {
            BigInteger a = getRandomBigIntegerByNumber(number, random);
            final BigInteger numberSubtractOne = number.subtract(one);

            a = a.mod(numberSubtractOne).add(one);
            BigInteger r = new BigInteger(d.toString());

            a = a.modPow(r, number);

            while (!r.equals(numberSubtractOne) &&
                    !a.equals(one) &&
                    !a.equals(numberSubtractOne)) {
                a = a.multiply(a).mod(number);
                r = r.multiply(two);
            }

            if (!a.equals(numberSubtractOne) && BigInteger.ZERO.equals(r.mod(two))) {
                return false;
            }
        }
        return true;
    }

    static private BigInteger getRandomBigIntegerByNumber(final BigInteger number, final SecureRandom random) {
        while (true) {
            final  BigInteger a = new BigInteger(number.bitLength(), random);

            if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(number) < 0) {
                return a;
            }
        }
    }
}
