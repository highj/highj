package org.highj.data.num.instances;

import org.highj.data.tuple.T2;
import org.highj.typeclass0.num.Integral;

import java.math.BigInteger;

public interface BigIntegerIntegral extends BigIntegerReal, BigIntegerEnum, Integral<BigInteger> {

    @Override
    default T2<BigInteger, BigInteger> quotRem(BigInteger a, BigInteger b) {
        BigInteger[] dr = a.divideAndRemainder(b);
        return T2.of(dr[0], dr[1]);
    }

    @Override
    default BigInteger toBigInteger(BigInteger a) {
        return a;
    }

}
