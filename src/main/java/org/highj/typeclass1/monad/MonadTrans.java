package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.__;

public interface MonadTrans<T, M> extends Monad<_<T, M>> {

    public <A> __<T, M, A> lift(_<M, A> nestedA);
}
