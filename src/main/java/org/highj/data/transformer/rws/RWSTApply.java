/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;

/**
 *
 * @author clintonselke
 */
public interface RWSTApply<R,W,S,M> extends RWSTFunctor<R,W,S,M>, Apply<__<__<__<__<RWST.µ,R>,W>,S>,M>> {
    
    @Override
    public Bind<M> m();
    
    public Semigroup<W> w();

    @Override
    public default <A, B> RWST<R, W, S, M, B> ap(__<__<__<__<__<RWST.µ, R>, W>, S>, M>, Function<A, B>> fn, __<__<__<__<__<RWST.µ, R>, W>, S>, M>, A> nestedA) {
        return (R r, S s) -> m().bind(
            RWST.narrow(fn).run(r, s),
            (T3<Function<A,B>,S,W> x) -> m().map(
                (T3<A,S,W> x2) -> T3.of(x._1().apply(x2._1()), x2._2(), w().apply(x._3(), x2._3())),
                RWST.narrow(nestedA).run(r, x._2())
            )
        );
    }
}
