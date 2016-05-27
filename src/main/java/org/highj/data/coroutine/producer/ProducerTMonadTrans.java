/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.coroutine.producer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.coroutine.ProducerT;
import org.highj.typeclass1.monad.MonadTrans;

/**
 *
 * @author clintonselke
 */
public interface ProducerTMonadTrans<E,M> extends ProducerTMonad<E,M>, MonadTrans<__<ProducerT.µ,E>,M> {

    @Override
    public default <A> __2<__<ProducerT.µ, E>, M, A> lift(__<M, A> nestedA) {
        return ProducerT.lift(nestedA);
    }
}
