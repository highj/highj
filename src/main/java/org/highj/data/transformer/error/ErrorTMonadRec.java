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
import org.highj.typeclass1.monad.MonadRec;

import static org.highj.Hkt.asErrorT;

/**
 *
 * @author clintonselke
 */
public interface ErrorTMonadRec<E,M> extends ErrorTMonad<E,M>, MonadRec<__<__<ErrorT.µ,E>,M>> {
    
    public MonadRec<M> get();

    @Override
    public default <A, B> ErrorT<E, M, B> tailRec(Function<A, __<__<__<ErrorT.µ, E>, M>, Either<A, B>>> f, A startA) {
        return () -> get().tailRec(
            (A a) -> get().map(
                (Either<E,Either<A,B>> x) ->
                    x.either(
                        (E err) -> Either.<A,Either<E,B>>Right(Either.Left(err)),
                        (Either<A,B> x2) -> x2.rightMap((B x3) -> Either.<E,B>Right(x3))
                    ),
                asErrorT(f.apply(a)).run()
            ),
            startA
        );
    }
}
