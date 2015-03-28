package org.highj.data.functions;

import org.highj.typeclass0.compare.Eq;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.util.Contracts;

import java.util.function.Function;

import static java.lang.Math.*;

public enum Integers {
    ;

    public final static Eq<Integer> eq = new Eq.JavaEq<>();

    public final static Function<Integer, Integer> negate = x -> -x;

    public final static Function<Integer, Integer> sqr = x -> x * x;

    public final static Function<Integer, Integer> not = x -> ~x;

    public final static Function<Integer, Integer> succ = x -> x + 1;

    public final static Function<Integer, Integer> pred = x -> x - 1;

    public final static Function<Integer, Function<Integer, Integer>> pow = x -> y -> {
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

    public final static Function<Integer, Function<Integer, Integer>> gcd = a -> b -> {
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

    public final static Function<Integer, Boolean> negative = x -> x < 0;

    public final static Function<Integer, Boolean> positive = x -> x > 0;

    public final static Function<Integer, Boolean> zero = x -> x == 0;

    public final static Function<Integer, Boolean> even = x -> (x & 1) == 0;

    public final static Function<Integer, Boolean> odd = x -> (x & 1) == 1;

    public final static Group<Integer> additiveGroup = Group.create(0, (x, y) -> x + y, z -> -z);

    public final static Monoid<Integer> multiplicativeMonoid = Monoid.create(1, (x, y) -> x * y);

    public final static Monoid<Integer> minMonoid = Monoid.create(Integer.MAX_VALUE, Math::min);

    public final static Monoid<Integer> maxMonoid = Monoid.create(Integer.MIN_VALUE, Math::max);

    public final static Semigroup<Integer> xorSemigroup = (x, y) -> x ^ y;
}
