package org.highj.data.tuple.t2;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface T2Functor<S> extends Functor<__<T2.µ, S>> {

    @Override
    default <A, B> T2<S, B> map(Function<A, B> fn, __<__<T2.µ, S>, A> nestedA) {
        return T2.narrow(nestedA).map_2(fn);
    }
}
