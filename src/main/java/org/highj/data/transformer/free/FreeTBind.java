package org.highj.data.transformer.free;

import org.derive4j.hkt.__;
import org.highj.data.transformer.FreeT;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.Hkt.asFreeT;

public interface FreeTBind<F,M> extends FreeTApply<F,M>, Bind<__<__<FreeT.µ,F>,M>> {
    @Override
    default <A, B> __<__<__<FreeT.µ, F>, M>, B> bind(__<__<__<FreeT.µ, F>, M>, A> nestedA, Function<A, __<__<__<FreeT.µ, F>, M>, B>> fn) {
        return FreeT.bind(asFreeT(nestedA), (A a) -> asFreeT(fn.apply(a)));
    }
}
