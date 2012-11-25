package org.highj.typeclass.monad;

import org.highj._;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.function.F3;

public interface Apply<µ> extends Functor<µ> {

    // <*> (Control.Applicative), ap (Control.Monad)
    public <A, B> _<µ, B> ap(_<µ, F1<A, B>> fn, _<µ, A> nestedA);

    //curried version of ap
    public <A, B> F1<_<µ, A>, _<µ, B>> ap(final _<µ, F1<A, B>> nestedFn);

    // <* (Control.Applicative)
    public <A, B> _<µ, A> leftSeq(_<µ, A> nestedA, _<µ, B> nestedB);

    // *> (Control.Applicative)
    public <A, B> _<µ, B> rightSeq(_<µ, A> nestedA, _<µ, B> nestedB);

    //liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public <A, B, C> F2<_<µ, A>, _<µ, B>, _<µ, C>> lift2(final F1<A, F1<B, C>> fn);

    //liftA3 (Control.Applicative), liftM3 (Control.Monad)
    public <A, B, C, D> F3<_<µ, A>, _<µ, B>, _<µ, C>, _<µ, D>> lift3(final F1<A, F1<B, F1<C, D>>> fn);
}
