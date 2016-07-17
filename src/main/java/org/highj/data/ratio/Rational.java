package org.highj.data.ratio;

import java.math.BigInteger;

/**
 * @author clintonselke
 */
public class Rational {
    private Ratio<BigInteger> _ratio;

    private Rational(Ratio<BigInteger> ratio) {
        this._ratio = ratio;
    }

    public static Rational rational(Ratio<BigInteger> ratio) {
       return rational(ratio.numerator(), ratio.denominator());
    }

    public static Rational rational(BigInteger n, BigInteger d) {
        if (d.compareTo(BigInteger.ZERO) == 0) {
            throw new ArithmeticException("denominator is zero");
        }
        BigInteger gcd = (d.compareTo(BigInteger.ZERO) < 0)
                ? n.gcd(d).negate()
                : n.gcd(d);
        return new Rational(Ratio.ratio(n.divide(gcd), d.divide(gcd)));
    }

    public Ratio<BigInteger> ratio() {
        return _ratio;
    }

    @Override
    public String toString() {
        return _ratio.numerator() + " % " + _ratio.denominator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rational rational = (Rational) o;

        return _ratio.numerator().equals(rational._ratio.numerator()) &&
                _ratio.denominator().equals(rational._ratio.denominator());

    }

    @Override
    public int hashCode() {
        return _ratio.numerator().hashCode() + 17 * _ratio.denominator().hashCode();
    }
}
