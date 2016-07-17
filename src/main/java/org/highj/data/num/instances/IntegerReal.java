package org.highj.data.num.instances;

import org.highj.data.ord.Ordering;
import org.highj.data.ratio.Ratio;
import org.highj.data.ratio.Rational;
import org.highj.typeclass0.num.Real;

import java.math.BigInteger;

public interface IntegerReal extends IntegerNum, Real<Integer> {
    @Override
    default Rational toRational(Integer a) {
        return Rational.rational(Ratio.ratio(BigInteger.valueOf(a), BigInteger.ONE));
    }

    @Override
    default Ordering cmp(Integer one, Integer two) {
        return Ordering.compare(one, two);
    }
}
