package org.highj.typeclass1.alternative;

import org.highj._;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.functor.Functor;

public interface Alt<µ> extends Functor<µ> {

    public <A> _<µ, A> mplus(_<µ, A> first, _<µ, A> second);

    public default <A> Semigroup<_<µ, A>> asSemigroup() {
        return Alt.this::mplus;
    }

    //public <F,A> _<F,List<A>> some(Applicative<F> applicative, _<F,A> fa);
    //public <F,A> _<F,List<A>> many(Applicative<F> applicative, _<F,A> fa);

}
