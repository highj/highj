package org.highj.typeclass.alternative;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass.monad.Applicative;

public interface Alternative<µ> extends Applicative<µ>, Plus<µ> {

     //optional (Control.Applicative)
    public default <A> _<µ, Maybe<A>> optional(_<µ, A> nestedA) {
        return mplus(map(Maybe.<A>just(), nestedA), pure(Maybe.<A>Nothing()));
    }

}