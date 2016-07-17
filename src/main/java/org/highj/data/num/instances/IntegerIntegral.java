package org.highj.data.num.instances;

import org.highj.data.tuple.T2;
import org.highj.typeclass0.num.Integral;

import java.math.BigInteger;

public interface IntegerIntegral extends IntegerReal, IntegerEnum, Integral<Integer> {
    @Override
    default T2<Integer, Integer> quotRem(Integer a, Integer b) {
        return T2.of(a / b, a % b);
    }

    @Override
    default BigInteger toBigInteger(Integer a) {
        return BigInteger.valueOf(a);
    }
}
