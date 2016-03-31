/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import java.util.function.Function;
import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj._____;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.functor.Functor;

/**
 *
 * @author clintonselke
 */
public interface RWSTFunctor<R,W,S,M> extends Functor<_<_<_<_<RWST.µ,R>,W>,S>,M>> {

    public Functor<M> m();

    @Override
    public default <A, B> RWST<R, W, S, M, B> map(Function<A, B> f, _<_<_<_<_<RWST.µ, R>, W>, S>, M>, A> nestedA) {
        return (R r, S s) -> m().map(
            (T3<A,S,W> x) -> T3.of(f.apply(x._1()), x._2(), x._3()),
            RWST.narrow(nestedA).run(r, s)
        );
    }
}