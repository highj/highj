/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.automaton;

import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.Automaton;
import org.highj.typeclass2.arrow.ArrowPlus;

/**
 *
 * @author clintonselke
 */
public interface AutomatonArrowPlus<A> extends AutomatonArrowZero<A>, ArrowPlus<___.µ<Automaton.µ,A>> {
    
    public ArrowPlus<A> get();

    @Override
    public default <B, C> Automaton<A,B,C> plus(__<___.µ<Automaton.µ, A>, B, C> arrow1, __<___.µ<Automaton.µ, A>, B, C> arrow2) {
        return () -> get().plus(
            Automaton.narrow(arrow1).unAutomaton(),
            Automaton.narrow(arrow2).unAutomaton()
        );
    }
}
