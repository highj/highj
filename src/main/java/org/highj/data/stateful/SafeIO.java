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
    public static class µ {}
    
    public static <A> SafeIO<A> narrow(__<µ,A> a) {
        return (SafeIO<A>)a;
    }
    
    public A run();
    
    public default IO<A> toIO() {
        return () -> run();
    }
    
    public static final SafeIOFunctor functor = new SafeIOFunctor() {};
    
    public static final SafeIOApply apply = new SafeIOApply() {};
    
    public static final SafeIOApplicative applicative = new SafeIOApplicative() {};
    
    public static final SafeIOBind bind = new SafeIOBind() {};
    
    public static final SafeIOMonad monad = new SafeIOMonad() {};

    public static final SafeIOMonadRec monadRec = new SafeIOMonadRec() {};
}
