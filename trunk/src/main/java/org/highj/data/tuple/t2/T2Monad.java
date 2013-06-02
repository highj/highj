package org.highj.data.tuple.t2;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface T2Monad<S> extends T2Bind<S>, T2Applicative<S>, Monad<__.µ<T2.µ, S>> {

    @Override
    public Monoid<S> getS();

    public default <A, B> _<__.µ<T2.µ, S>, B> ap(_<__.µ<T2.µ, S>, Function<A, B>> fn, _<__.µ<T2.µ, S>, A> nestedA) {
        return T2Applicative.super.ap(fn, nestedA);
    }
}
