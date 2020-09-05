package org.highj.data.tuple;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.HList;
import org.highj.data.eq.Eq;
import org.highj.data.num.Integers;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.t1.T1Comonad;
import org.highj.data.tuple.t1.T1Group;
import org.highj.data.tuple.t1.T1MonadRec;
import org.highj.function.Functions;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Monad;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asT1;

public class T1Test {

    @Test
    public void get() {
        assertThat(T1.of(42).get()).isEqualTo(42);
    }

    @Test
    public void _1() {
        assertThat(T1.of(42)._1()).isEqualTo(42);
    }

    @Test
    public void of() {
        assertThat(T1.of(42)._1()).isEqualTo(42);
    }

    @Test
    public void of$() {
        assertThat(T1.of$(() -> 42)._1()).isEqualTo(42);
        //test laziness
        T1.of$(() -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void narrow() {
        __<T1.µ, Integer> fortyTwo = T1.of(42);
        assertThat(asT1(fortyTwo)._1()).isEqualTo(42);
        //lazy version
        __<T1.µ, String> hello = T1.of$(() -> "hello");
        assertThat(asT1(hello)._1());
    }

    @Test
    public void testToString() {
        T1<Integer> fortyTwo = T1.of(42);
        assertThat(fortyTwo.toString()).isEqualTo("(42)");
        //lazy version
        T1<String> hello = T1.of$(() -> "hello");
        assertThat(hello.toString()).isEqualTo("(hello)");
    }

    @Test
    public void map() {
        T1<String> hello = T1.of("hello");
        T1<Integer> five = hello.map(String::length);
        assertThat(five._1()).isEqualTo(5);
    }

    @Test
    public void map$() {
        T1<String> hello = T1.of("hello");
        T1<Integer> five = hello.map$(String::length);
        assertThat(five._1()).isEqualTo(5);
        //test laziness
        T1<String> exT1 = T1.of$(() -> {
            throw new RuntimeException();
        });
        exT1.map$(String::length);
    }

    @Test
    public void ap() {
        T1<Function<String, Integer>> lengthFn = T1.of(String::length);
        T1<String> hello = T1.of("hello");
        T1<Integer> five = hello.ap(lengthFn);
        assertThat(five._1()).isEqualTo(5);
    }

    @Test
    public void ap$() {
        T1<Function<String, Integer>> lengthFn = T1.of(String::length);
        T1<String> hello = T1.of("hello");
        T1<Integer> five = hello.ap$(lengthFn);
        assertThat(five._1()).isEqualTo(5);

        //test laziness
        T1<Function<String, Integer>> exFn = T1.of$(() -> {
            throw new RuntimeException();
        });
        T1<String> exT1 = T1.of$(() -> {
            throw new RuntimeException();
        });
        exT1.ap$(exFn);
    }

    @Test
    public void bind() {
        Function<String, T1<Integer>> lengthFn = Functions.compose(T1::of, String::length);
        T1<String> hello = T1.of("hello");
        T1<Integer> five = hello.bind(lengthFn);
        assertThat(five._1()).isEqualTo(5);
    }

    @Test
    public void bind$() {
        Function<String, T1<Integer>> lengthFn = Functions.compose(T1::of, String::length);
        T1<String> hello = T1.of("hello");
        assertThat(hello.bind(lengthFn)._1()).isEqualTo(5);

        //test laziness
        Function<String, T1<Integer>> exFn = s -> {
            throw new RuntimeException();
        };
        T1<String> exT1 = T1.of$(() -> {
            throw new RuntimeException();
        });
        exT1.bind$(exFn);
    }

    @Test
    public void cata() {
        T1<String> hello = T1.of("hello");
        assertThat(hello.cata(String::length)).isEqualTo(5);
    }

    @Test
    public void equals() {
        T1<String> one = T1.of("hello");
        T1<String> two = T1.of$(() -> "hello");
        T1<String> three = T1.of("world");
        assertThat(one.equals(two)).isTrue();
        assertThat(one.equals(three)).isFalse();
        assertThat(two.equals(three)).isFalse();
    }

    @Test
    public void merge() {
        T1<String> hello = T1.of("hello");
        T1<Integer> fortyTwo = T1.of(42);
        BiFunction<String, Integer, String> function = (s, i) -> String.format("%s %d!", s, i);
        assertThat(T1.merge(hello, fortyTwo, function)._1()).isEqualTo("hello 42!");
    }

    @Test
    public void merge$() {
        T1<String> hello = T1.of("hello");
        T1<Integer> fortyTwo = T1.of(42);
        BiFunction<String, Integer, String> function = (s, i) -> String.format("%s %d!", s, i);
        assertThat(T1.merge$(hello, fortyTwo, function)._1()).isEqualTo("hello 42!");
        //test laziness
        T1<String> ex1 = T1.of$(() -> {
            throw new RuntimeException();
        });
        T1<Integer> ex2 = T1.of$(() -> {
            throw new RuntimeException();
        });
        T1.merge$(ex1, ex2, function);
    }

    @Test
    public void eq() {
        Eq<T1<String>> eq = T1.eq(Eq.fromEquals());
        T1<String> one = T1.of("hello");
        T1<String> two = T1.of$(() -> "hello");
        T1<String> three = T1.of("world");
        assertThat(eq.eq(one, two)).isTrue();
        assertThat(eq.eq(one, three)).isFalse();
        assertThat(eq.eq(two, three)).isFalse();
    }

    @Test
    public void ord() {
        Ord<T1<String>> ord = T1.ord(Ord.fromComparable());
        T1<String> one = T1.of("hello");
        T1<String> two = T1.of$(() -> "hello");
        T1<String> three = T1.of("world");
        assertThat(ord.eq(one, two)).isTrue();
        assertThat(ord.greaterEqual(one, two)).isTrue();
        assertThat(ord.greater(one, three)).isFalse();
        assertThat(ord.greaterEqual(one, three)).isFalse();
        assertThat(ord.lessEqual(two, three)).isTrue();
    }

    @Test
    public void functor() {
        Functor<T1.µ> functor = T1.functor;
        T1<String> hello = T1.of("hello");
        T1<Integer> five = asT1(functor.map(String::length, hello));
        assertThat(five._1()).isEqualTo(5);
    }

    @Test
    public void applicative() {
        assertThat(T1.applicative.pure(42)).isEqualTo(T1.of(42));

        T1<Function<String, Integer>> lengthFn = T1.of(String::length);
        T1<String> hello = T1.of("hello");
        T1<Integer> five = T1.applicative.ap(lengthFn, hello);
        assertThat(five._1()).isEqualTo(5);
    }

    @Test
    public void monad() {
        Monad<T1.µ> monad = T1.monad;
        Function<String, __<T1.µ, Integer>> lengthFn = Functions.compose(T1::of, String::length);
        T1<String> hello = T1.of("hello");
        T1<Integer> five = asT1(monad.bind(hello, lengthFn));
        assertThat(five._1()).isEqualTo(5);
    }

    @Test
    public void monadRec() {
        T1MonadRec monad = T1.monadRec;
        Function<Integer, __<T1.µ, Either<Integer, Integer>>> digitSum = i ->
                                                                             T1.of(i < 10 ? Either.Right(i) : Either.Left(i / 10 + i % 10));
        assertThat(monad.tailRec(digitSum, 4711)).isEqualTo(T1.of(4));
    }

    @Test
    public void comonad() {
        T1Comonad comonad = T1.comonad;
        assertThat(comonad.duplicate(T1.of(2))).isEqualTo(T1.of(T1.of(2)));
        assertThat(comonad.extract(T1.of(2))).isEqualTo(2);
        Function<__<T1.µ, String>, Integer> function = nested -> asT1(nested)._1().length();
        assertThat(comonad.extend(function).apply(T1.of("hello"))).isEqualTo(T1.of(5));
    }

    @Test
    public void semigroup() {
        T1<Integer> two = T1.of(2);
        T1<Integer> six = T1.of(6);
        T1<Integer> eight = T1.semigroup(Integers.additiveGroup).apply(two, six);
        assertThat(eight).isEqualTo(T1.of(8));
    }

    @Test
    public void monoid() {
        T1<Integer> eight = T1.monoid(Integers.additiveGroup).apply(T1.of(2), T1.of(6));
        assertThat(eight._1()).isEqualTo(8);
        assertThat(T1.monoid(Integers.additiveGroup).identity()).isEqualTo(T1.of(0));
    }

    @Test
    public void group() {
        T1Group<Integer> group = T1.group(Integers.additiveGroup);
        assertThat(group.apply(T1.of(2), T1.of(3))).isEqualTo(T1.of(5));
        assertThat(group.identity()).isEqualTo(T1.of(0));
        assertThat(group.inverse(T1.of(2))).isEqualTo(T1.of(-2));
    }

    @Test
    public void toHList() {
        HList.HCons<Integer, HList.HNil> hList = T1.of(42).toHList();
        assertThat(hList.head()).isEqualTo(42);
    }
}