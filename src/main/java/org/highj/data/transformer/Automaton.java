/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.automaton.AutomatonArrow;
import org.highj.data.transformer.automaton.AutomatonArrowChoice;
import org.highj.data.transformer.automaton.AutomatonArrowPlus;
import org.highj.data.transformer.automaton.AutomatonArrowTransformer;
import org.highj.data.transformer.automaton.AutomatonArrowZero;
import org.highj.data.transformer.automaton.AutomatonCategory;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.ArrowChoice;
import org.highj.typeclass2.arrow.ArrowPlus;
import org.highj.typeclass2.arrow.ArrowZero;

/**
 *
 * @author clintonselke
 */
// https://hackage.haskell.org/package/arrows-0.4.4.1/docs/src/Control-Arrow-Transformer-Automaton.html#Automaton
public interface Automaton<A,B,C> extends ___<Automaton.µ, A, B, C> {
    public static class µ {}
    
    public static <A,B,C> Automaton<A,B,C> narrow(___<µ, A, B, C> a) {
        return (Automaton<A,B,C>)a;
    }
    
    public static <A,B,C> Automaton<A,B,C> narrow(__<___.µ<Automaton.µ, A>, B, C> a) {
        return (Automaton<A,B,C>)a;
    }
    
    public __<A,B,T2<C,Automaton<A,B,C>>> unAutomaton();
    
    public static <A> AutomatonCategory<A> automatonCategory(Arrow<A> aArrow) {
        return (AutomatonCategory<A>)() -> aArrow;
    }
    
    public static <A> AutomatonArrow<A> automatonArrow(Arrow<A> aArrow) {
        return (AutomatonArrow<A>)() -> aArrow;
    }
    
    public static <A> AutomatonArrowZero<A> automatonArrowZero(ArrowZero<A> aArrowZero) {
        return (AutomatonArrowZero<A>)() -> aArrowZero;
    }
    
    public static <A> AutomatonArrowPlus<A> automatonArrowPlus(ArrowPlus<A> aArrowPlus) {
        return (AutomatonArrowPlus<A>)() -> aArrowPlus;
    }
    
    public static <A> AutomatonArrowChoice<A> automatonArrowChoice(ArrowChoice<A> aArrowChoice) {
        return (AutomatonArrowChoice<A>)() -> aArrowChoice;
    }
    
    public static <A> AutomatonArrowTransformer<A> arrowTransformer(Arrow<A> aArrow) {
        return (AutomatonArrowTransformer<A>)() -> aArrow;
    }
}
