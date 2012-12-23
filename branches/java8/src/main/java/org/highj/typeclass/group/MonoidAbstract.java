package org.highj.typeclass.group;

import org.highj.data.collection.List;
import org.highj.function.F2;

public class MonoidAbstract<A> extends SemigroupAbstract<A> implements Monoid<A> {
    private final A id;

    public MonoidAbstract(F2<A, A, A> dotFunction, A id) {
        super(dotFunction);
        this.id = id;
    }

    public MonoidAbstract(Semigroup<A> semigroup, A id) {
        this(semigroup.dot(), id);
    }

    @Override
    public A identity() {
        return id;
    }

    @Override
    public A fold(List<A> as) {
        return fold(id, as);
    }


}
