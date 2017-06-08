package org.highj.control.arrow.cokleisli;

import org.derive4j.hkt.__;
import org.highj.control.arrow.Cokleisli;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass2.profunctor.Profunctor;

import java.util.function.Function;

import static org.highj.Hkt.asCokleisli;

public interface CokleisliProfunctor<W> extends Profunctor<__<Cokleisli.µ, W>> {

    Functor<W> getW();

    @Override
    default <A, B, C, D> Cokleisli<W, A, D> dimap(Function<A, B> f, Function<C, D> g, __<__<__<Cokleisli.µ, W>, B>, C> p) {
        return new Cokleisli<>(g.compose(asCokleisli(p)).compose(getW().lift(f)));
    }

    @Override
    default <A, B, C> Cokleisli<W, A, C> lmap(Function<A, B> f, __<__<__<Cokleisli.µ, W>, B>, C> p) {
        return new Cokleisli<>(asCokleisli(p).compose(getW().lift(f)));
    }

    @Override
    default <A, B, C> Cokleisli<W, A, C> rmap(Function<B, C> g, __<__<__<Cokleisli.µ, W>, A>, B> p) {
        return new Cokleisli<>(g.compose(asCokleisli(p)));
    }
}
