package org.highj.typeclass.monad;

import org.highj._;
import org.highj.function.F1;

public interface Applicative<µ> extends Apply<µ> {

    // pure (Data.Pointed, Control.Applicative)
    public <A> _<µ, A> pure(A a);

    // curried version of pure
    //duplicated in MonadAbstract
    public <A> F1<A,_<µ, A>> pure();
}
