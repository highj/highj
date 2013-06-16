package org.highj.typeclass1.monad;

import org.highj._;

public interface MonadTrans<T,M> extends Monad<_<T,M>> {

    public <A> _<_<T,M>,A> lift(_<M,A> nestedA);
}
