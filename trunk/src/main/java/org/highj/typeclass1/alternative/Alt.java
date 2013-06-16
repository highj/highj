package org.highj.typeclass1.alternative;

import org.highj._;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.functor.Functor;

public interface Alt<F> extends Functor<F> {

    public <A> _<F, A> mplus(_<F, A> first, _<F, A> second);

    public default <A> Semigroup<_<F, A>> asSemigroup() {
        return Alt.this::mplus;
    }

    //public <F,A> _<F,List<A>> some(Applicative<F> applicative, _<F,A> fa);
    //public <F,A> _<F,List<A>> many(Applicative<F> applicative, _<F,A> fa);

}
