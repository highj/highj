package org.highj.typeclass2.bifoldable;

import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.function.Functions;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The combined Bifoldable / Bifoldable1 type class.
 * <p>
 * Minimal complete definition: 'bifoldr' OR 'bifoldMap'.
 */

public interface Bifoldable<P> {

    default <M> M bifold(Monoid<M> monoid, __2<P, M, M> nestedM) {
        return bifoldMap(monoid,
                Function.identity(),
                Function.identity(),
                nestedM);
    }

    //Note that highJ returns a Maybe.Nothing instead of throwing an exception
    default <M> Maybe<M> bifold1(Semigroup<M> semigroup, __2<P, M, M> nestedM) {
        return bifoldMap1(semigroup,
                Function.identity(),
                Function.identity(),
                nestedM);
    }


    default <M, A, B> M bifoldMap(Monoid<M> monoid, Function<A, M> fn1, Function<B, M> fn2, __2<P, A, B> nestedAB) {
        return bifoldr((a, m) -> monoid.apply(fn1.apply(a), m),
                (b, m) -> monoid.apply(fn2.apply(b), m),
                monoid.identity(),
                nestedAB);
    }

    //Note that highJ returns a Maybe.Nothing instead of throwing an exception
    default <M, A, B> Maybe<M> bifoldMap1(Semigroup<M> semigroup, Function<A, M> fn1, Function<B, M> fn2, __2<P, A, B> nestedAB) {
        return bifoldMap(Maybe.monoid(semigroup),
                a -> Maybe.Just(fn1.apply(a)),
                b -> Maybe.Just(fn2.apply(b)),
                nestedAB);
    }

    default <A, B, C> C bifoldr(BiFunction<A, C, C> fn1, BiFunction<B, C, C> fn2, C start, __2<P, A, B> nestedAB) {
        return bifoldMap(Functions.endoMonoid(),
                a -> (Function<C, C>) c -> fn1.apply(a, c),
                b -> (Function<C, C>) c -> fn2.apply(b, c),
                nestedAB
        ).apply(start);
    }

    default <A, B, C> C bifoldl(BiFunction<C, A, C> fn1, BiFunction<C, B, C> fn2, C start, __2<P, A, B> nestedAB) {
        return bifoldMap(Monoid.dual(Functions.<C>endoMonoid()),
                a -> c -> fn1.apply(c, a),
                b -> c -> fn2.apply(c, b),
                nestedAB
        ).apply(start);
    }
}
