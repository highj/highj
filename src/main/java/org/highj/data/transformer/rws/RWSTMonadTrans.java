/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import org.highj._;
import org.highj.___;
import org.highj.____;
import org.highj._____;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.monad.MonadTrans;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonadTrans<R,W,S,M> extends RWSTMonad<R,W,S,M>, MonadTrans<___.µ<____.µ<_____.µ<RWST.µ,R>,W>,S>,M> {

    @Override
    public default <A> RWST<R, W, S, M, A> lift(_<M, A> nestedA) {
        return (R r, S s) -> m().map((A a) -> T3.of(a,s,w().identity()), nestedA);
    }
}
