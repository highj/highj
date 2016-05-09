/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.state_arrow;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateArrow;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowState;

/**
 *
 * @author clintonselke
 */
public interface StateArrowArrowState<S,A> extends StateArrowArrow<S,A>, ArrowState<S,__<__<StateArrow.µ,S>,A>> {

    @Override
    public default <B> StateArrow<S, A, B, S> fetch() {
        return StateArrow.stateArrow(
            a().arr((T2<B,S> x) -> T2.of(x._2(), x._2()))
        );
    }

    @Override
    public default StateArrow<S, A, S, T0> store() {
        return StateArrow.stateArrow(
            a().arr((T2<S,S> x) -> T2.of(T0.of(), x._1()))
        );
    }
    
}
