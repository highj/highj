package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.typeclass1.comonad.Extend;

import static org.highj.Hkt.asMaybe;

public interface MaybeExtend extends MaybeFunctor, Extend<Maybe.µ> {
    @Override
    default <A> Maybe<__<Maybe.µ, A>> duplicate(__<Maybe.µ, A> nestedA) {
        return asMaybe(nestedA).map(Maybe::Just);
    }
}
