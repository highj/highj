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
import org.highj.typeclass2.arrow.ArrowLoop;

import static org.highj.Hkt.asAutomaton;

/**
 *
 * @author clintonselke
 */
public interface AutomatonArrowLoop<A> extends AutomatonArrow<A>, ArrowLoop<__<Automaton.µ,A>> {
    
    public ArrowLoop<A> get();

    @Override
    public default <B, C, D> Automaton<A,B,C> loop(__2<__<Automaton.µ, A>, T2<B, D>, T2<C, D>> arrow) {
        return () -> get().loop(
            get().dot(
                get().arr((T2<T2<C,D>,Automaton<A,T2<B,D>,T2<C,D>>> x) -> T2.of(T2.of(x._1()._1(), loop(x._2())), x._1()._2())),
                asAutomaton(arrow).unAutomaton()
            )
        );
    }
}
