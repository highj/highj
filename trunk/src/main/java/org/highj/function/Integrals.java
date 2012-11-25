package org.highj.function;

import org.highj.typeclass.group.*;
import org.highj.util.Contracts;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public interface Integrals<N extends Number> {

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

    public final static Group<Integer> additiveGroup = new GroupAbstract<Integer>(add, 0, negate);

    public final static Monoid<Integer> multiplicativeMonoid = new MonoidAbstract<Integer>(multiply, 1);

    public final static Monoid<Integer> minMonoid = new MonoidAbstract<Integer>(min, Integer.MAX_VALUE);

    public final static Monoid<Integer> maxMonoid = new MonoidAbstract<Integer>(max, Integer.MIN_VALUE);

    public final static Semigroup<Integer> xorSemigroup = new SemigroupAbstract<Integer>(xor);

}
