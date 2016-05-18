package org.highj.data.transformer.generator;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.transformer.GeneratorT;
import org.highj.typeclass1.monad.Apply;

public interface GeneratorTApply<E,M> extends GeneratorTFunctor<E,M>, Apply<__<__<GeneratorT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<GeneratorT.µ, E>, M>, B> ap(__<__<__<GeneratorT.µ, E>, M>, Function<A, B>> fn, __<__<__<GeneratorT.µ, E>, M>, A> nestedA) {
        return GeneratorT.bind(
            GeneratorT.narrow(fn),
            (Function<A, B> x1) ->
                GeneratorT.bind(
                    GeneratorT.narrow(nestedA),
                    (A x2) -> GeneratorT.done(x1.apply(x2))
                )
        );
    }
}
