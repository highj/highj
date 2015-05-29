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
import org.highj.typeclass1.monad.Bind;

/**
 *
 * @author clintonselke
 */
public interface RWSTBind<R,W,S,M> extends RWSTApply<R,W,S,M>, Bind<__.µ<___.µ<____.µ<_____.µ<RWST.µ,R>,W>,S>,M>> {

    @Override
    public default <A, B> RWST<R, W, S, M, B> bind(_<__.µ<___.µ<____.µ<_____.µ<RWST.µ, R>, W>, S>, M>, A> nestedA, Function<A, _<__.µ<___.µ<____.µ<_____.µ<RWST.µ, R>, W>, S>, M>, B>> fn) {
        return (R r, S s) -> m().bind(
            RWST.narrow(nestedA).run(r, s),
            (T3<A,S,W> x) -> m().map(
                (T3<B,S,W> x2) -> T3.of(x2._1(), x2._2(), w().apply(x._3(), x2._3())),
                RWST.narrow(fn.apply(x._1())).run(r, x._2())
            )
        );
    }
}
