/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.automaton;

import org.derive4j.hkt.__2;
import org.highj.data.transformer.Automaton;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.ArrowTransformer;

/**
 *
 * @author clintonselke
 */
public interface AutomatonArrowTransformer<A> extends AutomatonArrow<A>, ArrowTransformer<Automaton.µ,A> {
    
    public Arrow<A> get();
    
    @Override
    public default <B, C> Automaton<A,B,C> lift(__2<A, B, C> arrow) {
        return () -> get().dot(
            get().arr((C c) -> T2.of(c, lift(arrow))),
            arrow
        );
    }
}
