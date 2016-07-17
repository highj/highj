package org.highj.data.num.instances;

import org.highj.typeclass0.num.Num;

import java.math.BigInteger;

public interface IntegerNum extends Num<Integer> {
    @Override
    default Integer add(Integer a, Integer b) {
        return a+b;
    }

    @Override
    default Integer subtract(Integer a, Integer b) {
        return a-b;
    }

    @Override
    default Integer times(Integer a, Integer b) {
        return a*b;
    }

    @Override
    default Integer negate(Integer a) {
        return -a;
    }

    @Override
    default Integer abs(Integer a) {
        return Math.abs(a);
    }

    @Override
    default Integer signum(Integer a) {
        return (int)(Math.signum(a));
    }

    @Override
    default Integer fromBigInteger(BigInteger a) {
        return a.intValue();
    }
}
