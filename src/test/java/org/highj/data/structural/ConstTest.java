package org.highj.data.structural;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.num.BigIntegers;
import org.highj.data.structural.constant.ConstDivisible;
import org.highj.data.structural.constant.ConstEq1;
import org.highj.data.tuple.T2;
import org.highj.function.Strings;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.data.structural.Const.Const;

public class ConstTest {

    @Test
    public void get() {
        Const<String, Integer> c = Const("foo");
        assertThat(c.get()).isEqualTo("foo");
    }

    @Test
    public void map1() {
        Const<String, Integer> c = Const("foo");
        Const<Integer, Integer> i = c.map1(String::length);
        assertThat(i.get()).isEqualTo(3);
    }

    @Test
    public void eq() {
        Eq<Const<String, Integer>> eq = Const.eq(Eq.fromEquals());
        assertThat(eq.eq(Const("foo"), Const("foo"))).isTrue();
        assertThat(eq.eq(Const("foo"), Const("bar"))).isFalse();
    }

    @Test
    public void functor() {
        Const<String, Integer> c = Const("foo");
        Const<String, Double> result = Const.<String>functor().<Integer, Double>map(Math::sqrt, c);
        assertThat(result.get()).isEqualTo("foo");
    }

    @Test
    public void apply() {
        Const<String, Function<Integer, Double>> fn = Const("foo");
        Const<String, Integer> c = Const("bar");
        Const<String, Double> result = Const.apply(Strings.monoid).ap(fn, c);
        assertThat(result.get()).isEqualTo("foobar");
    }

    @Test
    public void applicative() {
        Const<String, Integer> result = Const.applicative(Strings.monoid).pure(42);
        assertThat(result.get()).isEqualTo(Strings.monoid.identity());
    }

    @Test
    public void contravariant() {
        Const<String, Double> c = Const("foo");
        Const<String, Integer> result = Const.<String>contravariant().<Integer, Double>contramap(Math::sqrt, c);
        assertThat(result.get()).isEqualTo("foo");
    }

    @Test
    public void divisible() {
        ConstDivisible<String> divisible = Const.divisible(Strings.monoid);
        assertThat(divisible.conquer().get()).isEqualTo(Strings.monoid.identity());

        Const<String, Integer> result = divisible.divide(i -> T2.of(Math.sqrt(i), i > 5), Const("foo"), Const("bar"));
        assertThat(result.get()).isEqualTo("foobar");
    }

    @Test
    public void eq1() {
        ConstEq1<String> eq1 = Const.eq1(Eq.fromEquals());
        Eq<__<__<Const.µ, String>, Integer>> eq = eq1.eq1(null);
        assertThat(eq.eq(Const("foo"), Const("foo"))).isTrue();
        assertThat(eq.eq(Const("foo"), Const("bar"))).isFalse();
    }

    @Test
    public void semigroup() {
        Semigroup<Const<String, Integer>> semigroup = Const.semigroup(Strings.monoid);
        assertThat(semigroup.apply(Const("foo"), Const("bar")).get()).isEqualTo("foobar");
    }

    @Test
    public void monoid() {
        Monoid<Const<String, Integer>> monoid = Const.monoid(Strings.monoid);
        assertThat(monoid.identity().get()).isEqualTo(Strings.monoid.identity());
    }

    @Test
    public void group() {
        Group<Const<BigInteger, Integer>> group = Const.group(BigIntegers.additiveGroup);
        assertThat(group.inverse(Const(BigInteger.TEN)).get()).isEqualTo(BigInteger.valueOf(-10));
    }

    @Test
    public void biaplicative() {
        assertThat(Const.biapplicative.bimap(String::length, Math::sqrt, Const("foo")).get()).isEqualTo(3);
        Const<Function<String, Integer>, Function<Double, Byte>> fn = Const(String::length);
        assertThat(Const.biapplicative.biapply(fn, Const("foo")).get()).isEqualTo(3);
        assertThat(Const.biapplicative.bipure("foo", 42).get()).isEqualTo("foo");
    }
}