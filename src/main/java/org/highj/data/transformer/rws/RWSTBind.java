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
import org.highj.typeclass1.monad.Bind;

import static org.highj.Hkt.asRWST;

/**
 *
 * @author clintonselke
 */
public interface RWSTBind<R,W,S,M> extends RWSTApply<R,W,S,M>, Bind<__<__<__<__<RWST.µ,R>,W>,S>,M>> {

    @Override
    public default <A, B> RWST<R, W, S, M, B> bind(__<__<__<__<__<RWST.µ, R>, W>, S>, M>, A> nestedA, Function<A, __<__<__<__<__<RWST.µ, R>, W>, S>, M>, B>> fn) {
        return (R r, S s) -> getM().bind(
            asRWST(nestedA).run(r, s),
            (T3<A,S,W> x) -> getM().map(
                (T3<B,S,W> x2) -> T3.of(x2._1(), x2._2(), getW().apply(x._3(), x2._3())),
                asRWST(fn.apply(x._1())).run(r, x._2())
            )
        );
    }
}
