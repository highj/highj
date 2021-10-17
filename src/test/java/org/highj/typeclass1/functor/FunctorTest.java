package org.highj.typeclass1.functor;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asMaybe;
import static org.highj.data.Maybe.*;

public class FunctorTest {

    private final Functor<µ> functor = monad;

    @Test
    public void left$() {
        Maybe<String> justX = Just("x");
        Maybe<Integer> three = asMaybe(functor.left$(3, justX));
        assertThat(three.get()).isEqualTo(3);

        Maybe<String> nothingString = Nothing();
        Maybe<Integer> nothingInt = asMaybe(functor.left$(3, nothingString));
        assertThat(nothingInt.isNothing()).isTrue();
    }

    @Test
    public void voidF() {
        Maybe<String> justX = Just("x");
        Maybe<T0> justUnit = asMaybe(functor.voidF(justX));
        assertThat(justUnit).isEqualTo(Just(T0.unit));

        Maybe<String> nothingString = Nothing();
        Maybe<T0> nothingUnit = asMaybe(functor.voidF(nothingString));
        assertThat(nothingUnit.isNothing()).isTrue();
    }

    @Test
    public void flip() {
        Maybe<Function<String, Integer>> justStringLength = Just(String::length);
        Maybe<Integer> justThree = asMaybe(functor.flip(justStringLength, "foo"));
        assertThat(justThree).isEqualTo(Just(3));

        Maybe<Function<String, Integer>> nothingFn = Nothing();
        Maybe<Integer> nothingInt = asMaybe(functor.flip(nothingFn, "foo"));
        assertThat(nothingInt.isNothing()).isTrue();
    }

    @Test
    public void lift() {
        Function<__<µ, String>, __<µ, Integer>> liftedFn = functor.lift(String::length);

        Maybe<String> justString = Just("foo");
        Maybe<Integer> justThree = asMaybe(liftedFn.apply(justString));
        assertThat(justThree).isEqualTo(Just(3));

        Maybe<String> nothingString = Nothing();
        Maybe<Integer> nothingInt = asMaybe(liftedFn.apply(nothingString));
        assertThat(nothingInt.isNothing()).isTrue();
    }

    @Test
    public void binary() {
        Function<__<List.µ, __<µ, String>>, __<List.µ, __<µ, Integer>>> fn =
            Functor.binary(List.monadPlus, Maybe.monad, String::length);
        List<__<Maybe.µ, String>> data = List.of(Just("one"), Nothing(), Just("three"));
        assertThat(Hkt.asList(fn.apply(data))).containsExactly(
            Just(3), Nothing(), Just(5));
    }

    @Test
    public void ternary() {
        Function<__<List.µ, __<µ, __<T1.µ, String>>>, __<List.µ, __<µ, __<T1.µ, Integer>>>> fn =
            Functor.ternary(List.monadPlus, Maybe.monad, T1.functor, String::length);
        List<__<Maybe.µ, __<T1.µ, String>>> data =
            List.of(Just(T1.of("one")), Nothing(), Just(T1.of("three")));
        assertThat(Hkt.asList(fn.apply(data))).containsExactly(
            Just(T1.of(3)), Nothing(), Just(T1.of(5)));
    }

    @Test
    public void unzip() {
        Maybe<T2<String, Integer>> m1 = Just(T2.of("hi", 42));
        assertThat(functor.unzip(m1)).isEqualTo(T2.of(Just("hi"), Just(42)));

        Maybe<T2<String, Integer>> m2 = Nothing();
        assertThat(functor.unzip(m2)).isEqualTo(T2.of(Nothing(), Nothing()));
    }

}
