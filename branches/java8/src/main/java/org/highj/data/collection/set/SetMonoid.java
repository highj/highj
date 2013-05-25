package org.highj.data.collection.set;

import org.highj.data.collection.Set;
import org.highj.typeclass0.group.Monoid;

public class SetMonoid<A> implements Monoid<Set<A>> {
    @Override
    public Set<A> identity() {
        return Set.empty();
    }

    @Override
    public Set<A> dot(Set<A> x, Set<A> y) {
        return x.plus(y);
    }
}
