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

    public final static Group<BigInteger> additiveGroup = new Group<BigInteger>(){
        @Override
        public BigInteger inverse(BigInteger x) {
            return x.negate();
        }

        @Override
        public BigInteger identity() {
            return BigInteger.ZERO;
        }

        @Override
        public BigInteger dot(BigInteger x, BigInteger y) {
            return x.add(y);
        }
    };

    public final static Monoid<BigInteger> multiplicativeMonoid = new Monoid<BigInteger>(){
        @Override
        public BigInteger identity() {
            return BigInteger.ONE;
        }

        @Override
        public BigInteger dot(BigInteger x, BigInteger y) {
            return x.multiply(y);
        }
    };

    public final static Semigroup<BigInteger> minSemigroup = BigInteger::min;

    public final static Semigroup<BigInteger> maxSemigroup = BigInteger::max;

    public final static Semigroup<BigInteger> xorSemigroup = BigInteger::xor;
}
