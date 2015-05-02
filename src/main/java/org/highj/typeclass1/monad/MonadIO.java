/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.stateful.IO;

/**
 *
 * @author clintonselke
 */
public interface MonadIO<M> extends Monad<M> {
    
    public <A> _<M,A> liftIO(IO<A> a);
}
