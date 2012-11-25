package org.highj.typeclass.monad;

import org.highj._;
import org.highj.data.tuple.T0;
import org.highj.function.F1;

public interface Functor<µ> {

    // fmap (Data.Functor) 
    public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA);

    // <$  (Data.Functor)
    public <A, B> _<µ, A> left$(A a, _<µ, B> nestedB);

    //void (Control.Monad)
    public <A> _<µ, T0> voidF(_<µ, A> nestedA);

    //flip (Data.Functor.Syntax)
    public <A, B> _<µ, B> flip(_<µ, F1<A, B>> nestedFn, final A a);

    //liftA (Control.Applicative), liftM (Control.Monad), curried version of fmap
    public <A, B> F1<_<µ, A>, _<µ, B>> lift(final F1<A, B> fn);
}
