/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.state_arrow;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateArrow;
import org.highj.typeclass2.arrow.Category;

/**
 *
 * @author clintonselke
 */
public interface StateArrowCategory<S,A> extends StateArrowSemigroupoid<S,A>, Category<__<__<StateArrow.µ,S>,A>> {
    
    @Override
    public Category<A> a();

    @Override
    public default <B> StateArrow<S, A, B, B> identity() {
        return StateArrow.stateArrow(a().identity());
    }
}
