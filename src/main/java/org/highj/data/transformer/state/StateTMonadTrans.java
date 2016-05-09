package org.highj.data.transformer.state;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadTrans;

/**
 * @author Clinton Selke
 */
public interface StateTMonadTrans<S, M> extends StateTMonad<S, M>, MonadTrans<__<StateT.µ, S>, M> {

    public Monad<M> m();

    @Override
    public default <A> StateT<S, M, A> lift(__<M, A> nestedA) {
        return (S s) -> m().map((A a) -> T2.of(a, s), nestedA);
    }

}