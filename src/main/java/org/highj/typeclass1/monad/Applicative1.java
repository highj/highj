package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;

public interface Applicative1<M> extends Apply1<M> {
    <A> __<M, A> pure(A a);
}
