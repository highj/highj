package org.highj.data.num;

import org.highj.data.ratio.Rational;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.num.Integral;
import org.highj.typeclass0.num.Num;
import org.highj.typeclass0.num.Real;
import org.highj.util.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class BigIntegersTest {

    @Test
    public void sqr() {
        assertThat(BigIntegers.sqr.apply($(0))).isEqualTo($(0));
        assertThat(BigIntegers.sqr.apply($(42))).isEqualTo($(1764));
        assertThat(BigIntegers.sqr.apply($(-42))).isEqualTo($(1764));
    }

    @Test
    public void succ() {
        assertThat(BigIntegers.succ.apply($(-1))).isEqualTo($(0));
        assertThat(BigIntegers.succ.apply($(0))).isEqualTo($(1));
        assertThat(BigIntegers.succ.apply($(42))).isEqualTo($(43));
    }

    @Test
    public void pred() {
        assertThat(BigIntegers.pred.apply($(0))).isEqualTo($(-1));
        assertThat(BigIntegers.pred.apply($(1))).isEqualTo($(0));
        assertThat(BigIntegers.pred.apply($(43))).isEqualTo($(42));
    }

    @Test
    public void pow() {
        assertThat(BigIntegers.pow.apply($(3)).apply($(4))).isEqualTo($(81));
        assertThat(BigIntegers.pow.apply($(10)).apply($(2))).isEqualTo($(100));
        assertThat(BigIntegers.pow.apply($(5)).apply($(3))).isEqualTo($(125));
        assertThat(BigIntegers.pow.apply($(2)).apply($(8))).isEqualTo($(256));
    }

    @Test
    public void negative() {
        assertThat(BigIntegers.negative.test($(0))).isFalse();
        assertThat(BigIntegers.negative.test($(42))).isFalse();
        assertThat(BigIntegers.negative.test($(-42))).isTrue();
    }

    @Test
    public void positive() {
        assertThat(BigIntegers.positive.test($(0))).isFalse();
        assertThat(BigIntegers.positive.test($(42))).isTrue();
        assertThat(BigIntegers.positive.test($(-42))).isFalse();
    }

    @Test
    public void zero() {
        assertThat(BigIntegers.zero.test($(0))).isTrue();
        assertThat(BigIntegers.zero.test($(42))).isFalse();
        assertThat(BigIntegers.zero.test($(-42))).isFalse();
    }

    @Test
    public void even() {
        assertThat(BigIntegers.even.test($(0))).isTrue();
        assertThat(BigIntegers.even.test($(42))).isTrue();
        assertThat(BigIntegers.even.test($(-42))).isTrue();
        assertThat(BigIntegers.even.test($(43))).isFalse();
        assertThat(BigIntegers.even.test($(-43))).isFalse();
    }

    @Test
    public void odd() {
        assertThat(BigIntegers.odd.test($(0))).isFalse();
        assertThat(BigIntegers.odd.test($(42))).isFalse();
        assertThat(BigIntegers.odd.test($(-42))).isFalse();
        assertThat(BigIntegers.odd.test($(43))).isTrue();
        assertThat(BigIntegers.odd.test($(-43))).isTrue();
    }

    @Test
    public void additiveGroup() {
        assertThat(BigIntegers.additiveGroup.identity()).isEqualTo($(0));
        assertThat(BigIntegers.additiveGroup.apply($(17), $(23))).isEqualTo($(40));
        assertThat(BigIntegers.additiveGroup.inverse($(5))).isEqualTo($(-5));
        assertThat(BigIntegers.additiveGroup.inverse($(-5))).isEqualTo($(5));
        assertThat(BigIntegers.additiveGroup.inverse($(0))).isEqualTo($(0));
    }

    @Test
    public void multiplicativeMonoid() {
        assertThat(BigIntegers.multiplicativeMonoid.identity()).isEqualTo($(1));
        assertThat(BigIntegers.multiplicativeMonoid.apply($(17), $(23))).isEqualTo($(391));
    }

    @Test
    public void minSemigroup() {
        assertThat(BigIntegers.minSemigroup.apply($(17), $(23))).isEqualTo($(17));
        assertThat(BigIntegers.minSemigroup.apply($(23), $(17))).isEqualTo($(17));
    }

    @Test
    public void maxSemigroup() {
        assertThat(BigIntegers.maxSemigroup.apply($(17), $(23))).isEqualTo($(23));
        assertThat(BigIntegers.maxSemigroup.apply($(23), $(17))).isEqualTo($(23));
    }

    @Test
    public void andSemigroup() {
        assertThat(BigIntegers.andSemigroup.apply($(17), $(23))).isEqualTo($(17 & 23));
    }

    @Test
    public void xorMonoid() {
        assertThat(BigIntegers.xorMonoid.identity()).isEqualTo($(0));
        assertThat(BigIntegers.xorMonoid.apply($(17), $(23))).isEqualTo($(6));
    }

    @Test
    public void orMonoid() {
        assertThat(BigIntegers.orMonoid.identity()).isEqualTo($(0));
        assertThat(BigIntegers.orMonoid.apply($(17), $(23))).isEqualTo($(17 | 23));
    }

    @Test
    public void enumeration() {
        org.highj.typeclass0.num.Enum<BigInteger> enumeration = BigIntegers.enumeration;

        assertThat(enumeration.toEnum(42)).isEqualTo($(42));
        assertThat(enumeration.fromEnum($(42))).isEqualTo(42);

        assertThat(enumeration.pred($(10))).isEqualTo($(9));
        assertThat(enumeration.pred($(-10))).isEqualTo($(-11));
        assertThat(enumeration.succ($(10))).isEqualTo($(11));
        assertThat(enumeration.succ($(-10))).isEqualTo($(-9));

        assertThat(enumeration.enumFrom($(10))).startsWith($$(10, 11, 12, 13));

        assertThat(enumeration.enumFromThen($(10), $(12))).startsWith($$(10, 12, 14, 16));
        assertThat(enumeration.enumFromThen($(10), $(8))).startsWith($$(10, 8, 6, 4));

        assertThat(enumeration.enumFromTo($(10), $(15))).containsExactly($$(10, 11, 12, 13, 14, 15));
        assertThat(enumeration.enumFromTo($(10), $(10))).containsExactly($(10));
        assertThat(enumeration.enumFromTo($(10), $(5))).isEmpty();

        assertThat(enumeration.enumFromThenTo($(10), $(12), $(16))).containsExactly($$(10, 12, 14, 16));
        assertThat(enumeration.enumFromThenTo($(10), $(12), $(17))).containsExactly($$(10, 12, 14, 16));
        assertThat(enumeration.enumFromThenTo($(-10), $(-12), $(-16))).containsExactly($$(-10, -12, -14, -16));
        assertThat(enumeration.enumFromThenTo($(-10), $(-12), $(-17))).containsExactly($$(-10, -12, -14, -16));
        assertThat(enumeration.enumFromThenTo($(10), $(8), $(15))).isEmpty();
        assertThat(enumeration.enumFromThenTo($(10), $(10), $(10))).startsWith($$(10, 10, 10, 10));
    }

    @Test
    public void num() {
        Num<BigInteger> num = BigIntegers.num;

        assertThat(num.abs($(-10))).isEqualTo($(10));
        assertThat(num.abs($(0))).isEqualTo($(0));
        assertThat(num.abs($(10))).isEqualTo($(10));

        assertThat(num.negate($(-10))).isEqualTo($(10));
        assertThat(num.negate($(0))).isEqualTo($(0));
        assertThat(num.negate($(10))).isEqualTo($(-10));

        assertThat(num.signum($(-10))).isEqualTo($(-1));
        assertThat(num.signum($(0))).isEqualTo($(0));
        assertThat(num.signum($(10))).isEqualTo($(1));

        assertThat(num.add($(7), $(3))).isEqualTo($(10));
        assertThat(num.subtract($(7), $(3))).isEqualTo($(4));
        assertThat(num.times($(7), $(3))).isEqualTo($(21));

        assertThat(num.fromBigInteger($(10))).isEqualTo($(10));
    }

    @Test
    public void real() {
        Real<BigInteger> real = BigIntegers.real;
        assertThat(real.toRational($(23))).isEqualTo(
            Rational.rational($(23), $(1)));
    }

    @Test
    public void integral() {
        Integral<BigInteger> integral = BigIntegers.integral;
        assertThat(integral.quotRem($(23), $(7))).isEqualTo(T2.of($(3), $(2)));
        assertThat(integral.quotRem($(-23), $(7))).isEqualTo(T2.of($(-3), $(-2)));
        assertThat(integral.quotRem($(23), $(-7))).isEqualTo(T2.of($(-3), $(2)));
        assertThat(integral.quotRem($(-23), $(-7))).isEqualTo(T2.of($(3), $(-2)));
    }

    private static BigInteger $(int i) {
        return BigInteger.valueOf(i);
    }

    private static BigInteger[] $$(Integer... is) {
        return ArrayUtils.map(is, BigIntegersTest::$, new BigInteger[is.length]);
    }


}