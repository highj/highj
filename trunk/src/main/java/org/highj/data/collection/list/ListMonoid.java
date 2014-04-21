package org.highj.data.collection.list;

import org.highj.data.collection.List;
import org.highj.typeclass0.group.Monoid;

public interface ListMonoid<A> extends Monoid<List<A>> {

    @Override
    public default List<A> identity() {
        return List.empty();
    }

    @Override
    public default List<A> dot(List<A> x, List<A> y) {
        return List.append(x,y);
    }
}
