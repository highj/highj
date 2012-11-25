package org.highj.typeclass.comonad;

import org.highj._;
import org.highj.function.F1;
import org.highj.typeclass.monad.Functor;

public interface Extend<µ> extends Functor<µ> {

    public <A> _<µ, _<µ, A>> duplicate(_<µ, A> nestedA);

    public <A> F1<_<µ, A>,_<µ, _<µ, A>>> duplicate();

    public <A, B> F1<_<µ, A>, _<µ, B>> extend(F1<_<µ, A>, B> fn);

    // (=>=)
    public <A, B, C> F1<_<µ, A>, C> cokleisli(F1<_<µ, A>, B> f, F1<_<µ, B>, C> g);
}