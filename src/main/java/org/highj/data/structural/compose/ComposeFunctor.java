package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Compose;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface ComposeFunctor<F,G> extends Functor<__<__<Compose.µ,F>,G>> {

    Functor<F> getF();

    Functor<G> getG();

    @Override
    default <A, B> Compose<F, G, B> map(Function<A, B> fn, __<__<__<Compose.µ, F>, G>, A> nestedA) {
        __<F, __<G, A>> fga = Hkt.asCompose(nestedA).get();
        __<F, __<G, B>> fgb = getF().map(ga -> getG().map(fn, ga), fga);
        return new Compose<>(fgb);
    }
}
