package org.highj.function.repo;

import org.highj.data.compare.Eq;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.group.*;

import java.math.BigInteger;

public enum BigIntegers {
    ;

    public final static Eq<BigInteger> eq = new Eq.JavaEq<BigInteger>();

    public final static F1<BigInteger, BigInteger> abs = new F1<BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger bigInteger) {
            return bigInteger.abs();
        }
    };

    public final static F1<BigInteger, BigInteger> negate = new F1<BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger bigInteger) {
            return bigInteger.negate();
        }
    };

    public final static F1<BigInteger, BigInteger> sqr = new F1<BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger bigInteger) {
            return bigInteger.pow(2);
        }
    };

    public final static F1<BigInteger, BigInteger> not = new F1<BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger bigInteger) {
            return bigInteger.not();
        }
    };

    public final static F1<BigInteger, BigInteger> succ = new F1<BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger bigInteger) {
            return bigInteger.add(BigInteger.ONE);
        }
    };

    public final static F1<BigInteger, BigInteger> pred = new F1<BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger bigInteger) {
            return bigInteger.subtract(BigInteger.ONE);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> add = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.add(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> subtract = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.subtract(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> multiply = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.multiply(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> divide = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.divide(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> pow = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.pow(b.intValue());
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> mod = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.mod(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> and = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.and(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> or = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.or(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> xor = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.xor(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> min = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.min(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> gcd = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.gcd(b);
        }
    };

    public final static F2<BigInteger, BigInteger, BigInteger> max = new F2<BigInteger, BigInteger, BigInteger>() {
        @Override
        public BigInteger $(BigInteger a, BigInteger b) {
            return a.max(b);
        }
    };

    public final static F1<BigInteger, Boolean> negative = new F1<BigInteger, Boolean>() {
        @Override
        public Boolean $(BigInteger bigInteger) {
            return bigInteger.compareTo(BigInteger.ZERO) < 0;
        }
    };

    public final static F1<BigInteger, Boolean> positive = new F1<BigInteger, Boolean>() {
        @Override
        public Boolean $(BigInteger bigInteger) {
            return bigInteger.compareTo(BigInteger.ZERO) > 0;
        }
    };

    public final static F1<BigInteger, Boolean> zero = new F1<BigInteger, Boolean>() {
        @Override
        public Boolean $(BigInteger bigInteger) {
            return bigInteger.equals(BigInteger.ZERO);
        }
    };

    public final static F1<BigInteger, Boolean> even = new F1<BigInteger, Boolean>() {
        @Override
        public Boolean $(BigInteger bigInteger) {
            return !bigInteger.testBit(0);
        }
    };

    public final static F1<BigInteger, Boolean> odd = new F1<BigInteger, Boolean>() {
        @Override
        public Boolean $(BigInteger bigInteger) {
            return bigInteger.testBit(0);
        }
    };

    public final static Group<BigInteger> additiveGroup = new GroupAbstract<BigInteger>(add, BigInteger.ZERO, negate);

    public final static Monoid<BigInteger> multiplicativeMonoid = new MonoidAbstract<BigInteger>(multiply, BigInteger.ONE);

    public final static Semigroup<BigInteger> minSemigroup = new SemigroupAbstract<BigInteger>(min);

    public final static Semigroup<BigInteger> maxSemigroup = new SemigroupAbstract<BigInteger>(max);

    public final static Semigroup<BigInteger> xorSemigroup = new SemigroupAbstract<BigInteger>(xor);
}
