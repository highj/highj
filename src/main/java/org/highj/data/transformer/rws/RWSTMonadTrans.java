/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import org.derive4j.hkt.__;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.monad.MonadTrans;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonadTrans<R,W,S,M> extends RWSTMonad<R,W,S,M>, MonadTrans<__<__<__<RWST.µ,R>,W>,S>,M> {

    @Override
    public default <A> RWST<R, W, S, M, A> lift(__<M, A> nestedA) {
        return (R r, S s) -> getM().map((A a) -> T3.of(a,s, getW().identity()), nestedA);
    }
}
