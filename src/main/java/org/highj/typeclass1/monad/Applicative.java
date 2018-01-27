package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;

public interface Applicative<M> extends Apply<M> {

    // pure (Data.Pointed, Control.Applicative)
    <A> __<M, A> pure(A a);

}
