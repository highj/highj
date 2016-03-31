/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.automaton;

import org.highj._;
import org.highj.___;
import org.highj.data.transformer.Automaton;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowCircuit;

/**
 *
 * @author clintonselke
 */
public interface AutomatonArrowCircuit<A> extends AutomatonArrowLoop<A>, ArrowCircuit<_<Automaton.µ,A>> {

    @Override
    public default <B> Automaton<A,B,B> delay(B b1) {
        return () -> get().arr((B b2) -> T2.of(b1, delay(b2)));
    }
}
