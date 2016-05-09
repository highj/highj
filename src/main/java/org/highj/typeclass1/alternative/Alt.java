package org.highj.typeclass1.alternative;

import org.derive4j.hkt.__;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.functor.Functor;

public interface Alt<F> extends Functor<F> {

    public <A> __<F, A> mplus(__<F, A> first, __<F, A> second);

    public default <A> Semigroup<__<F, A>> asSemigroup() {
        return Alt.this::mplus;
    }

    //public <F,A> __<F,List<A>> some(Applicative<F> applicative, __<F,A> fa);
    //public <F,A> __<F,List<A>> many(Applicative<F> applicative, __<F,A> fa);

}
