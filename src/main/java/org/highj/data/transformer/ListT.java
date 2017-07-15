package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.data.transformer.list.ListTAlt;
import org.highj.data.transformer.list.ListTAlternative;
import org.highj.data.transformer.list.ListTApplicative;
import org.highj.data.transformer.list.ListTApply;
import org.highj.data.transformer.list.ListTBind;
import org.highj.data.transformer.list.ListTFunctor;
import org.highj.data.transformer.list.ListTMonad;
import org.highj.data.transformer.list.ListTMonadPlus;
import org.highj.data.transformer.list.ListTMonadRec;
import org.highj.data.transformer.list.ListTMonadTrans;
import org.highj.data.transformer.list.ListTMonadZero;
import org.highj.data.transformer.list.ListTPlus;
import org.highj.data.transformer.list.ListTUnfoldable;
import org.highj.data.transformer.list.ListTZipApplicative;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.alternative.Alt;
import org.highj.typeclass1.alternative.Alternative;
import org.highj.typeclass1.alternative.Plus;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadPlus;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.typeclass1.monad.MonadTrans;
import org.highj.typeclass1.monad.MonadZero;
import org.highj.typeclass1.unfoldable.Unfoldable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.highj.Hkt.asT1;

/**
 * The ListT monad transformer, which allows to add indeterminism to a
 * monad transformer stack.
 * <p>
 * Based on the ListT implementation of purescript, see
 * purescript-transformers/src/Control/Monad/List/Trans.purs
 *
 * @param <M> nested monadic type
 * @param <A> element type
 */
public class ListT<M, A> implements __2<ListT.µ, M, A> {

    public interface µ {
    }

    private final __<M, Step<M, A>> step;

    ListT(__<M, Step<M, A>> step) {
        this.step = step;
    }

    /**
     * A calculation step of {@link ListT}.
     *
     * @param <M> nested monadic type
     * @param <A> element type
     */
    public interface Step<M, A> {
        <B> B map(Function<Yield<M, A>, B> yieldFn, Function<Skip<M, A>, B> skipFn, Supplier<B> doneSupplier);
    }

    /**
     * A calculation step resulting in a list head and tail.
     *
     * @param <M> nested monadic type
     * @param <A> element type
     */
    public static class Yield<M, A> implements Step<M, A> {
        private final A head;
        private final Supplier<ListT<M, A>> tail;

        private Yield(A head, Supplier<ListT<M, A>> tail) {
            this.head = head;
            this.tail = tail;
        }

        public A head() {
            return head;
        }

        public Supplier<ListT<M, A>> tail() {
            return tail;
        }

        public <T> Supplier<T> mapTail(Function<ListT<M, A>, T> fn) {
            return () -> fn.apply(tail.get());
        }

        @Override
        public <B> B map(Function<Yield<M, A>, B> yieldFn, Function<Skip<M, A>, B> skipFn, Supplier<B> doneSupplier) {
            return yieldFn.apply(this);
        }
    }

    /**
     * A calculation step resulting in a new list tail, without a head.
     *
     * @param <M> nested monadic type
     * @param <A> element type
     */
    public static class Skip<M, A> implements Step<M, A> {

        private final Supplier<ListT<M, A>> tail;

        private Skip(Supplier<ListT<M, A>> tail) {
            this.tail = tail;
        }

        public Supplier<ListT<M, A>> tail() {
            return tail;
        }

        public <T> Supplier<T> mapTail(Function<ListT<M, A>, T> fn) {
            return () -> fn.apply(tail.get());
        }

        @Override
        public <B> B map(Function<Yield<M, A>, B> yieldFn, Function<Skip<M, A>, B> skipFn, Supplier<B> doneSupplier) {
            return skipFn.apply(this);
        }
    }

    /**
     * A calculation step representing the end of the list.
     *
     * @param <M> nested monadic type
     * @param <A> element type
     */
    public static class Done<M, A> implements Step<M, A> {

        private Done() {
        }

        @Override
        public <B> B map(Function<Yield<M, A>, B> yieldFn, Function<Skip<M, A>, B> skipFn, Supplier<B> doneSupplier) {
            return doneSupplier.get();
        }
    }

    /**
     * Constructs a {@link Yield} step.
     *
     * @param a        head value
     * @param supplier tail supplier
     * @param <M>      nested monadic type
     * @param <A>      element type
     * @return the {@link Yield} step
     */
    public static <M, A> Yield<M, A> yield(A a, Supplier<ListT<M, A>> supplier) {
        return new Yield<>(a, supplier);
    }

    /**
     * Constructs a {@link Skip} step.
     *
     * @param supplier tail supplier
     * @param <M>      nested monadic value
     * @param <A>      element type
     * @return the {@link Skip} step
     */
    public static <M, A> Skip<M, A> skip(Supplier<ListT<M, A>> supplier) {
        return new Skip<>(supplier);
    }

    /**
     * Constructs a {@link Done} step.
     *
     * @param <M> nested monadic type
     * @param <A> element type
     * @return the {@link Done} step
     */
    public static <M, A> Done<A, M> done() {
        return new Done<>();
    }

    /**
     * Exposes the underlying step of the list.
     *
     * @return the step.
     */
    public __<M, Step<M, A>> run() {
        return step;
    }

    /**
     * Constructs an empty list.
     *
     * @param applicative {@link Applicative} instance
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return an empty list
     */
    public static <M, A> ListT<M, A> nil(Applicative<M> applicative) {
        return new ListT<>(applicative.pure(done()));
    }

    /**
     * Constructs a list in a lazy fashion from head and tail
     *
     * @param applicative the {@link Applicative} instance
     * @param supHead     supplier for the head
     * @param supTail     supplier for the tail
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the result list
     */
    public static <M, A> ListT<M, A> cons(Applicative<M> applicative, Supplier<A> supHead, Supplier<ListT<M, A>> supTail) {
        return new ListT<>(applicative.pure(yield(supHead.get(), supTail)));
    }

    /**
     * Construct a list from a head value and a tail {@link Supplier}.
     *
     * @param applicative the {@link Applicative} instance
     * @param head        the head value
     * @param lh          supplier for the tail
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the result list
     */
    public static <M, A> ListT<M, A> prepend_(Applicative<M> applicative, A head, Supplier<ListT<M, A>> lh) {
        return new ListT<>(applicative.pure(yield(head, lh)));
    }

    /**
     * Constructs a list from the current one by prepending a head value.
     *
     * @param applicative the {@link Applicative} instance
     * @param head        the head value
     * @return the prepended list
     */
    public ListT<M, A> prepend(Applicative<M> applicative, A head) {
        return new ListT<>(applicative.pure(yield(head, () -> this)));
    }

    /**
     * Constructs a list by mapping over the underlying step.
     *
     * @param functor the {@link Functor} instance
     * @param fn      the mapping function
     * @param <B>     the new element type
     * @return the mapped list
     */
    public <B> ListT<M, B> stepMap(Functor<M> functor,
                                   Function<Step<M, A>, Step<M, B>> fn) {
        return new ListT<>(functor.map(fn, step));
    }

    /**
     * Constructs a list by mapping over the underlying step by using mapping functions
     * specialized by step type.
     *
     * @param functor      the {@link Functor} instance
     * @param yieldFn      function for mapping a {@link Yield} step
     * @param skipFn       function for mapping a {@link Skip} step
     * @param doneSupplier supplier for mapping a {@link Done} step
     * @param <B>          the new element type
     * @return the mapped list
     */
    public <B> ListT<M, B> stepMap(
            Functor<M> functor,
            Function<Yield<M, A>, Step<M, B>> yieldFn,
            Function<Skip<M, A>, Step<M, B>> skipFn,
            Supplier<Step<M, B>> doneSupplier) {
        return stepMap(functor, step -> step.map(yieldFn, skipFn, doneSupplier));
    }

    /**
     * Appends two lists.
     *
     * @param applicative the {@link Applicative} instance
     * @param first       the first list
     * @param second      the second list
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the concatenated list
     */
    public static <M, A> ListT<M, A> concat(Applicative<M> applicative,
                                            ListT<M, A> first, ListT<M, A> second) {
        return first.stepMap(applicative,
                stepYield -> yield(stepYield.head, stepYield.mapTail(s -> concat(applicative, s, second))),
                stepSkip -> skip(stepSkip.mapTail(s -> concat(applicative, s, second))),
                () -> skip(() -> second));
    }

    /**
     * Apppends a list to the current one.
     *
     * @param applicative the {@link Applicative} instance
     * @param that        the list to append
     * @return the concatenated list
     */
    public ListT<M, A> append(Applicative<M> applicative, ListT<M, A> that) {
        return concat(applicative, this, that);
    }

    /**
     * Constructs a list holding a single element.
     *
     * @param applicative the {@link Applicative} instance
     * @param a           the element value
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the result list
     */
    public static <M, A> ListT<M, A> singleton(Applicative<M> applicative, A a) {
        return ListT.<M, A> nil(applicative).prepend(applicative, a);
    }

    /**
     * Constructs a list from a monadic value.
     *
     * @param applicative the {@link Applicative} instance
     * @param ma          the monadic value
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the result list
     */
    public static <M, A> ListT<M, A> fromEffect(Applicative<M> applicative, __<M, A> ma) {
        return new ListT<>(applicative.map(a -> yield(a, () -> nil(applicative)), ma));
    }

    /**
     * Constructs a list from a monadic list value.
     *
     * @param functor the {@link Functor} instance
     * @param v       the monadic list value
     * @param <M>     nested monadic type
     * @param <A>     element type
     * @return the result list
     */
    public static <M, A> ListT<M, A> wrapEffect(Functor<M> functor, __<M, ListT<M, A>> v) {
        return new ListT<>(functor.map(listT -> skip(() -> listT), v));
    }

    /**
     * Constructs a list from a {@link Supplier} of a list without forcing evaluation.
     *
     * @param applicative the {@link Applicative} instance
     * @param supplier    the list supplier
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the result list
     */
    public static <M, A> ListT<M, A> wrapLazy(Applicative<M> applicative, Supplier<ListT<M, A>> supplier) {
        return new ListT<>(applicative.pure(skip(supplier)));
    }

    /**
     * Constructs a list by iterating over a function.
     * The function returns a {@link T2} containing the iteration value and the value to be stored in the list,
     * wrapped in a {@link Maybe} to signalize the end of the list (by providing a Nothing),
     * wrapped as monadic value.
     *
     * @param functor the {@link Functor} instance
     * @param fn      the iteration function
     * @param z       the start value
     * @param <M>     nested monadic type
     * @param <A>     element type
     * @param <Z>     iterating type
     * @return the result list
     */
    public static <M, A, Z> ListT<M, A> unfold(Functor<M> functor,
                                               Function<Z, __<M, Maybe<T2<Z, A>>>> fn, Z z) {
        return new ListT<>(functor.map(maybe ->
                        maybe.<Step<M, A>> map(
                                (T2<Z, A> t2) -> yield(t2._2(), () -> unfold(functor, fn, t2._1())))
                                .getOrElse(() -> ListT.done()),
                fn.apply(z)));
    }

    /**
     * Constructs an infinite list by iterating over a function.
     *
     * @param applicative the {@link Applicative} instance
     * @param fn          the iterating function
     * @param a           the start value
     * @param <M>         nested monadic value
     * @param <A>         element and iterating type
     * @return the result list
     */
    public static <M, A> ListT<M, A> iterate(Applicative<M> applicative,
                                             Function<A, A> fn, A a) {
        return unfold(applicative, v -> applicative.pure(Maybe.Just(T2.of(fn.apply(v), v))), a);
    }

    /**
     * Constructs an infinite list repeating a single value.
     *
     * @param applicative the {@link Applicative} instance
     * @param a           the repeating element
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the result list
     */
    public static <M, A> ListT<M, A> repeat(Applicative<M> applicative, A a) {
        return iterate(applicative, Function.identity(), a);
    }

    /**
     * Returns a prefix of the current list with the given length, or the whole list if it is smaller.
     *
     * @param applicative the {@link Applicative} instance
     * @param n           requested length of the list prefix
     * @return the result list
     */
    public ListT<M, A> take(Applicative<M> applicative, int n) {
        return (n <= 0)
                ? nil(applicative)
                : stepMap(applicative,
                stepYield -> yield(stepYield.head, stepYield.mapTail(s -> s.take(applicative, n - 1))),
                stepSkip -> skip(stepSkip.mapTail(s -> s.take(applicative, n))),
                () -> ListT.done());
    }

    /**
     * Returns a prefix of the current list containing all subsequent elements where the condition holds.
     *
     * @param applicative the {@link Applicative} instance
     * @param predicate   the test condition
     * @return the result list
     */
    public ListT<M, A> takeWhile(Applicative<M> applicative, Predicate<A> predicate) {
        return stepMap(applicative,
                stepYield -> predicate.test(stepYield.head)
                        ? yield(stepYield.head, stepYield.mapTail(s -> s.takeWhile(applicative, predicate)))
                        : done(),
                stepSkip -> skip(stepSkip.mapTail(s -> s.takeWhile(applicative, predicate))),
                () -> ListT.done());
    }

    /**
     * Returns the remaining list after removing a prefix of the given length from the current list.
     * If the list is shorter than the requested number of elements to drop, an empty list is returned.
     *
     * @param applicative the {@link Applicative} instance
     * @param n           number of leading elements to drop
     * @return the result list
     */
    public ListT<M, A> drop(Applicative<M> applicative, int n) {
        return (n <= 0)
                ? this
                : stepMap(applicative,
                stepYield -> skip(stepYield.mapTail(s -> s.drop(applicative, n - 1))),
                stepSkip -> skip(stepSkip.mapTail(s -> s.drop(applicative, n))),
                () -> ListT.done());
    }

    /**
     * Drops all subsequent elements of the current list fulfilling the given condition.
     *
     * @param applicative the {@link Applicative} instance
     * @param predicate   the test condition
     * @return the result list
     */
    public ListT<M, A> dropWhile(Applicative<M> applicative, Predicate<A> predicate) {
        return stepMap(applicative,
                stepYield -> predicate.test(stepYield.head)
                        ? skip(stepYield.mapTail(s -> s.dropWhile(applicative, predicate)))
                        : stepYield,
                stepSkip -> skip(stepSkip.mapTail(s -> s.dropWhile(applicative, predicate))),
                () -> ListT.done());
    }

    /**
     * Constructs a list of all elements of the current list fulfilling the given condition.
     *
     * @param applicative the {@link Applicative} instance
     * @param predicate   the test condition
     * @return the filtered list
     */
    public ListT<M, A> filter(Applicative<M> applicative, Predicate<A> predicate) {
        return stepMap(applicative,
                stepYield -> predicate.test(stepYield.head)
                        ? yield(stepYield.head, stepYield.mapTail(s -> s.filter(applicative, predicate)))
                        : skip(stepYield.mapTail(s -> s.filter(applicative, predicate))),
                stepSkip -> skip(stepSkip.mapTail(s -> s.filter(applicative, predicate))),
                () -> ListT.done());
    }

    /**
     * Maps the elements of the current list to {@link Maybe} values, and keeps only the values wrapped in Just.
     *
     * @param functor the {@link Functor} instance
     * @param fn      the mapping function
     * @param <B>     new element type
     * @return the mapped list
     */
    public <B> ListT<M, B> mapMaybe(Functor<M> functor, Function<A, Maybe<B>> fn) {
        return stepMap(functor,
                stepYield -> {
                    Supplier<ListT<M, B>> mbSup = stepYield.mapTail(s -> s.mapMaybe(functor, fn));
                    return fn.apply(stepYield.head).cata$(() -> skip(mbSup), b -> yield(b, mbSup));
                },
                stepSkip -> skip(stepSkip.mapTail(s -> s.mapMaybe(functor, fn))),
                () -> ListT.done());
    }

    /**
     * Extracts the values wrapped in Just from a list of {@link Maybe} values.
     *
     * @param functor the {@link Functor} instance
     * @param maybes  list of {@link Maybe} values
     * @param <M>     nested monadic type
     * @param <A>     element type
     * @return the result list
     */
    public static <M, A> ListT<M, A> catMaybes(Functor<M> functor, ListT<M, Maybe<A>> maybes) {
        return maybes.mapMaybe(functor, Function.identity());
    }

    /**
     * Separates the current list in head and tail, if it is non-empty.
     * If the monadic return value contains Nothing, the list is empty.
     * Otherwise it wraps a {@link T2} containing head and tail of the list.
     *
     * @param monad the {@link Monad} instance
     * @return maybe head and tail of the list
     */
    public __<M, Maybe<T2<A, ListT<M, A>>>> uncons(Monad<M> monad) {
        return monad.bind(step, s -> s.map(
                stepYield -> monad.pure(Maybe.Just(T2.of(stepYield.head, stepYield.tail.get()))),
                stepSkip -> stepSkip.tail.get().uncons(monad),
                () -> monad.pure(Maybe.Nothing())));
    }

    /**
     * Extracts the head of the current list, if it is non-empty.
     * If the monadic return value contains Nothing, the list is empty,
     * else it wraps the head of the list.
     *
     * @param monad the {@link Monad} instance
     * @return maybe the head
     */
    public __<M, Maybe<A>> head(Monad<M> monad) {
        return monad.map(maybe -> maybe.map(T2::_1), uncons(monad));
    }

    /**
     * Extracts the tail of the current list, if it is non-empty.
     * If the monadic return value contains Nothing, the list is empty,
     * else it wraps the tail of the list.
     *
     * @param monad the {@link Monad} instance
     * @return maybe the tail
     */
    public __<M, Maybe<ListT<M, A>>> tail(Monad<M> monad) {
        return monad.map(maybe -> maybe.map(T2::_2), uncons(monad));
    }

    /**
     * Left-folds the list with a function returning a monadic value.
     *
     * @param monad the {@link Monad} instance
     * @param fn    folding function
     * @param b     start value
     * @param <B>   result type
     * @return the monadic result value
     */
    public <B> __<M, B> foldl_(Monad<M> monad, BiFunction<B, A, __<M, B>> fn, B b) {
        return monad.bind(uncons(monad),
                g -> g.map(t2 -> monad.bind(fn.apply(b, t2._1()), b_ -> t2._2().foldl_(monad, fn, b_)))
                        .getOrElse(() -> monad.pure(b)));
    }

    /**
     * Left-folds the list.
     *
     * @param monad the {@link Monad} instance
     * @param fn    folding function
     * @param b     start value
     * @param <B>   start value
     * @return the monadic result value
     */
    public <B> __<M, B> foldl(Monad<M> monad, BiFunction<B, A, B> fn, B b) {
        return monad.bind(uncons(monad),
                g -> g.map(t2 -> t2._2().foldl(monad, fn, fn.apply(b, t2._1()))).getOrElse(() -> monad.pure(b)));
    }

    /**
     * Left-folds the list, but collects all intermediate results in the process.
     *
     * @param monad the {@link Monad} instance
     * @param fn    folding function
     * @param b     start value
     * @param <B>   type of result list
     * @return the result list
     */
    public <B> ListT<M, B> scanl(Monad<M> monad, BiFunction<B, A, B> fn, B b) {
        Function<T2<B, ListT<M, A>>, __<M, Maybe<T2<T2<B, ListT<M, A>>, B>>>> g = t2 ->
                monad.map(h -> h.map(
                        stepYield -> {
                            B b__ = fn.apply(t2._1(), stepYield.head);
                            return Maybe.Just(T2.of(T2.of(b__, stepYield.tail.get()), b__));
                        },
                        stepSkip -> Maybe.Just(T2.of(T2.of(t2._1(), stepSkip.tail.get()), t2._1())),
                        Maybe::Nothing
                ), t2._2().step);
        return unfold(monad, g, T2.of(b, this));
    }

    /**
     * Zips two lists together with a function returning a monadic result.
     * The result list has the size of the shorter one of the input lists.
     *
     * @param monad the {@link Monad} instance
     * @param fn    the zip function
     * @param ma    the first list
     * @param mb    the second list
     * @param <M>   nested monadic type
     * @param <A>   element type of the first list
     * @param <B>   element type of the second list
     * @param <C>   element type of the result list
     * @return the zipped list.
     */
    public static <M, A, B, C> ListT<M, C> zipWith_(Monad<M> monad, BiFunction<A, B, __<M, C>> fn, ListT<M, A> ma, ListT<M, B> mb) {
        __<M, Maybe<T2<A, ListT<M, A>>>> ua = ma.uncons(monad);
        __<M, Maybe<T2<B, ListT<M, B>>>> ub = mb.uncons(monad);
        __<M, ListT<M, C>> uc = monad.bind(ua, va -> monad.bind(ub, vb -> va.isNothing() || vb.isNothing()
                ? monad.pure(nil(monad))
                : monad.map(a -> prepend_(monad, a, () -> zipWith_(monad, fn, va.get()._2(), vb.get()._2())),
                fn.apply(va.get()._1(), vb.get()._1()))));
        return wrapEffect(monad, uc);
    }

    /**
     * Zips two lists together with the given function.
     * The result list has the size of the shorter one of the input lists.
     *
     * @param monad the {@link Monad} instance
     * @param fn    the zip function
     * @param ma    the first list
     * @param mb    the second list
     * @param <M>   nested monadic type
     * @param <A>   element type of the first list
     * @param <B>   element type of the second list
     * @param <C>   element type of the result list
     * @return the zipped list.
     */
    public static <M, A, B, C> ListT<M, C> zipWith(Monad<M> monad, BiFunction<A, B, C> fn, ListT<M, A> ma, ListT<M, B> mb) {
        return zipWith_(monad, fn.andThen(monad::<C>pure), ma, mb);
    }


    /**
     * Converts a {@link ListT} based on the identity monad into a Java {@link Iterator}.
     *
     * @param listT the {@link ListT} instance
     * @param <E> the element type inside the identity monad
     * @return an iterator that will produce the same elements as the {@link ListT} instance
     */
    public static <E> Iterator<E> toIterator(ListT<T1.µ, E> listT) {
        return new Iterator<E>() {

            private Step<T1.µ, E> state = asT1(listT.run())._1();

            @Override
            public boolean hasNext() {
                while(state instanceof Skip) {
                    state = asT1(((Skip<T1.µ, E>)state).tail().get().run())._1();
                }
                return state instanceof Yield;
            }

            @Override
            public E next() {
                if (! hasNext()) {
                    throw new NoSuchElementException();
                }
                Yield<T1.µ, E> yield = (Yield<T1.µ, E>) state;
                state = asT1(yield.tail.get().run())._1();
                return yield.head;
            }
        };
    }

    /**
     * The {@link Semigroup} instance of {@link ListT}.
     *
     * @param applicative the {@link Applicative} instance
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the semigroup instance
     */
    public static <M, A> Semigroup<ListT<M, A>> semigroup(Applicative<M> applicative) {
        return (x, y) -> concat(applicative, x, y);
    }

    /**
     * The {@link Monoid} instance of {@link ListT}.
     *
     * @param applicative the {@link Applicative} instance
     * @param <M>         nested monadic type
     * @param <A>         element type
     * @return the monoid instance
     */
    public static <M, A> Monoid<ListT<M, A>> monoid(Applicative<M> applicative) {
        return Monoid.create(nil(applicative), (x, y) -> concat(applicative, x, y));
    }

    /**
     * The {@link Functor} instance of {@link ListT}.
     *
     * @param mFunctor the {@link Functor} instance
     * @param <M>      nested monadic type
     * @return the functor instance
     */
    public static <M> ListTFunctor<M> functor(Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    /**
     * The {@link Unfoldable} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the unfoldable instance
     */
    public static <M> ListTUnfoldable<M> unfoldable(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The {@link Apply} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the apply instance
     */
    public static <M> ListTApply<M> apply(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The standard {@link Applicative} instance of {@link ListT}, using the cross-product over lists.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the standard applicative instance
     */
    public static <M> ListTApplicative<M> applicative(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The list-zipping {@link Applicative} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the zip-applicative instance
     */
    public static <M> ListTZipApplicative<M> zipApplicative(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The {@link Bind} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the bind instance
     */
    public static <M> ListTBind<M> bind(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The {@link Monad} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the monad instance
     */
    public static <M> ListTMonad<M> monad(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The {@link MonadTrans} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the monadTrans instance
     */
    public static <M> ListTMonadTrans<M> monadTrans(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The {@link Alt} instance of {@link ListT}.
     *
     * @param mApplicative the {@link Applicative} instance
     * @param <M>          nested monadic type
     * @return the alt instance
     */
    public static <M> ListTAlt<M> alt(Applicative<M> mApplicative) {
        return () -> mApplicative;
    }

    /**
     * The {@link Plus} instance of {@link ListT}.
     *
     * @param mApplicative the {@link Applicative} instance
     * @param <M>          nested monadic type
     * @return the plus instance
     */
    public static <M> ListTPlus<M> plus(Applicative<M> mApplicative) {
        return () -> mApplicative;
    }

    /**
     * The {@link Alternative} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the alternative instance
     */
    public static <M> ListTAlternative<M> alternative(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The {@link MonadZero} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the monadZero instance
     */
    public static <M> ListTMonadZero<M> monadZero(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The {@link MonadPlus} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the monadPlus instance
     */
    public static <M> ListTMonadPlus<M> monadPlus(Monad<M> mMonad) {
        return () -> mMonad;
    }

    /**
     * The {@link MonadRec} instance of {@link ListT}.
     *
     * @param mMonad the {@link Monad} instance
     * @param <M>    nested monadic type
     * @return the monadRec instance
     */
    public static <M> ListTMonadRec<M> monadRec(Monad<M> mMonad) {
        return () -> mMonad;
    }
}
