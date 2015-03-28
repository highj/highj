package org.highj.data.functions;

import org.highj.typeclass0.group.*;

import java.math.BigInteger;
import java.util.function.Function;

public enum BigIntegers {
    ;

    public final static Function<BigInteger, BigInteger> sqr = x -> x.pow(2);

    public final static Function<BigInteger, BigInteger> succ = x -> x.add(BigInteger.ONE);

    public final static Function<BigInteger, BigInteger> pred = x -> x.subtract(BigInteger.ONE);

    public final static Function<BigInteger, Function<BigInteger, BigInteger>> pow = a -> b -> a.pow(b.intValue());

    public final static Function<BigInteger, Boolean> negative = bigInteger -> bigInteger.compareTo(BigInteger.ZERO) < 0;

    public final static Function<BigInteger, Boolean> positive = bigInteger -> bigInteger.compareTo(BigInteger.ZERO) > 0;

    public final static Function<BigInteger, Boolean> zero = bigInteger -> bigInteger.equals(BigInteger.ZERO);

    public final static Function<BigInteger, Boolean> even = bigInteger -> !bigInteger.testBit(0);

    public final static Function<BigInteger, Boolean> odd = bigInteger -> bigInteger.testBit(0);

    public final static Group<BigInteger> additiveGroup = Group.create(
            BigInteger.ZERO, BigInteger::add, BigInteger::negate);

    public final static Monoid<BigInteger> multiplicativeMonoid =
            Monoid.create(BigInteger.ONE, BigInteger::multiply);

    public final static Semigroup<BigInteger> minSemigroup = BigInteger::min;

    public final static Semigroup<BigInteger> maxSemigroup = BigInteger::max;

    public final static Semigroup<BigInteger> xorSemigroup = BigInteger::xor;
}
