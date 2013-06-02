package org.highj.typeclass1.monad;

import org.highj._;

public interface Applicative<µ> extends Apply<µ> {

    // pure (Data.Pointed, Control.Applicative)
    public <A> _<µ, A> pure(A a);

}
