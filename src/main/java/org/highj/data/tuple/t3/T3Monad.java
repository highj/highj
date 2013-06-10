package org.highj.data.tuple.t3;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface T3Monad<S,T> extends T3Bind<S,T>, T3Applicative<S,T>, Monad<__.µ<___.µ<T3.µ, S>, T>> {

    @Override
    public Monoid<S> getS();

    @Override
    public Monoid<T> getT();

    public default <A, B> T3<S, T, B> ap(_<__.µ<___.µ<T3.µ, S>, T>, Function<A, B>> fn,
                                                           _<__.µ<___.µ<T3.µ, S>, T>, A> nestedA) {
        return T3Applicative.super.ap(fn, nestedA);
    }
}
