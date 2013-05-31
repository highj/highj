package org.highj.data.tuple.t4;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface T4Monad<S,T,U> extends T4Applicative<S,T,U>, T4Bind<S,T,U>, Monad<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>> {
    @Override
    public Monoid<S> getS();

    @Override
    public Monoid<T> getT();

    @Override
    public Monoid<U> getU();


    public default <A, B> _<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>, B> ap(_<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>, Function<A, B>> fn,
                                                           _<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>, A> nestedA) {
        return T4Applicative.super.ap(fn, nestedA);
    }
}
