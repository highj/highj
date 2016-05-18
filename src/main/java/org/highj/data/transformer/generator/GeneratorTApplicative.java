package org.highj.data.transformer.generator;

import org.derive4j.hkt.__;
import org.highj.data.transformer.GeneratorT;
import org.highj.typeclass1.monad.Applicative;

public interface GeneratorTApplicative<E,M> extends GeneratorTApply<E,M>, Applicative<__<__<GeneratorT.µ,E>,M>> {

    @Override
    public default <A> __<__<__<GeneratorT.µ, E>, M>, A> pure(A a) {
        return GeneratorT.done(a);
    }
}
