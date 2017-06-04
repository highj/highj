package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Compose;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface ComposeApply<F, G> extends ComposeFunctor<F, G>, Apply<__<__<Compose.µ, F>, G>> {

    Apply<F> getF();

    Apply<G> getG();

    default <A, B> __<F, __<G, B>> __ap(__<F, __<G, Function<A, B>>> fn, __<F, __<G, A>> nestedA) {
        return getF().ap(getF().map(gab -> ga -> getG().ap(gab, ga), fn), nestedA);
    }

    @Override
    default <A, B> Compose<F, G, B> ap(__<__<__<Compose.µ, F>, G>, Function<A, B>> fn, __<__<__<Compose.µ, F>, G>, A> nestedA) {
        return new Compose<>(__ap(Hkt.asCompose(fn).get(), Hkt.asCompose(nestedA).get()));
    }
}
