package org.highj.data.tuple.t1;

import org.highj._;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public class T1Monad implements Monad<T1.µ>, T1Functor {

    @Override
    public <A> _<T1.µ, A> pure(A a) {
        return Tuple.of(a);
    }

    @Override
    public <A, B> _<T1.µ, B> ap(_<T1.µ, Function<A, B>> nestedFn, _<T1.µ, A> nestedA) {
        return Tuple.of(Tuple.narrow(nestedFn).get().apply(Tuple.narrow(nestedA).get()));
    }

    @Override
    public <A, B> _<T1.µ, B> bind(_<T1.µ, A> nestedA, Function<A, _<T1.µ, B>> fn) {
        return fn.apply(Tuple.narrow(nestedA)._1());
    }
}
