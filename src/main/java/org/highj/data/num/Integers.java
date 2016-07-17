package org.highj.data.num;

import org.highj.data.num.instances.IntegerEnum;
import org.highj.data.num.instances.IntegerIntegral;
import org.highj.data.num.instances.IntegerNum;
import org.highj.data.num.instances.IntegerReal;
import org.highj.data.predicates.Pred;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.util.Contracts;

import java.util.function.Function;

import static java.lang.Math.*;

/**
 * Useful operations and type classes of integers.
 */
public interface Integers {

    /**
     * Negates an integer.
     */
    Function<Integer, Integer> negate = x -> -x;

    /**
     * Squares an integer.
     */
    Function<Integer, Integer> sqr = x -> x * x;

    /**
     * Negates an integer bitwise.
     */
    Function<Integer, Integer> not = x -> ~x;

    /**
     * Increases an integer by one.
     */
    Function<Integer, Integer> succ = x -> x + 1;

    /**
     * Decreases an integer by one.
     */
    Function<Integer, Integer> pred = x -> x - 1;

    /**
     * Calculates the y-th power of x.
     */
    Function<Integer, Function<Integer, Integer>> pow = x -> y -> {
        Contracts.require(y >= 0, "exponent must be non-negative");
        int result = 1;
        int factor = x;
        while (y > 0) {
            if ((y & 1) == 1) {
                result *= factor;
            }
            factor *= factor;
            y /= 2;
        }
        return result;
    };

    /**
     * Calculates the greatest common divisor of two integers.
     */
    Function<Integer, Function<Integer, Integer>> gcd = a -> b -> {
        int absA = abs(a);
        int absB = abs(b);
        int x = max(absA, absB);
        int y = min(absA, absB);
        while (y > 0) {
            int temp = x % y;
            x = y;
            y = temp;
        }
        return x;
    };

    /**
     * A {@link Pred} for testing if a number is negative.
     */
    Pred<Integer> negative = x -> x < 0;

    /**
     * A {@link Pred} for testing if a number is positive.
     */
    Pred<Integer> positive = x -> x > 0;

    /**
     * A {@link Pred} for testing if a number is zero.
     */
    Pred<Integer> zero = x -> x == 0;

    /**
     * A {@link Pred} for testing if a number is even.
     */
    Pred<Integer> even = x -> (x & 1) == 0;

    /**
     * A {@link Pred} for testing if a number is odd.
     */
    Pred<Integer> odd = x -> (x & 1) == 1;

    /**
     * The additive group of integers.
     */
    Group<Integer> additiveGroup = Group.create(0, (x, y) -> x + y, z -> -z);

    /**
     * The multiplicative monoid of integers.
     */
    Monoid<Integer> multiplicativeMonoid = Monoid.create(1, (x, y) -> x * y);

    /**
     * The minimum monoid of integers.
     */
    Monoid<Integer> minMonoid = Monoid.create(Integer.MAX_VALUE, Math::min);

    /**
     * The maximum monoid of integers.
     */
    Monoid<Integer> maxMonoid = Monoid.create(Integer.MIN_VALUE, Math::max);

    /**
     * The bitwise and monoid of integers.
     */
    Monoid<Integer> andMonoid = Monoid.create(-1, (x, y) -> x & y);

    /**
     * The bitwise or monoid of integers.
     */
    Monoid<Integer> orMonoid = Monoid.create(0, (x, y) -> x | y);

    /**
     * The bitwise xor monoid of integers.
     */
    Monoid<Integer> xorMonoid = Monoid.create(0, (x, y) -> x ^ y);

    /**
     * The Enum instance of {@link Integer}
     */
    IntegerEnum enumeration = new IntegerEnum() {
    };

    /**
     * The Num instance of {@link Integer}
     */
    IntegerNum num = new IntegerNum() {
    };

    /**
     * The Real instance of {@link Integer}
     */
    IntegerReal real = new IntegerReal() {
    };

    /**
     * The Integral instance of {@link Integer}
     */
    IntegerIntegral integral = new IntegerIntegral() {
    };
}
