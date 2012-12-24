package org.highj.typeclass.monad;

import org.highj._;
import org.highj.function.F1;

//minimal definition pure() OR pure(a)
public interface Applicative<mu> extends Apply<mu> {

    // pure (Data.Pointed, Control.Applicative)
    public default  <A> _<mu, A> pure(A a) {
        return this.<A>pure().$(a);
    }

    // curried version of pure
    public default <A> F1<A,_<mu, A>> pure() {
        return new F1<A,_<mu, A>>(){
            @Override
            public _<mu, A> $(A a) {
                return pure(a);
            }
        };
    }
}
