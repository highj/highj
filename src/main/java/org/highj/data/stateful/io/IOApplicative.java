package org.highj.data.stateful.io;

import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.Applicative;

public interface IOApplicative extends IOApply, Applicative<IO.µ> {
    @Override
    default <A> IO<A> pure(A a) {
        return () -> a;
    }
}
