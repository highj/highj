package org.highj.data.num;

import org.highj.data.ratio.Rational;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.num.Integral;
import org.highj.typeclass0.num.Num;
import org.highj.typeclass0.num.Real;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegersTest {

    @Test
    public void negate() {
        assertThat(Integers.negate.apply(0)).isEqualTo(0);
        assertThat(Integers.negate.apply(42)).isEqualTo(-42);
        assertThat(Integers.negate.apply(-42)).isEqualTo(42);
    }

    @Test
    public void sqr() {
        assertThat(Integers.sqr.apply(0)).isEqualTo(0);
        assertThat(Integers.sqr.apply(42)).isEqualTo(1764);
        assertThat(Integers.sqr.apply(-42)).isEqualTo(1764);
    }

    @Test
    public void not() {
        assertThat(Integers.not.apply(0)).isEqualTo(-1);
        assertThat(Integers.not.apply(42)).isEqualTo(~42);
        assertThat(Integers.not.apply(-42)).isEqualTo(~-42);
    }

    @Test
    public void succ() {
        assertThat(Integers.succ.apply(-1)).isEqualTo(0);
        assertThat(Integers.succ.apply(0)).isEqualTo(1);
        assertThat(Integers.succ.apply(42)).isEqualTo(43);
    }

    @Test
    public void pred() {
        assertThat(Integers.pred.apply(0)).isEqualTo(-1);
        assertThat(Integers.pred.apply(1)).isEqualTo(0);
        assertThat(Integers.pred.apply(43)).isEqualTo(42);
    }

    @Test
    public void pow() {
        assertThat(Integers.pow.apply(3).apply(4)).isEqualTo(81);
        assertThat(Integers.pow.apply(10).apply(2)).isEqualTo(100);
        assertThat(Integers.pow.apply(5).apply(3)).isEqualTo(125);
        assertThat(Integers.pow.apply(2).apply(8)).isEqualTo(256);
    }

    @Test
    public void gcd() {
        assertThat(Integers.gcd.apply(257).apply(111)).isEqualTo(1);
        assertThat(Integers.gcd.apply(35).apply(-42)).isEqualTo(7);
        assertThat(Integers.gcd.apply(-30).apply(66)).isEqualTo(6);
    }

    @Test
    public void negative() {
        assertThat(Integers.negative.test(0)).isFalse();
        assertThat(Integers.negative.test(42)).isFalse();
        assertThat(Integers.negative.test(-42)).isTrue();
    }

    @Test
    public void positive() {
        assertThat(Integers.positive.test(0)).isFalse();
        assertThat(Integers.positive.test(42)).isTrue();
        assertThat(Integers.positive.test(-42)).isFalse();
    }

    @Test
    public void zero() {
        assertThat(Integers.zero.test(0)).isTrue();
        assertThat(Integers.zero.test(42)).isFalse();
        assertThat(Integers.zero.test(-42)).isFalse();
    }

    @Test
    public void even() {
        assertThat(Integers.even.test(0)).isTrue();
        assertThat(Integers.even.test(42)).isTrue();
        assertThat(Integers.even.test(-42)).isTrue();
        assertThat(Integers.even.test(43)).isFalse();
        assertThat(Integers.even.test(-43)).isFalse();
    }

    @Test
    public void odd() {
        assertThat(Integers.odd.test(0)).isFalse();
        assertThat(Integers.odd.test(42)).isFalse();
        assertThat(Integers.odd.test(-42)).isFalse();
        assertThat(Integers.odd.test(43)).isTrue();
        assertThat(Integers.odd.test(-43)).isTrue();
    }

    @Test
    public void additiveGroup() {
        assertThat(Integers.additiveGroup.identity()).isEqualTo(0);
        assertThat(Integers.additiveGroup.apply(17, 23)).isEqualTo(40);
        assertThat(Integers.additiveGroup.inverse(5)).isEqualTo(-5);
        assertThat(Integers.additiveGroup.inverse(-5)).isEqualTo(5);
        assertThat(Integers.additiveGroup.inverse(0)).isEqualTo(0);
    }

    @Test
    public void multiplicativeMonoid() {
        assertThat(Integers.multiplicativeMonoid.identity()).isEqualTo(1);
        assertThat(Integers.multiplicativeMonoid.apply(17, 23)).isEqualTo(391);
    }

    @Test
    public void minMonoid() {
        assertThat(Integers.minMonoid.identity()).isEqualTo(Integer.MAX_VALUE);
        assertThat(Integers.minMonoid.apply(17, 23)).isEqualTo(17);
        assertThat(Integers.minMonoid.apply(23, 17)).isEqualTo(17);
    }

    @Test
    public void maxMonoid() {
        assertThat(Integers.maxMonoid.identity()).isEqualTo(Integer.MIN_VALUE);
        assertThat(Integers.maxMonoid.apply(17, 23)).isEqualTo(23);
        assertThat(Integers.maxMonoid.apply(23, 17)).isEqualTo(23);
    }

    @Test
    public void xorMonoid() {
        assertThat(Integers.xorMonoid.identity()).isEqualTo(0);
        assertThat(Integers.xorMonoid.apply(17, 23)).isEqualTo(6);
    }

    @Test
    public void orMonoid() {
        assertThat(Integers.orMonoid.identity()).isEqualTo(0);
        assertThat(Integers.orMonoid.apply(17, 23)).isEqualTo(17 | 23);
    }

    @Test
    public void andMonoid() {
        assertThat(Integers.andMonoid.identity()).isEqualTo(-1);
        assertThat(Integers.andMonoid.apply(17, 23)).isEqualTo(17 & 23);
    }

    @Test
    public void num() {
        Num<Integer> num = Integers.num;

        assertThat(num.abs(-10)).isEqualTo(10);
        assertThat(num.abs(0)).isEqualTo(0);
        assertThat(num.abs(10)).isEqualTo(10);

        assertThat(num.negate(-10)).isEqualTo(10);
        assertThat(num.negate(0)).isEqualTo(0);
        assertThat(num.negate(10)).isEqualTo(-10);

        assertThat(num.signum(-10)).isEqualTo(-1);
        assertThat(num.signum(0)).isEqualTo(0);
        assertThat(num.signum(10)).isEqualTo(1);

        assertThat(num.add(7, 3)).isEqualTo(10);
        assertThat(num.subtract(7, 3)).isEqualTo(4);
        assertThat(num.times(7, 3)).isEqualTo(21);

        assertThat(num.fromBigInteger(BigInteger.TEN)).isEqualTo(10);
    }

    @Test
    public void enumeration() {
        org.highj.typeclass0.num.Enum<Integer> enumeration = Integers.enumeration;

        assertThat(enumeration.toEnum(42)).isEqualTo(42);
        assertThat(enumeration.fromEnum(42)).isEqualTo(42);

        assertThat(enumeration.pred(10)).isEqualTo(9);
        assertThat(enumeration.pred(-10)).isEqualTo(-11);
        assertThat(enumeration.succ(10)).isEqualTo(11);
        assertThat(enumeration.succ(-10)).isEqualTo(-9);

        assertThat(enumeration.enumFrom(10)).startsWith(10, 11, 12, 13);

        assertThat(enumeration.enumFromThen(10, 12)).startsWith(10, 12, 14, 16);
        assertThat(enumeration.enumFromThen(10, 8)).startsWith(10, 8, 6, 4);

        assertThat(enumeration.enumFromTo(10, 15)).containsExactly(10, 11, 12, 13, 14, 15);
        assertThat(enumeration.enumFromTo(10, 10)).containsExactly(10);
        assertThat(enumeration.enumFromTo(10, 5)).isEmpty();

        assertThat(enumeration.enumFromThenTo(10, 12, 16)).containsExactly(10, 12, 14, 16);
        assertThat(enumeration.enumFromThenTo(10, 12, 17)).containsExactly(10, 12, 14, 16);
        assertThat(enumeration.enumFromThenTo(-10, -12, -16)).containsExactly(-10, -12, -14, -16);
        assertThat(enumeration.enumFromThenTo(-10, -12, -17)).containsExactly(-10, -12, -14, -16);
        assertThat(enumeration.enumFromThenTo(10, 8, 15)).isEmpty();
        assertThat(enumeration.enumFromThenTo(10, 10, 10)).startsWith(10, 10, 10, 10);
    }

    @Test
    public void real() {
        Real<Integer> real = Integers.real;
        assertThat(real.toRational(23)).isEqualTo(
            Rational.rational(BigInteger.valueOf(23), BigInteger.ONE));
    }

    @Test
    public void integral() {
        Integral<Integer> integral = Integers.integral;
        assertThat(integral.quotRem(23, 7)).isEqualTo(T2.of(3, 2));
        assertThat(integral.quotRem(-23, 7)).isEqualTo(T2.of(-3, -2));
        assertThat(integral.quotRem(23, -7)).isEqualTo(T2.of(-3, 2));
        assertThat(integral.quotRem(-23, -7)).isEqualTo(T2.of(3, -2));
    }
}
