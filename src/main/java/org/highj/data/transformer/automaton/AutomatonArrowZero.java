/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.automaton;

import org.derive4j.hkt.__;
import org.highj.data.transformer.Automaton;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowZero;

/**
 *
 * @author clintonselke
 */
public interface AutomatonArrowZero<A> extends AutomatonArrow<A>, ArrowZero<__<Automaton.µ,A>> {
    
    public ArrowZero<A> get();

    @Override
    public default <B, C> Automaton<A,B,C> zero() {
        return () -> get().<B,T2<C,Automaton<A,B,C>>>zero();
    }
}
