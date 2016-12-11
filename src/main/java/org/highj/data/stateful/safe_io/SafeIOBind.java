/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.safe_io;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.stateful.SafeIO;
import org.highj.typeclass1.monad.Bind;

import static org.highj.Hkt.asSafeIO;

/**
 *
 * @author clintonselke
 */
public interface SafeIOBind extends SafeIOApply, Bind<SafeIO.µ> {

    @Override
    public default <A, B> SafeIO<B> bind(__<SafeIO.µ, A> nestedA, Function<A, __<SafeIO.µ, B>> fn) {
        return () -> asSafeIO(fn.apply(asSafeIO(nestedA).run())).run();
    }
}
