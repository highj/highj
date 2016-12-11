/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.MonadWriter;

import static org.highj.Hkt.asRWST;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonadWriter<R,W,S,M> extends RWSTMonad<R,W,S,M>, MonadWriter<W,__<__<__<__<RWST.µ,R>,W>,S>,M>> {

    @Override
    public default RWST<R, W, S, M, T0> tell(W w) {
        return (R r, S s) -> getM().pure(T3.of(T0.of(), s, w));
    }

    @Override
    public default <A> RWST<R, W, S, M, T2<A, W>> listen(__<__<__<__<__<RWST.µ, R>, W>, S>, M>, A> nestedA) {
        return (R r, S s) -> getM().map(
            (T3<A,S,W> x) -> T3.of(T2.of(x._1(),x._3()), x._2(), x._3()),
            asRWST(nestedA).run(r, s)
        );
    }

    @Override
    public default <A> RWST<R, W, S, M, A> pass(__<__<__<__<__<RWST.µ, R>, W>, S>, M>, T2<A, Function<W, W>>> m) {
        return (R r, S s) -> getM().map(
            (T3<T2<A,Function<W,W>>,S,W> x) -> T3.of(x._1()._1(), s, x._1()._2().apply(x._3())),
            asRWST(m).run(r, s)
        );
    }
    
    
}
