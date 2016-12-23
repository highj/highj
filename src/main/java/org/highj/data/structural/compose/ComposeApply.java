package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Compose;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface ComposeApply<F, G> extends ComposeFunctor<F, G>, Apply<__<__<Compose.µ, F>, G>> {

    @Override
    Apply<F> getF();

    @Override
    Apply<G> getG();

    @Override
    default <A, B> Compose<F, G, B> ap(__<__<__<Compose.µ, F>, G>, Function<A, B>> fn, __<__<__<Compose.µ, F>, G>, A> nestedA) {
        __<F, __<G, Function<A, B>>> fgab = Hkt.asCompose(fn).get();
        __<F, __<G, A>> fga = Hkt.asCompose(nestedA).get();
        return new Compose<>(getF().ap(getF().map(gab -> ga -> getG().ap(gab, ga), fgab), fga));
    }
}
