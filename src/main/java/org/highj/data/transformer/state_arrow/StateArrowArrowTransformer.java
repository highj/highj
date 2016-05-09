/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.state_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.StateArrow;
import org.highj.typeclass2.arrow.ArrowTransformer;

/**
 *
 * @author clintonselke
 */
public interface StateArrowArrowTransformer<S,A> extends StateArrowArrow<S,A>, ArrowTransformer<__<StateArrow.µ,S>,A> {

    @Override
    public default <B, C> StateArrow<S, A, B, C> lift(__2<A, B, C> arrow) {
        return StateArrow.stateArrow(a().first(arrow));
    }
}
