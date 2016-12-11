package org.highj.data.tuple.t4;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.tuple.T4;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

import static org.highj.Hkt.asT4;

public interface T4Bifunctor<S, T> extends Bifunctor<__<__<T4.µ, S>, T>> {
    @Override
    default <A, B, C, D> T4<S, T, B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __2<__<__<T4.µ, S>, T>, A, C> nestedAC) {
        T4<S, T, A, C> quadruple = asT4(nestedAC);
        return T4.of(quadruple._1(), quadruple._2(), fn1.apply(quadruple._3()), fn2.apply(quadruple._4()));
    }
}
