package org.highj.typeclass1.alternative;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.typeclass1.monad.Applicative;

public interface Alternative<F> extends Applicative<F>, Plus<F> {

    //optional (Control.Applicative)
    default <A> __<F, Maybe<A>> optional(__<F, A> nestedA) {
        __<F,Maybe<A>> ma = map(Maybe::Just, nestedA);
        return mplus(ma, pure(Maybe.Nothing()));
    }

}