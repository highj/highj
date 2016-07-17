package org.highj.data.num.instances;

import org.highj.typeclass0.num.Num;

import java.math.BigInteger;

public interface BigIntegerNum extends Num<BigInteger> {

    @Override
    default BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    default BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    default BigInteger times(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    default BigInteger negate(BigInteger a) {
        return a.negate();
    }

    @Override
    default BigInteger abs(BigInteger a) {
        return a.abs();
    }

    @Override
    default BigInteger signum(BigInteger a) {
        return BigInteger.valueOf(a.signum());
    }

    @Override
    default BigInteger fromBigInteger(BigInteger a) {
        return a;
    }
}
