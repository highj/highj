package org.highj.data.tuple.t3;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface T3Functor<S, T> extends Functor<__<__<T3.µ, S>, T>> {

    @Override
    default <A, B> T3<S, T, B> map(Function<A, B> fn, __<__<__<T3.µ, S>, T>, A> nestedA) {
        return T3.narrow(nestedA).map_3(fn);
    }
}
