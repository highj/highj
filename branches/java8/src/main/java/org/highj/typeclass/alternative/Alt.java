package org.highj.typeclass.alternative;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.function.F2;
import org.highj.typeclass.group.Semigroup;
import org.highj.typeclass.monad.Applicative;
import org.highj.typeclass.monad.Functor;

//minimal implementation: mplus() OR mplus(first,second)
public interface Alt<mu> extends Functor<mu> {

    public default <A> _<mu, A> mplus(_<mu, A> first, _<mu, A> second) {
        return this.<A>mplus().$(first, second);
    }

    // <|> (Control.Alternative), <!> (Data.Functor.Alt)
    public default <A> F2<_<mu, A>,_<mu, A>,_<mu, A>> mplus() {
        return new F2<_<mu, A>, _<mu, A>, _<mu, A>>() {
            @Override
            public _<mu, A> $(_<mu, A> one, _<mu, A> two) {
                return mplus(one, two);
            }
        };
    }

    public default <A> Semigroup<_<mu, A>> asSemigroup() {
        return () -> mplus();
    }

    //public <F,A> _<F,List<A>> some(Applicative<F> applicative, _<F,A> fa);
    //public <F,A> _<F,List<A>> many(Applicative<F> applicative, _<F,A> fa);

}
