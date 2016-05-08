/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.collection.Either;
import org.highj.data.transformer.ErrorT;
import org.highj.typeclass1.monad.Apply;

/**
 *
 * @author clintonselke
 */
public interface ErrorTApply<E,M> extends ErrorTFunctor<E,M>, Apply<__<__<ErrorT.µ,E>,M>> {
    
    public Apply<M> get();

    @Override
    public default <A, B> ErrorT<E, M, B> ap(__<__<__<ErrorT.µ, E>, M>, Function<A, B>> fn, __<__<__<ErrorT.µ, E>, M>, A> nestedA) {
        return () -> get().apply2(
            (Either<E,Function<A,B>> fn2) -> (Either<E,A> a) -> fn2.either(
                Either::<E,B>newLeft,
                (Function<A,B> fn3) -> a.either(
                    Either::<E,B>newLeft,
                    (A a2) -> Either.<E,B>newRight(fn3.apply(a2))
                )
            ),
            ErrorT.narrow(fn).run(),
            ErrorT.narrow(nestedA).run()
        );
    }
}
