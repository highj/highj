package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.Hkt.asT1;

public interface T1Applicative extends Applicative<T1.µ>, T1Functor {

    @Override
    default <A> T1<A> pure(A a) {
        return T1.of(a);
    }

    @Override
    default <A, B> T1<B> ap(__<T1.µ, Function<A, B>> nestedFn, __<T1.µ, A> nestedA) {
        return asT1(nestedA).ap(asT1(nestedFn));
    }

}
