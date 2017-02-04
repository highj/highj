package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Maybe;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ord1;
import org.highj.data.ord.Ordering;

public interface MaybeOrd1 extends Ord1<Maybe.µ> {
    Ordering nothingOrdering();

    @Override
    default <A> Ord<__<Maybe.µ, A>> cmp(Ord<? super A> ord) {
        return (one, two) -> {
            Maybe<A> maybeOne = Hkt.asMaybe(one);
            Maybe<A> maybeTwo = Hkt.asMaybe(two);
            if (maybeOne.isNothing()) {
                return maybeTwo.isNothing() ? Ordering.EQ : nothingOrdering();
            } else if (maybeTwo.isNothing()) {
                return nothingOrdering().inverse();
            } else {
                return ord.cmp(maybeOne.get(), maybeTwo.get());
            }
        };
    }
}
