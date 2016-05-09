package org.highj.data.transformer.state;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadState;

/**
 * @author Clinton Selke
 */
public interface StateTMonadState<S, M> extends StateTMonad<S, M>, MonadState<S, __<__<StateT.µ, S>, M>> {

    public Monad<M> m();

    @Override
    public default StateT<S, M, S> get() {
        return (S s) -> m().pure(T2.of(s, s));
    }

    @Override
    public default StateT<S, M, T0> put(S newS) {
        return (S s) -> m().pure(T2.of(T0.of(), newS));
    }

}