package org.highj.data.collection.maybe;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.comonad.Extend;

public class MaybeExtend extends MaybeFunctor implements Extend<Maybe.µ> {
    @Override
    public <A> Maybe<_<Maybe.µ, A>> duplicate(_<Maybe.µ, A> nestedA) {
        return Maybe.narrow(nestedA).isNothing()
                ? Maybe.<_<Maybe.µ, A>>Nothing()
                : Maybe.Just(nestedA);
    }
}
