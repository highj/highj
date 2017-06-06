package org.highj.data.structural.tagged;

import org.derive4j.hkt.__;
import org.highj.data.structural.Tagged;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.Hkt.asTagged;

public interface TaggedBind<S> extends Bind<__<Tagged.µ, S>>, TaggedApply<S> {
    @Override
    default <A, B> Tagged<S, B> bind(__<__<Tagged.µ, S>, A> nestedA, Function<A, __<__<Tagged.µ, S>, B>> fn) {
        return asTagged(asTagged(nestedA).map(fn).get());
    }
}
