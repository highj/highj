package org.highj.data.transformer.state;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateT;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface StateTMonad<S, M> extends StateTApplicative<S, M>, StateTBind<S, M>, Monad<__<__<StateT.µ, S>, M>> {

}