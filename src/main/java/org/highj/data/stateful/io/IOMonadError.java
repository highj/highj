/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.io;

import java.io.IOException;
import java.util.function.Function;
import org.highj._;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.MonadError;

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
    public default <A> IO<A> catchError(_<IO.µ, A> ma, Function<IOException, _<IO.µ, A>> fn) {
        return () -> {
            try {
                return IO.narrow(ma).run();
            } catch (IOException ex) {
                return IO.narrow(fn.apply(ex)).run();
            }
        };
    }
}
