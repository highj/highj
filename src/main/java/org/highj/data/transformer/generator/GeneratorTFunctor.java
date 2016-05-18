package org.highj.data.transformer.generator;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.transformer.GeneratorT;
import org.highj.typeclass1.functor.Functor;

public interface GeneratorTFunctor<E,M> extends Functor<__<__<GeneratorT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<GeneratorT.µ, E>, M>, B> map(Function<A, B> fn, __<__<__<GeneratorT.µ, E>, M>, A> nestedA) {
        return GeneratorT.bind(GeneratorT.narrow(nestedA), (A a) -> GeneratorT.done(fn.apply(a)));
    }
}
