/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import java.util.function.Function;
import org.highj._;
import org.highj.data.collection.Either;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.monad.MonadRec;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonadRec<R,W,S,M> extends RWSTMonad<R,W,S,M>, MonadRec<_<_<_<_<RWST.µ,R>,W>,S>,M>> {

    @Override
    public MonadRec<M> m();
    
    @Override
    public default <A, B> RWST<R, W, S, M, B> tailRec(Function<A, _<_<_<_<_<RWST.µ, R>, W>, S>, M>, Either<A, B>>> f, A startA) {
        return (R r, S s0) -> m().tailRec(
            (T3<A,S,W> x) -> m().map(
                (T3<Either<A,B>,S,W> x2) -> {
                    S nextS = x2._2();
                    W nextW = w().apply(x._3(), x2._3());
                    return x2._1().either(
                        (A x3) -> Either.<T3<A,S,W>,T3<B,S,W>>newLeft(T3.of(x3, nextS, nextW)),
                        (B x3) -> Either.<T3<A,S,W>,T3<B,S,W>>newRight(T3.of(x3, nextS, nextW))
                    );
                },
                RWST.narrow(f.apply(x._1())).run(r, x._2())
            ),
            T3.of(startA, s0, w().identity())
        );
    }
}
