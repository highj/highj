package org.highj.data.collection.list;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface ListFunctor extends Functor<List.µ> {
    @Override
    public default <A, B> List<B> map(final Function<A, B> fn, _<List.µ, A> nestedA) {
        return List.narrow(nestedA).map(fn);
    }
}
