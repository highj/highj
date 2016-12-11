/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.io;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.MonadRec;

import static org.highj.Hkt.asIO;

/**
 *
 * @author clintonselke
 */
public interface IOMonadRec extends IOMonad, MonadRec<IO.µ> {

    @Override
    public default <A, B> IO<B> tailRec(Function<A, __<IO.µ, Either<A, B>>> f, A startA) {
        return () -> {
            A a = startA;
            while (true) {
                Either<A,B> x = asIO(f.apply(a)).run();
                for (A x2 : x.maybeLeft()) {
                    a = x2;
                }
                for (B x2 : x.maybeRight()) {
                    return x2;
                }
            }
        };
    }
}
