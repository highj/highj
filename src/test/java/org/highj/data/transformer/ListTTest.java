package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asListT;
import static org.highj.Hkt.asMaybe;
import static org.highj.data.Maybe.*;

public class ListTTest {
    @Test
    public void narrow() {
        ListT<µ, Integer> listT = listTOf(42);
        // narrow() must convert __ HKTs
        __<__<ListT.µ, µ>, Integer> htk = listT;
        assertThat(asListT(htk)).isSameAs(listT);
        // narrow() must convert __2 HKTs
        __2<ListT.µ, µ, Integer> htk2 = listT;
        assertThat(asListT(htk2)).isSameAs(listT);
    }

    @Test
    public void yield() {
        ListT<µ, Integer> listT = listTOf(42);
        ListT.Yield<µ, Integer> yield = ListT.yield(24, () -> listT);
        // yield() must preserve head and tail
        assertThat(yield.head()).isEqualTo(24);
        assertThat(yield.tail().get()).isSameAs(listT);
        // Yield must call the correct map() function
        assertThat(yield.map(y -> y == yield, s -> false, () -> false)).isTrue();
        // Yield.mapTail() must convert the tail
        assertThat(yield.mapTail(l -> l.head(monad)).get()).isEqualTo(Just(Just(42)));
    }

    @Test
    public void skip() {
        ListT<µ, Integer> listT = listTOf(42);
        ListT.Skip<µ, Integer> skip = ListT.skip(() -> listT);
        // skip() must preserve the tail
        assertThat(skip.tail().get()).isSameAs(listT);
        // Skip must call the correct map() function
        assertThat(skip.map(y -> false, s -> s == skip, () -> false)).isTrue();
        // Skip.mapTail() must convert the tail
        assertThat(skip.mapTail(l -> l.head(monad)).get()).isEqualTo(Just(Just(42)));
    }

    @Test
    public void done() {
        // Done must call the map() supplier
        assertThat(ListT.done().map(y -> false, s -> false, () -> true)).isTrue();
    }

    @Test
    public void run() {
        ListT<µ, Integer> listT = listTOf(42);
        Maybe<ListT.Step<µ, Integer>> maybe = asMaybe(listT.run());
        // run() must contain head and tail of the list
        assertThat(maybe.get().map(ListT.Yield::head, s -> 0, () -> 0)).isEqualTo(42);
        assertThat(maybe.get().map(y -> asMaybe(y.tail().get().head(monad)).get().isNothing(),
            s -> false, () -> false)).isTrue();
    }

    @Test
    public void nil() {
        ListT<µ, Integer> listT = ListT.nil(monad);
        Maybe<ListT.Step<µ, Integer>> maybe = asMaybe(listT.run());
        // nil() must result in Done
        assertThat(maybe.get().map(y -> false, s -> false, () -> true)).isTrue();
        // the list produced by nil() must be empty
        assertListTEquals(listT);
    }

    @Test
    public void cons() {
        ListT<µ, Integer> listT = ListT.cons(monad, () -> 24, () -> listTOf(42));
        // cons() must preserve head and tail
        assertListTEquals(listT, 24, 42);
    }

    @Test
    public void prepend_() {
        ListT<µ, Integer> listT = ListT.prepend_(monad, 24, () -> listTOf(42));
        // prepend_() must preserve head and tail
        assertListTEquals(listT, 24, 42);
    }

    @Test
    public void prepend() {
        ListT<µ, Integer> listT = ListT.singleton(monad, 42).prepend(monad, 24);
        // prepend() must preserve head and tail
        assertListTEquals(listT, 24, 42);
    }

    @Test
    public void stepMap() {
        ListT<µ, String> listT = ListT.prepend_(monad, "foo", () -> listTOf("foobar"));
        // stepMap() must convert head and tail
        ListT<µ, Integer> listInt = listT.stepMap(monad, step -> step.map(
            y -> ListT.yield(y.head().length(),
                y.mapTail(l -> ListT.functor(monad).map(String::length, l))),
            s -> ListT.skip(() -> ListT.nil(monad)),
            ListT::done));
        assertListTEquals(listInt, 3, 6);
    }

    @Test
    public void concat() {
        // concat() must preserve both lists
        ListT<µ, Integer> listOneTwo = ListT.concat(monad,
            listTOf(12, 13), listTOf(14, 15));
        assertListTEquals(listOneTwo, 12, 13, 14, 15);
        // concat() must skip first list when empty
        ListT<µ, Integer> listNilTwo = ListT.concat(monad,
            ListT.nil(monad), listTOf(14, 15));
        assertListTEquals(listNilTwo, 14, 15);
        // concat() must skip second list when empty
        ListT<µ, Integer> listOneNil = ListT.concat(monad,
            listTOf(12, 13), ListT.nil(monad));
        assertListTEquals(listOneNil, 12, 13);
        // concat() must skip both lists when empty
        ListT<µ, Integer> listNilNil = ListT.concat(monad,
            ListT.nil(monad), ListT.nil(monad));
        assertListTEquals(listNilNil);
    }

    @Test
    public void append() {
        // append must preserve both lists
        ListT<µ, Integer> listT = listTOf(12, 13).append(monad, listTOf(14, 15));
        assertListTEquals(listT, 12, 13, 14, 15);
    }

    @Test
    public void singleton() {
        ListT<µ, Integer> listT = ListT.singleton(monad, 42);
        // singleton() must produce a list with a head and an empty tail
        assertListTEquals(listT, 42);
    }

    @Test
    public void fromEffect() {
        ListT<µ, Integer> listT = ListT.fromEffect(monad, Just(42));
        // fromEffect() must produce a singleton list from a monadic value
        assertListTEquals(listT, 42);
    }

    @Test
    public void wrapEffect() {
        // wrapEffect() must construct a list from a wrapped list
        ListT<µ, Integer> wrapped = ListT.wrapEffect(monad, Just(listTOf(12, 13)));
        assertListTEquals(wrapped, 12, 13);
    }

    @Test
    public void wrapLazy() {
        // wrapEffect() must construct a list from a lazy list
        ListT<µ, Integer> wrapped = ListT.wrapLazy(monad, () -> listTOf(12, 13));
        assertListTEquals(wrapped, 12, 13);
    }

    @Test
    public void unfold() {
        // unfold() must produce a list that may break up
        Function<Integer, __<µ, Maybe<T2<Integer, String>>>> fn = n ->
                                                                      Just(n > 3 ? Nothing() : Just(T2.of(n + 1, n + "#")));
        ListT<µ, String> listT = ListT.unfold(monad, fn, 0);
        assertListTEquals(listT, "0#", "1#", "2#", "3#");
    }

    @Test
    public void iterate() {
        // iterate must produce an infinite list
        ListT<µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0);
        assertListTStartsWith(listT, 0, 1, 2, 3, 4, 5, 6);
    }

    @Test
    public void repeat() {
        // repeat must produce an infinite list of the same value
        ListT<µ, Integer> listT = ListT.repeat(monad, 42);
        assertListTStartsWith(listT, 42, 42, 42, 42, 42, 42);
    }

    @Test
    public void take() {
        // take() with a positive argument must return a list prefix of this length
        // if the list contains enough elements
        ListT<µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).take(monad, 4);
        assertListTEquals(listT, 0, 1, 2, 3);
        // take() with a non-positive argument must return an empty list
        ListT<µ, Integer> listNil = ListT.iterate(monad, n -> n + 1, 0).take(monad, -100);
        assertListTEquals(listNil);
        // take() with a positive argument must return all elements of the list,
        // if its argument is larger than the list size
        ListT<µ, Integer> listShort = listTOf(12, 13).take(monad, 100);
        assertListTEquals(listShort, 12, 13);
    }

    @Test
    public void takeWhile() {
        // takeWhile() must return a list prefix of all subsequent elements fulfilling the predicate
        ListT<µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).takeWhile(monad, n -> n < 4);
        assertListTEquals(listT, 0, 1, 2, 3);
        // takeWhile() must return the whole list if all elements fulfill the predicate
        ListT<µ, Integer> listShort = listTOf(12, 13).takeWhile(monad, i -> i < 100);
        assertListTEquals(listShort, 12, 13);
    }

    @Test
    public void drop() {
        // drop() must skip the given number of elements, if the list is long enough
        ListT<µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).drop(monad, 4);
        assertListTStartsWith(listT, 4, 5, 6, 7);
        // drop() must return an empty list if the list is smaller than the drop count
        ListT<µ, Integer> listShort = listTOf(12, 13).drop(monad, 4);
        assertListTEquals(listShort);
        // drop() must leave the list intact when the argument is non-positive
        ListT<µ, Integer> listT2 = ListT.iterate(monad, n -> n + 1, 0).drop(monad, -4);
        assertListTStartsWith(listT2, 0, 1, 2, 3);
    }

    @Test
    public void dropWhile() {
        // dropWhile() must skip the fulfilling elements, if the list is long enough
        ListT<µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).dropWhile(monad, n -> n < 10);
        assertListTStartsWith(listT, 10, 11, 12, 13);
        // dropWhile() must return an empty list if all list elements satisfy the predicate
        ListT<µ, Integer> listShort = listTOf(12, 13).dropWhile(monad, n -> n < 100);
        assertListTEquals(listShort);
        // dropWhile() must leave the list intact when the first element fulfills the predicate
        ListT<µ, Integer> listT2 = ListT.iterate(monad, n -> n + 1, 0).dropWhile(monad, n -> n > 0);
        assertListTStartsWith(listT2, 0, 1, 2, 3);
    }

    @Test
    public void filter() {
        // filter() must eliminate all list elements not fulfilling the predicate
        ListT<µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).filter(monad, n -> n % 5 == 0);
        assertListTStartsWith(listT, 0, 5, 10, 15, 20);
    }

    @Test
    public void mapMaybe() {
        // mapMaybe() must return a list of all elements wrapped in Just by the given function
        ListT<µ, String> listT = ListT.iterate(monad, n -> n + 1, 0)
                                      .mapMaybe(monad, n -> n % 5 == 0 ? Just(n + "#") : Nothing());
    }

    @Test
    public void catMaybes() {
        // catMaybes() must return a list of all unwrapped elements
        ListT<µ, Integer> listT = ListT.catMaybes(monad, listTOf(Just(12), Nothing(), Just(13), Just(14)));
        assertListTEquals(listT, 12, 13, 14);
    }

    @Test
    public void uncons() {
        // uncons() must return head and tail of a non-empty list
        ListT<µ, Integer> listT = listTOf(12, 13, 14, 15);
        Maybe<Maybe<T2<Integer, ListT<µ, Integer>>>> maybe = asMaybe(listT.uncons(monad));
        T2<Integer, ListT<µ, Integer>> t2 = maybe.get().get();
        assertThat(t2._1()).isEqualTo(12);
        ListT<µ, Integer> tail = asListT(t2._2());
        assertListTEquals(tail, 13, 14, 15);
        // uncons() must return Nothing for an empty list
        ListT<µ, Integer> listNil = ListT.nil(monad);
        Maybe<Maybe<T2<Integer, ListT<µ, Integer>>>> maybeNil = asMaybe(listNil.uncons(monad));
        assertThat(maybeNil.get().isNothing()).isTrue();
    }

    @Test
    public void head() {
        // head() must return the head of a non-empty list
        ListT<µ, Integer> listT = listTOf(12, 13, 14, 15);
        Maybe<Maybe<Integer>> maybe = asMaybe(listT.head(monad));
        assertThat(maybe.get().get()).isEqualTo(12);
        // head() must return Nothing for an empty list
        ListT<µ, Integer> listNil = ListT.nil(monad);
        Maybe<Maybe<Integer>> maybeNil = asMaybe(listNil.head(monad));
        assertThat(maybeNil.get().isNothing()).isTrue();
    }

    @Test
    public void tail() {
        // tail() must return the tail of a non-empty list
        ListT<µ, Integer> listT = listTOf(12, 13, 14, 15);
        Maybe<Maybe<ListT<µ, Integer>>> maybe = asMaybe(listT.tail(monad));
        ListT<µ, Integer> tail = maybe.get().get();
        assertListTEquals(tail, 13, 14, 15);
        // tail() must return Nothing for an empty list
        ListT<µ, Integer> listNil = ListT.nil(monad);
        Maybe<Maybe<ListT<µ, Integer>>> maybeNil = asMaybe(listNil.tail(monad));
        assertThat(maybeNil.get().isNothing()).isTrue();
    }

    @Test
    public void foldl_() {
        // foldl_ must fold a list
        ListT<µ, Integer> listT = listTOf(1, 2, 3, 4);
        Maybe<Integer> integer = asMaybe(listT.foldl_(monad,
            (x, y) -> Just(10 * x + y), 0));
        assertThat(integer.get()).isEqualTo(1234);
        // TODO what is the expected result when return Nothing in the function?
    }

    @Test
    public void foldl() {
        // foldl must fold a list
        ListT<µ, Integer> listT = listTOf(1, 2, 3, 4);
        Maybe<Integer> integer = asMaybe(listT.foldl(monad, (x, y) -> 10 * x + y, 0));
        assertThat(integer.get()).isEqualTo(1234);
    }

    @Test
    public void scanl() {
        // scanl() must fold a list and keep the intermediate results
        ListT<µ, Integer> listT = listTOf(1, 2, 3, 4);
        ListT<µ, Integer> integers = listT.scanl(monad, (x, y) -> 10 * x + y, 0);
        assertListTEquals(integers, 1, 12, 123, 1234);
    }

    @Test
    public void zipWith_() {
        // zipWith_() must zip two lists (even if one input list is infinite)
        ListT<µ, Integer> listInt = ListT.iterate(monad, n -> n + 1, 1);
        ListT<µ, String> listString = listTOf("foo", "bar", "baz", "quux");
        ListT<µ, String> listT = ListT.zipWith_(monad,
            (i, s) -> Just(s.substring(0, i)), listInt, listString);
        assertListTEquals(listT, "f", "ba", "baz", "quux");
        // zipWith_() must respect the semantic of the monadic result
        // (here: if one or more computations fail, the list is Nothing)
        ListT<µ, String> listFail = ListT.zipWith_(monad,
            (i, s) -> i < 2 ? Nothing() : Just(s), listInt, listString);
        assertThat(listFail.head(monad)).isEqualTo(Nothing());
        // zipWith_() must produce an empty list if one or both input lists are empty
        ListT<µ, String> listOneNil = ListT.zipWith_(monad,
            (i, s) -> Just(s.substring(0, i)), listInt, ListTTest.<String>listTOf());
        assertListTEquals(listOneNil);
        ListT<µ, String> listNilTwo = ListT.zipWith_(monad,
            (i, s) -> Just(s.substring(0, i)), ListTTest.<Integer>listTOf(), listString);
        assertListTEquals(listNilTwo);
        ListT<µ, String> listNilNil = ListT.zipWith_(monad,
            (i, s) -> Just(s.substring(0, i)), ListTTest.<Integer>listTOf(), ListTTest.<String>listTOf());
        assertListTEquals(listNilNil);
    }

    @Test
    public void zipWith() {
        // zipWith() must zip two lists
        ListT<µ, Integer> listInt = listTOf(1, 2, 3);
        ListT<µ, String> listString = listTOf("foo", "bar", "baz", "quux");
        ListT<µ, String> listT = ListT.zipWith(monad,
            (i, s) -> s.substring(0, i), listInt, listString);
        assertListTEquals(listT, "f", "ba", "baz");
        // zipWith() must produce an empty list if one or both input lists are empty
        ListT<µ, String> listOneNil = ListT.zipWith(monad,
            (i, s) -> s.substring(0, i), listInt, ListTTest.<String>listTOf());
        assertListTEquals(listOneNil);
        ListT<µ, String> listNilTwo = ListT.zipWith(monad,
            (i, s) -> s.substring(0, i), ListTTest.<Integer>listTOf(), listString);
        assertListTEquals(listNilTwo);
        ListT<µ, String> listNilNil = ListT.zipWith(monad,
            (i, s) -> s.substring(0, i), ListTTest.<Integer>listTOf(), ListTTest.<String>listTOf());
        assertListTEquals(listNilNil);
    }

    @Test
    public void toIterator() {
        ListT<T1.µ, Integer> list = ListT.iterate(T1.applicative, x -> 2 * x, 1).take(T1.applicative, 10);
        assertThat(toIterable(ListT.toIterator(list))).containsExactly(1, 2, 4, 8, 16, 32, 64, 128, 256, 512);
        ListT<T1.µ, Integer> listWithSkip = list.drop(T1.applicative, 2);
        assertThat(toIterable(ListT.toIterator(listWithSkip))).containsExactly(4, 8, 16, 32, 64, 128, 256, 512);
    }

    @Test
    public void semigroup() {
        // the semigroup instance should concat two lists
        ListT<µ, Integer> listOne = listTOf(1, 2, 3);
        ListT<µ, Integer> listTwo = listTOf(4, 5);
        ListT<µ, Integer> listDot = ListT.<µ, Integer>semigroup(monad).apply(listOne, listTwo);
        assertListTEquals(listDot, 1, 2, 3, 4, 5);
    }

    @Test
    public void monoid() {
        // the monoid instance should concat two lists
        ListT<µ, Integer> listOne = listTOf(1, 2, 3);
        ListT<µ, Integer> listTwo = listTOf(4, 5);
        ListT<µ, Integer> listDot = ListT.<µ, Integer>monoid(monad).apply(listOne, listTwo);
        assertListTEquals(listDot, 1, 2, 3, 4, 5);
        // the monoid instance should have the empty list as neutral element
        ListT<µ, Integer> listZero = ListT.<µ, Integer>monoid(monad).identity();
        assertListTEquals(listZero);
    }

    @Test
    public void functor() {
        // the functor instance must map a list
        ListT<µ, String> listString = listTOf("one", "two", "three");
        ListT<µ, Integer> listInt = ListT.functor(monad).map(String::length, listString);
        assertListTEquals(listInt, 3, 3, 5);
    }

    @Test
    public void unfoldable() {
        // the unfoldable instance must be able to create a list
        ListT<µ, String> listT = ListT.unfoldable(monad)
                                      .unfoldr(i -> i < 5 ? Just(T2.of(i + "#", i + 1)) : Nothing(), 0);
        assertListTEquals(listT, "0#", "1#", "2#", "3#", "4#");
    }

    @Test
    public void apply() {
        // the apply instance must apply the functions to the values
        ListT<µ, Function<Integer, Integer>> listFn = listTOf(x -> x + 20, y -> y * 4);
        ListT<µ, Integer> listInt = listTOf(1, 2, 3);
        ListT<µ, Integer> listAp = ListT.apply(monad).ap(listFn, listInt);
        assertListTEquals(listAp, 21, 22, 23, 4, 8, 12);
    }

    @Test
    public void applicative() {
        // the applicative instance must provide a singleton list as pure value
        ListT<µ, Integer> listPure = ListT.applicative(monad).pure(42);
        assertListTEquals(listPure, 42);
    }

    @Test
    public void zipApplicative() {
        // the zipApplicative instance must apply the functions to the values
        ListT<µ, Function<Integer, Integer>> listFn = listTOf(x -> x + 10, y -> y + 20, z -> z + 30);
        ListT<µ, Integer> listInt = listTOf(1, 2, 3, 4);
        ListT<µ, Integer> listAp = ListT.zipApplicative(monad).ap(listFn, listInt);
        assertListTEquals(listAp, 11, 22, 33);
        // the zipApplicative instance must provide a repeating list as pure value
        ListT<µ, Integer> listPure = ListT.zipApplicative(monad).pure(42);
        assertListTStartsWith(listPure, 42, 42, 42, 42);
    }

    @Test
    public void bind() {
        // the bind instance must apply the given function to the list
        ListT<µ, Integer> listT = listTOf(1, 2, 3);
        ListT<µ, Integer> listBind = ListT.bind(monad).bind(listT, i -> listTOf(i, i, i));
        assertListTEquals(listBind, 1, 1, 1, 2, 2, 2, 3, 3, 3);
    }

    @Test
    public void monad() {
        // has no new methods
    }

    @Test
    public void monadTrans() {
        // the monadTrans instance must lift a monad value to a list
        ListT<µ, Integer> listMaybe = ListT.monadTrans(monad).lift(Just(42));
        assertListTEquals(listMaybe, 42);
        ListT<List.µ, Integer> listList = ListT.monadTrans(List.monadPlus).<Integer>lift(List.of(11, 12, 13, 14));
        assertThat(listList.head(List.monadPlus)).isEqualTo(List.of(Just(11), Just(12), Just(13), Just(14)));
    }

    @Test
    public void alt() {
        // the alt instance must append two lists
        ListT<µ, Integer> listOne = listTOf(1, 2, 3);
        ListT<µ, Integer> listTwo = listTOf(4, 5);
        ListT<µ, Integer> listOneTwo = ListT.alt(monad).mplus(listOne, listTwo);
        assertListTEquals(listOneTwo, 1, 2, 3, 4, 5);
    }

    @Test
    public void plus() {
        // the plus instance must provide a neutral element, here an empty list
        ListT<µ, Integer> listZero = ListT.plus(monad).mzero();
        assertListTEquals(listZero);
    }

    @Test
    public void alternative() {
        // has no new methods
    }

    @Test
    public void monadZero() {
        // the monadZero instance must provide a neutral element, here an empty list
        ListT<µ, Integer> listZero = ListT.monadZero(monad).mzero();
        assertListTEquals(listZero);
    }

    @Test
    public void monadPlus() {
        // the monadPlus instance must append two lists
        ListT<µ, Integer> listOne = listTOf(1, 2, 3);
        ListT<µ, Integer> listTwo = listTOf(4, 5);
        ListT<µ, Integer> listOneTwo = ListT.monadPlus(monad).mplus(listOne, listTwo);
        assertListTEquals(listOneTwo, 1, 2, 3, 4, 5);
    }

    @Test
    public void monadRec() {
        Function<String, __<__<ListT.µ, µ>, Either<String, Character>>> substrings = s -> {
            if (s.length() == 1) {
                return ListT.singleton(monad, Either.Right(s.charAt(0)));
            }
            ListT<µ, String> result = ListT.nil(monad);
            for (int lower = 0; lower < s.length(); lower++) {
                for (int higher = lower + 1; higher <= s.length(); higher++) {
                    String t = s.substring(lower, higher);
                    if (!t.equals(s)) {
                        result = result.prepend(monad, s.substring(lower, higher));
                    }
                }
            }
            return ListT.functor(monad).map(Either::Left, result);
        };

        ListT<µ, Character> listT = asListT(ListT.monadRec(monad).tailRec(substrings, "abc"));
        assertListTEquals(listT, 'c', 'c', 'b', 'b', 'b', 'a', 'a');
    }

    @SafeVarargs
    private static <A> void assertListTEquals(ListT<µ, A> list, A... as) {
        ListT<µ, A> current = list;
        for (A a : as) {
            assertThat(current.head(monad)).isEqualTo(Just(Just(a)));
            current = asMaybe(current.tail(monad)).get().get();
        }
        assertThat(current.head(monad)).isEqualTo(Just(Nothing()));
    }

    @SafeVarargs
    private static <A> void assertListTStartsWith(ListT<µ, A> list, A... as) {
        ListT<µ, A> current = list;
        for (A a : as) {
            assertThat(current.head(monad)).isEqualTo(Just(Just(a)));
            current = asMaybe(current.tail(monad)).get().get();
        }
    }

    @SafeVarargs
    private static <A> ListT<µ, A> listTOf(A... as) {
        Function<Integer, __<µ, Maybe<T2<Integer, A>>>> fn = n ->
                                                                 Just(n >= as.length ? Nothing() : Just(T2.of(n + 1, as[n])));
        return ListT.unfold(monad, fn, 0);
    }

    private static <T> Iterable<T> toIterable(Iterator<T> it) {
        return () -> it;
    }
}