package lab_1;

import java.math.BigInteger;

public class ExtendedEuclideanAlgorithm {
    private ExtendedEuclideanAlgorithm() {

    }

    // returnable value
    // [0] - gcd
    // [1] - x
    // [2] - y
    // where x and y is
    // a * x + b * y = gcd(a, b)
    static public BigInteger[] gcd(BigInteger a, BigInteger b) {
        BigInteger[] result = new BigInteger[3];
        if (a.equals(BigInteger.ZERO)) {
            result[0] = b;
            result[1] = BigInteger.ZERO;
            result[2] = BigInteger.ONE;
            return result;
        }

        final BigInteger[] sub_result = gcd(b.mod(a), a);
        result[0] = sub_result[0];
        result[1] = sub_result[2].subtract(b.divide(a).multiply(sub_result[1]));
        result[2] = sub_result[1];
        return result;
    }
}
