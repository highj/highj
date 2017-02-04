package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Maybe;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;

public interface MaybeEq1 extends Eq1<Maybe.µ> {
    @Override
    default <T> Eq<__<Maybe.µ, T>> eq1(Eq<? super T> eq) {
        return (one, two) -> {
            Maybe<T> maybeOne = Hkt.asMaybe(one);
            Maybe<T> maybeTwo = Hkt.asMaybe(two);
            return maybeOne.isJust()
                    ? maybeTwo.map(value -> eq.eq(maybeOne.get(), value)).getOrElse(false)
                    : maybeTwo.isNothing();
        };
    }
}
