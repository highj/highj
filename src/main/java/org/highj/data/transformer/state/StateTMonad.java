package org.highj.data.transformer.state;

/**
 * @author Clinton Selke
 */
public interface StateTMonad<S, M> extends StateTApplicative<S, M>, StateTBind<S, M> {

}