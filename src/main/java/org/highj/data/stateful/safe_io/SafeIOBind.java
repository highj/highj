/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.safe_io;

import java.util.function.Function;
import org.highj._;
import org.highj.data.stateful.SafeIO;
import org.highj.typeclass1.monad.Bind;

/**
 *
 * @author clintonselke
 */
public interface SafeIOBind extends SafeIOApply, Bind<SafeIO.µ> {

    @Override
    public default <A, B> SafeIO<B> bind(_<SafeIO.µ, A> nestedA, Function<A, _<SafeIO.µ, B>> fn) {
        return () -> SafeIO.narrow(fn.apply(SafeIO.narrow(nestedA).run())).run();
    }
}
