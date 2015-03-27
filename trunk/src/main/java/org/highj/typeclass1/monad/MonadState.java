package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.tuple.T0;

/**
 * @author Clinton Selke
 */
public interface MonadState<S, M> extends Monad<M> {

    public _<M, S> getState();

    public _<M, T0> putState(S s);

}
