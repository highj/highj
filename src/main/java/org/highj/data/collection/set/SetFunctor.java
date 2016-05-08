package org.highj.data.collection.set;

import org.derive4j.hkt.__;
import org.highj.data.collection.Set;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface SetFunctor extends Functor<Set.µ> {
    @Override
    default <A, B> Set<B> map(Function<A, B> fn, __<Set.µ, A> nestedA) {
        return Set.narrow(nestedA).map(fn);
    }
}
