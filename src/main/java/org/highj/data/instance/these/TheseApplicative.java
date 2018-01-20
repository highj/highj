package org.highj.data.instance.these;

import org.derive4j.hkt.__;
import org.highj.data.These;
import org.highj.typeclass1.monad.Applicative;

import static org.highj.data.These.That;

public interface TheseApplicative<F> extends TheseApply<F>, Applicative<__<These.µ, F>> {
    @Override
    default <A> These<F, A> pure(A a) {
        return That(a);
    }
}
