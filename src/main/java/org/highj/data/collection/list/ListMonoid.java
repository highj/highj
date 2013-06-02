package org.highj.data.collection.list;

import org.highj.data.collection.List;
import org.highj.typeclass0.group.Monoid;

public class ListMonoid<A> implements Monoid<List<A>> {

    @Override
    public List<A> identity() {
        return List.empty();
    }

    @Override
    public List<A> dot(List<A> x, List<A> y) {
        return List.append(x,y);
    }
}
