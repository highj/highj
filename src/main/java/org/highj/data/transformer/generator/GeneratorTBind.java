package org.highj.data.transformer.generator;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.transformer.GeneratorT;
import org.highj.typeclass1.monad.Bind;

public interface GeneratorTBind<E,M> extends GeneratorTApply<E,M>, Bind<__<__<GeneratorT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<GeneratorT.µ, E>, M>, B> bind(__<__<__<GeneratorT.µ, E>, M>, A> nestedA, Function<A, __<__<__<GeneratorT.µ, E>, M>, B>> fn) {
        return GeneratorT.bind(GeneratorT.narrow(nestedA), (A a) -> GeneratorT.narrow(fn.apply(a)));
    }
}
