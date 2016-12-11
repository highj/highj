package org.highj.data.transformer.free;

import org.derive4j.hkt.__;
import org.highj.data.transformer.FreeT;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asFreeT;

public interface FreeTFunctor<F,M> extends Functor<__<__<FreeT.µ,F>,M>> {
    @Override
    default <A, B> __<__<__<FreeT.µ, F>, M>, B> map(Function<A, B> fn, __<__<__<FreeT.µ, F>, M>, A> nestedA) {
        return FreeT.bind(asFreeT(nestedA), (A a) -> FreeT.done(fn.apply(a)));
    }
}
