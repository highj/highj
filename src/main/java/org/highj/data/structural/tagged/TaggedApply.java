package org.highj.data.structural.tagged;

import org.derive4j.hkt.__;
import org.highj.data.structural.Tagged;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asTagged;

public interface TaggedApply<S> extends Apply<__<Tagged.µ, S>>, TaggedFunctor<S> {
    @Override
    default <A, B> Tagged<S, B> ap(__<__<Tagged.µ, S>, Function<A, B>> fn, __<__<Tagged.µ, S>, A> nestedA) {
        return asTagged(nestedA).map(asTagged(fn).get());
    }
}
