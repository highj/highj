/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.monad.MonadRec;

import static org.highj.Hkt.asRWST;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonadRec<R,W,S,M> extends RWSTMonad<R,W,S,M>, MonadRec<__<__<__<__<RWST.µ,R>,W>,S>,M>> {

    @Override
    public MonadRec<M> getM();
    
    @Override
    public default <A, B> RWST<R, W, S, M, B> tailRec(Function<A, __<__<__<__<__<RWST.µ, R>, W>, S>, M>, Either<A, B>>> f, A startA) {
        return (R r, S s0) -> getM().tailRec(
            (T3<A,S,W> x) -> getM().map(
                (T3<Either<A,B>,S,W> x2) -> {
                    S nextS = x2._2();
                    W nextW = getW().apply(x._3(), x2._3());
                    return x2._1().either(
                        (A x3) -> Either.<T3<A,S,W>,T3<B,S,W>>Left(T3.of(x3, nextS, nextW)),
                        (B x3) -> Either.<T3<A,S,W>,T3<B,S,W>>Right(T3.of(x3, nextS, nextW))
                    );
                },
                asRWST(f.apply(x._1())).run(r, x._2())
            ),
            T3.of(startA, s0, getW().identity())
        );
    }
}
