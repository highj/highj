package org.highj.function.repo;

import org.highj.data.compare.Eq;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.group.*;
import org.highj.util.Contracts;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.max;

public enum Integers {
    ;

    public final static Eq<Integer> eq = new Eq.JavaEq<Integer>();

    public final static F1<Integer, Integer> abs = new F1<Integer, Integer>() {
        @Override
        public Integer $(Integer integer) {
            return abs(integer);
        }
    };

    public final static F1<Integer, Integer> negate = new F1<Integer, Integer>() {
        @Override
        public Integer $(Integer integer) {
            return -integer;
        }
    };

    public final static F1<Integer, Integer> sqr = new F1<Integer, Integer>() {
        @Override
        public Integer $(Integer integer) {
            return integer * integer;
        }
    };

    public final static F1<Integer, Integer> not = new F1<Integer, Integer>() {
        @Override
        public Integer $(Integer integer) {
            return ~integer;
        }
    };

    public final static F1<Integer, Integer> succ = new F1<Integer, Integer>() {
        @Override
        public Integer $(Integer integer) {
            return integer + 1;
        }
    };

    public final static F1<Integer, Integer> pred = new F1<Integer, Integer>() {
        @Override
        public Integer $(Integer integer) {
            return integer - 1;
        }
    };

    public final static F2<Integer, Integer, Integer> add = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return a + b;
        }
    };

    public final static F2<Integer, Integer, Integer> subtract = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return a - b;
        }
    };

    public final static F2<Integer, Integer, Integer> multiply = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return a * b;
        }
    };

    public final static F2<Integer, Integer, Integer> divide = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return a / b;
        }
    };

    public final static F2<Integer, Integer, Integer> pow = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer x, Integer y) {
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
        }
    };

    public final static F2<Integer, Integer, Integer> mod = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return a % b;
        }
    };

    public final static F2<Integer, Integer, Integer> and = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return a & b;
        }
    };

    public final static F2<Integer, Integer, Integer> or = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return a | b;
        }
    };

    public final static F2<Integer, Integer, Integer> xor = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return a ^ b;
        }
    };

    public final static F2<Integer, Integer, Integer> min = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return min(a, b);
        }
    };

    public final static F2<Integer, Integer, Integer> max = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
            return max(a, b);
        }
    };

    public final static F2<Integer, Integer, Integer> gcd = new F2<Integer, Integer, Integer>() {
        @Override
        public Integer $(Integer a, Integer b) {
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
        }
    };

    public final static F1<Integer, Boolean> negative = new F1<Integer, Boolean>() {
        @Override
        public Boolean $(Integer integer) {
            return integer < 0;
        }
    };

    public final static F1<Integer, Boolean> positive = new F1<Integer, Boolean>() {
        @Override
        public Boolean $(Integer integer) {
            return integer > 0;
        }
    };

    public final static F1<Integer, Boolean> zero = new F1<Integer, Boolean>() {
        @Override
        public Boolean $(Integer integer) {
            return integer == 0;
        }
    };

    public final static F1<Integer, Boolean> even = new F1<Integer, Boolean>() {
        @Override
        public Boolean $(Integer integer) {
            return (integer & 1) == 0;
        }
    };

    public final static F1<Integer, Boolean> odd = new F1<Integer, Boolean>() {
        @Override
        public Boolean $(Integer integer) {
            return (integer & 1) == 1;
        }
    };

    public final static Group<Integer> additiveGroup = new Group<Integer>(){
        @Override
        public F1<Integer, Integer> inverse() {
            return negate;
        }

        @Override
        public Integer identity() {
            return 0;
        }

        @Override
        public F2<Integer, Integer, Integer> dot() {
            return add;
        }
    };

    public final static Monoid<Integer> multiplicativeMonoid = new Monoid<Integer>(){
        @Override
        public Integer identity() {
            return 1;
        }

        @Override
        public F2<Integer, Integer, Integer> dot() {
            return multiply;
        }
    };

    public final static Monoid<Integer> minMonoid = new Monoid<Integer>(){
        @Override
        public Integer identity() {
            return Integer.MAX_VALUE;
        }

        @Override
        public F2<Integer, Integer, Integer> dot() {
            return min;
        }
    };

    public final static Monoid<Integer> maxMonoid = new Monoid<Integer>(){
        @Override
        public Integer identity() {
            return Integer.MIN_VALUE;
        }

        @Override
        public F2<Integer, Integer, Integer> dot() {
            return max;
        }
    };

    public final static Semigroup<Integer> xorSemigroup = () -> xor;
}
