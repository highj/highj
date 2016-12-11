/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.transformer.ErrorT;
import org.highj.typeclass1.functor.Functor;

import static org.highj.Hkt.asErrorT;

/**
 *
 * @author clintonselke
 */
public interface ErrorTFunctor<E,M> extends Functor<__<__<ErrorT.µ,E>,M>> {
    
    public Functor<M> get();

    @Override
    public default <A, B> ErrorT<E, M, B> map(Function<A, B> fn, __<__<__<ErrorT.µ, E>, M>, A> nestedA) {
        return () -> get().map(
            (Either<E,A> x) -> x.rightMap(fn),
            asErrorT(nestedA).run()
        );
    }
}
