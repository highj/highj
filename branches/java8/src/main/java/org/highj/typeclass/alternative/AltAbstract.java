package org.highj.typeclass.alternative;

import org.highj._;
import org.highj.function.F2;
import org.highj.typeclass.monad.FunctorAbstract;

//minimal implementation: one of the 'mplus' methods
public abstract class AltAbstract<mu> extends FunctorAbstract<mu> implements Alt<mu> {

    // <|> (Control.Alternative), <!> (Data.Functor.Alt)
    @Override
    public <A> _<mu, A> mplus(_<mu, A> first, _<mu, A> second) {
       return this.<A>mplus().$(first, second);
    }

    // <|> (Control.Alternative), <!> (Data.Functor.Alt)
    public <A> F2<_<mu, A>,_<mu, A>,_<mu, A>> mplus() {
       return new F2<_<mu, A>, _<mu, A>, _<mu, A>>() {
           @Override
           public _<mu, A> $(_<mu, A> one, _<mu, A> two) {
               return mplus(one, two);
           }
       };
    }

    /*
    @Override
    public <F, A> _<F, List<A>> some(Applicative<F> applicative, _<F, A> fa) {
        return null;
    }

    @Override
    public <F, A> _<F, List<A>> many(Applicative<F> applicative, _<F, A> fa) {
        return null;
    }


    some :: Applicative f => f a -> f [a]
    some v = some_v where
      many_v = some_v <!> pure []
      some_v = (:) <$> v <*> many_v

    many :: Applicative f => f a -> f [a]
    many v = many_v where
      many_v = some_v <!> pure []
      some_v = (:) <$> v <*> many_v
    */
}
