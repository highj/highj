package org.highj.data.tuple.t1;

import org.highj._;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public class T1Monad implements Monad<T1.µ>, T1Functor {

    @Override
    public <A> T1<A> pure(A a) {
        return T1.of(a);
    }

    @Override
    public <A, B> T1<B> ap(_<T1.µ, Function<A, B>> nestedFn, _<T1.µ, A> nestedA) {
        return T1.of(T1.narrow(nestedFn).get().apply(T1.narrow(nestedA).get()));
    }

    @Override
    public <A, B> T1<B> bind(_<T1.µ, A> nestedA, Function<A, _<T1.µ, B>> fn) {
        return T1.narrow(fn.apply(T1.narrow(nestedA)._1()));
    }
}
