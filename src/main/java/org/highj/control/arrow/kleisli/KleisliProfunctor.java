package org.highj.control.arrow.kleisli;

import org.derive4j.hkt.__;
import org.highj.control.arrow.Kleisli;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass2.profunctor.Profunctor;

import java.util.function.Function;

import static org.highj.Hkt.asKleisli;

public interface KleisliProfunctor<M> extends Profunctor<__<Kleisli.µ, M>> {

    Monad<M> getM();

    @Override
    default <A, B, C> Kleisli<M, A, C> lmap(Function<A, B> f, __<__<__<Kleisli.µ, M>, B>, C> p) {
        return asKleisli(p).lmap(f);
    }

    @Override
    default <A, B, C> Kleisli<M, A, C> rmap(Function<B, C> g, __<__<__<Kleisli.µ, M>, A>, B> p) {
        return new Kleisli<>(getM().lift(g).compose(asKleisli(p)));
    }
}
