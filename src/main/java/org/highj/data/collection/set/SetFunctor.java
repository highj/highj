package org.highj.data.collection.set;

import org.highj._;
import org.highj.data.collection.Set;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public class SetFunctor implements Functor<Set.µ> {
    @Override
    public <A, B> Set<B> map(Function<A, B> fn, _<Set.µ, A> nestedA) {
        return Set.narrow(nestedA).map(fn);
    }
}
