package org.highj.data.collection.maybe;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.comonad.Extend;

public interface MaybeExtend extends MaybeFunctor, Extend<Maybe.µ> {
    @Override
    default <A> Maybe<_<Maybe.µ, A>> duplicate(_<Maybe.µ, A> nestedA) {
        return Maybe.narrow(nestedA).map(Maybe::newJust);
    }
}
