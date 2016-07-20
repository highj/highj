/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import org.derive4j.hkt.__;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.monad.MonadState;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonadState<R,W,S,M> extends RWSTMonad<R,W,S,M>, MonadState<S,__<__<__<__<RWST.µ,R>,W>,S>,M>> {

    @Override
    public default RWST<R, W, S, M, S> get() {
        return (R r, S s) -> getM().pure(T3.of(s, s, getW().identity()));
    }

    @Override
    public default RWST<R, W, S, M, T0> put(S s) {
        return (R r, S oldS) -> getM().pure(T3.of(T0.of(), s, getW().identity()));
    }
}
