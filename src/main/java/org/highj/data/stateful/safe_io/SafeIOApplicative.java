/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.safe_io;

import org.highj.data.stateful.SafeIO;
import org.highj.typeclass1.monad.Applicative;

/**
 *
 * @author clintonselke
 */
public interface SafeIOApplicative extends SafeIOApply, Applicative<SafeIO.µ> {

    @Override
    public default <A> SafeIO<A> pure(A a) {
        return () -> a;
    }
}
