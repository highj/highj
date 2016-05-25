package org.highj.data.transformer.free;

import org.derive4j.hkt.__;
import org.highj.data.transformer.FreeT;
import org.highj.typeclass1.monad.Applicative;

public interface FreeTApplicative<F,M> extends FreeTApply<F,M>, Applicative<__<__<FreeT.µ,F>,M>> {
    @Override
    default <A> __<__<__<FreeT.µ, F>, M>, A> pure(A a) {
        return FreeT.done(a);
    }
}
