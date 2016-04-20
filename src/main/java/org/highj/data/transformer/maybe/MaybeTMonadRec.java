/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.maybe;

import java.util.function.Function;
import org.highj._;
import org.highj.data.collection.Either;
import org.highj.data.collection.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.MonadRec;

/**
 *
 * @author clintonselke
 */
public interface MaybeTMonadRec<M> extends MaybeTMonad<M>, MonadRec<_<MaybeT.µ, M>> {
    
    @Override
    public MonadRec<M> get();

    @Override
    public default <A, B> MaybeT<M, B> tailRec(Function<A, _<_<MaybeT.µ, M>, Either<A, B>>> f, A startA) {
        return new MaybeT<>(get().tailRec(
            (A a) -> get().map(
                (Maybe<Either<A,B>> x) ->
                    x.lazyCata(
                        () -> Either.<A,Maybe<B>>newRight(Maybe.newNothing()),
                        (Either<A,B> x2) -> x2.rightMap((B x3) -> Maybe.newJust(x3))
                    ),
                MaybeT.narrow(f.apply(a)).get()
            ),
            startA
        ));
    }
}
