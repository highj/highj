package org.highj.function.f1;

import org.derive4j.hkt.__;
import org.highj.function.F1;
import org.highj.typeclass2.profunctor.Profunctor;

import java.util.function.Function;

import static org.highj.Hkt.asF1;

public interface F1Profunctor extends Profunctor<F1.µ> {
    @Override
    default <A, B, C, D> F1<A, D> dimap(Function<A, B> f, Function<C, D> g, __<__<F1.µ, B>, C> p) {
        return F1.fromFunction(f.andThen(asF1(p)).andThen(g));
    }

    @Override
    default <A, B, C> F1<A, C> lmap(Function<A, B> f, __<__<F1.µ, B>, C> p) {
        return F1.fromFunction(f.andThen(asF1(p)));
    }

    @Override
    default <A, B, C> F1<A, C> rmap(Function<B, C> g, __<__<F1.µ, A>, B> p) {
        return F1.fromFunction(asF1(p).andThen(g));
    }
}
