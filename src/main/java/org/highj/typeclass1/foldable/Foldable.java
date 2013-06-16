package org.highj.typeclass1.foldable;

import org.highj._;
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

    public default <A> A fold(Monoid<A> ma, _<F, A> nestedA) {
        return foldMap(ma, Functions.<A>id(), nestedA);
    }

    public default <A, B> B foldMap(final Monoid<B> mb, final Function<A, B> fn, _<F, A> nestedA) {
        return foldr((A a) -> b -> mb.dot(fn.apply(a), b), mb.identity(), nestedA);
    }

    public default <A, B> B foldr(final Function<A, Function<B, B>> fn, B b, _<F, A> as) {
        //foldr f z t = appEndo (foldMap (Endo . f) t) z
        return foldMap(Functions.<B>endoMonoid(), fn, as).apply(b);
    }

    //This is very inefficient, please override if possible.
    public default <A, B> A foldl(final Function<A, Function<B, A>> fn, A a, _<F, B> bs) {
        //foldl f a bs = foldr (\b h -> \a ->h (f a b)  ) id bs a
        return foldr((B b) -> (Function<A, A> h) -> (A x) -> h.apply(fn.apply(x).apply(b)), Functions.<A>id(), bs).apply(a);
    }

    public default <A> A fold1(Semigroup<A> sa, _<F, A> nestedA) {
        return foldMap1(sa, Functions.<A>id(), nestedA);
    }

    public default <A, B> B foldMap1(Semigroup<B> sa, Function<A, B> fn, _<F, A> nestedA) {
        Maybe<B> result = foldMap(Maybe.<B>monoid(sa), a -> Maybe.Just(fn.apply(a)), nestedA);
        return result.getOrError("foldMap1 on mzero data structure");
    }

    public default <A> Maybe<A> foldr1(final Function<A, Function<A, A>> fn, _<F, A> nestedA) {
        return foldr((A one) -> (Maybe<A> maybeTwo) ->
                maybeTwo.isJust()
                        ? Maybe.Just(fn.apply(one).apply(maybeTwo.get()))
                        : Maybe.Just(one),
                Maybe.<A>Nothing(), nestedA);
    }

    public default <A> Maybe<A> foldl1(final Function<A, Function<A, A>> fn, _<F, A> nestedA) {
        return foldl((Maybe<A> maybeOne) -> (A two) ->
                maybeOne.isJust()
                        ? Maybe.Just(fn.apply(maybeOne.get()).apply(two))
                        : Maybe.Just(two),
                Maybe.<A>Nothing(), nestedA);
    }

    public default <A> List<A> toList(_<F, A> nestedA) {
        return foldr((A x) -> (List<A> xs) -> List.cons(x, xs), List.<A>nil(), nestedA);
    }
}