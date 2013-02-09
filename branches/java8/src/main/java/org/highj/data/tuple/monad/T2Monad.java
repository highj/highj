package org.highj.data.tuple.monad;

import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Monad;

public interface T2Monad<S> extends T2Bind<S>, T2Applicative<S>, Monad<__.µ<T2.µ, S>> {

    @Override
    public Monoid<S> getS();
}
