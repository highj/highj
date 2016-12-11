/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.automaton;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.Automaton;
import org.highj.typeclass2.arrow.ArrowPlus;

import static org.highj.Hkt.asAutomaton;

/**
 *
 * @author clintonselke
 */
public interface AutomatonArrowPlus<A> extends AutomatonArrowZero<A>, ArrowPlus<__<Automaton.µ,A>> {
    
    public ArrowPlus<A> get();

    @Override
    public default <B, C> Automaton<A,B,C> plus(__2<__<Automaton.µ, A>, B, C> arrow1, __2<__<Automaton.µ, A>, B, C> arrow2) {
        return () -> get().plus(
            asAutomaton(arrow1).unAutomaton(),
            asAutomaton(arrow2).unAutomaton()
        );
    }
}
