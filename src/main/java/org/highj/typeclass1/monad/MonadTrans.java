package org.highj.typeclass1.monad;

import org.highj._;

public interface MonadTrans<µ,M> extends Monad<_<µ,M>> {

    public <A> _<_<µ,M>,A> lift(_<M,A> nestedA);
}
