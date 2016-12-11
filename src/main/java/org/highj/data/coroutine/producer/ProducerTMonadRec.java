/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.coroutine.producer;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.coroutine.ProducerT;
import org.highj.typeclass1.monad.MonadRec;

import static org.highj.Hkt.asProducerT;

/**
 *
 * @author clintonselke
 */
public interface ProducerTMonadRec<E,M> extends ProducerTMonad<E,M>, MonadRec<__<__<ProducerT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<ProducerT.µ, E>, M>, B> tailRec(Function<A, __<__<__<ProducerT.µ, E>, M>, Either<A, B>>> function, A startA) {
        return ProducerT.<E,M>monad().bind(function.apply(startA),
            (Either<A,B> x) -> x.either((A a) -> ProducerT.suspend(() -> asProducerT(tailRec(function, a))),
                (B b) -> ProducerT.<E,M>applicative().pure(b)
            )
        );
    }
}
