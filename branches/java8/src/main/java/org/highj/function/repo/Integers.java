package org.highj.function.repo;

import org.highj.data.compare.Eq;
import org.highj.typeclass0.group.*;
import org.highj.util.Contracts;

import java.util.function.Function;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.max;

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
        while(y > 0) {
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
        int x = max(absA,absB);
        int y = min(absA,absB);
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

    public final static Group<Integer> additiveGroup = new Group<Integer>(){
        @Override
        public Integer inverse(Integer x) {
            return -x;
        }

        @Override
        public Integer identity() {
            return 0;
        }

        @Override
        public Integer dot(Integer x, Integer y) {
            return x + y;
        }
    };

    public final static Monoid<Integer> multiplicativeMonoid = new Monoid<Integer>(){
        @Override
        public Integer identity() {
            return 1;
        }

        @Override
        public Integer dot(Integer x, Integer y) {
            return x * y;
        }
    };

    public final static Monoid<Integer> minMonoid = new Monoid<Integer>(){
        @Override
        public Integer identity() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Integer dot(Integer x, Integer y) {
            return Math.min(x, y);
        }
    };

    public final static Monoid<Integer> maxMonoid = new Monoid<Integer>(){
        @Override
        public Integer identity() {
            return Integer.MIN_VALUE;
        }

        @Override
        public Integer dot(Integer x, Integer y) {
            return Math.max(x, y);
        }
    };

    public final static Semigroup<Integer> xorSemigroup = (x, y) -> x ^ y;
}
