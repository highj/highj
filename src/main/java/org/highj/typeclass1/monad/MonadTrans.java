package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.__;

public interface MonadTrans<T, M> extends Monad<__.µ<T, M>> {

    public <A> _<__.µ<T, M>, A> lift(_<M, A> nestedA);
}
