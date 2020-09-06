package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.eq.Eq;
import org.highj.data.num.Integers;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.T0;
import org.highj.function.F3;
import org.highj.function.Functions;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.comonad.Extend;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.*;
import org.highj.util.Gen1;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.highj.Hkt.asMaybe;
import static org.highj.data.Maybe.*;

public class MaybeTest {

    @Test
    public void asList() {
        assertThat(Just("foo").asList()).containsExactly("foo");
        assertThat(Just$(() -> "bar").asList()).containsExactly("bar");
        assertThat(Nothing().asList()).isEmpty();
    }

    @Test
    public void bind() {
        Function<Integer, Maybe<String>> halfFn = i -> i % 2 == 0
                                                           ? Just("half:" + i / 2)
                                                           : Nothing();

        Maybe<Integer> nothing = Nothing();
        assertThat(nothing.bind(halfFn).isNothing()).isTrue();

        assertThat(Just(23).bind(halfFn).isNothing()).isTrue();

        Maybe<String> success = Just(42).bind(halfFn);
        assertThat(success.isJust()).isTrue();
        assertThat(success.get()).isEqualTo("half:21");
    }

    @Test
    public void cata() {
        Maybe<String> nothing = Nothing();
        assertThat(nothing.cata("foo", Functions.constant("bar"))).isEqualTo("foo");

        Maybe<String> baz = Just("baz");
        assertThat(baz.cata("foo", Functions.constant("bar"))).isEqualTo("bar");
        assertThat(baz.cata("foo", Function.identity())).isEqualTo("baz");
    }

    @Test
    public void contravariant() {
        Maybe<Integer> nothing = Nothing();
        Maybe<Number> nothingSuper = Maybe.contravariant(nothing);
        assertThat(nothingSuper.isNothing()).isTrue();

        Maybe<Integer> four = Just(4);
        Maybe<Number> fourSuper = Maybe.contravariant(four);
        assertThat(fourSuper.isJust()).isTrue();
        assertThat(fourSuper.get()).isEqualTo(four.get());
    }

    @Test
    public void eq() {
        Eq<Maybe<String>> eq = Maybe.eq(Eq.fromEquals());
        Maybe<String> nothing1 = Nothing();
        Maybe<String> nothing2 = Nothing();
        Maybe<String> foo1 = Just("foo");
        Maybe<String> foo2 = Just("foo");
        Maybe<String> bar = Just("bar");
        assertThat(eq.eq(nothing1, nothing2)).isTrue();
        assertThat(eq.eq(foo1, foo2)).isTrue();
        assertThat(eq.eq(nothing1, foo1)).isFalse();
        assertThat(eq.eq(foo1, nothing1)).isFalse();
        assertThat(eq.eq(foo1, bar)).isFalse();
    }

    @Test
    public void equals() {
        Maybe<String> nothing1 = Nothing();
        Maybe<String> nothing2 = Nothing();
        Maybe<String> foo1 = Just("foo");
        Maybe<String> foo2 = Just("foo");
        Maybe<String> bar = Just("bar");
        assertThat(nothing1.equals(nothing2)).isTrue();
        assertThat(foo1.equals(foo2)).isTrue();
        assertThat(nothing1.equals(foo1)).isFalse();
        assertThat(foo1.equals(nothing1)).isFalse();
        assertThat(foo1.equals(bar)).isFalse();
    }

    @Test
    public void filter() {
        Maybe<String> nothing = Nothing();
        assertThat(nothing.filter(s -> s.startsWith("b")).isNothing()).isTrue();

        Maybe<String> foo = Just("foo");
        assertThat(foo.filter(s -> s.startsWith("b")).isNothing()).isTrue();

        Maybe<String> bar = Just("bar");
        assertThat(bar.filter(s -> s.startsWith("b"))).isEqualTo(bar);
    }

    @Test
    public void firstBiasedMonadPlus() {
        MonadPlus<µ> monadPlus = Maybe.firstBiasedMonadPlus;
        monadPlus(monadPlus);

        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> baz = Just("baz");
        Maybe<String> nothing = Nothing();

        //mplus
        assertThat(asMaybe(monadPlus.mplus(foo, bar)).get()).isEqualTo("foo");

        //msum
        List<__<µ, String>> fooBarBaz = List.contravariant(List.of(foo, bar, baz));
        assertThat(asMaybe(monadPlus.msum(fooBarBaz)).get()).isEqualTo("foo");
        List<__<µ, String>> fooBarNothing = List.contravariant(List.of(foo, bar, nothing));
        assertThat(asMaybe(monadPlus.msum(fooBarNothing)).get()).isEqualTo("foo");
        List<__<µ, String>> fooNothingBaz = List.contravariant(List.of(foo, nothing, baz));
        assertThat(asMaybe(monadPlus.msum(fooNothingBaz)).get()).isEqualTo("foo");
    }

    @Test
    public void firstMonoid() {
        Monoid<Maybe<String>> monoid = Maybe.firstMonoid();
        assertThat(monoid.identity().isNothing()).isTrue();

        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> nothing = Nothing();

        assertThat(monoid.apply(foo, bar).get()).isEqualTo("foo");
        assertThat(monoid.apply(foo, nothing).get()).isEqualTo("foo");
        assertThat(monoid.apply(nothing, bar).get()).isEqualTo("bar");
        assertThat(monoid.apply(nothing, nothing).isNothing()).isTrue();

        assertThat(monoid.fold(List.of(nothing, foo, nothing, bar, nothing)).get()).isEqualTo("foo");
        assertThat(monoid.fold(List.of(nothing, nothing, nothing)).isNothing()).isTrue();
        assertThat(monoid.fold(List.of()).isNothing()).isTrue();
    }

    @Test
    public void forEach() {
        java.util.List<String> fooList = new ArrayList<>();
        Maybe<String> foo = Just("foo");
        foo.forEach(fooList::add);
        assertThat(fooList).containsExactly("foo");

        java.util.List<String> nothingList = new ArrayList<>();
        Maybe<String> nothing = Nothing();
        nothing.forEach(nothingList::add);
        assertThat(nothingList).isEmpty();
    }

    @Test
    public void get() {
        Maybe<String> bar = Just("bar");
        assertThat(bar.get()).isEqualTo("bar");

        assertThatThrownBy(() -> Nothing().get())
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getOrElse() {
        Maybe<String> nothing = Nothing();
        assertThat(nothing.getOrElse("foo")).isEqualTo("foo");
        Maybe<String> bar = Just("bar");
        assertThat(bar.getOrElse("foo")).isEqualTo("bar");
    }

    @Test
    public void getOrElse_withSupplier() {
        Supplier<String> thunk = () -> "foo";
        Maybe<String> nothing = Nothing();
        assertThat(nothing.getOrElse(thunk)).isEqualTo("foo");
        Maybe<String> bar = Just("bar");
        assertThat(bar.getOrElse(thunk)).isEqualTo("bar");
    }

    @Test
    public void getOrException_withClass() {
        Maybe<String> bar = Just("bar");
        assertThat(bar.getOrException(NoSuchElementException.class)).isEqualTo("bar");

        assertThatThrownBy(() -> Nothing().getOrException(IllegalArgumentException.class))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getOrException_withMsg() {
        Maybe<String> bar = Just("bar");
        assertThat(bar.getOrException("argh!")).isEqualTo("bar");

        assertThatThrownBy(() -> Nothing().getOrException("argh!"))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("argh!");
    }

    @Test
    public void getOrException_withClassAndMsg() {
        Maybe<String> bar = Just("bar");
        assertThat(bar.getOrException(IllegalArgumentException.class, "argh!")).isEqualTo("bar");

        assertThatThrownBy(() -> Nothing().getOrException(IllegalArgumentException.class, "argh!"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("argh!");
    }

    @Test
    public void isJust() {
        Maybe<String> nothing = Nothing();
        assertThat(nothing.isJust()).isFalse();
        Maybe<String> bar = Just("bar");
        assertThat(bar.isJust()).isTrue();
    }

    @Test
    public void isNothing() {
        Maybe<String> nothing = Nothing();
        assertThat(nothing.isNothing()).isTrue();
        Maybe<String> bar = Just("bar");
        assertThat(bar.isNothing()).isFalse();
    }

    @Test
    public void iterator() {
        assertThat(Nothing()).isEmpty();
        assertThat(Just("bar")).containsExactly("bar");
    }

    @Test
    public void justs() {
        List<Maybe<String>> maybes = List.of(
            Maybe.Nothing(),
            Just("foo"),
            Maybe.Nothing(),
            Maybe.Just$(() -> "bar"),
            Maybe.Nothing());
        List<String> strings = Maybe.justs(maybes);
        assertThat(strings).containsExactly("foo", "bar");
    }

    @Test
    public void justWhenTrue() {
        Maybe<String> nothing = JustWhenTrue(false, () -> "foo");
        assertThat(nothing.isNothing()).isTrue();

        Maybe<String> foo = JustWhenTrue(true, () -> "foo");
        assertThat(foo.get()).isEqualTo("foo");
    }

    @Test
    public void lastBiasedMonadPlus() {
        MonadPlus<µ> monadPlus = Maybe.lastBiasedMonadPlus;
        monadPlus(monadPlus);

        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> baz = Just("baz");
        Maybe<String> nothing = Nothing();

        //mplus
        assertThat(asMaybe(monadPlus.mplus(foo, bar)).get()).isEqualTo("bar");

        //msum
        List<__<µ, String>> fooBarBaz = List.contravariant(List.of(foo, bar, baz));
        assertThat(asMaybe(monadPlus.msum(fooBarBaz)).get()).isEqualTo("baz");
        List<__<µ, String>> fooBarNothing = List.contravariant(List.of(foo, bar, nothing));
        assertThat(asMaybe(monadPlus.msum(fooBarNothing)).get()).isEqualTo("bar");
        List<__<µ, String>> fooNothingBaz = List.contravariant(List.of(foo, nothing, baz));
        assertThat(asMaybe(monadPlus.msum(fooNothingBaz)).get()).isEqualTo("baz");
    }

    @Test
    public void lastMonoid() {
        Monoid<Maybe<String>> monoid = Maybe.lastMonoid();
        assertThat(monoid.identity().isNothing()).isTrue();

        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> nothing = Nothing();

        assertThat(monoid.apply(foo, bar).get()).isEqualTo("bar");
        assertThat(monoid.apply(foo, nothing).get()).isEqualTo("foo");
        assertThat(monoid.apply(nothing, bar).get()).isEqualTo("bar");
        assertThat(monoid.apply(nothing, nothing).isNothing()).isTrue();

        assertThat(monoid.fold(List.of(nothing, foo, nothing, bar, nothing)).get()).isEqualTo("bar");
        assertThat(monoid.fold(List.of(nothing, nothing, nothing)).isNothing()).isTrue();
        assertThat(monoid.fold(List.of()).isNothing()).isTrue();
    }


    @Test
    public void lazyCata() {
        Supplier<String> thunk = () -> "foo";
        Maybe<String> nothing = Nothing();
        assertThat(nothing.cata$(thunk, Functions.constant("bar"))).isEqualTo("foo");

        Maybe<String> baz = Just("baz");
        assertThat(baz.cata$(thunk, Functions.constant("bar"))).isEqualTo("bar");
        assertThat(baz.cata$(thunk, Function.identity())).isEqualTo("baz");
    }

    @Test
    public void lazyJust() {
        Supplier<String> thunk = () -> "bar";
        Maybe<String> bar = Just$(thunk);
        assertThat(bar.isJust()).isTrue();
        assertThat(bar.get()).isEqualTo("bar");
    }

    @Test
    public void lift() {
        Function<Maybe<String>, Maybe<Integer>> fun = Maybe.lift(String::length);
        assertThat(fun.apply(Nothing()).isNothing()).isTrue();
        assertThat(fun.apply(Just("foo")).get()).isEqualTo(3);
    }

    @Test
    public void lift2() {
        BiFunction<Maybe<String>, Maybe<Integer>, Maybe<String>> fun = Maybe.lift2(String::substring);
        assertThat(fun.apply(Nothing(), Nothing()).isNothing()).isTrue();
        assertThat(fun.apply(Just("foo"), Nothing()).isNothing()).isTrue();
        assertThat(fun.apply(Nothing(), Just(1)).isNothing()).isTrue();
        assertThat(fun.apply(Just("foo"), Just(1)).get()).isEqualTo("oo");
    }

    @Test
    public void lift3() {
        F3<Maybe<String>, Maybe<Integer>, Maybe<Integer>, Maybe<String>> fun = Maybe.lift3(String::substring);
        assertThat(fun.apply(Nothing(), Nothing(), Nothing()).isNothing()).isTrue();
        assertThat(fun.apply(Just("foobar"), Nothing(), Nothing()).isNothing()).isTrue();
        assertThat(fun.apply(Nothing(), Just(1), Nothing()).isNothing()).isTrue();
        assertThat(fun.apply(Nothing(), Nothing(), Just(3)).isNothing()).isTrue();
        assertThat(fun.apply(Just("foobar"), Just(1), Nothing()).isNothing()).isTrue();
        assertThat(fun.apply(Just("foobar"), Nothing(), Just(3)).isNothing()).isTrue();
        assertThat(fun.apply(Nothing(), Just(1), Just(3)).isNothing()).isTrue();
        assertThat(fun.apply(Just("foobar"), Just(1), Just(3)).get()).isEqualTo("oo");
    }

    @Test
    public void map() {
        Maybe<String> nothing = Nothing();
        assertThat(nothing.map(String::length).isNothing()).isTrue();
        Maybe<String> foo = Just("foo");
        assertThat(foo.map(String::length).get()).isEqualTo(3);
    }

    @Test
    public void monad() {
        Monad<Maybe.µ> monad = Maybe.monad;

        //pure
        assertThat(asMaybe(monad.pure("foo")).get()).isEqualTo("foo");

        //map
        Maybe<String> nothing = Nothing();
        assertThat(asMaybe(monad.map(String::length, nothing)).isNothing()).isTrue();
        Maybe<String> foo = Just("foo");
        assertThat(asMaybe(monad.map(String::length, foo)).get()).isEqualTo(3);

        //ap
        Maybe<Function<String, Integer>> noFn = Nothing();
        Maybe<Function<String, Integer>> lenFn = Just(String::length);
        assertThat(asMaybe(monad.ap(noFn, nothing)).isNothing()).isTrue();
        assertThat(asMaybe(monad.ap(lenFn, nothing)).isNothing()).isTrue();
        assertThat(asMaybe(monad.ap(noFn, foo)).isNothing()).isTrue();
        assertThat(asMaybe(monad.ap(lenFn, foo)).get()).isEqualTo(3);

        //bind
        Maybe<String> fool = Just("fool");
        Function<String, __<µ, Integer>> lenIfEven =
            s -> s.length() % 2 == 0 ? Just(s.length()) : Maybe.Nothing();
        assertThat(asMaybe(monad.bind(nothing, lenIfEven)).isNothing()).isTrue();
        assertThat(asMaybe(monad.bind(foo, lenIfEven)).isNothing()).isTrue();
        assertThat(asMaybe(monad.bind(fool, lenIfEven)).get()).isEqualTo(4);
    }

    @Test
    public void monadFix() {
        MonadFix<µ> monadFix = Maybe.monad;
        Maybe<String> foo = asMaybe(monadFix.mfix((Supplier<String> x) -> Just("foo")));
        assertThat(foo.get()).isEqualTo("foo");
    }

    private void monadPlus(MonadPlus<µ> monadPlus) {
        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> baz = Just("baz");
        Maybe<String> nothing = Nothing();

        //mzero
        assertThat(asMaybe(monadPlus.mzero()).isNothing()).isTrue();

        //mplus
        //result of  monadPlus.mplus(foo, bar) depends on bias
        assertThat(asMaybe(monadPlus.mplus(foo, nothing)).get()).isEqualTo("foo");
        assertThat(asMaybe(monadPlus.mplus(nothing, bar)).get()).isEqualTo("bar");
        assertThat(asMaybe(monadPlus.mplus(nothing, nothing)).isNothing()).isTrue();

        //guard
        assertThat(asMaybe(monadPlus.guard(false)).isNothing()).isTrue();
        assertThat(asMaybe(monadPlus.guard(true)).get()).isEqualTo(T0.unit);

        //mfilter
        Maybe<Integer> one = Just(1);
        assertThat(asMaybe(monadPlus.mfilter(Integers.odd::test, one)).get()).isEqualTo(1);
        assertThat(asMaybe(monadPlus.mfilter(Integers.even::test, one)).isNothing()).isTrue();

        //msum
        List<__<µ, String>> fooNothingNothing = List.contravariant(List.of(foo, nothing, nothing));
        assertThat(asMaybe(monadPlus.msum(fooNothingNothing)).get()).isEqualTo("foo");
        List<__<µ, String>> nothingBarNothing = List.contravariant(List.of(nothing, bar, nothing));
        assertThat(asMaybe(monadPlus.msum(nothingBarNothing)).get()).isEqualTo("bar");
        List<__<µ, String>> nothingNothingBaz = List.contravariant(List.of(nothing, nothing, baz));
        assertThat(asMaybe(monadPlus.msum(nothingNothingBaz)).get()).isEqualTo("baz");
        List<__<µ, String>> nothingNothingNothing = List.contravariant(List.of(nothing, nothing, nothing));
        assertThat(asMaybe(monadPlus.msum(nothingNothingNothing)).isNothing()).isTrue();
    }

    @Test
    public void monadRec() {
        MonadRec<µ> monadRec = Maybe.monad;

        Function<Integer, __<µ, Either<Integer, String>>> divisibleBy3 = i -> {
            if (i < 10) {
                return i == 0 || i == 3 || i == 6 || i == 9
                           ? Maybe.Just(Either.Right("divisible"))
                           : Maybe.Nothing();
            } else {
                return Maybe.Just(Either.Left(i / 10 + i % 10));
            }
        };

        assertThat(asMaybe(monadRec.tailRec(divisibleBy3, 123456789)).get()).isEqualTo("divisible");
        assertThat(asMaybe(monadRec.tailRec(divisibleBy3, 123456788)).isNothing()).isTrue();
    }

    @Test
    public void extend() {
        Extend<µ> extend = Maybe.extend;
        assertThat(asMaybe(extend.duplicate(Nothing())).isNothing()).isTrue();
        assertThat(asMaybe(asMaybe(extend.duplicate(Just("foo"))).get()).get()).isEqualTo("foo");


        Function<__<µ, String>, __<µ, Integer>> fun = extend.extend(
            (__<µ, String> maybe) -> asMaybe(maybe).map(String::length).getOrElse(-1));
        assertThat(asMaybe(fun.apply(Just("foo"))).get()).isEqualTo(3);
        assertThat(asMaybe(fun.apply(Nothing())).isNothing()).isTrue();
    }

    @Test
    public void monoid() {
        Monoid<Maybe<String>> monoid = Maybe.monoid(String::concat);
        assertThat(monoid.identity().isNothing()).isTrue();

        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> nothing = Nothing();

        assertThat(monoid.apply(foo, bar).get()).isEqualTo("foobar");
        assertThat(monoid.apply(foo, nothing).get()).isEqualTo("foo");
        assertThat(monoid.apply(nothing, bar).get()).isEqualTo("bar");
        assertThat(monoid.apply(nothing, nothing).isNothing()).isTrue();

        assertThat(monoid.fold(List.of(nothing, foo, nothing, bar, nothing)).get()).isEqualTo("foobar");
        assertThat(monoid.fold(List.of(nothing, nothing, nothing)).isNothing()).isTrue();
        assertThat(monoid.fold(List.of()).isNothing()).isTrue();
    }

    @Test
    public void just() {
        Maybe<String> bar = Just("bar");
        assertThat(bar.isJust()).isTrue();
        assertThat(bar.get()).isEqualTo("bar");
    }

    @Test
    public void nothing() {
        Maybe<String> nothing = Nothing();
        assertThat(nothing.isNothing()).isTrue();
    }

    @Test
    public void orElse() {
        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> nothing = Nothing();
        assertThat(foo.orElse(bar).get()).isEqualTo("foo");
        assertThat(foo.orElse(nothing).get()).isEqualTo("foo");
        assertThat(nothing.orElse(bar).get()).isEqualTo("bar");
        assertThat(nothing.orElse(nothing).isNothing()).isTrue();
    }

    @Test
    public void testToString() {
        assertThat(Nothing().toString()).isEqualTo("Nothing");
        assertThat(Just("foo").toString()).isEqualTo("Just(foo)");
    }

    @Test
    public void traversable() {
        Traversable<Maybe.µ> traversable = Maybe.traversable;
        Maybe<__<List.µ, String>> maybeList = Just(List.of("foo", "bar"));
        List<__<µ, String>> sequenced = Hkt.asList(traversable.sequenceA(List.monadPlus, maybeList));
        assertThat(sequenced).hasSize(2);
        assertThat(asMaybe(sequenced.head()).get()).isEqualTo("foo");
        assertThat(asMaybe(sequenced.tail().head()).get()).isEqualTo("bar");
    }

    @Test
    public void eq1() {
        Eq<__<Maybe.µ, String>> eq = Maybe.eq1.eq1(Eq.fromEquals());
        Maybe<String> nothing1 = Nothing();
        Maybe<String> nothing2 = Nothing();
        Maybe<String> foo1 = Just("foo");
        Maybe<String> foo2 = Just("foo");
        Maybe<String> bar = Just("bar");
        assertThat(eq.eq(nothing1, nothing2)).isTrue();
        assertThat(eq.eq(foo1, foo2)).isTrue();
        assertThat(eq.eq(nothing1, foo1)).isFalse();
        assertThat(eq.eq(foo1, nothing1)).isFalse();
        assertThat(eq.eq(foo1, bar)).isFalse();
    }

    @Test
    public void ord1withNothingFirst() {
        Ord<__<µ, String>> ord = Maybe.ord1withNothingFirst.cmp(Ord.fromComparable());
        List<__<Maybe.µ, String>> list = List.of(Nothing(), Just("a"), Nothing(), Just("c"), Just("b"), Nothing());
        list.sort(ord);
        assertThat(list.sort(ord)).isEqualTo(List.of(Nothing(), Nothing(), Nothing(), Just("a"), Just("b"), Just("c")));
    }

    @Test
    public void ord1withNothingLast() {
        Ord<__<µ, String>> ord = Maybe.ord1withNothingLast.cmp(Ord.fromComparable());
        List<__<Maybe.µ, String>> list = List.of(Nothing(), Just("a"), Nothing(), Just("c"), Just("b"), Nothing());
        list.sort(ord);
        assertThat(list.sort(ord)).isEqualTo(List.of(Just("a"), Just("b"), Just("c"), Nothing(), Nothing(), Nothing()));
    }

    @Test
    public void monadLaw() {
        new MonadLaw<>(Maybe.monad, Gen1.maybeGen1, Maybe.eq1).test();
    }

}
