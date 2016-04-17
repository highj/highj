package org.highj.data.collection;

import org.highj._;
import org.highj.data.functions.Functions;
import org.highj.data.functions.Integers;
import org.highj.data.tuple.T0;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass0.compare.Eq;
import org.highj.typeclass1.monad.MonadFix;
import org.highj.typeclass1.monad.MonadPlus;
import org.highj.typeclass1.monad.MonadRec;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.highj.data.collection.Maybe.*;
import static org.assertj.core.api.Assertions.*;

public class MaybeTest {

    @Rule
    public ExpectedException shouldThrow = ExpectedException.none();

    @Test
    public void testCata() {
        Maybe<String> nothing = newNothing();
        assertThat(nothing.cata("foo", Functions.<String, String>constant("bar"))).isEqualTo("foo");

        Maybe<String> baz = newJust("baz");
        assertThat(baz.cata("foo", Functions.constant("bar"))).isEqualTo("bar");
        assertThat(baz.cata("foo", Function.identity())).isEqualTo("baz");
    }

    @Test
    public void testLazyCata() {
        Supplier<String> thunk = () -> "foo";
        Maybe<String> nothing = newNothing();
        assertThat(nothing.lazyCata(thunk, Functions.constant("bar"))).isEqualTo("foo");

        Maybe<String> baz = newJust("baz");
        assertThat(baz.lazyCata(thunk, Functions.constant("bar"))).isEqualTo("bar");
        assertThat(baz.lazyCata(thunk, Function.identity())).isEqualTo("baz");
    }

    @Test
    public void testNewNothing() {
        Maybe<String> nothing = newNothing();
        assertThat(nothing.isNothing()).isTrue();
    }

    @Test
    public void testNewJust() {
        Maybe<String> bar = newJust("bar");
        assertThat(bar.isJust()).isTrue();
        assertThat(bar.get()).isEqualTo("bar");
    }

    @Test
    public void testJustFn() {
        Function<String, Maybe<String>> just = Maybe::newJust;
        Maybe<String> bar = just.apply("bar");
        assertThat(bar.isJust()).isTrue();
        assertThat(bar.get()).isEqualTo("bar");
    }

    @Test
    public void testLazyJust() {
        Supplier<String> thunk = () -> "bar";
        Maybe<String> bar = lazyJust(thunk);
        assertThat(bar.isJust()).isTrue();
        assertThat(bar.get()).isEqualTo("bar");
    }

    @Test
    public void testIsNothing() {
        Maybe<String> nothing = newNothing();
        assertThat(nothing.isNothing()).isTrue();
        Maybe<String> bar = newJust("bar");
        assertThat(bar.isNothing()).isFalse();
    }

    @Test
    public void testIsJust() {
        Maybe<String> nothing = newNothing();
        assertThat(nothing.isJust()).isFalse();
        Maybe<String> bar = newJust("bar");
        assertThat(bar.isJust()).isTrue();
    }

    @Test
    public void testGetOrElse() {
        Maybe<String> nothing = newNothing();
        assertThat(nothing.getOrElse("foo")).isEqualTo("foo");
        Maybe<String> bar = newJust("bar");
        assertThat(bar.getOrElse("foo")).isEqualTo("bar");
    }

    @Test
    public void testGetOrElseThunk() {
        Supplier<String> thunk = () -> "foo";
        Maybe<String> nothing = newNothing();
        assertThat(nothing.getOrElse(thunk)).isEqualTo("foo");
        Maybe<String> bar = newJust("bar");
        assertThat(bar.getOrElse(thunk)).isEqualTo("bar");
    }

    @Test
    public void testGet() {
        Maybe<String> bar = newJust("bar");
        assertThat(bar.get()).isEqualTo("bar");
    }

    @Test
    public void testGetThrowingException()  {
        Maybe<String> nothing = newNothing();
        shouldThrow.expect(NoSuchElementException.class);
        nothing.get();
    }

    @Test
    public void testGetOrErrorMsg() {
        Maybe<String> bar = newJust("bar");
        assertThat(bar.getOrException("argh!")).isEqualTo("bar");
    }

    @Test
    public void testGetOrErrorMsgThrowingException() {
        Maybe<String> nothing = newNothing();
        shouldThrow.expect(RuntimeException.class);
        shouldThrow.expectMessage("argh!");
        nothing.getOrException("argh!");
    }


    @Test
    public void testGetOrExceptionClass() {
        Maybe<String> bar = newJust("bar");
        assertThat(bar.getOrException(NoSuchElementException.class)).isEqualTo("bar");
    }

    @Test
    public void testGetOrExceptionClassThrowingException() {
        Maybe<String> nothing = newNothing();
        shouldThrow.expect(IllegalArgumentException.class);
        nothing.getOrException(IllegalArgumentException.class);
    }

    @Test
    public void testGetOrExceptionClassMsg() {
        Maybe<String> bar = newJust("bar");
        assertThat(bar.getOrException(IllegalArgumentException.class, "argh!")).isEqualTo("bar");
    }

    @Test
    public void testGetOrExceptionClassMsgThrowingException() {
        Maybe<String> nothing = newNothing();
        shouldThrow.expect(IllegalArgumentException.class);
        shouldThrow.expectMessage("argh!");
        nothing.getOrException(IllegalArgumentException.class, "argh!");
    }

    @Test
    public void testOrElse() {
        Maybe<String> foo = newJust("foo");
        Maybe<String> bar = newJust("bar");
        Maybe<String> nothing = newNothing();
        assertThat(foo.orElse(bar).get()).isEqualTo("foo");
        assertThat(foo.orElse(nothing).get()).isEqualTo("foo");
        assertThat(nothing.orElse(bar).get()).isEqualTo("bar");
        assertThat(nothing.orElse(nothing).isNothing()).isTrue();
    }

    @Test
    public void testIterator() {
        assertThat(newNothing()).isEmpty();
        assertThat(newJust("bar")).containsExactly("bar");
    }

    @Test
    public void testEq() {
        Eq<Maybe<String>> eq = eq(new Eq.JavaEq<String>());
        Maybe<String> nothing1 = newNothing();
        Maybe<String> nothing2 = newNothing();
        Maybe<String> foo1 = newJust("foo");
        Maybe<String> foo2 = newJust("foo");
        Maybe<String> bar = newJust("bar");
        assertThat(eq.eq(nothing1, nothing2)).isTrue();
        assertThat(eq.eq(foo1, foo2)).isTrue();
        assertThat(eq.eq(nothing1, foo1)).isFalse();
        assertThat(eq.eq(foo1, nothing1)).isFalse();
        assertThat(eq.eq(foo1, bar)).isFalse();
    }

    @Test
    public void testMap() {
        Maybe<String> nothing = newNothing();
        assertThat(nothing.map(String::length).isNothing()).isTrue();
        Maybe<String> foo = newJust("foo");
        assertThat(foo.map(String::length).get()).isEqualTo(3);
    }
    
    @Test
    public void testMonad() {
        Monad<Maybe.µ> monad = Maybe.monad;

        //pure
        assertThat(Maybe.narrow(monad.pure("foo")).get()).isEqualTo("foo");

        //map
        Maybe<String> nothing = newNothing();
        assertThat(Maybe.narrow(monad.map(String::length, nothing)).isNothing()).isTrue();
        Maybe<String> foo = newJust("foo");
        assertThat(Maybe.narrow(monad.map(String::length, foo)).get()).isEqualTo(3);

        //ap
        Maybe<Function<String,Integer>> noFn = newNothing();
        Maybe<Function<String,Integer>> lenFn = newJust(String::length);
        assertThat(Maybe.narrow(monad.ap(noFn, nothing)).isNothing()).isTrue();
        assertThat(Maybe.narrow(monad.ap(lenFn, nothing)).isNothing()).isTrue();
        assertThat(Maybe.narrow(monad.ap(noFn, foo)).isNothing()).isTrue();
        assertThat(Maybe.narrow(monad.ap(lenFn, foo)).get()).isEqualTo(3);

        //bind
        Maybe<String> fool = newJust("fool");
        Function<String, _<Maybe.µ, Integer>> lenIfEven =
                s -> s.length() % 2 == 0 ? newJust(s.length()) : Maybe.newNothing();
        assertThat(Maybe.narrow(monad.bind(nothing, lenIfEven)).isNothing()).isTrue();
        assertThat(Maybe.narrow(monad.bind(foo, lenIfEven)).isNothing()).isTrue();
        assertThat(Maybe.narrow(monad.bind(fool, lenIfEven)).get()).isEqualTo(4);
    }

    private void testMonadPlus(MonadPlus<µ> monadPlus) {
        Maybe<String> foo = newJust("foo");
        Maybe<String> bar = newJust("bar");
        Maybe<String> baz = newJust("baz");
        Maybe<String> nothing = newNothing();

        //mzero
        assertThat(Maybe.narrow(monadPlus.mzero()).isNothing()).isTrue();

        //mplus
        //result of  monadPlus.mplus(foo, bar) depends on bias
        assertThat(Maybe.narrow(monadPlus.mplus(foo, nothing)).get()).isEqualTo("foo");
        assertThat(Maybe.narrow(monadPlus.mplus(nothing, bar)).get()).isEqualTo("bar");
        assertThat(Maybe.narrow(monadPlus.mplus(nothing, nothing)).isNothing()).isTrue();

        //guard
        assertThat(Maybe.narrow(monadPlus.guard(false)).isNothing()).isTrue();
        assertThat(Maybe.narrow(monadPlus.guard(true)).get()).isEqualTo(T0.unit);

        //mfilter
        Maybe<Integer> one = newJust(1);
        assertThat(Maybe.narrow(monadPlus.mfilter(Integers.odd::test, one)).get()).isEqualTo(1);
        assertThat(Maybe.narrow(monadPlus.mfilter(Integers.even::test, one)).isNothing()).isTrue();

        //msum
        List<_<Maybe.µ,String>> fooNothingNothing = List.contravariant(List.of(foo, nothing, nothing));
        assertThat(Maybe.narrow(monadPlus.msum(fooNothingNothing)).get()).isEqualTo("foo");
        List<_<Maybe.µ,String>> nothingBarNothing = List.contravariant(List.of(nothing, bar, nothing));
        assertThat(Maybe.narrow(monadPlus.msum(nothingBarNothing)).get()).isEqualTo("bar");
        List<_<Maybe.µ,String>> nothingNothingBaz = List.contravariant(List.of(nothing, nothing, baz));
        assertThat(Maybe.narrow(monadPlus.msum(nothingNothingBaz)).get()).isEqualTo("baz");
        List<_<Maybe.µ,String>> nothingNothingNothing = List.contravariant(List.of(nothing, nothing, nothing));
        assertThat(Maybe.narrow(monadPlus.msum(nothingNothingNothing)).isNothing()).isTrue();
    }


    @Test
    public void testFirstBiasedMonadPlus() {
        MonadPlus<µ> monadPlus = Maybe.firstBiasedMonadPlus;
        testMonadPlus(monadPlus);

        Maybe<String> foo = newJust("foo");
        Maybe<String> bar = newJust("bar");
        Maybe<String> baz = newJust("baz");
        Maybe<String> nothing = newNothing();

        //mplus
        assertThat(Maybe.narrow(monadPlus.mplus(foo, bar)).get()).isEqualTo("foo");

        //msum
        List<_<Maybe.µ,String>> fooBarBaz = List.<_<Maybe.µ,String>,Maybe<String>>contravariant(List.of(foo, bar, baz));
        assertThat(Maybe.narrow(monadPlus.msum(fooBarBaz)).get()).isEqualTo("foo");
        List<_<Maybe.µ,String>> fooBarNothing = List.<_<Maybe.µ,String>,Maybe<String>>contravariant(List.of(foo, bar, nothing));
        assertThat(Maybe.narrow(monadPlus.msum(fooBarNothing)).get()).isEqualTo("foo");
        List<_<Maybe.µ,String>> fooNothingBaz = List.<_<Maybe.µ,String>,Maybe<String>>contravariant(List.of(foo, nothing, baz));
        assertThat(Maybe.narrow(monadPlus.msum(fooNothingBaz)).get()).isEqualTo("foo");
    }

    @Test
    public void testLastBiasedMonadPlus() {
        MonadPlus<µ> monadPlus = Maybe.lastBiasedMonadPlus;
        testMonadPlus(monadPlus);

        Maybe<String> foo = newJust("foo");
        Maybe<String> bar = newJust("bar");
        Maybe<String> baz = newJust("baz");
        Maybe<String> nothing = newNothing();

        //mplus
        assertThat(Maybe.narrow(monadPlus.mplus(foo, bar)).get()).isEqualTo("bar");

        //msum
        List<_<Maybe.µ,String>> fooBarBaz = List.contravariant(List.of(foo, bar, baz));
        assertThat(Maybe.narrow(monadPlus.msum(fooBarBaz)).get()).isEqualTo("baz");
        List<_<Maybe.µ,String>> fooBarNothing = List.contravariant(List.of(foo, bar, nothing));
        assertThat(Maybe.narrow(monadPlus.msum(fooBarNothing)).get()).isEqualTo("bar");
        List<_<Maybe.µ,String>> fooNothingBaz = List.contravariant(List.of(foo, nothing, baz));
        assertThat(Maybe.narrow(monadPlus.msum(fooNothingBaz)).get()).isEqualTo("baz");
    }

    @Test
    public void testJusts() {
         List<Maybe<String>> maybes = List.of(
                 Maybe.<String>newNothing(),
                 newJust("foo"),
                 Maybe.<String>newNothing(),
                 Maybe.<String>lazyJust(() -> "bar"),
                 Maybe.<String>newNothing());
         List<String> strings = justs(maybes);
         assertThat(strings).containsExactly("foo", "bar");
    }
    
    @Test
    public void testAsList() {
        assertThat(newJust("foo").asList()).containsExactly("foo");
        assertThat(lazyJust(() -> "bar").asList()).containsExactly("bar");
        assertThat(newNothing().asList()).isEmpty();
    }

    @Test
    public void testMonadFix() {
        MonadFix<µ> monadFix = Maybe.monad;
        Maybe<String> foo = narrow(monadFix.mfix((Supplier<String> x) -> newJust("foo")));
        assertThat(foo.get()).isEqualTo("foo");
    }

    @Test
    public void testMonadRec()  {
        MonadRec<µ> monadRec = Maybe.monad;

        Function<Integer, _<µ, Either<Integer, String>>> divisibleBy3 = i -> {
           if (i < 10) {
               return i == 0 || i == 3 || i == 6 || i == 9
                       ? Maybe.newJust(Either.newRight("divisible"))
                       : Maybe.newNothing();
           } else {
               return Maybe.newJust(Either.newLeft(i / 10 + i % 10));
           }
        };

        assertThat(Maybe.narrow(monadRec.tailRec(divisibleBy3, 123456789)).get()).isEqualTo("divisible");
        assertThat(Maybe.narrow(monadRec.tailRec(divisibleBy3, 123456788)).isNothing()).isTrue();
    }

}
