package org.highj.typeclass1.monad;

import org.highj._;

public interface Applicative<M> extends Apply<M> {

    // pure (Data.Pointed, Control.Applicative)
    public <A> _<M, A> pure(A a);

}
