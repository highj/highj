/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.io;

import java.io.IOException;
import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.MonadError;

import static org.highj.Hkt.asIO;

/**
 *
 * @author clintonselke
 */
public interface IOMonadError extends IOMonad, MonadError<IOException,IO.µ> {

    @Override
    public default <A> IO<A> throwError(IOException error) {
        return () -> { throw error; };
    }

    @Override
    public default <A> IO<A> catchError(__<IO.µ, A> ma, Function<IOException, __<IO.µ, A>> fn) {
        return () -> {
            try {
                return asIO(ma).run();
            } catch (IOException ex) {
                return asIO(fn.apply(ex)).run();
            }
        };
    }
}
