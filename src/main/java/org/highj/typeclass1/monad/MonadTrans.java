package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

public interface MonadTrans<T, M> extends Monad<__<T, M>> {

    public <A> __2<T, M, A> lift(__<M, A> nestedA);
}
