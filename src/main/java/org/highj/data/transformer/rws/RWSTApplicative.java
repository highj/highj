/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj._____;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Monad;

/**
 *
 * @author clintonselke
 */
public interface RWSTApplicative<R,W,S,M> extends RWSTApply<R,W,S,M>, Applicative<_<_<_<_<RWST.µ,R>,W>,S>,M>> {
    
    @Override
    public Monad<M> m();
    
    @Override
    public Monoid<W> w();

    @Override
    public default <A> RWST<R, W, S, M, A> pure(A a) {
        return (R r, S s) -> m().pure(T3.of(a,s,w().identity()));
    }
}
