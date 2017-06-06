package org.highj.data.structural.tagged;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Tagged;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface TaggedFunctor<S> extends Functor<__<Tagged.µ, S>> {
    @Override
    default <A, B> Tagged<S, B> map(Function<A, B> fn, __<__<Tagged.µ, S>, A> nestedA) {
        return Hkt.asTagged(nestedA).map(fn);
    }
}
