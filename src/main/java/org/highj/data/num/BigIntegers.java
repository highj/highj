package org.highj.data.num;

import org.highj.data.num.instances.BigIntegerEnum;
import org.highj.data.num.instances.BigIntegerIntegral;
import org.highj.data.num.instances.BigIntegerNum;
import org.highj.data.num.instances.BigIntegerReal;
import org.highj.data.predicates.Pred;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;

import java.math.BigInteger;
import java.util.function.Function;

public interface BigIntegers {

    Function<BigInteger, BigInteger> sqr = x -> x.pow(2);

    Function<BigInteger, BigInteger> succ = x -> x.add(BigInteger.ONE);

    Function<BigInteger, BigInteger> pred = x -> x.subtract(BigInteger.ONE);

    Function<BigInteger, Function<BigInteger, BigInteger>> pow = a -> b -> a.pow(b.intValue());

    Pred<BigInteger> negative = bigInteger -> bigInteger.compareTo(BigInteger.ZERO) < 0;

    Pred<BigInteger> positive = bigInteger -> bigInteger.compareTo(BigInteger.ZERO) > 0;

    Pred<BigInteger> zero = bigInteger -> bigInteger.equals(BigInteger.ZERO);

    Pred<BigInteger> even = bigInteger -> !bigInteger.testBit(0);

    Pred<BigInteger> odd = bigInteger -> bigInteger.testBit(0);

    Group<BigInteger> additiveGroup = Group.create(
            BigInteger.ZERO, BigInteger::add, BigInteger::negate);

    Monoid<BigInteger> multiplicativeMonoid =
            Monoid.create(BigInteger.ONE, BigInteger::multiply);

    Semigroup<BigInteger> minSemigroup = BigInteger::min;

    Semigroup<BigInteger> maxSemigroup = BigInteger::max;

    Semigroup<BigInteger> andSemigroup = BigInteger::and;

    Monoid<BigInteger> xorMonoid = Monoid.create(
            BigInteger.ZERO, BigInteger::xor);

    Monoid<BigInteger> orMonoid = Monoid.create(
            BigInteger.ZERO, BigInteger::or);

    BigIntegerEnum enumeration = new BigIntegerEnum() {
    };

    BigIntegerNum num = new BigIntegerNum() {
    };

    BigIntegerReal real = new BigIntegerReal() {
    };

    BigIntegerIntegral integral = new BigIntegerIntegral() {
    };
}
