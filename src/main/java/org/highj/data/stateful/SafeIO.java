/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful;

import org.derive4j.hkt.__;
import org.highj.data.stateful.safe_io.SafeIOApplicative;
import org.highj.data.stateful.safe_io.SafeIOApply;
import org.highj.data.stateful.safe_io.SafeIOBind;
import org.highj.data.stateful.safe_io.SafeIOFunctor;
import org.highj.data.stateful.safe_io.SafeIOMonad;
import org.highj.data.stateful.safe_io.SafeIOMonadRec;

/**
 *
 * @author clintonselke
 */
public interface SafeIO<A> extends __<SafeIO.µ,A> {
    class µ {}
    
    A run();
    
    default IO<A> toIO() {
        return () -> run();
    }
    
    SafeIOFunctor functor = new SafeIOFunctor() {};
    
    SafeIOApply apply = new SafeIOApply() {};
    
    SafeIOApplicative applicative = new SafeIOApplicative() {};
    
    SafeIOBind bind = new SafeIOBind() {};
    
    SafeIOMonad monad = new SafeIOMonad() {};

    SafeIOMonadRec monadRec = new SafeIOMonadRec() {};
}
