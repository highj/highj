package org.highj.typeclass1.alternative;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.Applicative;

public interface Alternative<µ> extends Applicative<µ>, Plus<µ> {

     //optional (Control.Applicative)
    public default <A> _<µ, Maybe<A>> optional(_<µ, A> nestedA) {
        _<µ,Maybe<A>> ma = this.<A,Maybe<A>>map(Maybe::Just, nestedA);
        return mplus(ma, pure(Maybe.<A>Nothing()));
    }

}