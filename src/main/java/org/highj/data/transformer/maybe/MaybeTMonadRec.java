/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.maybe;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.MonadRec;

import static org.highj.Hkt.asMaybeT;

/**
 *
 * @author clintonselke
 */
public interface MaybeTMonadRec<M> extends MaybeTMonad<M>, MonadRec<__<MaybeT.µ, M>> {
    
    @Override
    public MonadRec<M> get();

    @Override
    public default <A, B> MaybeT<M, B> tailRec(Function<A, __<__<MaybeT.µ, M>, Either<A, B>>> f, A startA) {
        return new MaybeT<>(get().tailRec(
            (A a) -> get().map(
                (Maybe<Either<A,B>> x) ->
                    x.cata$(
                        () -> Either.<A,Maybe<B>>Right(Maybe.Nothing()),
                        (Either<A,B> x2) -> x2.rightMap((B x3) -> Maybe.Just(x3))
                    ),
                asMaybeT(f.apply(a)).get()
            ),
            startA
        ));
    }
}
