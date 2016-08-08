package org.highj.data.transformer;

import static org.assertj.core.api.Assertions.*;
import static org.highj.data.Maybe.*;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.data.tuple.T2;
import org.junit.Test;

public class ListTTest {
    @Test
    public void narrow() {
        ListT<Maybe.µ, Integer> listT = listTOf(42);
        // narrow() must convert __ HKTs
        __<__<ListT.µ, Maybe.µ>, Integer> htk = listT;
        assertThat(ListT.narrow(htk)).isSameAs(listT);
        // narrow() must convert __2 HKTs
        __2<ListT.µ, Maybe.µ, Integer> htk2 = listT;
        assertThat(ListT.narrow(htk2)).isSameAs(listT);
    }

    @Test
    public void yield() {
        ListT<Maybe.µ, Integer> listT = listTOf(42);
        ListT.Yield<Maybe.µ, Integer> yield = ListT.yield(24, () -> listT);
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
        ListT<Maybe.µ, Integer> listT = listTOf(42);
        ListT.Skip<Maybe.µ, Integer> skip = ListT.skip(() -> listT);
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
        ListT<Maybe.µ, Integer> listT = listTOf(42);
        Maybe<ListT.Step<Maybe.µ, Integer>> maybe = Maybe.narrow(listT.run());
        // run() must contain head and tail of the list
        assertThat(maybe.get().map(ListT.Yield::head, s -> 0, () -> 0)).isEqualTo(42);
        assertThat(maybe.get().map(y -> Maybe.narrow(y.tail().get().head(monad)).get().isNothing(),
                s -> false, () -> false)).isTrue();
    }

    @Test
    public void nil() {
        ListT<Maybe.µ, Integer> listT = ListT.nil(monad);
        Maybe<ListT.Step<Maybe.µ, Integer>> maybe = Maybe.narrow(listT.run());
        // nil() must result in Done
        assertThat(maybe.get().map(y -> false, s -> false, () -> true)).isTrue();
        // the list produced by nil() must be empty
        assertListTEquals(listT);
    }

    @Test
    public void cons() {
        ListT<Maybe.µ, Integer> listT = ListT.cons(monad, () -> 24, () -> listTOf(42));
        // cons() must preserve head and tail
        assertListTEquals(listT, 24, 42);
    }

    @Test
    public void prepend_() {
        ListT<Maybe.µ, Integer> listT = ListT.prepend_(monad, 24, () -> listTOf(42));
        // prepend_() must preserve head and tail
        assertListTEquals(listT, 24, 42);
    }

    @Test
    public void prepend() {
        ListT<Maybe.µ, Integer> listT = ListT.singleton(monad, 42).prepend(monad, 24);
        // prepend() must preserve head and tail
        assertListTEquals(listT, 24, 42);
    }

    @Test
    public void stepMap() {
        ListT<Maybe.µ, String> listT = ListT.prepend_(monad, "foo", () -> listTOf("foobar"));
        // stepMap() must convert head and tail
        ListT<Maybe.µ, Integer> listInt = listT.stepMap(monad, step -> step.map(
                y -> ListT.yield(y.head().length(),
                        y.mapTail(l -> ListT.functor(monad).map(String::length, l))),
                s -> ListT.skip(() -> ListT.nil(monad)),
                ListT::done));
        assertListTEquals(listInt, 3, 6);
    }

    @Test
    public void concat() {
        // concat() must preserve both lists
        ListT<Maybe.µ, Integer> listOneTwo = ListT.concat(monad,
                listTOf(12, 13), listTOf(14, 15));
        assertListTEquals(listOneTwo, 12, 13, 14, 15);
        // concat() must skip first list when empty
        ListT<Maybe.µ, Integer> listNilTwo = ListT.concat(monad,
                ListT.nil(monad), listTOf(14, 15));
        assertListTEquals(listNilTwo, 14, 15);
        // concat() must skip second list when empty
        ListT<Maybe.µ, Integer> listOneNil = ListT.concat(monad,
                listTOf(12, 13), ListT.nil(monad));
        assertListTEquals(listOneNil, 12, 13);
        // concat() must skip both lists when empty
        ListT<Maybe.µ, Integer> listNilNil = ListT.concat(monad,
                ListT.nil(monad), ListT.nil(monad));
        assertListTEquals(listNilNil);
    }

    @Test
    public void append() {
        // append must preserve both lists
        ListT<Maybe.µ, Integer> listT = listTOf(12, 13).append(monad, listTOf(14, 15));
        assertListTEquals(listT, 12, 13, 14, 15);
    }

    @Test
    public void singleton() {
        ListT<Maybe.µ, Integer> listT = ListT.singleton(monad, 42);
        // singleton() must produce a list with a head and an empty tail
        assertListTEquals(listT, 42);
    }

    @Test
    public void fromEffect() {
        ListT<Maybe.µ, Integer> listT = ListT.fromEffect(monad, Just(42));
        // fromEffect() must produce a singleton list from a monadic value
        assertListTEquals(listT, 42);
    }

    @Test
    public void wrapEffect() {
        // wrapEffect() must construct a list from a wrapped list
        ListT<Maybe.µ, Integer> wrapped = ListT.wrapEffect(monad, Just(listTOf(12, 13)));
        assertListTEquals(wrapped, 12, 13);
    }

    @Test
    public void wrapLazy() {
        // wrapEffect() must construct a list from a lazy list
        ListT<Maybe.µ, Integer> wrapped = ListT.wrapLazy(monad, () -> listTOf(12, 13));
        assertListTEquals(wrapped, 12, 13);
    }

    @Test
    public void unfold() {
        // unfold() must produce a list that may break up
        Function<Integer, __<µ, Maybe<T2<Integer, String>>>> fn = n ->
                Just(n > 3 ? Nothing() : Just(T2.of(n + 1, n + "#")));
        ListT<Maybe.µ, String> listT = ListT.unfold(monad, fn, 0);
        assertListTEquals(listT, "0#", "1#", "2#", "3#");
    }

    @Test
    public void iterate() {
        // iterate must produce an infinite list
        ListT<Maybe.µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0);
        assertListTStartsWith(listT, 0, 1, 2, 3, 4, 5, 6);
    }

    @Test
    public void repeat() {
        // repeat must produce an infinite list of the same value
        ListT<Maybe.µ, Integer> listT = ListT.repeat(monad, 42);
        assertListTStartsWith(listT, 42, 42, 42, 42, 42, 42);
    }

    @Test
    public void take() {
        // take() with a positive argument must return a list prefix of this length
        // if the list contains enough elements
        ListT<Maybe.µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).take(monad, 4);
        assertListTEquals(listT, 0, 1, 2, 3);
        // take() with a non-positive argument must return an empty list
        ListT<Maybe.µ, Integer> listNil = ListT.iterate(monad, n -> n + 1, 0).take(monad, -100);
        assertListTEquals(listNil);
        // take() with a positive argument must return all elements of the list,
        // if its argument is larger than the list size
        ListT<Maybe.µ, Integer> listShort = listTOf(12, 13).take(monad, 100);
        assertListTEquals(listShort, 12, 13);
    }

    @Test
    public void takeWhile() {
        // takeWhile() must return a list prefix of all subsequent elements fulfilling the predicate
        ListT<Maybe.µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).takeWhile(monad, n -> n < 4);
        assertListTEquals(listT, 0, 1, 2, 3);
        // takeWhile() must return the whole list if all elements fulfill the predicate
        ListT<Maybe.µ, Integer> listShort = listTOf(12, 13).takeWhile(monad, i -> i < 100);
        assertListTEquals(listShort, 12, 13);
    }

    @Test
    public void drop() {
        // drop() must skip the given number of elements, if the list is long enough
        ListT<Maybe.µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).drop(monad, 4);
        assertListTStartsWith(listT, 4, 5, 6, 7);
        // drop() must return an empty list if the list is smaller than the drop count
        ListT<Maybe.µ, Integer> listShort = listTOf(12, 13).drop(monad, 4);
        assertListTEquals(listShort);
        // drop() must leave the list intact when the argument is non-positive
        ListT<Maybe.µ, Integer> listT2 = ListT.iterate(monad, n -> n + 1, 0).drop(monad, -4);
        assertListTStartsWith(listT2, 0, 1, 2, 3);
    }

    @Test
    public void dropWhile() {
        // dropWhile() must skip the fulfilling elements, if the list is long enough
        ListT<Maybe.µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).dropWhile(monad, n -> n < 10);
        assertListTStartsWith(listT, 10, 11, 12, 13);
        // dropWhile() must return an empty list if all list elements satisfy the predicate
        ListT<Maybe.µ, Integer> listShort = listTOf(12, 13).dropWhile(monad, n -> n < 100);
        assertListTEquals(listShort);
        // dropWhile() must leave the list intact when the first element fulfills the predicate
        ListT<Maybe.µ, Integer> listT2 = ListT.iterate(monad, n -> n + 1, 0).dropWhile(monad, n -> n > 0);
        assertListTStartsWith(listT2, 0, 1, 2, 3);
    }

    @Test
    public void filter() {
        // filter() must eliminate all list elements not fulfilling the predicate
        ListT<Maybe.µ, Integer> listT = ListT.iterate(monad, n -> n + 1, 0).filter(monad, n -> n % 5 == 0);
        assertListTStartsWith(listT, 0, 5, 10, 15, 20);
    }

    @Test
    public void mapMaybe() {
        // mapMaybe() must return a list of all elements wrapped in Just by the given function
        ListT<Maybe.µ, String> listT = ListT.iterate(monad, n -> n + 1, 0)
                .mapMaybe(monad, n -> n % 5 == 0 ? Just(n + "#") : Nothing());
    }

    @Test
    public void catMaybes() {
        // catMaybes() must return a list of all unwrapped elements
        ListT<Maybe.µ, Integer> listT = ListT.catMaybes(monad, listTOf(Just(12), Nothing(), Just(13), Just(14)));
        assertListTEquals(listT, 12, 13, 14);
    }

    @Test
    public void uncons() {
        // uncons() must return head and tail of a non-empty list
        ListT<Maybe.µ, Integer> listT = listTOf(12,13,14,15);
        Maybe<Maybe<T2<Integer, ListT<Maybe.µ, Integer>>>> maybe = Maybe.narrow(listT.uncons(monad));
        T2<Integer, ListT<Maybe.µ, Integer>> t2 = maybe.get().get();
        assertThat(t2._1()).isEqualTo(12);
        ListT<Maybe.µ, Integer> tail = ListT.narrow(t2._2());
        assertListTEquals(tail, 13,14, 15);
        // uncons() must return Nothing for an empty list
        ListT<Maybe.µ, Integer> listNil = ListT.nil(monad);
        Maybe<Maybe<T2<Integer, ListT<Maybe.µ, Integer>>>> maybeNil = Maybe.narrow(listNil.uncons(monad));
        assertThat(maybeNil.get().isNothing()).isTrue();
    }

    @Test
    public void head() {
        // head() should return the head of a non-empty list
        ListT<Maybe.µ, Integer> listT = listTOf(12,13,14,15);
        Maybe<Maybe<Integer>> maybe = Maybe.narrow(listT.head(monad));
        assertThat(maybe.get().get()).isEqualTo(12);
        // head() should return Nothing for an empty list
        ListT<Maybe.µ, Integer> listNil = ListT.nil(monad);
        Maybe<Maybe<Integer>> maybeNil = Maybe.narrow(listNil.head(monad));
        assertThat(maybeNil.get().isNothing()).isTrue();
    }

    @Test
    public void tail() {
        // tail() should return the tail of a non-empty list
        ListT<Maybe.µ, Integer> listT = listTOf(12,13,14,15);
        Maybe<Maybe<ListT<Maybe.µ, Integer>>> maybe = Maybe.narrow(listT.tail(monad));
        ListT<Maybe.µ, Integer> tail = maybe.get().get();
        assertListTEquals(tail, 13,14,15);
        // tail() should return Nothing for an empty list
        ListT<Maybe.µ, Integer> listNil = ListT.nil(monad);
        Maybe<Maybe<ListT<Maybe.µ, Integer>>> maybeNil = Maybe.narrow(listNil.tail(monad));
        assertThat(maybeNil.get().isNothing()).isTrue();
    }

    @Test
    public void foldl_() {
        // foldl_ should fold a list
        ListT<µ, Integer> listT = listTOf(1,2,3,4);
        Maybe<Integer> integer = Maybe.narrow(listT.foldl_(monad,
                (x, y) -> Just(10 * x + y), 0));
        assertThat(integer.get()).isEqualTo(1234);
        //TODO what is the expected result when return Nothing in the function?
    }

    @Test
    public void foldl() {
        // foldl should fold a list
        ListT<µ, Integer> listT = listTOf(1,2,3,4);
        Maybe<Integer> integer = Maybe.narrow(listT.foldl(monad, (x, y) -> 10 * x + y, 0));
        assertThat(integer.get()).isEqualTo(1234);
    }

    @Test
    public void scanl() {
        // scanl should fold a list, but keep the intermediate results
        ListT<µ, Integer> listT = listTOf(1,2,3,4);
        ListT<µ, Integer> integers = listT.scanl(monad, (x, y) -> 10 * x + y, 0);
        assertListTEquals(integers, 1,12,123,1234);

    }

    @Test
    public void zipWith_() {

    }

    @Test
    public void zipWith() {

    }

    @Test
    public void semigroup() {

    }

    @Test
    public void monoid() {

    }

    @Test
    public void functor() {

    }

    @Test
    public void apply() {

    }

    @Test
    public void applicative() {

    }

    @Test
    public void bind() {

    }

    @Test
    public void monad() {

    }

    @Test
    public void monadTrans() {

    }

    @Test
    public void alt() {

    }

    @Test
    public void plus() {

    }

    @Test
    public void alternative() {

    }

    @Test
    public void monadZero() {

    }

    @Test
    public void monadPlus() {

    }

    private static <A> void assertListTEquals(ListT<µ, A> list, A... as) {
        ListT<µ, A> current = list;
        for (A a : as) {
            assertThat(current.head(monad)).isEqualTo(Just(Just(a)));
            current = Maybe.narrow(current.tail(monad)).get().get();
        }
        assertThat(current.head(monad)).isEqualTo(Just(Nothing()));
    }

    private static <A> void assertListTStartsWith(ListT<µ, A> list, A... as) {
        ListT<µ, A> current = list;
        for (A a : as) {
            assertThat(current.head(monad)).isEqualTo(Just(Just(a)));
            current = Maybe.narrow(current.tail(monad)).get().get();
        }
    }

    private static <A> ListT<µ, A> listTOf(A... as) {
        Function<Integer, __<µ, Maybe<T2<Integer, A>>>> fn = n ->
                Just(n >= as.length ? Nothing() : Just(T2.of(n + 1, as[n])));
        return ListT.unfold(monad, fn, 0);
    }
}