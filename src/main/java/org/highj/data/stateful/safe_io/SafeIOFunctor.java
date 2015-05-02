/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.safe_io;

import java.util.function.Function;
import org.highj._;
import org.highj.data.stateful.SafeIO;
import org.highj.typeclass1.functor.Functor;

/**
 *
 * @author clintonselke
 */
public interface SafeIOFunctor extends Functor<SafeIO.µ> {

    @Override
    public default <A, B> SafeIO<B> map(Function<A, B> fn, _<SafeIO.µ, A> nestedA) {
        return () -> fn.apply(SafeIO.narrow(nestedA).run());
    }
}
