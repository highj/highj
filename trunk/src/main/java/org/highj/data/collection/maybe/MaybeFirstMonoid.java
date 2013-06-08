package org.highj.data.collection.maybe;

import org.highj.data.collection.Maybe;
import org.highj.typeclass0.group.Monoid;

/**
 * Mirrors the Monoid (First a) instance in Data.Monoid
 */

public class MaybeFirstMonoid<A> implements Monoid<Maybe<A>> {
    @Override
    public Maybe<A> identity() {
        return Maybe.Nothing();
    }

    @Override
    public Maybe<A> dot(Maybe<A> x, Maybe<A> y) {
        return x.isJust() ? x : y;
    }
}
