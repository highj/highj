package org.highj.data.num.instances;

import org.highj.data.ord.Ordering;
import org.highj.data.ratio.Ratio;
import org.highj.data.ratio.Rational;
import org.highj.typeclass0.num.Real;

import java.math.BigInteger;

public interface BigIntegerReal extends BigIntegerNum, Real<BigInteger> {

    @Override
    default Rational toRational(BigInteger a) {
        return Rational.rational(Ratio.ratio(a, BigInteger.ONE));
    }

    @Override
    default Ordering cmp(BigInteger one, BigInteger two) {
        return Ordering.compare(one, two);
    }
}
