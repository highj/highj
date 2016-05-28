/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.transformer.ErrorT;
import org.highj.typeclass1.monad.MonadTrans;

/**
 *
 * @author clintonselke
 */
public interface ErrorTMonadTrans<E,M> extends ErrorTMonad<E,M>, MonadTrans<__<ErrorT.µ,E>,M> {

    @Override
    public default <A> __2<__<ErrorT.µ, E>, M, A> lift(__<M, A> nestedA) {
        return (ErrorT<E,M,A>)() -> get().map(Either::<E,A>Right, nestedA);
    }
}
