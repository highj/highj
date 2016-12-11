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
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

import static org.highj.Hkt.asErrorT;

/**
 *
 * @author clintonselke
 */
public interface ErrorTBind<E,M> extends ErrorTApply<E,M>, Bind<__<__<ErrorT.µ,E>,M>> {
    
    public Monad<M> get();

    @Override
    public default <A, B> ErrorT<E, M, B> bind(__<__<__<ErrorT.µ, E>, M>, A> nestedA, Function<A, __<__<__<ErrorT.µ, E>, M>, B>> fn) {
        return () -> get().bind(
            asErrorT(nestedA).run(),
            (Either<E,A> a) -> a.either(
                (E e) -> get().pure(Either.<E,B>Left(e)),
                (A a2) -> asErrorT(fn.apply(a2)).run()
            )
        );
    }
}
