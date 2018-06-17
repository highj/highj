package org.highj.typeclass1.foldable;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.function.Functions;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The combined Foldable / Foldable1 type class.
 * <p>
 * Minimal complete definition: 'foldr' OR 'foldMap'.
 */
public interface Foldable<F> {

    default <A> A fold(Monoid<A> ma, __<F, A> nestedA) {
        return foldMap(ma, Function.<A>identity(), nestedA);
    }

    default <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, __<F, A> nestedA) {
        return foldr(a -> b -> mb.apply(fn.apply(a), b), mb.identity(), nestedA);
    }

    default <A, B> B foldr(Function<A, Function<B, B>> fn, B b, __<F, A> as) {
        return foldMap(Functions.endoMonoid(), fn, as).apply(b);
    }

    default <A, B> B foldr(BiFunction<A, B, B> fn, B b, __<F, A> as) {
        return foldr(x -> y -> fn.apply(x, y), b, as);
    }

    //This is very inefficient, please override if possible.
    default <A, B> A foldl(Function<A, Function<B, A>> fn, A a, __<F, B> bs) {
        return foldr(b -> h -> x -> h.apply(fn.apply(x).apply(b)), Function.<A>identity(), bs).apply(a);
    }

    default <A, B> A foldl(BiFunction<A, B, A> fn, A a, __<F, B> bs) {
        return foldr(b -> h -> x -> h.apply(fn.apply(x, b)), Function.<A>identity(), bs).apply(a);
    }

    default <A> A fold1(Semigroup<A> sa, __<F, A> nestedA) {
        return foldMap1(sa, Function.identity(), nestedA);
    }

    default <A, B> B foldMap1(Semigroup<B> sa, Function<A, B> fn, __<F, A> nestedA) {
        Maybe<B> result = foldMap(Maybe.monoid(sa), a -> Maybe.Just(fn.apply(a)), nestedA);
        return result.getOrException("foldMap1 on mzero data structure");
    }

    default <A> Maybe<A> foldr1(Function<A, Function<A, A>> fn, __<F, A> nestedA) {
        return foldr(one -> maybeTwo -> maybeTwo.map(two -> fn.apply(one).apply(two)).orElse(Maybe.Just(one)),
            Maybe.Nothing(), nestedA);
    }

    default <A> Maybe<A> foldr1(BiFunction<A, A, A> fn, __<F, A> nestedA) {
        return foldr((one, maybeTwo) -> maybeTwo.map(two -> fn.apply(one, two)).orElse(Maybe.Just(one)),
            Maybe.Nothing(), nestedA);
    }

    default <A> Maybe<A> foldl1(Function<A, Function<A, A>> fn, __<F, A> nestedA) {
        return foldl(maybeOne -> two -> maybeOne.map(one -> fn.apply(one).apply(two)).orElse(Maybe.Just(two)),
            Maybe.Nothing(), nestedA);
    }

    default <A> Maybe<A> foldl1(BiFunction<A, A, A> fn, __<F, A> nestedA) {
        return foldl((maybeOne, two) -> maybeOne.map(one -> fn.apply(one, two)).orElse(Maybe.Just(two)),
            Maybe.Nothing(), nestedA);
    }

    default <A> List<A> toList(__<F, A> nestedA) {
        return foldr(x -> xs -> List.Cons(x, xs), List.Nil(), nestedA);
    }
}