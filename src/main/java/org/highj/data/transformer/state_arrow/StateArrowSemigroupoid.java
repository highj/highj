/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.state_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.StateArrow;
import org.highj.typeclass2.arrow.Semigroupoid;

import static org.highj.Hkt.asStateArrow;

/**
 *
 * @author clintonselke
 */
public interface StateArrowSemigroupoid<S,A> extends Semigroupoid<__<__<StateArrow.µ,S>,A>> {
    
    public Semigroupoid<A> a();

    @Override
    public default <B, C, D> StateArrow<S, A, B, D> dot(__2<__<__<StateArrow.µ, S>, A>, C, D> cd, __2<__<__<StateArrow.µ, S>, A>, B, C> bc) {
        return StateArrow.stateArrow(a().dot(
            asStateArrow(cd).run(),
            asStateArrow(bc).run()
        ));
    }
}
