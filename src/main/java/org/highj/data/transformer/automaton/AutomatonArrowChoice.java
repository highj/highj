/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.automaton;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.transformer.Automaton;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowChoice;

import static org.highj.Hkt.asAutomaton;

/**
 *
 * @author clintonselke
 */
public interface AutomatonArrowChoice<A> extends AutomatonArrow<A>, ArrowChoice<__<Automaton.µ,A>> {
    
    public ArrowChoice<A> get();
    
    @Override
    public default <B, C, D> Automaton<A, Either<B, D>, Either<C, D>> left(__2<__<Automaton.µ, A>, B, C> arrow) {
        return () -> get().dot(
            get().arr((Either<T2<C,Automaton<A,B,C>>,D> x1) ->
                x1.either(
                    (T2<C,Automaton<A,B,C>> x2) -> T2.of(Either.Left(x2._1()), left(x2._2())),
                    (D x2) -> T2.of(Either.Right(x2), left(arrow))
                )
            ),
            get().left(asAutomaton(arrow).unAutomaton())
        );
    }
}
