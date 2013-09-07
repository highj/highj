package org.highj.data.collection.multiset;

import org.highj.data.collection.MultiSet;
import org.highj.typeclass0.group.Monoid;

public class MultiSetMonoid<A> implements Monoid<MultiSet<A>> {
    @Override
    public MultiSet<A> identity() {
        return MultiSet.empty();
    }

    @Override
    public MultiSet<A> dot(MultiSet<A> x, MultiSet<A> y) {
        return x.plus(y);
    }
}
