package org.highj.data.instance.set;

import org.derive4j.hkt.__;
import org.highj.data.Set;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface SetFunctor extends Functor<Set.µ> {
    @Override
    default <A, B> Set<B> map(Function<A, B> fn, __<Set.µ, A> nestedA) {
        return Set.narrow(nestedA).map(fn);
    }
}
