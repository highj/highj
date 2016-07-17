package org.highj.data.num;

import org.highj.data.num.instances.BigIntegerEnum;
import org.highj.data.num.instances.BigIntegerIntegral;
import org.highj.data.num.instances.BigIntegerNum;
import org.highj.data.num.instances.BigIntegerReal;
import org.highj.data.predicates.Pred;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass0.num.Num;
import org.highj.typeclass0.num.Real;

import java.math.BigInteger;
import java.util.function.Function;

/**
 * Useful operations and type classes of {@link BigInteger}s.
 */
public interface BigIntegers {

    /**
     * The square function.
     */
    Function<BigInteger, BigInteger> sqr = x -> x.pow(2);

    /**
     * The successor function.
     */
    Function<BigInteger, BigInteger> succ = x -> x.add(BigInteger.ONE);

    /**
     * The predecessor function.
     */
    Function<BigInteger, BigInteger> pred = x -> x.subtract(BigInteger.ONE);

    /**
     * The power function.
     */
    Function<BigInteger, Function<BigInteger, BigInteger>> pow = a -> b -> a.pow(b.intValue());

    /**
     * The predicate to test if the number is negative.
     */
    Pred<BigInteger> negative = bigInteger -> bigInteger.compareTo(BigInteger.ZERO) < 0;

    /**
     * The predicate to test if the number is positiv.
     */
    Pred<BigInteger> positive = bigInteger -> bigInteger.compareTo(BigInteger.ZERO) > 0;

    /**
     * The predicate to test if the number is zero.
     */
    Pred<BigInteger> zero = bigInteger -> bigInteger.equals(BigInteger.ZERO);

    /**
     * The predicate to test if the number is even.
     */
    Pred<BigInteger> even = bigInteger -> !bigInteger.testBit(0);

    /**
     * The predicate to test if the number is odd.
     */
    Pred<BigInteger> odd = bigInteger -> bigInteger.testBit(0);

    /**
     * The additive {@link Group} of {@link BigInteger}.
     */
    Group<BigInteger> additiveGroup = Group.create(
            BigInteger.ZERO, BigInteger::add, BigInteger::negate);

    /**
     * The multiplicative {@link Monoid} of {@link BigInteger}.
     */
    Monoid<BigInteger> multiplicativeMonoid =
            Monoid.create(BigInteger.ONE, BigInteger::multiply);

    /**
     * The minimum {@link Semigroup} of {@link BigInteger}.
     */
    Semigroup<BigInteger> minSemigroup = BigInteger::min;

    /**
     * The maximum {@link Semigroup} of {@link BigInteger}.
     */
    Semigroup<BigInteger> maxSemigroup = BigInteger::max;

    /**
     * The and {@link Semigroup} of {@link BigInteger}.
     */
    Semigroup<BigInteger> andSemigroup = BigInteger::and;

    /**
     * The xor {@link Monoid} of {@link BigInteger}.
     */
    Monoid<BigInteger> xorMonoid = Monoid.create(
            BigInteger.ZERO, BigInteger::xor);

    /**
     * The or {@link Monoid} of {@link BigInteger}.
     */
    Monoid<BigInteger> orMonoid = Monoid.create(
            BigInteger.ZERO, BigInteger::or);

    /**
     * The {@link org.highj.typeclass0.num.Enum} instance of {@link BigInteger}.
     */
    BigIntegerEnum enumeration = new BigIntegerEnum() {
    };

    /**
     * The {@link Num} instance of {@link BigInteger}.
     */
    BigIntegerNum num = new BigIntegerNum() {
    };

    /**
     * The {@link Real} instance of {@link BigInteger}.
     */
    BigIntegerReal real = new BigIntegerReal() {
    };

    /**
     * The {@link org.highj.typeclass0.num.Integral} instance of {@link BigInteger}.
     */
    BigIntegerIntegral integral = new BigIntegerIntegral() {
    };
}
