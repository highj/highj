package org.highj.data.transformer.state;

import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadState;

/**
 * @author Clinton Selke
 */
public interface StateTMonadState<S, M> extends StateTMonad<S, M>, MonadState<S, __.µ<___.µ<StateT.µ, S>, M>> {

    public Monad<M> get();

    @Override
    public default StateT<S, M, S> getState() {
        return (S s) -> get().pure(T2.of(s, s));
    }

    @Override
    public default StateT<S, M, T0> putState(S newS) {
        return (S s) -> get().pure(T2.of(T0.of(), newS));
    }

}