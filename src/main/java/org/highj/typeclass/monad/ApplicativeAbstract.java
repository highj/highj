package org.highj.typeclass.monad;

import org.highj._;
import org.highj.function.F1;

public abstract class ApplicativeAbstract<mu> extends ApplyAbstract<mu> implements Applicative<mu>  {

    // pure (Data.Pointed, Control.Applicative)
    public abstract <A> _<mu, A> pure(A a);

    // curried version of pure
    //duplicated in MonadAbstract
    public <A> F1<A,_<mu, A>> pure() {
        return new F1<A,_<mu, A>>(){
            @Override
            public _<mu, A> $(A a) {
                return pure(a);
            }
        };
    }

}
