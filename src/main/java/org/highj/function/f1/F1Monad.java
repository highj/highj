package org.highj.function.f1;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.function.F1;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

import static org.highj.Hkt.asF1;

public interface F1Monad<R> extends F1Functor<R>, Monad<__<F1.µ, R>> {

    @Override
    default <A> F1<R, A> pure(A a) {
        return F1.constant(a);
    }

    @Override
    default <A, B> F1<R, B> ap(__<__<F1.µ, R>, Function<A, B>> fn, __<__<F1.µ, R>, A> a) {
        return r -> asF1(fn).apply(r).apply(
                asF1(a).apply(r));
    }

    @Override
    default <A, B> F1<R, B> bind(__<__<F1.µ, R>, A> a, Function<A, __<__<F1.µ, R>, B>> fn) {
        return r -> asF1(fn.apply(
                asF1(a).apply(r))).apply(r);
    }

}