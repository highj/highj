/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.automaton;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.Automaton;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.Category;

import static org.highj.Hkt.asAutomaton;

/**
 *
 * @author clintonselke
 */
public interface AutomatonCategory<A> extends Category<__<Automaton.µ,A>> {
    
    public Arrow<A> get();

    @Override
    public default <B> Automaton<A,B,B> identity() {
        return () -> get().arr((B b) -> T2.of(b, identity()));
    }

    @Override
    public default <B, C, D> Automaton<A,B,D> dot(__2<__<Automaton.µ, A>, C, D> cd, __2<__<Automaton.µ, A>, B, C> bc) {
        return () -> get().dot(
            get().dot(
                get().arr((T2<T2<D,Automaton<A,C,D>>,Automaton<A,B,C>> x) -> T2.of(x._1()._1(), dot(x._1()._2(), x._2()))),
                get().<C,T2<D,Automaton<A,C,D>>,Automaton<A,B,C>>first(asAutomaton(cd).unAutomaton())
            ),
            asAutomaton(bc).unAutomaton()
        );
    }
}
