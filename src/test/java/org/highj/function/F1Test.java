package org.highj.function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.function.F1;
import org.highj.function.f1.F1Monad;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class F1Test {
    @Test
    public void testId() throws Exception {
        assertThat(F1.id().apply("test")).isEqualTo("test");
    }

    @Test
    public void testNarrow() throws Exception {
        F1<String, Integer> fn = String::length;
        __<__<F1.µ,String>, Integer> fnMy1 = fn;
        assertThat(F1.narrow(fnMy1).apply("abcd")).isEqualTo(4);
        __2<F1.µ,String, Integer> fnMy2 = fn;
        assertThat(F1.narrow(fnMy2).apply("abcd")).isEqualTo(4);
    }

    @Test
    public void testContravariant() throws Exception {
        F1<Long, Timestamp> fn = Timestamp::new;
        F1<Long, Date> fnDate = F1.contravariant(fn);
        assertThat(fnDate.apply(123L)).isOfAnyClassIn(Timestamp.class).isEqualTo(new Timestamp(123));
    }

    @Test
    public void testConstantFn() throws Exception {
        F1<String, F1<Integer, String>> fn = F1.constant();
        F1<Integer, String> constFn = fn.apply("x");
        assertThat(constFn.apply(123)).isEqualTo("x");
        assertThat(constFn.apply(456)).isEqualTo("x");
    }

    @Test
    public void testConstantValue() throws Exception {
        F1<Integer, String> constFn = F1.constant("x");
        assertThat(constFn.apply(123)).isEqualTo("x");
        assertThat(constFn.apply(456)).isEqualTo("x");
    }

    @Test
    public void testConstantSupplier() throws Exception {
        F1<Integer, String> constFn = F1.constant(() -> "x");
        assertThat(constFn.apply(123)).isEqualTo("x");
        assertThat(constFn.apply(456)).isEqualTo("x");
    }

    @Test
    public void testCompose2() throws Exception {
        F1<String, Integer> f1 = String::length;
        F1<Integer, Integer> f2 = x -> x * x;
        F1<String, Integer> fn = F1.compose(f2, f1);
        assertThat(fn.apply("abcd")).isEqualTo(16);
    }

    @Test
    public void testCompose3() throws Exception {
        F1<String, Integer> f1 = String::length;
        F1<Integer, Integer> f2 = x -> x * x;
        F1<Integer, String> f3 = x -> "x:" + x;
        F1<String, String> fn = F1.compose(f3, f2, f1);
        assertThat(fn.apply("abcd")).isEqualTo("x:16");
    }

    @Test
    public void testFlip() throws Exception {
        F1<String, Integer> fn = String::length;
        assertThat(F1.<String, Integer>flip("abcd").apply(fn)).isEqualTo(4);
    }

    @Test
    public void testEndoMonoid() throws Exception {
        Monoid<F1<String, String>> monoid = F1.endoMonoid();
        assertThat(monoid.apply(s -> s + "!", s -> s + s).apply("da")).isEqualTo("dada!");
    }

    @Test
    public void testFlipApply() throws Exception {
        F1<String, Integer> fn = String::length;
        assertThat(F1.<String, Integer>flipApply().apply("abcd").apply(fn)).isEqualTo(4);
    }

    @Test
    public void testMonad() throws Exception {
        F1Monad<String> monad = F1.monad();
        F1<String, Integer> pure = monad.pure(42);
        assertThat(pure.apply("test")).isEqualTo(42);
        F1<String, Integer> length = String::length;
        F1<String, String> bind = monad.bind(length, (Integer i) -> (F1<String, String>) s -> s + i);
        assertThat(bind.apply("abcd")).isEqualTo("abcd4");
    }

    @Test
    public void testFanout2() throws Exception {
        F1<String, T2<Integer, String>> function =  F1.fanout((F1<String, Integer>) String::length, F1.id());
        T2<Integer, String> pair = function.apply("abcd");
        assertThat(pair._1()).isEqualTo(4);
        assertThat(pair._2()).isEqualTo("abcd");
    }

    @Test
    public void testFanout3() throws Exception {
        F1<String, T3<Integer, String, Boolean>> function =  F1.fanout(
                (F1<String, Integer>) String::length, F1.id(), (F1<String,Boolean>) s -> s.startsWith("ab"));
        T3<Integer, String, Boolean> triple = function.apply("abcd");
        assertThat(triple._1()).isEqualTo(4);
        assertThat(triple._2()).isEqualTo("abcd");
        assertThat(triple._3()).isEqualTo(true);
    }

    @Test
    public void testFanout4() throws Exception {
        F1<String, T4<Integer, String, Boolean, String>> function =  F1.fanout(
                (F1<String, Integer>) String::length, F1.id(),
                (F1<String,Boolean>) s -> s.startsWith("ab"), (F1<String, String>) s -> s + s);
        T4<Integer, String, Boolean, String> quad = function.apply("abcd");
        assertThat(quad._1()).isEqualTo(4);
        assertThat(quad._2()).isEqualTo("abcd");
        assertThat(quad._3()).isEqualTo(true);
        assertThat(quad._4()).isEqualTo("abcdabcd");
    }

    @Test
    public void testFromF1() throws Exception {
        Supplier<String> supplier = F1.fromF1(u -> "test");
        assertThat(supplier.get()).isEqualTo("test");
    }

    @Test
    public void testLazy() throws Exception {
        int[] sideEffect = {0};
        Supplier<Integer> supplier = F1.lazy(s -> { sideEffect[0] = 42; return s.length();}, "abcd");
        assertThat(sideEffect[0]).isEqualTo(0);
        assertThat(supplier.get()).isEqualTo(4);
        assertThat(sideEffect[0]).isEqualTo(42);
    }

    @Test
    public void testLazyFunction() throws Exception {
        int[] sideEffect = {0};
        F1<String, Integer> f1 = s -> { sideEffect[0] = 42; return s.length();};
        Supplier<Integer> supplier = f1.lazy("abcd");
        assertThat(sideEffect[0]).isEqualTo(0);
        assertThat(supplier.get()).isEqualTo(4);
        assertThat(sideEffect[0]).isEqualTo(42);
    }

    @Test
    public void testThen() throws Exception {
        F1<String, Integer> f = String::length;
        F1<Integer, Integer> g = x -> x*x;
        assertThat(f.then(g).apply("abcd")).isEqualTo(16);
    }

    @Test
    public void testFromJavaMap() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 10);
        map.put("b", 20);
        F1<String, Maybe<Integer>> fn = F1.fromJavaMap(map);
        assertThat(fn.apply("b").get()).isEqualTo(20);
        assertThat(fn.apply("c").isNothing()).isTrue();
    }

    @Test
    public void testFromJavaMapWithDefault() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 10);
        map.put("b", 20);
        F1<String, Integer> fn = F1.fromJavaMap(map, 42);
        assertThat(fn.apply("b")).isEqualTo(20);
        assertThat(fn.apply("c")).isEqualTo(42);
    }

    @Test
    public void testArrow() throws Exception {
    }
}