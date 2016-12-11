package org.highj.data.tuple.t3;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.tuple.T3;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

import static org.highj.Hkt.asT3;

public interface T3Bifunctor<S> extends Bifunctor<__<T3.µ, S>> {

    @Override
    default <A, B, C, D> T3<S, B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __2<__<T3.µ, S>, A, C> nestedAC) {
        T3<S, A, C> triple = asT3(nestedAC);
        return T3.of(triple._1(), fn1.apply(triple._2()), fn2.apply(triple._3()));
    }
}
