package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface T1Monad extends Monad<T1.µ>, T1Applicative {

    @Override
    default <A, B> T1<B> bind(__<T1.µ, A> nestedA, Function<A, __<T1.µ, B>> fn) {
        return T1.narrow(nestedA).bind(a -> T1.narrow(fn.apply(a)));
    }
}
