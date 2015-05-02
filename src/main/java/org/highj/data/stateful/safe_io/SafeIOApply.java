/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.safe_io;

import java.util.function.Function;
import org.highj._;
import org.highj.data.stateful.SafeIO;
import org.highj.typeclass1.monad.Apply;

/**
 *
 * @author clintonselke
 */
public interface SafeIOApply extends SafeIOFunctor, Apply<SafeIO.µ> {

    @Override
    public default <A, B> SafeIO<B> ap(_<SafeIO.µ, Function<A, B>> fn, _<SafeIO.µ, A> nestedA) {
        return () -> {
            Function<A,B> fn2 = SafeIO.narrow(fn).run(); // <-- this side effect 1st
            return fn2.apply(SafeIO.narrow(nestedA).run()); // <-- this side effect 2nd
        };
    }
}
