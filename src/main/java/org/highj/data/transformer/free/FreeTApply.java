package org.highj.data.transformer.free;

import org.derive4j.hkt.__;
import org.highj.data.transformer.FreeT;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asFreeT;

public interface FreeTApply<F,M> extends FreeTFunctor<F,M>, Apply<__<__<FreeT.µ,F>,M>> {
    @Override
    default <A, B> __<__<__<FreeT.µ, F>, M>, B> ap(__<__<__<FreeT.µ, F>, M>, Function<A, B>> fn, __<__<__<FreeT.µ, F>, M>, A> nestedA) {
        return FreeT.bind(
            asFreeT(fn),
            (Function<A,B> f) -> FreeT.bind(
                asFreeT(nestedA),
                (A a) -> FreeT.done(f.apply(a))
            )
        );
    }
}
