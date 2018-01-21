package org.highj.data;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.instance.these.*;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass2.bifoldable.Bifoldable;
import org.highj.typeclass2.bifoldable.Bitraversable;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The These type represents values with two non-exclusive possibilities.
 * The type constructors are This (only the first value), That (only the second value) and
 * Both (both values).
 *
 * @param <A> the first element type
 * @param <B> the second element type
 */
public abstract class These<A, B> implements __2<These.µ, A, B> {
    private These() {
    }

    public interface µ {
    }

    /**
     * The catamorphism of {@link These}.
     *
     * @param <C> the result type
     * @return the result
     */
    public abstract <C> C these(Function<A, C> thisFn, Function<B, C> thatFn, BiFunction<A, B, C> bothFn);

    /**
     * Tests if the {@link These} is a This.
     *
     * @return test result
     */
    public boolean isThis() {
        return these(a -> true, b -> false, (a, b) -> false);
    }

    /**
     * Tests if the {@link These} is a That.
     *
     * @return test result
     */
    public boolean isThat() {
        return these(a -> false, b -> true, (a, b) -> false);
    }

    /**
     * Test if the {@link These} is a Both.
     *
     * @return test result
     */
    public boolean isBoth() {
        return these(a -> false, b -> false, (a, b) -> true);
    }

    /**
     * Test if the first element is present.
     *
     * @return test result
     */
    public boolean hasFirst() {
        return these(a -> true, b -> false, (a, b) -> true);
    }

    /**
     * Test if the second element is present.
     *
     * @return test result
     */
    public boolean hasSecond() {
        return these(a -> false, b -> true, (a, b) -> true);
    }

    /**
     * Constructs a This instance.
     *
     * @param a   the value
     * @param <A> the first element type
     * @param <B> the second element type
     * @return the This instance
     * @throws NullPointerException if the argument is null
     */
    public static <A, B> These<A, B> This(A a) throws NullPointerException {
        Objects.requireNonNull(a);
        return new These<A, B>() {
            @Override
            public <C> C these(Function<A, C> thisFn, Function<B, C> thatFn, BiFunction<A, B, C> bothFn) {
                return thisFn.apply(a);
            }
        };
    }

    /**
     * Constructs a That instance.
     *
     * @param b   the value
     * @param <A> the first element type
     * @param <B> the second element type
     * @return the That instance
     * @throws NullPointerException if the argument is null
     */
    public static <A, B> These<A, B> That(B b) throws NullPointerException {
        Objects.requireNonNull(b);
        return new These<A, B>() {
            @Override
            public <C> C these(Function<A, C> thisFn, Function<B, C> thatFn, BiFunction<A, B, C> bothFn) {
                return thatFn.apply(b);
            }
        };
    }

    /**
     * Constructs a Both instance.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <A> this first element type
     * @param <B> this second element type
     * @return the Both instance
     * @throws NullPointerException if any of the arguments is null
     */
    public static <A, B> These<A, B> Both(A a, B b) throws NullPointerException {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        return new These<A, B>() {
            @Override
            public <C> C these(Function<A, C> thisFn, Function<B, C> thatFn, BiFunction<A, B, C> bothFn) {
                return bothFn.apply(a, b);
            }
        };
    }

    /**
     * Takes two default values and produces a tuple.
     *
     * @param defaultA default value for A
     * @param defaultB default value for B
     * @return the tuple
     */
    public T2<A, B> fromThese(A defaultA, B defaultB) {
        return these(a -> T2.of(a, defaultB),
                b -> T2.of(defaultA, b),
                T2::of);
    }

    /**
     * Coalesce with the provided operation.
     *
     * @param these   the These instance
     * @param mergeFn the merge function (if it is a Both)
     * @param <A>     the result type
     * @return the result
     */
    public static <A> A mergeThese(These<A, A> these, BiFunction<A, A, A> mergeFn) {
        return these.these(a -> a, b -> b, mergeFn);
    }

    /**
     * Bimaps and coalesces results with the provided operation.
     *
     * @param aFn     the first map function
     * @param bFn     the second map function
     * @param mergeFn the merge function (if it is a Both)
     * @param <C>     the result type
     * @return the result
     */
    public <C> C mergeTheseWith(Function<A, C> aFn, Function<B, C> bFn, BiFunction<C, C, C> mergeFn) {
        return mergeThese(mapThese(aFn, bFn), mergeFn);
    }

    /**
     * Extracts a value from a This.
     *
     * @return {@link Maybe} the value
     */
    public Maybe<A> justThis() {
        return these(Maybe::Just,
                b -> Maybe.<A>Nothing(),
                (a, b) -> Maybe.<A>Nothing());
    }

    /**
     * Extracts a value from a That.
     *
     * @return {@link Maybe} the value
     */
    public Maybe<B> justThat() {
        return these(a -> Maybe.<B>Nothing(),
                Maybe::Just,
                (a, b) -> Maybe.<B>Nothing());
    }

    /**
     * Extracts the values from a Both.
     *
     * @return {@link Maybe} the tuple of values
     */
    public Maybe<T2<A, B>> justBoth() {
        return these(b -> Maybe.<T2<A, B>>Nothing(),
                b -> Maybe.<T2<A, B>>Nothing(),
                (a, b) -> Maybe.Just(T2.of(a, b)));
    }

    /**
     * Extracts the value from This or the first value from Both.
     *
     * @return {@link Maybe} the value
     */
    public Maybe<A> justFirst() {
        return these(Maybe::Just,
                b -> Maybe.Nothing(),
                (a, b) -> Maybe.Just(a));
    }

    /**
     * Extracts the value from That or the second value from Both.
     *
     * @return {@link Maybe} the value
     */
    public Maybe<B> justSecond() {
        return these(a -> Maybe.Nothing(),
                Maybe::Just,
                (a, b) -> Maybe.Just(b));
    }


    /**
     * Bimaps over the instance.
     *
     * @param aFn  the first map function
     * @param bFn  the second map function
     * @param <A1> the new first element type
     * @param <B1> the new second element type
     * @return the result of the mapping
     */
    public <A1, B1> These<A1, B1> mapThese(Function<A, A1> aFn, Function<B, B1> bFn) {
        return these(a -> This(aFn.apply(a)),
                b -> That(bFn.apply(b)),
                (a, b) -> Both(aFn.apply(a), bFn.apply(b)));
    }

    /**
     * Map over the first value.
     *
     * @param aFn  the first map function
     * @param <A1> the new first element type
     * @return the result of the mapping
     */
    public <A1> These<A1, B> mapThis(Function<A, A1> aFn) {
        return these(a -> This(aFn.apply(a)),
                These::That,
                (a, b) -> Both(aFn.apply(a), b));
    }

    /**
     * Map over the second value.
     *
     * @param bFn  the second map function
     * @param <B1> the new second element type
     * @return the result of the mapping
     */
    public <B1> These<A, B1> mapThat(Function<B, B1> bFn) {
        return these(These::This,
                b -> That(bFn.apply(b)),
                (a, b) -> Both(a, bFn.apply(b)));
    }

    /**
     * Reverses the order of the values.
     *
     * @return the flipped {@link These} instance
     */
    public These<B, A> flip() {
        return these(These::That, These::This, (a, b) -> Both(b, a));
    }

    /**
     * Wrapping the instance in an {@link Applicative} by transforming the first value.
     *
     * @param applicative an {@link Applicative}
     * @param fn          the transformation function
     * @param <F>         the type of the {@link Applicative}
     * @param <A1>        the new first element type
     * @return the transformed instance wrapped in the given {@link Applicative}
     */
    public <F, A1> __<F, These<A1, B>> here(Applicative<F> applicative, Function<A, __<F, A1>> fn) {
        return these(a -> applicative.map(These::This, fn.apply(a)),
                b -> applicative.pure(These.That(b)),
                (a, b) -> applicative.map(a1 -> Both(a1, b), fn.apply(a)));
    }

    /**
     * Wrapping the instance in an {@link Applicative} by transforming the second value.
     *
     * @param applicative an {@link Applicative}
     * @param fn          the transformation function
     * @param <F>         the type of the {@link Applicative}
     * @param <B1>        the new second element type
     * @return the transformed instance wrapped in the given {@link Applicative}
     */
    public <F, B1> __<F, These<A, B1>> there(Applicative<F> applicative, Function<B, __<F, B1>> fn) {
        return these(a -> applicative.pure(These.This(a)),
                b -> applicative.map(These::That, fn.apply(b)),
                (a, b) -> applicative.map(b1 -> Both(a, b1), fn.apply(b)));
    }

    /**
     * Extracting the This values from a {@link List} of {@link These}.
     *
     * @param list the list
     * @param <A>  the first element type
     * @param <B>  the second element type
     * @return list of the values of This
     */
    public static <A, B> List<A> catThis(List<These<A, B>> list) {
        return Maybe.justs(list.map(These::justThis));
    }

    /**
     * Extracting the That values from a {@link List} of {@link These}.
     *
     * @param list the list
     * @param <A>  the first element type
     * @param <B>  the second element type
     * @return list of the values of That
     */
    public static <A,B> List<B> catThat(List<These<A, B>> list) {
        return Maybe.justs(list.map(These::justThat));
    }

    /**
     * Extracting both values of Both from a {@link List} of {@link These}.
     *
     * @param list the list
     * @param <A>  the first element type
     * @param <B>  the second element type
     * @return list of the tupled values of Both
     */
    public static <A, B> List<T2<A, B>> catBoth(List<These<A, B>> list) {
        return Maybe.justs(list.map(These::justBoth));
    }

    /**
     * Extracting the first values from a {@link List} of {@link These}.
     *
     * @param list the list
     * @param <A>  the first element type
     * @param <B>  the second element type
     * @return list of the values of This and the first values of Both
     */
    public static <A, B> List<A> catFirst(List<These<A, B>> list) {
        return Maybe.justs(list.map(These::justFirst));
    }

    /**
     * Extracting the second values from a {@link List} of {@link These}.
     *
     * @param list the list
     * @param <A>  the first element type
     * @param <B>  the second element type
     * @return list of the values of That and the second values of Both
     */
    public static <A, B> List<B> catSecond(List<These<A, B>> list) {
        return Maybe.justs(list.map(These::justSecond));
    }

    @Override
    public String toString() {
        return these(
                a -> "This(" + a + ")",
                b -> "That(" + b + ")",
                (a, b) -> "Both(" + a + "," + b + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof These)) return false;
        These<?, ?> second = (These<?, ?>) o;
        return these(
                a1 -> second.these(a1::equals, b2 -> false, (a2, b2) -> false),
                b1 -> second.these(a2 -> false, b1::equals, (a2, b2) -> false),
                (a1, b1) -> second.these(a2 -> false, b2 -> false, (a2, b2) -> a1.equals(a2) && b1.equals(b2)));
    }

    @Override
    public int hashCode() {
        return these(
                a -> 37 + a.hashCode(),
                b -> 41 + b.hashCode(),
                (a, b) -> 43 * a.hashCode() + 47 * b.hashCode());
    }

    /**
     * The {@link Semigroup} instance
     *
     * @param semigroupA the semigroup of the first element type
     * @param semigroupB the semigroup of the second element type
     * @param <A>        the first element type
     * @param <B>        the second element type
     * @return the instance
     */
    public static <A, B> Semigroup<These<A, B>> semigroup(Semigroup<A> semigroupA, Semigroup<B> semigroupB) {
        return (t1, t2) ->
                       t1.these(
                               a1 -> t2.these(
                                       a2 -> This(semigroupA.apply(a1, a2)),
                                       b2 -> Both(a1, b2),
                                       (a2, b2) -> Both(semigroupA.apply(a1, a2), b2)),
                               b1 -> t2.these(
                                       a2 -> Both(a2, b1),
                                       b2 -> That(semigroupB.apply(b1, b2)),
                                       (a2, b2) -> Both(a2, semigroupB.apply(b1, b2))),
                               (a1, b1) -> t2.these(
                                       a2 -> Both(semigroupA.apply(a1, a2), b1),
                                       b2 -> Both(a1, semigroupB.apply(b1, b2)),
                                       (a2, b2) -> Both(semigroupA.apply(a1, a2), semigroupB.apply(b1, b2)))
                       );
    }

    /**
     * The {@link Functor} instance.
     *
     * @param <F> the fixed first element type
     * @return the instance
     */
    public static <F> TheseFunctor<F> functor() {
        return new TheseFunctor<F>() {
        };
    }

    /**
     * The {@link Applicative} instance.
     *
     * @param semigroup the semigroup of the first element
     * @param <F>       the fixed first element type
     * @return the instance
     */
    public static <F> TheseApplicative<F> applicative(Semigroup<F> semigroup) {
        return () -> semigroup;
    }

    /**
     * The {@link Monad} instance.
     *
     * @param semigroup the semigroup of the first element
     * @param <F>       the fixed first element type
     * @return the instance
     */
    public static <F> TheseMonad<F> monad(Semigroup<F> semigroup) {
        return () -> semigroup;
    }

    /**
     * The {@link Bifunctor} instance.
     */
    public final TheseBifunctor bifunctor = new TheseBifunctor() {
    };

    /**
     * The {@link Foldable} instance.
     *
     * @param <F> the fixed first element type
     * @return the instance
     */
    public static <F> TheseFoldable<F> foldable() {
        return new TheseFoldable<F>() {
        };
    }

    /**
     * The {@link Traversable} instance.
     *
     * @param <F> the fixed first element type
     * @return the instance
     */
    public static <F> TheseTraversable<F> traversable() {
        return new TheseTraversable<F>() {
        };
    }

    /**
     * The {@link Bifoldable} instance.
     */
    public static final TheseBifoldable bifoldable = new TheseBifoldable() {
    };

    /**
     * The {@link Bitraversable} instance.
     */
    public static final TheseBitraversable bitraversable = new TheseBitraversable() {
    };
}
