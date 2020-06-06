package lab_1;


import java.math.BigInteger;
import java.util.Objects;


public final class MontgomeryReducer {
    private final BigInteger modulus;

    private final int reducerBits;
    private final BigInteger reciprocal;
    private final BigInteger mask;
    private final BigInteger factor;
    private final BigInteger convertedOne;

    // Must be an odd number at least 3
    public MontgomeryReducer(BigInteger modulus) {
        Objects.requireNonNull(modulus);
        if (!modulus.testBit(0) || modulus.compareTo(BigInteger.ONE) <= 0) {
            throw new IllegalArgumentException("Modulus must be an odd number at least 3");
        }
        this.modulus = modulus;

        reducerBits = (modulus.bitLength() / 8 + 1) * 8;
        BigInteger reducer = BigInteger.ONE.shiftLeft(reducerBits);
        mask = reducer.subtract(BigInteger.ONE);

        assert reducer.compareTo(modulus) > 0 && reducer.gcd(modulus).equals(BigInteger.ONE);

        reciprocal = reducer.modInverse(modulus);
        factor = reducer.multiply(reciprocal).subtract(BigInteger.ONE).divide(modulus);
        convertedOne = reducer.mod(modulus);
    }

    public BigInteger convertInMontgomeryForm(BigInteger number) {
        return number.shiftLeft(reducerBits).mod(modulus);
    }

    public BigInteger convertFromMontgomeryForm(BigInteger number) {
        return number.multiply(reciprocal).mod(modulus);
    }

    public BigInteger multiply(BigInteger first, BigInteger second) {
        assert first.signum() >= 0 && first.compareTo(modulus) < 0;
        assert second.signum() >= 0 && second.compareTo(modulus) < 0;

        BigInteger product = first.multiply(second);
        BigInteger temp = product.and(mask).multiply(factor).and(mask);
        BigInteger reduced = product.add(temp.multiply(modulus)).shiftRight(reducerBits);
        BigInteger result = reduced.compareTo(modulus) < 0 ? reduced : reduced.subtract(modulus);

        assert result.signum() >= 0 && result.compareTo(modulus) < 0;
        return result;
    }

    public BigInteger multiplyWithConvert(BigInteger first, BigInteger second) {
        final BigInteger convertedFirst = convertInMontgomeryForm(first);
        final BigInteger convertedSecond = convertInMontgomeryForm(second);
        final BigInteger result = multiply(convertedFirst, convertedSecond);
        return convertFromMontgomeryForm(result);
    }

    public BigInteger pow(BigInteger number, BigInteger exponent) {
        assert number.signum() >= 0 && number.compareTo(modulus) < 0;
        if (exponent.signum() == -1) {
            throw new IllegalArgumentException("Negative exponent");
        }

        BigInteger z = convertedOne;
        for (int i = 0, len = exponent.bitLength(); i < len; i++) {
            if (exponent.testBit(i)) {
                z = multiply(z, number);
            }
            number = multiply(number, number);
        }
        return z;
    }

    public BigInteger powWithConvert(BigInteger number, BigInteger exponent) {
        final BigInteger convertedNumber = convertInMontgomeryForm(number);
        final BigInteger result = pow(convertedNumber, exponent);
        return convertFromMontgomeryForm(result);
    }
}