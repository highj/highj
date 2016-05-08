/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.io;

import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.MonadIO;

/**
 *
 * @author clintonselke
 */
public interface IOMonadIO extends IOMonad, MonadIO<IO.µ> {

    @Override
    public default <A> IO<A> liftIO(IO<A> a) {
        return a;
    }
}
