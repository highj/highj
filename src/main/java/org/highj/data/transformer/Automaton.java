/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.data.Stream;
import org.highj.data.transformer.automaton.AutomatonArrow;
import org.highj.data.transformer.automaton.AutomatonArrowChoice;
import org.highj.data.transformer.automaton.AutomatonArrowPlus;
import org.highj.data.transformer.automaton.AutomatonArrowTransformer;
import org.highj.data.transformer.automaton.AutomatonArrowZero;
import org.highj.data.transformer.automaton.AutomatonCategory;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.ArrowApply;
import org.highj.typeclass2.arrow.ArrowChoice;
import org.highj.typeclass2.arrow.ArrowLoop;
import org.highj.typeclass2.arrow.ArrowPlus;
import org.highj.typeclass2.arrow.ArrowZero;

/**
 *
 * @author clintonselke
 */
// https://hackage.haskell.org/package/arrows-0.4.4.1/docs/src/Control-Arrow-Transformer-Automaton.html#Automaton
public interface Automaton<A,B,C> extends __3<Automaton.µ, A, B, C> {
    class µ {}
    
    __2<A,B,T2<C,Automaton<A,B,C>>> unAutomaton();
    
    static <A,E,B,C> __2<A,T2<E,Stream<B>>,Stream<C>> run(ArrowLoop<A> aArrowLoop, ArrowApply<A> aArrowApply, Automaton<A,T2<E,B>,C> x) {
        Arrow<A> a = aArrowApply;
        return a.dot(
            a.arr((T2<C,Stream<C>> x2) -> Stream.newStream(x2._1(), x2._2())),
            a.dot(
                a.second(aArrowApply.app()),
                a.dot(
                    a.arr((T2<T2<C,Automaton<A,T2<E,B>,C>>,T2<E,Stream<B>>> x2) -> T2.of(x2._1()._1(), T2.of(run(aArrowLoop, aArrowApply, x2._1()._2()), x2._2()))),
                    a.dot(
                        a.first(x.unAutomaton()),
                        a.arr((T2<E,Stream<B>> x2) -> T2.of(T2.of(x2._1(), x2._2().head()), T2.of(x2._1(), x2._2().tail())))
                    )
                )
            )
        );
    }
    
    static <A> AutomatonCategory<A> automatonCategory(Arrow<A> aArrow) {
        return (AutomatonCategory<A>)() -> aArrow;
    }
    
    static <A> AutomatonArrow<A> automatonArrow(Arrow<A> aArrow) {
        return (AutomatonArrow<A>)() -> aArrow;
    }
    
    static <A> AutomatonArrowZero<A> automatonArrowZero(ArrowZero<A> aArrowZero) {
        return (AutomatonArrowZero<A>)() -> aArrowZero;
    }
    
    static <A> AutomatonArrowPlus<A> automatonArrowPlus(ArrowPlus<A> aArrowPlus) {
        return (AutomatonArrowPlus<A>)() -> aArrowPlus;
    }
    
    static <A> AutomatonArrowChoice<A> automatonArrowChoice(ArrowChoice<A> aArrowChoice) {
        return (AutomatonArrowChoice<A>)() -> aArrowChoice;
    }
    
    static <A> AutomatonArrowTransformer<A> arrowTransformer(Arrow<A> aArrow) {
        return (AutomatonArrowTransformer<A>)() -> aArrow;
    }
}
