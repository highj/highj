/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.automaton;

import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.Automaton;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowLoop;

/**
 *
 * @author clintonselke
 */
public interface AutomatonArrowLoop<A> extends AutomatonArrow<A>, ArrowLoop<___.µ<Automaton.µ,A>> {
    
    public ArrowLoop<A> get();

    @Override
    public default <B, C, D> Automaton<A,B,C> loop(__<___.µ<Automaton.µ, A>, T2<B, D>, T2<C, D>> arrow) {
        return () -> get().loop(
            get().dot(
                get().arr((T2<T2<C,D>,Automaton<A,T2<B,D>,T2<C,D>>> x) -> T2.of(T2.of(x._1()._1(), loop(x._2())), x._1()._2())),
                Automaton.narrow(arrow).unAutomaton()
            )
        );
    }
}
