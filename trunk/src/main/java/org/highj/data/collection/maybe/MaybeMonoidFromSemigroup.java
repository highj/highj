package org.highj.data.collection.maybe;

import org.highj.data.collection.Maybe;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;

public class MaybeMonoidFromSemigroup<A> implements Monoid<Maybe<A>> {

    private final Semigroup<A> semigroup;

    public MaybeMonoidFromSemigroup(final Semigroup<A> semigroup) {
        this.semigroup = semigroup;
    }

    @Override
    public Maybe<A> identity() {
        return Maybe.Nothing();
    }

    @Override
    public Maybe<A> dot(Maybe<A> x, Maybe<A> y) {
        return x.bind(xValue -> y.<A>map(yValue -> semigroup.dot(xValue, yValue)));
    }
}
