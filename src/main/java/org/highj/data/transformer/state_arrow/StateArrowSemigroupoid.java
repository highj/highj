/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.state_arrow;

import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.transformer.StateArrow;
import org.highj.typeclass2.arrow.Semigroupoid;

/**
 *
 * @author clintonselke
 */
public interface StateArrowSemigroupoid<S,A> extends Semigroupoid<___.µ<____.µ<StateArrow.µ,S>,A>> {
    
    public Semigroupoid<A> a();

    @Override
    public default <B, C, D> StateArrow<S, A, B, D> dot(__<___.µ<____.µ<StateArrow.µ, S>, A>, C, D> cd, __<___.µ<____.µ<StateArrow.µ, S>, A>, B, C> bc) {
        return StateArrow.stateArrow(a().dot(
            StateArrow.narrow(cd).run(),
            StateArrow.narrow(bc).run()
        ));
    }
}
