package org.highj.typeclass1.foldable;

import org.derive4j.hkt.__;
import org.highj.data.collection.List;
import org.highj.data.collection.Maybe;
import org.highj.data.functions.Functions;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;

import java.util.function.Function;

/**
 * The combined Foldable / Foldable1 type class.
 *
 * Minimal complete definition: 'foldr' OR 'foldMap'.
 */
public interface Foldable<F> {

    default <A> A fold(Monoid<A> ma, __<F, A> nestedA) {
        return foldMap(ma, Function.<A>identity(), nestedA);
    }

    default <A, B> B foldMap(final Monoid<B> mb, final Function<A, B> fn, __<F, A> nestedA) {
        return foldr((A a) -> b -> mb.apply(fn.apply(a), b), mb.identity(), nestedA);
    }

    default <A, B> B foldr(final Function<A, Function<B, B>> fn, B b, __<F, A> as) {
        //foldr f z t = appEndo (foldMap (Endo . f) t) z
        return foldMap(Functions.<B>endoMonoid(), fn, as).apply(b);
    }

    //This is very inefficient, please override if possible.
    default <A, B> A foldl(final Function<A, Function<B, A>> fn, A a, __<F, B> bs) {
        //foldl f a bs = foldr (\b h -> \a ->h (f a b)  ) id bs a
        return foldr((B b) -> (Function<A, A> h) -> (A x) -> h.apply(fn.apply(x).apply(b)), Function.<A>identity(), bs).apply(a);
    }

    default <A> A fold1(Semigroup<A> sa, __<F, A> nestedA) {
        return foldMap1(sa, Function.<A>identity(), nestedA);
    }

    default <A, B> B foldMap1(Semigroup<B> sa, Function<A, B> fn, __<F, A> nestedA) {
        Maybe<B> result = foldMap(Maybe.<B>monoid(sa), a -> Maybe.newJust(fn.apply(a)), nestedA);
        return result.getOrException("foldMap1 on mzero data structure");
    }

    default <A> Maybe<A> foldr1(final Function<A, Function<A, A>> fn, __<F, A> nestedA) {
        return foldr((A one) -> (Maybe<A> maybeTwo) ->
                maybeTwo.isJust()
                        ? Maybe.newJust(fn.apply(one).apply(maybeTwo.get()))
                        : Maybe.newJust(one),
                Maybe.<A>newNothing(), nestedA);
    }

    default <A> Maybe<A> foldl1(final Function<A, Function<A, A>> fn, __<F, A> nestedA) {
        return foldl((Maybe<A> maybeOne) -> (A two) ->
                maybeOne.isJust()
                        ? Maybe.newJust(fn.apply(maybeOne.get()).apply(two))
                        : Maybe.newJust(two),
                Maybe.<A>newNothing(), nestedA);
    }

    default <A> List<A> toList(__<F, A> nestedA) {
        return foldr((A x) -> (List<A> xs) -> List.newList(x, xs), List.<A>nil(), nestedA);
    }
}