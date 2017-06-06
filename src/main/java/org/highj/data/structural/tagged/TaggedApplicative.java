package org.highj.data.structural.tagged;

import org.derive4j.hkt.__;
import org.highj.data.structural.Tagged;
import org.highj.typeclass1.monad.Applicative;

import static org.highj.data.structural.Tagged.*;

public interface TaggedApplicative<S> extends Applicative<__<Tagged.µ, S>>, TaggedApply<S> {
    @Override
    default <A> Tagged<S, A> pure(A a) {
        return Tagged(a);
    }
}
